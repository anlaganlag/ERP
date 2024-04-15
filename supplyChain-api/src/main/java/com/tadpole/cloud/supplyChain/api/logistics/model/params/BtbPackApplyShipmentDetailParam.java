package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: ty
 * @description: BTB订单申请发货明细入参
 * @date: 2023/11/21
 */
@Data
public class BtbPackApplyShipmentDetailParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /** BTB订单发货主表ID */
    @ApiModelProperty("BTB订单发货主表ID")
    private BigDecimal orderID;

    /** BTB订单发货明细集合 */
    @ApiModelProperty("BTB订单发货明细集合")
    private List<BigDecimal> orderDetailIDList;

}
