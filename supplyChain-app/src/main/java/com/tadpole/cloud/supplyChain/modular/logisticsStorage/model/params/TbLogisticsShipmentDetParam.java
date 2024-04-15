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
 * 亚马逊发货申请记录-明细项;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "亚马逊发货申请记录-明细项",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsShipmentDetParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** 编号 */
    @ApiModelProperty(value = "编号")
    private BigDecimal shipDetId ;
 
    /** 出货清单号 */
    @ApiModelProperty(value = "出货清单号")
    private String packCode ;
 
    /** PlanName */
    @ApiModelProperty(value = "PlanName")
    private String planName ;
 
    /** MerchantSKU */
    @ApiModelProperty(value = "MerchantSKU")
    private String merchantSku ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    private Integer quantity ;


}