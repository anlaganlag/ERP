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
    * 马帮分仓调拨单
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
@TableName("MABANG_ALLOCATION_WAREHOUSE")
@ExcelIgnoreUnannotated
public class MabangAllocationWarehouse implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 分仓调拨单数据记录id,可以采用马帮返回的批次号 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 同步概要记录id */
    @TableField("SUMMARY_ID")
    private String summaryId;

    /** K3调拨单据编号 */
    @TableField("BILL_NO")
    private String billNo;

    /** 分仓调拨拆单总数，根据实际情况可以填写为P1,P2,P3,...，PN(N为拆分的单据数）,PN为分母，意味着总拆分订单数 */
    @TableField("SPLIT_TOTAL")
    private String splitTotal;

    /** 拆单编号，根据实际情况可以填写为P1,P2,P3,...，PN(N为拆分的单据数）,PN为分母，意味着总拆分订单数 */
    @TableField("SPLIT_NUM")
    private String splitNum;

    /** 批次号 */
    @TableField("ALLOCATION_CODE")
    private String allocationCode;

    /** 该直接调拨单本次同步记录是否触发了反审核：0未触发，1已触发 */
    @TableField("ANTI_AUDIT")
    private int antiAudit;

    /** 反向创建分仓调拨单code */
    @TableField("REVERSE_ALLOCATION_CODE")
    private String reverseAllocationCode;

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

    /** 起始仓库名称 */
    @TableField("START_WAREHOUSE_NAME")
    private String startWarehouseName;

    /** 目标仓库名称 */
    @TableField("TARGET_WAREHOUSE_NAME")
    private String targetWarehouseName;

    /** 调拨商品清单 */
    @TableField("SKU")
    private String sku;

    /** 起始仓库ID */
    @TableField("START_WAREHOUSE_ID")
    private String startWarehouseId;

    /** 目标仓库ID */
    @TableField("TARGET_WAREHOUSE_ID")
    private String targetWarehouseId;

    /** 运输方式 1.陆地运输 2.空运 3.海运 */
    @TableField("TRANSPORT_TYPE")
    private String transportType;

    /** 运费 */
    @TableField("FREIGHT")
    private BigDecimal freight;

    /** 物流渠道 */
    @TableField("CHANNEL")
    private String channel;

    /** 物流单号 */
    @TableField("TRACK_NUMBER")
    private String trackNumber;

    /** 运费分摊方式 1.重量,2体积重 */
    @TableField("SHARE_METHOD")
    private String shareMethod;

    /** 备注 */
    @TableField("REMARK")
    private String remark;

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