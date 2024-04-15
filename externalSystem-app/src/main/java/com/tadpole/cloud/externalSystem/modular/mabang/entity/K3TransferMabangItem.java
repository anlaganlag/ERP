package com.tadpole.cloud.externalSystem.modular.mabang.entity;

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
    * K3调拨单同步到马帮记录明细项
    * </p>
*
* @author lsy
* @since 2022-06-15
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("K3_TRANSFER_MABANG_ITEM")
@ExcelIgnoreUnannotated
public class K3TransferMabangItem implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 同步记录明细id */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 同步概要记录id */
    @TableField("SUMMARY_ID")
    private String summaryId;

    /** 调拨单据编号 */
    @TableField("BILL_NO")
    private String billNo;

    /** 拆单编号 */
    @TableField("SPLIT_NUM")
    private String splitNum;

    /** K3调拨方向(0:其他仓库-->小平台仓库（入库增加库存），1：小平台仓库-->其他仓库（不包含小平台自身仓库，出库减少库存) */
    @TableField("TRANSFER_DIRECTION")
    private int transferDirection;

    /** 同步成功到马帮新增采购订单返回的采购单号 */
    @TableField("GROUP_ID")
    private String groupId;

    /** 创建分仓调拨批次号 */
    @TableField("ALLOCATION_CODE")
    private String allocationCode;

    /** 采购订单ID */
    @TableField("PURCHASE_ORDER_ID")
    private String purchaseOrderId;

    /** 分仓调拨单ID */
    @TableField("ALLOCATION_WAREHOUSE_ID")
    private String allocationWarehouseId;

    /** 该直接调拨单本次同步记录是否触发了反审核：0未触发，1已触发 */
    @TableField("ANTI_AUDIT")
    private int antiAudit;

    /** 反审核触发的操作（默认0：无变化，1：采购订单作废，2：反向创建分仓调拨单） */
    @TableField("ANTI_AUDIT_ACTION")
    private int antiAuditAction;

    /** 反向创建分仓调code，当ANTI_AUDIT_ACTION=2时有值 */
    @TableField("REVERSE_ALLOCATION_CODE")
    private String reverseAllocationCode;

    /** 明细ID-k3系统的 */
    @TableField("ENTRY_ID")
    private BigDecimal entryId;

    /** 物料编码 */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /** 物料名称 */
    @TableField("MATERIAL_NAME")
    private String materialName;

    /** 调出仓库编码 */
    @TableField("SRCSTOCK_CODE")
    private String srcstockCode;

    /** 调出仓库名称 */
    @TableField("SRCSTOCK_NAME")
    private String srcstockName;

    /** 调入仓库编码 */
    @TableField("DESTSTOCK_CODE")
    private String deststockCode;

    /** 调入仓库名称 */
    @TableField("DESTSTOCK_NAME")
    private String deststockName;

    /** 调拨数量 */
    @TableField("QTY")
    private BigDecimal qty;

    /** 单据状态 */
    @TableField("DOCUMENT_STATUS")
    private String documentStatus;

    /** 单据创建日期 */
    @TableField("CREATE_DATE")
    private Date createDate;

    /** 单据审核日期 */
    @TableField("APPROVE_DATE")
    private Date approveDate;

    /** 单据作废状态 */
    @TableField("CANCEL_STATUS")
    private String cancelStatus;

    /** 单据作废日期 */
    @TableField("CANCEL_DATE")
    private Date cancelDate;

    /** 是否删除:正常：0，删除：1 */
    @TableField("IS_DELETE")
    private String isDelete;

    /** 同步方式(0 ：系统同步,1：手动人工同步) */
    @TableField("SYNC_TYPE")
    private String syncType;

    /** 同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 同步状态(0 ：同步失败,1：同步成功) */
    @TableField("SYNC_STATUS")
    private String syncStatus;

    /** 同步成功次数(反审核通过情况次数增加) */
    @TableField("SYNC_SUCCESS_TIMES")
    private BigDecimal syncSuccessTimes;

    /** 同步失败次数 */
    @TableField("SYNC_FAIL_TIMES")
    private BigDecimal syncFailTimes;

    /** 同步结果消息内容 */
    @TableField("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

}