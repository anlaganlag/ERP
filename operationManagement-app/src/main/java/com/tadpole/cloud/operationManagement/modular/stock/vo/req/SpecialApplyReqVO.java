package com.tadpole.cloud.operationManagement.modular.stock.vo.req;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: ty
 * @description: 保存特殊备货运营申请提交VO
 * @date: 2021/10/26
 */
@Api("保存特殊备货申请提交VO")
@Data
public class SpecialApplyReqVO {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("申请数量")
    private Integer applyNumber;

    @ApiModelProperty("申请原因")
    private String applyReason;

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


    @ApiModelProperty("TEAM审核记录编号--如已申请，记录本推荐记录对应的Team审核记录编号当运营人员申请提交时，反写")
    private String teamVerifNo;
}
