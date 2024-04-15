package com.tadpole.cloud.operationManagement.modular.shipment.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
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
@ExcelIgnoreUnannotated
@TableName("TRACKING_SHIPPING")
public class TrackingShippingResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("ID;明细ID")
    private BigDecimal id;


    @ApiModelProperty("申请批次号;D6维度申请批次号")
    private String applyBatchNo;


    @ApiModelProperty("调拨单号;同步到k3的调拨单号")
    //    i.FTRANSFERAPPLYBILLNO 调拨单号,
    private String billNo;


    @ApiModelProperty("拣货单号")
    //    i.FPICKLISTBILLNO 拣货单号,
    private String pickListCode;


    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    @ApiModelProperty("sku")
    private String sku;


    @ApiModelProperty("出货清单号;出货清单号")
//    h.FBILLNO 出货清单号,
    private String shippingListCode;


    @ApiModelProperty("ShipmentID")
    //    h.FFBA FBA编号,
    private String shipmentId;


    @ApiModelProperty("打包结票日期;出货清单创建日期")
    //    h.FCREATEDATE 出货清单创建时间
    private Date packingFinishDate;


    @ApiModelProperty("接收完结日期;FBA来货报告")
    private Date receiveEndDate;


    @ApiModelProperty("接收数量;FBA来货报告")
    private BigDecimal receiveQty;


    @ApiModelProperty("差异数量")
    private BigDecimal deviationQty;

    @ApiModelProperty("出货清单出货数量")
    private BigDecimal shipingQty;


    @ApiModelProperty("是否需要追踪;0:需要追踪，1：不需要追踪")
    private Integer needTrack;


    @ApiModelProperty("创建人")
    private String createdBy;


    @ApiModelProperty("同步时间")
    private Date syncTime;


    @ApiModelProperty("创建时间")
    private Date createdTime;


    @ApiModelProperty("更新人")
    private String updatedBy;


    @ApiModelProperty("更新时间")
    private Date updatedTime;


    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("ERP-数据状态")
    private String fDocumentStatus;

}
