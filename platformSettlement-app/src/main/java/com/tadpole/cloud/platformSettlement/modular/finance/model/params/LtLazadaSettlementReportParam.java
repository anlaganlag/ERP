package com.tadpole.cloud.platformSettlement.modular.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class LtLazadaSettlementReportParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    private String id ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    private String shopName ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    private String site ;
 
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
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    private BigDecimal qty ;
 
    /** Sales_excluding_tax */
    @ApiModelProperty(value = "Sales_excluding_tax")
    private BigDecimal salesExcludingTax ;
 
    /** Tax */
    @ApiModelProperty(value = "Tax")
    private BigDecimal tax ;
 
    /** PROFIT */
    @ApiModelProperty(value = "PROFIT")
    private BigDecimal profit ;
 
    /** 鼓励金 */
    @ApiModelProperty(value = "鼓励金")
    private BigDecimal incentiveFund ;
 
    /** period */
    @ApiModelProperty(value = "period")
    private String period ;
 
    /** 手续费 */
    @ApiModelProperty(value = "手续费")
    private BigDecimal serviceCharge ;
 
    /** orderNumber */
    @ApiModelProperty(value = "orderNumber")
    private String orderNumber ;
 
    /** sellerSku */
    @ApiModelProperty(value = "sellerSku")
    private String sellerSku ;
 
    /** 款式二级标签 */
    @ApiModelProperty(value = "款式二级标签")
    private String styleSecondLabel ;
 
    /** SALES_PROMOTION */
    @ApiModelProperty(value = "SALES_PROMOTION")
    private BigDecimal salesPromotion ;
 
    /** REFUND */
    @ApiModelProperty(value = "REFUND")
    private BigDecimal refund ;
 
    /** ADVERTISING */
    @ApiModelProperty(value = "ADVERTISING")
    private BigDecimal advertising ;
 
    /** REIMBURSEMENT */
    @ApiModelProperty(value = "REIMBURSEMENT")
    private BigDecimal reimbursement ;
 
    /** 物流费用 */
    @ApiModelProperty(value = "物流费用")
    private BigDecimal logisticsFee ;
 
    /** 联盟费用 */
    @ApiModelProperty(value = "联盟费用")
    private BigDecimal allianceFee ;
 
    /** Commission */
    @ApiModelProperty(value = "Commission")
    private BigDecimal commission ;
 
    /** 销毁成本-采购成本 */
    @ApiModelProperty(value = "销毁成本-采购成本")
    private BigDecimal disposePurchaseFee ;
 
    /** 成本-USD */
    @ApiModelProperty(value = "成本-USD")
    private BigDecimal costUsd ;
 
    /** 附加成本-USD */
    @ApiModelProperty(value = "附加成本-USD")
    private BigDecimal addCostUsd ;
 
    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark ;

    @ApiModelProperty("销量占比")
    private BigDecimal revenueRation ;

    /** 人数 */
    @ApiModelProperty("人数")
    private BigDecimal peopleNum ;

    /** 人工成本 */
    @ApiModelProperty("人工成本")
    private BigDecimal peopleCost ;


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

    @ApiModelProperty(value = "")
    private String createBy ;
}