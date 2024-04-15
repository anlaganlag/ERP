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
@ExcelIgnoreUnannotated
@TableName("TRACKING_TRANSFER")
public class TrackingTransferResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("ID;明细ID")
    private BigDecimal id;


    @ApiModelProperty("申请批次号;D6维度申请批次号")
    private String applyBatchNo;


    @ApiModelProperty("物料编码")
    private String materialCode;


    @ApiModelProperty("店铺名称")
    private String shopName;


    @ApiModelProperty("站点")
    private String sysSite;


    @ApiModelProperty("SKU")
    private String sku;


    @ApiModelProperty("SKU标签类型")
    private String sysLabelType;


    @ApiModelProperty("账号-店铺简称")
    private String sysShopsName;


    @ApiModelProperty("ShipmentID")
    private String shipmentId;


    @ApiModelProperty("调入仓库;接收仓库")
    private String receiveWarehouse;


    @ApiModelProperty("发货数量")
    private BigDecimal applySendQty;


    @ApiModelProperty("发货方式;运输方式")
    private String transportationType;


    @ApiModelProperty("数据来源类型-BI推荐-手动选择-表格导入;数据来源类型-BI推荐-手动选择-表格导入")
    private String dataSourceType;


    @ApiModelProperty("申请日期")
    private Date applyDate;


    @ApiModelProperty("申请追踪状态;值域{0待审核-1已通过-2未通过-3待拣货-4拣货中-5装箱中-6物流中-7接收中-8已完结-9已撤销}")
    private Integer applyTrackStatus;

    @ApiModelProperty("申请追踪状态;值域{0待审核-1已通过-2未通过-3待拣货-4拣货中-5装箱中-6物流中-7接收中-8已完结-9已撤销}")
    private String applyTrackStatusName;


    @ApiModelProperty("申请人")
    private String apllyPerson;


    @ApiModelProperty("申请人编号")
    private String apllyPersonNo;


    @ApiModelProperty("审核状态;数据初始化为0--审核通过1--不通过2")
    private Integer checkStatus;


    @ApiModelProperty("审核原因")
    private String checkReason;


    @ApiModelProperty("审核日期")
    private Date checkDate;


    @ApiModelProperty("审核人编号")
    private String checkPersonNo;


    @ApiModelProperty("审核人")
    private String checkPerson;


    @ApiModelProperty("调拨单号;同步到k3的调拨单号")
    private String billNo;


    @ApiModelProperty("拣货完成日期")
    private Date pickFinishDate;


    @ApiModelProperty("创建人")
    private String createdBy;


    @ApiModelProperty("同步时间")
    private Date syncTime;


    @ApiModelProperty("拣货数量")
    private BigDecimal pickQty;

    /** 返仓数量 */
    @ApiModelProperty("返仓数量")
    private BigDecimal returnQty;


    @ApiModelProperty("拣货单号")
    private String pickListCode;


    @ApiModelProperty("实际发货数量")
    private BigDecimal actualSendQty;


    @ApiModelProperty("撤销人")
    private String revokePerson;


    @ApiModelProperty("撤销人编号")
    private String revokePersonNo;


    @ApiModelProperty("撤销日期")
    private Date revokeDate;


    @ApiModelProperty("撤销原因")
    private String revokeReason;


    @ApiModelProperty("ERP撤销响应结果")
    private String revokeErpResult;


    @ApiModelProperty("是否需要追踪;0:需要追踪，1：不需要追踪")
    private Integer needTrack;


    @ApiModelProperty("创建时间")
    private Date createdTime;


    @ApiModelProperty("更新人")
    private String updatedBy;


    @ApiModelProperty("更新时间")
    private Date updatedTime;

    @ApiModelProperty("UW业务类型")
    private String unwType;

}
