package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* 手动分摊调整表
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
@TableName("RP_MANUAL_ALLOCATION_ADJUST")
@ExcelIgnoreUnannotated
@ColumnWidth(15)
@HeadFontStyle(fontName = "宋体", fontHeightInPoints = 14, bold = true, color = -1)
public class ManualAllocationAdjust implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("ALLOC_ID")
    @ExcelProperty(value= "父id")
    private BigDecimal allocId;

    /** 会计期间 */
    @TableField("FISCAL_PERIOD")
    @ExcelProperty(value= "会计期间")
    private String fiscalPeriod;

    /** 报告类型 */
    @TableField("REPORT_TYPE")
    @ExcelProperty(value= "报告类型")
    private String reportType;

    /** 收入确认类型 */
    @TableField("INCOME_TYPE")
    @ExcelProperty(value= "收入确认类型")
    private String incomeType;

    /** 账号 */
    @TableField("SHOP_NAME")
    @ExcelProperty(value= "账号")
    private String shopName;

    /** 站点 */
    @TableField("SITE")
    @ExcelProperty(value= "站点")
    private String site;

    /** 原币 */
    @TableField("ACCOUNTING_CURRENCY")
    @ExcelProperty(value= "核算币种")
    private String accountingCurrency;

    @TableField("DEPARTMENT")
    @ExcelProperty(value= "事业部")
    private String department;

    @TableField("TEAM")
    @ExcelProperty(value= "team")
    private String team;

    @TableField("PRODUCT_TYPE")
    @ExcelProperty(value= "运营大类")
    private String productType;

    @TableField("VOLUME_NORMAL")
    @ExcelProperty(value= "Volume(正常发货)")
    private String volumeNormal;

    @TableField("VOLUME_REISSUE")
    @ExcelProperty(value= "Volume(补发货)")
    private String volumeReissue;

    /** sales_total */
    @TableField("SALES_EXCLUDING_TAX")
    @ExcelProperty(value= "Sales_excluding_tax")
    private BigDecimal salesExcludingTax;

    @TableField("TAX")
    @ExcelProperty(value= "tax")
    private BigDecimal tax;

    /** sales_promotion */
    @TableField("SALES_PROMOTION")
    @ExcelProperty(value= "Sales Promotion")
    private BigDecimal salesPromotion;

    /** refund_total */
    @TableField("REFUND")
    @ExcelProperty(value= "Refund")
    private BigDecimal refund;

    @TableField("REFUND_PROMOTION")
    @ExcelProperty(value= "Refund Promotion")
    private BigDecimal refundPromotion;

    /** giftwarp_shipping */
    @TableField("GIFTWARP_SHIPPING")
    @ExcelProperty(value= "Giftwrap_Shipping")
    private BigDecimal giftwarpShipping;

    /** commission_total */
    @TableField("COMMISSION")
    @ExcelProperty(value= "Commission")
    private BigDecimal commission;

    @TableField("REFUND_COMMISSION")
    @ExcelProperty(value= "refund commission")
    private BigDecimal refundCommission;

    /** goodwill */
    @TableField("GOODWILL")
    @ExcelProperty(value= "goodwill")
    private BigDecimal goodwill;

    /** amazon_fee_total非小平台 */
    @TableField("AMAZON_FEES")
    @ExcelProperty(value= "amazon fees")
    private BigDecimal amazonFees;

    /** storage_fee */
    @TableField("STORAGE_FEE")
    @ExcelProperty(value= "storage fee")
    private BigDecimal storageFee;

    /** reimbursement */
    @TableField("REIMBURSEMENT")
    @ExcelProperty(value= "reimbursement")
    private BigDecimal reimbursement;

    /** other */
    @TableField("OTHER")
    @ExcelProperty(value= "other")
    private BigDecimal other;

    /** withheld_tax */
    @TableField("WITHHELD_TAX")
    @ExcelProperty(value= "withheld tax")
    private BigDecimal withheldTax;

    /** lightning_deal */
    @TableField("REMOVAL_DEAL")
    @ExcelProperty(value= "removal deal")
    private BigDecimal removalDeal;

    @TableField("DISPOSE_FEE")
    @ExcelProperty(value= "dispose fee")
    private BigDecimal disposeFee;

    /** advertising */
    @TableField("ADVERTISING")
    @ExcelProperty(value= "advertising")
    private BigDecimal advertising;

    @TableField("CONFIRM_DATE")
    private Date confirmDate;

    @TableField("CONFIRM_BY")
    private String confirmBy;

    @TableField("CONFIRM_STATUS")
    private BigDecimal confirmStatus;

    /** 创建人 */
    @TableField("CREATE_BY")
    private String createBy;

    /** 创建时间 */
    @TableField("CREATE_AT")
    private Date createAt;

    @TableId(value="ID",type = IdType.AUTO)
    private BigDecimal id;

    @TableField(exist = false)
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
    @ExcelProperty(value= "导入错误备注")
    private String uploadRemark;
}