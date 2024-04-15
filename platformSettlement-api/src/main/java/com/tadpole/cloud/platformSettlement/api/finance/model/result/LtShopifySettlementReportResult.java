package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Shopify小平台结算报告;
 * @author : LSY
 * @date : 2023-12-23
 */
@ApiModel(value = "Shopify小平台结算报告",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LtShopifySettlementReportResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** id */
    @ApiModelProperty(value = "id")
    private String id ;

   @ExcelProperty(value ="平台")
   private String platform;


   /** sku */
    @ApiModelProperty(value = "sku")
    @ExcelProperty(value ="sku")
    private String sku ;
 
    /** 物料编码 */
    @ApiModelProperty(value = "物料编码")
    @ExcelProperty(value ="物料编码")
    private String matCode ;
 
    /** 类目 */
    @ApiModelProperty(value = "类目")
    @ExcelProperty(value ="类目")
    private String category ;
 
    /** 运营大类 */
    @ApiModelProperty(value = "运营大类")
    @ExcelProperty(value ="运营大类")
    private String productType ;
 
    /** 产品名称 */
    @ApiModelProperty(value = "产品名称")
    @ExcelProperty(value ="产品名称")
    private String productName ;
 
    /** 款式 */
    @ApiModelProperty(value = "款式")
    @ExcelProperty(value ="款式")
    private String style ;
 
    /** 主材料 */
    @ApiModelProperty(value = "主材料")
    @ExcelProperty(value ="主材料")
    private String mainMaterial ;
 
    /** 图案 */
    @ApiModelProperty(value = "图案")
    @ExcelProperty(value ="图案")
    private String design ;
 
    /** 适用品牌或对象 */
    @ApiModelProperty(value = "适用品牌或对象")
    @ExcelProperty(value ="适用品牌或对象")
    private String fitBrand ;
 
    /** 型号 */
    @ApiModelProperty(value = "型号")
    @ExcelProperty(value ="型号")
    private String model ;

   /** 公司品牌 */
   @ApiModelProperty(value = "公司品牌")
   @ExcelProperty(value ="公司品牌")
   private String companyBrand ;


   /** 颜色 */
    @ApiModelProperty(value = "颜色")
    @ExcelProperty(value ="颜色")
    private String color ;
 
    /** 尺码 */
    @ApiModelProperty(value = "尺码")
    @ExcelProperty(value ="尺码")
    private String sizes ;
 
    /** 包装数量 */
    @ApiModelProperty(value = "包装数量")
    @ExcelProperty(value ="包装数量")
    private String packing ;
 
    /** 版本 */
    @ApiModelProperty(value = "版本")
    @ExcelProperty(value ="版本")
    private String version ;
 
    /** 适用机型 */
    @ApiModelProperty(value = "适用机型")
    @ExcelProperty(value ="适用机型")
    private String type ;
 
    /** 款式二级标签 */
    @ApiModelProperty(value = "款式二级标签")
    @ExcelProperty(value ="款式二级标签")
    private String styleSecondLabel ;
 
    /** 组合属性 */
    @ApiModelProperty(value = "组合属性")
    @ExcelProperty(value ="组合属性")
    private String propertyMerge ;
 
    /** 销售品牌 */
    @ApiModelProperty(value = "销售品牌")
    @ExcelProperty(value ="销售品牌")
    private String salesBrand ;
 
    /** Volume（正常发货） */
    @ApiModelProperty(value = "Volume（正常发货）")
    @ExcelProperty(value ="Volume（正常发货）")
    private BigDecimal volumeNormal ;
 
    /** Sales_excluding_tax */
    @ApiModelProperty(value = "Sales_excluding_tax")
    @ExcelProperty(value ="Sales_excluding_tax")
    private BigDecimal salesExcludingTax ;
 
    /** Tax */
    @ApiModelProperty(value = "Tax")
    @ExcelProperty(value ="Tax")
    private BigDecimal tax ;
 
    /** Refund */
    @ApiModelProperty(value = "Refund")
    @ExcelProperty(value ="Refund")
    private BigDecimal refund ;
 
    /** Giftwarp&Shipping */
    @ApiModelProperty(value = "Giftwarp&Shipping")
    @ExcelProperty(value ="Giftwarp&Shipping")
    private BigDecimal giftwarpShipping ;
 
    /** Amazon Fees */
    @ApiModelProperty(value = "Amazon Fees")
    @ExcelProperty(value ="Amazon Fees")
    private BigDecimal amazonFees ;
 
    /** 其他站外广告 */
    @ApiModelProperty(value = "其他站外广告")
    @ExcelProperty(value ="其他站外广告")
    private BigDecimal otherAdvertisements ;
 
    /** Other */
    @ApiModelProperty(value = "Other")
    @ExcelProperty(value ="Other")
    private BigDecimal other ;
 
    /** 回款 */
    @ApiModelProperty(value = "回款")
    @ExcelProperty(value ="回款")
    private BigDecimal collection ;
 
    /** 成本核算金额 */
    @ApiModelProperty(value = "成本核算金额")
    @ExcelProperty(value ="成本核算金额")
    private BigDecimal costAccountingFee ;
 
    /** 附加成本金额 */
    @ApiModelProperty(value = "附加成本金额")
    @ExcelProperty(value ="附加成本金额")
    private BigDecimal additionalCostAmount ;
 
    /** 头程物流费 */
    @ApiModelProperty(value = "头程物流费")
    @ExcelProperty(value ="头程物流费")
    private BigDecimal firstTripFee ;
 
    /** 当地物流费 */
    @ApiModelProperty(value = "当地物流费")
    @ExcelProperty(value ="当地物流费")
    private BigDecimal localLogisticsFee ;
 
    /** Profit */
    @ApiModelProperty(value = "Profit")
    @ExcelProperty(value ="Profit")
    private BigDecimal profit ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @ExcelProperty(value ="站点")
    private String site ;
 
    /** period */
    @ApiModelProperty(value = "period")
    @ExcelProperty(value ="period")
    private String period ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    @ExcelProperty(value ="账号")
    private String shopName ;
 
    /** 发货方式 */
    @ApiModelProperty(value = "发货方式")
    @ExcelProperty(value ="发货方式")
    private String deliveryMode ;
 
    /** 订单号 */
    @ApiModelProperty(value = "订单号")
    @ExcelProperty(value ="订单号")
    private String orderNum ;
 
    /** PayPal Transaction ID */
    @ApiModelProperty(value = "PayPal Transaction ID")
    @ExcelProperty(value ="PayPal_Transaction_ID")
    private String paypalTransactionId ;
 
    /** 费用类型辅助说明 */
    @ApiModelProperty(value = "费用类型辅助说明")
    @ExcelProperty(value ="费用类型辅助说明")
    private String costAuxiliaryDescription ;
 

   @ApiModelProperty(value = "")
   private Date updateTime ;

   /**  */
   @ApiModelProperty(value = "")
   private String updateBy ;

   /**  */
   @ApiModelProperty(value = "")
   private Date createTime ;

   /**  */
   @ApiModelProperty(value = "")
   private String createBy ;

   @ApiModelProperty("CONFIRM_STATUS")
   private BigDecimal confirmStatus;

   private  String structId;
   private  BigDecimal amazonAlloc;
   ;

   private BigDecimal detailsalesvol;

   private BigDecimal teamsalesvol;


   @ApiModelProperty("销量占比")
   @ExcelProperty(value="销量占比")
   private BigDecimal revenueRation ;

   /** 人数 */
   @ApiModelProperty("人数")
   @ExcelProperty(value="人数")
   private BigDecimal peopleNum ;

   /** 人工成本 */
   @ApiModelProperty("人工成本")
   @ExcelProperty(value="人工成本")
   private BigDecimal peopleCost ;

   @ExcelProperty(value="事业部")
   private String department;

   @ExcelProperty(value="Team")
   private String team;



}