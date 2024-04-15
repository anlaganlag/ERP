package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;

 /**
 * 亚马逊发货申请记录-EBMS形成记录;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "亚马逊发货申请记录-EBMS形成记录",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsShipmentParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    private BigDecimal id ;
 
    /** 出货清单号 */
    @ApiModelProperty(value = "出货清单号")
    private String packCode ;
 
    /** 创建日期 */
    @ApiModelProperty(value = "创建日期")
    private Date sysAddDate ;
 
    /** 员工编号 */
    @ApiModelProperty(value = "员工编号")
    private String sysPerCode ;
 
    /** 员工姓名 */
    @ApiModelProperty(value = "员工姓名")
    private String sysPerName ;
 
    /** 店铺简称 */
    @ApiModelProperty(value = "店铺简称")
    private String shopNameSimple ;
 
    /** PlanName */
    @ApiModelProperty(value = "PlanName")
    private String planName ;
 
    /** ShipToCountry */
    @ApiModelProperty(value = "ShipToCountry")
    private String shipToCountry ;
 
    /** AddressName */
    @ApiModelProperty(value = "AddressName")
    private String addressName ;
 
    /** AddressFieldOne */
    @ApiModelProperty(value = "AddressFieldOne")
    private String addressFieldOne ;
 
    /** AddressFieldTwo */
    @ApiModelProperty(value = "AddressFieldTwo")
    private String addressFieldTwo ;
 
    /** AddressCity */
    @ApiModelProperty(value = "AddressCity")
    private String addressCity ;
 
    /** AddressCountryCode */
    @ApiModelProperty(value = "AddressCountryCode")
    private String addressCountryCode ;
 
    /** AddressStateOrRegion */
    @ApiModelProperty(value = "AddressStateOrRegion")
    private String addressStateOrRegion ;
 
    /** AddressDistrict */
    @ApiModelProperty(value = "AddressDistrict")
    private String addressDistrict ;
 
    /** AddressPostalCode */
    @ApiModelProperty(value = "AddressPostalCode")
    private String addressPostalCode ;
 
    /** 最后更新日期 */
    @ApiModelProperty(value = "最后更新日期")
    private Date sysUpdDatetime ;
 
    /** AGL */
    @ApiModelProperty(value = "AGL")
    private Integer busIsAgl ;
 
    /** Who will label */
    @ApiModelProperty(value = "Who will label")
    private String busWhoWillLabel ;
 
    /** Who will prep */
    @ApiModelProperty(value = "Who will prep")
    private String busWhoWillPrep ;
 
    /** 运输方式 */
    @ApiModelProperty(value = "运输方式")
    private String busShippingMethod ;
 
    /** 申请状态(待创建,完成创建) */
    @ApiModelProperty(value = "申请状态(待创建,完成创建)")
    private String busAppStatus ;
 
    /** 最大箱数 */
    @ApiModelProperty(value = "最大箱数")
    private Integer busMaxBoxNum ;


    /** 国家编码 */
    @ApiModelProperty(value = "countryCode")
    private String countryCode ;




}