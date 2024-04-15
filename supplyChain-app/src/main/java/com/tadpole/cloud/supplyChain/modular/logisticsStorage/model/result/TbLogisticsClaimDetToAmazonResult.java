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
 * 亚马逊物流索赔明细;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "亚马逊物流索赔明细",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsClaimDetToAmazonResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 索赔明细系统编号 */
    @ApiModelProperty(value = "索赔明细系统编号")
    @ExcelProperty(value ="索赔明细系统编号")
    private BigDecimal pkClaimDetId ;
 
    /** 索赔系统编号(yyyyMMddHHmmssffff) */
    @ApiModelProperty(value = "索赔系统编号(yyyyMMddHHmmssffff)")
    @ExcelProperty(value ="索赔系统编号(yyyyMMddHHmmssffff)")
    private String pkClaimId ;
 
    /** 是否有POD */
    @ApiModelProperty(value = "是否有POD")
    @ExcelProperty(value ="是否有POD")
    private String busIsPod ;
 
    /** 发货仓 */
    @ApiModelProperty(value = "发货仓")
    @ExcelProperty(value ="发货仓")
    private String busComWarehouseName ;
 
    /** 签收日期 */
    @ApiModelProperty(value = "签收日期")
    @ExcelProperty(value ="签收日期")
    private Date busLerSignDate ;
 
    /** 物流单号 */
    @ApiModelProperty(value = "物流单号")
    @ExcelProperty(value ="物流单号")
    private String busLhrOddNumb ;
 
    /** 物流状态 */
    @ApiModelProperty(value = "物流状态")
    @ExcelProperty(value ="物流状态")
    private String busLhrState ;
 
    /** 货运方式 */
    @ApiModelProperty(value = "货运方式")
    @ExcelProperty(value ="货运方式")
    private String busLogTraMode2 ;
 
    /** SKU */
    @ApiModelProperty(value = "SKU")
    @ExcelProperty(value ="SKU")
    private String busSku ;
 
    /** 发货数量 */
    @ApiModelProperty(value = "发货数量")
    @ExcelProperty(value ="发货数量")
    private Integer busSendQty ;
 
    /** 物流待发 */
    @ApiModelProperty(value = "物流待发")
    @ExcelProperty(value ="物流待发")
    private Integer busStayDeliverQty ;
 
    /** 物流已发 */
    @ApiModelProperty(value = "物流已发")
    @ExcelProperty(value ="物流已发")
    private Integer busIssuedQty ;
 
    /** 接受数量 */
    @ApiModelProperty(value = "接受数量")
    @ExcelProperty(value ="接受数量")
    private Integer busReceiveQty ;
 
    /** 在途数量 */
    @ApiModelProperty(value = "在途数量")
    @ExcelProperty(value ="在途数量")
    private Integer busInTransitQty ;
 
    /** 接收差异 */
    @ApiModelProperty(value = "接收差异")
    @ExcelProperty(value ="接收差异")
    private Integer busDiscrepancy ;
 
    /** 备注 */
    @ApiModelProperty(value = "备注")
    @ExcelProperty(value ="备注")
    private String busRemark ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1")
    @ExcelProperty(value ="货运方式1")
    private String busLogTraMode1 ;


}