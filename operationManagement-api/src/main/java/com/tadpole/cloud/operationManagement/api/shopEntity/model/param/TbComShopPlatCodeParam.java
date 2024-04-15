package com.tadpole.cloud.operationManagement.api.shopEntity.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import lombok.*;
import java.lang.*;

/**
 * 资源-店铺平台编码;资源-店铺平台编码
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-店铺平台编码",description = "资源-店铺平台编码")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbComShopPlatCodeParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** 电商平台 */
    @ApiModelProperty(value = "电商平台")
    private String elePlatformName ;
    
    /** 编码 */
    @ApiModelProperty(value = "编码")
    private String code ;
    

}