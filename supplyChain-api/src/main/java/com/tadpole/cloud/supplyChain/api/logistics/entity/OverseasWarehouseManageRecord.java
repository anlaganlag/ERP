package com.tadpole.cloud.supplyChain.api.logistics.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *  海外仓管理操作记录实体类
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
@TableName("OVERSEAS_WAREHOUSE_MANAGE_RECORD")
@ExcelIgnoreUnannotated
public class OverseasWarehouseManageRecord implements Serializable {

    private static final long serialVersionUID = 1L;


    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 主表ID */
    @TableField("PARENT_ID")
    private BigDecimal parentId;

    /** 平台 */
    @TableField("PLATFORM")
    private String platform;

    /** 账号 */
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** 站点 */
    @TableField("SYS_SITE")
    private String sysSite;

    /** 仓库名称 */
    @TableField("WAREHOUSE_NAME")
    private String warehouseName;

    /** FNSKU */
    @TableField("FN_SKU")
    private String fnSku;

    /** SKU */
    @TableField("SKU")
    private String sku;

    /** 物料编码 */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /** 事业部 */
    @TableField("DEPARTMENT")
    private String department;

    /** Team */
    @TableField("TEAM")
    private String team;

    /** 需求人员 */
    @TableField("NEEDS_USER")
    private String needsUser;

    /** 原账存数量 */
    @TableField("INVENTORY_QUANTITY")
    private BigDecimal inventoryQuantity;

    /** 更新数量 */
    @TableField("CHANGE_INVENTORY_QUANTITY")
    private BigDecimal changeInventoryQuantity;

    /** 现账存数量 */
    @TableField("NOW_INVENTORY_QUANTITY")
    private BigDecimal nowInventoryQuantity;

    /** 操作类型 0：换标，1：盘点，2：乐天海外仓出库 */
    @TableField("OPERATE_TYPE")
    private String operateType;

    /** 入库单号 */
    @TableField("IN_ORDER")
    private String inOrder;

    /** 出库单号 */
    @TableField("OUT_ORDER")
    private String outOrder;

    /** 同步EBMS状态 0：未处理，1：处理成功，-1：处理失败 */
    @TableField("SYNC_EBMS_STATUS")
    private String syncEbmsStatus;

    /** 同步EBMS时间 */
    @TableField("SYNC_EBMS_TIME")
    private Date syncEbmsTime;

    /** 同步ERP状态 0：未处理，1：处理成功，-1：处理失败 */
    @TableField("SYNC_ERP_STATUS")
    private String syncErpStatus;

    /** 同步ERP时间 */
    @TableField("SYNC_ERP_TIME")
    private Date syncErpTime;

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

    /** 操作类型明细 */
    @TableField("OPERATE_TYPE_DETAIL")
    private String operateTypeDetail;

    /** K3组织调拨ID */
    @TableField("ORG_TRANSFER_ID")
    private String orgTransferId;

    /** K3组织调拨单号 */
    @TableField("ORG_TRANSFER_BILL_NO")
    private String orgTransferBillNo;

    /** 调出组织编码 */
    @TableField("OUT_ORG")
    private String outOrg;

    /** 调入组织编码 */
    @TableField("IN_ORG")
    private String inOrg;

    /** 原来货数量 */
    @TableField("COME_QUANTITY")
    private BigDecimal comeQuantity;

    /** 更新来货数量 */
    @TableField("CHANGE_COME_QUANTITY")
    private BigDecimal changeComeQuantity;

    /** 现来货数量 */
    @TableField("NOW_COME_QUANTITY")
    private BigDecimal nowComeQuantity;

    /** 类型 */
    @TableField("BUSINESS_TYPE")
    private String businessType;

    /** FBA源数据ID */
    @TableField("RSD_ID")
    private BigDecimal rsdId;

    /** 备注 */
    @TableField("REMARK")
    private String remark;

    /** 是否跨组织 */
    @TableField("IS_CHANGE_ORG")
    private String isChangeOrg;

    /** 是否相同物料编码 */
    @TableField("IS_CHANGE_MATERIAL_CODE")
    private String isChangeMaterialCode;

    /** 出库组织名称 */
    @TableField("OUT_ORG_NAME")
    private String outOrgName;

    /** 入库组织名称 */
    @TableField("IN_ORG_NAME")
    private String inOrgName;

    /** 收货仓名称（收货类型为沃尔玛仓） */
    @TableField("IN_WAREHOUSE_NAME")
    private String inWarehouseName;

    /** 箱号 */
    @TableField("PACKAGE_NUM")
    private BigDecimal packageNum;

    /** 调拨单号 */
    @TableField("PACK_DIRECT_CODE")
    private String packDirectCode;
}