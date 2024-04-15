package com.tadpole.cloud.operationManagement.modular.shipment.model.params;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* <p>
    * 发货推荐
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
@TableName("SHIPMENT_RECOMMENDATION")
public class ShipmentRecommendationApplyItemParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 推荐日期 */
    @ApiModelProperty("推荐日期")
    private Date bizdate;

    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** 事业部 */
    @ApiModelProperty("事业部")
    private String department;

    /** Team */
    @ApiModelProperty("Team")
    private String team;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String materialCode;

    /** 区域 */
    @ApiModelProperty("区域")
    private String preArea;

    /** ASIN */
    @ApiModelProperty("ASIN")
    private String asin;

    /** AZ可售数量 */
    @ApiModelProperty("AZ可售数量")
    private BigDecimal azAvailQty;

    /** AZ预留数量 */
    @ApiModelProperty("AZ预留数量")
    private BigDecimal azReservedQty;

    /** AZ物流待发数量 */
    @ApiModelProperty("AZ物流待发数量")
    private BigDecimal azWaitSendQty;

    /** AZ空运来货数量 */
    @ApiModelProperty("AZ空运来货数量")
    private BigDecimal azAirQty;

    /** AZ海运来货数量 */
    @ApiModelProperty("AZ海运来货数量")
    private BigDecimal azShipQty;

    /** AZ铁运来货数量 */
    @ApiModelProperty("AZ铁运来货数量")
    private BigDecimal azTrainQty;

    /** AZ卡航来货数量 */
    @ApiModelProperty("AZ卡航来货数量")
    private BigDecimal azCarQty;

    /** 海外仓库存数量 */
    @ApiModelProperty("海外仓库存数量")
    private BigDecimal overseaInvQty;

    /** 海外仓在途数量 */
    @ApiModelProperty("海外仓在途数量")
    private BigDecimal overseaOnwayQty;

    /** 海外仓总库存 */
    @ApiModelProperty("海外仓总库存")
    private BigDecimal overseaTotalQty;

    /** AZ店铺即时库存总数 */
    @ApiModelProperty("AZ店铺即时库存总数")
    private BigDecimal azTotalQty;

    /** AZ海外总库存 */
    @ApiModelProperty("AZ海外总库存")
    private BigDecimal azOverseaTotalQty;

    /** 【本月】销量 */
    @ApiModelProperty("【本月】销量")
    private BigDecimal curqty;

    /** 【本月+1】销量 */
    @ApiModelProperty("【本月+1】销量")
    private BigDecimal nextmMon1qty;

    /** 【本月+2】销量 */
    @ApiModelProperty("【本月+2】销量")
    private BigDecimal nextmMon2qty;

    /** 【本月+3】销量 */
    @ApiModelProperty("【本月+3】销量")
    private BigDecimal nextmMon3qty;

    /** 【本月+4】销量 */
    @ApiModelProperty("【本月+4】销量")
    private BigDecimal nextmMon4qty;

    /** 【本月+5】销量 */
    @ApiModelProperty("【本月+5】销量")
    private BigDecimal nextmMon5qty;

    /** 【本月+6】销量 */
    @ApiModelProperty("【本月+6】销量")
    private BigDecimal nextmMon6qty;

    /** 7天销售数量 */
    @ApiModelProperty("7天销售数量")
    private BigDecimal day7qty;

    /** 14天销售数量 */
    @ApiModelProperty("14天销售数量")
    private BigDecimal day14qty;

    /** 30天销售数量 */
    @ApiModelProperty("30天销售数量")
    private BigDecimal day30qty;

    /** 日均销量 */
    @ApiModelProperty("日均销量")
    private BigDecimal dayavgsell;

    /** 备货用生产周期 */
    @ApiModelProperty("备货用生产周期")
    private BigDecimal restockProductionCycle;

    /** 推荐运输方式 */
    @ApiModelProperty("推荐运输方式")
    private String recommTransportation;

    /** 国内仓处理天数 */
    @ApiModelProperty("国内仓处理天数")
    private BigDecimal domesticWarehouseHandleDays;

    /** 运输天数 */
    @ApiModelProperty("运输天数")
    private BigDecimal deliveryDays;

    /** 海外仓处理天数 */
    @ApiModelProperty("海外仓处理天数")
    private BigDecimal overseaWarehouserHandleDays;

    /** 安全销售天数 */
    @ApiModelProperty("安全销售天数")
    private BigDecimal saleSafelyDays;

    /** 发货间隔天数 */
    @ApiModelProperty("发货间隔天数")
    private BigDecimal sendGapDays;

    /** 国内可用库存 */
    @ApiModelProperty("国内可用库存")
    private BigDecimal domesticAvailQty;

    /** 国内仓可调拨数量 */
    @ApiModelProperty("国内仓可调拨数量")
    private BigDecimal domesticTransferAvailQty;

    /** 国内待质检数量 */
    @ApiModelProperty("国内待质检数量")
    private BigDecimal domesticUncheckQty;

    /** 总发货天数 */
    @ApiModelProperty("总发货天数")
    private BigDecimal sendTotalDays;

    /** 总发货天数覆盖销售日期 */
    @ApiModelProperty("总发货天数覆盖销售日期")
    private Date canSaleDate;

    /** 发货总销售需求 */
    @ApiModelProperty("发货总销售需求")
    private BigDecimal requirementSendTotalQty;

    /** 发货前周转天数 */
    @ApiModelProperty("发货前周转天数")
    private BigDecimal turnoverBeforeSendDays;

    /** 推荐国内发货数量 */
    @ApiModelProperty("推荐国内发货数量")
    private BigDecimal domesticRecommSendQty;

    /** 在途数量供应情况 */
    @ApiModelProperty("在途数量供应情况")
    private String isCutGoods;

    /** 类目 */
    @ApiModelProperty("类目")
    private String category;

    /** 运营大类 */
    @ApiModelProperty("运营大类")
    private String productType;

    /** 产品名称 */
    @ApiModelProperty("产品名称")
    private String productName;

    /** 款式 */
    @ApiModelProperty("款式")
    private String style;

    /** 主材料 */
    @ApiModelProperty("主材料")
    private String mainMaterial;

    /** 图案 */
    @ApiModelProperty("图案")
    private String pattern;

    /** 公司品牌 */
    @ApiModelProperty("公司品牌")
    private String companyBrand;

    /** 适用品牌或对象 */
    @ApiModelProperty("适用品牌或对象")
    private String brand;

    /** 型号 */
    @ApiModelProperty("型号")
    private String model;

    /** 颜色 */
    @ApiModelProperty("颜色")
    private String color;

    /** 尺码 */
    @ApiModelProperty("尺码")
    private String sizes;

    /** 包装数量 */
    @ApiModelProperty("包装数量")
    private String packing;

    /** 版本描述 */
    @ApiModelProperty("版本描述")
    private String versionDes;

    /** 适用机型 */
    @ApiModelProperty("适用机型")
    private String type;

    /** 二级类目 */
    @ApiModelProperty("二级类目")
    private String matstylesecondlabel;

    /** 未完成订单-;7天内预计到货数量 */
    @ApiModelProperty("未完成订单-;7天内预计到货数量")
    private BigDecimal undoneIn7daysQty;

    /** 未完成订单-;8-14天预计到货数量 */
    @ApiModelProperty("未完成订单-;8-14天预计到货数量")
    private BigDecimal undone8to14daysQty;

    /** 未完成订单-;15天后预计到货数量 */
    @ApiModelProperty("未完成订单-;15天后预计到货数量")
    private BigDecimal undoneAfter15daysQty;

    /** 同步时间;从BI同步的时间 */
    @ApiModelProperty("同步时间;从BI同步的时间")
    private Date syncTime;

    /** 状态;申请状态 */
    @ApiModelProperty("状态;申请状态")
    private Integer status;


    /** 物料编码List */
    @ApiModelProperty("物料编码List")
    private List<String> materialCodeList;


    /** asinList */
    @ApiModelProperty("asin_list")
    private List<String> asinList;


    /** 只显示无申请记录 */
    @ApiModelProperty("只显示无申请记录")
    private Boolean isNoApply;


    /** 店铺名称 */
    @ApiModelProperty("店铺名称")
    private String shopName;

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;


    /** sku */
    @ApiModelProperty("sku")
    private String sku;


    /** FBA物流号 */
    @ApiModelProperty("FBA物流号")
    private String fbaNo;





    /** 店铺名称 */
    @ApiModelProperty("店铺名称")
    private String receive_warehouse;

    /** 发货数量 */
    @ApiModelProperty("发货数量")
    private String sendQty;


    /** 发货类型 */
    @ApiModelProperty("发货类型")
    private String transportationType;


    /** UNW类型 */
    @ApiModelProperty("UNW类型")
    private String unwType;


    /** 备注1 */
    @ApiModelProperty("备注1")
    private String remark1;

    /** 备注2 */
    @ApiModelProperty("备注2")
    private String remark2;


    /** 备注3 */
    @ApiModelProperty("备注3")
    private String remark3;





}
