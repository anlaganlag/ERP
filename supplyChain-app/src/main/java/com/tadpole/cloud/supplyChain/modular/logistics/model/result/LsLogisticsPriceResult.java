package com.tadpole.cloud.supplyChain.modular.logistics.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
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
    private Double busLogpDetUnitPrice;

    @ApiModelProperty("报关费")
    private Double busLogpDetCustDlearanceFee;

    @ApiModelProperty("清关费")
    private Double busLogpDetCustClearanceFee;

    @ApiModelProperty("旺季附加费KG")
    private Double busLogpDetBusySeasonAddFee;

    @ApiModelProperty("燃油附加税率")
    private Double busLogpDetFuelFee;

    @ApiModelProperty("附加费及杂费KG")
    private Double busLogpDetAddAndSundryFee;

    @ApiModelProperty("重量KG(>)")
    private Double busLogpDetWeightGreater;

    @ApiModelProperty("重量KG(<)")
    private Double busLogpDetWeightLess;

    @ApiModelProperty("体积CBM(>)")
    private Double busLogpDetVolumeGreater;

    @ApiModelProperty("体积CBM(<)")
    private Double busLogpDetVolumeLess;

    @ApiModelProperty("适用开始日期")
    private Date busLogpDetAppStartDate;

    @ApiModelProperty("适用结束日期")
    private Date busLogpDetAppEndDate;

}
