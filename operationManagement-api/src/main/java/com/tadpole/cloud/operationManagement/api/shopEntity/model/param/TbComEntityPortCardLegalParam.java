package com.tadpole.cloud.operationManagement.api.shopEntity.model.param;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

 /**
 * 资源-公司实体银行设备口岸卡法人;资源-公司实体银行设备口岸卡法人
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-公司实体银行设备口岸卡法人",description = "资源-公司实体银行设备口岸卡法人")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbComEntityPortCardLegalParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** 编号 */
    @ApiModelProperty(value = "编号")
    private BigDecimal pkCode ;
    
    /** 编号2;编号 */
    @ApiModelProperty(value = "编号2")
    private BigDecimal pkBeCode ;
    
    /** 口岸卡-法人的卡号 */
    @ApiModelProperty(value = "口岸卡-法人的卡号")
    private String busPortCardLegalCardNo ;
    

}