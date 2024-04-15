package com.tadpole.cloud.operationManagement.modular.stock.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: ty
 * @description:
 * @date: 2021/12/15
 */
@Data
public class StockAuditReqVO {

    @ApiModelProperty("申请id")
    private int id;

    @ApiModelProperty("审核id")
    private int applyId;

    @ApiModelProperty("事业部")
    private String department;

    @ApiModelProperty("期望日期")
    private String expectedDate;

    @ApiModelProperty("物料编码")
    private String materialCode;

    @ApiModelProperty("平台")
    private String platformName;

    @ApiModelProperty("采购原因")
    private String purchaseReason;

    @ApiModelProperty("备货后周转天数")
    private Integer purchaseTurnoverDay;

    @ApiModelProperty("备货状态")
    private Integer status;

    @ApiModelProperty("实际运输方式")
    private String transportTypeSujust;

    @ApiModelProperty("审核数量")
    private Integer verifyNumber;

    @ApiModelProperty("审核备注")
    private String verifyRemark;

    @ApiModelProperty("是否提交审核 0：保存，1：提交")
    private String isCommitAudit;
}
