package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
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
 * 采购订单
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
@TableName("STOCK_PURCHASE_ORDERS")
public class PurchaseOrdersVerifyResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 备货类型--备货类型:RCBH日常备货,JJBH紧急备货,XMBH项目备货,XPBH新品备货
     */
    @ApiModelProperty("备货类型")
    private String billType;

    @ApiModelProperty("备货类型文本")
    @ExcelProperty("备货类型")
    private String billTypeStr;


    /**
     * 数据记录ID
     */
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ExcelProperty("申请单号")
    @ApiModelProperty("申请单号")
    private String id;

    @ExcelProperty("申请人")
    private String applyPerson;


    /**
     * 推荐日期-按天，最新数据为当前日期
     */
    @ApiModelProperty("申请日期")
    @ExcelProperty("申请日期")
    private Date bizdate;



    /**
     * 采购订单状态:值域{"待审核"/"不备货"/"待计划部审批"/"计划未通过"/"待PMC审批"/"PMC未通过"/"已通过"}默认值：待审核
     */
    @ApiModelProperty("采购订单状态")
    private int orderStatus;


    @ApiModelProperty("采购订单状态文本")
    @ExcelProperty("采购订单状态")
    private String orderStatusStr;

    @ApiModelProperty("审批时间")
    @ExcelProperty("审批时间")
    private Date verifDate;

    /**
     * 事业部
     */
    @ApiModelProperty("事业部")
    @ExcelProperty("事业部")
    private String department;

    /**
     * TEAM
     */
    @ApiModelProperty("Team")
    @ExcelProperty("Team")
    private String team;



    /**
     * 平台
     */
    @ApiModelProperty("平台")
    @ExcelProperty("平台")
    private String platform;


    /**
     * 物料编码
     */
    @ApiModelProperty("物料编码")
    @ExcelProperty("物料编码")
    private String materialCode;

    /**
     * 运营大类
     */
    @ApiModelProperty("运营大类")
    @ExcelProperty("运营大类")
    private String productType;

    /**
     * 产品名称
     */
    @ApiModelProperty("产品名称")
    @ExcelProperty("产品名称")
    private String productName;

    /**
     * 款式
     */
    @ApiModelProperty("款式")
    @ExcelProperty("款式")
    private String style;

    /**
     * 主材料
     */
    @ApiModelProperty("主材料")
    @ExcelProperty("主材料")
    private String mainMaterial;

    /**
     * 图案
     */
    @ApiModelProperty("图案")
    @ExcelProperty("图案")
    private String pattern;

    /**
     * 公司品牌
     */
    @ApiModelProperty("公司品牌")
    @ExcelProperty("公司品牌")
    private String companyBrand;

    /**
     * 适用品牌或对象
     */
    @ApiModelProperty("适用品牌或对象")
    @ExcelProperty("适用品牌或对象")
    private String brand;

    /**
     * 型号
     */
    @ApiModelProperty("型号")
    @ExcelProperty("型号")
    private String model;

    /**
     * 颜色
     */
    @ApiModelProperty("颜色")
    @ExcelProperty("颜色")
    private String color;

    /**
     * 尺码
     */
    @ApiModelProperty("尺码")
    @ExcelProperty("尺码")
    private String sizes;

    /**
     * 包装数量
     */
    @ApiModelProperty("包装数量")
    @ExcelProperty("包装数量")
    private String packing;

    /**
     * 版本描述
     */
    @ApiModelProperty("版本描述")
    @ExcelProperty("版本描述")
    private String versionDes;


    /**
     * 适用机型
     */
    @ApiModelProperty("适用机型")
    @ExcelProperty("适用机型")
    private String type;

    /**
     * 二级类目
     */
    @ApiModelProperty("二级标签")
    @ExcelProperty("二级标签")
    private String matstylesecondlabel;


    /**
     * 采购起订量
     */
    @ApiModelProperty("MOQ")
    @ExcelProperty("MOQ")
    private Long minpoqty;

    @ApiModelProperty("事业部审核说明")
    @ExcelProperty("事业部审核说明")
    private String deptVerifyReason;

    @ApiModelProperty("计划部审批数量")
    @ExcelProperty("计划部审批数量")
    private BigDecimal planVerifyQty;


    @ApiModelProperty("计划部审批说明")
    @ExcelProperty("计划部审批说明")
    private String planVerifyReason;

    /**
     * 默认值：销售需求3-AZ海外总库存D4-国内可用库存-采购未交订单
     */
    @ApiModelProperty("采购申请数量")
    @ExcelProperty("采购申请数量")
    private BigDecimal purchaseApplyQty;

    @ApiModelProperty("申请备货后周转天数")
    @ExcelProperty("申请备货后周转天数")
    private BigDecimal turnoverDays;


    /**
     * 期望交期
     */
    @ApiModelProperty("运营期望交期")
    @ExcelProperty("运营期望交期")
    private Date operExpectedDate;


    /**
     * 区域-备货区域，备货区域和物理仓关联，目前合并的备货区域只有EU和UX，EU的不同站点间可以互相发货；
     * 墨西哥MX没有可用的物理仓，从US发货，所以就将US和MX合并为北美UX
     */
    @ApiModelProperty("AREA")
    @ExcelProperty("区域")
    private String area;


    /**
     * 物料创建日期
     */
    @ApiModelProperty("CREATE_DATE")
    @ExcelProperty("物料创建日期")
    private Date createDate;


    /**
     * 采购起订量备注
     */
    @ApiModelProperty("MINPOQTY_NOTES")
    @ExcelProperty("采购起订量备注")
    private String minpoqtyNotes;

    /** 拼单起订量 */
    @TableField("SPELL_ORDERNUM")
    @ExcelProperty("拼单起订量")
    private Long spellOrdernum;


    /** 拼单起订量备注 */
    @TableField("SPELL_ORDERNUM_REMARK ")
    @ExcelProperty("拼单起订量备注")
    private String spellOrdernumRemark;


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
     * 季节标签
     */
    @ApiModelProperty("SEASON_LABEL")
    @ExcelProperty("季节标签")
    private String seasonLabel;


    /**
     * 生产周期
     */
    @ApiModelProperty("MATPROCYCLE")
    @ExcelProperty("生产周期")
    private Integer matprocycle;


    /**
     * 建议物流方式
     */
    @ApiModelProperty("DELIVERY_TYPE")
    @ExcelProperty("建议物流方式")
    private String deliveryType;


    /**
     * AZ海外总库存
     */
    @ApiModelProperty("TOTAL_VOLUME")
    @ExcelProperty("AZ海外总库存")
    private BigDecimal totalVolume;

    /**
     * 国内待发数量
     */
    @ApiModelProperty("INTERNAL_STAY_DELIVER_QTY")
    @ExcelProperty("国内待发货数量")
    private BigDecimal internalStayDeliverQty;


    /**
     * 店铺即时库存总数=AZ在库库存+物流待发数+空运来货数量+海运来货数量+铁运来货数量+卡航来货数量
     */
    @ApiModelProperty("STORE_ONTIME_TOTAL_QTY")
    @ExcelProperty("AZ店铺即时库存总数")
    private BigDecimal storeOntimeTotalQty;


    /**
     * 在库库存数量=可售数量+预留数量
     */
    @ApiModelProperty("IN_STOCK_QTY")
    @ExcelProperty("Az在库库存")
    private BigDecimal inStockQty;

    /**
     * Amazon物流待发数量
     */
    @ApiModelProperty("AMAZON_STAY_DELIVER_QTY")
    @ExcelProperty("Az物流待发数量")
    private BigDecimal amazonStayDeliverQty;


    /**
     * 空运数量/Amazon发货数量
     */
    @ApiModelProperty("AIR_QTY")
    @ExcelProperty("Az空运来货数量")
    private BigDecimal airQty;

    /**
     * 海运数量
     */
    @ApiModelProperty("SHIPPING_QTY")
    @ExcelProperty("Az海运来货数量")
    private BigDecimal shippingQty;

    /**
     * 铁运数量
     */
    @ApiModelProperty("TRAIN_QTY")
    @ExcelProperty("Az铁运来货数量")
    private BigDecimal trainQty;

    /**
     * 卡运数量
     */
    @ApiModelProperty("CAR_QTY")
    @ExcelProperty("Az卡航来货数量")
    private BigDecimal carQty;


    /**
     * 海外仓总库存数量=海外仓在途数量+海外仓在库数量
     */
    @ApiModelProperty("TOTAL_INVENTORY_NUM_OVERSEA")
    @ExcelProperty("海外仓总库存")
    private BigDecimal totalInventoryNumOversea;


    /**
     * 海外仓库存数量
     */
    @ApiModelProperty("INVENTORY_NUM_OVERSEA")
    @ExcelProperty("海外仓库存数量")
    private BigDecimal inventoryNumOversea;


    /**
     * 海外仓在途数量
     */
    @ApiModelProperty("DELIVERY_NUM_OVERSEA")
    @ExcelProperty("海外仓在途数量")
    private BigDecimal deliveryNumOversea;


    /**
     * OVER_D180_INV_AGE_QTY_RATE
     */
    @ApiModelProperty("OVER_D180_INV_AGE_QTY_RATE")
    @ExcelProperty("AZ超180天库龄数量占比")
    private BigDecimal overD180InvAgeQtyRate;


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
     * 申请审核中数量
     */
    @ApiModelProperty("APPROVE_QTY")
    @ExcelProperty("申请审核中数量")
    private BigDecimal approveQty;


    /**
     * 头程在途预计到货时间:从国内仓到fba仓的头程在途最小到货日期
     */
    @ApiModelProperty("FIRST_ROUTE_INSTOCK_DATE")
    @ExcelProperty("头程在途预计到货时间")
    private Date firstRouteInstockDate;

    /**
     * 头程在途到货数量:从国内仓到fba仓的头程在途最小到货日期那天的所有在途数量和
     */
    @ApiModelProperty("FIRST_ROUTE_DELIVERY_QTY")
    @ExcelProperty("头程在途到货数量")
    private BigDecimal firstRouteDeliveryQty;

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
     * 7天销量
     */
    @ApiModelProperty("DAY7QTY")
    @ExcelProperty("Az7天销售数量")
    private BigDecimal day7qty;

    /**
     * 14天销量
     */
    @ApiModelProperty("DAY14QTY")
    @ExcelProperty("Az14天销售数量")
    private BigDecimal day14qty;

    /**
     * 30天销量
     */
    @ApiModelProperty("DAY30QTY")
    @ExcelProperty("Az30天销售数量")
    private BigDecimal day30qty;

    /**
     * 产品销售等级标签
     */
    @ApiModelProperty("PRODUCT_SALE_LEVEL")
    @ExcelProperty("销售等级标签")
    private String productSaleLevel;

    /**
     * 产品销售等级标签-team维度
     */
    @ApiModelProperty("productSaleLevelTeam")
    @ExcelProperty("产品销售等级标签-team维度")
    private String productSaleLevelTeam;

    /**
     * 日均销量
     */
    @ApiModelProperty("DAYAVGQTY")
    @ExcelProperty("日均销量")
    private BigDecimal dayavgqty;


    /**
     * 推荐运输方式
     */
    @ApiModelProperty("RECOM_DELIVERY_TYPE")
    @ExcelProperty("推荐运输方式")
    private String recomDeliveryType;

    /**
     * 总备货天数
     */
    @ApiModelProperty("TOTAL_BACK_DAYS")
    @ExcelProperty("总备货天数")
    private BigDecimal totalBackDays;


    /**
     * 备货用生产周期
     */
    @ApiModelProperty("SUPPLYCLE")
    @ExcelProperty("备货用生产周期")
    private Integer supplycle;


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
    @ApiModelProperty("PREPARE_DAYS")
    @ExcelProperty("AZ海外总库存供货天数")
    private BigDecimal prepareDays;



    /**
     * 推荐备货覆盖销售日期=推荐日期+总备货天数
     */
    @ApiModelProperty("RECOM_BACK_OVER_DAYS")
    @ExcelProperty("推荐备货覆盖销售日期")
    private Date recomBackOverDays;

    /**
     * 预估销售数量
     */
    @ApiModelProperty("PRE_SALE_QTY")
    @ExcelProperty("预估销售数量")
    private BigDecimal preSaleQty;

    /**
     * 建议备货数量
     */
    @ApiModelProperty("RECOM_PRE_QTY")
    @ExcelProperty("建议备货数量")
    private BigDecimal recomPreQty;


    /**
     * 未拣货数量
     */
    @ApiModelProperty("NOPICK")
    private BigDecimal nopick;

    /**
     * 已拣货未打包数量
     */
    @ApiModelProperty("UNPACK")
    private BigDecimal unpack;

    /**
     * 已打包未发货数量
     */
    @ApiModelProperty("UNSEND")
    private BigDecimal unsend;



    /**
     * Amazon可售数量
     */
    @ApiModelProperty("AMAZON_AVAILABLE_QTY")
    private BigDecimal amazonAvailableQty;

    /**
     * Amazon预留数量
     */
    @ApiModelProperty("AMAZON_RESERVED_QTY")
    private BigDecimal amazonReservedQty;


    /**
     * 超180天库龄数量=180-270天库龄数量+270-365天库龄数量+365天以上库龄数量
     */
    @ApiModelProperty("OVER_D180_INV_AGE_QTY")
    private BigDecimal overD180InvAgeQty;

    /**
     * 默认值：当前操作者员工编号
     */
    @ApiModelProperty("VERIF_PERSON_NO")
    private String verifPersonNo;

    /**
     * 默认值：当前操作者姓名
     */
    @ApiModelProperty("VERIF_PERSON_NAME")
    private String verifPersonName;


    /**
     * 值域{"自动"/"人工"}
     */
    @ApiModelProperty("TEAM_VERIF_TYPE")
    private String teamVerifType;

    /**
     * 标记此推荐记录是否已进行Team审核操作，值域{"待审核"/"已审核"}默认值：待审核
     */
    @ApiModelProperty("VERIF_STATUS")
    private String verifStatus;

    /**
     * 是否超时不备货，根据系统配置，初始化该值
     */
    @ApiModelProperty("OVER_TIME_NOT")
    private BigDecimal overTimeNot;

    /**
     * 当前维度下的其他申请是否都提交 ： 0 未提交或部分提交，1：全部提交
     */
    @ApiModelProperty("ALL_COMIT")
    private BigDecimal allComit;

    /**
     * 如已审核，记录本审核记录对应的采购申请单记录编号当生成采购订单记录时，反写
     */
    @ApiModelProperty("PURCHASE_APPLY_NO")
    private String purchaseApplyNo;

    /**
     * ASIN
     */
    @ApiModelProperty("ASIN")
    private String asin;


    /**
     * 今年上月销量,MC界面不展示，计划部审批界面需要用
     */
    @ApiModelProperty("CUR_PRE_MONTH_QTY")
    private BigDecimal curPreMonthQty;

    /**
     * 去年上月销量,MC界面不展示，计划部审批界面需要用
     */
    @ApiModelProperty("LAST_PRE_MONTH_QTY")
    private BigDecimal lastPreMonthQty;

    /**
     * 今年上月/去年上月:去年本月销量/去年上月销量
     */
    @ApiModelProperty("CUR_PRE_MONTH_LAST_PRE_MONTH")
    private BigDecimal curPreMonthLastPreMonth;


    /**
     * Amazon店铺库存供货天数
     */
    @ApiModelProperty("SHOP_SUPPLYDAYS")
    private BigDecimal shopSupplydays;


    /**
     * VERSION
     */
    @ApiModelProperty("VERSION")
    private String version;

    /**
     * FLAG
     */
    @ApiModelProperty("FLAG")
    private BigDecimal flag;

    /**
     * 可售库存供货天数
     */
    @ApiModelProperty("SELLABLE_SUPPLYDAYS")
    private BigDecimal sellableSupplydays;

    /**
     * 类目
     */
    @ApiModelProperty("CATEGORY")
    private String category;


    /**
     * FBA最早接收日期
     */
    @ApiModelProperty("FBA_FIRST_RECEIVEDATE")
    private Date fbaFirstReceivedate;

    /**
     * 备注
     */
    @ApiModelProperty("NOTE")
    private String note;


    /**
     * 日均销量计算公式
     */
    @ApiModelProperty("FORMULA")
    private String formula;

    /**
     * 日均销量计算明细
     */
    @ApiModelProperty("FORMULA_NUM")
    private String formulaNum;

    /**
     * 推荐备货数量公式
     */
    @ApiModelProperty("FORMULA_PREQTY")
    private String formulaPreqty;


    /**
     * 创建时间
     */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

    /**
     * 期望交期
     */
    @ApiModelProperty("EXPECTED_DELIVERY_DATE")
    private Date expectedDeliveryDate;


    /**
     * 建议申请数量.该字段 在5合4维度时临时存值
     */
    @ApiModelProperty("ADVICE_APPLY_QTY")
    private BigDecimal adviceApplyQty;


    /**
     * 默认值：sum（日常备货申请（同步）.【本月】销量1）；
     */
    @TableField("OPER_CUR_MONTH_QTY")
    @ExcelProperty("【本月】销量1）；")
    private BigDecimal operCurMonthQty;

    /**
     * 【本月+1】销量1）
     */
    @TableField("OPER_CUR_MONTH_ADD1_QTY")
    @ExcelProperty("【本月+1】销量1）")
    private BigDecimal operCurMonthAdd1Qty;

    /**
     * 【本月+2】销量1）
     */
    @TableField("OPER_CUR_MONTH_ADD2_QTY")
    @ExcelProperty("【本月+2】销量1）")
    private BigDecimal operCurMonthAdd2Qty;

    /**
     * 【本月+3】销量1）
     */
    @TableField("OPER_CUR_MONTH_ADD3_QTY")
    @ExcelProperty("【本月+3】销量1）")
    private BigDecimal operCurMonthAdd3Qty;

    /**
     * 【本月+4】销量1）
     */
    @TableField("OPER_CUR_MONTH_ADD4_QTY")
    @ExcelProperty("【本月+4】销量1）")
    private BigDecimal operCurMonthAdd4Qty;

    /**
     * 【本月+5】销量1）
     */
    @TableField("OPER_CUR_MONTH_ADD5_QTY")
    @ExcelProperty("【本月+5】销量1）")
    private BigDecimal operCurMonthAdd5Qty;

    /**
     * 【本月+6】销量1）
     */
    @TableField("OPER_CUR_MONTH_ADD6_QTY")
    @ExcelProperty("【本月+6】销量1）")
    private BigDecimal operCurMonthAdd6Qty;



    /**
     * 销售需求备货天数1
     */
    @TableField("SALES_STOCK_DAYS")
    @ExcelProperty("销售需求备货天数")
    private BigDecimal salesStockDays;

    /**
     * 销售需求覆盖销售日期
     */
    @ApiModelProperty("OPER_COVERS_SALES_DATE")
    @ExcelProperty("销售需求覆盖销售日期")
    private Date operCoversSalesDate;


    /**
     * 额外备货天数->实时计算：销售需求备货天数1-总备货天数D6
     */
    @TableField("EXTRA_DAYS")
    @ExcelProperty("额外备货天数->实时计算：销售需求备货天数1-总备货天数D6")
    private BigDecimal extraDays;

    /**
     * (设：运营需求覆盖销售日期.month(-推荐日期.month(-1 为N；
     * 取值来源：
     * (【本月】销量2 * (（30-推荐日期.day(/30 +
     * 【本月+1】销量2+…+
     * 【本月+N】销量2+
     * (【本月+N+1】销量2*运营需求覆盖销售日期.day(/30
     */
    @ApiModelProperty("SALES_DEMAND")
    @ExcelProperty("销售需求")
    private BigDecimal salesDemand;


    /**
     * 取值来源：销售需求2-AZ海外总库存D5
     */
    @ApiModelProperty("STOCK_QTY_AREA")
    @ExcelProperty("申请区域备货数量")
    private BigDecimal stockQtyArea;


    /**
     * 实时计算：销售需求/（【本月】销量2+【本月+1】销量2+【本月+2】销量2）/90
     */
    @ApiModelProperty("TURNOVER_DAYS_AREA")
    @ExcelProperty("申请区域备货后周转天数")
    private BigDecimal turnoverDaysArea;

    /**
     * 申请备货原因2
     */
    @ApiModelProperty("STOCK_REASON")
//    @ExcelProperty("申请备货原因")
    private String stockReason;








}