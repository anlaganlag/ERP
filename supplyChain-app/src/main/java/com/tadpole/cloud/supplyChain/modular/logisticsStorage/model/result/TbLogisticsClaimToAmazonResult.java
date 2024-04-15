package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

 /**
 * 亚马逊物流索赔;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "亚马逊物流索赔",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsClaimToAmazonResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 索赔系统编号(yyyyMMddHHmmssffff) */
    @ApiModelProperty(value = "索赔系统编号(yyyyMMddHHmmssffff)")
//    @ExcelProperty(value ="索赔系统编号(yyyyMMddHHmmssffff)")
    private String pkClaimId ;
 
    /** 创建日期 */
    @ApiModelProperty(value = "创建日期")
//    @ExcelProperty(value ="创建日期")
    private Date sysCreateDate ;
 
    /** 创建人编号 */
    @ApiModelProperty(value = "创建人编号")
//    @ExcelProperty(value ="创建人编号")
    private String sysCreatePerCode ;
 
    /** 创建人姓名 */
    @ApiModelProperty(value = "创建人姓名")
//    @ExcelProperty(value ="创建人姓名")
    private String sysCreatePerName ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    @ExcelProperty(value ="账号")
    private String busShopNameSimple ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @ExcelProperty(value ="站点")
    private String busCountryCode ;
 
    /** FBA号 */
    @ApiModelProperty(value = "FBA号")
    @ExcelProperty(value ="ShipmentID")
    private String busShipmentId ;
 
    /** FBA状态 */
    @ApiModelProperty(value = "FBA状态")
    @ExcelProperty(value ="FBA接收状态")
    private String busShipmentStatus ;
 
    /** ShipTo */
    @ApiModelProperty(value = "ShipTo")
    @ExcelProperty(value ="ShipTo")
    private String busShipTo ;
 
    /** Total SKUs */
    @ApiModelProperty(value = "Total SKUs")
    @ExcelProperty(value ="Total SKUs")
    private Integer busTotalSkus ;
 
    /** Total Units */
    @ApiModelProperty(value = "Total Units")
    @ExcelProperty(value ="Total Units")
    private Integer busTotalUnits ;
 
    /** Case 创建时间 */
    @ApiModelProperty(value = "Case 创建时间")
    @ExcelProperty(value ="Case创建日期")
    private Date busCaseCreateDate ;
 
    /** CaseID */
    @ApiModelProperty(value = "CaseID")
    @ExcelProperty(value ="CaseID")
    private String busCaseId ;
 
    /** Case 备注 */
    @ApiModelProperty(value = "Case 备注")
    @ExcelProperty(value ="Case备注")
    private String busCaseRemarks ;
 
    /** Case 创建人编号 */
    @ApiModelProperty(value = "Case 创建人编号")
//    @ExcelProperty(value ="Case 创建人编号")
    private String busCasePerCode ;
 
    /** Case 创建人姓名 */
    @ApiModelProperty(value = "Case 创建人姓名")
//    @ExcelProperty(value ="Case 创建人姓名")
    private String busCasePerName ;


}