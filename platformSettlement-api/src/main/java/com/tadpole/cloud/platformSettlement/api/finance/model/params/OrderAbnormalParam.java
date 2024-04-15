package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: ty
 * @description: 订单异常情况汇总请求参数
 * @date: 2022/5/10
 */
@Data
@ApiModel
public class OrderAbnormalParam extends BaseRequest {
    @ApiModelProperty(value = "账号")
    private String sysShopsName;

    @ApiModelProperty(value = "站点")
    private String sysSite;

    @ApiModelProperty(value = "交易ID")
    private String settlementId;

    @ApiModelProperty(value = "交易ID")
    private List<String> settlementIdList;

    @ApiModelProperty(value = "发货日期开始时间")
    private String shippedDateStart;

    @ApiModelProperty(value = "发货日期结束时间")
    private String shippedDateEnd;

    @ApiModelProperty(value = "退货日期开始时间")
    private String returnDateStart;

    @ApiModelProperty(value = "退货日期结束时间")
    private String returnDateEnd;

    @ApiModelProperty(value = "结算日期开始时间")
    private String settlementDateStart;

    @ApiModelProperty(value = "结算日期结束时间")
    private String settlementDateEnd;

    @ApiModelProperty(value = "销售日期开始时间")
    private String purchaseDateStart;

    @ApiModelProperty(value = "销售日期结束时间")
    private String purchaseDateEnd;

    @ApiModelProperty(value = "异常情况")
    private String errorType;
}
