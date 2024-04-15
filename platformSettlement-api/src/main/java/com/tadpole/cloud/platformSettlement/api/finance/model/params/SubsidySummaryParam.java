package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* <p>
* 补贴汇总表
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Data
@ApiModel
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubsidySummaryParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 会计期间 */
    @ApiModelProperty("FISCAL_PERIOD")
    private String fiscalPeriod;

    /** 报告类型 */
    @ApiModelProperty("REPORT_TYPE")
    private String reportType;

    /** 收入确认类型 */
    @ApiModelProperty("INCOME_TYPE")
    private String incomeType;

    /** 账号 */
    @ApiModelProperty("SHOP_NAME")
    private String shopName;

    /** 站点 */
    @ApiModelProperty("SITE")
    private String site;

    /** 原币 */
    @ApiModelProperty("ACCOUNTING_CURRENCY")
    private String accountingCurrency;

    @ApiModelProperty("SKU")
    private String sku;

    @ApiModelProperty("DEPARTMENT")
    private String department;

    @ApiModelProperty("TEAM")
    private String team;

    @ApiModelProperty("MATERIAL_CODE")
    private String materialCode;

    @ApiModelProperty("SALES_BRAND")
    private String salesBrand;

    /** advertising */
    @ApiModelProperty("COST_AUXILIARY_DESCRIPTION")
    private String costAuxiliaryDescription;

    /** storage_fee */
    @ApiModelProperty("SUBSIDY_TYPE")
    private String subsidyType;

    @ApiModelProperty("AMOUNT")
    private BigDecimal amount;

    @ApiModelProperty("CONFIRM_DATE")
    private Date confirmDate;

    @ApiModelProperty("CONFIRM_BY")
    private String confirmBy;

    @ApiModelProperty("CONFIRM_STATUS")
    private String confirmStatus;

    @ApiModelProperty("ID")
    private BigDecimal id;

    private List skus;

    private List sites;

}