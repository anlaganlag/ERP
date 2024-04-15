package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
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
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("fixed_exchange_rate_mcms")
public class FixedExchangeRateErp implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 直接汇率 */
    @TableField("DIRECT_RATE")
    private BigDecimal directRate;

    /** 间接汇率 */
    @TableField("INDIRECT_RATE")
    private BigDecimal indirectRate;

    /** 汇率类型 */
    @TableField("RATE_TYPE")
    private String rateType;

    /** 原币 */
    @TableField("ORIGINAL_CURRENCY")
    private String originalCurrency;

    /** 目标币 */
    @TableField("TARGET_CURRENCY")
    private String targetCurrency;

    /** 原币 */
    @ApiModelProperty("ORIGINAL_CURRENCY_NAME")
    private String originalCurrencyName;

    /** 目标币 */
    @ApiModelProperty("TARGET_CURRENCY_NAME")
    private String targetCurrencyName;

    /** 生效日期 */
    @TableField("EFFECT_DATE")
    private String effectDate;

    /** 失效日期 */
    @TableField("INEFFECTIVE_DATE")
    private String ineffectiveDate;

    /** 数据状态 */
    @TableField("DATA_STATUS")
    private String dataStatus;

    /** 禁用状态 */
    @TableField("FORBIDDEN_STATUS")
    private String forbiddenStatus;


}