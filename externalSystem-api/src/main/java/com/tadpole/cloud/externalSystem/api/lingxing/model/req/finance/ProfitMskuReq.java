package com.tadpole.cloud.externalSystem.api.lingxing.model.req.finance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: ty
 * @description: 查询利润报表 - MSKU请求参数
 * @date: 2022/4/25
 */
@Data
@ApiModel(value="查询利润报表 - MSKU请求参数")
public class ProfitMskuReq {

    @NotNull
    @ApiModelProperty("币种 :1：CNY 2：USD 3：EUR 4：JPY 5：AUD 6：CAD 7：MXN 8：GBP 9：INR 10：AED 11：SGD 12：SAR 13：BRL 14：SEK 15：PLN 16：TRY")
    private Integer currency_type;

    @NotNull
    @ApiModelProperty("店铺")
    private String sids;

    @NotNull
    @ApiModelProperty("月份，如2021-05")
    private String month;

    @NotNull
    @ApiModelProperty("拉取数据偏移量 默认：0")
    private String offset;

    @NotNull
    @ApiModelProperty("一页拉取的数据量 默认：20条")
    private String length;
}
