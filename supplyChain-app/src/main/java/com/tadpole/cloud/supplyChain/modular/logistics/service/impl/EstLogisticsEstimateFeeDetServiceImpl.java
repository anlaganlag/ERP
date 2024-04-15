package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FixedExchangeRateParam;
import com.tadpole.cloud.supplyChain.core.util.GeneratorShipmentIdNoUtil;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.FixedExchangeRateConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.entity.EstLogisticsEstimateFee;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.EstLogisticsEstimateFeeMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.model.params.BoxDimensionParam;
import com.tadpole.cloud.supplyChain.modular.logistics.model.params.EstLogisticsEstimateFeeDetUploadParam;
import com.tadpole.cloud.supplyChain.modular.logistics.model.result.*;
import com.tadpole.cloud.supplyChain.modular.logistics.packListreader.ExcelReader;
import com.tadpole.cloud.supplyChain.modular.logistics.service.EstLogisticsEstimateFeeService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsBtbPackOrderService;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLogisticsProviderService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.auth.api.model.LoginUser;
import com.tadpole.cloud.supplyChain.modular.logistics.entity.EstLogisticsEstimateFeeDet;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.EstLogisticsEstimateFeeDetMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.EstLogisticsEstimateFeeDetService;
import com.tadpole.cloud.supplyChain.modular.logistics.model.params.EstLogisticsEstimateFeeDetParam;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

/**
 * ;(EST_LOGISTICS_ESTIMATE_FEE_DET)表服务实现类
 * @author : LSY
 * @date : 2024-3-14
 */
@Service
@Transactional
@Slf4j
public class EstLogisticsEstimateFeeDetServiceImpl  extends ServiceImpl<EstLogisticsEstimateFeeDetMapper, EstLogisticsEstimateFeeDet> implements EstLogisticsEstimateFeeDetService{
    @Resource
    private EstLogisticsEstimateFeeDetMapper estLogisticsEstimateFeeDetMapper;

    @Resource
    private EstLogisticsEstimateFeeMapper estLogisticsEstimateFeeMapper;

    @Resource
    EstLogisticsEstimateFeeDetService estLogisticsEstimateFeeDetService;

    @Resource
    EstLogisticsEstimateFeeService estLogisticsEstimateFeeService;


    @Resource
    private ILsLogisticsProviderService logisticsProviderService;

    @Resource
    FixedExchangeRateConsumer fixedExchangeRateConsumer;


    @Resource
    private ILsBtbPackOrderService service;


    @Resource
    private GeneratorShipmentIdNoUtil generatorShipmentIdNoUtil;


    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public EstLogisticsEstimateFeeDet queryById(String id){
        return estLogisticsEstimateFeeDetMapper.selectById(id);
    }
    
    /**
     * 分页查询
     *
     * @param param 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    @DataSource(name = "logistics")
    @Override
    public Page<EstLogisticsEstimateFeeDetResult> paginQuery(EstLogisticsEstimateFeeDetParam param, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<EstLogisticsEstimateFeeDet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(param.getEstId()),EstLogisticsEstimateFeeDet::getEstId, param.getEstId());
        //2. 执行分页查询
        Page<EstLogisticsEstimateFeeDetResult> pagin = new Page<>(current , size , true);
        IPage<EstLogisticsEstimateFeeDetResult> selectResult = estLogisticsEstimateFeeDetMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }




    @Override
    public String generateShipmentId(String estId){
        return estId+generatorShipmentIdNoUtil.getBillNo();
    }


    @DataSource(name = "EBMS")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public List<LsLogisticsPriceResult> getLpPrice(EstLogisticsEstimateFeeDetParam param){
        return estLogisticsEstimateFeeDetMapper.getLpPrice(param);
    }
    
    /** 
     * 新增数据
     *
     * @param estLogisticsEstimateFeeDet 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public EstLogisticsEstimateFeeDet insert(EstLogisticsEstimateFeeDet estLogisticsEstimateFeeDet){
        estLogisticsEstimateFeeDetMapper.insert(estLogisticsEstimateFeeDet);
        return estLogisticsEstimateFeeDet;
    }
    
    /** 
     * 更新数据
     *
     * @param param 实例对象
     * @return 实例对象
     */
    @DataSource(name = "logistics")
    @Override
    public EstLogisticsEstimateFeeDet update(EstLogisticsEstimateFeeDetParam param){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<EstLogisticsEstimateFeeDet> wrapper = new LambdaUpdateChainWrapper<EstLogisticsEstimateFeeDet>(estLogisticsEstimateFeeDetMapper);
        //2. 设置主键，并更新
        wrapper.eq(EstLogisticsEstimateFeeDet::getId, param.getId());
        LoginUser loginUser = LoginContext.me().getLoginUser();
        wrapper.set(EstLogisticsEstimateFeeDet::getUpdatedTime, new Date());
        wrapper.set(EstLogisticsEstimateFeeDet::getUpdatedBy, loginUser.getName());
        boolean ret = wrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(param.getId());
        }else{
            return null;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteById(String id){
        int total = estLogisticsEstimateFeeDetMapper.deleteById(id);
        return total > 0;
    }
     
     /**
     * 通过主键批量删除数据
     *
     * @param idList 主键List
     * @return 是否成功
     */
    @DataSource(name = "logistics")
    @Override
    public boolean deleteBatchIds(List<String> idList) {
        int delCount = estLogisticsEstimateFeeDetMapper.deleteBatchIds(idList);
        if (idList.size() == delCount) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    @DataSource(name = "logistics")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData uploadPackList(EstLogisticsEstimateFeeDetUploadParam param, MultipartFile file) throws IOException, InvalidFormatException {
        try {
            String estId = param.getEstId();
            String shipmentId = param.getShipmentId();
            if (ObjectUtil.isEmpty(estId)) {
                return ResponseData.error("无费用测算编号");
            }
            if (ObjectUtil.isEmpty(shipmentId)) {
                return ResponseData.error("无shipmentId");
            }
            DateTime date = DateUtil.date();
            String operator = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser()) ? LoginContext.me().getLoginUser().getName() : "";
            String operatorCode = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser()) ? LoginContext.me().getLoginUser().getAccountId().toString() : "";

            //主数据是否存在,不在则新增一条主数据
            List<EstLogisticsEstimateFee> mainRows = new LambdaQueryChainWrapper<>(estLogisticsEstimateFeeMapper)
                    .eq(EstLogisticsEstimateFee::getEstId, estId)
                    .list();
            //如果主数据已经测算提示,主数据为空测插入一条
            if (mainRows.stream().anyMatch(i-> ObjectUtil.isNotEmpty(i.getEstDate()))) {
                return ResponseData.error(StrUtil.format("费用测算编号[{}]已测算", estId));
            }
            if (ObjectUtil.isEmpty(mainRows)) {
                EstLogisticsEstimateFee mainRow = EstLogisticsEstimateFee.builder().estId(estId)
                        .perName(operator).perCode(operatorCode).createdTime(date).createdBy(operator).build();
                estLogisticsEstimateFeeService.save(mainRow);
            }

            //首次导入和或更新packList
            EstLogisticsEstimateFeeDet detailRow = new LambdaQueryChainWrapper<>(estLogisticsEstimateFeeDetMapper)
                    .eq(EstLogisticsEstimateFeeDet::getEstId, estId)
                    .eq(EstLogisticsEstimateFeeDet::getShipmentId, shipmentId).one();
            if (ObjectUtil.isEmpty(detailRow)) {
                detailRow = new EstLogisticsEstimateFeeDet();
            }


            ExcelReader reader = new ExcelReader();
            List<BoxDimensionParam> packList = reader.readExcelCsv(file);
            if (ObjectUtil.isEmpty(packList)) {
                log.error("packList解析失败");
                return ResponseData.error("packList解析失败");
            }
            //关键步骤保存packLing
            BeanUtil.copyProperties(param, detailRow, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            if (ObjectUtil.isEmpty(detailRow.getCreatedBy())) {
                detailRow.setCreatedBy(operator);
                detailRow.setCreatedTime(date);
            }
            String jsonString = JSON.toJSONString(packList);
            //更新packList
            detailRow.setPacklist(jsonString);
            detailRow.setUpdatedTime(date);
            detailRow.setUpdatedBy(operator);
            detailRow.setPerName(operator);
            detailRow.setPerCode(operatorCode);
            this.saveOrUpdate(detailRow);
            return ResponseData.success(detailRow);
        } catch (Exception e) {
            log.error("PackList导入失败"+e.getMessage());
            return ResponseData.error("PackList导入失败"+e.getMessage());
        }


    }

    @DataSource(name = "logistics")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseData singleCostEst(EstLogisticsEstimateFeeDetParam param) {
        try {
            //物流商测算:isEntryUnitPrice = 0
            log.info(StrUtil.format("开始物流商测算:是否录入:[{}]", param.getIsEntryUnitPrice()));
            String shipmentId = param.getShipmentId();
            List<EstLogisticsEstimateFeeDet> oneRow = new LambdaQueryChainWrapper<>(estLogisticsEstimateFeeDetMapper)
                    .eq(EstLogisticsEstimateFeeDet::getEstId, param.getEstId())
                    .eq(EstLogisticsEstimateFeeDet::getShipmentId, shipmentId)
                    .list();
            if (ObjectUtil.isEmpty(oneRow)) {
                return ResponseData.error(StrUtil.format("无shipmentId{}", shipmentId));
            }
            EstLogisticsEstimateFeeDet dbEntity = oneRow.get(0);
            EstLogisticsEstimateFeeDet newEntity = new EstLogisticsEstimateFeeDet();
            String packListStr = dbEntity.getPacklist();
            String detEntityId = dbEntity.getId();
            BeanUtil.copyProperties(param,newEntity);
            newEntity.setPacklist(packListStr);
            newEntity.setId(detEntityId);
            estLogisticsEstimateFeeDetService.updateById(newEntity);
            String packList = newEntity.getPacklist();
            if (ObjectUtil.isEmpty(packList)) {
                log.error("请先导入packList");
                return ResponseData.error("请先导入packList");
            }
            //测算人
            String operator = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser()) ? LoginContext.me().getLoginUser().getName() : "未知测算人";
            List<BoxDimensionParam> boxes = JSON.parseArray(packList, BoxDimensionParam.class);
            //箱号,箱数及商品数量
            String boxNum = boxes.stream().map(BoxDimensionParam::getBoxNum).collect(Collectors.joining(","));
            newEntity.setBoxNum(boxNum);
            newEntity.setBoxCount(boxes.get(0).getBoxCount());
            newEntity.setQty(boxes.get(0).getQty());
            //箱数
            //重量KG
            Double weightKg = boxes.stream().map(BoxDimensionParam::getWeight).reduce(0.0, Double::sum);
            //体积KG
            Double volRatio = "快递".equals(newEntity.getTransportType()) ? 5000.0 : 6000.0;
            Double volweightKg = boxes.stream().map(i -> i.getHigh() * i.getLength() * i.getWidth() / volRatio).reduce(0.0, Double::sum);

            newEntity.setConfirmFeeType("重量");
            newEntity.setWeightKg(weightKg);
            newEntity.setVolweightKg(volweightKg);
            newEntity.setConfirmCountFee(weightKg >= volweightKg ? weightKg : volweightKg);

            if (ObjectUtil.isEmpty(newEntity.getConfirmCountFee()) || newEntity.getConfirmCountFee() == 0) {
                log.error("无计费量KG");
                return ResponseData.error("无计费量KG");
            }
            param.setConfirmCountFee(newEntity.getConfirmCountFee());

            //提供物流商则找到对应计费的那条数据
            //也可选择人工录入单价
            if (ObjectUtil.isEmpty(param.getLpname())) {
                return ResponseData.error("单条测算异常:物流商名称为空");
            }
            if (ObjectUtil.isEmpty(param.getSite())) {
                return ResponseData.error("单条测算异常:物站点为空");
            }
            if (ObjectUtil.isEmpty(param.getLspNum())) {
                return ResponseData.error("单条测算异常:物分区号为空");
            }

            if (ObjectUtil.isEmpty(param.getFreightCompany())) {
                return ResponseData.error("单条测算异常:货运方式1为空");
            }
            if (ObjectUtil.isEmpty(param.getTransportType())) {
                return ResponseData.error("单条测算异常:运输方式为空");
            }

            if (ObjectUtil.isEmpty(param.getHasTax())) {
                return ResponseData.error("单条测算异常:是否含税为空");
            }
            param.setHasTax("是".equals(param.getHasTax()) ? "1" : "0");

            List<LsLogisticsPriceResult> lpPrice = estLogisticsEstimateFeeDetService.getLpPrice(param);
            if (ObjectUtil.isEmpty(lpPrice)) {
                return ResponseData.error(StrUtil.format("单条测算异常:维度[{}]找不物流单价费用",
                        param.getLpname() + "_" +
                                param.getSite() + "_" +
                                param.getLspNum() + "_" +
                                param.getFreightCompany() + "_" +
                                (ObjectUtil.isNotEmpty(param.getTransportType())?param.getTransportType() + "_" :"") +
                                (ObjectUtil.isNotEmpty(param.getOrderType())?param.getOrderType() + "_" :"") +
                                param.getHasTax()+param.getConfirmCountFee()));
            }
//        if (ObjectUtil.isEmpty(lpPrice.size()>1)) {
//            return ResponseData.error("单条测算异常:多条物流单价费用");
//        }
            LsLogisticsPriceResult lsLogisticsPriceResult = lpPrice.get(0);
            //物流费CNY
            newEntity.setLogisticsFee(lsLogisticsPriceResult.getBusLogpDetUnitPrice() * newEntity.getConfirmCountFee());
            //燃油附加费CNY
            newEntity.setOilfee(lsLogisticsPriceResult.getBusLogpDetFuelFee() * newEntity.getLogisticsFee());
            //旺季附加费CNY
            newEntity.setBusyseasonFee(lsLogisticsPriceResult.getBusLogpDetBusySeasonAddFee() * newEntity.getLogisticsFee());
            //附加费及杂费CNY
            newEntity.setOthersFee(lsLogisticsPriceResult.getBusLogpDetAddAndSundryFee() * newEntity.getLogisticsFee());
            //报关费CNY
            newEntity.setApplyCustomsfee(lsLogisticsPriceResult.getBusLogpDetCustDlearanceFee());
            //清关费CNY
            newEntity.setClearCustomsfee(lsLogisticsPriceResult.getBusLogpDetCustClearanceFee());

            //FBA配置费CNY 货币-> CNY
            if (ObjectUtil.isNotEmpty(newEntity.getFbaconfigFee()) && ObjectUtil.isEmpty(newEntity.getFbaconfigCurrency())) {
                log.error("FBA配置费币种为空");
                return ResponseData.error("FBA配置费币种为空");
            }
            if (ObjectUtil.isEmpty(newEntity.getFbaconfigFee())) {
                newEntity.setFbaconfigFee(0.0);
                newEntity.setFbaconfigCurrency("USA");
            }
            Date date = DateUtil.date();

            // 查询固定汇率
            FixedExchangeRateParam rateParam = new FixedExchangeRateParam();
//        rateParam.setEffectDate(date);
            rateParam.setOriginalCurrency(newEntity.getFbaconfigCurrency().toUpperCase());
            List<FixedExchangeRate> fixedExchangeRateList = fixedExchangeRateConsumer.getFixedExchangeRateList(rateParam);
            List<FixedExchangeRate> fixedExchangeRateListSorted = fixedExchangeRateList.stream()
                    .sorted(Comparator.comparing(FixedExchangeRate::getEffectDate).reversed())
                    .collect(Collectors.toList());
            if (ObjectUtil.isNotEmpty(fixedExchangeRateListSorted) && ObjectUtil.isNotEmpty(fixedExchangeRateList.get(0).getDirectRate() != null)) {
                newEntity.setFbaconfigFee(newEntity.getFbaconfigFee() * fixedExchangeRateList.get(0).getDirectRate().doubleValue());
            }

//        newEntity.setFbaconfigFee(newEntity.getFbaconfigFee()*1);
            //费用合计CNY
            newEntity.setTotalFee(newEntity.getLogisticsFee()
                    + newEntity.getOilfee()
                    + newEntity.getBusyseasonFee()
                    + newEntity.getOthersFee()
                    + newEntity.getApplyCustomsfee()
                    + newEntity.getClearCustomsfee());

            //设置邮编
            Map<String, String> postCodeMap = estLogisticsEstimateFeeDetService.getPostCodeMap();
            String shipTo = newEntity.getShipTo();
            if (ObjectUtil.isNotEmpty(shipTo)) {
                String postCode = postCodeMap.get(shipTo);
                newEntity.setPostcode(postCode);
            }

            newEntity.setUpdatedBy(operator);
            newEntity.setUpdatedTime(date);
            newEntity.setEstDate(date);
            //明细测算人
            newEntity.setPerName(operator);
            this.saveOrUpdate(newEntity);
            return ResponseData.success();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("单条测算异常"+e.getMessage());
            return ResponseData.error("单条测算异常"+e.getMessage());
        }
    }



    @DataSource(name = "logistics")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseData entrySingleCostEst(EstLogisticsEstimateFeeDetParam param) {
        try {
            //录入单价测算:isEntryUnitPrice = 1
            log.info(StrUtil.format("开始录入单价测算:是否录入[{}],物流单价[{}]",param.getIsEntryUnitPrice(),param.getEntryUnitPrice()));
            if (ObjectUtil.isEmpty(param.getEntryUnitPrice()) || param.getEntryUnitPrice() <= 0.0) {
                return ResponseData.error("单价费用需非空且>0");
            }
            String shipmentId = param.getShipmentId();
            List<EstLogisticsEstimateFeeDet> oneRow = new LambdaQueryChainWrapper<>(estLogisticsEstimateFeeDetMapper)
                    .eq(EstLogisticsEstimateFeeDet::getEstId, param.getEstId())
                    .eq(EstLogisticsEstimateFeeDet::getShipmentId, shipmentId)
                    .list();
            if (ObjectUtil.isEmpty(oneRow)) {
                return ResponseData.error(StrUtil.format("无shipmentId{}", shipmentId));
            }
            EstLogisticsEstimateFeeDet dbEntity = oneRow.get(0);
            EstLogisticsEstimateFeeDet newEntity = new EstLogisticsEstimateFeeDet();
            String packListStr = dbEntity.getPacklist();
            String detEntityId = dbEntity.getId();
            BeanUtil.copyProperties(param,newEntity);
            newEntity.setPacklist(packListStr);
            newEntity.setId(detEntityId);
            estLogisticsEstimateFeeDetService.updateById(newEntity);
            String packList = newEntity.getPacklist();
            if (ObjectUtil.isEmpty(packList)) {
                log.error("请先导入packList");
                return ResponseData.error("请先导入packList");
            }
            //测算人
            String operator = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser()) ? LoginContext.me().getLoginUser().getName() : "未知测算人";
            List<BoxDimensionParam> boxes = JSON.parseArray(packList, BoxDimensionParam.class);
            //箱号,箱数和商品数量
            String boxNum = boxes.stream().map(BoxDimensionParam::getBoxNum).collect(Collectors.joining(","));
            newEntity.setBoxNum(boxNum);
            newEntity.setBoxCount(boxes.get(0).getBoxCount());
            newEntity.setQty(boxes.get(0).getQty());
            //重量KG
            Double weightKg = boxes.stream().map(BoxDimensionParam::getWeight).reduce(0.0, Double::sum);
            //体积KG
            Double volRatio = "快递".equals(newEntity.getTransportType()) ? 5000.0 : 6000.0;
            Double volweightKg = boxes.stream().map(i -> i.getHigh() * i.getLength() * i.getWidth() / volRatio).reduce(0.0, Double::sum);

            newEntity.setConfirmFeeType("重量");
            newEntity.setWeightKg(weightKg);
            newEntity.setVolweightKg(volweightKg);
            newEntity.setConfirmCountFee(weightKg >= volweightKg ? weightKg : volweightKg);

            if (ObjectUtil.isEmpty(newEntity.getConfirmCountFee()) || newEntity.getConfirmCountFee() == 0) {
                log.error("无计费量KG");
                return ResponseData.error("无计费量KG");
            }
            param.setConfirmCountFee(newEntity.getConfirmCountFee());

            //FBA配置费CNY 货币-> CNY
            if (ObjectUtil.isNotEmpty(newEntity.getFbaconfigFee()) && ObjectUtil.isEmpty(newEntity.getFbaconfigCurrency())) {
                log.error("FBA配置费币种为空");
                return ResponseData.error("FBA配置费币种为空");
            }
            if (ObjectUtil.isEmpty(newEntity.getFbaconfigFee())) {
                newEntity.setFbaconfigFee(0.0);
                newEntity.setFbaconfigCurrency("USA");
            }
            Date date = DateUtil.date();

            // 查询固定汇率
            FixedExchangeRateParam rateParam = new FixedExchangeRateParam();
            rateParam.setOriginalCurrency(newEntity.getFbaconfigCurrency().toUpperCase());
            List<FixedExchangeRate> fixedExchangeRateList = fixedExchangeRateConsumer.getFixedExchangeRateList(rateParam);
            List<FixedExchangeRate> fixedExchangeRateListSorted = fixedExchangeRateList.stream()
                    .sorted(Comparator.comparing(FixedExchangeRate::getEffectDate).reversed())
                    .collect(Collectors.toList());
            if (ObjectUtil.isNotEmpty(fixedExchangeRateListSorted) && ObjectUtil.isNotEmpty(fixedExchangeRateList.get(0).getDirectRate() != null)) {
                newEntity.setFbaconfigFee(newEntity.getFbaconfigFee() * fixedExchangeRateList.get(0).getDirectRate().doubleValue());
            }

            //录入单价:物流费=费用合计
            newEntity.setEntryUnitPrice(param.getEntryUnitPrice());
            newEntity.setLogisticsFee(param.getEntryUnitPrice() * newEntity.getConfirmCountFee());
            newEntity.setTotalFee(newEntity.getLogisticsFee());

            //设置邮编
            Map<String, String> postCodeMap = estLogisticsEstimateFeeDetService.getPostCodeMap();
            String shipTo = newEntity.getShipTo();
            if (ObjectUtil.isNotEmpty(shipTo)) {
                String postCode = postCodeMap.get(shipTo);
                newEntity.setPostcode(postCode);
            }

            newEntity.setUpdatedBy(operator);
            newEntity.setUpdatedTime(date);
            newEntity.setEstDate(date);
            //明细测算人
            newEntity.setPerName(operator);
            this.saveOrUpdate(newEntity);
            return ResponseData.success();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("人工录入单条费用测算异常"+e.getMessage());
            return ResponseData.error("人工录入单条费用测算异常"+e.getMessage());
        }
    }
    @DataSource(name = "logistics")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseData allCostEst(List<EstLogisticsEstimateFeeDetParam> params) {
        try {
            if (ObjectUtil.isEmpty(params)) {
                return ResponseData.error("明细数据为空");
            }
            if (params.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getEstId()))) {
                return ResponseData.error("明细存在费用测算编号为空");
            }
            if (params.stream().anyMatch(i -> ObjectUtil.isEmpty(i.getShipmentId()))) {
                return ResponseData.error("明细存在ShipmentId为空");
            }
            List<String> shipmentIdList = params.stream()
                    .map(EstLogisticsEstimateFeeDetParam::getShipmentId)
                    .collect(Collectors.toList());

            List<String> distinctShipmentIds = shipmentIdList.stream()
                    .distinct()
                    .collect(Collectors.toList());
            if (shipmentIdList.size() != distinctShipmentIds.size()) {
                return ResponseData.error("重复ShipmentId提交");
            }
            String estId = params.get(0).getEstId();

            //删除非当前测算ID明细
            new LambdaUpdateChainWrapper<>(estLogisticsEstimateFeeDetMapper)
                    .isNull(EstLogisticsEstimateFeeDet::getEstDate)
                    .ne(EstLogisticsEstimateFeeDet::getEstId, estId)
                    .remove();

            DateTime date = DateUtil.date();
            String operator = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser()) ? LoginContext.me().getLoginUser().getName() : "";
            String operatorCode = ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser()) ? LoginContext.me().getLoginUser().getAccountId().toString() : "";

            //总数据
            List<EstLogisticsEstimateFee> mainRows = new LambdaQueryChainWrapper<>(estLogisticsEstimateFeeMapper)
                    .eq(EstLogisticsEstimateFee::getEstId, estId)
                    .list();
            EstLogisticsEstimateFee mainRow = new EstLogisticsEstimateFee();
            EstLogisticsEstimateFeeDetParam param0 = params.get(0);
            if (ObjectUtil.isEmpty(mainRows)) {
                BeanUtil.copyProperties(param0, mainRow,CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
                mainRow.setPerCode(operatorCode);
                mainRow.setPerName(operator);
                mainRow.setCreatedTime(date);
                mainRow.setCreatedBy(operator);
                estLogisticsEstimateFeeService.save(mainRow);
            } else {
                mainRow = mainRows.get(0);
                BeanUtil.copyProperties(param0, mainRow,CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            }
            //遍历明细单条测算1录入价格测算和录入物流商测算
            for (EstLogisticsEstimateFeeDetParam param : params) {
                ResponseData responseData;
                if (param.getIsEntryUnitPrice()==1) {
                     responseData = this.entrySingleCostEst(param);
                } else{
                     responseData = this.singleCostEst(param);
                }
                if (responseData.getCode() == 500) {
                    new LambdaUpdateChainWrapper<>(estLogisticsEstimateFeeMapper)
                            .eq(EstLogisticsEstimateFee::getEstId, estId)
                            .remove();
                    return ResponseData.error(StrUtil.format("[{}]:{}", param.getShipmentId(), responseData.getMessage()));
                }
            }

            //更新主数据总计费量,总费用,测算日期
            List<EstLogisticsEstimateFeeDet> detailRows = new LambdaQueryChainWrapper<>(estLogisticsEstimateFeeDetMapper)
                    .eq(EstLogisticsEstimateFeeDet::getEstId, estId)
                    .list();
            if (ObjectUtil.isEmpty(detailRows)) {
                return ResponseData.error("明细为空");
            }
            Double fbaconfigFee = ObjectUtil.isNotEmpty(detailRows.get(0).getFbaconfigFee())?detailRows.get(0).getFbaconfigFee():0.0;
            Double totalCount = detailRows.stream().filter(i -> ObjectUtil.isNotEmpty(i.getConfirmCountFee())).map(EstLogisticsEstimateFeeDet::getConfirmCountFee).reduce(0.0, Double::sum);
            Double totalFee = detailRows.stream().filter(i -> ObjectUtil.isNotEmpty(i.getTotalFee())).map(EstLogisticsEstimateFeeDet::getTotalFee).reduce(0.0, Double::sum);
            mainRow.setTotalQty(totalCount);
            mainRow.setTotalFee(totalFee + fbaconfigFee);
            mainRow.setUpdatedTime(date);
            mainRow.setEstDate(date);
            mainRow.setUpdatedBy(ObjectUtil.isNotEmpty(LoginContext.me().getLoginUser()) ? LoginContext.me().getLoginUser().getName() : "");
            estLogisticsEstimateFeeService.saveOrUpdate(mainRow);
            return ResponseData.success();
        } catch (Exception e){
            log.error("测算异常"+e.getMessage());
            return ResponseData.error(StrUtil.format("测算异常"+e.getMessage()));
        }

    }

    @DataSource(name = "logistics")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData uploadPackListJson(MultipartFile file) throws IOException, InvalidFormatException {
        ExcelReader reader = new ExcelReader();
        List<BoxDimensionParam> packList = reader.readExcelCsv(file);
        if (ObjectUtil.isEmpty(packList)) {
            log.error("解析失败");
            return ResponseData.error("解析失败");
        }
        return ResponseData.success(JSON.toJSONString(packList));
    }

    @DataSource(name = "logistics")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<EstLogisticsEstimateFeeDetExportResult>  exportList(EstLogisticsEstimateFeeDetParam param)   {
        return  this.baseMapper.exportList(param);
    }




    @DataSource(name = "EBMS")
    @Override
    public List<String> shipToList() {
        return estLogisticsEstimateFeeDetMapper.shipToList();

    }

    @DataSource(name = "EBMS")
    @Override
    public List<String> currencyList() {
        List<FixedExchangeRate> fixedExchangeRates = fixedExchangeRateConsumer.originalCurrencyList();
        return fixedExchangeRates.stream().map(FixedExchangeRate::getOriginalCurrency).distinct().collect(Collectors.toList());

    }


//    @DataSource(name = "EBMS")
//    @Override
//    public List<String> logisticsProviderList() {
//        List<LsLogisticsProvider> LsLogisticsProviders = logisticsProviderService.logisticsProviderSelect();
//        return LsLogisticsProviders.stream().map(LsLogisticsProvider::getLpName).distinct().sorted().collect(Collectors.toList());
//
//    }
//
//    @DataSource(name = "EBMS")
//    @Override
//    public List<String> siteList() {
//        return baseSelectConsumer.getSite();
//
//    }
    @DataSource(name = "EBMS")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Map<String, String> getPostCodeMap() {
        List<ShipToPostCodeResult> postCode = estLogisticsEstimateFeeDetMapper.getPostCode();
        Map<String, String> collect = postCode.stream().collect(Collectors.toMap(ShipToPostCodeResult::getShipTo, ShipToPostCodeResult::getPostcode));
        return collect;
    }


    @DataSource(name = "EBMS")
    @Override
    public List<LogisticsInfoResult> getLogisticsInfo() {
        return estLogisticsEstimateFeeDetMapper.getLogisticsInfo();

    }


    @DataSource(name = "EBMS")
    @Override
    public List<String> logisticsProviderList() {
        List<LogisticsInfoResult> logisticsInfo = estLogisticsEstimateFeeDetMapper.getLogisticsInfo();
        return logisticsInfo.stream().map(LogisticsInfoResult::getLpName).distinct().sorted().collect(Collectors.toList());
    }


    @DataSource(name = "EBMS")
    @Override
    public List<String> siteList() {
        List<LogisticsInfoResult> logisticsInfo = estLogisticsEstimateFeeDetMapper.getLogisticsInfo();
        return logisticsInfo.stream().map(LogisticsInfoResult::getSite).distinct().sorted().collect(Collectors.toList());
    }

    @DataSource(name = "EBMS")
    @Override
    public List<String> freightCompanyList() {
        List<LogisticsInfoResult> logisticsInfo = estLogisticsEstimateFeeDetMapper.getLogisticsInfo();
        return logisticsInfo.stream().map(LogisticsInfoResult::getFreightCompany).distinct().sorted().collect(Collectors.toList());
    }

    @DataSource(name = "EBMS")
    @Override
    public List<String> transportTypeList() {
        List<LogisticsInfoResult> logisticsInfo = estLogisticsEstimateFeeDetMapper.getLogisticsInfo();
        return logisticsInfo.stream().map(LogisticsInfoResult::getTransportType).distinct().sorted().collect(Collectors.toList());
    }


    @DataSource(name = "EBMS")
    @Override
    public List<String> logisticsChannelList() {
        List<LogisticsInfoResult> logisticsInfo = estLogisticsEstimateFeeDetMapper.getLogisticsInfo();
        return logisticsInfo.stream().map(LogisticsInfoResult::getLogisticsChannel).distinct().sorted().collect(Collectors.toList());
    }

    @DataSource(name = "EBMS")
    @Override
    public List<String> orderTypeList() {
        List<LogisticsInfoResult> logisticsInfo = estLogisticsEstimateFeeDetMapper.getLogisticsInfo();
        return logisticsInfo.stream().map(LogisticsInfoResult::getOrderType).filter(ObjectUtil::isNotEmpty).distinct().sorted().collect(Collectors.toList());
    }

    @DataSource(name = "EBMS")
    @Override
    public List<String> lspNumList(EstLogisticsEstimateFeeDetParam param) {
        List<LogisticsInfoResult> logisticsInfo = estLogisticsEstimateFeeDetMapper.getLogisticsInfo();
        List<String> lspNumList;
        if (ObjectUtil.isNotEmpty(param.getLpname()) && ObjectUtil.isNotEmpty(param.getSite())) {
             lspNumList = logisticsInfo.stream()
                    .filter(i -> i.getLpName().equals(param.getLpname()))
                    .filter(i -> i.getSite().equals(param.getSite()))
                    .map(LogisticsInfoResult::getLspNum).distinct().sorted().collect(Collectors.toList());
        } else {
             lspNumList = logisticsInfo.stream()
                    .map(LogisticsInfoResult::getLspNum).distinct().sorted().collect(Collectors.toList());
        }
        return lspNumList;

    }

    @Override
    public FixedExchangeRate getFixedRate() {
        FixedExchangeRateParam rateParam = new FixedExchangeRateParam();
        rateParam.setEffectDate(DateUtil.date());
        rateParam.setOriginalCurrency("USD");
        return fixedExchangeRateConsumer.getRateByDateCurrency(rateParam);

    }



    @DataSource(name = "logistics")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData delByShipmentId( String estId,String shipmentId) {
        if (ObjectUtil.isEmpty(estId)) {
            return ResponseData.error("estId不能为空");
        }
        if (ObjectUtil.isEmpty(shipmentId)) {
            return ResponseData.error("shipmentId不能为空");
        }
        new LambdaUpdateChainWrapper<>(estLogisticsEstimateFeeDetMapper)
                .eq(EstLogisticsEstimateFeeDet::getShipmentId, shipmentId)
                .eq(EstLogisticsEstimateFeeDet::getEstId, estId)
                .remove();
        return ResponseData.success();

    }


    @DataSource(name = "logistics")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData updateByShipmentId(EstLogisticsEstimateFeeDetParam param) {
        String shipmentId = param.getShipmentId();
        if (ObjectUtil.isEmpty(shipmentId)) {
            return ResponseData.error("shipmentId不能为空");
        }
        if (ObjectUtil.isEmpty(param.getEstId())) {
            return ResponseData.error("estId不能为空");
        }
//        if (ObjectUtil.isNotEmpty(param.getNewShipmentId())) {
//            shipmentId = param.getNewShipmentId();
//        }

        if (!new LambdaUpdateChainWrapper<>(estLogisticsEstimateFeeDetMapper)
                .eq(ObjectUtil.isNotEmpty(param.getEstId()),EstLogisticsEstimateFeeDet::getEstId, param.getEstId())
                .eq(EstLogisticsEstimateFeeDet::getShipmentId, shipmentId)
                .set(ObjectUtil.isNotEmpty(param.getNewShipmentId()),EstLogisticsEstimateFeeDet::getShipmentId,param.getNewShipmentId())
                .set(ObjectUtil.isNotEmpty(param.getShipTo()),EstLogisticsEstimateFeeDet::getShipTo,param.getShipTo())
                .set(ObjectUtil.isNotEmpty(param.getLspNum()),EstLogisticsEstimateFeeDet::getLspNum,param.getLspNum())
                .set(EstLogisticsEstimateFeeDet::getUpdatedTime,new Date())
                .update()) {
            EstLogisticsEstimateFeeDet detail = new EstLogisticsEstimateFeeDet();
            BeanUtil.copyProperties(param, detail);
            detail.setCreatedTime(new Date());
            this.save(detail);
        }
        return ResponseData.success();

    }





}