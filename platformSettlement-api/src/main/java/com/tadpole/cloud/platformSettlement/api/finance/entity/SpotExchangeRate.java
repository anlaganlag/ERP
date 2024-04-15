package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* ERP即期汇率
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RP_SPOT_EXCHANGE_RATE")
public class SpotExchangeRate implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value ="ID",type = IdType.AUTO)
    private Integer id;

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
    private Date effectDate;

    /** 失效日期 */
    @TableField("INEFFECTIVE_DATE")
    private Date ineffectiveDate;

    /** 数据状态 */
    @TableField("DATA_STATUS")
    private String dataStatus;

    /** 禁用状态 */
    @TableField("FORBIDDEN_STATUS")
    private String forbiddenStatus;

    /** 是否系统预置 */
    @TableField("IS_SYSTEM_INITIALIZATION")
    private BigDecimal isSystemInitialization;

}