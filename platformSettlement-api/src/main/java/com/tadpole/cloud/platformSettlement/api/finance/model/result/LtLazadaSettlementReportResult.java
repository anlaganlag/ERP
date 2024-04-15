package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
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
@ExcelIgnoreUnannotated
public class LtLazadaSettlementReportResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    @ExcelProperty(value ="ID")
    private String id ;

    @ExcelProperty(value ="平台")
    private String platform;


    /** 账号 */
    @ApiModelProperty(value = "账号")
    @ExcelProperty(value ="账号")
    private String shopName ;

    /** 账号 */
    @ApiModelProperty(value = "事业部")
    @ExcelProperty(value ="事业部")
    private String department ;


    /** 账号 */
    @ApiModelProperty(value = "Team")
    @ExcelProperty(value ="Team")
    private String team ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @ExcelProperty(value ="站点")
    private String site ;
 
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
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    @ExcelProperty(value ="数量")
    private BigDecimal qty ;
 
    /** Sales_excluding_tax */
    @ApiModelProperty(value = "Sales_excluding_tax")
    @ExcelProperty(value ="Sales_excluding_tax")
    private BigDecimal salesExcludingTax ;
 
    /** Tax */
    @ApiModelProperty(value = "Tax")
    @ExcelProperty(value ="Tax")
    private BigDecimal tax ;
 
    /** PROFIT */
    @ApiModelProperty(value = "PROFIT")
    @ExcelProperty(value ="PROFIT")
    private BigDecimal profit ;
 
    /** 鼓励金 */
    @ApiModelProperty(value = "鼓励金")
    @ExcelProperty(value ="鼓励金")
    private BigDecimal incentiveFund ;
 
    /** period */
    @ApiModelProperty(value = "period")
    @ExcelProperty(value ="period")
    private String period ;
 
    /** 手续费 */
    @ApiModelProperty(value = "手续费")
    @ExcelProperty(value ="手续费")
    private BigDecimal serviceCharge ;
 
    /** orderNumber */
    @ApiModelProperty(value = "orderNumber")
    @ExcelProperty(value ="orderNumber")
    private String orderNumber ;
 
    /** sellerSku */
    @ApiModelProperty(value = "sellerSku")
    @ExcelProperty(value ="sellerSku")
    private String sellerSku ;
 
    /** 款式二级标签 */
    @ApiModelProperty(value = "款式二级标签")
    @ExcelProperty(value ="款式二级标签")
    private String styleSecondLabel ;
 
    /** SALES_PROMOTION */
    @ApiModelProperty(value = "SALES_PROMOTION")
    @ExcelProperty(value ="SALES_PROMOTION")
    private BigDecimal salesPromotion ;
 
    /** REFUND */
    @ApiModelProperty(value = "REFUND")
    @ExcelProperty(value ="REFUND")
    private BigDecimal refund ;
 
    /** ADVERTISING */
    @ApiModelProperty(value = "ADVERTISING")
    @ExcelProperty(value ="ADVERTISING")
    private BigDecimal advertising ;
 
    /** REIMBURSEMENT */
    @ApiModelProperty(value = "REIMBURSEMENT")
    @ExcelProperty(value ="REIMBURSEMENT")
    private BigDecimal reimbursement ;
 
    /** 物流费用 */
    @ApiModelProperty(value = "物流费用")
    @ExcelProperty(value ="物流费用")
    private BigDecimal logisticsFee ;
 
    /** 联盟费用 */
    @ApiModelProperty(value = "联盟费用")
    @ExcelProperty(value ="联盟费用")
    private BigDecimal allianceFee ;
 
    /** Commission */
    @ApiModelProperty(value = "Commission")
    @ExcelProperty(value ="Commission")
    private BigDecimal commission ;
 
    /** 销毁成本-采购成本 */
    @ApiModelProperty(value = "销毁成本-采购成本")
    @ExcelProperty(value ="销毁成本-采购成本")
    private BigDecimal disposePurchaseFee ;
 
    /** 成本-USD */
    @ApiModelProperty(value = "成本-USD")
    @ExcelProperty(value ="成本-USD")
    private BigDecimal costUsd ;
 
    /** 附加成本-USD */
    @ApiModelProperty(value = "附加成本-USD")
    @ExcelProperty(value ="附加成本-USD")
    private BigDecimal addCostUsd ;
 
    /** 备注 */
    @ApiModelProperty(value = "备注")
    @ExcelProperty(value ="备注")
    private String remark ;

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