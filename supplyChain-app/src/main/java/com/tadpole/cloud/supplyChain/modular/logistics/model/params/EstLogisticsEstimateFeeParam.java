package com.tadpole.cloud.supplyChain.modular.logistics.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import lombok.*;
import oracle.sql.DATE;

import java.lang.*;
import java.math.BigDecimal;

 /**
 * 物流费用测算;
 * @author : LSY
 * @date : 2024-3-14
 */
@ApiModel(value = "物流费用测算",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstLogisticsEstimateFeeParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** 测算id */
    @ApiModelProperty(value = "测算id")
    private String estId ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String perCode ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String perName ;
 
    /**  */
    @ApiModelProperty(value = "")
    private Date updatedTime ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String updatedBy ;
 
    /**  */
    @ApiModelProperty(value = "")
    private String createdBy ;
 
    /**  */
    @ApiModelProperty(value = "")
    private Date createdTime ;
 
    /**  */
    @ApiModelProperty(value = "")
    private Double totalQty ;
 
    /**  */
    @ApiModelProperty(value = "")
    private Double totalFee ;
 
    /**  */
    @ApiModelProperty(value = "")
    private Date estDate ;
 
    /**  */



}