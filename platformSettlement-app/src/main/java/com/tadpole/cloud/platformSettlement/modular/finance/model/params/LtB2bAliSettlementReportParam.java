package com.tadpole.cloud.platformSettlement.modular.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.util.Date;

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
public class LtB2bAliSettlementReportParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    private String id ;
 
    /** 单据编号 */
    @ApiModelProperty(value = "单据编号")
    private String billCode ;
 
    /** 新物料代码 */
    @ApiModelProperty(value = "新物料代码")
    private String newMatCode ;
 
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
 
    /** 订单号 */
    @ApiModelProperty(value = "订单号")
    private String orderNo ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    private BigDecimal qty ;
 
    /** 币种 */
    @ApiModelProperty(value = "币种")
    private String currency ;
 
    /** 销售额美金 */
    @ApiModelProperty(value = "销售额美金")
    private BigDecimal saleUsd ;
 
    /** 头程物流费 */
    @ApiModelProperty(value = "头程物流费")
    private BigDecimal firstTripFee ;
 
    /** FBA费用 */
    @ApiModelProperty(value = "FBA费用")
    private BigDecimal fbaFee ;
 
    /** 成本 */
    @ApiModelProperty(value = "成本")
    private BigDecimal cost ;
 
    /** 成本附加 */
    @ApiModelProperty(value = "成本附加")
    private BigDecimal costAdd ;
 
    /** 滞销库存利息费 */
    @ApiModelProperty(value = "滞销库存利息费")
    private BigDecimal domesticUnsalableInventory ;
 
    /** 利润 */
    @ApiModelProperty(value = "利润")
    private BigDecimal profit ;
 
    /** 鼓励金 */
    @ApiModelProperty(value = "鼓励金")
    private BigDecimal incentiveFund ;
 
    /** Period */
    @ApiModelProperty(value = "Period")
    private String period ;
 
    /** 平台 */
    @ApiModelProperty(value = "平台")
    private String platform ;
 
    /** 发货方式 */
    @ApiModelProperty(value = "发货方式")
    private String fulfillment ;
 
    /** 成本单价 */
    @ApiModelProperty(value = "成本单价")
    private BigDecimal costUnitPrice ;
 
    /** 含税单价 */
    @ApiModelProperty(value = "含税单价")
    private BigDecimal taxPrice ;
 
    /** 客户 */
    @ApiModelProperty(value = "客户")
    private String customer ;
 
    /** 客户年份 */
    @ApiModelProperty(value = "客户年份")
    private String customerYear ;
 
    /** 新老客户 */
    @ApiModelProperty(value = "新老客户")
    private String newOldCustomer ;
 
    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark ;
 
    /** 收款账户 */
    @ApiModelProperty(value = "收款账户")
    private String collectionAccount ;
 
    /** 收款日期 */
    @ApiModelProperty(value = "收款日期")
    private Date collectionDate ;
 
    /** 回款备注 */
    @ApiModelProperty(value = "回款备注")
    private String paymentRemark ;
 
    /** Period-算年份 */
    @ApiModelProperty(value = "Period-算年份")
    private String periodCalYear ;
 
    /** 销售员 */
    @ApiModelProperty(value = "销售员")
    private String salesman ;
 
    /** 销量占比 */
    @ApiModelProperty(value = "销量占比")
    private BigDecimal revenueRation ;
 
    /** 人数 */
    @ApiModelProperty(value = "人数")
    private BigDecimal peopleNum ;
 
    /** 人工成本 */
    @ApiModelProperty(value = "人工成本")
    private BigDecimal peopleCost ;
 
    /** 销售额原币 */
    @ApiModelProperty(value = "销售额原币")
    private BigDecimal saleOri ;
 
    /** 小包物流费 */
    @ApiModelProperty(value = "小包物流费")
    private BigDecimal smallPackLogFee ;
 
    /** 手续费 */
    @ApiModelProperty(value = "手续费")
    private BigDecimal serviceFee ;
 
    /** 实收款 */
    @ApiModelProperty(value = "实收款")
    private BigDecimal realCollection ;


    @ApiModelProperty("CONFIRM_STATUS")
    private BigDecimal confirmStatus;



    /**  */
    @ApiModelProperty(value = "")
    private String department ;


    /**  */
    @ApiModelProperty(value = "")
    private String team ;

}