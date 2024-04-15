package com.tadpole.cloud.operationManagement.api.shopEntity.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;

 /**
 * 资源-公司实体账户信息;资源-公司实体账户信息
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-公司实体账户信息",description = "资源-公司实体账户信息")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbComEntityAccountParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** 账户编号 */
    @ApiModelProperty(value = "账户编号")
    private BigDecimal comAccId ;
    
    /** 公司名称（中文） */
    @ApiModelProperty(value = "公司名称（中文）")
    private String comNameCn ;
    
    /** 账户类型 */
    @ApiModelProperty(value = "账户类型")
    private String comAccType ;
    
    /** 一般户开户行 */
    @ApiModelProperty(value = "一般户开户行")
    private String comAccBank ;
    
    /** 一般户银行账号 */
    @ApiModelProperty(value = "一般户银行账号")
    private String comAccNo ;
    
    /** 币种 */
    @ApiModelProperty(value = "币种")
    private String comAccCurrency ;
    

}