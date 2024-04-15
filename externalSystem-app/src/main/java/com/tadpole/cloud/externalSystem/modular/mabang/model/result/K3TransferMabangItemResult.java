package com.tadpole.cloud.externalSystem.modular.mabang.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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
@ExcelIgnoreUnannotated
@TableName("K3_TRANSFER_MABANG_ITEM")
public class K3TransferMabangItemResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 同步记录明细id */
   @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 同步概要记录id */
    @ApiModelProperty("同步概要记录id")
    private String summaryId;

    /** 调拨单据编号 */
    @ApiModelProperty("调拨单据编号")
    private String billNo;

    /** 拆单编号 */
    @ApiModelProperty("拆单编号")
    private String splitNum;

    /** K3调拨方向(0:其他仓库-->小平台仓库（入库增加库存），1：小平台仓库-->其他仓库（不包含小平台自身仓库，出库减少库存) */
    @ApiModelProperty("K3调拨方向(0:其他仓库-->小平台仓库（入库增加库存），1：小平台仓库-->其他仓库（不包含小平台自身仓库，出库减少库存)")
    private int transferDirection;

    /** 同步成功到马帮新增采购订单返回的采购单号 */
    @ApiModelProperty("同步成功到马帮新增采购订单返回的采购单号")
    private String groupId;

    /** 创建分仓调拨批次号 */
    @ApiModelProperty("创建分仓调拨批次号")
    private String allocationCode;

    /** 采购订单ID */
    @ApiModelProperty("采购订单ID")
    private String purchaseOrderId;

    /** 采购订单ID */
    @ApiModelProperty("采购订单ID")
    private String allocationWarehouseId;

    /** 该直接调拨单本次同步记录是否触发了反审核：0未触发，1已触发 */
    @ApiModelProperty("该直接调拨单本次同步记录是否触发了反审核：0未触发，1已触发")
    private int antiAudit;

    /** 反审核触发的操作（默认0：无变化，1：采购订单作废，2：反向创建分仓调拨单） */
    @ApiModelProperty("反审核触发的操作（默认0：无变化，1：采购订单作废，2：反向创建分仓调拨单）")
    private int antiAuditAction;

    /** 反向创建分仓调code，当ANTI_AUDIT_ACTION=2时有值 */
    @ApiModelProperty("反向创建分仓调code，当ANTI_AUDIT_ACTION=2时有值")
    private String reverseAllocationCode;

    /** 明细ID-k3系统的 */
    @ApiModelProperty("明细ID-k3系统的")
    private BigDecimal entryId;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 物料名称 */
    @ApiModelProperty("物料名称")
    private String materialName;

    /** 调出仓库编码 */
    @ApiModelProperty("调出仓库编码")
    private String srcstockCode;

    /** 调出仓库名称 */
    @ApiModelProperty("调出仓库名称")
    private String srcstockName;

    /** 调入仓库编码 */
    @ApiModelProperty("调入仓库编码")
    private String deststockCode;

    /** 调入仓库名称 */
    @ApiModelProperty("调入仓库名称")
    private String deststockName;

    /** 调拨数量 */
    @ApiModelProperty("调拨数量")
    private BigDecimal qty;

    /** 单据状态 */
    @ApiModelProperty("单据状态")
    private String documentStatus;

    /** 单据创建日期 */
    @ApiModelProperty("单据创建日期")
    private Date createDate;

    /** 单据审核日期 */
    @ApiModelProperty("单据审核日期")
    private Date approveDate;

    /** 单据作废状态 */
    @ApiModelProperty("单据作废状态")
    private String cancelStatus;

    /** 单据作废日期 */
    @ApiModelProperty("单据作废日期")
    private Date cancelDate;

    /** 是否删除:正常：0，删除：1 */
    @ApiModelProperty("是否删除:正常：0，删除：1")
    private String isDelete;

    /** 同步方式(0 ：系统同步,1：手动人工同步) */
    @ApiModelProperty("同步方式(0 ：系统同步,1：手动人工同步)")
    private String syncType;

    /** 同步时间 */
    @ApiModelProperty("同步时间")
    private Date syncTime;

    /** 同步状态(0 ：同步失败,1：同步成功) */
    @ApiModelProperty("同步状态(0 ：同步失败,1：同步成功)")
    private String syncStatus;

    /** 同步成功次数(反审核通过情况次数增加) */
    @ApiModelProperty("同步成功次数(反审核通过情况次数增加)")
    private BigDecimal syncSuccessTimes;

    /** 同步失败次数 */
    @ApiModelProperty("同步失败次数")
    private BigDecimal syncFailTimes;

    /** 同步结果消息内容 */
    @ApiModelProperty("同步结果消息内容")
    private String syncResultMsg;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

}