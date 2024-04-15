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
 * 电商平台仓储中心;
 * @author : LSY
 * @date : 2024-1-2
 */
@ApiModel(value = "电商平台仓储中心",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbEPlatStorageCenterParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    private BigDecimal id ;
 
    /** 添加时间;添加时间 */
    @ApiModelProperty(value = "添加时间")
    private Date sysAddDate ;
 
    /** 操作人;操作人 */
    @ApiModelProperty(value = "操作人")
    private String sysPerName ;
 
    /** 更新时间;更新时间 */
    @ApiModelProperty(value = "更新时间")
    private Date sysUpdDatetime ;
 
    /** 平台名称 */
    @ApiModelProperty(value = "平台名称")
    private String elePlatformName ;
 
    /** 国家编码 */
    @ApiModelProperty(value = "国家编码")
    private String countryCode ;
 
    /** FBA编码 */
    @ApiModelProperty(value = "FBA编码")
    private String fbaCode ;
 
    /** 城市 */
    @ApiModelProperty(value = "城市")
    private String city ;
 
    /** 状态 */
    @ApiModelProperty(value = "状态")
    private String state ;
 
    /** 邮编 */
    @ApiModelProperty(value = "邮编")
    private String zip ;
 
    /** 地址 */
    @ApiModelProperty(value = "地址")
    private String address ;


}