package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* <p>
* 新核算库存平均单价表
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
public class ManualAllocationAdjustParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ALLOC_ID")
    private BigDecimal allocId;

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

    @ApiModelProperty("PRODUCT_TYPE")
    private String productType;

    @ApiModelProperty("VOLUME_NORMAL")
    private String volumeNormal;

    @ApiModelProperty("VOLUME_REISSUE")
    private String volumeReissue;

    /** sales_total */
    @ApiModelProperty("SALES_EXCLUDING_TAX")
    private BigDecimal salesExcludingTax;

    @ApiModelProperty("TAX")
    private BigDecimal tax;

    /** sales_promotion */
    @ApiModelProperty("SALES_PROMOTION")
    private BigDecimal salesPromotion;

    /** refund_total */
    @ApiModelProperty("REFUND")
    private BigDecimal refund;

    @ApiModelProperty("REFUND_PROMOTION")
    private BigDecimal refundPromotion;

    /** giftwarp_shipping */
    @ApiModelProperty("GIFTWARP_SHIPPING")
    private BigDecimal giftwarpShipping;

    /** commission_total */
    @ApiModelProperty("COMMISSION")
    private BigDecimal commission;

    @ApiModelProperty("REFUND_COMMISSION")
    private BigDecimal refundCommission;

    /** goodwill */
    @ApiModelProperty("GOODWILL")
    private BigDecimal goodwill;

    /** amazon_fee_total非小平台 */
    @ApiModelProperty("AMAZON_FEES")
    private BigDecimal amazonFees;

    /** storage_fee */
    @ApiModelProperty("STORAGE_FEE")
    private BigDecimal storageFee;

    /** reimbursement */
    @ApiModelProperty("REIMBURSEMENT")
    private BigDecimal reimbursement;

    /** other */
    @ApiModelProperty("OTHER")
    private BigDecimal other;

    /** withheld_tax */
    @ApiModelProperty("WITHHELD_TAX")
    private BigDecimal withheldTax;

    /** lightning_deal */
    @ApiModelProperty("REMOVAL_DEAL")
    private BigDecimal removalDeal;

    @ApiModelProperty("DISPOSE_FEE")
    private BigDecimal disposeFee;

    /** advertising */
    @ApiModelProperty("ADVERTISING")
    private BigDecimal advertising;

    @ApiModelProperty("CONFIRM_DATE")
    private Date confirmDate;

    @ApiModelProperty("CONFIRM_BY")
    private String confirmBy;

    @ApiModelProperty("CONFIRM_STATUS")
    private String confirmStatus;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty("CREATE_AT")
    private Date createAt;

    @ApiModelProperty("ID")
    private BigDecimal id;

    private List sites;
}