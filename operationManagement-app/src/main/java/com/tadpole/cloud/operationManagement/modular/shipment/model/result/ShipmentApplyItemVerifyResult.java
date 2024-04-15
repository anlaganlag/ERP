package com.tadpole.cloud.operationManagement.modular.shipment.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentApplyItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* <p>
    * 发货审核查询结果集
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
public class ShipmentApplyItemVerifyResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("ID;明细ID")
    private BigDecimal id;


    @ApiModelProperty("批次号")
    private String batchNo;


    @ApiModelProperty("D6维度申请批次号")
    private String applyBatchNo;


    @ApiModelProperty("交货地点编号;交货地点编号")
    private String deliverypointNo;


    @ApiModelProperty("交货地点;默认联泰发货，还可以供应商发货")
    private String deliverypoint;


    @ApiModelProperty("发货推荐数据快照ID;只有提交的数据才会触发保存推荐数据快照")
    private BigDecimal recomSnapshotId;

    @ApiModelProperty("组织编码 Amazon_账号_站点_仓库 对应的组织编号")
    private String  orgCode;


    @ApiModelProperty("平台")
    private String platform;


    @ApiModelProperty("区域")
    private String area;


    @ApiModelProperty("部门")
    private String department;


    @ApiModelProperty("Team")
    private String team;


    @ApiModelProperty("物料编码")
    private String materialCode;


    @ApiModelProperty("物料信息;物料信息json字符串")
    private String materialInfo;


    @ApiModelProperty("产品名称")
    private String productName;


    @ApiModelProperty("ASIN")
    private String asin;


    @ApiModelProperty("运营大类")
    private String productType;


    @ApiModelProperty("店铺名称")
    private String shopName;


//    @ApiModelProperty("站点")
//    private String sysSite;


//    @ApiModelProperty("账号-店铺简称")
//    private String sysShopsName;


//    @ApiModelProperty("FBA号")
//    private String fbaNo;


//    @ApiModelProperty("调入仓库;接收仓库")
//    private String receiveWarehouse;

    /** 国内仓可调拨数 --erp系统team下改物料可调拨数量*/
    @ApiModelProperty("国内仓可调拨数")
    private BigDecimal erpCanTransferQty;

    /** 已被占用库存 --申请正在erp执行中的数量*/
    @ApiModelProperty("已被占用库存")
    private BigDecimal occupyQty;

    /** 推荐国内发货数量 */
    @ApiModelProperty("推荐国内发货数量")
    private BigDecimal domesticRecommSendQty;



    @ApiModelProperty("数据来源类型-BI推荐-手动选择-表格导入;数据来源类型-BI推荐-手动选择-表格导入")
    private String dataSourceType;


    @ApiModelProperty("库存校验;按批次校验")
    private String stockCheck;


    @ApiModelProperty("合理性判断;按批次分析")
    private String reasonableCheck;

    /** 发货前周转天数 */
    @ApiModelProperty("发货前周转天数")
    private BigDecimal turnoverBeforeSendDays;

    @ApiModelProperty("按批次分析发货后周转天数")
    private BigDecimal turnoverAfterSendDays;

    @ApiModelProperty("申请日期")
    private Date applyDate;



    @ApiModelProperty("申请状态;数据初始化为0--已提交1")
    private Integer applyStatus;


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


    @ApiModelProperty("同步时间")
    private Date syncTime;


    @ApiModelProperty("同步K3状态;数据初始化为0-调用K3接口成功1--失败2")
    private Integer syncStatus;


    @ApiModelProperty("同步请求参数")
    private String syncRequestMsg;


    @ApiModelProperty("同步结果")
    private String syncResultMsg;


    @ApiModelProperty("创建人")
    private String createdBy;


    @ApiModelProperty("创建时间")
    private Date createdTime;


    @ApiModelProperty("更新人")
    private String updatedBy;


    @ApiModelProperty("更新时间")
    private Date updatedTime;

    /**
     * 调入仓库code;接收仓库code
     */
//    @ApiModelProperty("调入仓库code")
//    private String receiveWarehouseCode;

    /** 发货总销售需求 */
    @ApiModelProperty("REQUIREMENT_SEND_TOTAL_QTY")
    private BigDecimal requirementSendTotalQty;

    List<ShipmentApplyItem> applyItemList =new ArrayList<>();


    /** 【本月】销量 */
    @ApiModelProperty("CURQTY")
    private BigDecimal curqty;

    /** 【本月+1】销量 */
    @ApiModelProperty("NEXTM_MON1QTY")
    private BigDecimal nextmMon1qty;

    /** 【本月+2】销量 */
    @ApiModelProperty("NEXTM_MON2QTY")
    private BigDecimal nextmMon2qty;

    /** 【本月+3】销量 */
    @ApiModelProperty("NEXTM_MON3QTY")
    private BigDecimal nextmMon3qty;

    /** 【本月+4】销量 */
    @ApiModelProperty("NEXTM_MON4QTY")
    private BigDecimal nextmMon4qty;

    /** 【本月+5】销量 */
    @ApiModelProperty("NEXTM_MON5QTY")
    private BigDecimal nextmMon5qty;

    /** 【本月+6】销量 */
    @ApiModelProperty("NEXTM_MON6QTY")
    private BigDecimal nextmMon6qty;

    /** 7天销售数量 */
    @ApiModelProperty("DAY7QTY")
    private BigDecimal day7qty;

    /** 14天销售数量 */
    @ApiModelProperty("DAY14QTY")
    private BigDecimal day14qty;

    /** 30天销售数量 */
    @ApiModelProperty("DAY30QTY")
    private BigDecimal day30qty;

    /** 日均销量 */
    @ApiModelProperty("DAYAVGSELL")
    private BigDecimal dayavgsell;

    /** 在途数量供应情况 */
    @ApiModelProperty("IS_CUT_GOODS")
    private String isCutGoods;


    /** 未完成订单-;7天内预计到货数量 */
    @ApiModelProperty("UNDONE_IN7DAYS_QTY")
    private BigDecimal undoneIn7daysQty;

    /** 未完成订单-;8-14天预计到货数量 */
    @ApiModelProperty("UNDONE_8TO14DAYS_QTY")
    private BigDecimal undone8to14daysQty;

    /** 未完成订单-;15天后预计到货数量 */
    @ApiModelProperty("UNDONE_AFTER15DAYS_QTY")
    private BigDecimal undoneAfter15daysQty;

    /** 备货用生产周期 */
    @ApiModelProperty("RESTOCK_PRODUCTION_CYCLE")
    private BigDecimal restockProductionCycle;

    /** 推荐运输方式 */
    @ApiModelProperty("RECOMM_TRANSPORTATION")
    private String recommTransportation;

    /** 国内仓处理天数 */
    @ApiModelProperty("DOMESTIC_WAREHOUSE_HANDLE_DAYS")
    private BigDecimal domesticWarehouseHandleDays;

    /** 运输天数 */
    @ApiModelProperty("DELIVERY_DAYS")
    private BigDecimal deliveryDays;

    /** 海外仓处理天数 */
    @ApiModelProperty("OVERSEA_WAREHOUSER_HANDLE_DAYS")
    private BigDecimal overseaWarehouserHandleDays;

    /** 安全销售天数 */
    @ApiModelProperty("SALE_SAFELY_DAYS")
    private BigDecimal saleSafelyDays;

    /** 发货间隔天数 */
    @ApiModelProperty("SEND_GAP_DAYS")
    private BigDecimal sendGapDays;

    /** 国内可用库存 */
    @ApiModelProperty("DOMESTIC_AVAIL_QTY")
    private BigDecimal domesticAvailQty;

    /** 国内仓可调拨数量 */
    @ApiModelProperty("DOMESTIC_TRANSFER_AVAIL_QTY")
    private BigDecimal domesticTransferAvailQty;

    /** 国内待质检数量 */
    @ApiModelProperty("DOMESTIC_UNCHECK_QTY")
    private BigDecimal domesticUncheckQty;

    /** 总发货天数 */
    @ApiModelProperty("SEND_TOTAL_DAYS")
    private BigDecimal sendTotalDays;

    /** 总发货天数覆盖销售日期 */
    @ApiModelProperty("CAN_SALE_DATE")
    private Date canSaleDate;

    /** AZ可售数量 */
    @ApiModelProperty("AZ_AVAIL_QTY")
    private BigDecimal azAvailQty;

    /** AZ预留数量 */
    @ApiModelProperty("AZ_RESERVED_QTY")
    private BigDecimal azReservedQty;

    /** AZ物流待发数量 */
    @ApiModelProperty("AZ_WAIT_SEND_QTY")
    private BigDecimal azWaitSendQty;

    /** AZ空运来货数量 */
    @ApiModelProperty("AZ_AIR_QTY")
    private BigDecimal azAirQty;

    /** AZ海运来货数量 */
    @ApiModelProperty("AZ_SHIP_QTY")
    private BigDecimal azShipQty;

    /** AZ铁运来货数量 */
    @ApiModelProperty("AZ_TRAIN_QTY")
    private BigDecimal azTrainQty;

    /** AZ卡航来货数量 */
    @ApiModelProperty("AZ_CAR_QTY")
    private BigDecimal azCarQty;

    /** 海外仓库存数量 */
    @ApiModelProperty("OVERSEA_INV_QTY")
    private BigDecimal overseaInvQty;

    /** 海外仓在途数量 */
    @ApiModelProperty("OVERSEA_ONWAY_QTY")
    private BigDecimal overseaOnwayQty;

    /** 海外仓总库存 */
    @ApiModelProperty("OVERSEA_TOTAL_QTY")
    private BigDecimal overseaTotalQty;

    /** AZ店铺即时库存总数 */
    @ApiModelProperty("AZ_TOTAL_QTY")
    private BigDecimal azTotalQty;

    /** AZ海外总库存 */
    @ApiModelProperty("AZ_OVERSEA_TOTAL_QTY")
    private BigDecimal azOverseaTotalQty;

    @ApiModelProperty("推荐日期")
    private Date bizdate;

    @ApiModelProperty("申请后国内可调拨库存")
    private String commitedAvailQty;



}
