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
 * 来货报告;
 * @author : LSY
 * @date : 2024-3-18
 */
@ApiModel(value = "来货报告",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbReceivedInvenrotyAnalysisV2Param extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    private String countryCode ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    private String shopNameSimple ;
 
    /** sku */
    @ApiModelProperty(value = "sku")
    private String itemSku ;
 
    /** ShipmentID */
    @ApiModelProperty(value = "ShipmentID")
    private String shipmentId ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    private Integer quantity ;
 
    /** 同步时间 */
    @ApiModelProperty(value = "同步时间")
    private Date syncTime ;


}