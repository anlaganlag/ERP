package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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
@ExcelIgnoreUnannotated
@TableName("BACK_PREPARE_RECOM_BI")
public class BackPrepareRecomBiResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 推荐日期-按天，最新数据为当前日期 */
    @ApiModelProperty("BIZDATE")
    @ExcelProperty(value = "推荐日期")
    @DateTimeFormat("yyyy-MM-dd")
    private Date bizdate;

    /** 区域-备货区域，备货区域和物理仓关联，目前合并的备货区域只有EU和UX，EU的不同站点间可以互相发货；	墨西哥MX没有可用的物理仓，从US发货，所以就将US和MX合并为北美UX */
    @ApiModelProperty("AREA")
    @ExcelProperty(value = "区域")
    private String area;

    /** 平台 */
    @ApiModelProperty("PLATFORM")
    @ExcelProperty(value = "平台")
    private String platform;

    /** ASIN */
    @ApiModelProperty("ASIN")
    @ExcelProperty(value = "ASIN")
    private String asin;

    /** 事业部 */
    @ApiModelProperty("DEPARTMENT")
    @ExcelProperty(value = "事业部")
    private String department;

    /** TEAM */
    @ApiModelProperty("TEAM")
    @ExcelProperty(value = "TEAM")
    private String team;

    /** 物料编码 */
    @ApiModelProperty("MATERIAL_CODE")
    @ExcelProperty(value = "物料编码")
    private String materialCode;

    /** 物料创建日期 */
    @ApiModelProperty("CREATE_DATE")
    @ExcelProperty(value = "物料创建日期")
    private Date createDate;

    /** 推荐运输方式 */
    @ApiModelProperty("RECOM_DELIVERY_TYPE")
    @ExcelProperty(value = "推荐运输方式")
    private String recomDeliveryType;

    /** 二级类目 */
    @ApiModelProperty("MATSTYLESECONDLABEL")
    @ExcelProperty(value = "二级类目")
    private String matstylesecondlabel;

    /** 生产周期 */
    @ApiModelProperty("MATPROCYCLE")
    @ExcelProperty(value = "生产周期")
    private Integer matprocycle;

    /** 建议物流方式 */
    @ApiModelProperty("DELIVERY_TYPE")
    @ExcelProperty(value = "建议物流方式")
    private String deliveryType;

    /** 国内待发数量 */
    @ApiModelProperty("INTERNAL_STAY_DELIVER_QTY")
    @ExcelProperty(value = "国内待发数量")
    private BigDecimal internalStayDeliverQty;

    /** 在库库存数量=可售数量+预留数量 */
    @ApiModelProperty("IN_STOCK_QTY")
    @ExcelProperty(value = "在库库存数量")
    private BigDecimal inStockQty;

    /** 店铺即时库存总数=AZ在库库存+物流待发数+空运来货数量+海运来货数量+铁运来货数量+卡航来货数量 */
    @ApiModelProperty("STORE_ONTIME_TOTAL_QTY")
    @ExcelProperty(value = "店铺即时库存总数")
    private BigDecimal storeOntimeTotalQty;

    /** 海外仓总库存数量=海外仓在途数量+海外仓在库数量 */
    @ApiModelProperty("TOTAL_INVENTORY_NUM_OVERSEA")
    @ExcelProperty(value = "海外仓总库存数量")
    private BigDecimal totalInventoryNumOversea;

    /** 超180天库龄数量=180-270天库龄数量+270-365天库龄数量+365天以上库龄数量 */
    @ApiModelProperty("OVER_D180_INV_AGE_QTY")
    @ExcelProperty(value = "超180天库龄数量")
    private BigDecimal overD180InvAgeQty;

    /** 超180天库龄数量占比=AZ超180天库龄数量/可售数量*100%，百分号后四舍五入保留两位小数，示例：0.89% */
    @ApiModelProperty("OVER_D180_INV_AGE_QTY_RATE")
    @ExcelProperty(value = "超180天库龄数量占比")
    private BigDecimal overD180InvAgeQtyRate;

    /** 头程在途预计到货时间:从国内仓到FBA仓的头程在途最小到货日期 */
    @ApiModelProperty("FIRST_ROUTE_INSTOCK_DATE")
    @ExcelProperty(value = "头程在途预计到货时间")
    private Date firstRouteInstockDate;

    /** 头程在途到货数量:从国内仓到FBA仓的头程在途最小到货日期那天的所有在途数量和 */
    @ApiModelProperty("FIRST_ROUTE_DELIVERY_QTY")
    @ExcelProperty(value = "头程在途到货数量")
    private BigDecimal firstRouteDeliveryQty;

    /** 今年上月销量,MC界面不展示，计划部审批界面需要用 */
    @ApiModelProperty("CUR_PRE_MONTH_QTY")
    @ExcelProperty(value = "今年上月销量")
    private BigDecimal curPreMonthQty;

    /** 去年上月销量,MC界面不展示，计划部审批界面需要用 */
    @ApiModelProperty("LAST_PRE_MONTH_QTY")
    @ExcelProperty(value = "去年上月销量")
    private BigDecimal lastPreMonthQty;

    /** 今年上月销量/去年上月销量 */
    @ApiModelProperty("CUR_PRE_MONTH_LAST_PRE_MONTH")
    @ExcelProperty(value = "今年上月销量/去年上月销量")
    private BigDecimal curPreMonthLastPreMonth;


    /** --【本月-3】月销量=（本月-3）月对应维度的月销售数量和， */
    @ApiModelProperty("CUR_MONTH_MINUS3_QTY")
    @ExcelProperty(value = "【本月-3】月销量")
    private BigDecimal curMonthMinus3Qty;

    /** --【本月-2】月销量=（本月-2）对应维度的月销售数量和 */
    @ApiModelProperty("CUR_MONTH_MINUS2_QTY")
    @ExcelProperty(value = "【本月-2】月销量")
    private BigDecimal curMonthMinus2Qty;

    /** --【本月-1】月销量=（本月-1）对应维度的月销售数量和 */
    @ApiModelProperty("CUR_MONTH_MINUS1_QTY")
    @ExcelProperty(value = "【本月-1】月销量")
    private BigDecimal curMonthMinus1Qty;

    /** 【本月】销量=本月1号到（推荐日期-2）这段日期范围销售数量和+（本月天数-推荐日期+2）*日均销量*本月季节系数，默认本月季节系数为1 */
    @ApiModelProperty("CUR_MONTH_QTY")
    @ExcelProperty(value = "【本月】销量")
    private BigDecimal curMonthQty;

    /** 【本月+1】销量=(本月+2)月份的天数*日均销量*A1 */
    @ApiModelProperty("CUR_MONTH_ADD1_QTY")
    @ExcelProperty(value = "【本月+1】销量")
    private BigDecimal curMonthAdd1Qty;

    /** 【本月+2】销量=(本月+2)月份的天数*日均销量*A1*A2 */
    @ApiModelProperty("CUR_MONTH_ADD2_QTY")
    @ExcelProperty(value = "【本月+2】销量")
    private BigDecimal curMonthAdd2Qty;

    /** 【本月+3】销量=(本月+2)月份的天数*日均销量*A1*A2*A3 */
    @ApiModelProperty("CUR_MONTH_ADD3_QTY")
    @ExcelProperty(value = "【本月+3】销量")
    private BigDecimal curMonthAdd3Qty;

    /** 【本月+4】销量=(本月+2)月份的天数*日均销量*A1*A2*A3*A4 */
    @ApiModelProperty("CUR_MONTH_ADD4_QTY")
    @ExcelProperty(value = "【本月+4】销量")
    private BigDecimal curMonthAdd4Qty;

    /** 【本月+5】销量=(本月+2)月份的天数*日均销量*A1*A2*A3*A4*A5 */
    @ApiModelProperty("CUR_MONTH_ADD5_QTY")
    @ExcelProperty(value = "【本月+5】销量")
    private BigDecimal curMonthAdd5Qty;

    /** 【本月+6】销量=(本月+2)月份的天数*日均销量*A1*A2*A3*A4*A5*A6 */
    @ApiModelProperty("CUR_MONTH_ADD6_QTY")
    @ExcelProperty(value = "【本月+6】销量")
    private BigDecimal curMonthAdd6Qty;

    /** 产品销售等级标签 */
    @ApiModelProperty("PRODUCT_SALE_LEVEL")
    @ExcelProperty(value = "产品销售等级标签")
    private String productSaleLevel;

    /**
     * 产品销售等级标签-team维度
     */
    @ApiModelProperty("productSaleLevelTeam")
    @ExcelProperty("productSaleLevelTeam")
    private String productSaleLevelTeam;

    /** 总备货天数 */
    @ApiModelProperty("TOTAL_BACK_DAYS")
    @ExcelProperty(value = "总备货天数")
    private BigDecimal totalBackDays;

    /** 推荐备货覆盖销售日期=推荐日期+总备货天数 */
    @ApiModelProperty("RECOM_BACK_OVER_DAYS")
    @ExcelProperty(value = "推荐备货覆盖销售日期")
    private Date recomBackOverDays;

    /** 预估销售数量 */
    @ApiModelProperty("PRE_SALE_QTY")
    @ExcelProperty(value = "预估销售数量")
    private BigDecimal preSaleQty;

    /** 生命周期 */
    @ApiModelProperty("LIFECYCLE")
    @ExcelProperty(value = "生命周期")
    private String lifecycle;

    /** 运营大类 */
    @ApiModelProperty("PRODUCT_TYPE")
    @ExcelProperty(value = "运营大类")
    private String productType;

    /** 产品名称 */
    @ApiModelProperty("PRODUCT_NAME")
    @ExcelProperty(value = "产品名称")
    private String productName;

    /** 款式 */
    @ApiModelProperty("STYLE")
    @ExcelProperty(value = "款式")
    private String style;

    /** 主材料 */
    @ApiModelProperty("MAIN_MATERIAL")
    @ExcelProperty(value = "主材料")
    private String mainMaterial;

    /** 图案 */
    @ApiModelProperty("PATTERN")
    @ExcelProperty(value = "图案")
    private String pattern;

    /** 公司品牌 */
    @ApiModelProperty("COMPANY_BRAND")
    @ExcelProperty(value = "公司品牌")
    private String companyBrand;

    /** 适用品牌或对象 */
    @ApiModelProperty("BRAND")
    @ExcelProperty(value = "适用品牌或对象")
    private String brand;

    /** 型号 */
    @ApiModelProperty("MODEL")
    @ExcelProperty(value = "型号")
    private String model;

    /** 颜色 */
    @ApiModelProperty("COLOR")
    @ExcelProperty(value = "颜色")
    private String color;

    /** 尺码 */
    @ApiModelProperty("SIZES")
    @ExcelProperty(value = "尺码")
    private String sizes;

    /** 包装数量 */
    @ApiModelProperty("PACKING")
    @ExcelProperty(value = "包装数量")
    private String packing;

    @ApiModelProperty("VERSION")
    @ExcelProperty(value = "版本")
    private String version;

    /** 版本描述 */
    @ApiModelProperty("VERSION_DES")
    @ExcelProperty(value = "版本描述")
    private String versionDes;

    /** 适用机型 */
    @ApiModelProperty("TYPE")
    @ExcelProperty(value = "适用机型")
    private String type;


    /** 拼单起订量 */
    @TableField("SPELL_ORDERNUM")
    @ExcelProperty(value = "采购起订量")
    private Long spellOrdernum;


    /** 拼单起订量备注 */
    @TableField("SPELL_ORDERNUM_REMARK ")
    @ExcelProperty(value = "拼单起订量备注")
    private String spellOrdernumRemark;

    /** 采购起订量 */
    @ApiModelProperty("MINPOQTY")
    @ExcelProperty(value = "采购起订量")
    private Long minpoqty;



    /** SPU */
    @ApiModelProperty("SPU")
    @ExcelProperty(value = "SPU")
    private String spu;

    /** NBDU */
    @ApiModelProperty("NBDU")
    @ExcelProperty(value = "NBDU")
    private String nbdu;

    /** 节日标签 */
    @ApiModelProperty("FESTIVAL_LABEL")
    @ExcelProperty(value = "节日标签")
    private String festivalLabel;

    /** 备货用生产周期 */
    @ApiModelProperty("SUPPLYCLE")
    @ExcelProperty(value = "备货用生产周期")
    private Integer supplycle;

    /** 季节系数 */
    @ApiModelProperty("SEASON_RADIO")
    @ExcelProperty(value = "季节系数")
    private BigDecimal seasonRadio;

    /** 国内可用库存 */
    @ApiModelProperty("CANUSEQTY")
    @ExcelProperty(value = "国内可用库存")
    private BigDecimal canuseqty;

    /** 采购未完成数量 */
    @ApiModelProperty("UNPURCHASE")
    @ExcelProperty(value = "采购未完成数量")
    private BigDecimal unpurchase;

    /** 未拣货数量 */
    @ApiModelProperty("NOPICK")
    @ExcelProperty(value = "未拣货数量")
    private BigDecimal nopick;

    /** 已拣货未打包数量 */
    @ApiModelProperty("UNPACK")
    @ExcelProperty(value = "已拣货未打包数量")
    private BigDecimal unpack;

    /** 已打包未发货数量 */
    @ApiModelProperty("UNSEND")
    @ExcelProperty(value = "已打包未发货数量")
    private BigDecimal unsend;

    /** AZ海外总库存数量=国内待发数量+AZ店铺即时库存总数+海外仓总库存数量 */
    @ApiModelProperty("TOTAL_VOLUME")
    @ExcelProperty(value = "AZ海外总库存数量")
    private BigDecimal totalVolume;

    /** AMAZON可售数量 */
    @ApiModelProperty("AMAZON_AVAILABLE_QTY")
    @ExcelProperty(value = "AMAZON可售数量")
    private BigDecimal amazonAvailableQty;

    /** AMAZON预留数量 */
    @ApiModelProperty("AMAZON_RESERVED_QTY")
    @ExcelProperty(value = "AMAZON预留数量")
    private BigDecimal amazonReservedQty;

    /** AMAZON物流待发数量 */
    @ApiModelProperty("AMAZON_STAY_DELIVER_QTY")
    @ExcelProperty(value = "AMAZON物流待发数量")
    private BigDecimal amazonStayDeliverQty;

    /** 空运在途数量 */
    @ApiModelProperty("AIR_QTY")
    @ExcelProperty(value = "空运在途数量")
    private BigDecimal airQty;

    /** 海运在途数量 */
    @ApiModelProperty("SHIPPING_QTY")
    @ExcelProperty(value = "海运在途数量")
    private BigDecimal shippingQty;

    /** 铁运在途数量 */
    @ApiModelProperty("TRAIN_QTY")
    @ExcelProperty(value = "铁运在途数量")
    private BigDecimal trainQty;

    /** 卡运在途数量 */
    @ApiModelProperty("CAR_QTY")
    @ExcelProperty(value = "卡运在途数量")
    private BigDecimal carQty;

    /** 海外仓在途数量 */
    @ApiModelProperty("DELIVERY_NUM_OVERSEA")
    @ExcelProperty(value = "海外仓在途数量")
    private BigDecimal deliveryNumOversea;

    /** 海外仓库存数量 */
    @ApiModelProperty("INVENTORY_NUM_OVERSEA")
    @ExcelProperty(value = "海外仓库存数量")
    private BigDecimal inventoryNumOversea;

    /** 7天销量 */
    @ApiModelProperty("DAY7QTY")
    @ExcelProperty(value = "7天销量")
    private BigDecimal day7qty;

    /** 14天销量 */
    @ApiModelProperty("DAY14QTY")
    @ExcelProperty(value = "14天销量")
    private BigDecimal day14qty;

    /** 30天销量 */
    @ApiModelProperty("DAY30QTY")
    @ExcelProperty(value = "30天销量")
    private BigDecimal day30qty;

    /** 日均销量 */
    @ApiModelProperty("DAYAVGQTY")
    @ExcelProperty(value = "日均销量")
    private BigDecimal dayavgqty;

    /** 建议备货数量 */
    @ApiModelProperty("RECOM_PRE_QTY")
    @ExcelProperty(value = "建议备货数量")
    private BigDecimal recomPreQty;

    /** 季节标签 */
    @ApiModelProperty("SEASON_LABEL")
    @ExcelProperty(value = "季节标签")
    private String seasonLabel;

    /** 采购起订量备注 */
    @ApiModelProperty("MINPOQTY_NOTES")
    @ExcelProperty(value = "采购起订量备注")
    private String minpoqtyNotes;



    /** FBA最早接收日期 */
    @ApiModelProperty("FBA_FIRST_RECEIVEDATE")
    @ExcelProperty(value = "FBA最早接收日期")
    private Date fbaFirstReceivedate;

    /** 备注 */
    @ApiModelProperty("NOTE")
    @ExcelProperty(value = "备注")
    private String note;

    /** 销量A */
    @ApiModelProperty("A")
    @ExcelProperty(value = "销量A")
    private BigDecimal a;

    /** 销量B */
    @ApiModelProperty("B")
    @ExcelProperty(value = "销量B")
    private BigDecimal b;

    /** 销量C */
    @ApiModelProperty("C")
    @ExcelProperty(value = "销量C")
    private BigDecimal c;

    /** 安全销售天数 */
    @ApiModelProperty("SAFE_SALEDAYS")
    @ExcelProperty(value = "安全销售天数")
    private BigDecimal safeSaledays;

    /** 订货天数 */
    @ApiModelProperty("ORDER_DAYS")
    @ExcelProperty(value = "订货天数")
    private BigDecimal orderDays;

    /** 运输天数 */
    @ApiModelProperty("DELIVERY_DAYS")
    @ExcelProperty(value = "运输天数")
    private BigDecimal deliveryDays;

    /** 订单处理天数 */
    @ApiModelProperty("ORDER_DEAL_DAY")
    @ExcelProperty(value = "订单处理天数")
    private BigDecimal orderDealDay;

    /** 海外仓处理天数 */
    @ApiModelProperty("OUTER_DEAL_DAY")
    @ExcelProperty(value = "海外仓处理天数")
    private BigDecimal outerDealDay;

    /** 国内仓处理天数 */
    @ApiModelProperty("INNER_DEAL_DAY")
    @ExcelProperty(value = "国内仓处理天数")
    private BigDecimal innerDealDay;

    /** 日均销量计算公式 */
    @ApiModelProperty("FORMULA")
    @ExcelProperty(value = "日均销量计算公式")
    private String formula;

    /** 日均销量计算明细 */
    @ApiModelProperty("FORMULA_NUM")
    @ExcelProperty(value = "日均销量计算明细")
    private String formulaNum;

    /** 海外总库存供货天数=AZ海外总库存*总备货天数/预估销售数量 */
    @ApiModelProperty("PREPARE_DAYS")
    @ExcelProperty(value = "海外总库存供货天数")
    private BigDecimal prepareDays;

    /** 推荐备货数量公式 */
    @ApiModelProperty("FORMULA_PREQTY")
    @ExcelProperty(value = "推荐备货数量公式")
    private String formulaPreqty;

    /** 申请审核中数量 */
    @ApiModelProperty("APPROVE_QTY")
    @ExcelProperty(value = "申请审核中数量")
    private BigDecimal approveQty;

    /** 期望交期=推荐日期+备货用生产周期 */
    @ApiModelProperty("EXPECTED_DELIVERY_DATE")
    @ExcelProperty(value = "期望交期")
    private Date expectedDeliveryDate;

    @ApiModelProperty("DAY60QTY")
    @ExcelProperty(value = "DAY60QTY")
    private BigDecimal day60qty;

    /** ASIN的状态 */
    @ApiModelProperty("ASINSTATU")
    @ExcelProperty(value = "ASIN的状态")
    private String asinstatu;




}