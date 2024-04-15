package com.tadpole.cloud.operationManagement.modular.shipment.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * WareHouse 接受查询结果
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WareHouseReceiveResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("ShipmentID")
    private String shipmentId;

    @ApiModelProperty("ASIN")
    private String asin;

    @ApiModelProperty("FNSKU")
    private String fnsku;
    @ApiModelProperty("SKU")
    private String sku;

    @ApiModelProperty("站点")
    private String sysSite;


    @ApiModelProperty("接收数量")
    private BigDecimal receiveQty;

    /** SYS_SHOPS_NAME */
    @ApiModelProperty("账号-店铺简称")
    private String sysShopsName;

    @ApiModelProperty("最近一次接收日期")
    private Date businessDate;

    @ApiModelProperty("shipmentId + sku +  sysSite")
    private String mergeField;



}
