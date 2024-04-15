package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

 /**
 * 出货清单明细1-箱子长宽高重-金蝶+海外仓;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "出货清单明细1-箱子长宽高重-金蝶+海外仓",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPackingListDet1Result implements Serializable{
 private static final long serialVersionUID = 1L;

    /** 箱号 */
    @ApiModelProperty(value = "箱号")
    @ExcelProperty(value ="箱号")
    private Integer packDetBoxNum ;

    /** 箱号上传名称 */
    @ApiModelProperty(value = "箱号上传名称")
    @ExcelProperty(value ="箱号上传名称")
    private String packDetBoxNumUpload ;

    /** 箱型 */
    @ApiModelProperty(value = "箱型")
    @ExcelProperty(value ="箱型")
    private String packDetBoxType ;

    /** 重量 */
    @ApiModelProperty(value = "重量")
    @ExcelProperty(value ="重量")
    private BigDecimal packDetBoxWeight ;

    /** 箱长 */
    @ApiModelProperty(value = "箱长")
    @ExcelProperty(value ="长")
    private BigDecimal packDetBoxLength ;

    /** 箱宽 */
    @ApiModelProperty(value = "箱宽")
    @ExcelProperty(value ="宽")
    private BigDecimal packDetBoxWidth ;

    /** 箱高 */
    @ApiModelProperty(value = "箱高")
    @ExcelProperty(value ="高")
    private BigDecimal packDetBoxHeight ;

    /** 票单号 */
    @ApiModelProperty(value = "票单号")
    @ExcelProperty(value ="出货清单号")
    private String packCode ;

    /** 体积 */
    @ApiModelProperty(value = "体积")
//    @ExcelProperty(value ="体积")
    private BigDecimal packDetBoxVolume ;


    /** 编号 */
    @ApiModelProperty(value = "编号")
//    @ExcelProperty(value ="编号")
    private BigDecimal packDetId ;

 


 
    /** packDetBoxCode;箱号 */
    @ApiModelProperty(value = "packDetBoxCode")
//    @ExcelProperty(value ="packDetBoxCode")
    private String packDetBoxCode ;
 
    /** 规格单位 */
    @ApiModelProperty(value = "规格单位")
//    @ExcelProperty(value ="规格单位")
    private String packDetBoxSpeUnit ;
 
    /** 体积单位 */
    @ApiModelProperty(value = "体积单位")
//    @ExcelProperty(value ="体积单位")
    private String packDetBoxVoluUnit ;
 

    /** 重量单位 */
    @ApiModelProperty(value = "重量单位")
//    @ExcelProperty(value ="重量单位")
    private String packDetBoxWeigUnit ;
 
    /** 物流状态 */
    @ApiModelProperty(value = "物流状态")
//    @ExcelProperty(value ="物流状态")
    private String packDetBoxLogState ;
 
    /** 发货申请状态 */
    @ApiModelProperty(value = "发货申请状态")
//    @ExcelProperty(value ="发货申请状态")
    private String packDetBoxPlanState ;
 
    /** 头程单号 */
    @ApiModelProperty(value = "头程单号")
//    @ExcelProperty(value ="头程单号")
    private String packDetBoxFirLogNo ;
 
    /** 发货方式2 */
    @ApiModelProperty(value = "发货方式2")
//    @ExcelProperty(value ="发货方式2")
    private String logTraMode2 ;
 
    /** 尾程单号 */
    @ApiModelProperty(value = "尾程单号")
//    @ExcelProperty(value ="尾程单号")
    private String packDetBoxEndLogNo ;
 
    /** 账单状态 */
    @ApiModelProperty(value = "账单状态")
//    @ExcelProperty(value ="账单状态")
    private String packDetBoxBillState ;
 
    /** 物流对账单号 */
    @ApiModelProperty(value = "物流对账单号")
//    @ExcelProperty(value ="物流对账单号")
    private String packDetBoxLogCostNo ;
 
    /** 税费发票单号 */
    @ApiModelProperty(value = "税费发票单号")
//    @ExcelProperty(value ="税费发票单号")
    private String packDetBoxTaxCostNo ;
 
    /** 物流索赔状态 */
    @ApiModelProperty(value = "物流索赔状态")
//    @ExcelProperty(value ="物流索赔状态")
    private String packDetBoxLogClaimState ;
 
    /** ShipmentID */
    @ApiModelProperty(value = "ShipmentID")
//    @ExcelProperty(value ="ShipmentID")
    private String shipmentId ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1")
//    @ExcelProperty(value ="货运方式1")
    private String logTraMode1 ;


}