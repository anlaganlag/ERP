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
 * 物流价格--暂时不需要;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流价格--暂时不需要",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsPriceParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** logpID */
    @ApiModelProperty(value = "logpID")
    private BigDecimal logpId ;
 
    /** 添加时间 */
    @ApiModelProperty(value = "添加时间")
    private Date sysAddDate ;
 
    /** 操作人 */
    @ApiModelProperty(value = "操作人")
    private String sysPerName ;
 
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    private Date sysUpdDatetime ;
 
    /** 物流账号 */
    @ApiModelProperty(value = "物流账号")
    private String lcCode ;
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    private String lpCode ;
 
    /** 物流商简称 */
    @ApiModelProperty(value = "物流商简称")
    private String lpSimpleName ;
 
    /** 国家编码 */
    @ApiModelProperty(value = "国家编码")
    private String countryCode ;
 
    /** 国家分区 */
    @ApiModelProperty(value = "国家分区")
    private String lspNum ;
 
    /** 收货分区 */
    @ApiModelProperty(value = "收货分区")
    private String countryAreaName ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1")
    private String logTraMode1 ;
 
    /** 货运方式2 */
    @ApiModelProperty(value = "货运方式2")
    private String logTraMode2 ;
 
    /** 物流渠道 */
    @ApiModelProperty(value = "物流渠道")
    private String logSeaTraRoute ;
 
    /** 海运货柜类型 */
    @ApiModelProperty(value = "海运货柜类型")
    private String logSeaTraConType ;
 
    /** 红蓝单 */
    @ApiModelProperty(value = "红蓝单")
    private String logRedOrBlueList ;
 
    /** 货物特性 */
    @ApiModelProperty(value = "货物特性")
    private String logGoodCharacter ;
 
    /** 是否含税 */
    @ApiModelProperty(value = "是否含税")
    private Integer logpIsIncTax ;
 
    /** 计费方式 */
    @ApiModelProperty(value = "计费方式")
    private String logpChargType ;


}