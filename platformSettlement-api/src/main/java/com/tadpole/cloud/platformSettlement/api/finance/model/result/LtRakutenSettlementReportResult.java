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
public class LtRakutenSettlementReportResult implements Serializable{
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
    private String companyBrand ;
 
    /** 型号 */
    @ApiModelProperty(value = "型号")
    @ExcelProperty(value ="型号")
    private String fitBrand ;
 
    /** 公司品牌 */
    @ApiModelProperty(value = "公司品牌")
    @ExcelProperty(value ="公司品牌")
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
 
    /** 个数 */
    @ApiModelProperty(value = "个数")
    @ExcelProperty(value ="个数")
    private BigDecimal qty ;
 
    /** 测评数量 */
    @ApiModelProperty(value = "测评数量")
    @ExcelProperty(value ="测评数量")
    private BigDecimal volumeBillQuantity ;
 
    /** 运费 */
    @ApiModelProperty(value = "运费")
    @ExcelProperty(value ="运费")
    private BigDecimal freight ;
 
    /** 不含税销售额 */
    @ApiModelProperty(value = "不含税销售额")
    @ExcelProperty(value ="不含税销售额")
    private BigDecimal taxFreeSale ;
 
    /** 税额 */
    @ApiModelProperty(value = "税额")
    @ExcelProperty(value ="税额")
    private BigDecimal tax ;
 
    /** 订单金额（含税） */
    @ApiModelProperty(value = "订单金额（含税）")
    @ExcelProperty(value ="订单金额（含税）")
    private BigDecimal orderFeeInTax ;
 
    /** Refund */
    @ApiModelProperty(value = "Refund")
    @ExcelProperty(value ="Refund")
    private BigDecimal refund ;
 
    /** 乐天费用 */
    @ApiModelProperty(value = "乐天费用")
    @ExcelProperty(value ="乐天费用")
    private BigDecimal ratenkuFee ;
 
    /** 积分费用 */
    @ApiModelProperty(value = "积分费用")
    @ExcelProperty(value ="积分费用")
    private BigDecimal creditFee ;
 
    /** FBA费用 */
    @ApiModelProperty(value = "FBA费用")
    @ExcelProperty(value ="FBA费用")
    private BigDecimal fbaFee ;
 
    /** 广告费 */
    @ApiModelProperty(value = "广告费")
    @ExcelProperty(value ="广告费")
    private BigDecimal advertising ;
 
    /**测评数量 */
    @ApiModelProperty(value = "测评货值")
    @ExcelProperty(value ="测评货值")
    private BigDecimal brushingValue ;
 
    /** 测评手续费 */
    @ApiModelProperty(value = "测评手续费")
    @ExcelProperty(value ="测评手续费")
    private BigDecimal brushingServiceCharge ;
 
    /** Cost原始 */
    @ApiModelProperty(value = "Cost原始")
    @ExcelProperty(value ="Cost原始")
    private BigDecimal costOri ;
 
    /** 成本附加 */
    @ApiModelProperty(value = "成本附加")
    @ExcelProperty(value ="成本附加")
    private BigDecimal costAdd ;
 
    /** 物流 */
    @ApiModelProperty(value = "物流")
    @ExcelProperty(value ="物流")
    private BigDecimal logistics ;
 
    /** 海外仓发货费用 */
    @ApiModelProperty(value = "海外仓发货费用")
    @ExcelProperty(value ="海外仓发货费用")
    private BigDecimal overseaWareDeliFee ;
 
    /** Storage_fee */
    @ApiModelProperty(value = "Storage_fee")
    @ExcelProperty(value ="Storage_fee")
    private BigDecimal storageFee ;
 
    /** 销毁费 */
    @ApiModelProperty(value = "销毁费")
    @ExcelProperty(value ="销毁费")
    private BigDecimal disposeFee ;
 
    /** 销毁成本-采购成本 */
    @ApiModelProperty(value = "销毁成本-采购成本")
    @ExcelProperty(value ="销毁成本-采购成本")
    private BigDecimal disposePurchaseFee ;
 
    /** 销毁成本-头程物流成本 */
    @ApiModelProperty(value = "销毁成本-头程物流成本")
    @ExcelProperty(value ="销毁成本-头程物流成本")
    private BigDecimal disposeLogisticsFee ;
 
    /** 滞销库存利息费 */
    @ApiModelProperty(value = "滞销库存利息费")
    @ExcelProperty(value ="滞销库存利息费")
    private BigDecimal domesticUnsalableInventory ;
 
    /** Profit */
    @ApiModelProperty(value = "Profit")
    @ExcelProperty(value ="Profit")
    private BigDecimal profit ;

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
 
    /** 采购员 */
    @ApiModelProperty(value = "采购员")
    @ExcelProperty(value ="采购员")
    private String buyer ;
 
    /** 开发人员 */
    @ApiModelProperty(value = "开发人员")
    @ExcelProperty(value ="开发人员")
    private String developer ;
 
    /** 最早下单日期 */
    @ApiModelProperty(value = "最早下单日期")
    @ExcelProperty(value ="最早下单日期")
    private String firstOrderDate ;


    /** 事业部 */
    @ApiModelProperty(value = "事业部")
    @ExcelProperty(value ="事业部")
    private String department ;

    /** Team */
    @ApiModelProperty(value = "Team")
    @ExcelProperty(value ="Team")
    private String team ;

    /** 是否新品 */
    @ApiModelProperty(value = "是否新品")
    @ExcelProperty(value ="是否新品")
    private String isNew ;
 
    /** 备注 */
    @ApiModelProperty(value = "备注")
    @ExcelProperty(value ="备注")
    private String remark ;
 
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




   private  String structId;
   private  BigDecimal amazonAlloc;
   private  BigDecimal detailsalesvol;


   private BigDecimal teamsalesvol;




}