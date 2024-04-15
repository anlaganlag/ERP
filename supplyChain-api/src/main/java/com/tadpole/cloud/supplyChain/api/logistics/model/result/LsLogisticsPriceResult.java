package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: ty
 * @description: EBMS物流商价格
 * @date: 2023/11/21
 */
@Data
public class LsLogisticsPriceResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("计费币种")
    private String busLogpChargCurrency;

    @ApiModelProperty("单价费用")
    private BigDecimal busLogpDetUnitPrice;

    @ApiModelProperty("报关费")
    private BigDecimal busLogpDetCustDlearanceFee;

    @ApiModelProperty("清关费")
    private BigDecimal busLogpDetCustClearanceFee;

    @ApiModelProperty("旺季附加费KG")
    private BigDecimal busLogpDetBusySeasonAddFee;

    @ApiModelProperty("燃油附加税率")
    private BigDecimal busLogpDetFuelFee;

    @ApiModelProperty("附加费及杂费KG")
    private BigDecimal busLogpDetAddAndSundryFee;

    @ApiModelProperty("重量KG(>)")
    private BigDecimal busLogpDetWeightGreater;

    @ApiModelProperty("重量KG(<)")
    private BigDecimal busLogpDetWeightLess;

    @ApiModelProperty("体积CBM(>)")
    private BigDecimal busLogpDetVolumeGreater;

    @ApiModelProperty("体积CBM(<)")
    private BigDecimal busLogpDetVolumeLess;

    @ApiModelProperty("适用开始日期")
    private Date busLogpDetAppStartDate;

    @ApiModelProperty("适用结束日期")
    private Date busLogpDetAppEndDate;

}
