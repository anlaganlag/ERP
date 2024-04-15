package com.tadpole.cloud.platformSettlement.modular.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
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
public class LtShopifySettlementReportParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** id */
    @ApiModelProperty(value = "id")
    private String id ;
 
    /** sku */
    @ApiModelProperty(value = "sku")
    private String sku ;
 
    /** 物料编码 */
    @ApiModelProperty(value = "物料编码")
    private String matCode ;
 
    /** 类目 */
    @ApiModelProperty(value = "类目")
    private String category ;
 
    /** 运营大类 */
    @ApiModelProperty(value = "运营大类")
    private String productType ;
 
    /** 产品名称 */
    @ApiModelProperty(value = "产品名称")
    private String productName ;
 
    /** 款式 */
    @ApiModelProperty(value = "款式")
    private String style ;
 
    /** 主材料 */
    @ApiModelProperty(value = "主材料")
    private String mainMaterial ;
 
    /** 图案 */
    @ApiModelProperty(value = "图案")
    private String design ;
 
    /** 适用品牌或对象 */
    @ApiModelProperty(value = "适用品牌或对象")
    private String fitBrand ;
 
    /** 型号 */
    @ApiModelProperty(value = "型号")
    private String model ;
 
    /** 颜色 */
    @ApiModelProperty(value = "颜色")
    private String color ;
 
    /** 尺码 */
    @ApiModelProperty(value = "尺码")
    private String sizes ;
 
    /** 包装数量 */
    @ApiModelProperty(value = "包装数量")
    private String packing ;
 
    /** 版本 */
    @ApiModelProperty(value = "版本")
    private String version ;
 
    /** 适用机型 */
    @ApiModelProperty(value = "适用机型")
    private String type ;
 
    /** 款式二级标签 */
    @ApiModelProperty(value = "款式二级标签")
    private String styleSecondLabel ;
 
    /** 组合属性 */
    @ApiModelProperty(value = "组合属性")
    private String propertyMerge ;
 
    /** 销售品牌 */
    @ApiModelProperty(value = "销售品牌")
    private String salesBrand ;
 
    /** Volume（正常发货） */
    @ApiModelProperty(value = "Volume（正常发货）")
    private BigDecimal volumeNormal ;
 
    /** Sales_excluding_tax */
    @ApiModelProperty(value = "Sales_excluding_tax")
    private BigDecimal salesExcludingTax ;
 
    /** Tax */
    @ApiModelProperty(value = "Tax")
    private BigDecimal tax ;
 
    /** Refund */
    @ApiModelProperty(value = "Refund")
    private BigDecimal refund ;
 
    /** Giftwarp&Shipping */
    @ApiModelProperty(value = "Giftwarp&Shipping")
    private BigDecimal giftwarpShipping ;
 
    /** Amazon Fees */
    @ApiModelProperty(value = "Amazon Fees")
    private BigDecimal amazonFees ;
 
    /** 其他站外广告 */
    @ApiModelProperty(value = "其他站外广告")
    private BigDecimal otherAdvertisements ;
 
    /** Other */
    @ApiModelProperty(value = "Other")
    private BigDecimal other ;
 
    /** 回款 */
    @ApiModelProperty(value = "回款")
    private BigDecimal collection ;
 
    /** 成本核算金额 */
    @ApiModelProperty(value = "成本核算金额")
    private BigDecimal costAccountingFee ;
 
    /** 附加成本金额 */
    @ApiModelProperty(value = "附加成本金额")
    private BigDecimal additionalCostAmount ;
 
    /** 头程物流费 */
    @ApiModelProperty(value = "头程物流费")
    private BigDecimal firstTripFee ;
 
    /** 当地物流费 */
    @ApiModelProperty(value = "当地物流费")
    private BigDecimal localLogisticsFee ;
 
    /** Profit */
    @ApiModelProperty(value = "Profit")
    private BigDecimal profit ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    private String site ;
 
    /** period */
    @ApiModelProperty(value = "period")
    private String period ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    private String shopName ;
 
    /** 发货方式 */
    @ApiModelProperty(value = "发货方式")
    private String deliveryMode ;
 
    /** 订单号 */
    @ApiModelProperty(value = "订单号")
    private String orderNum ;
 
    /** PayPal Transaction ID */
    @ApiModelProperty(value = "PayPal Transaction ID")
    private String paypalTransactionId ;
 
    /** 费用类型辅助说明 */
    @ApiModelProperty(value = "费用类型辅助说明")
    private String costAuxiliaryDescription ;
 
    /** 公司品牌 */
    @ApiModelProperty(value = "公司品牌")
    private String companyBrand ;

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

   @ApiModelProperty(value = "")
   private String platform ;

   /**  */
   @ApiModelProperty(value = "")
   private String department ;


   /**  */
   @ApiModelProperty(value = "")
   private String team ;

   private BigDecimal directRate;


}