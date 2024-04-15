package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FixedExchangeRateParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsCompanyBankParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsFeePaymentParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsPaymentApplyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.BaseSelectResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.K3CurrencyResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsFeePaymentDetailResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsFeePaymentResult;
import com.tadpole.cloud.supplyChain.modular.logistics.consumer.FixedExchangeRateConsumer;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLogisticsFeePaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 物流费付款 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-12-01
 */
@RestController
@Api(tags = "物流费付款")
@ApiResource(name = "物流费付款", path = "/lsLogisticsFeePayment")
public class LsLogisticsFeePaymentController {

    @Autowired
    private ILsLogisticsFeePaymentService service;
    @Autowired
    private FixedExchangeRateConsumer fixedExchangeRateConsumer;

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    @PostResource(name = "物流费付款", path = "/queryPage", menuFlag = true)
    @ApiOperation(value = "分页查询列表", response = LsLogisticsFeePaymentResult.class)
    @BusinessLog(title = "物流费付款-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody LsLogisticsFeePaymentParam param) {
        return service.queryPage(param);
    }

    /**
     * 删除
     * @param param
     * @return
     */
    @PostResource(name = "删除", path = "/del")
    @ApiOperation(value = "删除")
    @BusinessLog(title = "物流费付款-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData del(@RequestBody LsLogisticsFeePaymentParam param) {
        return service.del(param);
    }

    /**
     * 导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "导出", path = "/export", requiredPermission = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "物流费付款-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody LsLogisticsFeePaymentParam param, HttpServletResponse response) throws IOException {
        List<LsLogisticsFeePaymentResult> resultList = service.export(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("物流费付款导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), LsLogisticsFeePaymentResult.class).sheet("物流费付款导出").doWrite(resultList);
    }

    /**
     * 是否有足够的物流商押金或预付款
     * @param param
     * @return
     */
    @PostResource(name = "是否有足够的物流商押金或预付款", path = "/hasEnoughPrepayment")
    @ApiOperation(value = "是否有足够的物流商押金或预付款")
    @BusinessLog(title = "物流费付款-是否有足够的物流商押金或预付款",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData hasEnoughPrepayment(@RequestBody LsPaymentApplyParam param) {
        return service.hasEnoughPrepayment(param);
    }

    /**
     * K3付款申请单明细对应的物流对账单号
     * @param param
     * @return
     */
    @PostResource(name = "K3付款申请单明细对应的物流对账单号", path = "/logisticsCheckOrder")
    @ApiOperation(value = "K3付款申请单明细对应的物流对账单号", response = LsLogisticsFeePaymentDetailResult.class)
    @BusinessLog(title = "物流费付款-K3付款申请单明细对应的物流对账单号",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData logisticsCheckOrder(@RequestBody LsPaymentApplyParam param) {
        return service.logisticsCheckOrder(param);
    }

    /**
     * K3付款申请单
     * @param param
     * @return
     */
    @PostResource(name = "K3付款申请单", path = "/paymentApply")
    @ApiOperation(value = "K3付款申请单")
    @BusinessLog(title = "物流费付款-K3付款申请单",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData paymentApply(@RequestBody LsPaymentApplyParam param) {
        return service.paymentApply(param);
    }

    /**
     * K3付款申请单编辑
     * @param param
     * @return
     */
    @PostResource(name = "K3付款申请单编辑", path = "/paymentApplyEdit")
    @ApiOperation(value = "K3付款申请单编辑")
    @BusinessLog(title = "物流费付款-K3付款申请单编辑",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData paymentApplyEdit(@RequestBody LsPaymentApplyParam param) {
        return service.paymentApplyEdit(param);
    }

    /**
     * 同步K3付款申请单
     * @param param
     * @return
     */
    @PostResource(name = "同步K3付款申请单", path = "/syncK3PaymentApply")
    @ApiOperation(value = "同步K3付款申请单")
    @BusinessLog(title = "物流费付款-同步K3付款申请单",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData syncK3PaymentApply(@RequestBody LsLogisticsFeePaymentParam param) {
        return service.syncK3PaymentApply(param);
    }

    /**
     * 组织下拉
     * @return
     */
    @GetResource(name = "组织下拉", path = "/orgSelect")
    @ApiOperation(value = "组织下拉", response = BaseSelectResult.class)
    public ResponseData orgSelect() {
        return service.orgSelect();
    }

    /**
     * 付款类型下拉
     * @return
     */
    @GetResource(name = "付款类型下拉", path = "/payTypeSelect")
    @ApiOperation(value = "付款类型下拉", response = BaseSelectResult.class)
    public ResponseData payTypeSelect() {
        return service.payTypeSelect();
    }

    /**
     * 是否预付下拉
     * @return
     */
    @GetResource(name = "是否预付下拉", path = "/isPrePaySelect")
    @ApiOperation(value = "是否预付下拉", response = BaseSelectResult.class)
    public ResponseData isPrePaySelect(@RequestParam(value = "payType", required = true) String payType) {
        return service.isPrePaySelect(payType);
    }

    /**
     * 结算方式下拉
     * @return
     */
    @GetResource(name = "结算方式下拉", path = "/settlementTypeSelect")
    @ApiOperation(value = "结算方式下拉", response = BaseSelectResult.class)
    public ResponseData settlementTypeSelect() {
        return service.settlementTypeSelect();
    }

    /**
     * 付款用途下拉
     * @return
     */
    @GetResource(name = "付款用途下拉", path = "/useTypeSelect")
    @ApiOperation(value = "付款用途下拉", response = BaseSelectResult.class)
    public ResponseData useTypeSelect() {
        return service.useTypeSelect();
    }

    /**
     * 物流收款单位下拉
     * @return
     */
    @GetResource(name = "物流收款单位下拉", path = "/lsCompanySelect")
    @ApiOperation(value = "物流收款单位下拉", response = BaseSelectResult.class)
    public ResponseData lsCompanySelect() {
        return service.lsCompanySelect();
    }

    /**
     * 内部供应商下拉（对应K3付款申请表头供应链公司）
     * @return
     */
    @GetResource(name = "内部供应商下拉", path = "/supplierNameSelect")
    @ApiOperation(value = "内部供应商下拉", response = BaseSelectResult.class)
    public ResponseData supplierNameSelect() {
        return service.supplierNameSelect();
    }

    /**
     * 对方银行信息下拉
     * @return
     */
    @PostResource(name = "对方银行信息下拉", path = "/lsCompanyBankSelect")
    @ApiOperation(value = "对方银行信息下拉", response = BaseSelectResult.class)
    public ResponseData lsCompanyBankSelect(@RequestBody LsCompanyBankParam param) {
        return service.lsCompanyBankSelect(param);
    }

    /**
     * K3币别下拉
     * @return
     */
    @GetResource(name = "K3币别下拉", path = "/k3CurrencySelect")
    @ApiOperation(value = "K3币别下拉", response = K3CurrencyResult.class)
    public ResponseData k3CurrencySelect() {
        return service.k3CurrencySelect();
    }

    /**
     * 根据申请日期和币别获取汇率
     * @return
     */
    @PostResource(name = "根据申请日期和币别获取汇率", path = "/getExchangeRate")
    @ApiOperation(value = "根据申请日期和币别获取汇率", response = FixedExchangeRate.class)
    public ResponseData getExchangeRate(@RequestBody FixedExchangeRateParam param) {
        //查询ERP固定汇率表
        if(StringUtils.isBlank(param.getOriginalCurrency()) || param.getEffectDate() == null){
            return ResponseData.error("币别和日期不能为空");
        }
        param.setEffectDate(DateUtil.beginOfDay(param.getEffectDate()));
        FixedExchangeRate fixedExchangeRate = fixedExchangeRateConsumer.getRateByDateCurrency(param);
        if(fixedExchangeRate == null){
            return ResponseData.error("ERP固定汇率不存在");
        }
        return ResponseData.success(fixedExchangeRate);
    }

}
