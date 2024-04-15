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
 * 物流账单费用明细;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流账单费用明细",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsBillChargeDetialParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    private BigDecimal id ;
 
    /** 物流对账单号 */
    @ApiModelProperty(value = "物流对账单号")
    private String billNum ;
 
    /** 物流跟踪单号（头程物流单号） */
    @ApiModelProperty(value = "物流跟踪单号（头程物流单号）")
    private String lhrOddNum ;
 
    /** 费用类型 */
    @ApiModelProperty(value = "费用类型")
    private String chargeType ;
 
    /** 费用名称 */
    @ApiModelProperty(value = "费用名称")
    private String chargeName ;
 
    /** 金额原币 */
    @ApiModelProperty(value = "金额原币")
    private BigDecimal amountToOrigin ;
 
    /** 金额人民币 */
    @ApiModelProperty(value = "金额人民币")
    private BigDecimal amountToRmb ;
 
    /** 币种 */
    @ApiModelProperty(value = "币种")
    private String currency ;
 
    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remarks ;


}