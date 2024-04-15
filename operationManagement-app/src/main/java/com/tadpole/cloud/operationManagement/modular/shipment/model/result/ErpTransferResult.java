package com.tadpole.cloud.operationManagement.modular.shipment.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * erp系统调拨单查询结果
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
public class ErpTransferResult implements Serializable, BaseValidatingParam {

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
    //h.FMODIFYDATE1  拣货完成日期
    private Date pickFinishDate;


    @ApiModelProperty("创建人")
    private String createdBy;


    @ApiModelProperty("同步时间")
    private Date syncTime;


    @ApiModelProperty("拣货数量")
//    i.FSCANQTY 已拣数量 ,
    private BigDecimal pickQty;

    @ApiModelProperty("返仓数量")
//    i.F_UNW_RETURNQTY 已拣数量 ,
    private BigDecimal returnQty;


    @ApiModelProperty("拣货单号")
    //h.FBILLNO 拣货单号,
    private String pickListCode;


    @ApiModelProperty("实际发货数量")
    //f.F_UNW_PACKINGQTY 装箱数量,
    //f.F_UNW_PACKINGSHIPMENTQTY 出货数量
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

    //拣货单头
//    h.FID ,
//    h.FBILLNO 拣货单号,
//    h.F_UNW_SOURCEBILLNO 调拨申请编号,
    @ApiModelProperty("作废状态")
    //    h.FCANCELSTATUS 作废状态,
    private String fcancelStatus;


    @ApiModelProperty("拣货状态")
    //    F_UNW_PICKINGMETHOD 拣货状态,  拣货状态 A 待拣货，B已拣货
    private String fUnwPickingmethod;


    @ApiModelProperty("数据状态")
    private String fDocumentStatus;
    //    h.FDOCUMENTSTATUS 数据状态,

//    h.FMODIFYDATE1 拣货完成日期,
//    h.FCREATEDATE 拣货单创建日期,

    //拣货单明细
//    i.F_UNW_SKU,
//    i.F_UNW_MATERIALID,
//    i.F_UNW_APPLYQTY 申请数量,
//    i.F_UNW_UNPICKEDQTY 待拣数量 ,
//    i.FSCANQTY 已拣数量 ,

    //装箱信息
//    f.F_UNW_PACKINGAPPLYQTY 拣货单总的sku申请数量,
//    f.F_UNW_PACKINGACTUALPICKING 实际拣货数量,
//    f.F_UNW_RETURNQTY 返仓数量,
//    f.F_UNW_PACKINGQTY 装箱数量,
//    f.F_UNW_PACKINGINTERCEPTQTY 结票数量,
//    f.F_UNW_PACKINGSHIPMENTQTY 出货数量

/*    SELECT
    h.FID ,h.FBILLNO 拣货单号, h.F_UNW_SOURCEBILLNO 调拨申请编号, h.FCANCELSTATUS 作废状态, h.F_UNW_PICKINGMETHOD 拣货状态, h.FDOCUMENTSTATUS 数据状态, h.FMODIFYDATE1 拣货完成日期,h.FCREATEDATE 拣货单创建日期,
    i.F_UNW_SKU,i.F_UNW_MATERIALID, i.F_UNW_APPLYQTY 申请数量,i.F_UNW_UNPICKEDQTY 待拣数量 ,i.FSCANQTY 已拣数量 ,
    f.F_UNW_PACKINGAPPLYQTY 拣货单总的sku申请数量,f.F_UNW_PACKINGACTUALPICKING 实际拣货数量,f.F_UNW_RETURNQTY 返仓数量, f.F_UNW_PACKINGQTY 装箱数量, f.F_UNW_PACKINGINTERCEPTQTY 结票数量, f.F_UNW_PACKINGSHIPMENTQTY 出货数量
    from
    UNW_PickingBill h join UNW_PickingBillEntry i on h.FID =i.FID
    JOIN UNW_PackingInfoEntry f on h.FID =f.FID
            WHERE
    h.F_UNW_SOURCEBILLNO like '%MC%' AND --调拨单号
--	h.F_UNW_SOURCEBILLNO = 'ADS-903-202009062' and --调拨单号
    h.FCANCELSTATUS='A' -- 作废状态 A 未作废，B已作废
    AND H.F_UNW_PICKINGMETHOD='B' -- 拣货状态 A 待拣货，B已拣货
    AND h.FDOCUMENTSTATUS='C' -- 数据状态 A创建，B审核中，C已审核，D 重新审核，Z 暂存*/

}
