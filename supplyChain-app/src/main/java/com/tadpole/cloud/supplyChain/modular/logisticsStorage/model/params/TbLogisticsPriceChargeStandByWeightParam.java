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
 * 物流价格按重量计费的--暂时不需要;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流价格按重量计费的--暂时不需要",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsPriceChargeStandByWeightParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** lpcswID */
    @ApiModelProperty(value = "lpcswID")
    private BigDecimal lpcswId ;
 
    /** logpID */
    @ApiModelProperty(value = "logpID")
    private BigDecimal logpId ;
 
    /** 操作人 */
    @ApiModelProperty(value = "操作人")
    private String sysPerName ;
 
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    private Date sysUpdDatetime ;
 
    /** 重量大于 */
    @ApiModelProperty(value = "重量大于")
    private BigDecimal lpcswWeightL ;
 
    /** 重量小于等于 */
    @ApiModelProperty(value = "重量小于等于")
    private BigDecimal lpcswWeightH ;
 
    /** 币种 */
    @ApiModelProperty(value = "币种")
    private String lpcswCurrSystem ;
 
    /** 单价费用 */
    @ApiModelProperty(value = "单价费用")
    private BigDecimal lpcswUnitPrice ;
 
    /** 适用日期起 */
    @ApiModelProperty(value = "适用日期起")
    private Date lpcswAppDate ;


}