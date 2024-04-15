package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FixedExchangeRateParam;
import com.tadpole.cloud.supplyChain.api.logistics.entity.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LogisticsNoFeeParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsBtbLogisticsNoParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsTrackReportParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsBtbLogisticsNoResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsTrackReportResult;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.FixedExchangeRateConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LsBtbLogisticsNoMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  BTB物流单服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-11-17
 */
@Slf4j
@Service
public class LsBtbLogisticsNoServiceImpl extends ServiceImpl<LsBtbLogisticsNoMapper, LsBtbLogisticsNo> implements ILsBtbLogisticsNoService {

    @Resource
    private LsBtbLogisticsNoMapper mapper;
    @Autowired
    private ILsBtbLogisticsNoDetailService logisticsNoDetailService;
    @Autowired
    private ILsBtbPackOrderService packOrderService;
    @Autowired
    private ILsBtbPackBoxService packBoxService;
    @Autowired
    private ILsLogisticsNoCheckService checkService;
    @Autowired
    private ILsLogisticsNoCheckDetailService checkDetailService;
    @Autowired
    private ILsBtbLogisticsNoService logisticsNoService;
    @Autowired
    private ILsLogisticsNoRecordService logisticsNoRecordService;
    @Autowired
    private FixedExchangeRateConsumer fixedExchangeRateConsumer;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(LsBtbLogisticsNoParam param) {
        if(StringUtils.isNotBlank(param.getShipmentEndDate())){
            param.setShipmentEndDate(param.getShipmentEndDate() + " 23:59:59");
        }
        if(StringUtils.isNotBlank(param.getSignEndDate())){
            param.setSignEndDate(param.getSignEndDate() + " 23:59:59");
        }
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData add(LsBtbLogisticsNoParam param) {
        log.info("新建物流单入参[{}]", JSONObject.toJSON(param));
        if(StringUtils.isBlank(param.getLogisticsNo())){
            return ResponseData.error("物流单号不能为空！");
        }
        LambdaQueryWrapper<LsBtbLogisticsNo> logisticsNoWrapper = new LambdaQueryWrapper();
        logisticsNoWrapper.eq(LsBtbLogisticsNo :: getLogisticsNo, param.getLogisticsNo());
        LsBtbLogisticsNo logisticsNo = logisticsNoService.getOne(logisticsNoWrapper);
        if(logisticsNo != null){
            return ResponseData.error(param.getLogisticsNo() + "物流单号已存在！");
        }
        logisticsNo = new LsBtbLogisticsNo();
        Date nowDate = DateUtil.date();
        String shipmentNum = "QTFHPC" + DateUtil.format(nowDate, "yyyyMMddHHmmssSSSS");
        String name = LoginContext.me().getLoginUser().getName();
        BeanUtils.copyProperties(param, logisticsNo);
        logisticsNo.setShipmentNum(shipmentNum);
        logisticsNo.setShipmentDate(nowDate);
        logisticsNo.setShipmentType("物流部发货");
        logisticsNo.setLogisticsStatus("已发货");
        logisticsNo.setOilFee(logisticsNo.getOilFee() == null ? BigDecimal.ZERO : logisticsNo.getOilFee());
        logisticsNo.setBusySeasonFee(logisticsNo.getBusySeasonFee() == null ? BigDecimal.ZERO : logisticsNo.getBusySeasonFee());
        logisticsNo.setOthersFee(logisticsNo.getOthersFee() == null ? BigDecimal.ZERO : logisticsNo.getOthersFee());
        logisticsNo.setCustomsFee(logisticsNo.getCustomsFee() == null ? BigDecimal.ZERO : logisticsNo.getCustomsFee());
        logisticsNo.setClearCustomsFee(logisticsNo.getClearCustomsFee() == null ? BigDecimal.ZERO : logisticsNo.getClearCustomsFee());
        logisticsNo.setTaxFee(logisticsNo.getTaxFee() == null ? BigDecimal.ZERO : logisticsNo.getTaxFee());
        logisticsNo.setCreateUser(name);
        //总费用：物流费（单价*计费量）+报关费+清关费+税费+旺季附加费+燃油附加费+附加费及杂费
        logisticsNo.setTotalFee(logisticsNo.getUnitPrice().multiply(logisticsNo.getConfirmCountFee()).add(logisticsNo.getCustomsFee()).add(logisticsNo.getClearCustomsFee()).add(logisticsNo.getTaxFee()).add(logisticsNo.getBusySeasonFee()).add(logisticsNo.getOilFee()).add(logisticsNo.getOthersFee()));
        logisticsNo.setLogisticsFee(logisticsNo.getUnitPrice().multiply(logisticsNo.getConfirmCountFee()));
        //入库BTB物流单
        logisticsNoService.save(logisticsNo);

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
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData edit(LsBtbLogisticsNoParam param) {
        log.info("编辑保存BTB物流单入参[{}]", JSONObject.toJSON(param));
        if(param.getId() == null){
            return ResponseData.error("BTB物流单ID为空");
        }
        LsBtbLogisticsNo logisticsNo = this.getById(param.getId());
        if(logisticsNo == null){
            return ResponseData.error("未查询到BTB物流单信息");
        }
        if("物流完结".equals(logisticsNo.getLogisticsStatus())){
            return ResponseData.error("物流状态完结不支持编辑保存");
        }
        Boolean isBTB = "B2B".equals(logisticsNo.getShipmentNum().substring(0,3));

        //原费用
        LogisticsNoFeeParam oldFee = new LogisticsNoFeeParam();
        oldFee.setShipmentNum(logisticsNo.getShipmentNum());
        oldFee.setConfirmFeeType(logisticsNo.getConfirmFeeType());
        oldFee.setConfirmCountFee(logisticsNo.getConfirmCountFee());
        oldFee.setLogisticsCurrency(logisticsNo.getLogisticsCurrency());
        oldFee.setUnitPrice(logisticsNo.getUnitPrice());
        oldFee.setLogisticsFee(logisticsNo.getLogisticsFee());
        oldFee.setOilFee(logisticsNo.getOilFee());
        oldFee.setBusySeasonFee(logisticsNo.getBusySeasonFee());
        oldFee.setOthersFee(logisticsNo.getOthersFee());
        oldFee.setOthersFeeRemark(logisticsNo.getOthersFeeRemark());
        oldFee.setCustomsFee(logisticsNo.getCustomsFee());
        oldFee.setClearCustomsFee(logisticsNo.getClearCustomsFee());
        oldFee.setTaxFee(logisticsNo.getTaxFee());
        oldFee.setRemark(logisticsNo.getRemark());
        oldFee.setTotalFee(logisticsNo.getTotalFee());

        Date nowDate = DateUtil.date();
        String name = LoginContext.me().getLoginUser().getName();
        BeanUtils.copyProperties(param, logisticsNo);
        logisticsNo.setOilFee(logisticsNo.getOilFee() == null ? BigDecimal.ZERO : logisticsNo.getOilFee());
        logisticsNo.setBusySeasonFee(logisticsNo.getBusySeasonFee() == null ? BigDecimal.ZERO : logisticsNo.getBusySeasonFee());
        logisticsNo.setOthersFee(logisticsNo.getOthersFee() == null ? BigDecimal.ZERO : logisticsNo.getOthersFee());
        logisticsNo.setCustomsFee(logisticsNo.getCustomsFee() == null ? BigDecimal.ZERO : logisticsNo.getCustomsFee());
        logisticsNo.setClearCustomsFee(logisticsNo.getClearCustomsFee() == null ? BigDecimal.ZERO : logisticsNo.getClearCustomsFee());
        logisticsNo.setTaxFee(logisticsNo.getTaxFee() == null ? BigDecimal.ZERO : logisticsNo.getTaxFee());
        logisticsNo.setUnitPrice(logisticsNo.getUnitPrice() == null ? BigDecimal.ZERO : logisticsNo.getUnitPrice());
        logisticsNo.setConfirmCountFee(logisticsNo.getConfirmCountFee() == null ? BigDecimal.ZERO : logisticsNo.getConfirmCountFee());
        logisticsNo.setUpdateUser(name);
        logisticsNo.setUpdateTime(nowDate);

        //校验物流单对账发货批次号是否锁定，锁定则不允许编辑
        if("物流部发货".equals(logisticsNo.getShipmentType())){
            LambdaQueryWrapper<LsLogisticsNoCheck> checkQueryWrapper = new LambdaQueryWrapper();
            checkQueryWrapper.eq(LsLogisticsNoCheck :: getShipmentNum, logisticsNo.getShipmentNum());
            LsLogisticsNoCheck check = checkService.getOne(checkQueryWrapper);
            if(check == null){
                return ResponseData.error("未查询到物流单对账信息！");
            }
            if("锁定".equals(check.getLockStatus())){
                return ResponseData.error("锁定状态不能编辑数据！");
            }

            //总费用：物流费（单价*计费量）+报关费+清关费+税费+旺季附加费+燃油附加费+附加费及杂费
            logisticsNo.setTotalFee(logisticsNo.getUnitPrice().multiply(logisticsNo.getConfirmCountFee()).add(logisticsNo.getCustomsFee()).add(logisticsNo.getClearCustomsFee()).add(logisticsNo.getTaxFee()).add(logisticsNo.getBusySeasonFee()).add(logisticsNo.getOilFee()).add(logisticsNo.getOthersFee()));

            if(isBTB){
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
            }
            logisticsNo.setLogisticsFee(logisticsNo.getUnitPrice().multiply(logisticsNo.getConfirmCountFee()));

            //更新物流单对账数据
            LsLogisticsNoCheck updateCheck = new LsLogisticsNoCheck();
            BeanUtils.copyProperties(logisticsNo, updateCheck);
            updateCheck.setId(check.getId());
            updateCheck.setUpdateUser(name);
            updateCheck.setUpdateTime(nowDate);
            updateCheck.setCreateUser(null);
            updateCheck.setCreateTime(null);
            checkService.updateById(updateCheck);

            LambdaQueryWrapper<LsLogisticsNoCheckDetail> checkDetailQw = new LambdaQueryWrapper();
            checkDetailQw.eq(LsLogisticsNoCheckDetail :: getShipmentNum, check.getShipmentNum())
                    .eq(LsLogisticsNoCheckDetail :: getOrderNum, 1);
            LsLogisticsNoCheckDetail checkDetail = checkDetailService.getOne(checkDetailQw);
            if(checkDetail == null){
                return ResponseData.error("未查询到物流单对账明细信息！");
            }
            LsLogisticsNoCheckDetail updateCheckDetail = new LsLogisticsNoCheckDetail();
            BeanUtils.copyProperties(logisticsNo, updateCheckDetail);
            updateCheckDetail.setId(checkDetail.getId());
            //合计：物流费+DTP+报关费+清关费+旺季附加费+燃油附加费+杂费+产品附加费+流转税+DUTY/201
            BigDecimal checkTotalFee = logisticsNo.getLogisticsFee().add(logisticsNo.getCustomsFee()).add(logisticsNo.getClearCustomsFee()).add(logisticsNo.getBusySeasonFee()).add(logisticsNo.getOilFee()).add(logisticsNo.getOthersFee()).add(logisticsNo.getTaxFee());
            updateCheckDetail.setTotalFee(checkTotalFee);
            if(param.getTaxFee() != null){
                updateCheckDetail.setTaxCurrency(updateCheckDetail.getLogisticsCurrency());
            }
            updateCheckDetail.setUpdateUser(name);
            updateCheckDetail.setUpdateTime(nowDate);
            updateCheckDetail.setCreateUser(null);
            updateCheckDetail.setCreateTime(null);
            checkDetailService.updateById(updateCheckDetail);

            //新费用
            LogisticsNoFeeParam newFee = new LogisticsNoFeeParam();
            newFee.setShipmentNum(logisticsNo.getShipmentNum());
            newFee.setConfirmFeeType(logisticsNo.getConfirmFeeType());
            newFee.setConfirmCountFee(logisticsNo.getConfirmCountFee());
            newFee.setLogisticsCurrency(logisticsNo.getLogisticsCurrency());
            newFee.setUnitPrice(logisticsNo.getUnitPrice());
            newFee.setLogisticsFee(logisticsNo.getLogisticsFee());
            newFee.setOilFee(logisticsNo.getOilFee());
            newFee.setBusySeasonFee(logisticsNo.getBusySeasonFee());
            newFee.setOthersFee(logisticsNo.getOthersFee());
            newFee.setOthersFeeRemark(logisticsNo.getOthersFeeRemark());
            newFee.setCustomsFee(logisticsNo.getCustomsFee());
            newFee.setClearCustomsFee(logisticsNo.getClearCustomsFee());
            newFee.setTaxFee(logisticsNo.getTaxFee());
            newFee.setRemark(logisticsNo.getRemark());
            newFee.setTotalFee(logisticsNo.getTotalFee());

            //忽略比较的实体属性名称
            List<String> ignoreNameList = new ArrayList<>();
            ignoreNameList.add("confirmFeeType");
            ignoreNameList.add("logisticsCurrency");
            ignoreNameList.add("othersFeeRemark");
            ignoreNameList.add("remark");
            if(!logisticsNoService.compareBean(oldFee, newFee, ignoreNameList)){
                //费用修改前的数据入库费用修改记录
                LsLogisticsNoRecord insertRecord = new LsLogisticsNoRecord();
                insertRecord.setCreateUser(name);
                logisticsNoService.generateLogisticsFeeRecord(insertRecord, oldFee);
                logisticsNoRecordService.save(insertRecord);
            }
        }
        this.updateById(logisticsNo);

        //更新BTB订单发货信息
        LambdaQueryWrapper<LsBtbPackBox> packBoxWrapper = new LambdaQueryWrapper();
        packBoxWrapper.eq(LsBtbPackBox :: getShipmentNum, logisticsNo.getShipmentNum());
        List<LsBtbPackBox> packBoxList = packBoxService.list(packBoxWrapper);
        if(CollectionUtil.isNotEmpty(packBoxList)){
            for (LsBtbPackBox updatePackBox : packBoxList) {
                updatePackBox.setShipmentDate(logisticsNo.getShipmentDate());
                updatePackBox.setUpdateTime(nowDate);
                updatePackBox.setUpdateUser(name);
            }
            packBoxService.updateBatchById(packBoxList);
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData del(LsBtbLogisticsNoParam param) {
        log.info("删除BTB物流单入参[{}]", JSONObject.toJSON(param));
        if(param.getId() == null){
            return ResponseData.error("BTB物流单ID为空");
        }
        LsBtbLogisticsNo logisticsNo = this.getById(param.getId());
        if(logisticsNo == null){
            return ResponseData.error("未查询到BTB物流单信息");
        }
        if("物流完结".equals(logisticsNo.getLogisticsStatus())){
            return ResponseData.error("物流状态完结不支持删除");
        }
        //校验物流单对账发货批次号是否锁定，锁定则不允许删除
        if("物流部发货".equals(logisticsNo.getShipmentType())){
            LambdaQueryWrapper<LsLogisticsNoCheck> checkQueryWrapper = new LambdaQueryWrapper();
            checkQueryWrapper.eq(LsLogisticsNoCheck :: getShipmentNum, logisticsNo.getShipmentNum());
            LsLogisticsNoCheck check = checkService.getOne(checkQueryWrapper);
            if(check != null){
                if("锁定".equals(check.getLockStatus())){
                    return ResponseData.error("锁定状态不能删除数据！");
                }
                //删除物流单对账数据
                checkService.removeById(check.getId());

                //删除物流单对账明细数据
                LambdaQueryWrapper<LsLogisticsNoCheckDetail> checkDetailQw = new LambdaQueryWrapper();
                checkDetailQw.eq(LsLogisticsNoCheckDetail :: getShipmentNum, check.getShipmentNum());
                checkDetailService.remove(checkDetailQw);

                //删除物流单费用操作记录
                LambdaQueryWrapper<LsLogisticsNoRecord> recordQw = new LambdaQueryWrapper();
                recordQw.eq(LsLogisticsNoRecord :: getShipmentNum, check.getShipmentNum());
                logisticsNoRecordService.remove(recordQw);
            }
        }

        Date nowDate = DateUtil.date();
        String name = LoginContext.me().getLoginUser().getName();

        //更新BTB订单发货信息
        Set<String> allOrderNoSet = new HashSet<>();
        Set<String> shipmentOrderNoSet = new HashSet<>();
        LambdaQueryWrapper<LsBtbPackBox> packBoxWrapper = new LambdaQueryWrapper();
        packBoxWrapper.eq(LsBtbPackBox :: getShipmentNum, logisticsNo.getShipmentNum());
        List<LsBtbPackBox> packBoxList = packBoxService.list(packBoxWrapper);
        if(CollectionUtil.isNotEmpty(packBoxList)){
            for (LsBtbPackBox updatePackBox : packBoxList) {
                updatePackBox.setShipmentDate(logisticsNo.getShipmentDate());
                updatePackBox.setLogisticsStatus("未发货");
                updatePackBox.setUpdateTime(nowDate);
                updatePackBox.setUpdateUser(name);
                allOrderNoSet.add(updatePackBox.getOrderNo());
            }
            packBoxService.updateBatchById(packBoxList);
        }

        LambdaQueryWrapper<LsBtbPackBox> packBoxWrapper1 = new LambdaQueryWrapper();
        packBoxWrapper1.in(LsBtbPackBox :: getOrderNo, allOrderNoSet)
                    .ne(LsBtbPackBox :: getLogisticsStatus, "未发货");
        List<LsBtbPackBox> shipmentPackBoxList = packBoxService.list(packBoxWrapper1);
        if(CollectionUtil.isNotEmpty(shipmentPackBoxList)){
            for (LsBtbPackBox shipmentPackBox : shipmentPackBoxList) {
                shipmentOrderNoSet.add(shipmentPackBox.getOrderNo());
            }
        }

        allOrderNoSet.removeAll(shipmentOrderNoSet);
        if(CollectionUtil.isNotEmpty(shipmentOrderNoSet)){
            LambdaUpdateWrapper<LsBtbPackOrder> updatePackOrder = new LambdaUpdateWrapper();
            updatePackOrder.in(LsBtbPackOrder :: getOrderNo, shipmentOrderNoSet)
                    .set(LsBtbPackOrder :: getLogisticsStatus, "部分发货")
                    .set(LsBtbPackOrder :: getUpdateUser, name)
                    .set(LsBtbPackOrder :: getUpdateTime, nowDate);
            packOrderService.update(updatePackOrder);
        }

        if(CollectionUtil.isNotEmpty(allOrderNoSet)){
            LambdaUpdateWrapper<LsBtbPackOrder> updatePackOrder = new LambdaUpdateWrapper();
            updatePackOrder.in(LsBtbPackOrder :: getOrderNo, allOrderNoSet)
                    .set(LsBtbPackOrder :: getShipmentType, "")
                    .set(LsBtbPackOrder :: getLogisticsStatus, "未发货")
                    .set(LsBtbPackOrder :: getUpdateUser, name)
                    .set(LsBtbPackOrder :: getUpdateTime, nowDate);
            packOrderService.update(updatePackOrder);
        }

        //删除物流单主表
        this.removeById(logisticsNo.getId());

        //删除物流单明细数据
        LambdaQueryWrapper<LsBtbLogisticsNoDetail> logisticsNoDetailWrapper = new LambdaQueryWrapper();
        logisticsNoDetailWrapper.eq(LsBtbLogisticsNoDetail :: getShipmentNum, logisticsNo.getShipmentNum());
        logisticsNoDetailService.remove(logisticsNoDetailWrapper);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData importExcel(MultipartFile file) {
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<LsBtbLogisticsNoParam>();
            EasyExcel.read(buffer, LsBtbLogisticsNoParam.class, listener).sheet().doRead();

            List<LsBtbLogisticsNoParam> dataList = listener.getDataList();
            log.info("导入BTB物流单开始[{}]", JSONObject.toJSON(dataList));
            long start = System.currentTimeMillis();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据为空，导入失败！");
            }
            Set<String> repeat = new HashSet<>();
            Set<String> logisticsNoSet = new HashSet<>();
            for (LsBtbLogisticsNoParam param : dataList) {
                String logisticsNo = param.getLogisticsNo();
                if(StringUtils.isBlank(logisticsNo)){
                    return ResponseData.error("导入数据物流单号不能为空！");
                }
                if(CollectionUtil.isNotEmpty(logisticsNoSet) && logisticsNoSet.contains(logisticsNo)){
                    repeat.add(logisticsNo);
                } else {
                    logisticsNoSet.add(logisticsNo);
                }
            }
            if(CollectionUtil.isNotEmpty(repeat)){
                return ResponseData.error("导入数据物流单号重复，" + JSONObject.toJSON(repeat) + "，导入失败！");
            }
            //异常数据
            List<LsBtbLogisticsNoParam> errorList = new ArrayList<>();
            //正常数据
            List<LsBtbLogisticsNo> logisticsNoList = new ArrayList<>();
            //正常物流单费用更新记录数据
            List<LsLogisticsNoRecord> logisticsFeeList = new ArrayList<>();
            //正常物流对账数据
            List<LsLogisticsNoCheck> updateCheckList = new ArrayList<>();
            //正常物流对账明细数据
            List<LsLogisticsNoCheckDetail> updateCheckDetailList = new ArrayList<>();
            this.validExcel(dataList, errorList, logisticsNoList, logisticsFeeList, updateCheckList, updateCheckDetailList);

            //异常数据输出异常Excel文件流
            if(CollectionUtil.isNotEmpty(errorList)){
                String fileName = this.dealImportErrorList(errorList);
                log.info("导入BTB物流单结束，耗时------------------->" + (System.currentTimeMillis() - start) + "ms");
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "存在导入失败数据！", fileName);
            } else {
                this.updateBatchById(logisticsNoList);
                if(CollectionUtil.isNotEmpty(logisticsFeeList)){
                    logisticsNoRecordService.saveBatch(logisticsFeeList);
                }
                if(CollectionUtil.isNotEmpty(updateCheckList)){
                    checkService.updateBatchById(updateCheckList);
                }
                if(CollectionUtil.isNotEmpty(updateCheckDetailList)){
                    checkDetailService.updateBatchById(updateCheckDetailList);
                }
                log.info("导入BTB物流单结束，耗时------------------->" + (System.currentTimeMillis() - start) + "ms");
                return ResponseData.success();
            }
        } catch (Exception e) {
            log.error("导入BTB物流单处理异常，导入失败！", e);
            return ResponseData.error("导入BTB物流单处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入BTB物流单关闭流异常", e);
                }
            }
        }
    }

    /**
     * 导入Excel校验
     * @param dataList 导入数据
     * @param errorList 导入校验异常数据
     * @param logisticsNoList 导入校验正常数据
     * @param logisticsFeeList 正常物流单费用更新记录数据
     * @param updateCheckList 正常物流对账数据
     * @param updateCheckDetailList 正常物流对账明细数据
     */
    private void validExcel(List<LsBtbLogisticsNoParam> dataList,
                            List<LsBtbLogisticsNoParam> errorList,
                            List<LsBtbLogisticsNo> logisticsNoList,
                            List<LsLogisticsNoRecord> logisticsFeeList,
                            List<LsLogisticsNoCheck> updateCheckList,
                            List<LsLogisticsNoCheckDetail> updateCheckDetailList){
        String name = LoginContext.me().getLoginUser().getName();
        Date nowDate = DateUtil.date();
        for (LsBtbLogisticsNoParam logisticsNoParam : dataList) {
            logisticsNoParam.setUploadRemark("");
            StringBuffer errorMsg = new StringBuffer();
            if(StringUtils.isBlank(logisticsNoParam.getLogisticsNo())){
                logisticsNoParam.setUploadRemark("物流单号为必填项！");
                errorList.add(logisticsNoParam);
                continue;
            }
            if(logisticsNoParam.getConfirmCountFee() != null && BigDecimal.ZERO.compareTo(logisticsNoParam.getConfirmCountFee()) > 0){
                errorMsg.append("计费量不能为负数！");
                errorList.add(logisticsNoParam);
            }
            if(logisticsNoParam.getUnitPrice() != null && BigDecimal.ZERO.compareTo(logisticsNoParam.getUnitPrice()) > 0){
                errorMsg.append("单价不能为负数！");
                errorList.add(logisticsNoParam);
            }
            LambdaQueryWrapper<LsBtbLogisticsNo> logisticsNoWrapper = new LambdaQueryWrapper();
            logisticsNoWrapper.eq(LsBtbLogisticsNo :: getLogisticsNo, logisticsNoParam.getLogisticsNo());
            LsBtbLogisticsNo logisticsNo = this.getOne(logisticsNoWrapper);
            if(logisticsNo == null){
                logisticsNoParam.setUploadRemark("未查询到此物流单信息！");
                errorList.add(logisticsNoParam);
                continue;
            }
            if("业务部发货".equals(logisticsNo.getShipmentType())){
                logisticsNoParam.setUploadRemark("业务部发货不支持导入！");
                errorList.add(logisticsNoParam);
                continue;
            }
            if("物流完结".equals(logisticsNo.getLogisticsStatus())){
                errorMsg.append("物流状态完结不支持编辑！");
            }
            Boolean isBTB = "B2B".equals(logisticsNo.getShipmentNum().substring(0,3));

            //校验物流单对账发货批次号是否锁定，锁定则不允许编辑
            LsLogisticsNoCheck check = null;
            if("物流部发货".equals(logisticsNo.getShipmentType())){
                LambdaQueryWrapper<LsLogisticsNoCheck> checkQueryWrapper = new LambdaQueryWrapper();
                checkQueryWrapper.eq(LsLogisticsNoCheck :: getShipmentNum, logisticsNo.getShipmentNum());
                check = checkService.getOne(checkQueryWrapper);
                if(check == null){
                    errorMsg.append("未查询到物流单对账信息！");
                    continue;
                }
                if("锁定".equals(check.getLockStatus())){
                    errorMsg.append("锁定状态不能编辑数据！");
                }

                if(isBTB){
                    //查询固定汇率
                    FixedExchangeRateParam rateParam = new FixedExchangeRateParam();
                    rateParam.setEffectDate(DateUtil.beginOfDay(logisticsNo.getShipmentDate()));
                    List<FixedExchangeRate> rateList = fixedExchangeRateConsumer.getFixedExchangeRateList(rateParam);
                    if(CollectionUtil.isEmpty(rateList)){
                        errorMsg.append(DateUtil.format(logisticsNo.getShipmentDate(), DatePattern.NORM_DATE_FORMAT) + "汇率不存在！");
                    }
                }
            }

            String uploadRemark = errorMsg.toString();
            if(StringUtils.isNotBlank(uploadRemark)){
                logisticsNoParam.setUploadRemark(uploadRemark);
                errorList.add(logisticsNoParam);
            }else{
                //原费用
                LogisticsNoFeeParam oldFee = new LogisticsNoFeeParam();
                oldFee.setShipmentNum(logisticsNo.getShipmentNum());
                oldFee.setConfirmFeeType(logisticsNo.getConfirmFeeType());
                oldFee.setConfirmCountFee(logisticsNo.getConfirmCountFee());
                oldFee.setLogisticsCurrency(logisticsNo.getLogisticsCurrency());
                oldFee.setUnitPrice(logisticsNo.getUnitPrice());
                oldFee.setLogisticsFee(logisticsNo.getLogisticsFee());
                oldFee.setOilFee(logisticsNo.getOilFee());
                oldFee.setBusySeasonFee(logisticsNo.getBusySeasonFee());
                oldFee.setOthersFee(logisticsNo.getOthersFee());
                oldFee.setOthersFeeRemark(logisticsNo.getOthersFeeRemark());
                oldFee.setCustomsFee(logisticsNo.getCustomsFee());
                oldFee.setClearCustomsFee(logisticsNo.getClearCustomsFee());
                oldFee.setTaxFee(logisticsNo.getTaxFee());
                oldFee.setRemark(logisticsNo.getRemark());
                oldFee.setTotalFee(logisticsNo.getTotalFee());

                logisticsNo.setConfirmCountFee(logisticsNoParam.getConfirmCountFee() == null ? logisticsNo.getConfirmCountFee() : logisticsNoParam.getConfirmCountFee());
                logisticsNo.setUnitPrice(logisticsNoParam.getUnitPrice() == null ? logisticsNo.getUnitPrice() : logisticsNoParam.getUnitPrice());
                logisticsNo.setOilFee(logisticsNoParam.getOilFee() == null ? logisticsNo.getOilFee() : logisticsNoParam.getOilFee());
                logisticsNo.setBusySeasonFee(logisticsNoParam.getBusySeasonFee() == null ? logisticsNo.getBusySeasonFee() : logisticsNoParam.getBusySeasonFee());
                logisticsNo.setOthersFee(logisticsNoParam.getOthersFee() == null ? logisticsNo.getOthersFee() : logisticsNoParam.getOthersFee());
                logisticsNo.setCustomsFee(logisticsNoParam.getCustomsFee() == null ? logisticsNo.getCustomsFee() : logisticsNoParam.getCustomsFee());
                logisticsNo.setClearCustomsFee(logisticsNoParam.getClearCustomsFee() == null ? logisticsNo.getClearCustomsFee() : logisticsNoParam.getClearCustomsFee());
                logisticsNo.setTaxFee(logisticsNoParam.getTaxFee() == null ? logisticsNo.getTaxFee() : logisticsNoParam.getTaxFee());
                logisticsNo.setOthersFeeRemark(StringUtils.isBlank(logisticsNoParam.getOthersFeeRemark()) ? logisticsNo.getOthersFeeRemark() : logisticsNoParam.getOthersFeeRemark());
                logisticsNo.setRemark(StringUtils.isBlank(logisticsNoParam.getRemark()) ? logisticsNo.getRemark() : logisticsNoParam.getRemark());
                logisticsNo.setUpdateTime(nowDate);
                logisticsNo.setUpdateUser(name);

                //校验物流单对账发货批次号是否锁定，锁定则不允许编辑
                if("物流部发货".equals(logisticsNo.getShipmentType())){
                    //总费用：物流费（单价*计费量）+报关费+清关费+税费+旺季附加费+燃油附加费+附加费及杂费
                    logisticsNo.setTotalFee(logisticsNo.getUnitPrice().multiply(logisticsNo.getConfirmCountFee()).add(logisticsNo.getCustomsFee()).add(logisticsNo.getClearCustomsFee()).add(logisticsNo.getTaxFee()).add(logisticsNo.getBusySeasonFee()).add(logisticsNo.getOilFee()).add(logisticsNo.getOthersFee()));

                    if(isBTB){
                        //查询固定汇率
                        FixedExchangeRateParam rateParam = new FixedExchangeRateParam();
                        rateParam.setEffectDate(DateUtil.beginOfDay(logisticsNo.getShipmentDate()));
                        List<FixedExchangeRate> rateList = fixedExchangeRateConsumer.getFixedExchangeRateList(rateParam);
                        if(CollectionUtil.isEmpty(rateList)){
                            errorMsg.append(DateUtil.format(logisticsNo.getShipmentDate(), DatePattern.NORM_DATE_FORMAT) + "汇率不存在！");
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
                        if(ResponseData.DEFAULT_SUCCESS_CODE.equals(rs.getCode())){
                            LsBtbLogisticsNo predict = (LsBtbLogisticsNo) rs.getData();
                            BeanUtils.copyProperties(predict, logisticsNo);
                            if(!"CNY".equals(logisticsNo.getLogisticsCurrency())){
                                List<FixedExchangeRate> fixedExchangeRateList = rateListMap.get(logisticsNo.getLogisticsCurrency());
                                if(CollectionUtil.isNotEmpty(fixedExchangeRateList)){
                                    logisticsNo.setDiffTotalFee(fixedExchangeRateList.get(0).getDirectRate().multiply(logisticsNo.getTotalFee()).setScale(2, RoundingMode.HALF_UP).subtract(logisticsNo.getPredictTotalFee()).abs());
                                }
                            }else{
                                logisticsNo.setDiffTotalFee(logisticsNo.getTotalFee().subtract(logisticsNo.getPredictTotalFee()).abs());
                            }
                        }
                    }
                    logisticsNo.setLogisticsFee(logisticsNo.getUnitPrice().multiply(logisticsNo.getConfirmCountFee()));

                    //更新物流单对账数据
                    LsLogisticsNoCheck updateCheck = new LsLogisticsNoCheck();
                    BeanUtils.copyProperties(logisticsNo, updateCheck);
                    updateCheck.setId(check.getId());
                    updateCheck.setUpdateUser(name);
                    updateCheck.setUpdateTime(nowDate);
                    updateCheck.setCreateUser(null);
                    updateCheck.setCreateTime(null);
                    updateCheckList.add(updateCheck);

                    LambdaQueryWrapper<LsLogisticsNoCheckDetail> checkDetailQw = new LambdaQueryWrapper();
                    checkDetailQw.eq(LsLogisticsNoCheckDetail :: getShipmentNum, check.getShipmentNum())
                            .eq(LsLogisticsNoCheckDetail :: getOrderNum, 1);
                    LsLogisticsNoCheckDetail checkDetail = checkDetailService.getOne(checkDetailQw);
                    if(checkDetail != null){
                        LsLogisticsNoCheckDetail updateCheckDetail = new LsLogisticsNoCheckDetail();
                        BeanUtils.copyProperties(logisticsNo, updateCheckDetail);
                        updateCheckDetail.setId(checkDetail.getId());
                        //合计：物流费+DTP+报关费+清关费+旺季附加费+燃油附加费+杂费+产品附加费+流转税+DUTY/201
                        BigDecimal checkTotalFee = logisticsNo.getLogisticsFee().add(logisticsNo.getCustomsFee()).add(logisticsNo.getClearCustomsFee()).add(logisticsNo.getBusySeasonFee()).add(logisticsNo.getOilFee()).add(logisticsNo.getOthersFee()).add(logisticsNo.getTaxFee());
                        updateCheckDetail.setTotalFee(checkTotalFee);
                        if(logisticsNo.getTaxFee() != null){
                            updateCheckDetail.setTaxCurrency(updateCheckDetail.getLogisticsCurrency());
                        }
                        updateCheckDetail.setUpdateUser(name);
                        updateCheckDetail.setUpdateTime(nowDate);
                        updateCheckDetail.setCreateUser(null);
                        updateCheckDetail.setCreateTime(null);
                        updateCheckDetailList.add(updateCheckDetail);
                    }
                }
                logisticsNoList.add(logisticsNo);

                //新费用
                LogisticsNoFeeParam newFee = new LogisticsNoFeeParam();
                newFee.setShipmentNum(logisticsNo.getShipmentNum());
                newFee.setConfirmFeeType(logisticsNo.getConfirmFeeType());
                newFee.setConfirmCountFee(logisticsNo.getConfirmCountFee());
                newFee.setLogisticsCurrency(logisticsNo.getLogisticsCurrency());
                newFee.setUnitPrice(logisticsNo.getUnitPrice());
                newFee.setLogisticsFee(logisticsNo.getLogisticsFee());
                newFee.setOilFee(logisticsNo.getOilFee());
                newFee.setBusySeasonFee(logisticsNo.getBusySeasonFee());
                newFee.setOthersFee(logisticsNo.getOthersFee());
                newFee.setOthersFeeRemark(logisticsNo.getOthersFeeRemark());
                newFee.setCustomsFee(logisticsNo.getCustomsFee());
                newFee.setClearCustomsFee(logisticsNo.getClearCustomsFee());
                newFee.setTaxFee(logisticsNo.getTaxFee());
                newFee.setRemark(logisticsNo.getRemark());
                newFee.setTotalFee(logisticsNo.getTotalFee());

                //忽略比较的实体属性名称
                List<String> ignoreNameList = new ArrayList<>();
                ignoreNameList.add("confirmFeeType");
                ignoreNameList.add("logisticsCurrency");
                ignoreNameList.add("othersFeeRemark");
                ignoreNameList.add("remark");
                if(!logisticsNoService.compareBean(oldFee, newFee, ignoreNameList)){
                    //费用修改前的数据入库费用修改记录
                    LsLogisticsNoRecord insertRecord = new LsLogisticsNoRecord();
                    insertRecord.setCreateUser(name);
                    logisticsNoService.generateLogisticsFeeRecord(insertRecord, oldFee);
                    logisticsFeeList.add(insertRecord);
                }
            }
        }
    }

    /**
     * 校验错误文件流输出
     * @param errorList
     * @return
     */
    private String dealImportErrorList(List<LsBtbLogisticsNoParam> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, LsBtbLogisticsNoParam.class)
                    .sheet("导入异常数据").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("BTB物流单信息导入异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("BTB物流单信息导入异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    @Override
    @DataSource(name = "logistics")
    public List<LsBtbLogisticsNoResult> export(LsBtbLogisticsNoParam param) {
        if(StringUtils.isNotBlank(param.getShipmentEndDate())){
            param.setShipmentEndDate(param.getShipmentEndDate() + " 23:59:59");
        }
        if(StringUtils.isNotBlank(param.getSignEndDate())){
            param.setSignEndDate(param.getSignEndDate() + " 23:59:59");
        }
        return this.baseMapper.export(param);
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData sign(LsBtbLogisticsNoParam param) {
        log.info("签收BTB物流单入参[{}]", JSONObject.toJSON(param));
        if(param.getId() == null){
            return ResponseData.error("BTB物流单为空");
        }
        LsBtbLogisticsNo logisticsNo = this.getById(param.getId());
        if(logisticsNo == null){
            return ResponseData.error("未查询到BTB物流单信息");
        }
        if("物流完结".equals(logisticsNo.getLogisticsStatus())){
            return ResponseData.error("物流状态完结无需再次签收");
        }
        Date nowDate = DateUtil.date();
        String name = LoginContext.me().getLoginUser().getName();
        LsBtbLogisticsNo updateLogisticsNo = new LsBtbLogisticsNo();
        updateLogisticsNo.setId(logisticsNo.getId());
        updateLogisticsNo.setSignDate(param.getSignDate());
        updateLogisticsNo.setSignUser(name);
        updateLogisticsNo.setLogisticsStatus("物流完结");
        updateLogisticsNo.setUpdateUser(name);
        updateLogisticsNo.setUpdateTime(nowDate);
        this.updateById(updateLogisticsNo);

        //更新BTB订单发货信息
        Set<String> allOrderNoSet = new HashSet<>();
        Set<String> notOverOrderNoSet = new HashSet<>();
        LambdaQueryWrapper<LsBtbPackBox> packBoxWrapper = new LambdaQueryWrapper();
        packBoxWrapper.eq(LsBtbPackBox :: getShipmentNum, logisticsNo.getShipmentNum());
        List<LsBtbPackBox> packBoxList = packBoxService.list(packBoxWrapper);
        if(CollectionUtil.isNotEmpty(packBoxList)){
            for (LsBtbPackBox updatePackBox : packBoxList) {
                updatePackBox.setLogisticsStatus("物流完结");
                updatePackBox.setUpdateTime(nowDate);
                updatePackBox.setUpdateUser(name);
                allOrderNoSet.add(updatePackBox.getOrderNo());
            }
            packBoxService.updateBatchById(packBoxList);
        }

        if(CollectionUtil.isNotEmpty(allOrderNoSet)){
            LambdaQueryWrapper<LsBtbPackBox> packBoxWrapper1 = new LambdaQueryWrapper();
            packBoxWrapper1.in(LsBtbPackBox :: getOrderNo, allOrderNoSet)
                    .ne(LsBtbPackBox :: getLogisticsStatus, "物流完结");
            List<LsBtbPackBox> shipmentPackBoxList = packBoxService.list(packBoxWrapper1);
            if(CollectionUtil.isNotEmpty(shipmentPackBoxList)){
                for (LsBtbPackBox shipmentPackBox : shipmentPackBoxList) {
                    notOverOrderNoSet.add(shipmentPackBox.getOrderNo());
                }
            }

            allOrderNoSet.removeAll(notOverOrderNoSet);
            if(CollectionUtil.isNotEmpty(allOrderNoSet)){
                LambdaUpdateWrapper<LsBtbPackOrder> updatePackOrder = new LambdaUpdateWrapper();
                updatePackOrder.in(LsBtbPackOrder :: getOrderNo, allOrderNoSet);
                updatePackOrder.set(LsBtbPackOrder :: getLogisticsStatus, "物流完结")
                        .set(LsBtbPackOrder :: getUpdateUser, name)
                        .set(LsBtbPackOrder :: getUpdateTime, nowDate);
                packOrderService.update(updatePackOrder);
            }
        }

        //更新物流单对账发货批次号签收日期
        if("物流部发货".equals(logisticsNo.getShipmentNum())){
            LambdaUpdateWrapper<LsLogisticsNoCheck> checkUpdateWrapper = new LambdaUpdateWrapper();
            checkUpdateWrapper.eq(LsLogisticsNoCheck :: getShipmentNum, logisticsNo.getShipmentNum())
                    .set(LsLogisticsNoCheck :: getSignDate, nowDate);
            checkService.update(checkUpdateWrapper);
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryBtbPage(LsLogisticsTrackReportParam param) {
        return ResponseData.success(mapper.queryBtbPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "EBMS")
    public ResponseData queryEbmsPage(LsLogisticsTrackReportParam param) {
        return ResponseData.success(mapper.queryEbmsPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public List<LsLogisticsTrackReportResult> queryBtbList(LsLogisticsTrackReportParam param) {
        return mapper.queryBtbList(param);
    }

    @Override
    @DataSource(name = "EBMS")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<LsLogisticsTrackReportResult> queryEbmsList(LsLogisticsTrackReportParam param) {
        return mapper.queryEbmsList(param);
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional
    public List<LsLogisticsTrackReportResult> exportLogisticsTrack(LsLogisticsTrackReportParam param) {
        List<LsLogisticsTrackReportResult> btbList = logisticsNoService.queryBtbList(param);
        List<LsLogisticsTrackReportResult> ebmsList = logisticsNoService.queryEbmsList(param);
        if(CollectionUtil.isNotEmpty(ebmsList)){
            btbList.addAll(ebmsList);
        }
        return btbList;
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional
    public ResponseData shipmentWarehouseSelect() {
        List<BaseSelectResult> btbShipmentWarehouseList = logisticsNoService.btbShipmentWarehouseList();
        List<BaseSelectResult> ebmsShipmentWarehouseList = logisticsNoService.ebmsShipmentWarehouseList();
        if(CollectionUtil.isNotEmpty(ebmsShipmentWarehouseList)){
            btbShipmentWarehouseList.addAll(ebmsShipmentWarehouseList);
        }
        Set<String> set = new LinkedHashSet<>();
        Iterator<BaseSelectResult> iterator = btbShipmentWarehouseList.listIterator();
        while(iterator.hasNext()) {
            BaseSelectResult result = iterator.next();
            if(set.contains(result.getName())){
                iterator.remove();
            } else {
                set.add(result.getName());
            }
        }
        return ResponseData.success(btbShipmentWarehouseList);
    }

    @Override
    @DataSource(name = "logistics")
    public List<BaseSelectResult> btbShipmentWarehouseList() {
        return mapper.btbShipmentWarehouseList();
    }

    @Override
    @DataSource(name = "EBMS")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<BaseSelectResult> ebmsShipmentWarehouseList() {
        return mapper.ebmsShipmentWarehouseList();
    }

    /**
     * 比较两个实体属性值是否一致
     * @param b1 实体1
     * @param b2 实体2
     * @param ignoreNameList 忽略比较的字段名称集合
     * @param <T>
     * @return
     */
    public <T> Boolean compareBean(T b1, T b2, List<String> ignoreNameList) {
        if(b1.getClass() != b2.getClass()){
            return false;
        }
        try {
            Boolean isSame = true;
            //获取对象所有的属性
            Class clazz = b1.getClass();
            PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                // 属性名
                String name = pd.getName();
                if(CollectionUtil.isNotEmpty(ignoreNameList) && ignoreNameList.contains(name)){
                    continue;
                }

                //get方法
                Method readMethod = pd.getReadMethod();
                if(readMethod.getReturnType() == BigDecimal.class){
                    //获取属性值
                    BigDecimal pValue1 = (BigDecimal) readMethod.invoke(b1);
                    BigDecimal pValue2 = (BigDecimal) readMethod.invoke(b2);
                    if(pValue1 == null && pValue2 == null){
                        continue;
                    }
                    if(pValue1 != null && pValue2 == null){
                        isSame = false;
                        break;
                    }
                    if(pValue1 == null && pValue2 != null){
                        isSame = false;
                        break;
                    }
                    if(pValue1.compareTo(pValue2) == 0){
                        continue;
                    }else{
                        isSame = false;
                        break;
                    }
                }else{
                    //获取属性值
                    Object pValue1 = readMethod.invoke(b1);
                    Object pValue2 = readMethod.invoke(b2);
                    if(pValue1 == null && pValue2 == null){
                        continue;
                    }
                    if(pValue1 != null && pValue2 == null){
                        isSame = false;
                        break;
                    }
                    if(pValue1 == null && pValue2 != null){
                        isSame = false;
                        break;
                    }
                    if(pValue1.equals(pValue2)){
                        continue;
                    }else{
                        isSame = false;
                        break;
                    }
                }
            }
            return isSame;
        } catch (Exception  e) {
            e.printStackTrace();
            return false;
        }
    }

    public void generateLogisticsFeeRecord(LsLogisticsNoRecord insertRecord, LogisticsNoFeeParam oldFee){
        StringBuffer sb = new StringBuffer();
        sb.append("【合计】：").append(oldFee.getTotalFee()).append("，");
        sb.append("【计费类型】：").append(oldFee.getConfirmFeeType()).append("，");
        sb.append("【计费量】：").append(oldFee.getConfirmCountFee()).append("，");
        sb.append("【单价】：").append(oldFee.getUnitPrice()).append("，");
        sb.append("【燃油附加费】：").append(oldFee.getOilFee()).append("，");
        sb.append("【旺季附加费】：").append(oldFee.getBusySeasonFee()).append("，");
        sb.append("【附加费及杂费】：").append(oldFee.getOthersFee()).append("，");
        sb.append("【附加费及杂费备注】：").append(oldFee.getOthersFeeRemark()).append("，");
        sb.append("【报关费】：").append(oldFee.getCustomsFee()).append("，");
        sb.append("【清关费】：").append(oldFee.getClearCustomsFee()).append("，");
        sb.append("【税费】：").append(oldFee.getTaxFee()).append("，");
        sb.append("【备注】：").append(oldFee.getRemark()).append("，");
        sb.append("【物流费币制】：").append(oldFee.getLogisticsCurrency()).append("，");
        String optDetail = sb.toString();
        if("，".equals(optDetail.substring(optDetail.length() - 1))){
            optDetail = optDetail.substring(0, optDetail.length() - 1);
        }
        insertRecord.setOptDetail(optDetail);
        insertRecord.setOptType("物流费更新");
        insertRecord.setShipmentNum(oldFee.getShipmentNum());
    }

}
