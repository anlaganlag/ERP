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
    * 发货推荐数据快照
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
@TableName("SHIPMENT_RECOM_SNAPSHOT")
@ExcelIgnoreUnannotated
public class ShipmentRecomSnapshot implements Serializable {

    private static final long serialVersionUID = 1L;


    /** ID */
    @TableId(type = IdType.ASSIGN_ID)
    private BigDecimal id;

    /** 推荐日期 */
    @TableField("BIZDATE")
    private Date bizdate;

    /** 平台 */
    @TableField("PLATFORM")
    private String platform;

    /** 事业部 */
    @TableField("DEPARTMENT")
    private String department;

    /** Team */
    @TableField("TEAM")
    private String team;

    /** 物料编码 */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /** 区域 */
    @TableField("PRE_AREA")
    private String preArea;

    /** ASIN */
    @TableField("ASIN")
    private String asin;

    /** AZ可售数量 */
    @TableField("AZ_AVAIL_QTY")
    private BigDecimal azAvailQty;

    /** AZ预留数量 */
    @TableField("AZ_RESERVED_QTY")
    private BigDecimal azReservedQty;

    /** AZ物流待发数量 */
    @TableField("AZ_WAIT_SEND_QTY")
    private BigDecimal azWaitSendQty;

    /** AZ空运来货数量 */
    @TableField("AZ_AIR_QTY")
    private BigDecimal azAirQty;

    /** AZ海运来货数量 */
    @TableField("AZ_SHIP_QTY")
    private BigDecimal azShipQty;

    /** AZ铁运来货数量 */
    @TableField("AZ_TRAIN_QTY")
    private BigDecimal azTrainQty;

    /** AZ卡航来货数量 */
    @TableField("AZ_CAR_QTY")
    private BigDecimal azCarQty;

    /** 海外仓库存数量 */
    @TableField("OVERSEA_INV_QTY")
    private BigDecimal overseaInvQty;

    /** 海外仓在途数量 */
    @TableField("OVERSEA_ONWAY_QTY")
    private BigDecimal overseaOnwayQty;

    /** 海外仓总库存 */
    @TableField("OVERSEA_TOTAL_QTY")
    private BigDecimal overseaTotalQty;

    /** AZ店铺即时库存总数 */
    @TableField("AZ_TOTAL_QTY")
    private BigDecimal azTotalQty;

    /** AZ海外总库存 */
    @TableField("AZ_OVERSEA_TOTAL_QTY")
    private BigDecimal azOverseaTotalQty;

    /** 【本月】销量 */
    @TableField("CURQTY")
    private BigDecimal curqty;

    /** 【本月+1】销量 */
    @TableField("NEXTM_MON1QTY")
    private BigDecimal nextmMon1qty;

    /** 【本月+2】销量 */
    @TableField("NEXTM_MON2QTY")
    private BigDecimal nextmMon2qty;

    /** 【本月+3】销量 */
    @TableField("NEXTM_MON3QTY")
    private BigDecimal nextmMon3qty;

    /** 【本月+4】销量 */
    @TableField("NEXTM_MON4QTY")
    private BigDecimal nextmMon4qty;

    /** 【本月+5】销量 */
    @TableField("NEXTM_MON5QTY")
    private BigDecimal nextmMon5qty;

    /** 【本月+6】销量 */
    @TableField("NEXTM_MON6QTY")
    private BigDecimal nextmMon6qty;

    /** 7天销售数量 */
    @TableField("DAY7QTY")
    private BigDecimal day7qty;

    /** 14天销售数量 */
    @TableField("DAY14QTY")
    private BigDecimal day14qty;

    /** 30天销售数量 */
    @TableField("DAY30QTY")
    private BigDecimal day30qty;

    /** 日均销量 */
    @TableField("DAYAVGSELL")
    private BigDecimal dayavgsell;

    /** 备货用生产周期 */
    @TableField("RESTOCK_PRODUCTION_CYCLE")
    private BigDecimal restockProductionCycle;

    /** 推荐运输方式 */
    @TableField("RECOMM_TRANSPORTATION")
    private String recommTransportation;

    /** 申请物流方式 */
    @TableField("APPLY_TRANSPORTATION")
    private String applyTransportation;

    /** 国内仓处理天数 */
    @TableField("DOMESTIC_WAREHOUSE_HANDLE_DAYS")
    private BigDecimal domesticWarehouseHandleDays;

    /** 运输天数 */
    @TableField("DELIVERY_DAYS")
    private BigDecimal deliveryDays;

    /** 海外仓处理天数 */
    @TableField("OVERSEA_WAREHOUSER_HANDLE_DAYS")
    private BigDecimal overseaWarehouserHandleDays;

    /** 安全销售天数 */
    @TableField("SALE_SAFELY_DAYS")
    private BigDecimal saleSafelyDays;

    /** 发货间隔天数 */
    @TableField("SEND_GAP_DAYS")
    private BigDecimal sendGapDays;

    /** 国内可用库存 */
    @TableField("DOMESTIC_AVAIL_QTY")
    private BigDecimal domesticAvailQty;

    /** 国内仓可调拨数量 */
    @TableField("DOMESTIC_TRANSFER_AVAIL_QTY")
    private BigDecimal domesticTransferAvailQty;

    /** 国内待质检数量 */
    @TableField("DOMESTIC_UNCHECK_QTY")
    private BigDecimal domesticUncheckQty;

    /** 总发货天数 */
    @TableField("SEND_TOTAL_DAYS")
    private BigDecimal sendTotalDays;

    /** 总发货天数覆盖销售日期 */
    @TableField("CAN_SALE_DATE")
    private Date canSaleDate;

    /** 发货总销售需求 */
    @TableField("REQUIREMENT_SEND_TOTAL_QTY")
    private BigDecimal requirementSendTotalQty;

    /** 发货后周转天数 */
    @TableField("TURNOVER_AFTER_SEND_DAYS")
    private BigDecimal turnoverAfterSendDays;

    /** 发货前周转天数 */
    @TableField("TURNOVER_BEFORE_SEND_DAYS")
    private BigDecimal turnoverBeforeSendDays;

    /** 推荐国内发货数量 */
    @TableField("DOMESTIC_RECOMM_SEND_QTY")
    private BigDecimal domesticRecommSendQty;

    /** 申请发货数量 */
    @TableField("APPLY_SEND_QTY")
    private BigDecimal applySendQty;

    /** 在途数量供应情况 */
    @TableField("IS_CUT_GOODS")
    private String isCutGoods;

    /** 类目 */
    @TableField("CATEGORY")
    private String category;

    /** 运营大类 */
    @TableField("PRODUCT_TYPE")
    private String productType;

    /** 产品名称 */
    @TableField("PRODUCT_NAME")
    private String productName;

    /** 款式 */
    @TableField("STYLE")
    private String style;

    /** 主材料 */
    @TableField("MAIN_MATERIAL")
    private String mainMaterial;

    /** 图案 */
    @TableField("PATTERN")
    private String pattern;

    /** 公司品牌 */
    @TableField("COMPANY_BRAND")
    private String companyBrand;

    /** 适用品牌或对象 */
    @TableField("BRAND")
    private String brand;

    /** 型号 */
    @TableField("MODEL")
    private String model;

    /** 颜色 */
    @TableField("COLOR")
    private String color;

    /** 尺码 */
    @TableField("SIZES")
    private String sizes;

    /** 包装数量 */
    @TableField("PACKING")
    private String packing;

    /** 版本描述 */
    @TableField("VERSION_DES")
    private String versionDes;

    /** 适用机型 */
    @TableField("TYPE")
    private String type;

    /** 二级类目 */
    @TableField("MATSTYLESECONDLABEL")
    private String matstylesecondlabel;

    /** 未完成订单-;7天内预计到货数量 */
    @TableField("UNDONE_IN7DAYS_QTY")
    private BigDecimal undoneIn7daysQty;

    /** 未完成订单-;8-14天预计到货数量 */
    @TableField("UNDONE_8TO14DAYS_QTY")
    private BigDecimal undone8to14daysQty;

    /** 未完成订单-;15天后预计到货数量 */
    @TableField("UNDONE_AFTER15DAYS_QTY")
    private BigDecimal undoneAfter15daysQty;

    /** 同步时间;从BI同步的时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

    /** 状态;申请状态 */
    @TableField("STATUS")
    private Integer status;

    /** 节日标签 */
    @TableField("festival_label")
    private String festivalLabel;

    /** 季节标签 */
    @TableField("season_label")
    private String seasonLabel;

    /*** 源净重量*/
    @TableField("NET_WEIGHT_ORG")
    private BigDecimal netWeightOrg;

    /*** 源毛重*/
    @TableField("BOX_WEIGHT2_ORG")
    private BigDecimal boxWeight2Org;


    /*** 源包装体积*/
    @TableField("BOX_VOLUME_ORG")
    private BigDecimal boxVolumeOrg;
}




