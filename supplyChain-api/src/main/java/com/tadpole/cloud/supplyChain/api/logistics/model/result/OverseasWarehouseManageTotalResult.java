package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 海外仓库存管理数量汇总
 * @date: 2022/10/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OverseasWarehouseManageTotalResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 来货总数量 */
    @ApiModelProperty("来货总数量")
    private BigDecimal comeQuantityTotal;

    /** 账存总数量 */
    @ApiModelProperty("账存总数量")
    private BigDecimal inventoryQuantityTotal;
}
