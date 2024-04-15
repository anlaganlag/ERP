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
 * Amazon在途库存报表;
 * @author : LSY
 * @date : 2024-3-18
 */
@ApiModel(value = "Amazon在途库存报表",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbAmazonInGoodsQtyNewV2Param extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    private BigDecimal id ;
 
    /** SKU */
    @ApiModelProperty(value = "SKU")
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
 
    /** 统计日期 */
    @ApiModelProperty(value = "统计日期")
    private Date sysStatisticalDate ;
 
    /** 总来货数量 */
    @ApiModelProperty(value = "总来货数量")
    private Integer allQty ;
 
    /** 物流待发数 */
    @ApiModelProperty(value = "物流待发数")
    private Integer stayDeliverQty ;
 
    /** 海运 */
    @ApiModelProperty(value = "海运")
    private Integer shippingQty ;
 
    /** 铁运 */
    @ApiModelProperty(value = "铁运")
    private Integer trainQty ;
 
    /** 卡航 */
    @ApiModelProperty(value = "卡航")
    private Integer carQty ;
 
    /** 空运 */
    @ApiModelProperty(value = "空运")
    private Integer airQty ;
 
    /** 同步时间 */
    @ApiModelProperty(value = "同步时间")
    private Date syncTime ;


}