package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 销毁移除成本月分摊费用汇总结果类
 * @date: 2022/5/23
 */
@Data
public class RemovalShipmentTotalCost {
    /** 总分摊额销毁成本-采购成本 */
    @ApiModelProperty("总分摊额销毁成本-采购成本")
//    @ExcelProperty(value ="总分摊额销毁成本-采购成本")
    private BigDecimal allSharePurchaseCost;

    /** 总分摊额销毁成本-头程物流成本 */
    @ApiModelProperty("总分摊额销毁成本-头程物流成本")
//    @ExcelProperty(value ="总分摊额销毁成本-头程物流成本")
    private BigDecimal allShareLogisticsCost;

    /** 累计分摊额销毁成本-采购成本 */
    @ApiModelProperty("累计分摊额销毁成本-采购成本")
//    @ExcelProperty(value ="累计分摊额销毁成本-采购成本")
    private BigDecimal alreadySharePurchaseCost;

    /** 累计分摊额销毁成本-头程物流成本 */
    @ApiModelProperty("累计分摊额销毁成本-头程物流成本")
//    @ExcelProperty(value ="累计分摊额销毁成本-头程物流成本")
    private BigDecimal alreadyShareLogisticsCost;

    /** 剩余分摊额销毁成本-采购成本 */
    @ApiModelProperty("剩余分摊额销毁成本-采购成本")
//    @ExcelProperty(value ="剩余分摊额销毁成本-采购成本")
    private BigDecimal notSharePurchaseCost;

    /** 剩余分摊额销毁成本-头程物流成本 */
    @ApiModelProperty("剩余分摊额销毁成本-头程物流成本")
//    @ExcelProperty(value ="剩余分摊额销毁成本-头程物流成本")
    private BigDecimal notShareLogisticsCost;

    /** 月摊额销毁成本-采购成本 */
    @ApiModelProperty("月摊额销毁成本-采购成本")
//    @ExcelProperty(value ="月摊额销毁成本-采购成本")
    private BigDecimal monthlySharePurchaseCost;

    /** 月摊额销毁成本-头程物流成本 */
    @ApiModelProperty("月摊额销毁成本-头程物流成本")
//    @ExcelProperty(value ="月摊额销毁成本-头程物流成本")
    private BigDecimal monthlyShareLogisticsCost;

    /** 本期摊额销毁成本-采购成本 */
    @ApiModelProperty("本期摊额销毁成本-采购成本")
//    @ExcelProperty(value ="本期摊额销毁成本-采购成本")
    private BigDecimal nowSharePurchaseCost;

    /** 本期摊额销毁成本-头程物流成本 */
    @ApiModelProperty("本期摊额销毁成本-头程物流成本")
//    @ExcelProperty(value ="本期摊额销毁成本-头程物流成本")
    private BigDecimal nowShareLogisticsCost;

    /** 销毁数量 */
    @ApiModelProperty("销毁数量")
//    @ExcelProperty(value ="销毁数量")
    private BigDecimal shippedQuantity;
}
