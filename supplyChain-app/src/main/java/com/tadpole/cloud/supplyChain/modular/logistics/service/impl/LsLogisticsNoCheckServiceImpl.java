package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsNoCheckExport0Result;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsNoCheckExport1Result;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LsLogisticsNoCheckMapper;
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
import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 物流单对账 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-11-28
 */
@Slf4j
@Service
public class LsLogisticsNoCheckServiceImpl extends ServiceImpl<LsLogisticsNoCheckMapper, LsLogisticsNoCheck> implements ILsLogisticsNoCheckService {

    @Resource
    private LsLogisticsNoCheckMapper mapper;
    @Autowired
    private ILsLogisticsNoCheckService checkService;
    @Autowired
    private ILsLogisticsNoCheckDetailService checkDetailService;
    @Autowired
    private ILsLogisticsFeePaymentService paymentService;
    @Autowired
    private ILsLogisticsFeePaymentDetailService paymentDetailService;
    @Autowired
    private ILsLogisticsNoRecordService logisticsNoRecordService;
    @Autowired
    private ILsBtbLogisticsNoService logisticsNoService;

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(LsLogisticsNoCheckParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPageTotal(LsLogisticsNoCheckParam param) {
        return ResponseData.success(mapper.queryPageTotal(param));
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData importExcel(MultipartFile file) {
        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<LsLogisticsNoCheckDetailParam>();
            EasyExcel.read(buffer, LsLogisticsNoCheckDetailParam.class, listener).sheet().doRead();

            List<LsLogisticsNoCheckDetailParam> dataList = listener.getDataList();
            log.info("导入物流单对账开始[{}]", JSONObject.toJSON(dataList));
            long start = System.currentTimeMillis();
            if(CollectionUtil.isEmpty(dataList)){
                return ResponseData.error("导入数据为空，导入失败！");
            }
            Set<String> repeat = new HashSet<>();
            Set<String> shipmentNumSet = new HashSet<>();
            for (LsLogisticsNoCheckDetailParam param : dataList) {
                String shipmentNum = param.getShipmentNum();
                if(StringUtils.isBlank(shipmentNum)){
                    return ResponseData.error("导入数据发货批次号不能为空！");
                }
                if(CollectionUtil.isNotEmpty(shipmentNumSet) && shipmentNumSet.contains(shipmentNum)){
                    repeat.add(shipmentNum);
                } else {
                    shipmentNumSet.add(shipmentNum);
                }
            }
            if(CollectionUtil.isNotEmpty(repeat)){
                return ResponseData.error("导入数据发货批次号重复，" + JSONObject.toJSON(repeat) + "，导入失败！");
            }
            //异常数据
            List<LsLogisticsNoCheckDetailParam> errorList = new ArrayList<>();
            //正常物流单对账明细数据
            List<LsLogisticsNoCheckDetail> shipmentNumDetailList = new ArrayList<>();
            //正常物流单对账数据
            List<LsLogisticsNoCheck> shipmentNumList = new ArrayList<>();
            //正常物流费付款明细数据
            List<LsLogisticsFeePaymentDetail> paymentDetailList = new ArrayList<>();
            this.validExcel(dataList, errorList, shipmentNumDetailList, shipmentNumList, paymentDetailList);

            //异常数据输出异常Excel文件流
            if(CollectionUtil.isNotEmpty(errorList)){
                String fileName = this.dealImportErrorList(errorList);
                log.info("导入物流单对账结束，耗时------------------->" + (System.currentTimeMillis() - start) + "ms");
                return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, "存在导入失败数据！", fileName);
            } else {
                if(CollectionUtil.isNotEmpty(shipmentNumList)){
                    this.updateBatchById(shipmentNumList);
                }

                checkDetailService.updateBatchById(shipmentNumDetailList);

                //更新物流费付款信息
                if(CollectionUtil.isNotEmpty(paymentDetailList)){
                    paymentDetailService.updateBatchById(paymentDetailList);
                }
                log.info("导入物流单对账结束，耗时------------------->" + (System.currentTimeMillis() - start) + "ms");
                return ResponseData.success();
            }
        } catch (Exception e) {
            log.error("导入物流单对账处理异常，导入失败！", e);
            return ResponseData.error("导入物流单对账处理异常，导入失败！");
        } finally {
            if(buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入物流单对账关闭流异常", e);
                }
            }
        }
    }

    /**
     * 导入Excel校验
     * @param dataList 导入数据
     * @param errorList 导入校验异常数据
     * @param shipmentNumDetailList 导入校验正常物流单对账明细数据
     * @param shipmentNumList 导入校验正常物流单对账数据
     * @param paymentDetailList 正常物流费付款明细数据
     */
    private void validExcel(List<LsLogisticsNoCheckDetailParam> dataList, List<LsLogisticsNoCheckDetailParam> errorList, List<LsLogisticsNoCheckDetail> shipmentNumDetailList,
                            List<LsLogisticsNoCheck> shipmentNumList, List<LsLogisticsFeePaymentDetail> paymentDetailList){
        String name = LoginContext.me().getLoginUser().getName();
        Date nowDate = DateUtil.date();
        for (LsLogisticsNoCheckDetailParam shipmentNumParam : dataList) {
            shipmentNumParam.setUploadRemark("");
            StringBuffer errorMsg = new StringBuffer();
            if(StringUtils.isBlank(shipmentNumParam.getShipmentNum()) || StringUtils.isBlank(shipmentNumParam.getLogisticsCheckOrder())){
                shipmentNumParam.setUploadRemark("发货批次号和物流对账单号为必填项！");
                errorList.add(shipmentNumParam);
                continue;
            }
            if(shipmentNumParam.getVat() != null && BigDecimal.ZERO.compareTo(shipmentNumParam.getVat()) > 0){
                errorMsg.append("VAT原币金额不能为负数！");
            }
            LambdaQueryWrapper<LsLogisticsNoCheckDetail> checkDetailWrapper = new LambdaQueryWrapper();
            checkDetailWrapper.eq(LsLogisticsNoCheckDetail:: getShipmentNum, shipmentNumParam.getShipmentNum())
                    .eq(LsLogisticsNoCheckDetail:: getOrderNum, 1);
            LsLogisticsNoCheckDetail checkDetail = checkDetailService.getOne(checkDetailWrapper);
            if(checkDetail == null){
                shipmentNumParam.setUploadRemark("未查询到此发货批次号明细信息！");
                errorList.add(shipmentNumParam);
                continue;
            }

            //对账状态为对账中的则可以编辑物流对账单号，对账完成的则不可以编辑物流对账单号
            LambdaQueryWrapper<LsLogisticsNoCheck> checkQueryWrapper = new LambdaQueryWrapper();
            checkQueryWrapper.eq(LsLogisticsNoCheck :: getShipmentNum, checkDetail.getShipmentNum());
            LsLogisticsNoCheck check = this.getOne(checkQueryWrapper);
            if(check == null){
                errorMsg.append("未查询到物流单对账信息！");
            }
            if("对账完成".equals(check.getCheckStatus())){
                if(!checkDetail.getLogisticsCheckOrder().equals(shipmentNumParam.getLogisticsCheckOrder())){
                    errorMsg.append("物流对账单号与对账完成的物流对账单号不一致！");
                }
            }

            //校验物流对账单号
            ResponseData resp = validateCheckOrder(shipmentNumParam);
            if(ResponseData.DEFAULT_ERROR_CODE.equals(resp.getCode())){
                errorMsg.append(resp.getMessage());
            }

            LambdaQueryWrapper<LsLogisticsFeePaymentDetail> paymentDetailQw = new LambdaQueryWrapper();
            paymentDetailQw.eq(LsLogisticsFeePaymentDetail :: getShipmentNum, check.getShipmentNum())
                    .eq(LsLogisticsFeePaymentDetail :: getOrderNum, 1);
            LsLogisticsFeePaymentDetail paymentDetail = paymentDetailService.getOne(paymentDetailQw);
            if(paymentDetail != null){
                LsLogisticsFeePaymentDetail updatePaymentDetail = new LsLogisticsFeePaymentDetail();
                updatePaymentDetail.setId(paymentDetail.getId());
                updatePaymentDetail.setOilFeePercent(shipmentNumParam.getOilFeePercent());
                updatePaymentDetail.setTaxOrder(shipmentNumParam.getTaxOrder());
                updatePaymentDetail.setC88(shipmentNumParam.getC88());
                updatePaymentDetail.setC88Remark(shipmentNumParam.getC88Remark());
                updatePaymentDetail.setVat(shipmentNumParam.getVat());
                updatePaymentDetail.setTaxInvoiceOrder(shipmentNumParam.getTaxInvoiceOrder());
                updatePaymentDetail.setUpdateUser(name);
                updatePaymentDetail.setUpdateTime(nowDate);
                paymentDetailList.add(updatePaymentDetail);
            }

            String uploadRemark = errorMsg.toString();
            if(StringUtils.isNotBlank(uploadRemark)){
                shipmentNumParam.setUploadRemark(uploadRemark);
                errorList.add(shipmentNumParam);
            }else{
                BigDecimal checkDetailId = checkDetail.getId();
                BeanUtils.copyProperties(shipmentNumParam, checkDetail);
                checkDetail.setId(checkDetailId);
                checkDetail.setUpdateTime(nowDate);
                checkDetail.setUpdateUser(name);
                shipmentNumDetailList.add(checkDetail);

                if(StringUtils.isNotBlank(shipmentNumParam.getRemark())){
                    LsLogisticsNoCheck updateCheck = new LsLogisticsNoCheck();
                    updateCheck.setId(check.getId());
                    updateCheck.setRemark(shipmentNumParam.getRemark());
                    updateCheck.setUpdateTime(nowDate);
                    updateCheck.setUpdateUser(name);
                    shipmentNumList.add(updateCheck);
                }
            }
        }
    }

    /**
     * 校验错误文件流输出
     * @param errorList
     * @return
     */
    private String dealImportErrorList(List<LsLogisticsNoCheckDetailParam> errorList){
        String filePath = System.getProperty("user.dir") + "/upload/";
        String fileName =  DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + ".xlsx";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath + fileName,false);
            EasyExcel.write(out, LsLogisticsNoCheckDetailParam.class)
                    .sheet("导入异常数据").doWrite(errorList);
        } catch (FileNotFoundException e) {
            log.error("物流单对账导入异常数据导出异常", e);
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("物流单对账导入异常数据导出流关闭异常", e);
                }
            }
        }
        return fileName;
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData batchLock(LsLogisticsNoCheckLockParam param) {
        String name = LoginContext.me().getLoginUser().getName();
        if(CollectionUtil.isNotEmpty(param.getIdList())){
            LambdaUpdateWrapper<LsLogisticsNoCheck> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.in(LsLogisticsNoCheck :: getId, param.getIdList());
            if("锁定".equals(param.getLockStatus())){
                updateWrapper.eq(LsLogisticsNoCheck :: getLockStatus, "未锁定");
            }
            if("未锁定".equals(param.getLockStatus())){
                //已经生成物流费的不允许操作解锁
                LambdaQueryWrapper<LsLogisticsNoCheck> checkQw = new LambdaQueryWrapper<>();
                checkQw.in(LsLogisticsNoCheck :: getId, param.getIdList());
                List<LsLogisticsNoCheck> checkList = this.list(checkQw);
                if(CollectionUtil.isNotEmpty(checkList)){
                    Set<String> shipmentNumSet = checkList.stream().map(i -> i.getShipmentNum()).collect(Collectors.toSet());
                    LambdaQueryWrapper<LsLogisticsFeePaymentDetail> paymentDetailQw = new LambdaQueryWrapper<>();
                    paymentDetailQw.in(LsLogisticsFeePaymentDetail :: getShipmentNum, shipmentNumSet);
                    List<LsLogisticsFeePaymentDetail> paymentDetailList = paymentDetailService.list(paymentDetailQw);
                    if(CollectionUtil.isNotEmpty(paymentDetailList)){
                        return ResponseData.error("存在已经生成物流费付款数据，不允许操作解锁");
                    }
                }

                updateWrapper.eq(LsLogisticsNoCheck :: getLockStatus, "锁定")
                        .set(LsLogisticsNoCheck :: getCheckStatus, "对账中");
            }
            updateWrapper.set(LsLogisticsNoCheck :: getLockStatus, param.getLockStatus())
                    .set(LsLogisticsNoCheck :: getUpdateUser, name)
                    .set(LsLogisticsNoCheck :: getUpdateTime, DateUtil.date());
            this.update(updateWrapper);

            LambdaQueryWrapper<LsLogisticsNoCheck> qw = new LambdaQueryWrapper<>();
            qw.in(LsLogisticsNoCheck :: getId, param.getIdList())
                    .eq(LsLogisticsNoCheck :: getDataSource, "EBMS");
            List<LsLogisticsNoCheck> checkList = this.list(qw);
            if(CollectionUtil.isNotEmpty(checkList)){
                List<String> shipmentNumList = checkList.stream().map(i -> i.getShipmentNum()).collect(Collectors.toList());
                //同步EBMS锁定状态
                String lockStatus = param.getLockStatus();
                if("未锁定".equals(lockStatus)){
                    lockStatus = null;
                }
                checkService.batchLockEbms(shipmentNumList, lockStatus);
            }
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "EBMS")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void batchLockEbms(List<String> shipmentNumList, String lockStatus) {
        mapper.batchLockEbms(shipmentNumList, lockStatus);
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData batchCheck(LsLogisticsNoCheckLockParam param) {
        String name = LoginContext.me().getLoginUser().getName();
        if(CollectionUtil.isEmpty(param.getIdList())){
            return ResponseData.error("数据ID为空");
        }
        LambdaQueryWrapper<LsLogisticsNoCheck> qw = new LambdaQueryWrapper<>();
        qw.in(LsLogisticsNoCheck :: getId, param.getIdList())
                .eq(LsLogisticsNoCheck :: getCheckStatus, "未锁定");
        List<LsLogisticsNoCheck> checkList = this.list(qw);
        if(CollectionUtil.isNotEmpty(checkList)){
            return ResponseData.error("存在未锁定的数据，请先锁定数据再操作对账完成");
        }

        List<LsLogisticsNoCheck> allCheckList = this.listByIds(param.getIdList());
        if(CollectionUtil.isEmpty(allCheckList)){
            return ResponseData.error("未查询到对账数据");
        }
        Set<String> shipmentNumSet = allCheckList.stream().map(i -> i.getShipmentNum()).collect(Collectors.toSet());
        LambdaQueryWrapper<LsLogisticsNoCheckDetail> checkDetailQw = new LambdaQueryWrapper<>();
        checkDetailQw.in(LsLogisticsNoCheckDetail :: getShipmentNum, shipmentNumSet)
                .isNull(LsLogisticsNoCheckDetail :: getLogisticsCheckOrder);
        List<LsLogisticsNoCheckDetail> checkDetailList = checkDetailService.list(checkDetailQw);
        if(CollectionUtil.isNotEmpty(checkDetailList)){
            return ResponseData.error("物流对账单号为空，请先维护物流对账单号再操作对账完成");
        }
        LambdaUpdateWrapper<LsLogisticsNoCheck> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(LsLogisticsNoCheck :: getId, param.getIdList())
                .eq(LsLogisticsNoCheck :: getCheckStatus, "对账中")
                .set(LsLogisticsNoCheck :: getCheckStatus, "对账完成")
                .set(LsLogisticsNoCheck :: getLockStatus, "锁定")
                .set(LsLogisticsNoCheck :: getUpdateUser, name)
                .set(LsLogisticsNoCheck :: getUpdateTime, DateUtil.date());
        this.update(updateWrapper);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public List<LsLogisticsNoCheckExport0Result> export(LsLogisticsNoCheckParam param) {
        return mapper.export(param);
    }

    @Override
    @DataSource(name = "logistics")
    public List<LsLogisticsNoCheckExport1Result> exportDetail(LsLogisticsNoCheckParam param) {
        return mapper.exportDetail(param);
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData edit(LsLogisticsNoCheckDetailParam param) {
        log.info("编辑保存物流单对账入参[{}]", JSONObject.toJSON(param));
        if(param.getId() == null){
            return ResponseData.error("物流单对账ID为空");
        }
        //校验物流单对账发货批次号是否锁定，锁定则不允许编辑
        LsLogisticsNoCheck check = this.getById(param.getId());
        if(check == null){
            return ResponseData.error("未查询到物流单对账信息！");
        }

        LambdaQueryWrapper<LsLogisticsNoCheckDetail> checkDetailQw = new LambdaQueryWrapper();
        checkDetailQw.eq(LsLogisticsNoCheckDetail :: getShipmentNum, check.getShipmentNum())
                .eq(LsLogisticsNoCheckDetail :: getOrderNum, 1);
        LsLogisticsNoCheckDetail checkDetail = checkDetailService.getOne(checkDetailQw);
        if(checkDetail == null){
            return ResponseData.error("未查询到物流单对账明细信息！");
        }

        String name = LoginContext.me().getLoginUser().getName();
        Date nowDate = DateUtil.date();
        LambdaUpdateWrapper<LsLogisticsNoCheckDetail> updateCheckDetail = new LambdaUpdateWrapper();
        updateCheckDetail.eq(LsLogisticsNoCheckDetail :: getId, checkDetail.getId())
                .set(LsLogisticsNoCheckDetail :: getOilFeePercent, param.getOilFeePercent() == null ? "" : param.getOilFeePercent())
                .set(LsLogisticsNoCheckDetail :: getLogisticsCheckOrder, param.getLogisticsCheckOrder() == null ? "" : param.getLogisticsCheckOrder())
                .set(LsLogisticsNoCheckDetail :: getTaxOrder, param.getTaxOrder() == null ? "" : param.getTaxOrder())
                .set(LsLogisticsNoCheckDetail :: getC88, param.getC88() == null ? "" : param.getC88())
                .set(LsLogisticsNoCheckDetail :: getC88Remark, param.getC88Remark() == null ? "" : param.getC88Remark())
                .set(LsLogisticsNoCheckDetail :: getVat, param.getVat() == null ? "" : param.getVat())
                .set(LsLogisticsNoCheckDetail :: getTaxInvoiceOrder, param.getTaxInvoiceOrder() == null ? "" : param.getTaxInvoiceOrder())
                .set(LsLogisticsNoCheckDetail :: getUpdateTime, nowDate)
                .set(LsLogisticsNoCheckDetail :: getUpdateUser, name);
        checkDetailService.update(updateCheckDetail);

        LambdaUpdateWrapper<LsLogisticsNoCheck> updateCheck = new LambdaUpdateWrapper();
        updateCheck.eq(LsLogisticsNoCheck :: getId, check.getId())
                .set(LsLogisticsNoCheck :: getRemark, param.getRemark() == null ? "" : param.getRemark())
                .set(LsLogisticsNoCheck :: getUpdateTime, nowDate)
                .set(LsLogisticsNoCheck :: getUpdateUser, name);
        this.update(updateCheck);

        //更新物流费付款信息
        LambdaQueryWrapper<LsLogisticsFeePaymentDetail> paymentDetailQw = new LambdaQueryWrapper();
        paymentDetailQw.eq(LsLogisticsFeePaymentDetail :: getShipmentNum, check.getShipmentNum())
                .eq(LsLogisticsFeePaymentDetail :: getOrderNum, 1);
        LsLogisticsFeePaymentDetail paymentDetail = paymentDetailService.getOne(paymentDetailQw);
        if(paymentDetail != null){
            LambdaUpdateWrapper<LsLogisticsFeePaymentDetail> updatePaymentDetail = new LambdaUpdateWrapper();
            updatePaymentDetail.eq(LsLogisticsFeePaymentDetail :: getId, paymentDetail.getId())
                    .set(LsLogisticsFeePaymentDetail :: getOilFeePercent, param.getOilFeePercent() == null ? "" : param.getOilFeePercent())
                    .set(LsLogisticsFeePaymentDetail :: getLogisticsCheckOrder, param.getLogisticsCheckOrder() == null ? "" : param.getLogisticsCheckOrder())
                    .set(LsLogisticsFeePaymentDetail :: getTaxOrder, param.getTaxOrder() == null ? "" : param.getTaxOrder())
                    .set(LsLogisticsFeePaymentDetail :: getC88, param.getC88() == null ? "" : param.getC88())
                    .set(LsLogisticsFeePaymentDetail :: getC88Remark, param.getC88Remark() == null ? "" : param.getC88Remark())
                    .set(LsLogisticsFeePaymentDetail :: getVat, param.getVat() == null ? "" : param.getVat())
                    .set(LsLogisticsFeePaymentDetail :: getTaxInvoiceOrder, param.getTaxInvoiceOrder() == null ? "" : param.getTaxInvoiceOrder())
                    .set(LsLogisticsFeePaymentDetail :: getUpdateTime, nowDate)
                    .set(LsLogisticsFeePaymentDetail :: getUpdateUser, name);
            paymentDetailService.update(updatePaymentDetail);
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData generateLogisticsFee(List<LsLogisticsNoCheckDetailParam> param) {
        log.info("物流单对账生成物流费入参[{}]", JSONObject.toJSON(param));
        if(CollectionUtil.isNotEmpty(param)){
            String name = LoginContext.me().getLoginUser().getName();
            Date nowDate = DateUtil.date();
            //物流费对账数据支付次数
            List<LsLogisticsNoCheck> updateCheckList = new ArrayList<>();
            //入库物流单对账非首次生成物流费
            List<LsLogisticsNoCheckDetail> insertCheckDetailList = new ArrayList<>();
            //入库物流费付款明细
            List<LsLogisticsFeePaymentDetail> paymentDetailList = new ArrayList();

            String lpCode = null;
            String lpName = null;
            String lpSimpleName = null;
            String currency = null;
            for (LsLogisticsNoCheckDetailParam detailParam : param) {
                //前端传的是主数据的ID
                LsLogisticsNoCheck check = this.getById(detailParam.getId());
                if(check == null){
                    return ResponseData.error("未查询到物流单对账数据");
                }
                if(StringUtils.isNotBlank(lpCode) && !lpCode.equals(check.getLpCode())){
                    return ResponseData.error("物流商名称一致方可生成物流费");
                }
                if((detailParam.getChangeTax() != null || detailParam.getTaxFee() != null) && StringUtils.isBlank(detailParam.getTaxCurrency())){
                    return ResponseData.error("税费币制为空，请选择税费币制");
                }
                if(
                    (
                        detailParam.getLogisticsFee() != null
                        || detailParam.getDtp() != null
                        || detailParam.getCustomsFee() != null
                        || detailParam.getClearCustomsFee() != null
                        || detailParam.getBusySeasonFee() != null
                        || detailParam.getOilFee() != null
                        || detailParam.getOthersFee() != null
                        || detailParam.getSundryFee() != null
                    ) && StringUtils.isBlank(detailParam.getLogisticsCurrency())){
                    return ResponseData.error("物流费币制为空，请选择物流费币制");
                }
                if(StringUtils.isNotBlank(detailParam.getTaxCurrency())
                        && StringUtils.isNotBlank(detailParam.getLogisticsCurrency())
                        && !detailParam.getTaxCurrency().equals(detailParam.getLogisticsCurrency())){
                    return ResponseData.error("物流费币制和税费币制一致方可生成物流费");
                }
                lpCode = check.getLpCode();
                lpName = check.getLpName();
                lpSimpleName = check.getLpSimpleName();
                //0为首次生成物流费，非0为非首次生成物流费
                if(check.getPaymentCount() != 0 && StringUtils.isBlank(detailParam.getShipmentNum())){
                    return ResponseData.error("非首次生成物流费需要录入物流费信息");
                }
                LsLogisticsNoCheck updateCheck = new LsLogisticsNoCheck();
                updateCheck.setId(check.getId());
                updateCheck.setPaymentCount(check.getPaymentCount() + 1);
                updateCheck.setUpdateTime(nowDate);
                updateCheck.setUpdateUser(name);
                updateCheckList.add(updateCheck);

                LambdaQueryWrapper<LsLogisticsNoCheckDetail> checkDetailQw = new LambdaQueryWrapper<>();
                checkDetailQw.eq(LsLogisticsNoCheckDetail :: getShipmentNum, check.getShipmentNum())
                        .orderByDesc(LsLogisticsNoCheckDetail :: getOrderNum);
                List<LsLogisticsNoCheckDetail> checkDetailList = checkDetailService.list(checkDetailQw);
                if(CollectionUtil.isEmpty(checkDetailList)){
                    return ResponseData.error("未查询到物流单对账明细数据");
                }
                LsLogisticsFeePaymentDetail paymentDetail = new LsLogisticsFeePaymentDetail();
                if(check.getPaymentCount() == 0){
                    //首次生成物流费
                    LsLogisticsNoCheckDetail checkDetail = checkDetailList.get(0);
                    BeanUtils.copyProperties(checkDetail, paymentDetail);
                    paymentDetail.setId(null);
                    paymentDetail.setCreateUser(name);

                    //物流费币制
                    if(StringUtils.isNotBlank(currency) && !currency.equals(checkDetail.getLogisticsCurrency())){
                        return ResponseData.error("所选数据币制一致方可生成物流费");
                    }
                    currency = checkDetail.getLogisticsCurrency();
                } else {
                    //非首次生成物流费
                    //物流费币制
                    String notFirstCurrency = detailParam.getLogisticsCurrency() == null ? detailParam.getTaxCurrency() : detailParam.getLogisticsCurrency();
                    if(StringUtils.isNotBlank(currency) && !currency.equals(notFirstCurrency)){
                        return ResponseData.error("所选数据币制一致方可生成物流费");
                    }
                    currency = notFirstCurrency;
                    LsLogisticsNoCheckDetail checkDetail = new LsLogisticsNoCheckDetail();
                    BeanUtils.copyProperties(detailParam, checkDetail);
                    checkDetail.setId(null);
                    checkDetail.setOrderNum(checkDetailList.get(0).getOrderNum() + 1);
                    checkDetail.setCreateUser(name);
                    //计算合计
                    //合计：物流费+DTP+报关费+清关费+旺季附加费+燃油附加费+杂费+产品附加费+流转税+DUTY/201
                    BigDecimal logisticsFee = ObjectUtil.isNull(checkDetail.getLogisticsFee()) ? BigDecimal.ZERO : checkDetail.getLogisticsFee();
                    BigDecimal dtp = ObjectUtil.isNull(checkDetail.getDtp()) ? BigDecimal.ZERO : checkDetail.getDtp();
                    BigDecimal customsFee = ObjectUtil.isNull(checkDetail.getCustomsFee()) ? BigDecimal.ZERO : checkDetail.getCustomsFee();
                    BigDecimal clearCustomsFee = ObjectUtil.isNull(checkDetail.getClearCustomsFee()) ? BigDecimal.ZERO : checkDetail.getClearCustomsFee();
                    BigDecimal busySeasonFee = ObjectUtil.isNull(checkDetail.getBusySeasonFee()) ? BigDecimal.ZERO : checkDetail.getBusySeasonFee();
                    BigDecimal oilFee = ObjectUtil.isNull(checkDetail.getOilFee()) ? BigDecimal.ZERO : checkDetail.getOilFee();
                    BigDecimal sundryFee = ObjectUtil.isNull(checkDetail.getSundryFee()) ? BigDecimal.ZERO : checkDetail.getSundryFee();
                    BigDecimal othersFee = ObjectUtil.isNull(checkDetail.getOthersFee()) ? BigDecimal.ZERO : checkDetail.getOthersFee();
                    BigDecimal taxFee = ObjectUtil.isNull(checkDetail.getTaxFee()) ? BigDecimal.ZERO : checkDetail.getTaxFee();
                    BigDecimal changeTax = ObjectUtil.isNull(checkDetail.getChangeTax()) ? BigDecimal.ZERO : checkDetail.getChangeTax();
                    //费用必填一项
                    if(checkDetail.getLogisticsFee() == null && checkDetail.getDtp() == null && checkDetail.getCustomsFee() == null && checkDetail.getClearCustomsFee() == null
                            && checkDetail.getBusySeasonFee() == null && checkDetail.getOilFee() == null && checkDetail.getSundryFee() == null && checkDetail.getOthersFee() == null
                            && checkDetail.getTaxFee() == null && checkDetail.getChangeTax() == null){
                        return ResponseData.error("至少录入一项以上费用");
                    }
                    BigDecimal checkTotalFee = logisticsFee.add(customsFee).add(clearCustomsFee).add(busySeasonFee).add(oilFee).add(othersFee).add(taxFee).add(dtp).add(sundryFee).add(changeTax);
                    checkDetail.setTotalFee(checkTotalFee);
                    insertCheckDetailList.add(checkDetail);

                    BeanUtils.copyProperties(checkDetail, paymentDetail);
                    paymentDetail.setId(null);
                    paymentDetail.setCreateUser(name);
                }
                paymentDetail.setLogisticsNo(check.getLogisticsNo());
                paymentDetailList.add(paymentDetail);
            }

            if(CollectionUtil.isNotEmpty(insertCheckDetailList)){
                //入库物流费对账明细非首次生成物流费
                for (LsLogisticsNoCheckDetail insertCheckDetail : insertCheckDetailList) {
                    checkDetailService.save(insertCheckDetail);
                }
//                checkDetailService.saveBatch(insertCheckDetailList);
            }

            BigDecimal logisticsFee = BigDecimal.ZERO;
            BigDecimal dtp = BigDecimal.ZERO;
            BigDecimal customsFee = BigDecimal.ZERO;
            BigDecimal clearCustomsFee = BigDecimal.ZERO;
            BigDecimal busySeasonFee = BigDecimal.ZERO;
            BigDecimal oilFee = BigDecimal.ZERO;
            BigDecimal sundryFee = BigDecimal.ZERO;
            BigDecimal othersFee = BigDecimal.ZERO;
            BigDecimal taxFee = BigDecimal.ZERO;
            BigDecimal changeTax = BigDecimal.ZERO;
            for (LsLogisticsFeePaymentDetail paymentDetail : paymentDetailList) {
                logisticsFee = logisticsFee.add(ObjectUtil.isNull(paymentDetail.getLogisticsFee()) ? BigDecimal.ZERO : paymentDetail.getLogisticsFee());
                dtp = dtp.add(ObjectUtil.isNull(paymentDetail.getDtp()) ? BigDecimal.ZERO : paymentDetail.getDtp());
                customsFee = customsFee.add(ObjectUtil.isNull(paymentDetail.getCustomsFee()) ? BigDecimal.ZERO : paymentDetail.getCustomsFee());
                clearCustomsFee = clearCustomsFee.add(ObjectUtil.isNull(paymentDetail.getClearCustomsFee()) ? BigDecimal.ZERO : paymentDetail.getClearCustomsFee());
                busySeasonFee = busySeasonFee.add(ObjectUtil.isNull(paymentDetail.getBusySeasonFee()) ? BigDecimal.ZERO : paymentDetail.getBusySeasonFee());
                oilFee = oilFee.add(ObjectUtil.isNull(paymentDetail.getOilFee()) ? BigDecimal.ZERO : paymentDetail.getOilFee());
                sundryFee = sundryFee.add(ObjectUtil.isNull(paymentDetail.getSundryFee()) ? BigDecimal.ZERO : paymentDetail.getSundryFee());
                othersFee = othersFee.add(ObjectUtil.isNull(paymentDetail.getOthersFee()) ? BigDecimal.ZERO : paymentDetail.getOthersFee());
                taxFee = taxFee.add(ObjectUtil.isNull(paymentDetail.getTaxFee()) ? BigDecimal.ZERO : paymentDetail.getTaxFee());
                changeTax = changeTax.add(ObjectUtil.isNull(paymentDetail.getChangeTax()) ? BigDecimal.ZERO : paymentDetail.getChangeTax());
            }
            BigDecimal totalLogisticsFee = logisticsFee.add(dtp).add(customsFee).add(clearCustomsFee).add(busySeasonFee).add(oilFee).add(sundryFee).add(othersFee);
            BigDecimal totalPaymentFee = paymentDetailList.stream().map(LsLogisticsFeePaymentDetail :: getTotalFee).reduce(BigDecimal.ZERO,BigDecimal::add);

            LsLogisticsFeePayment payment = new LsLogisticsFeePayment();
            payment.setLogisticsFeeNo(paymentService.getNowLogisticsFeeNo());
            payment.setLpCode(lpCode);
            payment.setLpName(lpName);
            payment.setLpSimpleName(lpSimpleName);
            payment.setTotalLogisticsFee(totalLogisticsFee);
            payment.setTotalTaxFee(taxFee.add(changeTax));
            payment.setTotalPaymentFee(totalPaymentFee);
            payment.setPaymentApplyStatus("未申请");
            payment.setCreateUser(name);
            //入库物流费付款
            paymentService.save(payment);

            //入库物流费付款明细
            for (LsLogisticsFeePaymentDetail paymentDetail : paymentDetailList) {
                paymentDetail.setLogisticsFeeNo(payment.getLogisticsFeeNo());
                paymentDetailService.save(paymentDetail);
            }

            //物流费对账数据支付次数
            this.updateBatchById(updateCheckList);
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData receiveLogisticsCheck(List<EbmsLogisticsCheckParam> params) {
        for (EbmsLogisticsCheckParam param : params) {
            if(!"CNY".equals(param.getLogisticsCurrency())){
                //四舍五入保留4位小数
                MathContext mathContext = new MathContext(4, RoundingMode.HALF_UP);
                param.setPredictUnitPrice(param.getDirectRate().multiply(param.getPredictUnitPrice(), mathContext));
                param.setPredictLogisticsFee(param.getDirectRate().multiply(param.getPredictLogisticsFee(), mathContext));
                param.setPredictOilFee(param.getDirectRate().multiply(param.getPredictOilFee(), mathContext));
                param.setPredictBusySeasonFee(param.getDirectRate().multiply(param.getPredictBusySeasonFee(), mathContext));
                param.setPredictOthersFee(param.getDirectRate().multiply(param.getPredictOthersFee(), mathContext));
                param.setPredictCustomsFee(param.getDirectRate().multiply(param.getPredictCustomsFee(), mathContext));
                param.setPredictClearCustomsFee(param.getDirectRate().multiply(param.getPredictClearCustomsFee(), mathContext));
//                param.setPredictTaxFee(param.getDirectRate().multiply(param.getPredictTaxFee(), mathContext));//已经是CNY
                param.setPredictTotalFee(param.getDirectRate().multiply(param.getPredictTotalFee(), mathContext));
            }

            //总费用差异
            param.setDiffTotalFee(param.getTotalFee().subtract(param.getPredictTotalFee()).abs());
            //税费有值的情况取物流费币制
            if(param.getTaxFee() != null){
                param.setTaxCurrency(param.getLogisticsCurrency());
            }
            LambdaQueryWrapper<LsLogisticsNoCheck> checkQw = new LambdaQueryWrapper<>();
            checkQw.eq(LsLogisticsNoCheck :: getShipmentNum, param.getShipmentNum());
            LsLogisticsNoCheck check = this.getOne(checkQw);
            if(check == null){
                //新增
                LsLogisticsNoCheck insertCheck = new LsLogisticsNoCheck();
                BeanUtils.copyProperties(param, insertCheck);
                insertCheck.setPaymentCount(0l);
                insertCheck.setCheckStatus("对账中");
                insertCheck.setLockStatus("未锁定");
                insertCheck.setDataSource("EBMS");
                this.save(insertCheck);

                LsLogisticsNoCheckDetail insertCheckDetail = new LsLogisticsNoCheckDetail();
                BeanUtils.copyProperties(param, insertCheckDetail);
                insertCheckDetail.setOrderNum(1);
                checkDetailService.save(insertCheckDetail);
            }else{
                if("锁定".equals(check.getLockStatus())){
                    return ResponseData.error(param.getShipmentNum() + "发货批次号已锁定，不能操作编辑");
                }

                //原费用
                LogisticsNoFeeParam oldFee = new LogisticsNoFeeParam();
                oldFee.setShipmentNum(check.getShipmentNum());
                oldFee.setConfirmFeeType(check.getConfirmFeeType());
                oldFee.setConfirmCountFee(check.getConfirmCountFee());
                oldFee.setRemark(check.getRemark());

                //更新
                BeanUtils.copyProperties(param, check);
                this.updateById(check);

                LambdaQueryWrapper<LsLogisticsNoCheckDetail> checkDetailQw = new LambdaQueryWrapper<>();
                checkDetailQw.eq(LsLogisticsNoCheckDetail :: getShipmentNum, param.getShipmentNum())
                        .eq(LsLogisticsNoCheckDetail :: getOrderNum, 1);
                LsLogisticsNoCheckDetail checkDetail = checkDetailService.getOne(checkDetailQw);

                oldFee.setLogisticsCurrency(checkDetail.getLogisticsCurrency());
                oldFee.setUnitPrice(checkDetail.getUnitPrice());
                oldFee.setLogisticsFee(checkDetail.getLogisticsFee());
                oldFee.setOilFee(checkDetail.getOilFee());
                oldFee.setBusySeasonFee(checkDetail.getBusySeasonFee());
                oldFee.setOthersFee(checkDetail.getOthersFee());
                oldFee.setCustomsFee(checkDetail.getCustomsFee());
                oldFee.setClearCustomsFee(checkDetail.getClearCustomsFee());
                oldFee.setTaxFee(checkDetail.getTaxFee());
                oldFee.setTotalFee(checkDetail.getTotalFee());

                BeanUtils.copyProperties(param, checkDetail);
                checkDetailService.updateById(checkDetail);

                //新费用
                LogisticsNoFeeParam newFee = new LogisticsNoFeeParam();
                newFee.setShipmentNum(check.getShipmentNum());
                newFee.setConfirmFeeType(check.getConfirmFeeType());
                newFee.setConfirmCountFee(check.getConfirmCountFee());
                newFee.setRemark(check.getRemark());
                newFee.setLogisticsCurrency(checkDetail.getLogisticsCurrency());
                newFee.setUnitPrice(checkDetail.getUnitPrice());
                newFee.setLogisticsFee(checkDetail.getLogisticsFee());
                newFee.setOilFee(checkDetail.getOilFee());
                newFee.setBusySeasonFee(checkDetail.getBusySeasonFee());
                newFee.setOthersFee(checkDetail.getOthersFee());
                newFee.setCustomsFee(checkDetail.getCustomsFee());
                newFee.setClearCustomsFee(checkDetail.getClearCustomsFee());
                newFee.setTaxFee(checkDetail.getTaxFee());
                newFee.setTotalFee(checkDetail.getTotalFee());

                //忽略比较的实体属性名称
                List<String> ignoreNameList = new ArrayList<>();
                ignoreNameList.add("confirmFeeType");
                ignoreNameList.add("logisticsCurrency");
                ignoreNameList.add("othersFeeRemark");
                ignoreNameList.add("remark");
                if(!logisticsNoService.compareBean(oldFee, newFee, ignoreNameList)){
                    //费用修改前的数据入库费用修改记录
                    LsLogisticsNoRecord insertRecord = new LsLogisticsNoRecord();
                    insertRecord.setCreateUser(param.getUpdateUser());
                    logisticsNoService.generateLogisticsFeeRecord(insertRecord, oldFee);
                    logisticsNoRecordService.save(insertRecord);
                }
            }
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData deleteEbmsCheck(List<LogisticsSettlementDetailParam> params) {
        log.info("接收EBMS删除物流单入参[{}]", JSONObject.toJSONString(params));
        if(CollectionUtil.isEmpty(params)){
            return ResponseData.error("发货批次号入参为空");
        }
        List<BigDecimal> idList = new ArrayList<>();
        List<String> shipmentNumList = new ArrayList<>();
        for (LogisticsSettlementDetailParam param : params) {
            LambdaQueryWrapper<LsLogisticsNoCheck> checkQw = new LambdaQueryWrapper();
            checkQw.eq(LsLogisticsNoCheck :: getShipmentNum, param.getShipmentNum())
                    .eq(LsLogisticsNoCheck :: getDataSource, "EBMS");
            LsLogisticsNoCheck check = this.getOne(checkQw);
            if(check != null){
                if("锁定".equals(check.getLockStatus())){
                    return ResponseData.error(param.getShipmentNum() + "发货批次号已锁定，不能操作删除");
                }
                idList.add(check.getId());
            }
            shipmentNumList.add(param.getShipmentNum());
        }
        this.removeByIds(idList);

        //删除物流单对账明细
        LambdaQueryWrapper<LsLogisticsNoCheckDetail> checkDetailQw = new LambdaQueryWrapper<>();
        checkDetailQw.in(LsLogisticsNoCheckDetail :: getShipmentNum, shipmentNumList);
        checkDetailService.remove(checkDetailQw);

        //删除物流单费用操作记录
        LambdaQueryWrapper<LsLogisticsNoRecord> recordQw = new LambdaQueryWrapper();
        recordQw.in(LsLogisticsNoRecord :: getShipmentNum, shipmentNumList);
        logisticsNoRecordService.remove(recordQw);
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional
    public ResponseData refreshSignDate() {
        log.info("定时获取EBMS物流跟踪表更新物流对账签收日期");
        //获取签收日期为null的数据
        LambdaQueryWrapper<LsLogisticsNoCheck> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LsLogisticsNoCheck :: getDataSource, "EBMS")
                .isNull(LsLogisticsNoCheck :: getSignDate);
        List<LsLogisticsNoCheck> list = checkService.list(queryWrapper);
        if(CollectionUtil.isNotEmpty(list)){
            List<List<LsLogisticsNoCheck>> lists = ListUtil.split(list, 1000);
            for (List<LsLogisticsNoCheck> subList : lists) {
                List<LsLogisticsNoCheck> signDateList = checkService.getEbmsSignDate(subList);
                if(CollectionUtil.isNotEmpty(signDateList)){
                    mapper.refreshSignDate(signDateList);
                }
            }
        }
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "EBMS")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<LsLogisticsNoCheck> getEbmsSignDate(List<LsLogisticsNoCheck> param){
        return mapper.getEbmsSignDate(param);
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData validateCheckOrder(LsLogisticsNoCheckDetailParam param) {
        if(StringUtils.isBlank(param.getLogisticsCheckOrder()) || StringUtils.isBlank(param.getShipmentNum())){
            return ResponseData.error("发货批次号和物流对账单号都不能为空！");
        }

        LambdaQueryWrapper<LsLogisticsNoCheck> qw = new LambdaQueryWrapper<>();
        qw.eq(LsLogisticsNoCheck :: getShipmentNum, param.getShipmentNum());
        LsLogisticsNoCheck check = mapper.selectOne(qw);
        if(check == null){
            return ResponseData.error("未查询到发货批次号对应的物流单对账信息！");
        }

        List<LsLogisticsNoCheck> resultList = mapper.validateCheckOrder(param);
        if(CollectionUtil.isNotEmpty(resultList)){
           List<String> lpCodeList = resultList.stream().map(LsLogisticsNoCheck :: getLpCode).collect(Collectors.toList());
           if(lpCodeList.size() == 1 && lpCodeList.contains(check.getLpCode())){
               return ResponseData.success();
           }else{
               log.info("物流对账单号不能对应多个物流商，物流商编码[{}]", String.join(",", lpCodeList));
               return ResponseData.error("物流对账单号不能对应多个物流商");
           }
        }else{
            return ResponseData.success();
        }
    }

}
