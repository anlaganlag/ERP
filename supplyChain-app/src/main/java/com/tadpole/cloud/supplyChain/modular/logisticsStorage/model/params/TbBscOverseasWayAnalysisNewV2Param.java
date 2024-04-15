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
 * 发货单数据;
 * @author : LSY
 * @date : 2024-3-18
 */
@ApiModel(value = "发货单数据",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbBscOverseasWayAnalysisNewV2Param extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    private String sku ;
 
    /** 物料编码 */
    @ApiModelProperty(value = "物料编码")
    private String matCode ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    private String shopNameSimple ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    private String countryCode ;
 
    /** FBA号 */
    @ApiModelProperty(value = "FBA号")
    private String shipmentId ;
 
    /** 运输方式 */
    @ApiModelProperty(value = "运输方式")
    private String deliverType ;
 
    /** 状态 */
    @ApiModelProperty(value = "状态")
    private String status ;
 
    /** 货件实际状态 */
    @ApiModelProperty(value = "货件实际状态")
    private String shipmentRealStatus ;
 
    /** 出货数量 */
    @ApiModelProperty(value = "出货数量")
    private Integer deliveryNum ;
 
    /** 同步时间 */
    @ApiModelProperty(value = "同步时间")
    private Date syncTime ;


}