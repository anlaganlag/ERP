package com.tadpole.cloud.operationManagement.modular.shipment.model.params;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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
public class TrackingLogisticsParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID;明细ID */
    @ApiModelProperty("ID;明细ID")
    private BigDecimal id;

    /** 申请批次号;D6维度申请批次号 */
    @ApiModelProperty("申请批次号;D6维度申请批次号")
    private String applyBatchNo;

    /** 调拨单号;同步到k3的调拨单号 */
    @ApiModelProperty("调拨单号;同步到k3的调拨单号")
    private String billNo;

    /** 拣货单号 */
    @ApiModelProperty("拣货单号")
    private String pickListCode;

    @ApiModelProperty("sku")
    private String sku;

    /** 出货清单号;出货清单号 */
    @ApiModelProperty("出货清单号;出货清单号")
    private String shippingListCode;

    /** ShipmentID */
    @ApiModelProperty("ShipmentID")
    private String shipmentId;

    /** 发货批次号;一个出货清单对应多个发货批次号，一个发货批次号对应1个物料单号 */
    @ApiModelProperty("发货批次号;一个出货清单对应多个发货批次号，一个发货批次号对应1个物料单号")
    private String sendBatchNo;

    /** 物流单号 */
    @ApiModelProperty("物流单号")
    private String logisticsNumber;

    /** 发货日期;EBMS物流跟踪-发货日期 */
    @ApiModelProperty("发货日期;EBMS物流跟踪-发货日期")
    private Date logisticsSendDate;

    /** 运输方式;EBMS物流跟踪-运输方式 */
    @ApiModelProperty("运输方式;EBMS物流跟踪-运输方式")
    private String logisticsMode;

    /** 发货数量;sum（EBMS.出货清单.票单明细2.数量） 注：只计算本物料单中所有箱子中当前SKU数量的合计 */
    @ApiModelProperty("发货数量;sum（EBMS.出货清单.票单明细2.数量） 注：只计算本物料单中所有箱子中当前SKU数量的合计")
    private BigDecimal logisticsSendQty;

    /** 物流状态;EBMS物流跟踪-物流跟踪 */
    @ApiModelProperty("物流状态;EBMS物流跟踪-物流跟踪")
    private String logisticsState;

    /** 签收日期;EBMS物流跟踪-签收日期 */
    @ApiModelProperty("签收日期;EBMS物流跟踪-签收日期")
    private Date signDate;

    /** 签收数量;EBMS物流索赔管理-接收数量 注：如无数量，默认是全部接收 */
    @ApiModelProperty("签收数量;EBMS物流索赔管理-接收数量 注：如无数量，默认是全部接收")
    private BigDecimal signQty;

    /** 是否需要追踪;0:需要追踪，1：不需要追踪 */
    @ApiModelProperty("是否需要追踪;0:需要追踪，1：不需要追踪")
    private Integer needTrack;

    /** 备注 */
    @ApiModelProperty("备注")
    private String remark;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createdBy;

    /** 同步时间 */
    @ApiModelProperty("同步时间")
    private Date syncTime;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createdTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updatedBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updatedTime;

}
