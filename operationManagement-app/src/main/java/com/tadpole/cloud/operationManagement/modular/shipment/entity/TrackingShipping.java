package com.tadpole.cloud.operationManagement.modular.shipment.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 追踪明细项-出货清单
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
@TableName("TRACKING_SHIPPING")
@ExcelIgnoreUnannotated
public class TrackingShipping implements Serializable {

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

    /** 站点 */
    @TableField("SYS_SITE")
    private String sysSite;

    /** SKU */
    @TableField("SKU")
    private String sku;

    /** 出货清单号;出货清单号 */
    @TableField("SHIPPING_LIST_CODE")
    private String shippingListCode;

    /** ShipmentID */
    @TableField("SHIPMENT_ID")
    private String shipmentId;

    /** 打包结票日期;出货清单创建日期 */
    @TableField("PACKING_FINISH_DATE")
    private Date packingFinishDate;

    /** 接收完结日期;FBA来货报告 */
    @TableField("RECEIVE_END_DATE")
    private Date receiveEndDate;

    /** 接收数量;FBA来货报告 */
    @TableField("RECEIVE_QTY")
    private BigDecimal receiveQty;

    /** 差异数量 */
    @TableField("DEVIATION_QTY")
    private BigDecimal deviationQty;

    /**出货清单出货数量*/
    @ApiModelProperty("SHIPING_QTY")
    private BigDecimal shipingQty;

    /** 是否需要追踪;0:需要追踪，1：不需要追踪 */
    @TableField("NEED_TRACK")
    private Integer needTrack;

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

    /** 备注 */
    @TableField("REMARK")
    private String remark;

}