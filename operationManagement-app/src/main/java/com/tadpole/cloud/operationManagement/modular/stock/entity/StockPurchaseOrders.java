package com.tadpole.cloud.operationManagement.modular.stock.entity;

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
    * 采购订单
    * </p>
*
* @author cyt
* @since 2022-06-30
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("STOCK_PURCHASE_ORDERS")
@ExcelIgnoreUnannotated
public class StockPurchaseOrders implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 数据记录ID */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 采购申请单类型 */
    @TableField("PURCHASE_ORDER_TYPE")
    private BigDecimal purchaseOrderType;

    /** 采购订单状态:值域{"待审核"/"不备货"/"待计划部审批"/"计划未通过"/"待PMC审批"/"PMC未通过"/"已通过"}默认值：待审核	 */
    @TableField("ORDER_STATUS")
    private BigDecimal orderStatus;

    /** 默认值：销售需求3-AZ海外总库存D4-国内可用库存-采购未交订单 */
    @TableField("PURCHASE_APPLY_QTY")
    private BigDecimal purchaseApplyQty;

    /** 到达时间 */
    @TableField("ARRIVAL_TIME")
    private Date arrivalTime;

    /** 最近一次下单时间 */
    @TableField("ORDER_LAST_TIME")
    private Date orderLastTime;

    /** 实时计算：销售需求3/（【本月】销量3+【本月+1】销量3+【本月+2】销量3）/90 */
    @TableField("TURNOVER_DAYS")
    private BigDecimal turnoverDays;

    /** 采购申请说明 */
    @TableField("PURCHASE_REASON")
    private String purchaseReason;

    /** 是否超时不备货，根据系统配置，初始化该值 */
    @TableField("OVER_TIME_NOT")
    private Long overTimeNot;

    /** 备货类型:正常备货：0，特殊紧急备货：1 */
    @TableField("BILL_TYPE")
    private String billType;

    /** 推荐日期-按天，最新数据为当前日期 */
    @TableField("BIZDATE")
    private Date bizdate;

    /** 区域-备货区域，备货区域和物理仓关联，目前合并的备货区域只有EU和UX，EU的不同站点间可以互相发货；	墨西哥MX没有可用的物理仓，从US发货，所以就将US和MX合并为北美UX */
    @TableField("AREA")
    private String area;

    /** 平台 */
    @TableField("PLATFORM")
    private String platform;

    /** ASIN */
    @TableField("ASIN")
    private String asin;

    /** 事业部 */
    @TableField("DEPARTMENT")
    private String department;

    /** TEAM */
    @TableField("TEAM")
    private String team;

    /** 物料编码 */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /** 物料创建日期 */
    @TableField("CREATE_DATE")
    private Date createDate;

    /** 推荐运输方式 */
    @TableField("RECOM_DELIVERY_TYPE")
    private String recomDeliveryType;

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

    /** 二级类目 */
    @TableField("MATSTYLESECONDLABEL")
    private String matstylesecondlabel;

    /** 生产周期 */
    @TableField("MATPROCYCLE")
    private Integer matprocycle;

    /** 适用机型 */
    @TableField("TYPE")
    private String type;

    /** 采购起订量 */
    @TableField("MINPOQTY")
    private Long minpoqty;

    /** SPU */
    @TableField("SPU")
    private String spu;

    /** NBDU */
    @TableField("NBDU")
    private String nbdu;

    /** 节日标签 */
    @TableField("FESTIVAL_LABEL")
    private String festivalLabel;

    /** 建议物流方式 */
    @TableField("DELIVERY_TYPE")
    private String deliveryType;

    /** 备货用生产周期 */
    @TableField("SUPPLYCLE")
    private Integer supplycle;

    /** 国内可用库存 */
    @TableField("CANUSEQTY")
    private BigDecimal canuseqty;

    /** 采购未完成数量 */
    @TableField("UNPURCHASE")
    private BigDecimal unpurchase;

    /** 未拣货数量 */
    @TableField("NOPICK")
    private BigDecimal nopick;

    /** 已拣货未打包数量 */
    @TableField("UNPACK")
    private BigDecimal unpack;

    /** 已打包未发货数量 */
    @TableField("UNSEND")
    private BigDecimal unsend;

    /** 国内待发数量 */
    @TableField("INTERNAL_STAY_DELIVER_QTY")
    private BigDecimal internalStayDeliverQty;

    /** Amazon可售数量 */
    @TableField("AMAZON_AVAILABLE_QTY")
    private BigDecimal amazonAvailableQty;

    /** Amazon预留数量 */
    @TableField("AMAZON_RESERVED_QTY")
    private BigDecimal amazonReservedQty;

    /** Amazon物流待发数量 */
    @TableField("AMAZON_STAY_DELIVER_QTY")
    private BigDecimal amazonStayDeliverQty;

    /** 空运数量/Amazon发货数量 */
    @TableField("AIR_QTY")
    private BigDecimal airQty;

    /** 海运数量 */
    @TableField("SHIPPING_QTY")
    private BigDecimal shippingQty;

    /** 铁运数量 */
    @TableField("TRAIN_QTY")
    private BigDecimal trainQty;

    /** 卡运数量 */
    @TableField("CAR_QTY")
    private BigDecimal carQty;

    /** 在库库存数量=可售数量+预留数量 */
    @TableField("IN_STOCK_QTY")
    private BigDecimal inStockQty;

    /** 店铺即时库存总数=AZ在库库存+物流待发数+空运来货数量+海运来货数量+铁运来货数量+卡航来货数量 */
    @TableField("STORE_ONTIME_TOTAL_QTY")
    private BigDecimal storeOntimeTotalQty;

    /** 海外仓在途数量 */
    @TableField("DELIVERY_NUM_OVERSEA")
    private BigDecimal deliveryNumOversea;

    /** 海外仓库存数量 */
    @TableField("INVENTORY_NUM_OVERSEA")
    private BigDecimal inventoryNumOversea;

    /** 海外仓总库存数量=海外仓在途数量+海外仓在库数量 */
    @TableField("TOTAL_INVENTORY_NUM_OVERSEA")
    private BigDecimal totalInventoryNumOversea;

    /** 超180天库龄数量=180-270天库龄数量+270-365天库龄数量+365天以上库龄数量 */
    @TableField("OVER_D180_INV_AGE_QTY")
    private BigDecimal overD180InvAgeQty;

    /** OVER_D180_INV_AGE_QTY_RATE */
    @TableField("OVER_D180_INV_AGE_QTY_RATE")
    private BigDecimal overD180InvAgeQtyRate;

    /** 头程在途预计到货时间:从国内仓到fba仓的头程在途最小到货日期 */
    @TableField("FIRST_ROUTE_INSTOCK_DATE")
    private Date firstRouteInstockDate;

    /** 头程在途到货数量:从国内仓到fba仓的头程在途最小到货日期那天的所有在途数量和 */
    @TableField("FIRST_ROUTE_DELIVERY_QTY")
    private BigDecimal firstRouteDeliveryQty;

    /** 7天销量 */
    @TableField("DAY7QTY")
    private BigDecimal day7qty;

    /** 14天销量 */
    @TableField("DAY14QTY")
    private BigDecimal day14qty;

    /** 30天销量 */
    @TableField("DAY30QTY")
    private BigDecimal day30qty;

    /** 今年上月销量,MC界面不展示，计划部审批界面需要用 */
    @TableField("CUR_PRE_MONTH_QTY")
    private BigDecimal curPreMonthQty;

    /** 去年上月销量,MC界面不展示，计划部审批界面需要用 */
    @TableField("LAST_PRE_MONTH_QTY")
    private BigDecimal lastPreMonthQty;

    /** 今年上月/去年上月:去年本月销量/去年上月销量 */
    @TableField("CUR_PRE_MONTH_LAST_PRE_MONTH")
    private BigDecimal curPreMonthLastPreMonth;

    /** --【本月-3】月销量=（本月-3）月对应维度的月销售数量和， */
    @TableField("CUR_MONTH_MINUS3_QTY")
    private BigDecimal curMonthMinus3Qty;

    /** --【本月-2】月销量=（本月-2）对应维度的月销售数量和 */
    @TableField("CUR_MONTH_MINUS2_QTY")
    private BigDecimal curMonthMinus2Qty;

    /** --【本月-1】月销量=（本月-1）对应维度的月销售数量和 */
    @TableField("CUR_MONTH_MINUS1_QTY")
    private BigDecimal curMonthMinus1Qty;

    /** 【本月】销量=本月1号到（推荐日期-2）这段日期范围销售数量和+（本月天数-推荐日期+2）*日均销量*本月季节系数，默认本月季节系数为1 */
    @TableField("CUR_MONTH_QTY")
    private BigDecimal curMonthQty;

    /** 【本月+1】销量=(本月+2)月份的天数*日均销量*A1 */
    @TableField("CUR_MONTH_ADD1_QTY")
    private BigDecimal curMonthAdd1Qty;

    /** 【本月+2】销量=(本月+2)月份的天数*日均销量*A1*A2 */
    @TableField("CUR_MONTH_ADD2_QTY")
    private BigDecimal curMonthAdd2Qty;

    /** 【本月+3】销量=(本月+2)月份的天数*日均销量*A1*A2*A3 */
    @TableField("CUR_MONTH_ADD3_QTY")
    private BigDecimal curMonthAdd3Qty;

    /** 【本月+4】销量=(本月+2)月份的天数*日均销量*A1*A2*A3*A4 */
    @TableField("CUR_MONTH_ADD4_QTY")
    private BigDecimal curMonthAdd4Qty;

    /** 【本月+5】销量=(本月+2)月份的天数*日均销量*A1*A2*A3*A4*A5 */
    @TableField("CUR_MONTH_ADD5_QTY")
    private BigDecimal curMonthAdd5Qty;

    /** 【本月+6】销量=(本月+2)月份的天数*日均销量*A1*A2*A3*A4*A5*A6 */
    @TableField("CUR_MONTH_ADD6_QTY")
    private BigDecimal curMonthAdd6Qty;

    /** 产品销售等级标签 */
    @TableField("PRODUCT_SALE_LEVEL")
    private String productSaleLevel;

    /** 产品销售等级标签-team维度 */
    @TableField("PRODUCT_SALE_LEVEL_TEAM")
    private String productSaleLevelTeam;


    /** 日均销量 */
    @TableField("DAYAVGQTY")
    private BigDecimal dayavgqty;

    /** Amazon店铺库存供货天数 */
    @TableField("SHOP_SUPPLYDAYS")
    private BigDecimal shopSupplydays;

    /** 建议备货数量 */
    @TableField("RECOM_PRE_QTY")
    private BigDecimal recomPreQty;

    /** 季节标签 */
    @TableField("SEASON_LABEL")
    private String seasonLabel;

    /** VERSION */
    @TableField("VERSION")
    private String version;

    /** FLAG */
    @TableField("FLAG")
    private BigDecimal flag;

    /** 可售库存供货天数 */
    @TableField("SELLABLE_SUPPLYDAYS")
    private BigDecimal sellableSupplydays;

    /** 类目 */
    @TableField("CATEGORY")
    private String category;

    /** 采购起订量备注 */
    @TableField("MINPOQTY_NOTES")
    private String minpoqtyNotes;

 /** 拼单起订量 */
 @TableField("SPELL_ORDERNUM")
 private Long spellOrdernum;


 /** 拼单起订量备注 */
 @TableField("SPELL_ORDERNUM_REMARK ")
 private String spellOrdernumRemark;

    /** FBA最早接收日期 */
    @TableField("FBA_FIRST_RECEIVEDATE")
    private Date fbaFirstReceivedate;

    /** 备注 */
    @TableField("NOTE")
    private String note;

    /** 安全销售天数 */
    @TableField("SAFE_SALEDAYS")
    private BigDecimal safeSaledays;

    /** 订货天数 */
    @TableField("ORDER_DAYS")
    private BigDecimal orderDays;

    /** 运输天数 */
    @TableField("DELIVERY_DAYS")
    private BigDecimal deliveryDays;

    /** 订单处理天数 */
    @TableField("ORDER_DEAL_DAY")
    private BigDecimal orderDealDay;

    /** 海外仓处理天数 */
    @TableField("OUTER_DEAL_DAY")
    private BigDecimal outerDealDay;

    /** 国内仓处理天数 */
    @TableField("INNER_DEAL_DAY")
    private BigDecimal innerDealDay;

    /** 总备货天数 */
    @TableField("TOTAL_BACK_DAYS")
    private BigDecimal totalBackDays;

    /** 日均销量计算公式 */
    @TableField("FORMULA")
    private String formula;

    /** 日均销量计算明细 */
    @TableField("FORMULA_NUM")
    private String formulaNum;

    /** 总备货天数 */
    @TableField("PREPARE_DAYS")
    private BigDecimal prepareDays;

    /** 推荐备货数量公式 */
    @TableField("FORMULA_PREQTY")
    private String formulaPreqty;

    /** TURNOVER_DAYS */
    @TableField("TURNOVER_DAYS2")
    private BigDecimal turnoverDays2;

    /** 申请审核中数量 */
    @TableField("APPROVE_QTY")
    private BigDecimal approveQty;

    /** 推荐备货覆盖销售日期=推荐日期+总备货天数 */
    @TableField("RECOM_BACK_OVER_DAYS")
    private Date recomBackOverDays;

    /** 预估销售数量 */
    @TableField("PRE_SALE_QTY")
    private BigDecimal preSaleQty;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 期望交期 */
    @TableField("EXPECTED_DELIVERY_DATE")
    private Date expectedDeliveryDate;

    /** AZ海外总库存 */
    @TableField("TOTAL_VOLUME")
    private BigDecimal totalVolume;

    /** 运营期望交期 */
    @TableField("OPER_EXPECTED_DATE")
    private Date operExpectedDate;

    /** 建议采购申请数量 默认值：销售需求3-AZ海外总库存D4-国内可用库存-采购未交订单 */
    @TableField("ADVICE_APPLY_QTY")
    private BigDecimal adviceApplyQty;

}