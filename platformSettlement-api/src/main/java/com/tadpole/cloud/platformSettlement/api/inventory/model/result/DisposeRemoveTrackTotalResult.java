package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * @author: ty
 * @description: 销毁移除跟踪表汇总数量
 * @date: 2023/2/28
 */
@Data
@ExcelIgnoreUnannotated
@ApiModel
public class DisposeRemoveTrackTotalResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("申请数量（订单）")
    private String applyAmount;

    @ApiModelProperty("销毁数量（订单）")
    private String orderDisposedAmount;

    @ApiModelProperty("移除数量（订单）")
    private String orderShippedAmount;

    @ApiModelProperty("取消数量（订单）")
    private String cancelAmount;

    @ApiModelProperty("剩余数量（订单）")
    private String surplusAmount;

    @ApiModelProperty("销毁数量（货件）")
    private String disposeAmount;

    @ApiModelProperty("移除数量（货件）")
    private String removeAmount;
}
