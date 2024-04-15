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
 * 平台货件接收地址;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "平台货件接收地址",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsShipToRecParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    private BigDecimal lfrId ;
 
    /** 添加日期 */
    @ApiModelProperty(value = "添加日期")
    private Date sysAddDate ;
 
    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    private String sysPerName ;
 
    /** 最后更新日期 */
    @ApiModelProperty(value = "最后更新日期")
    private Date sysUpdDatetime ;
 
    /** 电商平台 */
    @ApiModelProperty(value = "电商平台")
    private String elePlatformName ;
 
    /** 国家/地区 */
    @ApiModelProperty(value = "国家/地区")
    private String countryCode ;
 
    /** 城市 */
    @ApiModelProperty(value = "城市")
    private String city ;
 
    /** 州/省/郡 */
    @ApiModelProperty(value = "州/省/郡")
    private String state ;
 
    /** 收货地址1 */
    @ApiModelProperty(value = "收货地址1")
    private String logRecAddress1 ;
 
    /** 收货地址2 */
    @ApiModelProperty(value = "收货地址2")
    private String logRecAddress2 ;
 
    /** 收货地址3 */
    @ApiModelProperty(value = "收货地址3")
    private String logRecAddress3 ;
 
    /** 邮政编码 */
    @ApiModelProperty(value = "邮政编码")
    private String logRecZip ;
 
    /** 收件人 */
    @ApiModelProperty(value = "收件人")
    private String logRecPerson ;
 
    /** 收件公司 */
    @ApiModelProperty(value = "收件公司")
    private String logRecCompany ;
 
    /** 收件电话 */
    @ApiModelProperty(value = "收件电话")
    private String logRecPersonTel ;
 
    /** 收货仓名称 */
    @ApiModelProperty(value = "收货仓名称")
    private String logRecHouseName ;


}