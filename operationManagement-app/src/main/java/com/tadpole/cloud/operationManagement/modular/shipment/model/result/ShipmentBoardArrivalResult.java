package com.tadpole.cloud.operationManagement.modular.shipment.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
* <p>
    * 追踪明细项-出货清单
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
@ExcelIgnoreUnannotated
@TableName("TRACKING_SHIPPING")
public class ShipmentBoardArrivalResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;




    @ApiModelProperty("物流单号")
    private String logisticsNumber;


    @ApiModelProperty("出货仓")
    private String deliverypoint;


    @ApiModelProperty("收货仓")
    private String receiveWarehouse;


    @ApiModelProperty("账号")
    private String sysShopsName;


    @ApiModelProperty("站点")
    private String sysSite;


    @ApiModelProperty("sku")
    private String sku;

    @ApiModelProperty("asin")
    private String asin;







    @ApiModelProperty("发货数量")
    private String shipingQty;

    @ApiModelProperty("发货数量")
    private String logisticsSendQty;


    @ApiModelProperty("接收数量")
    private String receiveQty;


    @ApiModelProperty("发货方式")
    private String logisticsMode;

    @ApiModelProperty("发货日期")
    private String logisticsSendDate;


    @ApiModelProperty("预计到达日期")
    private String preArriveDate;

    @ApiModelProperty("到货日期")
    private String signDate;


    @ApiModelProperty("备注")
    private String remark;




}
