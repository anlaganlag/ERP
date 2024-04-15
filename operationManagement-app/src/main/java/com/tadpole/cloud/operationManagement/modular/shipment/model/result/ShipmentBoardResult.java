package com.tadpole.cloud.operationManagement.modular.shipment.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.LogisticsDayResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
* <p>
    * 发货追踪汇总表
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
@TableName("SHIPMENT_TRACKING")
public class ShipmentBoardResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("待质检数量")
    private BigDecimal toCheckQty;


    @ApiModelProperty("联泰可调拨数量")
    private BigDecimal ytErpCanTransferQty;

    @ApiModelProperty("供应商可调拨数量")
    private BigDecimal supplierErpCanTransferQty;

    @ApiModelProperty("合计可调拨数量")
    private BigDecimal allErpCanTransferQty;



    @ApiModelProperty("联泰已申请未审核数量")
    private BigDecimal ytOccupyQty;

    @ApiModelProperty("供应商已申请未审核数量")
    private BigDecimal supplierOccupyQty;


    @ApiModelProperty("合计已申请未审核数量")
    private BigDecimal allOccupyQty;


    @ApiModelProperty("联泰实际可调拨数量")
    private BigDecimal ytCanTransferQty;

    @ApiModelProperty("供应商实际可调拨数量")
    private BigDecimal supplierCanTransferQty;


    @ApiModelProperty("合计实际可调拨数量")
    private BigDecimal allCanTransferQty;


    @ApiModelProperty("联泰仓待发数量")
    private BigDecimal ytToSendQty;

    @ApiModelProperty("供应商仓待发数量")
    private BigDecimal supplierToSendQty;

    @ApiModelProperty("合计仓待发数量")
    private BigDecimal allToSendQty;


    @ApiModelProperty("在途数量")
    private BigDecimal transitQty;


    @ApiModelProperty("预计到达日期")
    private String preArriveDate;



    @ApiModelProperty("待上架数量")
    private BigDecimal toShelveQty;


    @ApiModelProperty("来货数量")
    private BigDecimal arrivalQty;


    @ApiModelProperty("物流参考列表")
    private List<LogisticsDayResult>  logisticsInfoList;


    @ApiModelProperty("来货情况列表")
    private List<ShipmentBoardArrivalResult> arrivalList;



}
