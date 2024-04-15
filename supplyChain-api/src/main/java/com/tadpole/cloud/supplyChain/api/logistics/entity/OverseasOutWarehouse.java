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
*  海外仓出库管理
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
@TableName("OVERSEAS_OUT_WAREHOUSE")
@ExcelIgnoreUnannotated
public class OverseasOutWarehouse implements Serializable {

    private static final long serialVersionUID = 1L;


    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 主表ID */
    @TableField("PARENT_ID")
    private BigDecimal parentId;

    /** 出库单号 */
    @TableField("OUT_ORDER")
    private String outOrder;

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

    /** 应出库数量 */
    @TableField("SHOULD_OUT_QUANTITY")
    private BigDecimal shouldOutQuantity;

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

    /** 物流状态 0：未发货，1：部分发货，2：全部发货 */
    @TableField("LOGISTICS_STATUS")
    private String logisticsStatus;

    /** 发货时间 */
    @TableField("LOGISTICS_TIME")
    private Date logisticsTime;

    /** 发货人 */
    @TableField("LOGISTICS_USER")
    private String logisticsUser;

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

    /** 出货仓类型 */
    @TableField("OUT_WAREHOUSE_TYPE")
    private String outWarehouseType;

    /** 收货仓类型 */
    @TableField("IN_WAREHOUSE_TYPE")
    private String inWarehouseType;

    /** 同步ERP状态 */
    @TableField("SYNC_ERP_STATUS")
    private String syncErpStatus;

    /** 收货仓平台 */
    @TableField("IN_PLATFORM")
    private String inPlatform;

    /** 收货仓账号 */
    @TableField("IN_SYS_SHOPS_NAME")
    private String inSysShopsName;

    /** 收货仓站点 */
    @TableField("IN_SYS_SITE")
    private String inSysSite;
}