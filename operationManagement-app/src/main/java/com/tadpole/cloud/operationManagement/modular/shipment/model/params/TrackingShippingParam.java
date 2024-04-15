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
public class TrackingShippingParam implements Serializable, BaseValidatingParam {

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

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    @ApiModelProperty("sku")
    private String sku;

    /** 出货清单号;出货清单号 */
    @ApiModelProperty("出货清单号;出货清单号")
    private String shippingListCode;

    /** ShipmentID */
    @ApiModelProperty("ShipmentID")
    private String shipmentId;

    /** 打包结票日期;出货清单创建日期 */
    @ApiModelProperty("打包结票日期;出货清单创建日期")
    private Date packingFinishDate;

    /** 接收完结日期;FBA来货报告 */
    @ApiModelProperty("接收完结日期;FBA来货报告")
    private Date receiveEndDate;

    /** 接收数量;FBA来货报告 */
    @ApiModelProperty("接收数量;FBA来货报告")
    private BigDecimal receiveQty;

    /** 差异数量 */
    @ApiModelProperty("差异数量")
    private BigDecimal deviationQty;

    /** 是否需要追踪;0:需要追踪，1：不需要追踪 */
    @ApiModelProperty("是否需要追踪;0:需要追踪，1：不需要追踪")
    private Integer needTrack;

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

    /** 备注 */
    @ApiModelProperty("备注")
    private String remark;

}
