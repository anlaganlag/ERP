package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 订单源报告数据监控响应参数
 * @date: 2022/5/6
 */
@Data
@ApiModel
@ExcelIgnoreUnannotated
public class OrderDataSourceMonitorResult implements Serializable, BaseValidatingParam {

    @ExcelProperty(value = "账号")
    @ApiModelProperty(value = "账号")
    private String sysShopsName;

    @ExcelProperty(value = "站点")
    @ApiModelProperty(value = "站点")
    private String sysSite;

    @ExcelProperty(value = "销售日期")
    @ApiModelProperty(value = "销售日期")
    private String purchaseDate;

    @ExcelProperty(value = "日均销售数量")
    @ApiModelProperty(value = "日均销售数量")
    private Integer saleDailyAvgQuantity;

    @ExcelProperty(value = "日销售数量")
    @ApiModelProperty(value = "日销售数量")
    private Integer saleDailyQuantity;

    @ExcelProperty(value = "销售数量环比")
    @ApiModelProperty(value = "销售数量环比")
    private String saleDailyQuantityMOM;

    @ExcelProperty(value = "日销售额")
    @ApiModelProperty(value = "日销售额")
    private BigDecimal saleDailyAmount;

    @ExcelProperty(value = "销售额环比")
    @ApiModelProperty(value = "销售额环比")
    private String saleDailyAmountMOM;

    @ExcelProperty(value = "正常订单")
    @ApiModelProperty(value = "正常订单")
    private Integer normalOrderQuantity;

    @ExcelProperty(value = "测评订单")
    @ApiModelProperty(value = "测评订单")
    private Integer testOrderQuantity;

    @ExcelProperty(value = "小包订单")
    @ApiModelProperty(value = "小包订单")
    private Integer smallOrderQuantity;

    @ExcelProperty(value = "补发订单")
    @ApiModelProperty(value = "补发订单")
    private Integer reissueOrderQuantity;

    @ExcelProperty(value = "移除/销毁订单")
    @ApiModelProperty(value = "移除/销毁订单")
    private Integer destroyOrderQuantity;

    @ExcelProperty(value = "非常规订单")
    @ApiModelProperty(value = "非常规订单")
    private Integer abnormalOrderQuantity;

    @ExcelProperty(value = "小平台订单")
    @ApiModelProperty(value = "小平台订单")
    private Integer smallPlatformOrderQuantity;

    @ExcelProperty(value = "取消订单")
    @ApiModelProperty(value = "取消订单")
    private Integer cancelOrderQuantity;

    @ExcelProperty(value = "其他订单")
    @ApiModelProperty(value = "其他订单")
    private Integer otherOrderQuantity;
}
