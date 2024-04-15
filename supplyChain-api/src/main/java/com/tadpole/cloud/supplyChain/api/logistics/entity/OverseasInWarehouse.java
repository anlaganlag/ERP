package com.tadpole.cloud.supplyChain.api.logistics.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* 海外仓入库管理
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
@TableName("OVERSEAS_IN_WAREHOUSE")
@ExcelIgnoreUnannotated
public class OverseasInWarehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 主表ID */
    @TableField("PARENT_ID")
    private BigDecimal parentId;

    /** 入库单号 */
    @TableField("IN_ORDER")
    private String inOrder;

    /** 平台 */
    @TableField("PLATFORM")
    private String platform;

    /** 账号 */
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** 站点 */
    @TableField("SYS_SITE")
    private String sysSite;

    /** 业务类型 */
    @TableField("OPERATE_TYPE")
    private String operateType;

    /** 应入库数量 */
    @TableField("SHOULD_INVENTORY_QUANTITY")
    private BigDecimal shouldInventoryQuantity;

    /** 已到货数量 */
    @TableField("ALREADY_INVENTORY_QUANTITY")
    private BigDecimal alreadyInventoryQuantity;

    /** 未到货数量 */
    @TableField("NOT_INVENTORY_QUANTITY")
    private BigDecimal notInventoryQuantity;

    /** SKU种类数量 */
    @TableField("SKU_NUM")
    private BigDecimal skuNum;

    /** 总箱数 */
    @TableField("TOTAL_PACKAGE_NUM")
    private BigDecimal totalPackageNum;

    /** 出货仓名称 */
    @TableField("OUT_WAREHOUSE_NAME")
    private String outWarehouseName;

    /** 收货仓名称 */
    @TableField("IN_WAREHOUSE_NAME")
    private String inWarehouseName;

    /** 签收状态 待签收、部分签收、签收完成 */
    @TableField("CONFIRM_STATUS")
    private String confirmStatus;

    /** 签收开始时间 */
    @TableField("CONFIRM_START_TIME")
    private Date confirmStartTime;

    /** 签收完成时间 */
    @TableField("CONFIRM_END_TIME")
    private Date confirmEndTime;

    /** 签收人，多个签收人用英文逗号分割 */
    @TableField("CONFIRM_USER")
    private String confirmUser;

    /** 备注 */
    @TableField("REMARK")
    private String remark;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @TableField("CREATE_USER")
    private String createUser;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @TableField("UPDATE_USER")
    private String updateUser;

    /** FBA退海外仓移除时间 */
    @TableField("SHIPMENT_DATE")
    private Date shipmentDate;

    /** 签收原账号（只针对亚马逊发海外仓JP站点） */
    @TableField("UPDATE_SYS_SHOPS_NAME")
    private String updateSysShopsName;

    /** 原账号签收状态：待签收、签收完成（只针对亚马逊发海外仓JP站点） */
    @TableField("CONFIRM_UPDATE_STATUS")
    private String confirmUpdateStatus;

    /** 原账号签收数量（只针对亚马逊发海外仓JP站点） */
    @TableField("CONFIRM_UPDATE_QUANTITY")
    private BigDecimal confirmUpdateQuantity;

    /** 原账号签收时间（只针对亚马逊发海外仓JP站点） */
    @TableField("CONFIRM_UPDATE_TIME")
    private Date confirmUpdateTime;

    /** 签收新账号（只针对亚马逊发海外仓JP站点） */
    @TableField("NEW_SYS_SHOPS_NAME")
    private String newSysShopsName;

    /** 新账号签收状态：待签收、签收完成（只针对亚马逊发海外仓JP站点） */
    @TableField("CONFIRM_NEW_STATUS")
    private String confirmNewStatus;

    /** 新账号签收数量（只针对亚马逊发海外仓JP站点） */
    @TableField("CONFIRM_NEW_QUANTITY")
    private BigDecimal confirmNewQuantity;

    /** 新账号签收时间（只针对亚马逊发海外仓JP站点） */
    @TableField("CONFIRM_NEW_TIME")
    private Date confirmNewTime;
}