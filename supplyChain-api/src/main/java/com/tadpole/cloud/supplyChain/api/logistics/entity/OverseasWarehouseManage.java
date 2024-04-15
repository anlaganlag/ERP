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
 * 海外仓管理实体类
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
@TableName("OVERSEAS_WAREHOUSE_MANAGE")
@ExcelIgnoreUnannotated
public class OverseasWarehouseManage implements Serializable {

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

    /** 来货数量 */
    @TableField("COME_QUANTITY")
    private BigDecimal comeQuantity;

    /** 账存数量 */
    @TableField("INVENTORY_QUANTITY")
    private BigDecimal inventoryQuantity;

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

    /** 初始数据来源标记 0：手动添加，1：FBA发海外仓，2：国内仓发海外仓，3：换标 */
    @TableField("DATA_SOURCE")
    private String dataSource;
}