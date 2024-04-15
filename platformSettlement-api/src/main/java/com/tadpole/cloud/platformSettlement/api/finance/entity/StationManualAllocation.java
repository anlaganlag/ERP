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
* 站内手工分摊表
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
@TableName("RP_STATION_MANUAL_ALLOCATION")
@ExcelIgnoreUnannotated
@ColumnWidth(15)
@HeadFontStyle(fontName = "宋体", fontHeightInPoints = 14, bold = true, color = -1)
public class StationManualAllocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("ALLOC_ID")
    @ExcelProperty(value= "父ID")
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

    @TableField("PRODUCT_TYPE")
    @ExcelProperty(value= "运营大类")
    private String productType;

    /** 原币 */
    @TableField("ACCOUNTING_CURRENCY")
    @ExcelProperty(value= "核算币种")
    private String accountingCurrency;

    @TableField("SALES_BRAND")
    @ExcelProperty(value= "销售品牌")
    private String salesBrand;

    /** advertising */
    @TableField("ADVERTISING")
    @ExcelProperty(value= "advertising")
    private BigDecimal advertising;

    /** storage_fee */
    @TableField("STORAGE_FEE")
    @ExcelProperty(value= "Storage Fee")
    private BigDecimal storageFee;

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

    /** Storage Fee原值 */
    @TableField("STORAGE_FEE_ORI")
    private BigDecimal storageFeeOri;



    /** 仓储费分摊比率 */
    @TableField("STORAGE_FEE_ALLOC_RATE")
    private BigDecimal storageFeeAllocRate;



    /** 销毁费 */
    @TableField("DISPOSE_FEE")
    private BigDecimal disposeFee;



    /** 移除费 */
    @TableField("REMOVAL_DEAL")
    private BigDecimal removalDeal;



    @TableField(exist = false)
    private String siteDimension;


    @TableField(exist = false)
    private String skuDimension;





    @TableField(exist = false)
    private String skuSiteDimension;


    @TableField(exist = false)
    private BigDecimal monFee;


    @TableField(exist = false)
    private BigDecimal LongFee;

}

