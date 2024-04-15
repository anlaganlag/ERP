package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
* <p>
* 收入记录表
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
@TableName("RP_SETTLEMENT_DETAIL_LISTING")
@ExcelIgnoreUnannotated
public class SettlementDetailListing implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @ExcelProperty("ID")
    @TableId(value="ID",type= IdType.AUTO)
    private BigDecimal id;

    /** 会计期间 */
    @TableField("FISCAL_PERIOD")
    private String fiscalPeriod;

    /** 开始日期 */
    @TableField("START_TIME")
    private Date startTime;

    /** 结束日期 */
    @TableField("END_TIME")
    private Date endTime;

    /** 结算id */
    @TableField("SETTLEMENT_ID")
    private String settlementId;

    /** 报告类型 */
    @TableField("REPORT_TYPE")
    private String reportType;

    /** 收入确认类型 */
    @TableField("INCOME_TYPE")
    private String incomeType;

    /** 平台 */
    @TableField("PLATFORM_NAME")
    private String platformName;

    /** 账号 */
    @TableField("SHOP_NAME")
    private String shopName;

    /** 站点 */
    @TableField("SITE")
    private String site;

    /** 原币 */
    @TableField("ORIGINAL_CURRENCY")
    private String originalCurrency;

    @ExcelProperty("SKU")
    @TableField("SKU")
    private String sku;

    @TableField("DEPARTMENT")
    private String department;

    @TableField("TEAM")
    private String team;

    @TableField("MATERIAL_CODE")
    private String materialCode;

    @TableField("SALES_BRAND")
    private String salesBrand;

    @TableField("VOLUME_NORMAL")
    private BigDecimal volumeNormal;

    @TableField("VOLUME_REISSUE")
    private BigDecimal volumeReissue;

    /** sales_total */
    @TableField("SALES_EXCLUDING_TAX")
    private BigDecimal salesExcludingTax;

    @TableField("TAX")
    private BigDecimal tax;

    /** sales_promotion */
    @TableField("SALES_PROMOTION")
    private BigDecimal salesPromotion;

    /** refund_total */
    @TableField("REFUND")
    private BigDecimal refund;

    @TableField("REFUND_PROMOTION")
    private BigDecimal refundPromotion;

    /** giftwarp_shipping */
    @TableField("GIFTWARP_SHIPPING")
    private BigDecimal giftwarpShipping;

    /** commission_total */
    @TableField("COMMISSION")
    private BigDecimal commission;

    @TableField("REFUND_COMMISSION")
    private BigDecimal refundCommission;

    /** goodwill */
    @TableField("GOODWILL")
    private BigDecimal goodwill;

    /** amazon_fee_total非小平台 */
    @TableField("AMAZON_FEES")
    private BigDecimal amazonFees;

    /** storage_fee */
    @TableField("STORAGE_FEE")
    private BigDecimal storageFee;

    /** reimbursement */
    @TableField("REIMBURSEMENT")
    private BigDecimal reimbursement;

    /** other */
    @TableField("OTHER")
    private BigDecimal other;

    /** withheld_tax */
    @TableField("WITHHELD_TAX")
    private BigDecimal withheldTax;

    /** lightning_deal */
    @TableField("REMOVAL_DEAL")
    private BigDecimal removalDeal;

    @TableField("DISPOSE_FEE")
    private BigDecimal disposeFee;

    /** advertising */
    @TableField("ADVERTISING")
    private BigDecimal advertising;

    @TableField("CONFIRM_DATE")
    private LocalDateTime confirmDate;

    @TableField("CONFIRM_BY")
    private String confirmBy;

    @TableField("CONFIRM_STATUS")
    private BigDecimal confirmStatus;

    /** 创建人 */
    @TableField("CREATE_BY")
    private String createBy;

    /** 创建时间 */
    @TableField("CREATE_AT")
    private LocalDateTime createAt;

    /** 最晚时间 */
    @TableField("LATEST_DATE")
    private LocalDateTime latestDate;

}