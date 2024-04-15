package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 海外仓入库管理数量汇总
 * @date: 2022/10/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OverseasInWarehouseTotalResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 应入库总数量 */
    @ApiModelProperty("应入库总数量")
    private BigDecimal shouldInventoryTotal;

    /** 已到货总数量 */
    @ApiModelProperty("已到货总数量")
    private BigDecimal alreadyInventoryTotal;

    /** 未到货总数量 */
    @ApiModelProperty("未到货总数量")
    private BigDecimal notInventoryTotal;

    /** 签收总数量 */
    @ApiModelProperty("签收总数量")
    private BigDecimal signTotal;
}
