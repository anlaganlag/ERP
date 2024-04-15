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
 * 发货汇总表;
 * @author : LSY
 * @date : 2024-1-10
 */
@ApiModel(value = "发货汇总表",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbBscOverseasWayParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /**  */
    @ApiModelProperty(value = "")
    private BigDecimal id ;
 
    /** sku */
    @ApiModelProperty(value = "sku")
    private String sku ;
 
    /** DELIVER_STATUS */
    @ApiModelProperty(value = "DELIVER_STATUS")
    private String packCode ;
 
    /** 物流发货状态 */
    @ApiModelProperty(value = "物流发货状态")
    private String deliverStatus ;
 
    /** ERP回传状态 */
    @ApiModelProperty(value = "ERP回传状态")
    private String returnStatus ;
 
    /** FBA状态 */
    @ApiModelProperty(value = "FBA状态")
    private String status ;
 
    /** 物料代码 */
    @ApiModelProperty(value = "物料代码")
    private String matCode ;
 
    /** 出货数量 */
    @ApiModelProperty(value = "出货数量")
    private Integer deliveryNum ;
 
    /** 货运方式 */
    @ApiModelProperty(value = "货运方式")
    private String deliverType ;
 
    /** 业务数据：1 历史数据：2  */
    @ApiModelProperty(value = "业务数据：1 历史数据：2 ")
    private String owName ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    private String countryCode ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    private String shopNameSimple ;
 
    /** FBA_SHIPMENT_ID */
    @ApiModelProperty(value = "FBA_SHIPMENT_ID")
    private String shipmentId ;
 
    /** 平台 */
    @ApiModelProperty(value = "平台")
    private String plateForm ;
 
    /** 收货仓名称 */
    @ApiModelProperty(value = "收货仓名称")
    private String receiveWarehouse ;
 
    /** 出货仓名称 */
    @ApiModelProperty(value = "出货仓名称")
    private String deliverWarehouse ;
 
    /** 回传时间 */
    @ApiModelProperty(value = "回传时间")
    private Date updateTime ;
 
    /** 箱号 */
    @ApiModelProperty(value = "箱号")
    private Integer packDetBoxNum ;
 
    /** 货件实际状态 */
    @ApiModelProperty(value = "货件实际状态")
    private String shipmentRealStatus ;


}