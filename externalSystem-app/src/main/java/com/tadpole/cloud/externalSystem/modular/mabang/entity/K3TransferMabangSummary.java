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
    * K3调拨单同步概要
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
@TableName("K3_TRANSFER_MABANG_SUMMARY")
@ExcelIgnoreUnannotated
public class K3TransferMabangSummary implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 同步概要记录id */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** K3调拨单据编号 */
    @TableField("BILL_NO")
    private String billNo;

    /** K3调拨方向(0:其他仓库-->小平台仓库（入库增加库存），1：小平台仓库-->其他仓库（不包含小平台自身仓库，出库减少库存)，2：包含前两种的混合业务（调拨单里面既有入库，又有出库操作的业务） */
    @TableField("TRANSFER_DIRECTION")
    private int transferDirection;

    /** 同步到马帮的时候该直接调拨单是否属于混合业务 0：非混合业务，1：混合业务 */
    @TableField("MIXED_BUSINESS")
    private int mixedBusiness;

    /** 调拨单拆单总数=调拨单拆成采购订单数量+调拨单拆成分仓调拨单数量 */
    @TableField("SPLIT_TOTAL")
    private BigDecimal splitTotal;

    /** 调拨单拆单总数，根据实际情况可以填写为P1,P2,P3,...，PN(N为拆分的单据数）,PN为分母，意味着总拆分订单数 */
    @TableField("SPLIT_TOTAL_PURCHASE_ORDER")
    private BigDecimal splitTotalPurchaseOrder;

    /** 调拨单拆单总数，根据实际情况可以填写为P1,P2,P3,...，PN(N为拆分的单据数）,PN为分母，意味着总拆分订单数 */
    @TableField("SPLIT_TOTAL_ALLOCATION_WAREHOU")
    private BigDecimal splitTotalAllocationWarehou;

    /** K3调拨单据状态 */
    @TableField("DOCUMENT_STATUS")
    private String documentStatus;

    /** K3单据创建日期 */
    @TableField("CREATE_DATE")
    private Date createDate;

    /** K3单据审核日期 */
    @TableField("APPROVE_DATE")
    private Date approveDate;

    /** K3单据作废状态 */
    @TableField("CANCEL_STATUS")
    private String cancelStatus;

    /** K3单据作废日期 */
    @TableField("CANCEL_DATE")
    private Date cancelDate;

    /** 该直接调拨单本次同步记录是否触发了反审核：0未触发，1已触发 */
    @TableField("ANTI_AUDIT")
    private int antiAudit;

    /** 反审核触发的操作（默认0：无变化，1：采购订单作废，2：反向创建分仓调拨单） */
    @TableField("ANTI_AUDIT_ACTION")
    private int antiAuditAction;

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