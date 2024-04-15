package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 亚马逊物流索赔;
 *
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "亚马逊物流索赔", description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LogisticsAmazonClaimsEmailViewModel implements Serializable {
    private static final long serialVersionUID = 1L;


    // 货运方式1
    @ApiModelProperty(value = "货运方式1")
    public String busLogTraMode1;


    // planName
    @ApiModelProperty(value = "planName")
    public String planName;


    // 物流单号
    @ApiModelProperty(value = "物流单号")
    public String busLhrOddNumb;


    // SKU
    @ApiModelProperty(value = "SKU")
    public String busSKU;


    // FNSKU
    @ApiModelProperty(value = "FNSKU")
    public String fnsku;


    // 接收差异
    @ApiModelProperty(value = "接收差异")
    public Integer busDiscrepancy;


}