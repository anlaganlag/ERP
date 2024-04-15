package com.tadpole.cloud.platformSettlement.modular.vat.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 基础信息-汇率表
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("BASE_EXCHANGE_RATE")
public class BaseExchangeRateResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 汇率id--自增 */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 汇率期间 */
    @ApiModelProperty("EXCHANGE_RATE_PERIOD")
    private String exchangeRatePeriod;

    /** 汇率日期 */
    @ExcelProperty("申请日期")
    @DateTimeFormat("yyyy-MM-dd")
    @ApiModelProperty("EXCHANGE_RATE_DATE")
    private Date exchangeRateDate;

    /** 目标币种 */
    @ExcelProperty("目标币种")
    @ApiModelProperty("TARGET_CURRENCY")
    private String targetCurrency;

    /** 原币币种 */
    @ExcelProperty("原币币种")
    @ApiModelProperty("ORIGINAL_CURRENCY")
    private String originalCurrency;

    /** 汇率 */
    @ExcelProperty("汇率")
    @ApiModelProperty("EXCHANGE_RATE")
    private BigDecimal exchangeRate;

    /** 版本号 */
    @ApiModelProperty("VERSION_NUMBER")
    private BigDecimal versionNumber;

    /** 期间 */
    @ApiModelProperty("期间")
    private String activityPeriod;
}