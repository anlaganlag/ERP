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
    * 每日备货推荐表-BI
    * </p>
*
* @author cyt
* @since 2022-08-19
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("BACK_PREPARE_RECOM_BI")
@ExcelIgnoreUnannotated
public class BackPrepareRecomBi implements Serializable {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

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

    /** 二级类目 */
    @TableField("MATSTYLESECONDLABEL")
    private String matstylesecondlabel;

    /** 生产周期 */
    @TableField("MATPROCYCLE")
    private Integer matprocycle;

    /** 建议物流方式 */
    @TableField("DELIVERY_TYPE")
    private String deliveryType;

    /** 国内待发数量 */
    @TableField("INTERNAL_STAY_DELIVER_QTY")
    private BigDecimal internalStayDeliverQty;

    /** 在库库存数量=可售数量+预留数量 */
    @TableField("IN_STOCK_QTY")
    private BigDecimal inStockQty;

    /** 店铺即时库存总数=AZ在库库存+物流待发数+空运来货数量+海运来货数量+铁运来货数量+卡航来货数量 */
    @TableField("STORE_ONTIME_TOTAL_QTY")
    private BigDecimal storeOntimeTotalQty;

    /** 海外仓总库存数量=海外仓在途数量+海外仓在库数量 */
    @TableField("TOTAL_INVENTORY_NUM_OVERSEA")
    private BigDecimal totalInventoryNumOversea;

    /** 超180天库龄数量=180-270天库龄数量+270-365天库龄数量+365天以上库龄数量 */
    @TableField("OVER_D180_INV_AGE_QTY")
    private BigDecimal overD180InvAgeQty;

    /** 超180天库龄数量占比=AZ超180天库龄数量/可售数量*100%，百分号后四舍五入保留两位小数，示例：0.89% */
    @TableField("OVER_D180_INV_AGE_QTY_RATE")
    private BigDecimal overD180InvAgeQtyRate;

    /** 头程在途预计到货时间:从国内仓到FBA仓的头程在途最小到货日期 */
    @TableField("FIRST_ROUTE_INSTOCK_DATE")
    private Date firstRouteInstockDate;

    /** 头程在途到货数量:从国内仓到FBA仓的头程在途最小到货日期那天的所有在途数量和 */
    @TableField("FIRST_ROUTE_DELIVERY_QTY")
    private BigDecimal firstRouteDeliveryQty;

    /** 今年上月销量,MC界面不展示，计划部审批界面需要用 */
    @TableField("CUR_PRE_MONTH_QTY")
    private BigDecimal curPreMonthQty;

    /** 去年上月销量,MC界面不展示，计划部审批界面需要用 */
    @TableField("LAST_PRE_MONTH_QTY")
    private BigDecimal lastPreMonthQty;

    /** 今年上月销量/去年上月销量 */
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

    /** 总备货天数 */
    @TableField("TOTAL_BACK_DAYS")
    private BigDecimal totalBackDays;

    /** 推荐备货覆盖销售日期=推荐日期+总备货天数 */
    @TableField("RECOM_BACK_OVER_DAYS")
    private Date recomBackOverDays;

    /** 预估销售数量 */
    @TableField("PRE_SALE_QTY")
    private BigDecimal preSaleQty;

    /** 生命周期 */
    @TableField("LIFECYCLE")
    private String lifecycle;

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

    /** 采购起订量 */
    @TableField("MINPOQTY")
    private Long minpoqty;

    /** 拼单起订量 */
    @TableField("SPELL_ORDERNUM")
    private Long spellOrdernum;


   /** 拼单起订量备注 */
   @TableField("SPELL_ORDERNUM_REMARK ")
   private String spellOrdernumRemark;

    /** SPU */
    @TableField("SPU")
    private String spu;

    /** NBDU */
    @TableField("NBDU")
    private String nbdu;

    /** 节日标签 */
    @TableField("FESTIVAL_LABEL")
    private String festivalLabel;

    /** 备货用生产周期 */
    @TableField("SUPPLYCLE")
    private Integer supplycle;

    /** 季节系数 */
    @TableField("SEASON_RADIO")
    private BigDecimal seasonRadio;

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

    /** AZ海外总库存数量=国内待发数量+AZ店铺即时库存总数+海外仓总库存数量 */
    @TableField("TOTAL_VOLUME")
    private BigDecimal totalVolume;

    /** AMAZON可售数量 */
    @TableField("AMAZON_AVAILABLE_QTY")
    private BigDecimal amazonAvailableQty;

    /** AMAZON预留数量 */
    @TableField("AMAZON_RESERVED_QTY")
    private BigDecimal amazonReservedQty;

    /** AMAZON物流待发数量 */
    @TableField("AMAZON_STAY_DELIVER_QTY")
    private BigDecimal amazonStayDeliverQty;

    /** 空运在途数量 */
    @TableField("AIR_QTY")
    private BigDecimal airQty;

    /** 海运在途数量 */
    @TableField("SHIPPING_QTY")
    private BigDecimal shippingQty;

    /** 铁运在途数量 */
    @TableField("TRAIN_QTY")
    private BigDecimal trainQty;

    /** 卡运在途数量 */
    @TableField("CAR_QTY")
    private BigDecimal carQty;

    /** 海外仓在途数量 */
    @TableField("DELIVERY_NUM_OVERSEA")
    private BigDecimal deliveryNumOversea;

    /** 海外仓库存数量 */
    @TableField("INVENTORY_NUM_OVERSEA")
    private BigDecimal inventoryNumOversea;

    /** 7天销量 */
    @TableField("DAY7QTY")
    private BigDecimal day7qty;

    /** 14天销量 */
    @TableField("DAY14QTY")
    private BigDecimal day14qty;

    /** 30天销量 */
    @TableField("DAY30QTY")
    private BigDecimal day30qty;

    /** 日均销量 */
    @TableField("DAYAVGQTY")
    private BigDecimal dayavgqty;

    /** 建议备货数量 */
    @TableField("RECOM_PRE_QTY")
    private BigDecimal recomPreQty;

    /** 季节标签 */
    @TableField("SEASON_LABEL")
    private String seasonLabel;

    /** 采购起订量备注 */
    @TableField("MINPOQTY_NOTES")
    private String minpoqtyNotes;






    /** FBA最早接收日期 */
    @TableField("FBA_FIRST_RECEIVEDATE")
    private Date fbaFirstReceivedate;

    /** 备注 */
    @TableField("NOTE")
    private String note;

    /** 销量A */
    @TableField("A")
    private BigDecimal a;

    /** 销量B */
    @TableField("B")
    private BigDecimal b;

    /** 销量C */
    @TableField("C")
    private BigDecimal c;

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

    /** 日均销量计算公式 */
    @TableField("FORMULA")
    private String formula;

    /** 日均销量计算明细 */
    @TableField("FORMULA_NUM")
    private String formulaNum;

    /** 海外总库存供货天数=AZ海外总库存*总备货天数/预估销售数量 */
    @TableField("PREPARE_DAYS")
    private BigDecimal prepareDays;

    /** 推荐备货数量公式 */
    @TableField("FORMULA_PREQTY")
    private String formulaPreqty;

    /** 申请审核中数量 */
    @TableField("APPROVE_QTY")
    private BigDecimal approveQty;

    /** 期望交期=推荐日期+备货用生产周期 */
    @TableField("EXPECTED_DELIVERY_DATE")
    private Date expectedDeliveryDate;

    @TableField("DAY60QTY")
    private BigDecimal day60qty;

    /** ASIN的状态 */
    @TableField("ASINSTATU")
    private String asinstatu;

    @TableField("VERSION")
    private String version;

}