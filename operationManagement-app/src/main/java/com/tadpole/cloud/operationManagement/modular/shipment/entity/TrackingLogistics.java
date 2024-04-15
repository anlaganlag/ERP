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
    * 追踪明细项-物流信息
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
@TableName("TRACKING_LOGISTICS")
@ExcelIgnoreUnannotated
public class TrackingLogistics implements Serializable {

    private static final long serialVersionUID = 1L;


    /** ID;明细ID */
    @TableId(type = IdType.AUTO)
    private BigDecimal id;

    /** 申请批次号;D6维度申请批次号 */
    @TableField("APPLY_BATCH_NO")
    private String applyBatchNo;

    /** 调拨单号;同步到k3的调拨单号 */
    @TableField("BILL_NO")
    private String billNo;

    /** 拣货单号 */
    @TableField("PICK_LIST_CODE")
    private String pickListCode;

    @TableField("SKU")
    private String sku;

    /** 出货清单号;出货清单号 */
    @TableField("SHIPPING_LIST_CODE")
    private String shippingListCode;

    /** ShipmentID */
    @TableField("SHIPMENT_ID")
    private String shipmentId;

    /** 发货批次号;一个出货清单对应多个发货批次号，一个发货批次号对应1个物料单号 */
    @TableField("SEND_BATCH_NO")
    private String sendBatchNo;

    /** 物流单号 */
    @TableField("LOGISTICS_NUMBER")
    private String logisticsNumber;

    /** 发货日期;EBMS物流跟踪-发货日期 */
    @TableField("LOGISTICS_SEND_DATE")
    private Date logisticsSendDate;

    /** 运输方式;EBMS物流跟踪-运输方式 */
    @TableField("LOGISTICS_MODE")
    private String logisticsMode;

    /** 发货数量;sum（EBMS.出货清单.票单明细2.数量） 注：只计算本物料单中所有箱子中当前SKU数量的合计 */
    @TableField("LOGISTICS_SEND_QTY")
    private BigDecimal logisticsSendQty;

    /** 物流状态;EBMS物流跟踪-物流跟踪 */
    @TableField("LOGISTICS_STATE")
    private String logisticsState;

    /** 签收日期;EBMS物流跟踪-签收日期 */
    @TableField("SIGN_DATE")
    private Date signDate;

    /** 签收数量;EBMS物流索赔管理-接收数量 注：如无数量，默认是全部接收 */
    @TableField("SIGN_QTY")
    private BigDecimal signQty;

    /** 是否需要追踪;0:需要追踪，1：不需要追踪 */
    @TableField("NEED_TRACK")
    private Integer needTrack;

    /** 备注 */
    @TableField("REMARK")
    private String remark;

    /** 创建人 */
    @TableField("CREATED_BY")
    private String createdBy;

    /** 同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 创建时间 */
    @TableField("CREATED_TIME")
    private Date createdTime;

    /** 更新人 */
    @TableField("UPDATED_BY")
    private String updatedBy;

    /** 更新时间 */
    @TableField("UPDATED_TIME")
    private Date updatedTime;

    /** 预计到达日期 */
    @TableField("PRE_ARRIVE_DATE")
    private Date preArriveDate;

}