package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import com.tadpole.cloud.supplyChain.api.logistics.entity.LsK3PaymentApply;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsK3PaymentApplyDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: ty
 * @description: 物流费付款申请入参
 * @date: 2023/12/5
 */
@Data
public class LsPaymentApplyParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 物流费付款主数据ID集合 */
    @ApiModelProperty("物流费付款主数据ID集合")
    List<BigDecimal> idList;

    /** K3付款申请单据头 */
    @ApiModelProperty("K3付款申请单据头")
    private LsK3PaymentApply apply;

    /** K3付款申请单据明细 */
    @ApiModelProperty("K3付款申请单据明细")
    private List<LsK3PaymentApplyDetail> applyDetailList;

}
