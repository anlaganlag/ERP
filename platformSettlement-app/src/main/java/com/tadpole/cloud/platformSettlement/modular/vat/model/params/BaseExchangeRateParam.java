package com.tadpole.cloud.platformSettlement.modular.vat.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
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
@TableName("BASE_EXCHANGE_RATE")
public class BaseExchangeRateParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 汇率id--自增 */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 汇率期间*/
    @ApiModelProperty("EXCHANGE_RATE_PERIOD")
    private String exchangeRatePeriod;

    /** 汇率日期 */
    @ApiModelProperty("EXCHANGE_RATE_DATE")
    private Date exchangeRateDate;

    /** 目标币种 */
    @ApiModelProperty("TARGET_CURRENCY")
    private String targetCurrency;

    /** 原币币种 */
    @ApiModelProperty("ORIGINAL_CURRENCY")
    private String originalCurrency;

    /** 汇率 */
    @ApiModelProperty("EXCHANGE_RATE")
    private BigDecimal exchangeRate;

    /** 汇率日期查询 2022-08 */
    @ApiModelProperty("汇率日期查询")
    private String exchangeRateDateM;

}