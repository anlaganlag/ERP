package com.tadpole.cloud.platformSettlement.api.finance.entity;

import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import lombok.*;

/**
* <p>
* 补贴汇总表
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
@TableName("RP_SUBSIDY_SUMMARY")
@ExcelIgnoreUnannotated
@ColumnWidth(15)
@HeadFontStyle(fontName = "宋体", fontHeightInPoints = 14, bold = true, color = -1)
public class SubsidySummary implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @TableField("SKU")
    @ExcelProperty(value= "SKU")
    private String sku;

    @TableField("DEPARTMENT")
    @ExcelProperty(value= "事业部")
    private String department;

    @TableField("TEAM")
    @ExcelProperty(value= "team")
    private String team;

    @TableField("MATERIAL_CODE")
    @ExcelProperty(value= "物料编码")
    private String materialCode;

    @TableField("SALES_BRAND")
    @ExcelProperty(value= "销售品牌")
    private String salesBrand;

    /** advertising */
    @TableField("COST_AUXILIARY_DESCRIPTION")
    @ExcelProperty(value= "费用类型辅助说明")
    private String costAuxiliaryDescription;

    /** storage_fee */
    @TableField("SUBSIDY_TYPE")
    @ExcelProperty(value= "补贴类型")
    private String subsidyType;

    @TableField("AMOUNT")
    @ExcelProperty(value= "金额")
    private BigDecimal amount;

    @TableField("CONFIRM_DATE")
    private Date confirmDate;

    @TableField("CONFIRM_BY")
    private String confirmBy;

    @TableField("CONFIRM_STATUS")
    private BigDecimal confirmStatus;

    @TableId(value = "ID",type = IdType.AUTO)
    private BigDecimal id;

    @TableField(exist = false)
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
    @ExcelProperty(value= "导入错误备注")
    private String uploadRemark;
}