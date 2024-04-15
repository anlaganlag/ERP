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
 * 物流单尾程信息-明细;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流单尾程信息-明细",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbLogisticsListToEndRouteDetParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    private BigDecimal id ;
 
    /** 发货批次号 */
    @ApiModelProperty(value = "发货批次号")
    private String lhrCode ;
 
    /** 头程物流单号 */
    @ApiModelProperty(value = "头程物流单号")
    private String lhrOddNumb ;
 
    /** 尾程物流单号 */
    @ApiModelProperty(value = "尾程物流单号")
    private String lerOddNumb ;
 
    /** 添加日期 */
    @ApiModelProperty(value = "添加日期")
    private Date sysAddDate ;
 
    /** 出货清单号 */
    @ApiModelProperty(value = "出货清单号")
    private String packCode ;
 
    /** 箱号 */
    @ApiModelProperty(value = "箱号")
    private Integer packDetBoxNum ;
 
    /** 箱条码 */
    @ApiModelProperty(value = "箱条码")
    private String packDetBoxCode ;
 
    /** FBA条码 */
    @ApiModelProperty(value = "FBA条码")
    private String shipmentId ;
 
    /** 物流跟踪状态 */
    @ApiModelProperty(value = "物流跟踪状态")
    private String lerdState ;



}