package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: EBMS出货清单更新物流信息和物流跟踪状态入参
 * @date: 2022/9/22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EbmsOiwLogisticsParam implements Serializable, BaseValidatingParam {
    private static final long serialVersionUID = 1L;

    /** 入库单号 */
    @ApiModelProperty("入库单号")
    private String inOrder;

    /** 箱号 */
    @ApiModelProperty("箱号")
    private BigDecimal packageNum;

    /** 承运商 */
    @ApiModelProperty("承运商")
    private String logisticsCompany;

    /** 物流单号 */
    @ApiModelProperty("物流单号")
    private String logisticsNum;

    /** 物流跟踪状态：已发货，出口报关中，国外清关中，尾程派送中，已签收，物流完结*/
    @ApiModelProperty("物流跟踪状态：已发货，出口报关中，国外清关中，尾程派送中，已签收，物流完结")
    private String logisticsStatus;

    /** 操作人 */
    @ApiModelProperty("操作人")
    private String operator;
}
