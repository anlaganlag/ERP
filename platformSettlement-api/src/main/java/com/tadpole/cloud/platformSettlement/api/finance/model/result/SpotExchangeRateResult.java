package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
* <p>
* ERP固定汇率
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@ApiModel
public class SpotExchangeRateResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 直接汇率 */
    @ApiModelProperty("DIRECT_RATE")
    @ExcelProperty(value= "直接汇率")
    private BigDecimal directRate;

    /** 间接汇率 */
    @ApiModelProperty("INDIRECT_RATE")
    @ExcelProperty(value= "间接汇率")
    private BigDecimal indirectRate;

    /** 汇率类型 */
    @ApiModelProperty("RATE_TYPE")
    @ExcelProperty(value= "汇率类型")
    private String rateType;

    /** 原币 */
    @ApiModelProperty("ORIGINAL_CURRENCY")
    @ExcelProperty(value= "原币")
    private String originalCurrency;

    /** 原币 */
    @ApiModelProperty("ORIGINAL_CURRENCY_NAME")
    @ExcelProperty(value= "原币")
    private String originalCurrencyName;

    /** 目标币 */
    @ApiModelProperty("TARGET_CURRENCY")
    @ExcelProperty(value= "目标币")
    private String targetCurrency;

    /** 目标币 */
    @ApiModelProperty("TARGET_CURRENCY_NAME")
    @ExcelProperty(value= "目标币")
    private String targetCurrencyName;

    /** 生效日期 */
    @ApiModelProperty("EFFECT_DATE")
    @ExcelProperty(value= "生效日期")
    private String effectDate;

    /** 失效日期 */
    @ApiModelProperty("INEFFECTIVE_DATE")
    @ExcelProperty(value= "失效日期")
    private String ineffectiveDate;

    /** 数据状态 */
    @ApiModelProperty("DATA_STATUS")
    @ExcelProperty(value= "数据状态")
    private String dataStatus;

    /** 禁用状态 */
    @ApiModelProperty("FORBIDDEN_STATUS")
    @ExcelProperty(value= "禁用状态")
    private String forbiddenStatus;

    /** 是否系统预置 */
    @ApiModelProperty("IS_SYSTEM_INITIALIZATION")
    @ExcelProperty(value= "是否系统预置")
    private String isSystemInitialization;

}