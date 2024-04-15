package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 备货申请信息
 * </p>
 *
 * @author lsy
 * @since 2022-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class OperApplyInfoExcelExportParam implements Serializable, BaseValidatingParam {

 private static final long serialVersionUID = 1L;


 @TableId("ID")
 @ExcelProperty(value = "id")
 private BigDecimal id;

 /** 默认值：推荐的【本月】销量D6；运营申请填写的字段 */
 @TableField("OPER_CUR_MONTH_QTY")
 @ExcelProperty(value = "operCurMonthQty")
 private BigDecimal operCurMonthQty;

 /** 默认值：推荐的【本月+1】销量D6；运营申请填写的字段 */
 @TableField("OPER_CUR_MONTH_ADD1_QTY")
 @ExcelProperty(value = "operCurMonthAdd1Qty")
 private BigDecimal operCurMonthAdd1Qty;

 /** 默认值：推荐的【本月+2】销量D6；运营申请填写的字段 */
 @TableField("OPER_CUR_MONTH_ADD2_QTY")
 @ExcelProperty(value = "operCurMonthAdd2Qty")
 private BigDecimal operCurMonthAdd2Qty;

 /** 默认值：推荐的【本月+3】销量D6；运营申请填写的字段 */
 @TableField("OPER_CUR_MONTH_ADD3_QTY")
 @ExcelProperty(value = "operCurMonthAdd3Qty")
 private BigDecimal operCurMonthAdd3Qty;

 /** 默认值：推荐的【本月+4】销量D6；运营申请填写的字段 */
 @TableField("OPER_CUR_MONTH_ADD4_QTY")
 @ExcelProperty(value = "operCurMonthAdd4Qty")
 private BigDecimal operCurMonthAdd4Qty;

 /** 默认值：推荐的【本月+5】销量D6；运营申请填写的字段 */
 @TableField("OPER_CUR_MONTH_ADD5_QTY")
 @ExcelProperty(value = "operCurMonthAdd5Qty")
 private BigDecimal operCurMonthAdd5Qty;

 /** 默认值：推荐的【本月+6】销量D6；运营申请填写的字段 */
 @TableField("OPER_CUR_MONTH_ADD6_QTY")
 @ExcelProperty(value = "operCurMonthAdd6Qty")
 private BigDecimal operCurMonthAdd6Qty;

 /** 销售需求备货天数1 */
 @TableField("SALES_STOCK_DAYS")
 @ExcelProperty(value = "salesStockDays")
 private BigDecimal salesStockDays;

 /** 基于销售需求备货天数计算出来的可销售日期取值来源：推荐日期+销售需求备货天数1 */
 @TableField("OPER_COVERS_SALES_DATE")
 @ExcelProperty(value = "operCoversSalesDate")
 @DateTimeFormat
 private String operCoversSalesDate;

 /** 额外备货天数->实时计算：销售需求备货天数1-总备货天数D6 */
 @TableField("EXTRA_DAYS")
 @ExcelProperty(value = "extraDays")
 private String extraDays;

 /** 销售需求：(设：运营需求覆盖销售日期.month(-推荐日期.month(-1 为N；
  取值来源：
  (【本月】销量 * (（30-推荐日期.day(/30 +
  【本月+1】销量+…+
  【本月+N】销量+
  (【本月+N+1】销量*运营需求覆盖销售日期.day(/30 */
 @TableField("SALES_DEMAND")
 @ExcelProperty(value = "salesDemand")
 private String salesDemand;

 /**申请区域备货数量： 取值来源：销售需求-全流程总库存 **/
 @TableField("STOCK_QTY_AREA")
 @ExcelProperty(value = "stockQtyArea")
 private String stockQtyArea;

 /**申请区域备货后周转天数： 取值来源：销售需求/日均销量D6 **/
 @TableField("TURNOVER_DAYS_AREA")
 @ExcelProperty(value = "turnoverDaysArea")
 private String turnoverDaysArea;

 /** 申请备货原因1 */
 @TableField("STOCK_REASON")
 @ExcelProperty(value = "stockReason")
 private String stockReason;


 /** 推荐日期-按天，最新数据为当前日期 */
 @TableField("BIZDATE")
 @ExcelProperty(value = "bizdate")
 @DateTimeFormat
 private Date bizdate;

 /** 区域-备货区域，备货区域和物理仓关联，目前合并的备货区域只有EU和UX，EU的不同站点间可以互相发货；
  墨西哥MX没有可用的物理仓，从US发货，所以就将US和MX合并为北美UX */
 @TableField("AREA")
 @ExcelProperty(value = "area")
 private String area;

 /** 平台 */
 @TableField("PLATFORM")
 @ExcelProperty(value = "platform")
 private String platform;

 /** ASIN */
 @TableField("ASIN")
 @ExcelProperty(value = "asin")
 private String asin;

 /** 事业部 */
 @TableField("DEPARTMENT")
 @ExcelProperty(value = "department")
 private String department;

 /** TEAM */
 @TableField("TEAM")
 @ExcelProperty(value = "team")
 private String team;

 /** 物料编码 */
 @TableField("MATERIAL_CODE")
 @ExcelProperty(value = "materialCode")
 private String materialCode;

 /** 物料创建日期 */
 @TableField("CREATE_DATE")
 @ExcelProperty(value = "createDate")
 @DateTimeFormat
 private Date createDate;

 /** 推荐运输方式 */
 @TableField("RECOM_DELIVERY_TYPE")
 @ExcelProperty(value = "recomDeliveryType")
 private String recomDeliveryType;

 /** 运营大类 */
 @TableField("PRODUCT_TYPE")
 @ExcelProperty(value = "productType")
 private String productType;

 /** 产品名称 */
 @TableField("PRODUCT_NAME")
 @ExcelProperty(value = "productName")
 private String productName;

 /** 款式 */
 @TableField("STYLE")
 @ExcelProperty(value = "style")
 private String style;

 /** 主材料 */
 @TableField("MAIN_MATERIAL")
 @ExcelProperty(value = "mainMaterial")
 private String mainMaterial;

 /** 图案 */
 @TableField("PATTERN")
 @ExcelProperty(value = "pattern")
 private String pattern;

 /** 公司品牌 */
 @TableField("COMPANY_BRAND")
 @ExcelProperty(value = "companyBrand")
 private String companyBrand;

 /** 适用品牌或对象 */
 @TableField("BRAND")
 @ExcelProperty(value = "brand")
 private String brand;

 /** 型号 */
 @TableField("MODEL")
 @ExcelProperty(value = "model")
 private String model;

 /** 颜色 */
 @TableField("COLOR")
 @ExcelProperty(value = "color")
 private String color;

 /** 尺码 */
 @TableField("SIZES")
 @ExcelProperty(value = "sizes")
 private String sizes;

 /** 包装数量 */
 @TableField("PACKING")
 @ExcelProperty(value = "packing")
 private String packing;

 /** 版本描述 */
 @TableField("VERSION_DES")
 @ExcelProperty(value = "versionDes")
 private String versionDes;

 /** 二级类目 */
 @TableField("MATSTYLESECONDLABEL")
 @ExcelProperty(value = "matstylesecondlabel")
 private String matstylesecondlabel;

 /** 生产周期 */
 @TableField("MATPROCYCLE")
 @ExcelProperty(value = "matprocycle")
 private Integer matprocycle;

 /** 适用机型 */
 @TableField("TYPE")
 @ExcelProperty(value = "type")
 private String type;

 /** 采购起订量 */
 @TableField("MINPOQTY")
 @ExcelProperty(value = "minpoqty")
 private Long minpoqty;




 /** 采购起订量备注 */
 @TableField("MINPOQTY_NOTES")
 private String minpoqtyNotes;


 /** 拼单起订量 */
 @TableField("SPELL_ORDERNUM")
 private Long spellOrdernum;


 /** 拼单起订量备注 */
 @TableField("SPELL_ORDERNUM_REMARK ")
 private String spellOrdernumRemark;

 /** SPU */
 @TableField("SPU")
 @ExcelProperty(value = "spu")
 private String spu;

 /** NBDU */
 @TableField("NBDU")
 @ExcelProperty(value = "nbdu")
 private String nbdu;

 /** 节日标签 */
 @TableField("FESTIVAL_LABEL")
 @ExcelProperty(value = "festivalLabel")
 private String festivalLabel;

 /** 建议物流方式 */
 @TableField("DELIVERY_TYPE")
 @ExcelProperty(value = "deliveryType")
 private String deliveryType;

 /** 备货用生产周期 */
 @TableField("SUPPLYCLE")
 @ExcelProperty(value = "supplycle")
 private BigDecimal supplycle;

 /** 国内可用库存 */
 @TableField("CANUSEQTY")
 @ExcelProperty(value = "canuseqty")
 private BigDecimal canuseqty;

 /** 采购未完成数量 */
 @TableField("UNPURCHASE")
 @ExcelProperty(value = "unpurchase")
 private BigDecimal unpurchase;

 /** 未拣货数量 */
 @TableField("NOPICK")
 @ExcelProperty(value = "nopick")
 private BigDecimal nopick;

 /** 已拣货未打包数量 */
 @TableField("UNPACK")
 @ExcelProperty(value = "unpack")
 private BigDecimal unpack;

 /** 已打包未发货数量 */
 @TableField("UNSEND")
 @ExcelProperty(value = "unsend")
 private BigDecimal unsend;

 /** 国内待发数量 */
 @TableField("INTERNAL_STAY_DELIVER_QTY")
 @ExcelProperty(value = "internalStayDeliverQty")
 private BigDecimal internalStayDeliverQty;

 /** Amazon可售数量 */
 @TableField("AMAZON_AVAILABLE_QTY")
 @ExcelProperty(value = "amazonAvailableQty")
 private BigDecimal amazonAvailableQty;

 /** Amazon预留数量 */
 @TableField("AMAZON_RESERVED_QTY")
 @ExcelProperty(value = "amazonReservedQty")
 private BigDecimal amazonReservedQty;

 /** Amazon物流待发数量 */
 @TableField("AMAZON_STAY_DELIVER_QTY")
 @ExcelProperty(value = "amazonStayDeliverQty")
 private BigDecimal amazonStayDeliverQty;

 /** 空运数量/Amazon发货数量 */
 @TableField("AIR_QTY")
 @ExcelProperty(value = "airQty")
 private BigDecimal airQty;

 /** 海运数量 */
 @TableField("SHIPPING_QTY")
 @ExcelProperty(value = "shippingQty")
 private BigDecimal shippingQty;

 /** 铁运数量 */
 @TableField("TRAIN_QTY")
 @ExcelProperty(value = "trainQty")
 private BigDecimal trainQty;

 /** 卡运数量 */
 @TableField("CAR_QTY")
 @ExcelProperty(value = "carQty")
 private BigDecimal carQty;

 /** 在库库存数量=可售数量+预留数量 */
 @TableField("IN_STOCK_QTY")
 @ExcelProperty(value = "inStockQty")
 private BigDecimal inStockQty;

 /** 店铺即时库存总数=AZ在库库存+物流待发数+空运来货数量+海运来货数量+铁运来货数量+卡航来货数量 */
 @TableField("STORE_ONTIME_TOTAL_QTY")
 @ExcelProperty(value = "storeOntimeTotalQty")
 private BigDecimal storeOntimeTotalQty;

 /** 海外仓在途数量 */
 @TableField("DELIVERY_NUM_OVERSEA")
 @ExcelProperty(value = "deliveryNumOversea")
 private BigDecimal deliveryNumOversea;

 /** 海外仓库存数量 */
 @TableField("INVENTORY_NUM_OVERSEA")
 @ExcelProperty(value = "inventoryNumOversea")
 private BigDecimal inventoryNumOversea;

 /** 海外仓总库存数量=海外仓在途数量+海外仓在库数量 */
 @TableField("TOTAL_INVENTORY_NUM_OVERSEA")
 @ExcelProperty(value = "totalInventoryNumOversea")
 private BigDecimal totalInventoryNumOversea;

 /** 超180天库龄数量=180-270天库龄数量+270-365天库龄数量+365天以上库龄数量 */
 @TableField("OVER_D180_INV_AGE_QTY")
 @ExcelProperty(value = "overD180InvAgeQty")
 private BigDecimal overD180InvAgeQty;

 /** OVER_D180_INV_AGE_QTY_RATE */
 @TableField("OVER_D180_INV_AGE_QTY_RATE")
 @ExcelProperty(value = "overD180InvAgeQtyRate")
 private BigDecimal overD180InvAgeQtyRate;

 /** 头程在途预计到货时间:从国内仓到fba仓的头程在途最小到货日期 */
 @TableField("FIRST_ROUTE_INSTOCK_DATE")
 @ExcelProperty(value = "firstRouteInstockDate")
 @DateTimeFormat
 private Date firstRouteInstockDate;

 /** 头程在途到货数量:从国内仓到fba仓的头程在途最小到货日期那天的所有在途数量和 */
 @TableField("FIRST_ROUTE_DELIVERY_QTY")
 @ExcelProperty(value = "firstRouteDeliveryQty")
 private BigDecimal firstRouteDeliveryQty;

 /** 7天销量 */
 @TableField("DAY7QTY")
 @ExcelProperty(value = "day7qty")
 private BigDecimal day7qty;

 /** 14天销量 */
 @TableField("DAY14QTY")
 @ExcelProperty(value = "day14qty")
 private BigDecimal day14qty;

 /** 30天销量 */
 @TableField("DAY30QTY")
 @ExcelProperty(value = "day30qty")
 private BigDecimal day30qty;

 /** 今年上月销量,MC界面不展示，计划部审批界面需要用 */
 @TableField("CUR_PRE_MONTH_QTY")
 @ExcelProperty(value = "curPreMonthQty")
 private BigDecimal curPreMonthQty;

 /** 去年上月销量,MC界面不展示，计划部审批界面需要用 */
 @TableField("LAST_PRE_MONTH_QTY")
 @ExcelProperty(value = "lastPreMonthQty")
 private BigDecimal lastPreMonthQty;

 /** 今年上月/去年上月:去年本月销量/去年上月销量 */
 @TableField("CUR_PRE_MONTH_LAST_PRE_MONTH")
 @ExcelProperty(value = "curPreMonthLastPreMonth")
 private BigDecimal curPreMonthLastPreMonth;

 /** --【本月-3】月销量=（本月-3）月对应维度的月销售数量和， */
 @TableField("CUR_MONTH_MINUS3_QTY")
 @ExcelProperty(value = "curMonthMinus3Qty")
 private BigDecimal curMonthMinus3Qty;

 /** --【本月-2】月销量=（本月-2）对应维度的月销售数量和 */
 @TableField("CUR_MONTH_MINUS2_QTY")
 @ExcelProperty(value = "curMonthMinus2Qty")
 private BigDecimal curMonthMinus2Qty;

 /** --【本月-1】月销量=（本月-1）对应维度的月销售数量和 */
 @TableField("CUR_MONTH_MINUS1_QTY")
 @ExcelProperty(value = "curMonthMinus1Qty")
 private BigDecimal curMonthMinus1Qty;

 /** 【本月】销量=本月1号到（推荐日期-2）这段日期范围销售数量和+（本月天数-推荐日期+2）*日均销量*本月季节系数，默认本月季节系数为1 */
 @TableField("CUR_MONTH_QTY")
 @ExcelProperty(value = "curMonthQty")
 private BigDecimal curMonthQty;

 /** 【本月+1】销量=(本月+2)月份的天数*日均销量*A1 */
 @TableField("CUR_MONTH_ADD1_QTY")
 @ExcelProperty(value = "curMonthAdd1Qty")
 private BigDecimal curMonthAdd1Qty;

 /** 【本月+2】销量=(本月+2)月份的天数*日均销量*A1*A2 */
 @TableField("CUR_MONTH_ADD2_QTY")
 @ExcelProperty(value = "curMonthAdd2Qty")
 private BigDecimal curMonthAdd2Qty;

 /** 【本月+3】销量=(本月+2)月份的天数*日均销量*A1*A2*A3 */
 @TableField("CUR_MONTH_ADD3_QTY")
 @ExcelProperty(value = "curMonthAdd3Qty")
 private BigDecimal curMonthAdd3Qty;

 /** 【本月+4】销量=(本月+2)月份的天数*日均销量*A1*A2*A3*A4 */
 @TableField("CUR_MONTH_ADD4_QTY")
 @ExcelProperty(value = "curMonthAdd4Qty")
 private BigDecimal curMonthAdd4Qty;

 /** 【本月+5】销量=(本月+2)月份的天数*日均销量*A1*A2*A3*A4*A5 */
 @TableField("CUR_MONTH_ADD5_QTY")
 @ExcelProperty(value = "curMonthAdd5Qty")
 private BigDecimal curMonthAdd5Qty;

 /** 【本月+6】销量=(本月+2)月份的天数*日均销量*A1*A2*A3*A4*A5*A6 */
 @TableField("CUR_MONTH_ADD6_QTY")
 @ExcelProperty(value = "curMonthAdd6Qty")
 private BigDecimal curMonthAdd6Qty;

 /** 产品销售等级标签 */
 @TableField("PRODUCT_SALE_LEVEL")
 @ExcelProperty(value = "productSaleLevel")
 private String productSaleLevel;

 /** 产品销售等级标签-team维度 */
 @ExcelProperty(value = "productSaleLevelTeam")
 @TableField("PRODUCT_SALE_LEVEL_TEAM")
 private String productSaleLevelTeam;

 /** 日均销量 */
 @TableField("DAYAVGQTY")
 @ExcelProperty(value = "dayavgqty")
 private BigDecimal dayavgqty;

 /** Amazon店铺库存供货天数 */
 @TableField("SHOP_SUPPLYDAYS")
 @ExcelProperty(value = "shopSupplydays")
 private BigDecimal shopSupplydays;

 /** 建议备货数量 */
 @TableField("RECOM_PRE_QTY")
 @ExcelProperty(value = "recomPreQty")
 private BigDecimal recomPreQty;

 /** 季节标签 */
 @TableField("SEASON_LABEL")
 @ExcelProperty(value = "seasonLabel")
 private String seasonLabel;

 /** VERSION */
 @TableField("VERSION")
 @ExcelProperty(value = "version")
 private String version;

 /** FLAG */
 @TableField("FLAG")
 @ExcelProperty(value = "flag")
 private BigDecimal flag;

 /** 可售库存供货天数 */
 @TableField("SELLABLE_SUPPLYDAYS")
 @ExcelProperty(value = "sellableSupplydays")
 private BigDecimal sellableSupplydays;

 /** 类目 */
 @TableField("CATEGORY")
 @ExcelProperty(value = "category")
 private String category;



 /** FBA最早接收日期 */
 @TableField("FBA_FIRST_RECEIVEDATE")
 @ExcelProperty(value = "fbaFirstReceivedate")
 @DateTimeFormat
 private Date fbaFirstReceivedate;



 /** 安全销售天数 */
 @TableField("SAFE_SALEDAYS")
 @ExcelProperty(value = "safeSaledays")
 private BigDecimal safeSaledays;

 /** 订货天数 */
 @TableField("ORDER_DAYS")
 @ExcelProperty(value = "orderDays")
 private BigDecimal orderDays;

 /** 运输天数 */
 @TableField("DELIVERY_DAYS")
 @ExcelProperty(value = "deliveryDays")
 private BigDecimal deliveryDays;

 /** 订单处理天数 */
 @TableField("ORDER_DEAL_DAY")
 @ExcelProperty(value = "orderDealDay")
 private BigDecimal orderDealDay;

 /** 海外仓处理天数 */
 @TableField("OUTER_DEAL_DAY")
 @ExcelProperty(value = "outerDealDay")
 private BigDecimal outerDealDay;

 /** 国内仓处理天数 */
 @TableField("INNER_DEAL_DAY")
 @ExcelProperty(value = "innerDealDay")
 private BigDecimal innerDealDay;

 /** 总备货天数 */
 @TableField("TOTAL_BACK_DAYS")
 @ExcelProperty(value = "totalBackDays")
 private String totalBackDays;

 /** 日均销量计算公式 */
 @TableField("FORMULA")
 @ExcelProperty(value = "formula")
 private String formula;

 /** 日均销量计算明细 */
 @TableField("FORMULA_NUM")
 @ExcelProperty(value = "formulaNum")
 private String formulaNum;

 /** 总备货天数 */
 @TableField("PREPARE_DAYS")
 @ExcelProperty(value = "prepareDays")
 private BigDecimal prepareDays;

 /** 推荐备货数量公式 */
 @TableField("FORMULA_PREQTY")
 @ExcelProperty(value = "formulaPreqty")
 private String formulaPreqty;

 /** TURNOVER_DAYS */
 @TableField("TURNOVER_DAYS")
 @ExcelProperty(value = "turnoverDays")
 private BigDecimal turnoverDays;

 /** 申请审核中数量 */
 @TableField("APPROVE_QTY")
 @ExcelProperty(value = "approveQty")
 private BigDecimal approveQty;

 /** 推荐备货覆盖销售日期=推荐日期+总备货天数 */
 @TableField("RECOM_BACK_OVER_DAYS")
 @ExcelProperty(value = "recomBackOverDays")
 @DateTimeFormat
 private String recomBackOverDays;

 /** 预估销售数量 */
 @TableField("PRE_SALE_QTY")
 @ExcelProperty(value = "preSaleQty")
 private BigDecimal preSaleQty;


 /** 推荐区域备货数量 */
 @TableField("PRE_SALE_QTY")
 @ExcelProperty(value = "preSaleQty")
 private BigDecimal stockAreQty;


 /** 期望交期 */
 @TableField("EXPECTED_DELIVERY_DATE")
 @ExcelProperty(value = "expectedDeliveryDate")
 @DateTimeFormat
 private Date expectedDeliveryDate;

 /** AZ海外总库存 */
 @TableField("TOTAL_VOLUME")
 @ExcelProperty(value = "totalVolume")
 private BigDecimal totalVolume;

 /** 运营期望交期 */
 @TableField("OPER_EXPECTED_DATE")
 @ExcelProperty(value = "operExpectedDate")
 @DateTimeFormat
 private Date operExpectedDate;


 //扩充字段
// {.curPreMonthQty}/{.lastPreMonthQty}  今年上月/去年上月

 @TableField("TOTAL_VOLUME")
 @ExcelProperty(value = "curPreDivideLastPreMonth")
 private BigDecimal curPreDivideLastPreMonth;

 @TableField("NOTE")
 @ExcelProperty(value = "note")
 private String note;


 @TableField("rownum")
 @ExcelProperty(value = "rownum")
 private Long rownum;
}