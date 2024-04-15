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
*  海外仓入库管理明细
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
@TableName("OVERSEAS_IN_WAREHOUSE_DETAIL")
@ExcelIgnoreUnannotated
public class OverseasInWarehouseDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 入库单号 */
    @TableField("IN_ORDER")
    private String inOrder;

    /** 箱条码 */
    @TableField("PACKAGE_BAR_CODE")
    private String packageBarCode;

    /** 箱号 */
    @TableField("PACKAGE_NUM")
    private BigDecimal packageNum;

    /** FNSKU */
    @TableField("FN_SKU")
    private String fnSku;

    /** SKU */
    @TableField("SKU")
    private String sku;

    /** 数量 */
    @TableField("QUANTITY")
    private BigDecimal quantity;

    /** 实际到货数量 */
    @TableField("ACTUAL_QUANTITY")
    private BigDecimal actualQuantity;

    /** 物料编码 */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /** 需求部门 */
    @TableField("DEPARTMENT")
    private String department;

    /** 需求Team */
    @TableField("TEAM")
    private String team;

    /** 需求人 */
    @TableField("NEEDS_USER")
    private String needsUser;

    /** 承运商 */
    @TableField("LOGISTICS_COMPANY")
    private String logisticsCompany;

    /** 建议发货方式 */
    @TableField("SUGGEST_TRANS_TYPE")
    private String suggestTransType;

    /** 物流单号 */
    @TableField("LOGISTICS_NUM")
    private String logisticsNum;

    /** 签收状态 */
    @TableField("CONFIRM_STATUS")
    private String confirmStatus;

    /** 签收时间 */
    @TableField("CONFIRM_TIME")
    private Date confirmTime;

    /** 签收人 */
    @TableField("CONFIRM_USER")
    private String confirmUser;

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

    /** 产品名称 */
    @TableField("MATERIAL_NAME")
    private String materialName;

    /** 重量 */
    @TableField("WEIGHT")
    private BigDecimal weight;

    /** 长 */
    @TableField("LENGTH")
    private BigDecimal length;

    /** 宽 */
    @TableField("WIDTH")
    private BigDecimal width;

    /** 高 */
    @TableField("HEIGHT")
    private BigDecimal height;

    /** 发货时间 */
    @TableField("SHIPMENT_DATE")
    private Date shipmentDate;

    /** 物流状态 */
    @TableField("LOGISTICS_STATUS")
    private String logisticsStatus;

    /** 调拨单号 */
    @TableField("PACK_DIRECT_CODE")
    private String packDirectCode;

    /** 用户实际签收数量 */
    @TableField("USER_ACTUAL_QUANTITY")
    private BigDecimal userActualQuantity;
}