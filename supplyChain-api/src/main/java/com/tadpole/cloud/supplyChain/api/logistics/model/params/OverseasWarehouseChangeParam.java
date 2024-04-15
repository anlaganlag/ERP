package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 海外仓库存管理换标入参
 * @date: 2022/7/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("海外仓库存管理换标入参")
public class OverseasWarehouseChangeParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @NotNull(message = "ID不能为空")
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 换标数量 */
    @NotNull(message = "换标数量不能为空")
    @ApiModelProperty("换标数量")
    private BigDecimal inventoryQuantity;

    /** 事业部 */
    @NotBlank(message = "事业部不能为空")
    @ApiModelProperty("事业部")
    private String department;

    /** Team */
    @NotBlank(message = "Team组不能为空")
    @ApiModelProperty("Team组")
    private String team;

    /** 需求人员 */
    @NotBlank(message = "需求人员不能为空")
    @ApiModelProperty("需求人员")
    private String needsUser;

    /** 平台 */
    @NotBlank(message = "平台不能为空")
    @ApiModelProperty("平台")
    private String platform;

    /** 账号 */
    @NotBlank(message = "账号不能为空")
    @ApiModelProperty("账号")
    private String sysShopsName;

    /** 站点 */
    @NotBlank(message = "站点不能为空")
    @ApiModelProperty("站点")
    private String sysSite;

    /** 仓库名称 */
    @NotBlank(message = "仓库名称不能为空")
    @ApiModelProperty("仓库名称")
    private String warehouseName;

    /** FNSKU */
    @NotBlank(message = "FNSKU不能为空")
    @ApiModelProperty("FNSKU")
    private String fnSku;

    /** SKU */
    @NotBlank(message = "SKU不能为空")
    @ApiModelProperty("SKU")
    private String sku;

    /**
     * 调出组织编码
     */
    @ApiModelProperty(hidden = true)
    private String outOrg;

    /**
     * 调出组织名称
     */
    @ApiModelProperty(hidden = true)
    private String outOrgName;

    /**
     * 调入组织编码
     */
    @ApiModelProperty(hidden = true)
    private String inOrg;

    /**
     * 调入组织名称
     */
    @ApiModelProperty(hidden = true)
    private String inOrgName;

    /**
     * 是否跨组织
     */
    @ApiModelProperty(hidden = true)
    private Boolean isChangeOrg;

    /**
     * 是否跨站点换标
     */
    @ApiModelProperty(hidden = true)
    private Boolean isChangeSite;

    /**
     * 是否相同物料编码
     */
    @ApiModelProperty(hidden = true)
    private Boolean isChangeMaterialCode;

    /** 物料编码，后台通过校验标签获得 */
    @ApiModelProperty(hidden = true)
    private String materialCode;
}
