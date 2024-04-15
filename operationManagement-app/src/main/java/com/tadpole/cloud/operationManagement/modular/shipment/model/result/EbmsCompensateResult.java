package com.tadpole.cloud.operationManagement.modular.shipment.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* <p>
    * EBM赔偿查询结果
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
public class EbmsCompensateResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("ShipmentID")
    private String shipmentId;

    @ApiModelProperty("物流单号")
    private String logisticsNumber;

    @ApiModelProperty("SKU")
    private String sku;

    @ApiModelProperty("接收数量")
    private BigDecimal receiveQty;

    @ApiModelProperty("发货仓库名称：国内仓，海外仓")
    private String busComWarehouseName;

    @ApiModelProperty("shipmentId + logisticsNumber + sku 更新接收数量")
    private String mergeField;



}
