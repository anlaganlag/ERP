package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: cyt
 * @description: 结算异常情况汇总请求参数
 * @date: 2022/5/13
 */
@Data
@ApiModel
public class SettlementAbnormalParam extends BaseRequest {
    @ApiModelProperty(value = "账号")
    private String shopName;

    @ApiModelProperty(value = "站点")
    private String site;

    @ApiModelProperty(value = "交易ID")
    private String amazonOrderId;

    @ApiModelProperty(value = "结算日期开始时间")
    private String startTime;

    @ApiModelProperty(value = "结算日期结束时间")
    private String endTime;
}
