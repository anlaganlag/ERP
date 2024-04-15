package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 海外仓入库管理入参类
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
public class OverseasInWarehouseParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 主表ID */
    @ApiModelProperty("PARENT_ID")
    private BigDecimal parentId;

    /** 入库单号 */
    @ApiModelProperty("入库单号")
    private String inOrder;

    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** 账号 */
    @ApiModelProperty("账号")
    private String sysShopsName;

    /** 签收原账号（只针对亚马逊发海外仓JP站点） */
    @ApiModelProperty("签收原账号（只针对亚马逊发海外仓JP站点）")
    private String updateSysShopsName;

    /** 原账号签收状态：待签收、签收完成（只针对亚马逊发海外仓JP站点） */
    @ApiModelProperty(hidden = true)
    private String confirmUpdateStatus;

    /** 原账号签收数量（只针对亚马逊发海外仓JP站点） */
    @ApiModelProperty(hidden = true)
    private BigDecimal confirmUpdateQuantity;

    /** 签收新账号（只针对亚马逊发海外仓JP站点） */
    @ApiModelProperty("签收新账号（只针对亚马逊发海外仓JP站点）")
    private String newSysShopsName;

    /** 新账号签收状态：待签收、签收完成（只针对亚马逊发海外仓JP站点） */
    @ApiModelProperty(hidden = true)
    private String confirmNewStatus;

    /** 新账号签收数量（只针对亚马逊发海外仓JP站点） */
    @ApiModelProperty(hidden = true)
    private BigDecimal confirmNewQuantity;

    /** 签收平台（只针对亚马逊发海外仓JP站点签收TS账号） */
    @ApiModelProperty(hidden = true)
    private String newPlatform;

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    /** 业务类型 */
    @ApiModelProperty("业务类型")
    private String operateType;

    /** 应入库数量 */
    @ApiModelProperty("应入库数量")
    private BigDecimal shouldInventoryQuantity;

    /** 已到货数量 */
    @ApiModelProperty("已到货数量")
    private BigDecimal alreadyInventoryQuantity;

    /** 未到货数量 */
    @ApiModelProperty("未到货数量")
    private BigDecimal notInventoryQuantity;

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

    /** 签收状态 待签收，部分签收，全部签收完成 */
    @ApiModelProperty("签收状态")
    private String confirmStatus;

    /** 签收开始时间 */
    @ApiModelProperty("签收开始时间")
    private Date confirmStartTime;

    /** 签收完成时间 */
    @ApiModelProperty("签收完成时间")
    private Date confirmEndTime;

    /** 签收人，多个签收人用英文逗号分割 */
    @ApiModelProperty("签收人")
    private String confirmUser;

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

    /** 签收开始时间 */
    @ApiModelProperty("签收开始时间_开始时间")
    private String confirmStartDate;

    /** 签收完成时间 */
    @ApiModelProperty("签收开始时间_结束时间")
    private String confirmEndDate;

    /** 签收开始时间 */
    @ApiModelProperty("签收完成时间_开始时间")
    private String completeStartTime;

    /** 签收完成时间 */
    @ApiModelProperty("签收完成时间_结束时间")
    private String completeEndTime;

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

    /** 签收状态 */
    @ApiModelProperty("签收状态集合")
    private List<String>  confirmStatusList;

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
     * 调入TS组织编码（亚马逊仓发海外仓JP站点TS账号签收）
     */
    @ApiModelProperty(hidden = true)
    private String inTsOrg;

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

    /**
     * 调入TS组织名称（亚马逊仓发海外仓JP站点TS账号签收）
     */
    @ApiModelProperty(hidden = true)
    private String inTsOrgName;

    /** 箱条码 */
    @ApiModelProperty("箱条码")
    private String packageBarCode;

    /** SKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 入库单号集合 */
    @ApiModelProperty("入库单号集合")
    private List<String> inOrders;

    /** 物流单号 */
    @ApiModelProperty("物流单号")
    private String logisticsNum;

    /** 物流状态 */
    @ApiModelProperty("物流状态")
    private List<String> logisticsStatuss;

    /** 系统备注 */
    @ApiModelProperty(hidden = true)
    private String sysRemark;

    /** 盘点原因 */
    @ApiModelProperty("盘点原因")
    private String checkReason;

    /** 签收类型 0:国内仓发海外仓全部签收 1:国内仓发海外仓部分签收 2:亚马逊发海外仓签收 3:亚马逊发海外仓JP站点部分签收 */
    @ApiModelProperty(hidden = true)
    private String allSign;
}