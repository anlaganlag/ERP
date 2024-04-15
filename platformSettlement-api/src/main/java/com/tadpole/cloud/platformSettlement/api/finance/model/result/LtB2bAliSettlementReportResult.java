package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
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
@ExcelIgnoreUnannotated
public class LtB2bAliSettlementReportResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    @ExcelProperty(value ="ID")
    private String id ;
 
    /** 单据编号 */
    @ApiModelProperty(value = "单据编号")
    @ExcelProperty(value ="单据编号")
    private String billCode ;
 
    /** 新物料代码 */
    @ApiModelProperty(value = "新物料代码")
    @ExcelProperty(value ="新物料代码")
    private String newMatCode ;
 
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
 
    /** 公司品牌 */
    @ApiModelProperty(value = "公司品牌")
    @ExcelProperty(value ="公司品牌")
    private String companyBrand ;
 
    /** 适用品牌或对象 */
    @ApiModelProperty(value = "适用品牌或对象")
    @ExcelProperty(value ="适用品牌或对象")
    private String fitBrand ;
 
    /** 型号 */
    @ApiModelProperty(value = "型号")
    @ExcelProperty(value ="型号")
    private String model ;
 
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
 
    /** 销售品牌 */
    @ApiModelProperty(value = "销售品牌")
    @ExcelProperty(value ="销售品牌")
    private String salesBrand ;
 
    /** 订单号 */
    @ApiModelProperty(value = "订单号")
    @ExcelProperty(value ="订单号")
    private String orderNo ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    @ExcelProperty(value ="数量")
    private BigDecimal qty ;
 
    /** 币种 */
    @ApiModelProperty(value = "币种")
    @ExcelProperty(value ="币种")
    private String currency ;
 
    /** 销售额美金 */
    @ApiModelProperty(value = "销售额美金")
    @ExcelProperty(value ="销售额美金")
    private BigDecimal saleUsd ;
 
    /** 头程物流费 */
    @ApiModelProperty(value = "头程物流费")
    @ExcelProperty(value ="头程物流费")
    private BigDecimal firstTripFee ;
 
    /** FBA费用 */
    @ApiModelProperty(value = "FBA费用")
    @ExcelProperty(value ="FBA费用")
    private BigDecimal fbaFee ;
 
    /** 成本 */
    @ApiModelProperty(value = "成本")
    @ExcelProperty(value ="成本")
    private BigDecimal cost ;
 
    /** 成本附加 */
    @ApiModelProperty(value = "成本附加")
    @ExcelProperty(value ="成本附加")
    private BigDecimal costAdd ;
 
    /** 滞销库存利息费 */
    @ApiModelProperty(value = "滞销库存利息费")
    @ExcelProperty(value ="滞销库存利息费")
    private BigDecimal domesticUnsalableInventory ;
 
    /** 利润 */
    @ApiModelProperty(value = "利润")
    @ExcelProperty(value ="利润")
    private BigDecimal profit ;
 
    /** 鼓励金 */
    @ApiModelProperty(value = "鼓励金")
    @ExcelProperty(value ="鼓励金")
    private BigDecimal incentiveFund ;
 
    /** Period */
    @ApiModelProperty(value = "Period")
    @ExcelProperty(value ="Period")
    private String period ;
 
    /** 平台 */
    @ApiModelProperty(value = "平台")
    @ExcelProperty(value ="平台")
    private String platform ;
 
    /** 发货方式 */
    @ApiModelProperty(value = "发货方式")
    @ExcelProperty(value ="发货方式")
    private String fulfillment ;
 
    /** 成本单价 */
    @ApiModelProperty(value = "成本单价")
    @ExcelProperty(value ="成本单价")
    private BigDecimal costUnitPrice ;
 
    /** 含税单价 */
    @ApiModelProperty(value = "含税单价")
    @ExcelProperty(value ="含税单价")
    private BigDecimal taxPrice ;
 
    /** 客户 */
    @ApiModelProperty(value = "客户")
    @ExcelProperty(value ="客户")
    private String customer ;
 
    /** 客户年份 */
    @ApiModelProperty(value = "客户年份")
    @ExcelProperty(value ="客户年份")
    private String customerYear ;
 
    /** 新老客户 */
    @ApiModelProperty(value = "新老客户")
    @ExcelProperty(value ="新老客户")
    private String newOldCustomer ;
 
    /** 备注 */
    @ApiModelProperty(value = "备注")
    @ExcelProperty(value ="备注")
    private String remark ;
 
    /** 收款账户 */
    @ApiModelProperty(value = "收款账户")
    @ExcelProperty(value ="收款账户")
    private String collectionAccount ;
 
    /** 收款日期 */
    @ApiModelProperty(value = "收款日期")
    @ExcelProperty(value ="收款日期")
    private Date collectionDate ;
 
    /** 回款备注 */
    @ApiModelProperty(value = "回款备注")
    @ExcelProperty(value ="回款备注")
    private String paymentRemark ;
 
    /** Period-算年份 */
    @ApiModelProperty(value = "Period-算年份")
    @ExcelProperty(value ="Period-算年份")
    private String periodCalYear ;
 
    /** 销售员 */
    @ApiModelProperty(value = "销售员")
    @ExcelProperty(value ="销售员")
    private String salesman ;
 
    /** 销量占比 */
    @ApiModelProperty(value = "销量占比")
    @ExcelProperty(value ="销量占比")
    private BigDecimal revenueRation ;
 
    /** 人数 */
    @ApiModelProperty(value = "人数")
    @ExcelProperty(value ="人数")
    private BigDecimal peopleNum ;
 
    /** 人工成本 */
    @ApiModelProperty(value = "人工成本")
    @ExcelProperty(value ="人工成本")
    private BigDecimal peopleCost ;
 
    /** 销售额原币 */
    @ApiModelProperty(value = "销售额原币")
    @ExcelProperty(value ="销售额原币")
    private BigDecimal saleOri ;
 
    /** 小包物流费 */
    @ApiModelProperty(value = "小包物流费")
    @ExcelProperty(value ="小包物流费")
    private BigDecimal smallPackLogFee ;
 
    /** 手续费 */
    @ApiModelProperty(value = "手续费")
    @ExcelProperty(value ="手续费")
    private BigDecimal serviceFee ;
 
    /** 实收款 */
    @ApiModelProperty(value = "实收款")
    @ExcelProperty(value ="实收款")
    private BigDecimal realCollection ;

   @ApiModelProperty("CONFIRM_STATUS")
   private BigDecimal confirmStatus;

   private  String structId;
   private  BigDecimal amazonAlloc;
   ;

   private BigDecimal detailsalesvol;

   private BigDecimal teamsalesvol;

   @ExcelProperty(value="事业部")
   private String department;

   @ExcelProperty(value="Team")
   private String team;



}