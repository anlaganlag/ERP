package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 计划部审核查询列表返回参数
 * </p>
 *
 * @author lsy
 * @since 2022-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated

public class PlanPurchaseOrders2Result implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 数据记录ID
     */
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 采购申请单类型
     */
    @ApiModelProperty("PURCHASE_ORDER_TYPE")
    @ExcelProperty("采购申请单类型")
    private BigDecimal purchaseOrderType;

    /**
     * 采购订单状态:值域{"待审核"/"不备货"/"待计划部审批"/"计划未通过"/"待PMC审批"/"PMC未通过"/"已通过"}默认值：待审核
     */
    @ApiModelProperty("ORDER_STATUS")
    @ExcelProperty("采购订单状态")
    private int orderStatus;

    /**
     * 默认值：销售需求3-AZ海外总库存D4-国内可用库存-采购未交订单
     */
    @ApiModelProperty("PURCHASE_APPLY_QTY")
    @ExcelProperty("销售需求")
    private BigDecimal purchaseApplyQty;


    /**
     * 建议采购申请数量
     */
    @ApiModelProperty("建议采购申请数量")
    @ExcelProperty("建议采购申请数量")
    private BigDecimal adviceApplyQty;

    /**
     * 到达时间
     */
    @ApiModelProperty("ARRIVAL_TIME")
    @ExcelProperty("到达时间")
    private Date arrivalTime;

    /**
     * 最近一次下单时间
     */
    @ApiModelProperty("ORDER_LAST_TIME")
    @ExcelProperty("最近一次下单时间")
    private Date orderLastTime;

    /**
     * 实时计算：销售需求3/（【本月】销量3+【本月+1】销量3+【本月+2】销量3）/90
     */
    @ApiModelProperty("TURNOVER_DAYS")
    @ExcelProperty("销售需求")
    private BigDecimal turnoverDays;

    /**
     * 采购申请说明
     */
    @ApiModelProperty("PURCHASE_REASON")
    @ExcelProperty("采购申请说明")
    private String purchaseReason;

    /**
     * 是否超时不备货，根据系统配置，初始化该值
     */
    @ApiModelProperty("OVER_TIME_NOT")
    @ExcelProperty("是否超时不备货")
    private Long overTimeNot;

    /**
     * 备货类型--备货类型:RCBH日常备货,JJBH紧急备货,XMBH项目备货,XPBH新品备货
     */
    @ApiModelProperty("BILL_TYPE")
    @ExcelProperty("备货类型")
    private String billType;

    /**
     * 备货类型名称--备货类型:RCBH日常备货,JJBH紧急备货,XMBH项目备货,XPBH新品备货
     */
    @ApiModelProperty("备货类型名称")
    private String billTypeName;


    /**
     * 推荐日期-按天，最新数据为当前日期
     */
    @ApiModelProperty("BIZDATE")
    @ExcelProperty("推荐日期")
    private Date bizdate;

    /**
     * 区域-备货区域，备货区域和物理仓关联，目前合并的备货区域只有EU和UX，EU的不同站点间可以互相发货；
     * 墨西哥MX没有可用的物理仓，从US发货，所以就将US和MX合并为北美UX
     */
    @ApiModelProperty("AREA")
    @ExcelProperty("备货区域")
    private String area;

    /**
     * 平台
     */
    @ApiModelProperty("PLATFORM")
    @ExcelProperty("平台")
    private String platform;

    /**
     * ASIN
     */
    @ApiModelProperty("ASIN")
    @ExcelProperty("ASIN")
    private String asin;

    /**
     * 事业部
     */
    @ApiModelProperty("DEPARTMENT")
    @ExcelProperty("事业部")
    private String department;

    /**
     * TEAM
     */
    @ApiModelProperty("TEAM")
    @ExcelProperty("TEAM")
    private String team;

    /**
     * 物料编码
     */
    @ApiModelProperty("MATERIAL_CODE")
    @ExcelProperty("物料编码")
    private String materialCode;

    /**
     * 物料创建日期
     */
    @ApiModelProperty("CREATE_DATE")
    @ExcelProperty("物料创建日期")
    private Date createDate;

    /**
     * 推荐运输方式
     */
    @ApiModelProperty("RECOM_DELIVERY_TYPE")
    @ExcelProperty("推荐运输方式")
    private String recomDeliveryType;

    /**
     * 运营大类
     */
    @ApiModelProperty("PRODUCT_TYPE")
    @ExcelProperty("运营大类")
    private String productType;

    /**
     * 产品名称
     */
    @ApiModelProperty("PRODUCT_NAME")
    @ExcelProperty("产品名称")
    private String productName;

    /**
     * 款式
     */
    @ApiModelProperty("STYLE")
    @ExcelProperty("款式")
    private String style;

    /**
     * 主材料
     */
    @ApiModelProperty("MAIN_MATERIAL")
    @ExcelProperty("主材料")
    private String mainMaterial;

    /**
     * 图案
     */
    @ApiModelProperty("PATTERN")
    @ExcelProperty("图案")
    private String pattern;

    /**
     * 公司品牌
     */
    @ApiModelProperty("COMPANY_BRAND")
    @ExcelProperty("公司品牌")
    private String companyBrand;

    /**
     * 适用品牌或对象
     */
    @ApiModelProperty("BRAND")
    @ExcelProperty("适用品牌或对象")
    private String brand;

    /**
     * 型号
     */
    @ApiModelProperty("MODEL")
    @ExcelProperty("型号")
    private String model;

    /**
     * 颜色
     */
    @ApiModelProperty("COLOR")
    @ExcelProperty("颜色")
    private String color;

    /**
     * 尺码
     */
    @ApiModelProperty("SIZES")
    @ExcelProperty("尺码")
    private String sizes;

    /**
     * 包装数量
     */
    @ApiModelProperty("PACKING")
    @ExcelProperty("包装数量")
    private String packing;

    /**
     * 版本描述
     */
    @ApiModelProperty("VERSION_DES")
    @ExcelProperty("版本描述")
    private String versionDes;

    /**
     * 二级类目
     */
    @ApiModelProperty("MATSTYLESECONDLABEL")
    @ExcelProperty("二级类目")
    private String matstylesecondlabel;

    /**
     * 生产周期
     */
    @ApiModelProperty("MATPROCYCLE")
    @ExcelProperty("生产周期")
    private Integer matprocycle;

    /**
     * 适用机型
     */
    @ApiModelProperty("TYPE")
    @ExcelProperty("适用机型")
    private String type;

    /** 拼单起订量 */
    @ApiModelProperty("SPELL_ORDERNUM")
    @ExcelProperty("拼单起订量")
    private Long spellOrdernum;


    /** 拼单起订量备注 */
    @ApiModelProperty("SPELL_ORDERNUM_REMARK ")
    @ExcelProperty("拼单起订量备注")
    private String spellOrdernumRemark;

    /**
     * 采购起订量
     */
    @ApiModelProperty("MINPOQTY")
    @ExcelProperty("采购起订量")
    private Long minpoqty;

    /**
     * SPU
     */
    @ApiModelProperty("SPU")
    @ExcelProperty("SPU")
    private String spu;

    /**
     * NBDU
     */
    @ApiModelProperty("NBDU")
    @ExcelProperty("NBDU")
    private String nbdu;

    /**
     * 节日标签
     */
    @ApiModelProperty("FESTIVAL_LABEL")
    @ExcelProperty("节日标签")
    private String festivalLabel;

    /**
     * 建议物流方式
     */
    @ApiModelProperty("DELIVERY_TYPE")
    @ExcelProperty("建议物流方式")
    private String deliveryType;

    /**
     * 备货用生产周期
     */
    @ApiModelProperty("SUPPLYCLE")
    @ExcelProperty("备货用生产周期")
    private Integer supplycle;

    /**
     * 国内可用库存
     */
    @ApiModelProperty("CANUSEQTY")
    @ExcelProperty("国内可用库存")
    private BigDecimal canuseqty;

    /**
     * 采购未完成数量
     */
    @ApiModelProperty("UNPURCHASE")
    @ExcelProperty("采购未完成数量")
    private BigDecimal unpurchase;

    /**
     * 未拣货数量
     */
    @ApiModelProperty("NOPICK")
    @ExcelProperty("未拣货数量")
    private BigDecimal nopick;

    /**
     * 已拣货未打包数量
     */
    @ApiModelProperty("UNPACK")
    @ExcelProperty("已拣货未打包数量")
    private BigDecimal unpack;

    /**
     * 已打包未发货数量
     */
    @ApiModelProperty("UNSEND")
    @ExcelProperty("已打包未发货数量")
    private BigDecimal unsend;

    /**
     * 国内待发数量
     */
    @ApiModelProperty("INTERNAL_STAY_DELIVER_QTY")
    @ExcelProperty("国内待发数量")
    private BigDecimal internalStayDeliverQty;

    /**
     * Amazon可售数量
     */
    @ApiModelProperty("AMAZON_AVAILABLE_QTY")
    @ExcelProperty("Amazon可售数量")
    private BigDecimal amazonAvailableQty;

    /**
     * Amazon预留数量
     */
    @ApiModelProperty("AMAZON_RESERVED_QTY")
    @ExcelProperty("Amazon预留数量")
    private BigDecimal amazonReservedQty;

    /**
     * Amazon物流待发数量
     */
    @ApiModelProperty("AMAZON_STAY_DELIVER_QTY")
    @ExcelProperty("Amazon物流待发数量")
    private BigDecimal amazonStayDeliverQty;

    /**
     * 空运数量/Amazon发货数量
     */
    @ApiModelProperty("AIR_QTY")
    @ExcelProperty("Amazon发货数量")
    private BigDecimal airQty;

    /**
     * 海运数量
     */
    @ApiModelProperty("SHIPPING_QTY")
    @ExcelProperty("海运数量")
    private BigDecimal shippingQty;

    /**
     * 铁运数量
     */
    @ApiModelProperty("TRAIN_QTY")
    @ExcelProperty("铁运数量")
    private BigDecimal trainQty;

    /**
     * 卡运数量
     */
    @ApiModelProperty("CAR_QTY")
    @ExcelProperty("卡运数量")
    private BigDecimal carQty;

    /**
     * 在库库存数量=可售数量+预留数量
     */
    @ApiModelProperty("IN_STOCK_QTY")
    @ExcelProperty("在库库存数量=可售数量+预留数量")
    private BigDecimal inStockQty;

    /**
     * 店铺即时库存总数=AZ在库库存+物流待发数+空运来货数量+海运来货数量+铁运来货数量+卡航来货数量
     */
    @ApiModelProperty("STORE_ONTIME_TOTAL_QTY")
    @ExcelProperty("铺即时库存总数=AZ在库库存+物流待发数+空运来货数量+海运来货数量+铁运来货数量+卡航来货数量")
    private BigDecimal storeOntimeTotalQty;

    /**
     * 海外仓在途数量
     */
    @ApiModelProperty("DELIVERY_NUM_OVERSEA")
    @ExcelProperty("海外仓在途数量")
    private BigDecimal deliveryNumOversea;

    /**
     * 海外仓库存数量
     */
    @ApiModelProperty("INVENTORY_NUM_OVERSEA")
    @ExcelProperty("海外仓库存数量")
    private BigDecimal inventoryNumOversea;

    /**
     * 海外仓总库存数量=海外仓在途数量+海外仓在库数量
     */
    @ApiModelProperty("TOTAL_INVENTORY_NUM_OVERSEA")
    @ExcelProperty("海外仓总库存数量=海外仓在途数量+海外仓在库数量")
    private BigDecimal totalInventoryNumOversea;

    /**
     * 超180天库龄数量=180-270天库龄数量+270-365天库龄数量+365天以上库龄数量
     */
    @ApiModelProperty("OVER_D180_INV_AGE_QTY")
    @ExcelProperty("超180天库龄数量=180-270天库龄数量+270-365天库龄数量+365天以上库龄数量")
    private BigDecimal overD180InvAgeQty;

    /**
     * OVER_D180_INV_AGE_QTY_RATE
     */
    @ApiModelProperty("OVER_D180_INV_AGE_QTY_RATE")
    @ExcelProperty("ORDER_STATUS")
    private BigDecimal overD180InvAgeQtyRate;

    /**
     * 头程在途预计到货时间:从国内仓到fba仓的头程在途最小到货日期
     */
    @ApiModelProperty("FIRST_ROUTE_INSTOCK_DATE")
    @ExcelProperty("ORDER_STATUS")
    private Date firstRouteInstockDate;

    /**
     * 头程在途到货数量:从国内仓到fba仓的头程在途最小到货日期那天的所有在途数量和
     */
    @ApiModelProperty("FIRST_ROUTE_DELIVERY_QTY")
    @ExcelProperty("头程在途到货数量:从国内仓到fba仓的头程在途最小到货日期那天的所有在途数量和")
    private BigDecimal firstRouteDeliveryQty;

    /**
     * 7天销量
     */
    @ApiModelProperty("DAY7QTY")
    @ExcelProperty("7天销量")
    private BigDecimal day7qty;

    /**
     * 14天销量
     */
    @ApiModelProperty("DAY14QTY")
    @ExcelProperty("14天销量")
    private BigDecimal day14qty;

    /**
     * 30天销量
     */
    @ApiModelProperty("DAY30QTY")
    @ExcelProperty("30天销量")
    private BigDecimal day30qty;

    /**
     * 今年上月销量,MC界面不展示，计划部审批界面需要用
     */
    @ApiModelProperty("CUR_PRE_MONTH_QTY")
    @ExcelProperty("今年上月销量,MC界面不展示，计划部审批界面需要用")
    private BigDecimal curPreMonthQty;

    /**
     * 去年上月销量,MC界面不展示，计划部审批界面需要用
     */
    @ApiModelProperty("LAST_PRE_MONTH_QTY")
    @ExcelProperty("去年上月销量,MC界面不展示，计划部审批界面需要用")
    private BigDecimal lastPreMonthQty;

    /**
     * 今年上月/去年上月:去年本月销量/去年上月销量
     */
    @ApiModelProperty("CUR_PRE_MONTH_LAST_PRE_MONTH")
    @ExcelProperty("今年上月/去年上月:去年本月销量/去年上月销量")
    private BigDecimal curPreMonthLastPreMonth;

    /**
     * --【本月-3】月销量=（本月-3）月对应维度的月销售数量和，
     */
    @ApiModelProperty("CUR_MONTH_MINUS3_QTY")
    @ExcelProperty("--【本月-3】月销量=（本月-3）月对应维度的月销售数量和，")
    private BigDecimal curMonthMinus3Qty;

    /**
     * --【本月-2】月销量=（本月-2）对应维度的月销售数量和
     */
    @ApiModelProperty("CUR_MONTH_MINUS2_QTY")
    @ExcelProperty("--【本月-2】月销量=（本月-2）对应维度的月销售数量和")
    private BigDecimal curMonthMinus2Qty;

    /**
     * --【本月-1】月销量=（本月-1）对应维度的月销售数量和
     */
    @ApiModelProperty("CUR_MONTH_MINUS1_QTY")
    @ExcelProperty("--【本月-1】月销量=（本月-1）对应维度的月销售数量和")
    private BigDecimal curMonthMinus1Qty;

    /**
     * 【本月】销量=本月1号到（推荐日期-2）这段日期范围销售数量和+（本月天数-推荐日期+2）*日均销量*本月季节系数，默认本月季节系数为1
     */
    @ApiModelProperty("CUR_MONTH_QTY")
    @ExcelProperty("【本月】销量=本月1号到（推荐日期-2）这段日期范围销售数量和+（本月天数-推荐日期+2）*日均销量*本月季节系数，默认本月季节系数为1")
    private BigDecimal curMonthQty;

    /**
     * 【本月+1】销量=(本月+2)月份的天数*日均销量*A1
     */
    @ApiModelProperty("CUR_MONTH_ADD1_QTY")
    @ExcelProperty("【本月+1】销量=(本月+2)月份的天数*日均销量*A1")
    private BigDecimal curMonthAdd1Qty;

    /**
     * 【本月+2】销量=(本月+2)月份的天数*日均销量*A1*A2
     */
    @ApiModelProperty("CUR_MONTH_ADD2_QTY")
    @ExcelProperty("【本月+2】销量=(本月+2)月份的天数*日均销量*A1*A2")
    private BigDecimal curMonthAdd2Qty;

    /**
     * 【本月+3】销量=(本月+2)月份的天数*日均销量*A1*A2*A3
     */
    @ApiModelProperty("CUR_MONTH_ADD3_QTY")
    @ExcelProperty("【本月+3】销量=(本月+2)月份的天数*日均销量*A1*A2*A3")
    private BigDecimal curMonthAdd3Qty;

    /**
     * 【本月+4】销量=(本月+2)月份的天数*日均销量*A1*A2*A3*A4
     */
    @ApiModelProperty("CUR_MONTH_ADD4_QTY")
    @ExcelProperty("【本月+4】销量=(本月+2)月份的天数*日均销量*A1*A2*A3*A4")
    private BigDecimal curMonthAdd4Qty;

    /**
     * 【本月+5】销量=(本月+2)月份的天数*日均销量*A1*A2*A3*A4*A5
     */
    @ApiModelProperty("CUR_MONTH_ADD5_QTY")
    @ExcelProperty("【本月+5】销量=(本月+2)月份的天数*日均销量*A1*A2*A3*A4*A5")
    private BigDecimal curMonthAdd5Qty;

    /**
     * 【本月+6】销量=(本月+2)月份的天数*日均销量*A1*A2*A3*A4*A5*A6
     */
    @ApiModelProperty("CUR_MONTH_ADD6_QTY")
    @ExcelProperty("【本月+6】销量=(本月+2)月份的天数*日均销量*A1*A2*A3*A4*A5*A6")
    private BigDecimal curMonthAdd6Qty;

    /**
     * 产品销售等级标签
     */
    @ApiModelProperty("PRODUCT_SALE_LEVEL")
    @ExcelProperty("产品销售等级标签")
    private String productSaleLevel;

    /**
     * 产品销售等级标签-team维度
     */
    @ApiModelProperty("productSaleLevelTeam")
    @ExcelProperty("productSaleLevelTeam")
    private String productSaleLevelTeam;

    /**
     * 日均销量
     */
    @ApiModelProperty("DAYAVGQTY")
    @ExcelProperty("日均销量")
    private BigDecimal dayavgqty;

    /**
     * Amazon店铺库存供货天数
     */
    @ApiModelProperty("SHOP_SUPPLYDAYS")
    @ExcelProperty("Amazon店铺库存供货天数")
    private BigDecimal shopSupplydays;

    /**
     * 建议备货数量
     */
    @ApiModelProperty("RECOM_PRE_QTY")
    @ExcelProperty("建议备货数量")
    private BigDecimal recomPreQty;

    /**
     * 季节标签
     */
    @ApiModelProperty("SEASON_LABEL")
    @ExcelProperty("季节标签")
    private String seasonLabel;

    /**
     * VERSION
     */
    @ApiModelProperty("VERSION")
    @ExcelProperty("VERSION")
    private String version;

    /**
     * FLAG
     */
    @ApiModelProperty("FLAG")
    @ExcelProperty("FLAG")
    private BigDecimal flag;

    /**
     * 可售库存供货天数
     */
    @ApiModelProperty("SELLABLE_SUPPLYDAYS")
    @ExcelProperty("可售库存供货天数")
    private BigDecimal sellableSupplydays;

    /**
     * 类目
     */
    @ApiModelProperty("CATEGORY")
    @ExcelProperty("类目")
    private String category;

    /**
     * 采购起订量备注
     */
    @ApiModelProperty("MINPOQTY_NOTES")
    @ExcelProperty("采购起订量备注")
    private String minpoqtyNotes;





    /**
     * FBA最早接收日期
     */
    @ApiModelProperty("FBA_FIRST_RECEIVEDATE")
    @ExcelProperty("FBA最早接收日期")
    private Date fbaFirstReceivedate;

    /**
     * 备注
     */
    @ApiModelProperty("NOTE")
    @ExcelProperty("备注")
    private String note;

    /**
     * 安全销售天数
     */
    @ApiModelProperty("SAFE_SALEDAYS")
    @ExcelProperty("安全销售天数")
    private BigDecimal safeSaledays;

    /**
     * 订货天数
     */
    @ApiModelProperty("ORDER_DAYS")
    @ExcelProperty("订货天数")
    private BigDecimal orderDays;

    /**
     * 运输天数
     */
    @ApiModelProperty("DELIVERY_DAYS")
    @ExcelProperty("运输天数")
    private BigDecimal deliveryDays;

    /**
     * 订单处理天数
     */
    @ApiModelProperty("ORDER_DEAL_DAY")
    @ExcelProperty("订单处理天数")
    private BigDecimal orderDealDay;

    /**
     * 海外仓处理天数
     */
    @ApiModelProperty("OUTER_DEAL_DAY")
    @ExcelProperty("海外仓处理天数")
    private BigDecimal outerDealDay;

    /**
     * 国内仓处理天数
     */
    @ApiModelProperty("INNER_DEAL_DAY")
    @ExcelProperty("国内仓处理天数")
    private BigDecimal innerDealDay;

    /**
     * 总备货天数
     */
    @ApiModelProperty("TOTAL_BACK_DAYS")
    @ExcelProperty("总备货天数")
    private BigDecimal totalBackDays;

    /**
     * 日均销量计算公式
     */
    @ApiModelProperty("FORMULA")
    @ExcelProperty("日均销量计算公式")
    private String formula;

    /**
     * 日均销量计算明细
     */
    @ApiModelProperty("FORMULA_NUM")
    @ExcelProperty("日均销量计算明细")
    private String formulaNum;

    /**
     * 总备货天数
     */
    @ApiModelProperty("PREPARE_DAYS")
    @ExcelProperty("总备货天数")
    private BigDecimal prepareDays;

    /**
     * 推荐备货数量公式
     */
    @ApiModelProperty("FORMULA_PREQTY")
    @ExcelProperty("推荐备货数量公式")
    private String formulaPreqty;

    /**
     * TURNOVER_DAYS
     */
    @ApiModelProperty("TURNOVER_DAYS2")
    @ExcelProperty("TURNOVER_DAYS")
    private BigDecimal turnoverDays2;

    /**
     * 申请审核中数量
     */
    @ApiModelProperty("APPROVE_QTY")
    @ExcelProperty("申请审核中数量")
    private BigDecimal approveQty;

    /**
     * 推荐备货覆盖销售日期=推荐日期+总备货天数
     */
    @ApiModelProperty("RECOM_BACK_OVER_DAYS")
    @ExcelProperty("推荐备货覆盖销售日期=推荐日期+总备货天数")
    private Date recomBackOverDays;

    /**
     * 预估销售数量
     */
    @ApiModelProperty("PRE_SALE_QTY")
    @ExcelProperty("预估销售数量")
    private BigDecimal preSaleQty;

    /**
     * 创建时间
     */
    @ApiModelProperty("CREATE_TIME")
    @ExcelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("UPDATE_TIME")
    @ExcelProperty("更新时间")
    private Date updateTime;

    /**
     * 期望交期
     */
    @ApiModelProperty("EXPECTED_DELIVERY_DATE")
    @ExcelProperty("期望交期")
    private Date expectedDeliveryDate;

    /**
     * AZ海外总库存
     */
    @ApiModelProperty("TOTAL_VOLUME")
    @ExcelProperty("AZ海外总库存")
    private BigDecimal totalVolume;

    /**
     * 运营期望交期
     */
    @ApiModelProperty("OPER_EXPECTED_DATE")
    @ExcelProperty("运营期望交期")
    private Date operExpectedDate;


    /**
     * 默认值：sum（日常备货申请（同步）.【本月】销量2）；
     */
    @ApiModelProperty("【本月】销量3默认值：sum（Team审核.【本月】销量2")
    @ExcelProperty("operCurMonthQty默认值：sum（日常备货申请（同步）.【本月】销量2）；")
    private BigDecimal operCurMonthQty;

    /**
     * 默认值：sum（日常备货申请（同步）.【本月+1】销量2）；
     */
    @ApiModelProperty("【本月+1】销量3默认值：sum（Team审核.【本月+1】销量2")
    @ExcelProperty("operCurMonthAdd1Qty默认值：sum（日常备货申请（同步）.【本月+1】销量2）；")
    private BigDecimal operCurMonthAdd1Qty;

    /**
     * 默认值：sum（日常备货申请（同步）.【本月+2】销量2）；
     */
    @ApiModelProperty("【本月+2】销量3默认值：sum（Team审核.【本月+2】销量2")
    @ExcelProperty("operCurMonthAdd2Qty默认值：sum（日常备货申请（同步）.【本月+2】销量2）；")
    private BigDecimal operCurMonthAdd2Qty;

    /**
     * 默认值：sum（日常备货申请（同步）.【本月+3】销量2）；
     */
    @ApiModelProperty("【本月+3】销量3默认值：sum（Team审核.【本月+3】销量2")
    @ExcelProperty("operCurMonthAdd3Qty默认值：sum（日常备货申请（同步）.【本月+3】销量2）；")
    private BigDecimal operCurMonthAdd3Qty;

    /**
     * 默认值：sum（日常备货申请（同步）.【本月+4】销量2）；
     */
    @ApiModelProperty("【本月+4】销量3默认值：sum（Team审核.【本月+4】销量2")
    @ExcelProperty("operCurMonthAdd4Qty默认值：sum（日常备货申请（同步）.【本月+4】销量2）；")
    private BigDecimal operCurMonthAdd4Qty;

    /**
     * 默认值：sum（日常备货申请（同步）.【本月+5】销量2）；
     */
    @ApiModelProperty("【本月+5】销量3默认值：sum（Team审核.【本月+5】销量2")
    @ExcelProperty("operCurMonthAdd5Qty默认值：sum（日常备货申请（同步）.【本月+5】销量2）；")
    private BigDecimal operCurMonthAdd5Qty;

    /**
     * 默认值：sum（日常备货申请（同步）.【本月+6】销量2）；
     */
    @ApiModelProperty("【本月+6】销量3默认值：sum（Team审核.【本月+6】销量2")
    @ExcelProperty("operCurMonthAdd6Qty默认值：sum（日常备货申请（同步）.【本月+6】销量2）；")
    private BigDecimal operCurMonthAdd6Qty;

    @ApiModelProperty("审核结果：0：未通过，1：通过")
    @ExcelProperty("审核结果")
    private String verifResult;

    @ApiModelProperty("计划审核、PMC审核填写的数量")
    @ExcelProperty("计划审核、PMC审核填写的数量")
    private BigDecimal qty;

    /**
     * 计划审核、PMC审核 填写原因
     */
    @ApiModelProperty("事业部审核原因")
    @ExcelProperty("事业部审核原因")
    private String verifReason;

    /**
     * 销售需求
     */
    @ApiModelProperty("销售需求")
    @ExcelProperty("销售需求")
    private BigDecimal salesDemand;

    /**
     * 销售需求备货天数1--销售需求备货天数1
     */
    @ApiModelProperty("销售需求备货天数")
    @ExcelProperty("销售需求备货天数1--销售需求备货天数1")
    private BigDecimal salesStockDays;

    /**
     * 事业部审核时间
     */
    @ApiModelProperty("事业部审核时间")
    @ExcelProperty("事业部审核时间")
    private Date verifDate;

    /**
     * 事业部审核时间
     */
    @ApiModelProperty("事业部审核人")
    @ExcelProperty("事业部审核人")
    private String verifPersonName;




}