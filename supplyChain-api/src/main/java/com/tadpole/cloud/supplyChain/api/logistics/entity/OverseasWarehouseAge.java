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
 * 海外仓库龄报表实体类
 * </p>
 *
 * @author ty
 * @since 2023-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("OVERSEAS_WAREHOUSE_AGE")
@ExcelIgnoreUnannotated
public class OverseasWarehouseAge implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

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

    /** 签收数量 */
    @TableField("SIGN_QUANTITY")
    private BigDecimal signQuantity;

    /** 账存数量 */
    @TableField("INVENTORY_QUANTITY")
    private BigDecimal inventoryQuantity;

    /** 签收时间 */
    @TableField("SIGN_DATE")
    private Date signDate;

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

    /** 初始数据来源标记 0：历史数据，1：系统数据 */
    @TableField("DATA_SOURCE")
    private String dataSource;

    /** 数据状态 0：出库，1：在库 */
    @TableField("DATA_STATUS")
    private String dataStatus;

    /** 备注 */
    @TableField("REMARK")
    private String remark;
}
