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
public class LtAliexpressSettlementReportResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    private String id ;

   @ExcelProperty(value ="平台")
   private String platform;


   /** 物流跟踪单号 */
    @ApiModelProperty(value = "物流跟踪单号")
    @ExcelProperty(value ="物流跟踪单号")
    private String logisticsTrackNo ;


   @ExcelProperty(value="事业部")
   private String department;

   @ExcelProperty(value="Team")
   private String team;


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
 
    /** 国家 */
    @ApiModelProperty(value = "国家")
    @ExcelProperty(value ="国家")
    private String country ;
 
    /** 订单号 */
    @ApiModelProperty(value = "订单号")
    @ExcelProperty(value ="订单号")
    private String orderNo ;
 
    /** 销售数量 */
    @ApiModelProperty(value = "销售数量")
    @ExcelProperty(value ="销售数量")
    private BigDecimal saleQty ;
 
    /** 订单金额 */
    @ApiModelProperty(value = "订单金额")
    @ExcelProperty(value ="订单金额")
    private String orderAomount ;
 
    /** 平台佣金 */
    @ApiModelProperty(value = "平台佣金")
    @ExcelProperty(value ="平台佣金")
    private BigDecimal platformFee ;
 
    /** 推广联盟佣金 */
    @ApiModelProperty(value = "推广联盟佣金")
    @ExcelProperty(value ="推广联盟佣金")
    private BigDecimal promoteAllianceFee ;
 
    /** 物流费 */
    @ApiModelProperty(value = "物流费")
    @ExcelProperty(value ="物流费")
    private BigDecimal logisticsFee ;
 
    /** 成本*1 */
    @ApiModelProperty(value = "成本*1")
    @ExcelProperty(value ="成本*1")
    private BigDecimal cost1 ;
 
    /** 成本*阶梯 */
    @ApiModelProperty(value = "成本*阶梯")
    @ExcelProperty(value ="成本*阶梯")
    private BigDecimal costStep ;
 
    /** 销毁成本-采购成本 */
    @ApiModelProperty(value = "销毁成本-采购成本")
    @ExcelProperty(value ="销毁成本-采购成本")
    private BigDecimal disposePurchaseFee ;
 
    /** 滞销库存利息费 */
    @ApiModelProperty(value = "滞销库存利息费")
    @ExcelProperty(value ="滞销库存利息费")
    private BigDecimal domesticUnsalableInventory ;
 
    /** profit */
    @ApiModelProperty(value = "profit")
    @ExcelProperty(value ="profit")
    private BigDecimal profit ;
 
    /** 鼓励金 */
    @ApiModelProperty(value = "鼓励金")
    @ExcelProperty(value ="鼓励金")
    private BigDecimal incentiveFund ;
 
    /** period */
    @ApiModelProperty(value = "period")
    @ExcelProperty(value ="period")
    private String period ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    @ExcelProperty(value ="账号")
    private String shopName ;
 
    /** 出库时间 */
    @ApiModelProperty(value = "出库时间")
    @ExcelProperty(value ="出库时间")
    private Date outTime ;
 
    /** 单位成本-CNY */
    @ApiModelProperty(value = "单位成本-CNY")
    @ExcelProperty(value ="单位成本-CNY")
    private BigDecimal unitCostCny ;
 
    /** 含税单价-CNY */
    @ApiModelProperty(value = "含税单价-CNY")
    @ExcelProperty(value ="含税单价-CNY")
    private BigDecimal taxPriceCny ;
 
    /** 备注 */
    @ApiModelProperty(value = "备注")
    @ExcelProperty(value ="备注")
    private String remark ;
 
    /** 放款金额 */
    @ApiModelProperty(value = "放款金额")
    @ExcelProperty(value ="放款金额")
    private BigDecimal giveAmount ;
 
    /** 回款账户 */
    @ApiModelProperty(value = "回款账户")
    @ExcelProperty(value ="回款账户")
    private String collectAccount ;
 
    /** 回款币种 */
    @ApiModelProperty(value = "回款币种")
    @ExcelProperty(value ="回款币种")
    private String paymentCurrency ;
 
    /** 备注2 */
    @ApiModelProperty(value = "备注2")
    @ExcelProperty(value ="备注2")
    private String remark2 ;

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

   @ApiModelProperty("CONFIRM_STATUS")
   private BigDecimal confirmStatus;

   private  String structId;
   private  BigDecimal amazonAlloc;
   ;

   private BigDecimal detailsalesvol;

   private BigDecimal teamsalesvol;




}