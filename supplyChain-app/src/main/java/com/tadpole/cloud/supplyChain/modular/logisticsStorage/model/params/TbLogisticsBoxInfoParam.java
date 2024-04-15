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
 * 物流箱信息-长宽高重;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流箱信息-长宽高重",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsBoxInfoParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** 箱号 */
    @ApiModelProperty(value = "箱号")
    private BigDecimal packDetBoxNum ;
 
    /** 箱型 */
    @ApiModelProperty(value = "箱型")
    private String packDetBoxType ;
 
    /** 箱长 */
    @ApiModelProperty(value = "箱长")
    private BigDecimal packDetBoxLength ;
 
    /** 箱宽 */
    @ApiModelProperty(value = "箱宽")
    private BigDecimal packDetBoxWidth ;
 
    /** 箱高 */
    @ApiModelProperty(value = "箱高")
    private BigDecimal packDetBoxHeight ;
 
    /** 规格单位 */
    @ApiModelProperty(value = "规格单位")
    private String packDetBoxSpeUnit ;
 
    /** 体积单位 */
    @ApiModelProperty(value = "体积单位")
    private String packDetBoxVoluUnit ;
 
    /** 体积 */
    @ApiModelProperty(value = "体积")
    private BigDecimal packDetBoxVolume ;
 
    /** 重量单位 */
    @ApiModelProperty(value = "重量单位")
    private String packDetBoxWeigUnit ;


}