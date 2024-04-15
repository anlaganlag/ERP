package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FixedExchangeRateParam;
import com.tadpole.cloud.supplyChain.api.logistics.entity.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.*;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.FixedExchangeRateConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.MabangOrderConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LsBtbPackOrderMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.*;
import com.tadpole.cloud.supplyChain.modular.consumer.BaseSelectConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  BTB订单发货服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-11-10
 */
@Slf4j
@Service
public class LsBtbPackOrderServiceImpl extends ServiceImpl<LsBtbPackOrderMapper, LsBtbPackOrder> implements ILsBtbPackOrderService {

    @Resource
    private LsBtbPackOrderMapper mapper;
    @Autowired
    private ILsBtbPackBoxService btbPackBoxService;
    @Autowired
    private ILsBtbPackBoxDetailService btbPackBoxDetailService;
    @Autowired
    private ILsBtbLogisticsNoService logisticsNoService;
    @Autowired
    private ILsBtbLogisticsNoDetailService logisticsNoDetailService;
    @Autowired
    private ILsBtbPackOrderService packOrderService;
    @Autowired
    private ILsBtbPackOrderDetailService packOrderDetailService;
    @Autowired
    private ILsLogisticsNoCheckService checkService;
    @Autowired
    private ILsLogisticsNoCheckDetailService checkDetailService;
    @Autowired
    private MabangOrderConsumer mabangOrderConsumer;
    @Autowired
    private FixedExchangeRateConsumer fixedExchangeRateConsumer;
    @Autowired
    private BaseSelectConsumer baseSelectConsumer;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(LsBtbPackOrderParam param) {
        if(StringUtils.isNotBlank(param.getOrderEndDate())){
            param.setOrderEndDate(param.getOrderEndDate() + " 23:59:59");
        }
        Page<LsBtbPackOrderResult> result = mapper.queryPage(param.getPageContext(), param);
        if(CollectionUtil.isNotEmpty(result.getRecords())){
            for (LsBtbPackOrderResult btbPackOrder : result.getRecords()) {
                //BTB订单发货箱子信息
                LsBtbPackBoxParam packBoxParam = new LsBtbPackBoxParam();
                packBoxParam.setOrderNo(btbPackOrder.getOrderNo());
                btbPackOrder.setBoxList(btbPackBoxService.queryList(packBoxParam));

                //BTB订单发货箱子明细信息
                LsBtbPackBoxDetailParam packBoxDetailParam = new LsBtbPackBoxDetailParam();
                packBoxDetailParam.setOrderNo(btbPackOrder.getOrderNo());
                btbPackOrder.setBoxDetailList(btbPackBoxDetailService.queryList(packBoxDetailParam));
            }
        }
        return ResponseData.success(result);
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryDetail(LsBtbPackOrderDetailParam param) {
        return ResponseData.success(mapper.queryDetail(param));
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData applyShipment(BtbPackApplyShipmentParam param) {
        log.info("BTB订单发货申请入参[{}]", JSONObject.toJSON(param));
        List<BtbPackApplyShipmentDetailParam> applyShipmentDetailParamList = param.getDetailList();
        if(CollectionUtil.isEmpty(applyShipmentDetailParamList)){
            return ResponseData.error("订单申请发货ID不能为空！");
        }
        List<String> logisticsStatusList = Arrays.asList("已发货", "物流完结");

        //马帮已作废的订单对应的id
        List<BigDecimal> delMabangOrderIDList = new ArrayList<>();
        //马帮已作废的订单需要删除的BTB订单数据id
        List<BigDecimal> delOrderIDList = new ArrayList<>();
        //马帮已作废的订单需要删除的BTB订单明细数据订单号
        List<String> delOrderNoList = new ArrayList<>();

        Date nowDate = DateUtil.date();
        String shipmentNum = "B2BFHPC" + DateUtil.format(nowDate, "yyyyMMddHHmmssSSSS");
        String name = LoginContext.me().getLoginUser().getName();
        //物流单明细
        List<LsBtbLogisticsNoDetail> logisticsNoDetailList = new ArrayList<>();
        //发货数量
        BigDecimal shipmentQuantity = BigDecimal.ZERO;
        //出仓重量（KG）
        BigDecimal totalWeight = BigDecimal.ZERO;
        //出仓体积（CBM）
        BigDecimal totalVolume = BigDecimal.ZERO;
        List<LsBtbPackOrder> updateOrderList = new ArrayList<>();
        List<LsBtbPackBox> updatePackBoxList = new ArrayList<>();
        for (BtbPackApplyShipmentDetailParam applyShipmentDetail : applyShipmentDetailParamList) {
            LsBtbPackOrder order = this.getById(applyShipmentDetail.getOrderID());
            if(order == null){
                return ResponseData.error("未查询到BTB订单数据！");
            }
            if(logisticsStatusList.contains(order.getLogisticsStatus())){
                return ResponseData.error("已发货或物流完结的BTB订单不能申请发货！订单号：" + order.getOrderNo());
            }
            if(order.getShipmentType() != null && !order.getShipmentType().equals(param.getShipmentType())){
                return ResponseData.error("BTB订单号：" + order.getOrderNo() + "存在不同的发货类型！");
            }
            //根据BTB订单号获取BTB订单状态，已作废的订单不能申请发货且删除此订单
            log.info("马帮BTB订单查询接口请求参数[{}]", order.getOrderNo());
            ResponseData mabangResp = mabangOrderConsumer.getOrderListByOrderId(order.getOrderNo());
            if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(mabangResp.getCode())){
                mabangResp.setMessage("获取马帮BTB订单查询接口异常，" + mabangResp.getMessage());
                return mabangResp;
            }
            Object mabangDate =  mabangResp.getData();
            log.info("马帮BTB订单查询接口响应参数[{}]", JSONObject.toJSON(mabangDate));
            JSONArray jsonDataArray = JSON.parseArray(JSON.toJSONString(mabangDate));
            if(CollectionUtil.isNotEmpty(jsonDataArray)){
                Object jsonDataObj = jsonDataArray.get(0);
                JSONObject dataJson = JSON.parseObject(JSON.toJSONString(jsonDataObj));
                String platformOrderId = (String) dataJson.get("platformOrderId");
                String orderStatus = (String) dataJson.get("orderStatus");
                //5：马帮已作废，未发货的订单需要删除订单且需要重新操作申请发货
                if(order.getOrderNo().equals(platformOrderId) && "5".equals(orderStatus)){
                    delMabangOrderIDList.add(order.getId());
                    if("未发货".equals(order.getLogisticsStatus())){
                        delOrderIDList.add(order.getId());
                        delOrderNoList.add(order.getOrderNo());
                    }
                }
            }

            //业务部发货，无需填写装箱信息
            if("业务部发货".equals(param.getShipmentType())){
                //BTB物流单明细
                LsBtbLogisticsNoDetail logisticsNoDetail = new LsBtbLogisticsNoDetail();
                logisticsNoDetail.setShipmentNum(shipmentNum);
                logisticsNoDetail.setOrderNo(order.getOrderNo());
                logisticsNoDetail.setCreateUser(name);
                logisticsNoDetailList.add(logisticsNoDetail);

                order.setLogisticsStatus("已发货");
                order.setShipmentType(param.getShipmentType());
                order.setUpdateTime(nowDate);
                order.setUpdateUser(name);
                updateOrderList.add(order);
            }

            //物流部发货，需填写装箱信息
            if("物流部发货".equals(param.getShipmentType())){
                List<BigDecimal> orderDetailIDList = applyShipmentDetail.getOrderDetailIDList();
                List<LsBtbPackBox> packBoxList = btbPackBoxService.listByIds(orderDetailIDList);
                if(orderDetailIDList.size() != packBoxList.size()){
                    return ResponseData.error("存在已作废的BTB订单明细！");
                }

                //出仓重量（KG）
                BigDecimal weight = packBoxList.stream().map(LsBtbPackBox :: getBoxWeight).reduce(BigDecimal.ZERO, BigDecimal :: add);
                totalWeight = totalWeight.add(weight);
                //出仓体积（CBM）
                BigDecimal volume = packBoxList.stream().map(LsBtbPackBox :: getBoxVolume).reduce(BigDecimal.ZERO, BigDecimal :: add);
                totalVolume = totalVolume.add(volume);
                for (LsBtbPackBox packBox : packBoxList) {
                    if(logisticsStatusList.contains(packBox.getLogisticsStatus())){
                        return ResponseData.error("已发货或物流完结的BTB订单不能申请发货！订单号：" + packBox.getOrderNo());
                    }
                    //根据BTB订单号+箱条码获取箱子明细信息
                    LambdaQueryWrapper<LsBtbPackBoxDetail> packBoxDetailWrapper = new LambdaQueryWrapper();
                    packBoxDetailWrapper.eq(LsBtbPackBoxDetail :: getOrderNo, packBox.getOrderNo())
                            .eq(LsBtbPackBoxDetail :: getBoxBarcode, packBox.getBoxBarcode());
                    List<LsBtbPackBoxDetail> packBoxDetailList = btbPackBoxDetailService.list(packBoxDetailWrapper);
                    if(CollectionUtil.isEmpty(packBoxDetailList)){
                        return ResponseData.error("BTB订单号：" + packBox.getOrderNo() + "，箱条码：" + packBox.getBoxBarcode() + "的装箱产品信息不存在！");
                    }
                    BigDecimal totalQuantity = packBoxDetailList.stream().map(LsBtbPackBoxDetail :: getQuantity).reduce(BigDecimal.ZERO, BigDecimal :: add);
                    shipmentQuantity = shipmentQuantity.add(totalQuantity);

                    //BTB物流单明细
                    LsBtbLogisticsNoDetail logisticsNoDetail = new LsBtbLogisticsNoDetail();
                    logisticsNoDetail.setShipmentNum(shipmentNum);
                    logisticsNoDetail.setOrderNo(packBox.getOrderNo());
                    logisticsNoDetail.setBoxBarcode(packBox.getBoxBarcode());
                    logisticsNoDetail.setBoxNo(packBox.getBoxNo());
                    logisticsNoDetail.setBoxVolume(packBox.getBoxVolume());
                    logisticsNoDetail.setBoxWeight(packBox.getBoxWeight());
                    logisticsNoDetail.setCreateUser(name);
                    logisticsNoDetailList.add(logisticsNoDetail);

                    //更新BTB订单明细物流状态发货时间
                    packBox.setShipmentNum(shipmentNum);
                    packBox.setShipmentDate(param.getShipmentDate());
                    packBox.setLogisticsStatus("已发货");
                    packBox.setUpdateTime(nowDate);
                    packBox.setUpdateUser(name);
                    updatePackBoxList.add(packBox);
                }

                //部分发货、全部发货
                LambdaQueryWrapper<LsBtbPackBox> packBoxWrapper = new LambdaQueryWrapper();
                packBoxWrapper.eq(LsBtbPackBox :: getOrderNo, order.getOrderNo())
                        .eq(LsBtbPackBox :: getLogisticsStatus, "未发货")
                        .notIn(LsBtbPackBox :: getId, orderDetailIDList);
                if(btbPackBoxService.count(packBoxWrapper) > 0){
                    order.setLogisticsStatus("部分发货");
                } else {
                    order.setLogisticsStatus("已发货");
                }
                order.setShipmentType(param.getShipmentType());
                order.setUpdateTime(nowDate);
                order.setUpdateUser(name);
                updateOrderList.add(order);
            }
        }

        Map<String, List<LsBtbPackOrder>> groupListMap = updateOrderList.stream().collect(Collectors.groupingBy(order -> order.getPlatform() + order.getSysShopsName() + order.getSysSite(), LinkedHashMap::new, Collectors.toList()));
        if(groupListMap.keySet().size() > 1){
            return ResponseData.error("不同平台、账号、站点的BTB订单不能操作同一批发货！");
        }

        if(delMabangOrderIDList.size() != delOrderIDList.size()){
            return ResponseData.error("存在马帮系统已作废的订单，不能操作申请发货！");
        }
        //删除马帮系统BTB已作废的金畅ERP未发货的订单和订单明细
        if(CollectionUtil.isNotEmpty(delOrderIDList)){
            this.removeByIds(delOrderIDList);

            LambdaQueryWrapper<LsBtbPackBox> packBoxWrapper = new LambdaQueryWrapper();
            packBoxWrapper.in(LsBtbPackBox :: getOrderNo, delOrderNoList);
            btbPackBoxService.remove(packBoxWrapper);

            LambdaQueryWrapper<LsBtbPackBoxDetail> packBoxDetailWrapper = new LambdaQueryWrapper();
            packBoxDetailWrapper.in(LsBtbPackBoxDetail :: getOrderNo, delOrderNoList);
            btbPackBoxDetailService.remove(packBoxDetailWrapper);
            return ResponseData.error("马帮系统存在已作废的BTB订单，请重新操作申请发货！");
        }

        LsBtbLogisticsNo logisticsNo = new LsBtbLogisticsNo();
        BeanUtils.copyProperties(param, logisticsNo);
        logisticsNo.setShipmentNum(shipmentNum);
        logisticsNo.setPlatform(updateOrderList.get(0).getPlatform());
        logisticsNo.setSysShopsName(updateOrderList.get(0).getSysShopsName());
        logisticsNo.setSysSite(updateOrderList.get(0).getSysSite());
        logisticsNo.setShipmentWarehouse("国内仓");
        logisticsNo.setShipmentUnit((long) updatePackBoxList.size());
        logisticsNo.setShipmentQuantity(shipmentQuantity.longValue());
        logisticsNo.setWeight(totalWeight);
        logisticsNo.setVolume(totalVolume);
        logisticsNo.setLogisticsStatus("已发货");
        logisticsNo.setCreateUser(name);
        logisticsNo.setOilFee(logisticsNo.getOilFee() == null ? BigDecimal.ZERO : logisticsNo.getOilFee());
        logisticsNo.setBusySeasonFee(logisticsNo.getBusySeasonFee() == null ? BigDecimal.ZERO : logisticsNo.getBusySeasonFee());
        logisticsNo.setOthersFee(logisticsNo.getOthersFee() == null ? BigDecimal.ZERO : logisticsNo.getOthersFee());
        logisticsNo.setCustomsFee(logisticsNo.getCustomsFee() == null ? BigDecimal.ZERO : logisticsNo.getCustomsFee());
        logisticsNo.setClearCustomsFee(logisticsNo.getClearCustomsFee() == null ? BigDecimal.ZERO : logisticsNo.getClearCustomsFee());
        logisticsNo.setTaxFee(logisticsNo.getTaxFee() == null ? BigDecimal.ZERO : logisticsNo.getTaxFee());
        logisticsNo.setUnitPrice(logisticsNo.getUnitPrice() == null ? BigDecimal.ZERO : logisticsNo.getUnitPrice());
        logisticsNo.setConfirmCountFee(logisticsNo.getConfirmCountFee() == null ? BigDecimal.ZERO : logisticsNo.getConfirmCountFee());
        if("物流部发货".equals(logisticsNo.getShipmentType())){
            //总费用：物流费（单价*计费量）+报关费+清关费+税费+旺季附加费+燃油附加费+附加费及杂费
            logisticsNo.setTotalFee(logisticsNo.getUnitPrice().multiply(logisticsNo.getConfirmCountFee()).add(logisticsNo.getCustomsFee()).add(logisticsNo.getClearCustomsFee()).add(logisticsNo.getTaxFee()).add(logisticsNo.getBusySeasonFee()).add(logisticsNo.getOilFee()).add(logisticsNo.getOthersFee()));

            //查询固定汇率
            FixedExchangeRateParam rateParam = new FixedExchangeRateParam();
            rateParam.setEffectDate(DateUtil.beginOfDay(param.getShipmentDate()));
            List<FixedExchangeRate> rateList = fixedExchangeRateConsumer.getFixedExchangeRateList(rateParam);
            if(CollectionUtil.isEmpty(rateList)){
                return ResponseData.error(DateUtil.format(param.getShipmentDate(), DatePattern.NORM_DATE_FORMAT) + "汇率不存在！");
            }
            Map<String, List<FixedExchangeRate>> rateListMap = rateList.stream().collect(Collectors.groupingBy(FixedExchangeRate :: getOriginalCurrency, LinkedHashMap::new, Collectors.toList()));

            //计算体积重
            BigDecimal volume = logisticsNo.getVolume().multiply(new BigDecimal(1000000));
            List<String> LcCodeList = Arrays.asList("5X328V", "5X299V", "4FR657");
            BigDecimal divideVal = new BigDecimal(6000);
            if("快递".equals(logisticsNo.getTransportType()) && !LcCodeList.contains(logisticsNo.getLcCode())){
                divideVal = new BigDecimal(5000);
            }
            if("快递".equals(logisticsNo.getFreightCompany())){
                logisticsNo.setVolumeWeight(volume.divide(divideVal, 4, BigDecimal.ROUND_HALF_UP));
            } else {
                logisticsNo.setVolumeWeight(volume.divide(divideVal, 4, BigDecimal.ROUND_HALF_UP));
            }

            //预估计费类型取计费类型
            logisticsNo.setPredictFeeType(logisticsNo.getConfirmFeeType());
            //计算预估计费量
            if("重量".equals(logisticsNo.getPredictFeeType())){
                //体积重和实重，取最大的
                logisticsNo.setPredictCountFee(logisticsNo.getWeight());
                if(logisticsNo.getVolumeWeight().compareTo(logisticsNo.getWeight()) > 0){
                    logisticsNo.setPredictCountFee(logisticsNo.getVolumeWeight());
                }
            }
            if("体积".equals(logisticsNo.getPredictFeeType())){
                logisticsNo.setPredictCountFee(logisticsNo.getVolume());
            }

            //计算预估费用
            ResponseData rs = packOrderService.calculatePredict(logisticsNo, rateListMap);
            if(!ResponseData.DEFAULT_SUCCESS_CODE.equals(rs.getCode())){
                return rs;
            }
            LsBtbLogisticsNo predict = (LsBtbLogisticsNo) rs.getData();
            BeanUtils.copyProperties(predict, logisticsNo);
            if(!"CNY".equals(param.getLogisticsCurrency())){
                List<FixedExchangeRate> fixedExchangeRateList = rateListMap.get(param.getLogisticsCurrency());
                if(CollectionUtil.isNotEmpty(fixedExchangeRateList)){
                    logisticsNo.setDiffTotalFee(fixedExchangeRateList.get(0).getDirectRate().multiply(logisticsNo.getTotalFee()).setScale(2, RoundingMode.HALF_UP).subtract(logisticsNo.getPredictTotalFee()).abs());
                }
            }else{
                logisticsNo.setDiffTotalFee(logisticsNo.getTotalFee().subtract(logisticsNo.getPredictTotalFee()).abs());
            }
            logisticsNo.setLogisticsFee(logisticsNo.getUnitPrice().multiply(logisticsNo.getConfirmCountFee()));

            //入库物流单对账
            LsLogisticsNoCheck insertCheck = new LsLogisticsNoCheck();
            BeanUtils.copyProperties(logisticsNo, insertCheck);
            insertCheck.setId(null);
            insertCheck.setCheckStatus("对账中");
            insertCheck.setLockStatus("未锁定");
            insertCheck.setPaymentCount(0l);
            insertCheck.setDataSource("JCERP");
            insertCheck.setUpdateUser(null);
            insertCheck.setUpdateTime(null);
            insertCheck.setCreateUser(name);
            insertCheck.setCreateTime(null);
            checkService.save(insertCheck);

            //入库物流单对账明细
            LsLogisticsNoCheckDetail insertCheckDetail = new LsLogisticsNoCheckDetail();
            BeanUtils.copyProperties(logisticsNo, insertCheckDetail);
            insertCheckDetail.setId(null);
            insertCheckDetail.setOrderNum(1);
            //合计：物流费+DTP+报关费+清关费+旺季附加费+燃油附加费+杂费+产品附加费+流转税+DUTY/201
            BigDecimal checkTotalFee = logisticsNo.getLogisticsFee().add(logisticsNo.getCustomsFee()).add(logisticsNo.getClearCustomsFee()).add(logisticsNo.getBusySeasonFee()).add(logisticsNo.getOilFee()).add(logisticsNo.getOthersFee()).add(logisticsNo.getTaxFee());
            insertCheckDetail.setTotalFee(checkTotalFee);
            if(param.getTaxFee() != null){
                insertCheckDetail.setTaxCurrency(insertCheckDetail.getLogisticsCurrency());
            }
            insertCheckDetail.setUpdateUser(null);
            insertCheckDetail.setUpdateTime(null);
            insertCheckDetail.setCreateUser(name);
            insertCheckDetail.setCreateTime(null);
            checkDetailService.save(insertCheckDetail);
        } else {
            logisticsNo.setShipmentUnit(0l);
            logisticsNo.setShipmentQuantity(0l);
            logisticsNo.setWeight(BigDecimal.ZERO);
            logisticsNo.setVolume(BigDecimal.ZERO);
            logisticsNo.setUnitPrice(BigDecimal.ZERO);
            logisticsNo.setPredictTotalFee(BigDecimal.ZERO);
            logisticsNo.setDiffTotalFee(logisticsNo.getTotalFee().subtract(logisticsNo.getPredictTotalFee()).abs());
            logisticsNo.setVolumeWeight(BigDecimal.ZERO);
            logisticsNo.setPredictUnitPrice(BigDecimal.ZERO);
            logisticsNo.setPredictLogisticsFee(BigDecimal.ZERO);
            logisticsNo.setPredictOilFee(BigDecimal.ZERO);
            logisticsNo.setPredictBusySeasonFee(BigDecimal.ZERO);
            logisticsNo.setPredictOthersFee(BigDecimal.ZERO);
            logisticsNo.setPredictCustomsFee(BigDecimal.ZERO);
            logisticsNo.setPredictClearCustomsFee(BigDecimal.ZERO);
            logisticsNo.setPredictTaxFee(BigDecimal.ZERO);
            logisticsNo.setLogisticsFee(BigDecimal.ZERO);
            logisticsNo.setTotalFee(param.getTotalFee());
        }

        //入库BTB物流单
        logisticsNoService.save(logisticsNo);

        //入库BTB物流单明细
        if(CollectionUtil.isNotEmpty(logisticsNoDetailList)){
            for (LsBtbLogisticsNoDetail insertLogisticsNoDetail : logisticsNoDetailList) {
                logisticsNoDetailService.save(insertLogisticsNoDetail);
            }
//            logisticsNoDetailService.saveBatch(logisticsNoDetailList);
        }

        //更新BTB订单发货类型、物流状态
        this.updateBatchById(updateOrderList);

        //更新BTB订单明细物流状态发货时间
        btbPackBoxService.updateBatchById(updatePackBoxList);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "EBMS")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResponseData calculatePredict(LsBtbLogisticsNo param, Map<String, List<FixedExchangeRate>> rateListMap){
        List<LsLogisticsPriceResult> lpPriceList = mapper.getLpPrice(param);
        if(CollectionUtil.isEmpty(lpPriceList)){
            param.setPredictUnitPrice(BigDecimal.ZERO);
            param.setPredictLogisticsFee(BigDecimal.ZERO);
            param.setPredictOilFee(BigDecimal.ZERO);
            if("重量".equals(param.getConfirmFeeType())){
                param.setPredictBusySeasonFee(BigDecimal.ZERO);
                param.setPredictOthersFee(BigDecimal.ZERO);
                param.setPredictCustomsFee(BigDecimal.ZERO);
            }
            if("体积".equals(param.getConfirmFeeType())){
                param.setPredictBusySeasonFee(BigDecimal.ZERO);
                param.setPredictOthersFee(BigDecimal.ZERO);
                param.setPredictCustomsFee(BigDecimal.ZERO);
            }
            param.setPredictClearCustomsFee(BigDecimal.ZERO);
            log.info("未查询到EBMS对应的物流商价格数据！");
        } else {
            if(lpPriceList.size() > 1){
                 log.info("查询到" + lpPriceList.size() + "条EBMS对应的物流商价格数据！数据[{}]", JSONObject.toJSON(lpPriceList));
            }
            LsLogisticsPriceResult lpPrice = lpPriceList.get(0);
            param.setPredictUnitPrice(lpPrice.getBusLogpDetUnitPrice());
            param.setPredictLogisticsFee(param.getConfirmCountFee().multiply(lpPrice.getBusLogpDetUnitPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
            param.setPredictOilFee(param.getPredictLogisticsFee().multiply(lpPrice.getBusLogpDetFuelFee()).setScale(2, BigDecimal.ROUND_HALF_UP));
            if("重量".equals(param.getConfirmFeeType())){
                param.setPredictBusySeasonFee(param.getConfirmCountFee().multiply(lpPrice.getBusLogpDetBusySeasonAddFee()).setScale(2, BigDecimal.ROUND_HALF_UP));
                param.setPredictOthersFee(param.getConfirmCountFee().multiply(lpPrice.getBusLogpDetAddAndSundryFee()).setScale(2, BigDecimal.ROUND_HALF_UP));
                param.setPredictCustomsFee(param.getConfirmCountFee().multiply(lpPrice.getBusLogpDetCustDlearanceFee()).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            if("体积".equals(param.getConfirmFeeType())){
                param.setPredictBusySeasonFee(BigDecimal.ZERO);
                param.setPredictOthersFee(BigDecimal.ZERO);
                param.setPredictCustomsFee(BigDecimal.ZERO);
            }
            param.setPredictClearCustomsFee(lpPrice.getBusLogpDetCustClearanceFee());

            //非CNY的需转换成CNY
            if(StringUtils.isNotBlank(lpPrice.getBusLogpChargCurrency()) && !"CNY".equals(lpPrice.getBusLogpChargCurrency())){
                List<FixedExchangeRate> fixedExchangeRateList = rateListMap.get(param.getLogisticsCurrency());
                if(CollectionUtil.isEmpty(fixedExchangeRateList)){
                    return ResponseData.error(DateUtil.format(param.getShipmentDate(), DatePattern.NORM_DATE_FORMAT) + "，计费币别：" + param.getLogisticsCurrency() + "汇率信息不存在！");
                }
                BigDecimal directRate = fixedExchangeRateList.get(0).getDirectRate();
                param.setPredictUnitPrice(directRate.multiply(param.getPredictUnitPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
                param.setPredictLogisticsFee(directRate.multiply(param.getPredictLogisticsFee()).setScale(2, BigDecimal.ROUND_HALF_UP));
                param.setPredictOilFee(directRate.multiply(param.getPredictOilFee()).setScale(2, BigDecimal.ROUND_HALF_UP));
                param.setPredictBusySeasonFee(directRate.multiply(param.getPredictBusySeasonFee()).setScale(2, BigDecimal.ROUND_HALF_UP));
                param.setPredictOthersFee(directRate.multiply(param.getPredictOthersFee()).setScale(2, BigDecimal.ROUND_HALF_UP));
                param.setPredictCustomsFee(directRate.multiply(param.getPredictCustomsFee()).setScale(2, BigDecimal.ROUND_HALF_UP));
                param.setPredictClearCustomsFee(directRate.multiply(param.getPredictClearCustomsFee()).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }
        if(!"CNY".equals(param.getLogisticsCurrency())){
            List<FixedExchangeRate> fixedExchangeRateList = rateListMap.get(param.getLogisticsCurrency());
            if(CollectionUtil.isEmpty(fixedExchangeRateList)){
                return ResponseData.error(DateUtil.format(param.getShipmentDate(), DatePattern.NORM_DATE_FORMAT) + "，币别：" + param.getLogisticsCurrency() + "汇率信息不存在！");
            }
            param.setPredictTaxFee(fixedExchangeRateList.get(0).getDirectRate().multiply(param.getTaxFee()).setScale(2, RoundingMode.HALF_UP));
        }else{
            param.setPredictTaxFee(param.getTaxFee());
        }
        //预估总费用：预估物流费+预估报关费+预估清关费+预估税费+预估旺季附加费+预估燃油附加费+预估附加费及杂费
        param.setPredictTotalFee(param.getPredictLogisticsFee().add(param.getPredictCustomsFee()).add(param.getPredictClearCustomsFee()).add(param.getPredictTaxFee()).add(param.getPredictBusySeasonFee()).add(param.getPredictOilFee()).add(param.getPredictOthersFee()));
        return ResponseData.success(param);
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData hasLogisticsNo(BtbPackApplyShipmentParam param) {
        if(StringUtils.isBlank(param.getLogisticsNo())){
            return ResponseData.error("物流单号不能为空！");
        }
        LambdaQueryWrapper<LsBtbLogisticsNo> logisticsNoWrapper = new LambdaQueryWrapper();
        logisticsNoWrapper.eq(LsBtbLogisticsNo :: getLogisticsNo, param.getLogisticsNo())
                .ne(param.getBtbLogisticsNoId() != null, LsBtbLogisticsNo :: getId, param.getBtbLogisticsNoId());
        LsBtbLogisticsNo logisticsNo = logisticsNoService.getOne(logisticsNoWrapper);
        return ResponseData.success(logisticsNo == null ? Boolean.FALSE : Boolean.TRUE);
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData importExcel(BigDecimal id, MultipartFile file) {
        if(id == null){
            return ResponseData.error("BTB订单数据不存在，导入失败！");
        }
        LsBtbPackOrder packOrder = this.getById(id);
        if(packOrder == null){
            return ResponseData.error("未查询到BTB订单信息");
        }
        if(!"未发货".equals(packOrder.getLogisticsStatus())){
            return ResponseData.error("已发货或者部分发货不支持编辑导入");
        }

        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            ExcelReader excelReader = EasyExcel.read(buffer).build();
            BaseExcelListener listener0 = new BaseExcelListener<LsBtbPackBox>();
            ReadSheet readSheet0 = EasyExcel.readSheet(0).head(LsBtbPackBox.class).registerReadListener(listener0).build();
            BaseExcelListener listener1 = new BaseExcelListener<LsBtbPackBoxDetail>();
            ReadSheet readSheet1 = EasyExcel.readSheet(1).head(LsBtbPackBoxDetail.class).registerReadListener(listener1).build();
            excelReader.read(readSheet0, readSheet1);
            excelReader.finish();

            List<LsBtbPackBox> dataList0 = listener0.getDataList();
            log.info("BTB订单发货导入装箱基本信息数据[{}]", JSONObject.toJSON(dataList0));
            if(CollectionUtil.isEmpty(dataList0)){
                return ResponseData.error("BTB订单发货导入装箱基本信息数据为空，导入失败！");
            }

            List<LsBtbPackBoxDetail> dataList1 = listener1.getDataList();
            log.info("BTB订单发货导入装箱产品信息数据[{}]", JSONObject.toJSON(dataList1));
            if(CollectionUtil.isEmpty(dataList1)){
                return ResponseData.error("BTB订单发货导入装箱产品信息数据为空，导入失败！");
            }

            //sheet0箱条码和箱号维度
            Map<String, List<LsBtbPackBox>> boxListMap = dataList0.stream().collect(Collectors.groupingBy(detail -> detail.getBoxBarcode()+detail.getBoxNo(), LinkedHashMap::new, Collectors.toList()));
            //sheet1箱条码和箱号维度
            Map<String, List<LsBtbPackBoxDetail>> boxDetailListMap = dataList1.stream().collect(Collectors.groupingBy(detail -> detail.getBoxBarcode()+detail.getBoxNo(), LinkedHashMap::new, Collectors.toList()));
            for (String key : boxListMap.keySet()) {
                if(boxDetailListMap.get(key) == null){
                    return ResponseData.error("装箱产品信息缺少装箱基本信息的箱子！");
                }
            }
            for (String key : boxDetailListMap.keySet()) {
                if(boxListMap.get(key) == null){
                    return ResponseData.error("装箱基本信息缺少装箱产品信息的箱子！");
                }
            }

            //主数据物料编码
            String[] materialCodeArr = packOrder.getMaterialCode().split(",");
            List<String> materialCodeList = Arrays.asList(materialCodeArr);
            Set<String> materialCodeSet = new HashSet<>();
            materialCodeSet.addAll(materialCodeList);

            //查询BTB明细物料编码
            QueryWrapper<LsBtbPackOrderDetail> orderDetailQw = new QueryWrapper<>();
            orderDetailQw.select("MATERIAL_CODE AS materialCode, SUM(QUANTITY) AS QUANTITY")
                    .eq("ORDER_NO", packOrder.getOrderNo())
                    .groupBy("MATERIAL_CODE");
            List<LsBtbPackOrderDetail> orderDetailList = packOrderDetailService.list(orderDetailQw);
            if(CollectionUtil.isEmpty(orderDetailList)){
                return ResponseData.error("未查询到BTB订单明细记录！");
            }
            Set<String> btbMaterialCodeSet = orderDetailList.stream().map(i -> i.getMaterialCode()).collect(Collectors.toSet());

            List<LsBtbPackBox> errorSheet0List = new ArrayList<>();
            List<LsBtbPackBoxDetail> errorSheet1List = new ArrayList<>();
            this.validExcel(dataList0, dataList1, errorSheet0List, errorSheet1List, materialCodeSet, btbMaterialCodeSet);

            if(CollectionUtil.isEmpty(errorSheet0List) && CollectionUtil.isEmpty(errorSheet1List)){
                for (LsBtbPackBox packBoxParam : dataList0) {
                    packBoxParam.setBoxVolume(packBoxParam.getBoxLength().multiply(packBoxParam.getBoxWidth()).multiply(packBoxParam.getBoxHeight()).multiply(new BigDecimal(0.000001)).setScale(4, BigDecimal.ROUND_HALF_UP));
                }
                //导入物料编码数量汇总
                Map<String, BigDecimal> matCodeQuantity = new HashMap<>();
                for (LsBtbPackBoxDetail boxDetail : dataList1) {
                    if(matCodeQuantity.get(boxDetail.getMaterialCode()) == null){
                        matCodeQuantity.put(boxDetail.getMaterialCode(), boxDetail.getQuantity());
                    } else {
                        matCodeQuantity.put(boxDetail.getMaterialCode(), matCodeQuantity.get(boxDetail.getMaterialCode()).add(boxDetail.getQuantity()));
                    }
                }

                BigDecimal totalQuantity = dataList1.stream().map(LsBtbPackBoxDetail :: getQuantity).reduce(BigDecimal.ZERO, BigDecimal :: add);
                if(!packOrder.getTotalQuantity().equals(totalQuantity)){
                    return ResponseData.error("装箱产品信息总数量与BTB订单总数量不相等！");
                }
                //装箱产品信息的总物料编码
                Set<String> sheet1MaterialCodeSet = dataList1.stream().map(i -> i.getMaterialCode()).collect(Collectors.toSet());
                if(!(btbMaterialCodeSet.containsAll(sheet1MaterialCodeSet) && btbMaterialCodeSet.size() == sheet1MaterialCodeSet.size())){
                    return ResponseData.error("装箱产品信息的物料编码总数与BTB订单明细物料编码不相等！");
                }
                if(!(materialCodeSet.containsAll(sheet1MaterialCodeSet) && materialCodeSet.size() == sheet1MaterialCodeSet.size())){
                    return ResponseData.error("装箱产品信息的物料编码总数与BTB订单物料编码不相等！");
                }

                //比较导入物料编码数量汇总与明细物料编码汇总数是否一致，不一致则提示异常
                for (LsBtbPackOrderDetail detailMatQuantity : orderDetailList) {
                    if(new BigDecimal(detailMatQuantity.getQuantity()).compareTo(matCodeQuantity.get(detailMatQuantity.getMaterialCode())) != 0){
                        return ResponseData.error("导入装箱产品信息物料编码总数量与BTB订单明细物料编码总数量不相等！");
                    }
                }

                LsBtbPackOrderResult result = new LsBtbPackOrderResult();
                result.setId(id);
                result.setBoxList(dataList0);
                result.setBoxDetailList(dataList1);
                return ResponseData.success(result);
            } else {
                String fileName = dealImportErrorList(dataList0, dataList1);
                return ResponseData.error(fileName);
            }
        } catch (Exception e) {
            log.error("BTB订单发货导入Excel处理异常，导入失败！", e);
            return ResponseData.error("BTB订单发货导入Excel处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("BTB订单发货导入Excel关闭流异常", e);
                }
            }
        }
    }

    /**
     * 导入Excel校验
     * @param dataList0
     * @param dataList1
     * @param errorSheet0List
     * @param errorSheet1List
     * @param materialCodeSet
     * @param btbMaterialCodeSet
     */
    private void validExcel(List<LsBtbPackBox> dataList0, List<LsBtbPackBoxDetail> dataList1, List<LsBtbPackBox> errorSheet0List, List<LsBtbPackBoxDetail> errorSheet1List, Set<String> materialCodeSet, Set<String> btbMaterialCodeSet){
        Set sheet0BarcodeSet = new HashSet<>();
        Set sheet0BoxNoSet = new HashSet<>();
        for (LsBtbPackBox sheet0Param: dataList0) {
            if(StringUtils.isAnyBlank(
                    sheet0Param.getBoxBarcode(),
                    sheet0Param.getBoxNo(),
                    sheet0Param.getBoxType())
                    || sheet0Param.getBoxLength() == null
                    || sheet0Param.getBoxWidth() == null
                    || sheet0Param.getBoxHeight() == null
                    || sheet0Param.getBoxWidth() == null){
                sheet0Param.setUploadRemark("箱条码、箱号、箱型、箱长、箱宽、箱高、重量为必填项！");
                errorSheet0List.add(sheet0Param);
            }

            if(sheet0BarcodeSet.contains(sheet0Param.getBoxBarcode())){
                sheet0Param.setUploadRemark(sheet0Param.getUploadRemark() == null ? "箱条码重复！" : sheet0Param.getUploadRemark() + "箱条码重复！");
                errorSheet0List.add(sheet0Param);
            }else{
                sheet0BarcodeSet.add(sheet0Param.getBoxBarcode());
            }

            if(sheet0BoxNoSet.contains(sheet0Param.getBoxNo())){
                sheet0Param.setUploadRemark(sheet0Param.getUploadRemark() == null ? "箱号重复！" : sheet0Param.getUploadRemark() + "箱号重复！");
                errorSheet0List.add(sheet0Param);
            }else{
                sheet0BoxNoSet.add(sheet0Param.getBoxNo());
            }
        }

        Set repeatSet = new HashSet<>();
        for (LsBtbPackBoxDetail sheet1Param: dataList1) {
            if(StringUtils.isAnyBlank(
                    sheet1Param.getBoxBarcode(),
                    sheet1Param.getBoxNo(),
                    sheet1Param.getProductName(),
                    sheet1Param.getMaterialCode())
                    || sheet1Param.getQuantity() == null){
                sheet1Param.setUploadRemark("箱条码、箱号、商品名称、物料编码、数量为必填项！");
                errorSheet1List.add(sheet1Param);
            }
            if(!btbMaterialCodeSet.contains(sheet1Param.getMaterialCode())){
                sheet1Param.setUploadRemark(sheet1Param.getUploadRemark() == null ? "BTB订单明细物料编码不包含导入的物料编码！" : sheet1Param.getUploadRemark() + "BTB订单明细物料编码不包含导入的物料编码！");
                errorSheet1List.add(sheet1Param);
            }
            if(!materialCodeSet.contains(sheet1Param.getMaterialCode())){
                sheet1Param.setUploadRemark(sheet1Param.getUploadRemark() == null ? "导入的物料编码不在订单物料编码范围内！" : sheet1Param.getUploadRemark() + "导入的物料编码不在订单物料编码范围内！");
                errorSheet1List.add(sheet1Param);
            }
            String repeatStr = sheet1Param.getBoxBarcode() + sheet1Param.getBoxNo() + sheet1Param.getMaterialCode();
            if(repeatSet.contains(repeatStr)){
                sheet1Param.setUploadRemark(sheet1Param.getUploadRemark() == null ? "箱条码、箱号、物料编码重复！" : sheet1Param.getUploadRemark() + "箱条码、箱号、物料编码重复！");
                errorSheet1List.add(sheet1Param);
            }else{
                repeatSet.add(repeatStr);
            }
        }
    }

    /**
     * 校验错误文件流输出
     * @param errorSheet0List
     * @param errorSheet1List
     * @return
     */
    private String dealImportErrorList(List<LsBtbPackBox> errorSheet0List, List<LsBtbPackBoxDetail> errorSheet1List){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        ExcelWriter excelWriter = null;//EasyExcel.write(fileName).build();
        try {
            out = new FileOutputStream(filePath + fileName,false);
            excelWriter = EasyExcel.write(out).build();

            WriteSheet writeSheet0 = EasyExcel.writerSheet(0, "装箱基本信息").head(LsBtbPackBox.class).build();
            excelWriter.write(errorSheet0List, writeSheet0);

            WriteSheet writeSheet1 = EasyExcel.writerSheet(1, "装箱产品信息").head(LsBtbPackBoxDetail.class).build();
            excelWriter.write(errorSheet1List, writeSheet1);
            excelWriter.finish();
        } catch (FileNotFoundException e) {
            log.error("BTB订单发货导入装箱信息异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("BTB订单发货导入装箱信息异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData edit(LsBtbPackOrderResult param) {
        log.info("编辑保存BTB订单发货入参[{}]", JSONObject.toJSON(param));
        if(param.getId() == null){
            return ResponseData.error("BTB订单ID为空");
        }
        LsBtbPackOrder packOrder = this.getById(param.getId());
        if(packOrder == null){
            return ResponseData.error("未查询到BTB订单信息");
        }
        if(!"未发货".equals(packOrder.getLogisticsStatus())){
            return ResponseData.error("已发货或者部分发货不支持编辑保存");
        }
        List<LsBtbPackBox> insertBoxList = param.getBoxList();
        List<LsBtbPackBoxDetail> insertBoxDetailList = param.getBoxDetailList();
        if(CollectionUtil.isEmpty(insertBoxList)){
            return ResponseData.error("装箱基本信息不允许为空");
        }
        if(CollectionUtil.isEmpty(insertBoxDetailList)){
            return ResponseData.error("装箱产品信息不允许为空");
        }

        //计算总重量KG、总体积CBM、总数量
        BigDecimal totalWeight = insertBoxList.stream().map(LsBtbPackBox :: getBoxWeight).reduce(BigDecimal.ZERO, BigDecimal :: add);
        BigDecimal totalVolume = insertBoxList.stream().map(LsBtbPackBox :: getBoxVolume).reduce(BigDecimal.ZERO, BigDecimal :: add);
        BigDecimal totalQuantity = insertBoxDetailList.stream().map(LsBtbPackBoxDetail :: getQuantity).reduce(BigDecimal.ZERO, BigDecimal :: add);
        if(!packOrder.getTotalQuantity().equals(totalQuantity)){
            return ResponseData.error("装箱产品信息总数量与BTB订单总数量不相等！");
        }
        LsBtbPackOrder updatePackOrder = new LsBtbPackOrder();
        updatePackOrder.setId(packOrder.getId());
        updatePackOrder.setTotalWeight(totalWeight);
        updatePackOrder.setTotalVolume(totalVolume);
        updatePackOrder.setUpdateTime(DateUtil.date());
        String name = LoginContext.me().getLoginUser().getName();
        updatePackOrder.setUpdateUser(name);
        this.updateById(updatePackOrder);

        LambdaQueryWrapper<LsBtbPackBox> packBoxWrapper = new LambdaQueryWrapper();
        packBoxWrapper.eq(LsBtbPackBox :: getOrderNo, packOrder.getOrderNo());
        btbPackBoxService.remove(packBoxWrapper);
        for (LsBtbPackBox insertBox : insertBoxList) {
            insertBox.setOrderNo(packOrder.getOrderNo());
            insertBox.setLogisticsStatus("未发货");
            insertBox.setCreateUser(name);
            btbPackBoxService.save(insertBox);
        }
//        btbPackBoxService.saveBatch(insertBoxList);

        LambdaQueryWrapper<LsBtbPackBoxDetail> packBoxDetailWrapper = new LambdaQueryWrapper();
        packBoxDetailWrapper.eq(LsBtbPackBoxDetail :: getOrderNo, packOrder.getOrderNo());
        btbPackBoxDetailService.remove(packBoxDetailWrapper);
        for (LsBtbPackBoxDetail insertBoxDetail : insertBoxDetailList) {
            insertBoxDetail.setOrderNo(packOrder.getOrderNo());
            insertBoxDetail.setCreateUser(name);
            btbPackBoxDetailService.save(insertBoxDetail);
        }
//        btbPackBoxDetailService.saveBatch(insertBoxDetailList);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional
    public LsBtbPackOrderResult export(LsBtbPackOrderParam param) {
        LsBtbPackOrderResult result = new LsBtbPackOrderResult();
        LsBtbPackOrder btbPackOrder = this.getById(param.getId());
        if(btbPackOrder != null){
            //BTB订单发货箱子信息
            LsBtbPackBoxParam packBoxParam = new LsBtbPackBoxParam();
            packBoxParam.setOrderNo(btbPackOrder.getOrderNo());
            List<LsBtbPackBox> boxList = btbPackBoxService.queryList(packBoxParam);
            List<LsBtbPackBoxResult>  boxResultList = new ArrayList<>();
            if(CollectionUtil.isNotEmpty(boxList)){
                for (LsBtbPackBox box : boxList) {
                    LsBtbPackBoxResult boxResult = new LsBtbPackBoxResult();
                    BeanUtils.copyProperties(box, boxResult);
                    boxResultList.add(boxResult);
                }
            }
            result.setBoxResultList(boxResultList);

            //BTB订单发货箱子明细信息
            LsBtbPackBoxDetailParam packBoxDetailParam = new LsBtbPackBoxDetailParam();
            packBoxDetailParam.setOrderNo(btbPackOrder.getOrderNo());
            List<LsBtbPackBoxDetail> boxDetailList = btbPackBoxDetailService.queryList(packBoxDetailParam);
            List<LsBtbPackBoxDetailResult>  boxDetailResultList = new ArrayList<>();
            if(CollectionUtil.isNotEmpty(boxDetailList)){
                Set<String> materialCodeSet = boxDetailList.stream().map(i -> i.getMaterialCode()).collect(Collectors.toSet());
                //根据物料编码获取K3适用机型或机型、款式或尺寸、数量单位、套装属性、公司品牌、开票品名物料信息
                List<LsBtbPackBoxDetailResult> boxDetailMatList = packOrderService.getK3MaterialInfo(materialCodeSet);
                Map<String, List<LsBtbPackBoxDetailResult>> groupMap = boxDetailMatList.stream().collect(Collectors.groupingBy(LsBtbPackBoxDetailResult :: getMaterialCode, LinkedHashMap::new, Collectors.toList()));

                for (LsBtbPackBoxDetail boxDetail : boxDetailList) {
                    LsBtbPackBoxDetailResult boxDetailResult = new LsBtbPackBoxDetailResult();
                    BeanUtils.copyProperties(boxDetail, boxDetailResult);
                    boxDetailResult.setSku(boxDetailResult.getMaterialCode());
                    List<LsBtbPackBoxDetailResult> materialInfoList = groupMap.get(boxDetailResult.getMaterialCode());
                    if(CollectionUtil.isNotEmpty(materialInfoList)){
                        LsBtbPackBoxDetailResult materialInfo = materialInfoList.get(0);
                        boxDetailResult.setType(materialInfo.getType());
                        boxDetailResult.setStyle(materialInfo.getStyle());
                        boxDetailResult.setUnit(materialInfo.getUnit());
                        boxDetailResult.setAttribute(materialInfo.getAttribute());
                        boxDetailResult.setCompanyBrand(materialInfo.getCompanyBrand());
                        boxDetailResult.setInvoiceProNameCn(materialInfo.getInvoiceProNameCn());
                    }
                    boxDetailResultList.add(boxDetailResult);
                }
            }
            result.setBoxDetailResultList(boxDetailResultList);
        }
        return result;
    }

    @Override
    @DataSource(name = "erpcloud")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<LsBtbPackBoxDetailResult> getK3MaterialInfo(Set materialCodeSet){
        return mapper.getK3MaterialInfo(materialCodeSet);
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional
    public ResponseData syncBtbOrderInfo(){
        //主数据
        mapper.syncBtbOrderInfo();

        //明细数据
        List<LsBtbPackOrderDetail> orderDetailList = packOrderService.getBtbOrderDetailInfo();
        if(CollectionUtil.isNotEmpty(orderDetailList)){
            //先删除
            packOrderDetailService.remove(null);
            //新增
            packOrderDetailService.saveBatch(orderDetailList);
//            for (LsBtbPackOrderDetail orderDetail : orderDetailList) {
//                packOrderDetailService.save(orderDetail);
//            }
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "external")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<LsBtbPackOrderDetail> getBtbOrderDetailInfo(){
        return mapper.getBtbOrderDetailInfo();
    }

    @Override
    @DataSource(name = "EBMS")
    public List<LsLogisticAccountResult> logisticsAccountSelect() {
        return mapper.logisticsAccountSelect();
    }

    @Override
    @DataSource(name = "EBMS")
    public List<LsLogisticAccountResult> lCountryPartitionSelect(LsLogisticAccountResult param) {
        return mapper.lCountryPartitionSelect(param);
    }

    @Override
    @DataSource(name = "EBMS")
    public List<LsLogisticAccountResult> lCountryPartitionAreaSelect(LsLogisticAccountResult param) {
        return mapper.lCountryPartitionAreaSelect(param);
    }

    @Override
    @DataSource(name = "EBMS")
    public ResponseData freightCompanySelect() {
        return ResponseData.success(mapper.freightCompanySelect());
    }

    @Override
    @DataSource(name = "EBMS")
    public ResponseData transportTypeSelect() {
        return ResponseData.success(mapper.transportTypeSelect());
    }

    @Override
    @DataSource(name = "EBMS")
    public ResponseData logisticsChannelSelect() {
        return ResponseData.success(mapper.logisticsChannelSelect());
    }

    @Override
    @DataSource(name = "EBMS")
    public ResponseData goodsPropertySelect() {
        return ResponseData.success(mapper.goodsPropertySelect());
    }

    @Override
    @DataSource(name = "logistics")
    public  List<BaseSelectResult> btbPlatformSelect() {
        List<String> basePlatformList = baseSelectConsumer.getPlatform();
        ListIterator<String> iterator = basePlatformList.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals("BTB")) {
                iterator.set("B2B");
                break;
            }
        }

        List<String> btbPlatformList = mapper.btbPlatformSelect();
        basePlatformList.addAll(btbPlatformList);
        List<BaseSelectResult> resp = basePlatformList.stream().distinct().map(i -> {
            BaseSelectResult result = new BaseSelectResult();
            result.setName(i);
            return result;
        }).collect(Collectors.toList());
        return resp;
    }

    @Override
    @DataSource(name = "logistics")
    public  List<BaseSelectResult> btbShopNameSelect() {
        List<String> baseShopNameList = baseSelectConsumer.getShop();
        List<String> btbShopNameList = mapper.btbShopNameSelect();
        baseShopNameList.addAll(btbShopNameList);
        List<BaseSelectResult> resp = baseShopNameList.stream().distinct().map(i -> {
            BaseSelectResult result = new BaseSelectResult();
            result.setName(i);
            return result;
        }).collect(Collectors.toList());
        return resp;
    }

    @Override
    @DataSource(name = "logistics")
    public  List<BaseSelectResult> btbSiteSelect() {
        List<String> baseSiteList = baseSelectConsumer.getSite();
        List<String> btbSiteList = mapper.btbSiteSelect();
        baseSiteList.addAll(btbSiteList);
        List<BaseSelectResult> resp = baseSiteList.stream().distinct().map(i -> {
            BaseSelectResult result = new BaseSelectResult();
            result.setName(i);
            return result;
        }).collect(Collectors.toList());
        return resp;
    }

}
