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
 * 物流费用名称;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流费用名称",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsChargeNameParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    private BigDecimal id ;
 
    /** EBMS字典标准费用名称 */
    @ApiModelProperty(value = "EBMS字典标准费用名称")
    private String standardName ;
 
    /** 物流商费用名称 */
    @ApiModelProperty(value = "物流商费用名称")
    private String logisticsName ;
 
    /** 费用类型 */
    @ApiModelProperty(value = "费用类型")
    private String chargeType ;


}