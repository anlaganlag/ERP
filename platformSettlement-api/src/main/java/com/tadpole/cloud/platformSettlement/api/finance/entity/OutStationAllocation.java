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
* 站外分摊汇总表
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
@TableName("RP_OUT_STATION_ALLOCATION")
@ExcelIgnoreUnannotated
@ColumnWidth(15)
@HeadFontStyle(fontName = "宋体", fontHeightInPoints = 14, bold = true, color = -1)
public class OutStationAllocation implements Serializable {

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

    @TableField("SALES_BRAND")
    @ExcelProperty(value= "销售品牌")
    private String salesBrand;

    @TableField("MATERIAL_CODE")
    @ExcelProperty(value= "物料编码")
    private String materialCode;

    @TableField("PRODUCT_TYPE")
    @ExcelProperty(value= "运营大类")
    private String productType;

    @TableField("COST_AUXILIARY_DESCRIPTION")
    @ExcelProperty(value= "费用类型辅助说明")
    private String costAuxiliaryDescription;

    @TableField("VOLUME_BILL_QUANTITY")
    @ExcelProperty(value= "Volume(测评数量)")
    private BigDecimal volumeBillQuantity;

    @TableField("OTHER_ADVERTISEMENTS")
    @ExcelProperty(value= "其它站外广告")
    private BigDecimal otherAdvertisements;

    @TableField("BRUSHING_VALUE")
    @ExcelProperty(value= "测评货值")
    private BigDecimal brushingValue;

    @TableField("BRUSHING_SERVICE_CHARGE")
    @ExcelProperty(value= "测评手续费")
    private BigDecimal brushingServiceCharge;

    @TableField("LOCAL_LOGISTICS_FEE")
    @ExcelProperty(value= "当地物流费")
    private BigDecimal localLogisticsFee;

    @TableField("OVERSEAS_WAREHOUSE_FEE")
    @ExcelProperty(value= "海外仓费用")
    private BigDecimal overseasWarehouseFee;

    @TableField("DISPOSE_PURCHASE_FEE")
    @ExcelProperty(value= "销毁成本-采购成本")
    private BigDecimal disposePurchaseFee;

    @TableField("DISPOSE_LOGISTICS_FEE")
    @ExcelProperty(value= "销毁成本-头程物流费")
    private BigDecimal disposeLogisticsFee;

    @TableField("DOMESTIC_UNSALABLE_INVENTORY")
    @ExcelProperty(value= "国内滞销库存")
    private BigDecimal domesticUnsalableInventory;

    @TableField("MOLD_OPENING_COST")
    @ExcelProperty(value= "开模费用")
    private BigDecimal moldOpeningCost;

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

    /** 更新人 */
    @TableField("UPDATE_USER")
    private String updateUser;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 原始数据来源：0：系统生成（站外费用月分摊），1：人工生成（导入站外费用分摊） */
    @TableField("DATA_SOURCE")
    private String dataSource;

    @TableId(value = "ID",type= IdType.AUTO)
    private BigDecimal id;

    @TableField(exist = false)
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
    @ExcelProperty(value= "导入错误备注")
    private String uploadRemark;
}