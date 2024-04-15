package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsFeePayment;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsCompanyBankParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsLogisticsFeePaymentParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsPaymentApplyParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.LsLogisticsFeePaymentResult;

import java.util.List;

/**
 * <p>
 * 物流费付款 服务类
 * </p>
 *
 * @author ty
 * @since 2023-12-01
 */
public interface ILsLogisticsFeePaymentService extends IService<LsLogisticsFeePayment> {

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    ResponseData queryPage(LsLogisticsFeePaymentParam param);

    /**
     * 获取最新的物流付费编号
     * @return
     */
    String getNowLogisticsFeeNo();

    /**
     * 获取物流费付款最新的K3单据编号
     * @return
     */
    String getNowLogisticsPaymentNo();

    /**
     * 删除
     * @param param
     * @return
     */
    ResponseData del(LsLogisticsFeePaymentParam param);

    /**
     * 导出
     * @param param
     * @return
     */
    List<LsLogisticsFeePaymentResult> export(LsLogisticsFeePaymentParam param);

    /**
     * 是否有足够的物流商押金或预付款
     * @param param
     * @return
     */
    ResponseData hasEnoughPrepayment(LsPaymentApplyParam param);

    /**
     * K3付款申请单明细对应的物流对账单号
     * @param param
     * @return
     */
    ResponseData logisticsCheckOrder(LsPaymentApplyParam param);

    /**
     * K3付款申请单
     * @param param
     * @return
     */
    ResponseData paymentApply(LsPaymentApplyParam param);

    /**
     * K3付款申请单编辑
     * @param param
     * @return
     */
    ResponseData paymentApplyEdit(LsPaymentApplyParam param);

    /**
     * 同步K3付款申请单
     * @param param
     * @return
     */
    ResponseData syncK3PaymentApply(LsLogisticsFeePaymentParam param);

    /**
     * 组织下拉
     * @return
     */
    ResponseData orgSelect();

    /**
     * 付款类型下拉
     * @return
     */
    ResponseData payTypeSelect();

    /**
     * 是否预付下拉
     * @param payType
     * @return
     */
    ResponseData isPrePaySelect(String payType);

    /**
     * 结算方式下拉
     * @return
     */
    ResponseData settlementTypeSelect();

    /**
     * 付款用途下拉
     * @return
     */
    ResponseData useTypeSelect();

    /**
     * 物流收款单位下拉
     * @return
     */
    ResponseData lsCompanySelect();

    /**
     * 内部供应商下拉
     * @return
     */
    ResponseData supplierNameSelect();

    /**
     * 对方银行信息下拉
     * @return
     */
    ResponseData lsCompanyBankSelect(LsCompanyBankParam param);

    /**
     * K3币别下拉
     * @return
     */
    ResponseData k3CurrencySelect();

}
