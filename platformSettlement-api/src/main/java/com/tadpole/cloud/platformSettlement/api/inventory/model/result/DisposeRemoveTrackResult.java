package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: ty
 * @description: 销毁移除跟踪表多站点结果类
 * @date: 2023/3/3
 */
@Data
@ExcelIgnoreUnannotated
@ApiModel
public class DisposeRemoveTrackResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private BigDecimal id;

    @ApiModelProperty("唯一key")
    private String uniKey;

    @ApiModelProperty("分组key")
    private String groupKey;

    @ApiModelProperty("最新站点（REMOVAL_SHIPMENT_DETAIL刷REMOVAL_ORDER_DETAIL最新站点）")
    private String lastSysSite;

    @ApiModelProperty("销毁移除跟踪表销毁数量")
    private BigDecimal orderDisposedAmount;

    @ApiModelProperty("销毁移除跟踪表移除数量")
    private BigDecimal orderShippedAmount;

    @ApiModelProperty("移除货件表销毁数量")
    private BigDecimal disposeAmount;

    @ApiModelProperty("移除货件表移除数量")
    private BigDecimal removeAmount;

    @ApiModelProperty("移除货件表报告日期")
    private Date uploadDate;

    @ApiModelProperty("绝对值（订单和货件数量差的绝对值，扣减数量时按照绝对值最小优先扣取，扣完为止）")
    private BigDecimal numAbs;
}
