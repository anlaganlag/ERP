package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("BACK_PREPARE_RECOM_BI")
public class BackPrepareRecomBiExcel implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 推荐日期 */
    @ApiModelProperty("BIZDATE")
    private Date bizdate;

    /** 平台 */
    @ApiModelProperty("PLATFORM")
    private String platform;

    /** 事业部 */
    @ApiModelProperty("DEPARTMENT")
    private String department;

    /** Team */
    @ApiModelProperty("TEAM")
    private String team;

    /** 物料编码 */
    @ApiModelProperty("MATERIAL_CODE")
    private String materialCode;

    /** ASIN */
    @ApiModelProperty("ASIN")
    private String ASIN;

    /** 物料创建日期 */
    @ApiModelProperty("CREATE_DATE")
    private Date createDate;

    /** 运营大类 */
    @ApiModelProperty("PRODUCT_TYPE")
    private String productType;

    /** 产品名称 */
    @ApiModelProperty("PRODUCT_NAME")
    private String productName;

    /** 款式 */
    @ApiModelProperty("STYLE")
    private String style;

    /** 主材料 */
    @ApiModelProperty("MAIN_MATERIAL")
    private String mainMaterial;

    /** 图案 */
    @ApiModelProperty("PATTERN")
    private String pattern;

    /** 公司品牌 */
    @ApiModelProperty("COMPANY_BRAND")
    private String companyBrand;

    /** 适用品牌或对象 */
    @ApiModelProperty("BRAND")
    private String brand;

    /** 型号 */
    @ApiModelProperty("MODEL")
    private String model;

    /** 二级类目 */
    @ApiModelProperty("MATSTYLESECONDLABEL")
    private String matstylesecondlabel;

    /** 颜色 */
    @ApiModelProperty("COLOR")
    private String color;

    /** 尺码 */
    @ApiModelProperty("SIZES")
    private String sizes;

    /** 包装数量 */
    @ApiModelProperty("PACKING")
    private String packing;

    /** 版本 */
    @ApiModelProperty("VERSION_DES")
    private String versionDes;

    /** 适用机型 */
    @ApiModelProperty("TYPE")
    private String type;

    /** MOQ */
    @ApiModelProperty("MINPOQTY")
    private BigDecimal minpoqty;


    /** 拼单起订量 */
    @ApiModelProperty("SPELL_ORDERNUM")
    private Long spellOrdernum;


    /** 拼单起订量备注 */
    @ApiModelProperty("SPELL_ORDERNUM_REMARK ")
    private String spellOrdernumRemark;

    /** 节日标签 */
    @ApiModelProperty("FESTIVAL_LABEL")
    private String festivalLabel;

    /** 季节标签 */
    @ApiModelProperty("SEASON_LABEL")
    private String seasonLabel;

    /** 区域 */
    @ApiModelProperty("AREA")
    private String area;

    /** AZ海外总库存D6 */
    @ApiModelProperty("TOTAL_VOLUME")
    private BigDecimal totalVolume;

    /** 国内待发货数量D6 */
    @ApiModelProperty("INTERNAL_STAY_DELIVER_QTY")
    private BigDecimal internalStayDeliverQty;

    /** AZ店铺即时库存总数D6 */
    @ApiModelProperty("STORE_ONTIME_TOTAL_QTY")
    private BigDecimal storeOntimeTotalQty;

    /** Az在库库存D6 */
    @ApiModelProperty("IN_STOCK_QTY")
    private BigDecimal inStockQty;

    /** Az物流待发数量D6 */
    @ApiModelProperty("AMAZON_STAY_DELIVER_QTY")
    private BigDecimal amazonStayDeliverQty;

    /** Az空运来货数量D6 */
    @ApiModelProperty("AIR_QTY")
    private BigDecimal airQty;

    /** Az海运来货数量D6 */
    @ApiModelProperty("SHIPPING_QTY")
    private BigDecimal shippingQty;

    /** Az铁运来货数量D6 */
    @ApiModelProperty("TRAIN_QTY")
    private BigDecimal trainQty;

    /** Az卡航来货数量D6 */
    @ApiModelProperty("CAR_QTY")
    private BigDecimal carQty;

    /** 海外仓总库存D6 */
    @ApiModelProperty("TOTAL_INVENTORY_NUM_OVERSEA")
    private BigDecimal totalInventoryNumOversea;

    /** 海外仓库存数量D6 */
    @ApiModelProperty("INVENTORY_NUM_OVERSEA")
    private BigDecimal inventoryNumOversea;

    /** 海外仓在途数量D6 */
    @ApiModelProperty("DELIVERY_NUM_OVERSEA")
    private BigDecimal deliveryNumOversea;

    /** 国内可用库存 */
    @ApiModelProperty("CANUSEQTY")
    private BigDecimal canuseqty;

    /** 采购未交订单 */
    @ApiModelProperty("UNPURCHASE")
    private BigDecimal unpurchase;

    /** 申请审核中数量 */
    @ApiModelProperty("APPROVE_QTY")
    private BigDecimal approveQty;

    /** 头程在途预计最近到货时间 */
    @ApiModelProperty("FIRST_ROUTE_INSTOCK_DATE")
    private Date firstRouteInstockDate;

    /** 头程在途到货数量 */
    @ApiModelProperty("FIRST_ROUTE_DELIVERY_QTY")
    private BigDecimal firstRouteDeliveryQty;

    /** {curMonthMinus3}月 */
    @ApiModelProperty("CUR_MONTH_MINUS3_QTY")
    private BigDecimal curMonthMinus3Qty;

    /** {curMonthMinus2}月 */
    @ApiModelProperty("CUR_MONTH_MINUS2_QTY")
    private BigDecimal curMonthMinus2Qty;

    /** {curMonthMinus1}月 */
    @ApiModelProperty("CUR_MONTH_MINUS1_QTY")
    private BigDecimal curMonthMinus1Qty;

    /** {curMonth}月 */
    @ApiModelProperty("CUR_MONTH_QTY")
    private BigDecimal curMonthQty;

    /** {curMonthAdd1}月 */
    @ApiModelProperty("CUR_MONTH_ADD1_QTY")
    private BigDecimal curMonthAdd1Qty;

    /** {curMonthAdd2}月 */
    @ApiModelProperty("CUR_MONTH_ADD2_QTY")
    private BigDecimal curMonthAdd2Qty;

    /** {curMonthAdd3}月 */
    @ApiModelProperty("CUR_MONTH_ADD3_QTY")
    private BigDecimal curMonthAdd3Qty;

    /** {curMonthAdd4}月 */
    @ApiModelProperty("CUR_MONTH_ADD4_QTY")
    private BigDecimal curMonthAdd4Qty;

    /** {curMonthAdd5}月 */
    @ApiModelProperty("CUR_MONTH_ADD5_QTY")
    private BigDecimal curMonthAdd5Qty;

    /** {curMonthAdd6}月 */
    @ApiModelProperty("CUR_MONTH_ADD6_QTY")
    private BigDecimal curMonthAdd6Qty;

    /** 销售等级标签 */
    @ApiModelProperty("PRODUCT_SALE_LEVEL")
    private String productSaleLevel;

    /**
     * 产品销售等级标签-team维度
     */
    @ApiModelProperty("productSaleLevelTeam")
    @ExcelProperty("productSaleLevelTeam")
    private String productSaleLevelTeam;

    /** Az7天销售数量D6 */
    @ApiModelProperty("DAY7QTY")
    private BigDecimal day7qty;

    /** Az14天销售数量D6 */
    @ApiModelProperty("DAY14QTY")
    private BigDecimal day14qty;

    /** Az30天销售数量D6 */
    @ApiModelProperty("DAY30QTY")
    private BigDecimal day30qty;

    /** 今年上月/去年上月 */
    @ApiModelProperty("CUR_PRE_MONTH_LAST_PRE_MONTH")
    private BigDecimal curPreMonthLastPreMonth;

    /** 日均销量D6 */
    @ApiModelProperty("DAYAVGQTY")
    private BigDecimal dayavgqty;

    /** Amazon店铺库存供货天数D6 */
    @ApiModelProperty("PREPARE_DAYS")
    private BigDecimal prepareDays;

    /** AZ超180天库龄数量占比D6 */
    @ApiModelProperty("OVER_D180_INV_AGE_QTY_RATE")
    private BigDecimal overD180InvAgeQtyRate;

    /** 推荐运输方式D6 */
    @ApiModelProperty("RECOM_DELIVERY_TYPE")
    private String recomDeliveryType;

    /** 总备货天数D6 */
    @ApiModelProperty("TOTAL_BACK_DAYS")
    private BigDecimal totalBackDays;

    /** 备货用生产周期D6 */
    @ApiModelProperty("SUPPLYCLE")
    private BigDecimal supplycle;

    /** 安全销售天数D6 */
    @ApiModelProperty("SAFE_SALEDAYS")
    private BigDecimal safeSaledays;

    /** 订货天数D6 */
    @ApiModelProperty("ORDER_DAYS")
    private BigDecimal orderDays;

    /** 运输天数D6 */
    @ApiModelProperty("DELIVERY_DAYS")
    private BigDecimal deliveryDays;

    /** 订单处理天数D6 */
    @ApiModelProperty("ORDER_DEAL_DAY")
    private BigDecimal orderDealDay;

    /** 海外仓处理天数D6 */
    @ApiModelProperty("OUTER_DEAL_DAY")
    private BigDecimal outerDealDay;

    /** 国内仓处理天数D6 */
    @ApiModelProperty("INNER_DEAL_DAY")
    private BigDecimal innerDealDay;

    /** 预估销售数量D6 */
    @ApiModelProperty("PRE_SALE_QTY")
    private BigDecimal preSaleQty;

    /** 推荐区域备货数量D6 */
    @ApiModelProperty("RECOM_PRE_QTY")
    private BigDecimal recomPreQty;

    /** 推荐备货覆盖销售日期D6 */
    @ApiModelProperty("RECOM_BACK_OVER_DAYS")
    private Date recomBackOverDays;

    /** BI备注 */
    @ApiModelProperty("NOTE")
    private String note;


}
