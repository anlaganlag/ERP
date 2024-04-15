package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.context.auth.LoginContext;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.*;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsCompanyBankParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsK3PaymentApplyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsFeePaymentParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsPaymentApplyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsFeePaymentDetailResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsFeePaymentResult;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.ErpWarehouseCodeConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LsLogisticsFeePaymentMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 物流费付款 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-12-01
 */
@Slf4j
@Service
public class LsLogisticsFeePaymentServiceImpl extends ServiceImpl<LsLogisticsFeePaymentMapper, LsLogisticsFeePayment> implements ILsLogisticsFeePaymentService {

    @Resource
    private LsLogisticsFeePaymentMapper mapper;
    @Autowired
    private ILsLogisticsFeePaymentDetailService paymentDetailService;
    @Autowired
    private ILsLogisticsNoCheckService checkService;
    @Autowired
    private ILsLogisticsNoCheckDetailService checkDetailService;
    @Autowired
    private ILsLpDepositPrepaymentService depositPrepaymentService;
    @Autowired
    private ILsK3PaymentApplyService k3PaymentApplyService;
    @Autowired
    private ILsK3PaymentApplyDetailService k3PaymentApplyDetailService;
    @Autowired
    private ILsLpDepositPrepaymentRecordService recordService;
    @Autowired
    private ErpWarehouseCodeConsumer erpWarehouseCodeConsumer;


    @Override
    @DataSource(name = "logistics")
    public ResponseData queryPage(LsLogisticsFeePaymentParam param) {
        return ResponseData.success(mapper.queryPage(param.getPageContext(), param));
    }

    @Override
    @DataSource(name = "logistics")
    public String getNowLogisticsFeeNo() {
        Integer noLength = 4;
        String pureDate = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        String preString = "WLF";
        String nowQgdOrder = mapper.getNowLogisticsFeeNo(preString + pureDate);//获取最新的物流付费编号
        String newQgdOrder;
        if(StringUtils.isNotBlank(nowQgdOrder)){
            Integer newNum = Integer.parseInt(nowQgdOrder.substring(nowQgdOrder.length() - noLength)) + 1;
            newQgdOrder = pureDate + StringUtils.leftPad(newNum.toString(), noLength, "0");
        }else{
            newQgdOrder = pureDate + StringUtils.leftPad("1", noLength, "0");
        }
        return preString + newQgdOrder;
    }

    @Override
    @DataSource(name = "logistics")
    public String getNowLogisticsPaymentNo() {
        Integer noLength = 4;
        String pureDate = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        String preString = "WLFFK";
        String nowQgdOrder = mapper.getNowLogisticsPaymentNo(preString + pureDate);//获取物流费付款最新的K3单据编号
        String newQgdOrder;
        if(StringUtils.isNotBlank(nowQgdOrder)){
            Integer newNum = Integer.parseInt(nowQgdOrder.substring(nowQgdOrder.length() - noLength)) + 1;
            newQgdOrder = pureDate + StringUtils.leftPad(newNum.toString(), noLength, "0");
        }else{
            newQgdOrder = pureDate + StringUtils.leftPad("1", noLength, "0");
        }
        return preString + newQgdOrder;
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData del(LsLogisticsFeePaymentParam param) {
        log.info("删除物流费付款入参[{}]", JSONObject.toJSON(param));
        if(param.getId() == null){
            return ResponseData.error("物流费付款ID为空");
        }
        LsLogisticsFeePayment payment = this.getById(param.getId());
        if(payment == null){
            return ResponseData.error("未查询到物流费付款信息");
        }
        if(!"未申请".equals(payment.getPaymentApplyStatus())){
            String paymentApplyNo = payment.getPaymentApplyNo();

            //删除K3付款申请单
            LsK3PaymentApplyParam paymentApplyParam = new LsK3PaymentApplyParam();
            paymentApplyParam.setPaymentApplyNo(paymentApplyNo);
            ResponseData resp = k3PaymentApplyService.delPaymentApply(paymentApplyParam);
            if(ResponseData.DEFAULT_SUCCESS_CODE.equals(resp.getCode())){
                LambdaQueryWrapper<LsK3PaymentApply> paymentApplyQW = new LambdaQueryWrapper();
                paymentApplyQW.eq(LsK3PaymentApply :: getPaymentApplyNo, paymentApplyNo);
                k3PaymentApplyService.remove(paymentApplyQW);

                LambdaQueryWrapper<LsK3PaymentApplyDetail> paymentApplyDetailQW = new LambdaQueryWrapper();
                paymentApplyDetailQW.eq(LsK3PaymentApplyDetail :: getPaymentApplyNo, paymentApplyNo);
                k3PaymentApplyDetailService.remove(paymentApplyDetailQW);
            }else{
                return resp;
            }
        }

        String name = LoginContext.me().getLoginUser().getName();
        Date nowDate = DateUtil.date();

        Set<String> shipmentNumList = new HashSet<>();
        LambdaQueryWrapper<LsLogisticsFeePaymentDetail> paymentDetailQw = new LambdaQueryWrapper();
        paymentDetailQw.eq(LsLogisticsFeePaymentDetail :: getLogisticsFeeNo, payment.getLogisticsFeeNo());
        List<LsLogisticsFeePaymentDetail> paymentDetailList = paymentDetailService.list(paymentDetailQw);
        if(CollectionUtil.isNotEmpty(paymentDetailList)){
            for (LsLogisticsFeePaymentDetail paymentDetail : paymentDetailList) {
                LambdaQueryWrapper<LsLogisticsNoCheckDetail> checkDetailQw = new LambdaQueryWrapper();
                checkDetailQw.eq(LsLogisticsNoCheckDetail :: getShipmentNum, paymentDetail.getShipmentNum())
                        .eq(LsLogisticsNoCheckDetail :: getOrderNum, paymentDetail.getOrderNum())
                        .ne(LsLogisticsNoCheckDetail :: getOrderNum, 1);
                //删除物流费对账明细非首次生成物流费
                checkDetailService.remove(checkDetailQw);
                shipmentNumList.add(paymentDetail.getShipmentNum());
            }
        }

        //更新物流费对账支付次数
        LambdaUpdateWrapper<LsLogisticsNoCheck> checkUw = new LambdaUpdateWrapper();
        checkUw.in(LsLogisticsNoCheck :: getShipmentNum, shipmentNumList)
                .ne(LsLogisticsNoCheck :: getPaymentCount, 0)
                .set(LsLogisticsNoCheck :: getUpdateTime, nowDate)
                .set(LsLogisticsNoCheck :: getUpdateUser, name)
                .setSql("PAYMENT_COUNT = PAYMENT_COUNT - 1");
        checkService.update(checkUw);

        //删除物流费付款明细
        paymentDetailService.remove(paymentDetailQw);

        //删除物流费付款
        this.removeById(payment.getId());
        return ResponseData.success();
    }

    @Override
    @DataSource(name = "logistics")
    public List<LsLogisticsFeePaymentResult> export(LsLogisticsFeePaymentParam param) {
        Page pageContext = param.getPageContext();
        pageContext.setCurrent(1);
        pageContext.setSize(Integer.MAX_VALUE);
        return mapper.queryPage(pageContext, param).getRecords();
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData hasEnoughPrepayment(LsPaymentApplyParam param) {
        log.info("是否有足够的物流商押金或预付款入参[{}]", JSONObject.toJSON(param));
        if(CollectionUtil.isEmpty(param.getIdList())){
            return ResponseData.error("物流费付款ID为空");
        }
        List<LsLogisticsFeePayment> paymentList = this.listByIds(param.getIdList());
        if(CollectionUtil.isEmpty(paymentList)){
            return ResponseData.error("未查询到物流费付款信息");
        }
        if(param.getIdList().size() != paymentList.size()){
            return ResponseData.error("物流费付款信息查询记录数与所选记录数不一致");
        }
        BigDecimal totalPaymentFee = paymentList.stream().map(LsLogisticsFeePayment :: getTotalPaymentFee).reduce(BigDecimal.ZERO, BigDecimal::add);
        Map<String, List<LsLogisticsFeePayment>> paymentListMap = paymentList.stream().collect(Collectors.groupingBy(payment -> payment.getLpCode(), LinkedHashMap::new, Collectors.toList()));
        if(paymentListMap.size() > 1){
            return ResponseData.error("相同的物流商名称方可申请物流费付款");
        }
        //校验币制是否一致
        Set<String> logisticsFeeNoSet = paymentList.stream().map(LsLogisticsFeePayment :: getLogisticsFeeNo).collect(Collectors.toSet());
        LambdaQueryWrapper<LsLogisticsFeePaymentDetail> allPaymentDetailQw = new LambdaQueryWrapper<>();
        allPaymentDetailQw.in(LsLogisticsFeePaymentDetail :: getLogisticsFeeNo, logisticsFeeNoSet);
        List<LsLogisticsFeePaymentDetail> allPaymentDetailList = paymentDetailService.list(allPaymentDetailQw);
        if(CollectionUtil.isEmpty(allPaymentDetailList)){
            return ResponseData.error("未查询到物流费付款明细信息");
        }
        Set<String> logisticsCurrencySet = allPaymentDetailList.stream().filter(i -> StringUtils.isNotBlank(i.getLogisticsCurrency())).map(LsLogisticsFeePaymentDetail :: getLogisticsCurrency).collect(Collectors.toSet());
        Set<String> taxCurrencySet = allPaymentDetailList.stream().filter(i -> StringUtils.isNotBlank(i.getTaxCurrency())).map(LsLogisticsFeePaymentDetail :: getTaxCurrency).collect(Collectors.toSet());
        if(logisticsCurrencySet.size() > 1
                || taxCurrencySet.size() > 1
                || (logisticsCurrencySet.size() != 0 && !logisticsCurrencySet.containsAll(taxCurrencySet))
                || (taxCurrencySet.size() != 0 && !taxCurrencySet.containsAll(logisticsCurrencySet))){
            return ResponseData.error("相同的币制方可申请物流费付款");
        }
        String currency = CollectionUtil.isEmpty(logisticsCurrencySet) ? taxCurrencySet.stream().findFirst().get() : logisticsCurrencySet.stream().findFirst().get();
        LambdaQueryWrapper<LsLpDepositPrepayment> depositPrepaymentQw = new LambdaQueryWrapper<>();
        depositPrepaymentQw.eq(LsLpDepositPrepayment :: getLpCode, paymentList.get(0).getLpCode())
                .eq(LsLpDepositPrepayment :: getPaymentCurrency, currency);
        LsLpDepositPrepayment prepayment = depositPrepaymentService.getOne(depositPrepaymentQw);
        if(prepayment == null){
            return ResponseData.success(Boolean.FALSE);
        } else {
            if(prepayment.getPrepaymentAmt().compareTo(totalPaymentFee) >= 0){
                return ResponseData.success(Boolean.TRUE);
            }
        }
        return ResponseData.success(Boolean.TRUE);
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData logisticsCheckOrder(LsPaymentApplyParam param) {
        log.info("查询新建申请付款单对应明细的物流对账单号入参[{}]", JSONObject.toJSON(param));
        if(CollectionUtil.isEmpty(param.getIdList())){
            return ResponseData.error("物流费付款ID为空");
        }
        List<LsLogisticsFeePayment> paymentList = this.listByIds(param.getIdList());
        if(CollectionUtil.isEmpty(paymentList)){
            return ResponseData.error("未查询到物流费付款信息");
        }
        List<LsLogisticsFeePaymentDetailResult> returnDetail = new ArrayList<>();
        for (LsLogisticsFeePayment payment : paymentList) {
            LambdaQueryWrapper<LsLogisticsFeePaymentDetail> paymentDetailQw = new LambdaQueryWrapper<>();
            paymentDetailQw.eq(LsLogisticsFeePaymentDetail :: getLogisticsFeeNo, payment.getLogisticsFeeNo());
            List<LsLogisticsFeePaymentDetail> paymentDetailList = paymentDetailService.list(paymentDetailQw);
            if(CollectionUtil.isNotEmpty(paymentDetailList)){
                LsLogisticsFeePaymentDetailResult detail = new LsLogisticsFeePaymentDetailResult();
                Set<String> logisticsCheckOrderSet = paymentDetailList.stream().map(i -> i.getLogisticsCheckOrder()).collect(Collectors.toSet());
                String logisticsCheckOrderStr = logisticsCheckOrderSet.stream().map(Object :: toString).collect(Collectors.joining(","));
                detail.setId(payment.getId());
                detail.setLogisticsCheckOrder(logisticsCheckOrderStr);
                returnDetail.add(detail);
            }
        }
        return ResponseData.success(returnDetail);
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData paymentApply(LsPaymentApplyParam param) {
        log.info("K3付款申请单入参[{}]", JSONObject.toJSON(param));
        if(CollectionUtil.isEmpty(param.getIdList())){
            return ResponseData.error("物流费付款ID为空");
        }
        List<LsLogisticsFeePayment> paymentList = this.listByIds(param.getIdList());
        if(CollectionUtil.isEmpty(paymentList)){
            return ResponseData.error("未查询到物流费付款信息");
        }
        if(param.getIdList().size() != paymentList.size()){
            return ResponseData.error("物流费付款信息查询记录数与所选记录数不一致");
        }
        BigDecimal totalPaymentFee = paymentList.stream().map(LsLogisticsFeePayment :: getTotalPaymentFee).reduce(BigDecimal.ZERO, BigDecimal::add);
        Map<String, List<LsLogisticsFeePayment>> paymentListMap = paymentList.stream().collect(Collectors.groupingBy(payment -> payment.getLpCode(), LinkedHashMap::new, Collectors.toList()));
        if(paymentListMap.size() > 1){
            return ResponseData.error("相同的物流商名称方可申请物流费付款");
        }

        //校验币制是否一致
        Set<String> logisticsFeeNoSet = paymentList.stream().map(LsLogisticsFeePayment :: getLogisticsFeeNo).collect(Collectors.toSet());
        LambdaQueryWrapper<LsLogisticsFeePaymentDetail> allPaymentDetailQw = new LambdaQueryWrapper<>();
        allPaymentDetailQw.in(LsLogisticsFeePaymentDetail :: getLogisticsFeeNo, logisticsFeeNoSet);
        List<LsLogisticsFeePaymentDetail> allPaymentDetailList = paymentDetailService.list(allPaymentDetailQw);
        if(CollectionUtil.isEmpty(allPaymentDetailList)){
            return ResponseData.error("未查询到物流费付款明细信息");
        }
        Set<String> logisticsCurrencySet = allPaymentDetailList.stream().filter(i -> StringUtils.isNotBlank(i.getLogisticsCurrency())).map(LsLogisticsFeePaymentDetail :: getLogisticsCurrency).collect(Collectors.toSet());
        Set<String> taxCurrencySet = allPaymentDetailList.stream().filter(i -> StringUtils.isNotBlank(i.getTaxCurrency())).map(LsLogisticsFeePaymentDetail :: getTaxCurrency).collect(Collectors.toSet());
        if(logisticsCurrencySet.size() > 1
                || taxCurrencySet.size() > 1
                || (logisticsCurrencySet.size() != 0 && !logisticsCurrencySet.containsAll(taxCurrencySet))
                || (taxCurrencySet.size() != 0 && !taxCurrencySet.containsAll(logisticsCurrencySet))){
            return ResponseData.error("相同的币制方可申请物流费付款");
        }

        String name = LoginContext.me().getLoginUser().getName();
        Date nowDate = DateUtil.date();

        LsK3PaymentApply k3Apply = param.getApply();
        List<LsK3PaymentApplyDetail> applyDetailList = param.getApplyDetailList();
        if(k3Apply == null){
            return ResponseData.error("单据头数据不能为空");
        }
        if(CollectionUtil.isEmpty(applyDetailList)){
            return ResponseData.error("单据明细数据不能为空");
        }
        BigDecimal totalSettlementAmt = applyDetailList.stream().map(LsK3PaymentApplyDetail :: getSettlementAmt).reduce(BigDecimal.ZERO, BigDecimal::add);
        if(totalPaymentFee.compareTo(totalSettlementAmt) != 0){
            return ResponseData.error("所选数据的申请付款金额与单据明细的对账金额合计金额不相等");
        }
        String applyOrgCode = erpWarehouseCodeConsumer.getOrganizationCode(k3Apply.getApplyOrg());
        if(StringUtils.isBlank(applyOrgCode)){
            return ResponseData.error("未查询到申请组织对应的组织编码");
        }
        String currency = CollectionUtil.isEmpty(logisticsCurrencySet) ? taxCurrencySet.stream().findFirst().get() : logisticsCurrencySet.stream().findFirst().get();
        String logisticsPaymentNo = this.getNowLogisticsPaymentNo();
        //预付款支付，扣除预付款
        if("是".equals(k3Apply.getIsDepositPrepayment())){
            LambdaQueryWrapper<LsLpDepositPrepayment> depositPrepaymentQw = new LambdaQueryWrapper<>();
            depositPrepaymentQw.eq(LsLpDepositPrepayment :: getLpCode, paymentList.get(0).getLpCode())
                    .eq(LsLpDepositPrepayment :: getPaymentCurrency, currency);
            LsLpDepositPrepayment prepayment = depositPrepaymentService.getOne(depositPrepaymentQw);
            if(prepayment == null){
                return ResponseData.error("未查询到物流商预付款信息，物流商编码：" + paymentList.get(0).getLpCode() + "币制：" + currency);
            } else {
                if(prepayment.getPrepaymentAmt().compareTo(totalPaymentFee) < 0){
                    return ResponseData.error("物流商预付款金额不足以支付申请付款金额，物流商编码：" + paymentList.get(0).getLpCode() + "物流商名称：" + paymentList.get(0).getLpName() + "币制：" + currency);
                }
                BigDecimal nowPrepaymentAmt = prepayment.getPrepaymentAmt().subtract(totalPaymentFee);
                LsLpDepositPrepaymentRecord record = new LsLpDepositPrepaymentRecord();
                record.setPid(prepayment.getId());
                record.setLpCode(prepayment.getLpCode());
                record.setCreateUser(name);
                record.setOptType("付款");
                StringBuffer sb = new StringBuffer();
                sb.append("付款金额【").append(totalPaymentFee).append("】，");
                sb.append("付款单据编号【").append(logisticsPaymentNo).append("】");
                record.setOptDetail(sb.toString());
                recordService.save(record);

                LsLpDepositPrepayment updatePrepayment = new LsLpDepositPrepayment();
                updatePrepayment.setId(prepayment.getId());
                updatePrepayment.setPrepaymentAmt(nowPrepaymentAmt);
                updatePrepayment.setUpdateTime(nowDate);
                updatePrepayment.setUpdateUser(name);
                depositPrepaymentService.updateById(updatePrepayment);
            }
        }

        //更新物流费付款集合
//        List<LsLogisticsFeePayment> updateFeePaymentList = new ArrayList<>();
        //更新物流单对账集合
//        List<LsLogisticsNoCheckDetail> updateCheckDetailList = new ArrayList<>();
        for (LsLogisticsFeePayment feePayment : paymentList) {
            LsLogisticsFeePayment updateFeePayment = new LsLogisticsFeePayment();
            updateFeePayment.setId(feePayment.getId());
            updateFeePayment.setErpApplyDate(nowDate);
            updateFeePayment.setPaymentApplyNo(logisticsPaymentNo);
            updateFeePayment.setUpdateTime(nowDate);
            updateFeePayment.setUpdateUser(name);
//            updateFeePaymentList.add(updateFeePayment);
            this.updateById(updateFeePayment);

            LambdaQueryWrapper<LsLogisticsFeePaymentDetail> paymentDetailQw = new LambdaQueryWrapper<>();
            paymentDetailQw.eq(LsLogisticsFeePaymentDetail :: getLogisticsFeeNo, feePayment.getLogisticsFeeNo());
            List<LsLogisticsFeePaymentDetail> paymentDetailList = paymentDetailService.list(paymentDetailQw);
            for (LsLogisticsFeePaymentDetail paymentDetail : paymentDetailList) {
                LambdaUpdateWrapper<LsLogisticsNoCheckDetail> checkDetailUw = new LambdaUpdateWrapper<>();
                checkDetailUw.eq(LsLogisticsNoCheckDetail :: getShipmentNum, paymentDetail.getShipmentNum())
                        .eq(LsLogisticsNoCheckDetail :: getOrderNum, paymentDetail.getOrderNum())
                        .set(LsLogisticsNoCheckDetail :: getLogisticsErpDate, nowDate)
                        .set(LsLogisticsNoCheckDetail :: getLogisticsBillOrder, logisticsPaymentNo);
                checkDetailService.update(checkDetailUw);
            }
        }

        //批量回写物流费付款ERP申请日期和单据编号
//        this.updateBatchById(updateFeePaymentList);
        //批量回写物流单对账明细ERP申请日期和单据编号

        k3Apply.setPaymentApplyNo(logisticsPaymentNo);
        k3Apply.setApplyOrgCode(applyOrgCode);
        k3Apply.setSettlementCode(applyOrgCode);
        k3Apply.setPaymentOrgCode(applyOrgCode);
        k3Apply.setCreateUser(name);
        k3Apply.setSyncK3Date(nowDate);
        k3PaymentApplyService.save(k3Apply);

        for (LsK3PaymentApplyDetail applyDetail : applyDetailList) {
            applyDetail.setPaymentApplyNo(k3Apply.getPaymentApplyNo());
            applyDetail.setCreateUser(name);
            k3PaymentApplyDetailService.save(applyDetail);
        }

        //K3物流费申请保存
        LambdaUpdateWrapper<LsLogisticsFeePayment> updateFeePaymentQw = new LambdaUpdateWrapper<>();
        updateFeePaymentQw.in(LsLogisticsFeePayment :: getId, param.getIdList());
        try {
            ResponseData resp = k3PaymentApplyService.k3Save(k3Apply, applyDetailList);
            if(ResponseData.DEFAULT_SUCCESS_CODE.equals(resp.getCode())){
                updateFeePaymentQw.set(LsLogisticsFeePayment :: getPaymentApplyStatus, "申请成功");
                this.update(updateFeePaymentQw);
                return ResponseData.success();
            }else{
                updateFeePaymentQw.set(LsLogisticsFeePayment :: getPaymentApplyStatus, "申请失败");
                this.update(updateFeePaymentQw);
                return resp;
            }
        } catch (Exception e){
            updateFeePaymentQw.set(LsLogisticsFeePayment :: getPaymentApplyStatus, "申请失败");
            this.update(updateFeePaymentQw);
            log.error("K3物流费付款申请单保存异常！异常信息[{}]", e.getMessage());
            return ResponseData.error("K3物流费付款申请单保存异常！" + e.getMessage());
        }
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData paymentApplyEdit(LsPaymentApplyParam param) {
        log.info("K3付款申请单编辑入参[{}]", JSONObject.toJSON(param));
        LsK3PaymentApply k3Apply = param.getApply();
        List<LsK3PaymentApplyDetail> applyDetailList = param.getApplyDetailList();
        if(k3Apply == null){
            return ResponseData.error("单据头数据不能为空");
        }
        if(CollectionUtil.isEmpty(applyDetailList)){
            return ResponseData.error("单据明细数据不能为空");
        }
        String logisticsPaymentNo = k3Apply.getPaymentApplyNo();
        LambdaQueryWrapper<LsLogisticsFeePayment> feePaymentQw = new LambdaQueryWrapper<>();
        feePaymentQw.eq(LsLogisticsFeePayment :: getPaymentApplyNo, logisticsPaymentNo);
        List<LsLogisticsFeePayment> paymentList = this.list(feePaymentQw);
        if(CollectionUtil.isEmpty(paymentList)){
            return ResponseData.error("未查询到物流费付款信息");
        }
        BigDecimal totalPaymentFee = paymentList.stream().map(LsLogisticsFeePayment :: getTotalPaymentFee).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalSettlementAmt = applyDetailList.stream().map(LsK3PaymentApplyDetail :: getSettlementAmt).reduce(BigDecimal.ZERO, BigDecimal::add);
        if(totalPaymentFee.compareTo(totalSettlementAmt) != 0){
            return ResponseData.error("所选数据的申请付款金额与单据明细的对账金额合计金额不相等");
        }
        String name = LoginContext.me().getLoginUser().getName();
        Date nowDate = DateUtil.date();
        k3Apply.setUpdateUser(name);
        k3Apply.setUpdateUserAccount(LoginContext.me().getLoginUser().getAccount());
        k3Apply.setUpdateTime(nowDate);
        k3PaymentApplyService.updateById(k3Apply);

        //单据明细先删除再新增
        LambdaQueryWrapper<LsK3PaymentApplyDetail> k3PaymentApplyDetailQw = new LambdaQueryWrapper<>();
        k3PaymentApplyDetailQw.eq(LsK3PaymentApplyDetail :: getPaymentApplyNo, logisticsPaymentNo);
        k3PaymentApplyDetailService.remove(k3PaymentApplyDetailQw);
        for (LsK3PaymentApplyDetail applyDetail : applyDetailList) {
            applyDetail.setPaymentApplyNo(logisticsPaymentNo);
            applyDetail.setCreateUser(name);
            k3PaymentApplyDetailService.save(applyDetail);
        }

        //K3物流费申请保存
        ResponseData resp = k3PaymentApplyService.k3Save(k3Apply, applyDetailList);
        if(ResponseData.DEFAULT_SUCCESS_CODE.equals(resp.getCode())){
            LambdaUpdateWrapper<LsLogisticsFeePayment> updateFeePaymentQw = new LambdaUpdateWrapper<>();
            updateFeePaymentQw.eq(LsLogisticsFeePayment :: getPaymentApplyNo, logisticsPaymentNo)
                    .set(LsLogisticsFeePayment :: getPaymentApplyStatus, "申请成功");
            this.update(updateFeePaymentQw);
            return ResponseData.success();
        }else{
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return resp;
        }
    }

    @Override
    @DataSource(name = "logistics")
    @Transactional(rollbackFor = Exception.class)
    public ResponseData syncK3PaymentApply(LsLogisticsFeePaymentParam param) {
        log.info("K3付款申请单编辑入参[{}]", JSONObject.toJSON(param));
        if(param.getId() == null){
            return ResponseData.error("物流费付款ID为空");
        }
        LsLogisticsFeePayment feePayment = this.getById(param.getId());
        if(feePayment == null){
            return ResponseData.error("未查询到物流费付款信息");
        }
        if(!"申请失败".equals(feePayment.getPaymentApplyStatus())){
            return ResponseData.error("此状态无需操作同步");
        }
        String logisticsPaymentNo = feePayment.getPaymentApplyNo();
        LambdaQueryWrapper<LsLogisticsFeePayment> feePaymentQw = new LambdaQueryWrapper<>();
        feePaymentQw.eq(LsLogisticsFeePayment :: getPaymentApplyNo, logisticsPaymentNo);
        List<LsLogisticsFeePayment> paymentList = this.list(feePaymentQw);

        LambdaQueryWrapper<LsK3PaymentApply> applyQw = new LambdaQueryWrapper<>();
        applyQw.eq(LsK3PaymentApply :: getPaymentApplyNo, logisticsPaymentNo);
        LsK3PaymentApply k3Apply = k3PaymentApplyService.getOne(applyQw);
        if(k3Apply == null){
            return ResponseData.error("单据头数据不能为空");
        }

        LambdaQueryWrapper<LsK3PaymentApplyDetail> k3PaymentApplyDetailQw = new LambdaQueryWrapper<>();
        k3PaymentApplyDetailQw.eq(LsK3PaymentApplyDetail :: getPaymentApplyNo, logisticsPaymentNo);
        List<LsK3PaymentApplyDetail> applyDetailList = k3PaymentApplyDetailService.list(k3PaymentApplyDetailQw);
        if(CollectionUtil.isEmpty(applyDetailList)){
            return ResponseData.error("单据明细数据不能为空");
        }

        BigDecimal totalPaymentFee = paymentList.stream().map(LsLogisticsFeePayment :: getTotalPaymentFee).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalSettlementAmt = applyDetailList.stream().map(LsK3PaymentApplyDetail :: getSettlementAmt).reduce(BigDecimal.ZERO, BigDecimal::add);
        if(totalPaymentFee.compareTo(totalSettlementAmt) != 0){
            return ResponseData.error("所选数据的付款费用与单据明细的对账金额合计金额不相等");
        }

        //调用K3付款申请单接口
        ResponseData resp = k3PaymentApplyService.k3Save(k3Apply, applyDetailList);
        if(ResponseData.DEFAULT_SUCCESS_CODE.equals(resp.getCode())){
            LambdaUpdateWrapper<LsLogisticsFeePayment> updateFeePaymentQw = new LambdaUpdateWrapper<>();
            updateFeePaymentQw.in(LsLogisticsFeePayment :: getPaymentApplyNo, logisticsPaymentNo);
            updateFeePaymentQw.set(LsLogisticsFeePayment :: getPaymentApplyStatus, "申请成功");
            this.update(updateFeePaymentQw);
            return ResponseData.success();
        }else{
            return resp;
        }
    }

    @Override
    @DataSource(name = "warehouse")
    public ResponseData orgSelect() {
        return ResponseData.success(mapper.orgSelect());
    }

    @Override
    @DataSource(name = "erpcloud")
    public ResponseData payTypeSelect() {
        return ResponseData.success(mapper.payTypeSelect());
    }

    @Override
    @DataSource(name = "logistics")
    public ResponseData isPrePaySelect(String payType) {
        BaseSelectResult result = new BaseSelectResult();
        String[] prePayArr = new String[]{"预付-纯预付", "预付-现货", "预付-定金", "预付-尾款"};
        String[] notPrePayArr = new String[]{"周结付款", "半月结付款", "月结付款"};
        if(ArrayUtils.contains(prePayArr, payType)){
            result.setCode("是");
            result.setName("是");
        }
        if(ArrayUtils.contains(notPrePayArr, payType)){
            result.setCode("否");
            result.setName("否");
        }
        return ResponseData.success(result);
    }

    @Override
    @DataSource(name = "erpcloud")
    public ResponseData settlementTypeSelect() {
        return ResponseData.success(mapper.settlementTypeSelect());
    }

    @Override
    @DataSource(name = "erpcloud")
    public ResponseData useTypeSelect() {
        return ResponseData.success(mapper.useTypeSelect());
    }

    @Override
    @DataSource(name = "erpcloud")
    public ResponseData lsCompanySelect() {
        return ResponseData.success(mapper.lsCompanySelect());
    }

    @Override
    @DataSource(name = "erpcloud")
    public ResponseData supplierNameSelect() {
        return ResponseData.success(mapper.supplierNameSelect());
    }

    @Override
    @DataSource(name = "erpcloud")
    public ResponseData lsCompanyBankSelect(LsCompanyBankParam param) {
        return ResponseData.success(mapper.lsCompanyBankSelect(param));
    }

    @Override
    @DataSource(name = "erpcloud")
    public ResponseData k3CurrencySelect() {
        return ResponseData.success(mapper.k3CurrencySelect());
    }

}
