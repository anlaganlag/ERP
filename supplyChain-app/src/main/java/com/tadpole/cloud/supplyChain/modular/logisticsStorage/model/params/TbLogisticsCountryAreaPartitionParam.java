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
 * 物流国家区域划分;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流国家区域划分",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsCountryAreaPartitionParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    private BigDecimal id ;
 
    /** 添加时间 */
    @ApiModelProperty(value = "添加时间")
    private Date sysAddDate ;
 
    /** 操作人 */
    @ApiModelProperty(value = "操作人")
    private String sysPerName ;
 
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    private Date sysUpdDatetime ;
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    private String lpCode ;
 
    /** 国家编码 */
    @ApiModelProperty(value = "国家编码")
    private String countryCode ;
 
    /** 收货地区名称+收货分区 */
    @ApiModelProperty(value = "收货地区名称+收货分区")
    private String countryAreaName ;
 
    /** 分区号 */
    @ApiModelProperty(value = "分区号")
    private String lspNum ;


}