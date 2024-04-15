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
public class QueryApplySiteParam extends BaseRequest implements Serializable,BaseValidatingParam{
private static final long serialVersionUID = 1L;
   /** 平台 */
   @ApiModelProperty(value = "平台")
   private String platform ;


   /** 账号 */
   @ApiModelProperty(value = "账号")
   private String shopName ;




}