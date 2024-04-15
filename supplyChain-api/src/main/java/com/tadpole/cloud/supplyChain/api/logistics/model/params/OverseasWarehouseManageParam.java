package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 海外仓库存管理入参类
 * </p>
 *
 * @author cyt
 * @since 2022-07-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OverseasWarehouseManageParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** 账号 */
    @ApiModelProperty("账号")
    private String sysShopsName;

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    /** 仓库名称 */
    @ApiModelProperty("仓库名称")
    private String warehouseName;

    /** FNSKU */
    @ApiModelProperty("FNSKU")
    private String fnSku;

    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 来货数量 */
    @ApiModelProperty("来货数量")
    private BigDecimal comeQuantity;

    /** 账存数量 */
    @ApiModelProperty("账存数量")
    private BigDecimal inventoryQuantity;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updateUser;

    /** 平台 */
    @ApiModelProperty("平台集合")
    private List<String> platforms;

    /** 账号 */
    @ApiModelProperty("账号集合")
    private List<String> sysShopsNames;

    /** 站点 */
    @ApiModelProperty("站点集合")
    private List<String> sysSites;

    /** 仓库名称 */
    @ApiModelProperty("仓库名称集合")
    private List<String> warehouseNames;

    /** 事业部 */
    @ApiModelProperty("事业部")
    private String department;

    /** Team */
    @ApiModelProperty("Team组")
    private String team;

    /** 备注 */
    @ApiModelProperty("备注")
    private String remark;

    /** 六位码 */
    @ApiModelProperty("六位码")
    private String sixCode;

    /**
     * 调出组织编码
     */
    @ApiModelProperty(hidden = true)
    private String outOrg;

    /**
     * 调入组织编码
     */
    @ApiModelProperty(hidden = true)
    private String inOrg;

    /**
     * 调出组织名称
     */
    @ApiModelProperty(hidden = true)
    private String outOrgName;

    /**
     * 调入组织名称
     */
    @ApiModelProperty(hidden = true)
    private String inOrgName;

    /** 盘点原因 */
    @ApiModelProperty("盘点原因")
    private String checkReason;
}