package com.tadpole.cloud.supplyChain.modular.logistics.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsK3PaymentApply;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsK3PaymentApplyDetail;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.LsK3PaymentApplyParam;

import java.util.List;

/**
 * <p>
 * 物流费K3付款申请单 服务类
 * </p>
 *
 * @author ty
 * @since 2023-12-05
 */
public interface ILsK3PaymentApplyService extends IService<LsK3PaymentApply> {

    /**
     * 查询
     * @param param
     * @return
     */
    ResponseData queryPaymentApply(LsK3PaymentApplyParam param);

    /**
     * 是否可以编辑K3付款申请单
     * @param param
     * @return
     */
    ResponseData canEditPaymentApply(LsK3PaymentApplyParam param);

    /**
     * K3物流费申请保存
     * @param k3Apply K3物流费单据头
     * @param applyDetailList K3物流费单明细
     * @return
     */
    ResponseData k3Save(LsK3PaymentApply k3Apply, List<LsK3PaymentApplyDetail> applyDetailList);

    /**
     * 删除
     * @param param
     * @return
     */
    ResponseData delPaymentApply(LsK3PaymentApplyParam param);

}
