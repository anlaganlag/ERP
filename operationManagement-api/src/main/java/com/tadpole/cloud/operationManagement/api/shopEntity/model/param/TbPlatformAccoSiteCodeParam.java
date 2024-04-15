package com.tadpole.cloud.operationManagement.api.shopEntity.model.param;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

 /**
 * 资源-平台-账号-站点-对应编码配置;资源-税号管理
 * @author : LSY
 * @date : 2023-8-3
 */
@ApiModel(value = "资源-平台-账号-站点-对应编码配置",description = "资源-税号管理")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbPlatformAccoSiteCodeParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** 名称 */
    @ApiModelProperty(value = "名称")
    private String name ;
    
    /** 编码值 */
    @ApiModelProperty(value = "编码值")
    private String code ;
    
    /** 类型;类型:平台、账号、站点 */
    @ApiModelProperty(value = "类型")
    private String type ;
    

}