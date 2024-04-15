package com.tadpole.cloud.operationManagement.modular.stock.vo.req;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: ty
 * @description: 保存日常备货运营申请提交VO
 * @date: 2021/10/26
 */
@Api("保存日常备货运营申请提交VO")
@Data
public class OperApplyReqVO {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("申请数量")
    private Integer applyNumber;

    @ApiModelProperty("申请原因")
    private String stockReason;

    @ApiModelProperty("需求备货天数")
    private Integer requireDay;

    @ApiModelProperty("销售需求")
    private Integer salesRequire;

    @ApiModelProperty("平台")
    private String platform;

    @ApiModelProperty("Team")
    private String Team;

    @ApiModelProperty("物料编码")
    private String materialCode;

    @ApiModelProperty("区域")
    private String area;

    @ApiModelProperty("状态 0：保存，1：提交，2:已申请，3：不备货")
    private Integer stockStatus;

    @ApiModelProperty("备货后周转天数")
    private Integer purchaseTurnoverDay;

    @ApiModelProperty("需求人,前端不用传值，后端设值")
    private String requireBy;

    @ApiModelProperty("需求人名称,前端不用传值，后端设值")
    private String requireByName;

    @ApiModelProperty("销售需求覆盖日期")
    private Date operCoversSalesDate;
}
