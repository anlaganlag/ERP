package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author Amte Ma<br>
 * @version 1.0<br>
 * @date 2022/12/20 <br>
 */
@Data
public class AmazonInventoryTotal {

    @ApiModelProperty("期初数量-期初数量合计")
    private Long openingTotal;

    @ApiModelProperty("期初数量在途数量")
    private Long openingInTransit;

    @ApiModelProperty("期初数量物流待发数量")
    private Long openingLogistics;

    @ApiModelProperty("期初数量在库数量")
    private Long openingInStock;

    @ApiModelProperty("入库数量-入库数量合计")
    private Long inboundTotal;

    @ApiModelProperty("入库数量- 国内发FBA")
    private Long inboundDomesticFba;

    @ApiModelProperty("入库数量-欧洲站点调拨")
    private Long inboundEuTransfer;

    @ApiModelProperty("入库数量-海外仓发FBA")
    private Long inboundOwToFba;

    @ApiModelProperty("出库数量-出库数量合计")
    private Long outgoingTotal;

    @ApiModelProperty("出库数量-当月销售数量")
    private Long outgoingMonthlySales;

    @ApiModelProperty("出库数量-当月退货数量")
    private Long outgoingMonthlyReturned;

    @ApiModelProperty("出库数量-当月销毁的数量")
    private Long outgoingMonthlyDestoryed;

    @ApiModelProperty("出库数量-当月移除的数量")
    private Long outgoingMonthlyRemoval;

    @ApiModelProperty("出库数量-Amazon库存调增")
    private Long outgoingInventoryIncrease;

    @ApiModelProperty("出库数量-Amazon库存调减")
    private Long outgoingInventoryReduction;

    @ApiModelProperty("期末数量账面数")
    private Long closingQuantityBook;

    @ApiModelProperty("期末数量-店铺库存数-店铺库存数合计")
    private Long closingStoreInventoryTotal;

    @ApiModelProperty("期末数量-店铺库存数-在途数量")
    private Long closingStoreInventoryInTransit;

    @ApiModelProperty("期末数量-店铺库存数-物流待发数量")
    private Long closingStoreInventoryLogistics;

    @ApiModelProperty("期末数量-店铺库存数-在库数量")
    private Long closingStoreInventoryInStock;

    @ApiModelProperty("盘盈盘亏数量-盘盈数量")
    private Long inventorySurplus;

    @ApiModelProperty("盘盈盘亏数量-盘亏数量")
    private Long inventoryLoss;
}
