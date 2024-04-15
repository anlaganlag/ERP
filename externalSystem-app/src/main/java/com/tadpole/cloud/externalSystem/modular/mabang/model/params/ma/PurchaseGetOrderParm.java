package com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 采购单查询
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseGetOrderParm {

    @ApiModelProperty("采购单状态，1：1688对帐异常 2:异常 3:已作废 4:已完成 5:采购中 6:新订单")
    private String flag;

    @ApiModelProperty("最后修改时间开始时间")
    private String operTimeStart;

    @ApiModelProperty("最后修改时间结束时间")
    private Date operTimeEnd;

    @ApiModelProperty("创建时间开始时间")
    private Date createTimeStart;

    @ApiModelProperty("创建时间结束时间")
    private Date createTimeEnd;

    @ApiModelProperty("采购单号")
    private String groupId;

    @ApiModelProperty("自定义单号")
    private String orderBillNO;

    @ApiModelProperty("当前页数，默认1")
    private int page;

    @ApiModelProperty("供应商名称")
    private String providerName;
}
