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
 * 物流体积重量;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流体积重量",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsVolumeWeightParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** lvwID */
    @ApiModelProperty(value = "lvwID")
    private BigDecimal lvwId ;
 
    /** sysAddDate */
    @ApiModelProperty(value = "sysAddDate")
    private Date sysAddDate ;
 
    /** sysPerName */
    @ApiModelProperty(value = "sysPerName")
    private String sysPerName ;
 
    /** sysUpdDatetime */
    @ApiModelProperty(value = "sysUpdDatetime")
    private Date sysUpdDatetime ;
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    private String lpCode ;
 
    /** 货运方式2 */
    @ApiModelProperty(value = "货运方式2")
    private String logTraMode2 ;
 
    /** 箱型 */
    @ApiModelProperty(value = "箱型")
    private String lvwBoxType ;
 
    /** 体积单位 */
    @ApiModelProperty(value = "体积单位")
    private String lvwUnit ;
 
    /** 体积重 */
    @ApiModelProperty(value = "体积重")
    private BigDecimal lvwVaule ;


}