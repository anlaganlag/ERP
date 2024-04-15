package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 物流实际结算汇总结果类
 * @date: 2022/11/18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsSettlementPageTotalResult implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发货件数")
    private Long shipmentUnit;

    @ApiModelProperty(value = "出仓重量（KG）")
    private BigDecimal weight;

    @ApiModelProperty(value = "出仓体积（CBM）")
    private BigDecimal volume;

    @ApiModelProperty(value = "出仓体积体重（KG）")
    private BigDecimal volumeWeight;

    @ApiModelProperty(value = "发货数量")
    private Long shipmentQuantity;

    @ApiModelProperty(value = "预估物流费（CNY）")
    private BigDecimal predictLogisticsFee;

    @ApiModelProperty(value = "预估燃油附加费（CNY）")
    private BigDecimal predictOilFee;

    @ApiModelProperty(value = "预估旺季附加费（CNY）")
    private BigDecimal predictBusySeasonFee;

    @ApiModelProperty(value = "预估附加费及杂费（CNY）")
    private BigDecimal predictOthersFee;

    @ApiModelProperty(value = "预估报关费（CNY）")
    private BigDecimal predictTariffFee;

    @ApiModelProperty(value = "预估清关费（CNY）")
    private BigDecimal predictClearTariffFee;

    @ApiModelProperty(value = "预估税费（CNY）")
    private BigDecimal predictTaxFee;

    @ApiModelProperty(value = "预估总费用（CNY）")
    private BigDecimal predictTotalFee;

    @ApiModelProperty(value = "总差异费用（CNY）")
    private BigDecimal diffTotalFee;
}
