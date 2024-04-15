package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.*;
import java.math.BigDecimal;

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
public class TbLogisticsClaimToAmazonParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** 索赔系统编号(yyyyMMddHHmmssffff) */
    @ApiModelProperty(value = "索赔系统编号(yyyyMMddHHmmssffff)")
    private String pkClaimId ;
 
    /** 创建日期 */
    @ApiModelProperty(value = "创建日期")
    private Date sysCreateDate ;
 
    /** 创建人编号 */
    @ApiModelProperty(value = "创建人编号")
    private String sysCreatePerCode ;
 
    /** 创建人姓名 */
    @ApiModelProperty(value = "创建人姓名")
    private String sysCreatePerName ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    private String busShopNameSimple ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    private String busCountryCode ;
 
    /** FBA号 */
    @ApiModelProperty(value = "FBA号")
    private String busShipmentId ;
 
    /** FBA状态 */
    @ApiModelProperty(value = "FBA状态")
    private String busShipmentStatus ;
 
    /** ShipTo */
    @ApiModelProperty(value = "ShipTo")
    private String busShipTo ;
 
    /** Total SKUs */
    @ApiModelProperty(value = "Total SKUs")
    private Integer busTotalSkus ;
 
    /** Total Units */
    @ApiModelProperty(value = "Total Units")
    private Integer busTotalUnits ;
 
    /** Case 创建时间 */
    @ApiModelProperty(value = "Case 创建时间")
    private Date busCaseCreateDate ;
 
    /** CaseID */
    @ApiModelProperty(value = "CaseID")
    private String busCaseId ;
 
    /** Case 备注 */
    @ApiModelProperty(value = "Case 备注")
    private String busCaseRemarks ;
 
    /** Case 创建人编号 */
    @ApiModelProperty(value = "Case 创建人编号")
    private String busCasePerCode ;
 
    /** Case 创建人姓名 */
    @ApiModelProperty(value = "Case 创建人姓名")
    private String busCasePerName ;


    /** 查询传入参数 busSKU */
    @ApiModelProperty(value = "查询传入参数 busSKU")
    private String busSKU ;


    /** 查询Case 创建时间 --开始时间 */
    @ApiModelProperty(value = "Case 查询Case 创建时间 --开始时间")
    private Date endDateTime ;


    /** 查询Case 创建时间 --结束时间 */
    @ApiModelProperty(value = "Case 查询Case 创建时间 --结束时间")
    private Date startDateTime ;


}