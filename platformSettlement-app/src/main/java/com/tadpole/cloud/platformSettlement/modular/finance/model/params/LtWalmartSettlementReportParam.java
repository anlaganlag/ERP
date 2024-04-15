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
 * ;
 * @author : LSY
 * @date : 2023-12-22
 */
@ApiModel(value = "",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LtWalmartSettlementReportParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /**  */
    @ApiModelProperty(value = "")
    private String id ;
 
    /** 会计期间 */
    @ApiModelProperty(value = "会计期间")
    private String fiscalPeriod ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    private String shopName ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    private String site ;
 
    /** 事业部 */
    @ApiModelProperty(value = "事业部")
    private String department ;
 
    /** Team */
    @ApiModelProperty(value = "Team")
    private String team ;
 
    /** 店铺 */
    @ApiModelProperty(value = "店铺")
    private String shop ;
 
    /** 发货方式 */
    @ApiModelProperty(value = "发货方式")
    private String deliveryMode ;
 
    /** 订单号 */
    @ApiModelProperty(value = "订单号")
    private String orderNum ;
 
    /** 核算币种 */
    @ApiModelProperty(value = "核算币种")
    private String accountingCurrency ;
 
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
 
    /** 公司品牌 */
    @ApiModelProperty(value = "公司品牌")
    private String companyBrand ;
 
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
 
    /** 销售品牌 */
    @ApiModelProperty(value = "销售品牌")
    private String salesBrand ;
 
    /** 费用类型辅助说明 */
    @ApiModelProperty(value = "费用类型辅助说明")
    private String costAuxiliaryDescription ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    private BigDecimal qty ;
 
    /** 测评数量 */
    @ApiModelProperty(value = "测评数量")
    private BigDecimal volumeBillQuantity ;
 
    /** Product Price */
    @ApiModelProperty(value = "Product Price")
    private BigDecimal productPrice ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal productCommission ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal productTax ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal refund ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal refundCommission ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal refundTax ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal refundTaxWithheld ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal wfsFulfillmentFee ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal storageFee ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal inventoryDisposalFee ;
 
    /**  */
    @ApiModelProperty(value = "Inbound_Fee")
    private BigDecimal inboundFee ;
 
    /** 移除费 */
    @ApiModelProperty(value = "移除费")
    private BigDecimal removalDeal ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal salesShipping ;
 
    /**  */
    @ApiModelProperty(value = "Sales_Shipping_Reversal")
    private BigDecimal salesShippingReversal ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal otherTax ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal other ;
 
    /** 回款 */
    @ApiModelProperty(value = "回款")
    private BigDecimal collection ;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal advertising ;
 
    /** 回款期间 */
    @ApiModelProperty(value = "回款期间")
    private String collectionPeriod ;
 
    /** 站外物流费 */
    @ApiModelProperty(value = "站外物流费")
    private BigDecimal stationOutLog ;
 
    /** 站外广告费 */
    @ApiModelProperty(value = "站外广告费")
    private BigDecimal stationOutAd ;
 
    /** 站外仓储费 */
    @ApiModelProperty(value = "站外仓储费")
    private BigDecimal stationOutStorage ;
 
    /** 滞销库存利息费 */
    @ApiModelProperty(value = "滞销库存利息费")
    private BigDecimal domesticUnsalableInventory ;
 
    /**测评数量 */
    @ApiModelProperty(value = "测评货值")
    private BigDecimal brushingValue ;
 
    /** 测评手续费 */
    @ApiModelProperty(value = "测评手续费")
    private BigDecimal brushingServiceCharge ;
 
    /** 鼓励金 */
    @ApiModelProperty(value = "鼓励金")
    private BigDecimal incentiveFund ;
 
    /** 成本核算金额 */
    @ApiModelProperty(value = "成本核算金额")
    private BigDecimal costAccountingFee ;
 
    /** 附加成本金额 */
    @ApiModelProperty(value = "附加成本金额")
    private BigDecimal additionalCostAmount ;
 
    /** 头程物流费 */
    @ApiModelProperty(value = "头程物流费")
    private BigDecimal firstTripFee ;
 
    /** Profit */
    @ApiModelProperty(value = "Profit")
    private BigDecimal profit ;
 
    /** 成本单价CNY */
    @ApiModelProperty(value = "成本单价CNY")
    private BigDecimal unitCostCny ;
 
    /** 含税单价CNY */
    @ApiModelProperty(value = "含税单价CNY")
    private BigDecimal taxPriceCny ;
 
    /** 物流单价CNY */
    @ApiModelProperty(value = "物流单价CNY")
    private BigDecimal logPriceCny ;
 
    /**  */
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

    @ApiModelProperty("销量占比")
    private BigDecimal revenueRation ;

    /** 人数 */
    @ApiModelProperty("人数")
    private BigDecimal peopleNum ;

    /** 人工成本 */
    @ApiModelProperty("人工成本")
    private BigDecimal peopleCost ;


    @ApiModelProperty("productTaxWithheld")
    private BigDecimal productTaxWithheld ;


    @ApiModelProperty("CONFIRM_STATUS")
    private BigDecimal confirmStatus;


    @ApiModelProperty(value = "")
    private String platform ;

    private BigDecimal directRate;


}