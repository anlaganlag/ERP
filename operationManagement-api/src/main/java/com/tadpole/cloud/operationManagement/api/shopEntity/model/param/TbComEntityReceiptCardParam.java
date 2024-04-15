package com.tadpole.cloud.operationManagement.api.shopEntity.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;

 /**
 * 资源-公司实体银行设备回单卡;资源-公司实体银行设备回单卡
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-公司实体银行设备回单卡",description = "资源-公司实体银行设备回单卡")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbComEntityReceiptCardParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** 编号 */
    @ApiModelProperty(value = "编号")
    private BigDecimal pkCode ;
    
    /** 编号2;编号 */
    @ApiModelProperty(value = "编号2")
    private BigDecimal pkBeCode ;
    
    /** 回单卡的开户行 */
    @ApiModelProperty(value = "回单卡的开户行")
    private String busReceiptCardBrank ;
    
    /** 回单卡的卡号 */
    @ApiModelProperty(value = "回单卡的卡号")
    private String busReceiptCardNo ;
    
    /** 回单卡的状态 */
    @ApiModelProperty(value = "回单卡的状态")
    private String busReceiptCardStatus ;
    

}