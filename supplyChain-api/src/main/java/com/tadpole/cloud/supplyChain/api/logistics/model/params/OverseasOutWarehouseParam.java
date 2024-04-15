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
 * 海外仓出库管理入参类
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OverseasOutWarehouseParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 主表ID */
    @ApiModelProperty("主表ID")
    private BigDecimal parentId;

    /** 出库单号 */
    @ApiModelProperty("出库单号")
    private String outOrder;

    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** 账号 */
    @ApiModelProperty("账号")
    private String sysShopsName;

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    /** 业务类型 */
    @ApiModelProperty("业务类型")
    private String operateType;

    /** 应出库数量 */
    @ApiModelProperty("应出库数量")
    private BigDecimal shouldOutQuantity;

    /** SKU种类数量 */
    @ApiModelProperty("SKU种类数量")
    private BigDecimal skuNum;

    /** 总箱数 */
    @ApiModelProperty("总箱数")
    private BigDecimal totalPackageNum;

    /** 出货仓名称 */
    @ApiModelProperty("出货仓名称")
    private String outWarehouseName;

    /** 收货仓名称 */
    @ApiModelProperty("收货仓名称")
    private String inWarehouseName;

    /** 物流状态 未发货，部分发货，全部发货 */
    @ApiModelProperty("物流状态 未发货，部分发货，全部发货")
    private String logisticsStatus;

    /** 发货时间 */
    @ApiModelProperty("发货时间")
    private Date logisticsTime;

    /** 发货人 */
    @ApiModelProperty("发货人")
    private String logisticsUser;

    /** 备注 */
    @ApiModelProperty("备注")
    private String remark;

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

    /** 出货仓类型 */
    @ApiModelProperty("出货仓类型")
    private String outWarehouseType;

    /** 收货仓类型 */
    @ApiModelProperty("收货仓类型")
    private String inWarehouseType;

    /** 平台 */
    @ApiModelProperty("电商平台集合")
    private List<String> platforms;

    /** 账号 */
    @ApiModelProperty("账号集合")
    private List<String> sysShopsNames;

    /** 站点 */
    @ApiModelProperty("站点集合")
    private List<String> sysSites;

    /** 业务类型 */
    @ApiModelProperty("业务类型集合")
    private List<String>  operateTypes;

    /** 出货仓名称 */
    @ApiModelProperty("出货仓集合")
    private List<String>  outWarehouseNames;

    /** 收货仓名称 */
    @ApiModelProperty("收货仓集合")
    private List<String>  inWarehouseNames;

    /** 物流状态 */
    @ApiModelProperty("物流状态集合")
    private List<String>  logisticsStatusList;

    /** 新建出库任务导入明细 */
    @ApiModelProperty("新建出库任务导入明细")
    private List<OverseasOutWarehouseDetailParam> detailList;

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


    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 箱号 */
    @ApiModelProperty("箱号")
    private BigDecimal packageNum;

    /** 箱条码 */
    @ApiModelProperty("箱条码")
    private String packageBarCode;

    /** 出库单号集合 */
    @ApiModelProperty("出库单号集合")
    private List<String> outOrders;

    /** 收货仓平台 */
    @ApiModelProperty("收货仓平台")
    private String inPlatform;

    /** 收货仓账号 */
    @ApiModelProperty("收货仓账号")
    private String inSysShopsName;

    /** 收货仓站点 */
    @ApiModelProperty("收货仓站点")
    private String inSysSite;

    /** 盘点原因 */
    @ApiModelProperty("盘点原因")
    private String checkReason;
}