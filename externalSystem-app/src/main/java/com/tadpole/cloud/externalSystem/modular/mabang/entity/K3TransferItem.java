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
    * K3调拨单明细项
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("K3_TRANSFER_ITEM")
@ExcelIgnoreUnannotated
public class K3TransferItem implements Serializable {

    private static final long serialVersionUID = 1L;


    /** K3调拨单明细项数据id，和ENTRY_ID保持一直 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private BigDecimal id;

    /** 调拨单据编号 */
    @TableField("BILL_NO")
    private String billNo;

    /** 明细ID */
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

    /** 同步方式(0 ：系统同步,1：手动人工同步) */
    @TableField("SYNC_TYPE")
    private String syncType;

    /** 同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 同步状态(0 ：同步失败,1：同步成功) */
    @TableField("SYNC_STATUS")
    private String syncStatus;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

}