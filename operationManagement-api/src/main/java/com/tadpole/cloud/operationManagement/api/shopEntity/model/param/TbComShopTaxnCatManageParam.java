package com.tadpole.cloud.operationManagement.api.shopEntity.model.param;

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
 * 资源-税号类别管理;资源-税号类别管理
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-税号类别管理",description = "资源-税号类别管理")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbComShopTaxnCatManageParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** 收款银行账号变更编号 */
    @ApiModelProperty(value = "收款银行账号变更编号")
    private BigDecimal sysId ;
    
    /** 店铺名称 */
    @ApiModelProperty(value = "店铺名称")
    private String shopName ;
    
    /** 操作人姓名;接收人姓名 */
    @ApiModelProperty(value = "操作人姓名")
    private String sysOperatePerName ;
    
    /** 操作人编号;接收人编号 */
    @ApiModelProperty(value = "操作人编号")
    private String sysOperatePerCode ;
    
    /** 操作人时间;接收人时间 */
    @ApiModelProperty(value = "操作人时间")
    private Date sysOperateDate ;
    
    /** 税号类别 */
    @ApiModelProperty(value = "税号类别")
    private String taxnType ;
    
    /** 有效范围(开始) */
    @ApiModelProperty(value = "有效范围(开始)")
    private Date taxnEffectRangeStart ;
    
    /** 有效范围(结束) */
    @ApiModelProperty(value = "有效范围(结束)")
    private Date taxnEffectRangeEnd ;
    

}