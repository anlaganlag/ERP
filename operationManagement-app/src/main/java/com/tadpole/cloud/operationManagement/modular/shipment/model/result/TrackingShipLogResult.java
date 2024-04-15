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
public class TrackingShipLogResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID;明细ID")
    private BigDecimal id;


    @ApiModelProperty("申请批次号;D6维度申请批次号")
    private String applyBatchNo;


    @ApiModelProperty("调拨单号;同步到k3的调拨单号")
    private String billNo;


    @ApiModelProperty("拣货单号")
    private String pickListCode;
    @ApiModelProperty("sku")
    private String sku;


    @ApiModelProperty("出货清单号;出货清单号")
    private String shippingListCode;


    @ApiModelProperty("ShipmentID")
    private String shipmentId;


    @ApiModelProperty("发货批次号;一个出货清单对应多个发货批次号，一个发货批次号对应1个物料单号")
    private String sendBatchNo;


    @ApiModelProperty("物流单号")
    private String logisticsNumber;


    @ApiModelProperty("发货日期;EBMS物流跟踪-发货日期")
    private Date logisticsSendDate;


    @ApiModelProperty("运输方式;EBMS物流跟踪-运输方式")
    private String logisticsMode;


    @ApiModelProperty("发货数量;sum（EBMS.出货清单.票单明细2.数量） 注：只计算本物料单中所有箱子中当前SKU数量的合计")
    private BigDecimal logisticsSendQty;


    @ApiModelProperty("物流状态;EBMS物流跟踪-物流跟踪")
    private String logisticsState;


    @ApiModelProperty("签收日期;EBMS物流跟踪-签收日期")
    private Date signDate;


    @ApiModelProperty("签收数量;EBMS物流索赔管理-接收数量 注：如无数量，默认是全部接收")
    private BigDecimal signQty;


    @ApiModelProperty("是否需要追踪;0:需要追踪，1：不需要追踪")
    private Integer needTrack;


    @ApiModelProperty("备注")
    private String remark;


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















    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;









    @ApiModelProperty("打包结票日期;出货清单创建日期")
    //    h.FCREATEDATE 出货清单创建时间
    private Date packingFinishDate;


    @ApiModelProperty("接收完结日期;FBA来货报告")
    private Date receiveEndDate;


    @ApiModelProperty("接收数量;FBA来货报告")
    private BigDecimal receiveQty;


    @ApiModelProperty("差异数量")
    private BigDecimal deviationQty;



    @ApiModelProperty("ERP-数据状态")
    private String fDocumentStatus;


    @ApiModelProperty("预计到达日期;EBMS物流跟踪-预计到达日期")
    private String preArriveDate;




}
