package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ;
 * @author : LSY
 * @date : 2023-12-22
 */
@TableName("LT_LAZADA_SETTLEMENT_REPORT")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LtLazadaSettlementReportExport implements Serializable{
 private static final long serialVersionUID = 1L;

   /**
    * ID
    */
   @ApiModelProperty("ID")
   @TableField("ID")
   private String id;


   @ApiModelProperty("平台")
   @ExcelProperty(value = "平台")
   private String platform;


   /**
    * 账号
    */
   @ApiModelProperty("账号")
   @ExcelProperty(value = "账号")
   @TableField("SHOP_NAME")
   private String shopName;

   /**
    * 站点
    */
   @ApiModelProperty("站点")
   @ExcelProperty(value = "站点")
   @TableField("SITE")
   private String site;


   @ExcelProperty(value = "事业部")
   private String department;

   @ExcelProperty(value = "Team")
   private String team;


   /**
    * period
    */
   @ApiModelProperty("period")
   @ExcelProperty(value = "period")
   @TableField("period")
   private String period;


   /**
    * orderNumber
    */
   @ApiModelProperty("orderNumber")
   @ExcelProperty(value = "orderNumber")
   @TableField("ORDER_NUMBER")
   private String orderNumber;


   /**
    * sellerSku
    */
   @ApiModelProperty("sellerSku")
   @ExcelProperty(value = "sellerSku")
   @TableField("SELLER_SKU")
   private String sellerSku;



   /**
    * 类目
    */
   @ApiModelProperty("类目")
   @ExcelProperty(value = "类目")
   @TableField("CATEGORY")
   private String category;

   /**
    * 运营大类
    */
   @ApiModelProperty("运营大类")
   @ExcelProperty(value = "运营大类")
   @TableField("PRODUCT_TYPE")
   private String productType;

   /**
    * 产品名称
    */
   @ApiModelProperty("产品名称")
   @ExcelProperty(value = "产品名称")
   @TableField("PRODUCT_NAME")
   private String productName;

   /**
    * 款式
    */
   @ApiModelProperty("款式")
   @ExcelProperty(value = "款式")
   @TableField("STYLE")
   private String style;

   /**
    * 主材料
    */
   @ApiModelProperty("主材料")
   @ExcelProperty(value = "主材料")
   @TableField("MAIN_MATERIAL")
   private String mainMaterial;

   /**
    * 图案
    */
   @ApiModelProperty("图案")
   @ExcelProperty(value = "图案")
   @TableField("DESIGN")
   private String design;

   /**
    * 适用品牌或对象
    */
   @ApiModelProperty("适用品牌或对象")
   @ExcelProperty(value = "适用品牌或对象")
   @TableField("FIT_BRAND")
   private String fitBrand;

   /**
    * 型号
    */
   @ApiModelProperty("型号")
   @ExcelProperty(value = "型号")
   @TableField("MODEL")
   private String model;

   /**
    * 公司品牌
    */
   @ApiModelProperty("公司品牌")
   @ExcelProperty(value = "公司品牌")
   @TableField("COMPANY_BRAND")
   private String companyBrand;

   /**
    * 颜色
    */
   @ApiModelProperty("颜色")
   @ExcelProperty(value = "颜色")
   @TableField("COLOR")
   private String color;


   /**
    * 尺码
    */
   @ApiModelProperty("尺码")
   @ExcelProperty(value = "尺码")
   @TableField("SIZES")
   private String sizes;

   /**
    * 版本
    */
   @ApiModelProperty("版本")
   @ExcelProperty(value = "版本")
   @TableField("VERSION")
   private String version;

   /**
    * 包装数量
    */
   @ApiModelProperty("包装数量")
   @ExcelProperty(value = "包装数量")
   @TableField("PACKING")
   private String packing;


   /**
    * 适用机型
    */
   @ApiModelProperty("适用机型")
   @ExcelProperty(value = "适用机型")
   @TableField("TYPE")
   private String type;

   /**
    * 款式二级标签
    */
   @ApiModelProperty("款式二级标签")
   @ExcelProperty(value = "款式二级标签")
   @TableField("STYLE_SECOND_LABEL")
   private String styleSecondLabel;


   /**
    * 销售品牌
    */
   @ApiModelProperty("销售品牌")
   @ExcelProperty(value = "销售品牌")
   @TableField("SALES_BRAND")
   private String salesBrand;

   /**
    * 数量
    */
   @ApiModelProperty("个数")
   @ExcelProperty(value = "个数")
   @TableField("QTY")
   private BigDecimal qty;

   /**
    * Sales_excluding_tax
    */
   @ApiModelProperty("Sales_excluding_tax")
   @ExcelProperty(value = "Sales_excluding_tax")
   @TableField("SALES_EXCLUDING_TAX")
   private BigDecimal salesExcludingTax;

   /**
    * Tax
    */
   @ApiModelProperty("Tax")
   @ExcelProperty(value = "Tax")
   @TableField("TAX")
   private BigDecimal tax;


   /**
    * SALES_PROMOTION
    */
   @ApiModelProperty("SALES_PROMOTION")
   @ExcelProperty(value = "SALES_PROMOTION")
   @TableField("SALES_PROMOTION")
   private BigDecimal salesPromotion;

   /**
    * REFUND
    */
   @ApiModelProperty("REFUND")
   @ExcelProperty(value = "Refund")
   @TableField("REFUND")
   private BigDecimal refund;

   /**
    * ADVERTISING
    */
   @ApiModelProperty("ADVERTISING")
   @ExcelProperty(value = "Advertising")
   @TableField("ADVERTISING")
   private BigDecimal advertising;

   /**
    * REIMBURSEMENT
    */
   @ApiModelProperty("REIMBURSEMENT")
   @ExcelProperty(value = "Reimbursement")
   @TableField("REIMBURSEMENT")
   private BigDecimal reimbursement;


   /**
    * 手续费
    */
   @ApiModelProperty("手续费")
   @ExcelProperty(value = "手续费")
   @TableField("SERVICE_CHARGE")
   private BigDecimal serviceCharge;


   /**
    * 物流费用
    */
   @ApiModelProperty("物流费用")
   @ExcelProperty(value = "物流费用")
   @TableField("LOGISTICS_FEE")
   private BigDecimal logisticsFee;

   /**
    * 联盟费用
    */
   @ApiModelProperty("联盟费用")
   @ExcelProperty(value = "联盟费用")
   @TableField("ALLIANCE_FEE")
   private BigDecimal allianceFee;

   /**
    * Commission
    */
   @ApiModelProperty("Commission")
   @ExcelProperty(value = "Commission")
   @TableField("COMMISSION")
   private BigDecimal commission;

   /**
    * 销毁成本-采购成本
    */
   @ApiModelProperty("销毁成本-采购成本")
   @ExcelProperty(value = "销毁成本-采购成本")
   @TableField("DISPOSE_PURCHASE_FEE")
   private BigDecimal disposePurchaseFee;

   /**
    * 成本-USD
    */
   @ApiModelProperty("成本-USD")
   @ExcelProperty(value = "成本-USD")
   @TableField("COST_USD")
   private BigDecimal costUsd;

   /**
    * 附加成本-USD
    */
   @ApiModelProperty("附加成本-USD")
   @ExcelProperty(value = "附加成本-USD")
   @TableField("ADD_COST_USD")
   private BigDecimal addCostUsd;

  /**
   * 滞销库存利息费
   */
  @ApiModelProperty("滞销库存利息费")
  @ExcelProperty(value = "滞销库存利息费")
  @TableField("DOMESTIC_UNSALABLE_INVENTORY")
  private BigDecimal domesticUnsalableInventory;


   /**
    * profit
    */
   @ApiModelProperty("profit")
   @ExcelProperty(value = "profit")
   @TableField("profit")
   private BigDecimal profit;



   /**
    * 鼓励金
    */
   @ApiModelProperty("鼓励金")
   @ExcelProperty(value = "鼓励金")
   @TableField("INCENTIVE_FUND")
   private BigDecimal incentiveFund;


   /**
    * 备注
    */
   @ApiModelProperty("备注")
   @ExcelProperty(value = "备注")
   @TableField("REMARK")
   private String remark;

   private Date updateTime;
   private String updateBy;
   private Date createTime;
   private String CreateBy;


   @TableField("CONFIRM_STATUS")
   private BigDecimal confirmStatus;

}