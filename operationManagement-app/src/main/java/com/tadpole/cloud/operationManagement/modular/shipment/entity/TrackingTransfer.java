package com.tadpole.cloud.operationManagement.modular.shipment.entity;

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
    * 追踪明细项-调拨单
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("TRACKING_TRANSFER")
@ExcelIgnoreUnannotated
public class TrackingTransfer implements Serializable {

    private static final long serialVersionUID = 1L;


    /** ID;明细ID */
    @TableId(type = IdType.AUTO)
    private BigDecimal id;

    /** 申请批次号;D6维度申请批次号 */
    @TableField("APPLY_BATCH_NO")
    private String applyBatchNo;

    /** 物料编码 */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /** 店铺名称 */
    @TableField("SHOP_NAME")
    private String shopName;

    /** 站点 */
    @TableField("SYS_SITE")
    private String sysSite;

    /** SKU */
    @TableField("SKU")
    private String sku;

    /** SKU标签类型 */
    @TableField("SYS_LABEL_TYPE")
    private String sysLabelType;

    /** 账号 */
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** ShipmentID */
    @TableField("SHIPMENT_ID")
    private String shipmentId;

    /** 调入仓库;接收仓库 */
    @TableField("RECEIVE_WAREHOUSE")
    private String receiveWarehouse;

    /** 发货数量 */
    @TableField("APPLY_SEND_QTY")
    private BigDecimal applySendQty;

    /** 发货方式;运输方式 */
    @TableField("TRANSPORTATION_TYPE")
    private String transportationType;

    /** 数据来源类型-BI推荐-手动选择-表格导入;数据来源类型-BI推荐-手动选择-表格导入 */
    @TableField("DATA_SOURCE_TYPE")
    private String dataSourceType;

    /** 申请日期 */
    @TableField("APPLY_DATE")
    private Date applyDate;

    /** 申请追踪状态;值域{"0待审核"/"1已通过"/"2未通过"/"3同步中"/"4拣货中"/"5装箱中"/"6物流中"/"7接收中"/"8已完结"/"9已撤销"} */
    @TableField("APPLY_TRACK_STATUS")
    private Integer applyTrackStatus;

    /** 申请人 */
    @TableField("APLLY_PERSON")
    private String apllyPerson;

    /** 申请人编号 */
    @TableField("APLLY_PERSON_NO")
    private String apllyPersonNo;

    /** 审核状态;数据初始化为0--审核通过1--不通过2 */
    @TableField("CHECK_STATUS")
    private Integer checkStatus;

    /** 审核原因 */
    @TableField("CHECK_REASON")
    private String checkReason;

    /** 审核日期 */
    @TableField("CHECK_DATE")
    private Date checkDate;

    /** 审核人编号 */
    @TableField("CHECK_PERSON_NO")
    private String checkPersonNo;

    /** 审核人 */
    @TableField("CHECK_PERSON")
    private String checkPerson;

    /** 调拨单号;同步到k3的调拨单号 */
    @TableField("BILL_NO")
    private String billNo;

    /** 拣货完成日期 */
    @TableField("PICK_FINISH_DATE")
    private Date pickFinishDate;

    /** 创建人 */
    @TableField("CREATED_BY")
    private String createdBy;

    /** 同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 拣货数量 */
    @TableField("PICK_QTY")
    private BigDecimal pickQty;

    /** 返仓数量 */
    @TableField("RETURN_QTY")
    private BigDecimal returnQty;



    /** 拣货单号 */
    @TableField("PICK_LIST_CODE")
    private String pickListCode;

    /** 实际发货数量 */
    @TableField("ACTUAL_SEND_QTY")
    private BigDecimal actualSendQty;

    /** 撤销人 */
    @TableField("REVOKE_PERSON")
    private String revokePerson;

    /** 撤销人编号 */
    @TableField("REVOKE_PERSON_NO")
    private String revokePersonNo;

    /** 撤销日期 */
    @TableField("REVOKE_DATE")
    private Date revokeDate;

    /** 撤销原因 */
    @TableField("REVOKE_REASON")
    private String revokeReason;

    /** ERP撤销响应结果 */
    @TableField("REVOKE_ERP_RESULT")
    private String revokeErpResult;

    /** 是否需要追踪;0:需要追踪，1：不需要追踪 */
    @TableField("NEED_TRACK")
    private Integer needTrack;

    /** 创建时间 */
    @TableField("CREATED_TIME")
    private Date createdTime;

    /** 更新人 */
    @TableField("UPDATED_BY")
    private String updatedBy;

    /** 更新时间 */
    @TableField("UPDATED_TIME")
    private Date updatedTime;

}