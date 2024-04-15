package com.tadpole.cloud.platformSettlement.api.finance.model.result;

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
public class FixedExchangeRateResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 直接汇率 */
    @ApiModelProperty("DIRECT_RATE")
    private BigDecimal directRate;

    /** 间接汇率 */
    @ApiModelProperty("INDIRECT_RATE")
    private BigDecimal indirectRate;

    /** 汇率类型 */
    @ApiModelProperty("RATE_TYPE")
    private String rateType;

    /** 原币 */
    @ApiModelProperty("ORIGINAL_CURRENCY")
    private String originalCurrency;

    /** 原币 */
    @ApiModelProperty("ORIGINAL_CURRENCY_NAME")
    private String originalCurrencyName;

    /** 目标币 */
    @ApiModelProperty("TARGET_CURRENCY")
    private String targetCurrency;

    /** 目标币 */
    @ApiModelProperty("TARGET_CURRENCY_NAME")
    private String targetCurrencyName;

    /** 生效日期 */
    @ApiModelProperty("EFFECT_DATE")
    private String effectDate;

    /** 失效日期 */
    @ApiModelProperty("INEFFECTIVE_DATE")
    private String ineffectiveDate;

    /** 数据状态 */
    @ApiModelProperty("DATA_STATUS")
    private String dataStatus;

    /** 禁用状态 */
    @ApiModelProperty("FORBIDDEN_STATUS")
    private String forbiddenStatus;

    /** 是否系统预置 */
    @ApiModelProperty("IS_SYSTEM_INITIALIZATION")
    private String isSystemInitialization;

}