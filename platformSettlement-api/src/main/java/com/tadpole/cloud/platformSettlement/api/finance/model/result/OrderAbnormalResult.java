package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: ty
 * @description: 订单异常情况汇总响应参数
 * @date: 2022/5/10
 */
@Data
@ApiModel
@ExcelIgnoreUnannotated
public class OrderAbnormalResult {

    @ExcelProperty(value = "销售日期")
    @ApiModelProperty(value = "销售日期")
    private String purchaseDate;

    @ExcelProperty(value = "发货日期")
    @ApiModelProperty(value = "发货日期")
    private String shippedDate;

    @ExcelProperty(value = "退货日期")
    @ApiModelProperty(value = "退货日期")
    private String returnDate;

    @ExcelProperty(value = "结算日期")
    @ApiModelProperty(value = "结算日期")
    private String settlementDate;

    @ExcelProperty(value = "账号")
    @ApiModelProperty(value = "账号")
    private String sysShopsName;

    @ExcelProperty(value = "站点")
    @ApiModelProperty(value = "站点")
    private String sysSite;

    @ExcelProperty(value = "交易ID")
    @ApiModelProperty(value = "交易ID")
    private String settlementId;

    @ExcelProperty(value = "发货渠道")
    @ApiModelProperty(value = "发货渠道")
    private String fulfillmentChannel;

    @ExcelProperty(value = "销售数量")
    @ApiModelProperty(value = "销售数量")
    private Integer saleQuantity;

    @ExcelProperty(value = "亚马逊发货数量")
    @ApiModelProperty(value = "亚马逊发货数量")
    private Integer shippedQuantity;

    @ExcelProperty(value = "销售出库发货数量")
    @ApiModelProperty(value = "销售出库发货数量")
    private Integer fbaShippedQuantity;

    @ExcelProperty(value = "是否发货异常")
    @ApiModelProperty(value = "是否发货异常")
    private String isShippedError;

    @ExcelProperty(value = "销售出库站点")
    @ApiModelProperty(value = "销售出库站点")
    private String outSite;

    @ExcelProperty(value = "是否异常")
    @ApiModelProperty(value = "是否异常")
    private String isError;

    @ExcelProperty(value = "销售退货数量")
    @ApiModelProperty(value = "销售退货数量")
    private Integer saleReturnQuantity;

    @ExcelProperty(value = "是否退货异常")
    @ApiModelProperty(value = "是否退货异常")
    private String isReturnError;

    @ExcelProperty(value = "结算数量")
    @ApiModelProperty(value = "结算数量")
    private Integer saleSettlementQuantity;

    @ExcelProperty(value = "是否结算异常")
    @ApiModelProperty(value = "是否结算异常")
    private String isSettlementError;
}
