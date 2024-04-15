package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
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
@ExcelIgnoreUnannotated
@TableName("STOCK_PURCHASE_ORDERS")

public class StockApplicationRecordAnalysisResult  implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** 申请单编号 */
    @ExcelProperty("申请单编号")
    @TableId(value = "ID", type = IdType.AUTO)
    private String id;


    /** 备货类型*/
    @ApiModelProperty("BILL_TYPE_Name")
    private String billTypeName;

    /** 申请单号*/
    @ApiModelProperty("purchase_apply_no")
    private String purchaseApplyNo;

    /** 申请人*/
    @ApiModelProperty("verif_person_name")
    private String verifPersonName;

    /** 申请日期 */
    @ApiModelProperty("create_time")
    private Date createTime;

    /** "采购订单状态{0:待审核；1:不备货；2:待计划部审批；3:计划未通过；4:待PMC审批；5:PMC未通过；6:已通过}默认值：待审核" */
    @ApiModelProperty("ORDER_STATUS_NAME：采购订单状态{0:待审核；1:不备货；2:待计划部审批；3:计划未通过；4:待PMC审批；5:PMC未通过；6:已通过}默认值：待审核")
    private String orderStatusName;

    /** 审批时间 */
    @ApiModelProperty("VERIF_DATE_Approval")
    private Date verifDateApproval;

    /** 事业部 */
    @ExcelProperty("事业部")
    @ApiModelProperty("事业部")
    private String department;

    /** team*/
    @ApiModelProperty("team")
    private String team;

    /** 平台*/
    @ApiModelProperty("平台")
    private String platform;

    /** 物料编码 */
    @ApiModelProperty("material_code")
    private String materialCode;

    /** 运营大类*/
    @ApiModelProperty("product_type")
    private String productType;

    /** 产品名称 */
    @ApiModelProperty("product_name")
    private String productName;

    /** 款式 */
    @ApiModelProperty("style")
    private String style;

    /** 主材料 */
    @ApiModelProperty("main_material")
    private String mainMaterial;

    /** 图案 */
    @ApiModelProperty("pattern")
    private String pattern;

    /**公司品牌 */
    @ApiModelProperty("company_brand")
    private String companyBrand;

    /** 适用品牌或对象*/
    @ApiModelProperty("brand")
    private String brand;

    /** 型号*/
    @ApiModelProperty("model")
    private String model;

    /** 颜色 */
    @ApiModelProperty("color")
    private String color;

    /** 尺码*/
    @ApiModelProperty("sizes")
    private String sizes;

    /** 包装数量 */
    @ApiModelProperty("packing")
    private String packing;

    /** 版本描述 */
    @ApiModelProperty("versionDes")
    private String versionDes;

    /** 适用机型*/
    @ApiModelProperty("type")
    private String type;

    /**二级标签*/
    @ApiModelProperty("matstylesecondlabel")
    private String matstylesecondlabel;

    /** MOQ */
//    @ApiModelProperty("MOQ:minpoqty")
//    private String minpoqty;

    /** 事业部审核说明 */
    @ApiModelProperty("VERIF_REASON_DEPT")
    private String verifReasonDept;

    /** 计划部审批数量 */
    @ApiModelProperty("QTY_APPROVAL_PLAN")
    private int qtyApprovalPlan;

    /** 计划部审批说明 */
    @ApiModelProperty("VERIF_REASON_PLAN")
    private String VerifReasonPlan;

    /** 采购申请数量 */
    @ApiModelProperty("采购申请数量PURCHASE_APPLY_QTY")
    private String purchaseApplyQty;

    /** 采购起订量 */
    @ApiModelProperty("采购起订量")
    private String minpoqty;

    /**申请备货后周转天数**/
    @ApiModelProperty("TURNOVER_DAYS")
    private BigDecimal turnoverDays;


    /** 申请备货后周转天数*/
    @ApiModelProperty("extra_days")
    private int extraDays;

    /** 运营期望交期 */
    @ApiModelProperty("运营期望交期 oper_expected_date")
    private String operExpectedDate;

    /** 区域 */
    @ApiModelProperty("区域")
    private String area;

    /**物料创建日期 */
    @ApiModelProperty("物料创建日期 create_date")
    private Date createDate;

    /** 采购起订量备注 */
    @ApiModelProperty("minpoqty_notes")
    private String minpoqtyNotes;

    /** 拼单起订量 */
    @ApiModelProperty("SPELL_ORDERNUM")
    private Long spellOrdernum;


    /** 拼单起订量备注 */
    @ApiModelProperty("SPELL_ORDERNUM_REMARK ")
    private String spellOrdernumRemark;

    /** SPU */
    @ApiModelProperty("SPU")
    private String spu;

    /** NBDU */
    @ApiModelProperty("NBDU")
    private String nbdu;

    /** 节日标签 */
    @ApiModelProperty("节日标签 festival_label")
    private String festivalLabel;

    /** 季节标签*/
    @ExcelProperty("")
    @ApiModelProperty("季节标签 season_label")
    private String seasonLabel;

    /** 生产周期 */
    @ApiModelProperty("supplycle")
    private int supplycle;

    /** 建议物流方式 */
    @ApiModelProperty("delivery_type")
    private String deliveryType;

    /** AZ海外总库存*/
    @ApiModelProperty("AZ海外总库存 total_volume")
    private int totalVolume;

    /** 国内待发货数量 */
    @ApiModelProperty("国内待发货数量 internal_stay_deliver_qty")
    private int internalStayDeliverQty;

    /** AZ店铺即时库存总数 */
    @ApiModelProperty("AZ店铺即时库存总数  store_ontime_total_qty")
    private int storeOntimeTotalQty;

    /** Az在库库存*/
    @ApiModelProperty("Az在库库存 in_stock_qty")
    private int inStockQty;

    /**Az物流待发数量 */
    @ApiModelProperty("Az物流待发数量")
    private int amazonStayDeliverQty;

    /** */
    @ApiModelProperty("airQty")
    private int airQty;

    /** */
    @ApiModelProperty("shippingQty")
    private int shippingQty;

    /** 铁运数量 */
    @ApiModelProperty("trainQty")
    private int trainQty;

    /** 卡运数量 */
    @ApiModelProperty("carQty")
    private int carQty;

    /** */
    @ApiModelProperty("totalInventoryNumOversea")
    private int totalInventoryNumOversea;

    /** */
    @ApiModelProperty("inventoryNumOversea")
    private int inventoryNumOversea;

    /** */
    @ApiModelProperty("deliveryNumOversea")
    private int deliveryNumOversea;

    /** */
    @ApiModelProperty("overD180InvAgeQtyRate")
    private BigDecimal overD180InvAgeQtyRate;

    /** */
    @ApiModelProperty("canuseqty")
    private int canuseqty;

    /** */
    @ApiModelProperty("unpurchase")
    private int unpurchase;

    /** */
    @ApiModelProperty("approveQty")
    private int approveQty;

    /** */
    @ApiModelProperty("firstRouteInstockDate")
    private Date firstRouteInstockDate;

    /** */
    @ApiModelProperty("firstRouteDeliveryQty")
    private BigDecimal firstRouteDeliveryQty;

    /** */
    @ApiModelProperty("curMonthMinus3Qty")
    private int curMonthMinus3Qty;

    /** */
    @ApiModelProperty("curMonthMinus2Qty")
    private int curMonthMinus2Qty;

    /** */
    @ApiModelProperty("curMonthMinus1Qty")
    private int curMonthMinus1Qty;

    /** */
    @ApiModelProperty("curMonthQty")
    private int curMonthQty;

    /** */
    @ApiModelProperty("curMonthAdd1Qty")
    private int curMonthAdd1Qty;

    /** */
    @ApiModelProperty("")
    private int curMonthAdd2Qty;

    /** */
    @ApiModelProperty("curMonthAdd3Qty")
    private int curMonthAdd3Qty;

    /** */
    @ApiModelProperty("curMonthAdd4Qty")
    private int curMonthAdd4Qty;

    /** */
    @ApiModelProperty("curMonthAdd5Qty")
    private int curMonthAdd5Qty;

    /** */
    @ApiModelProperty("curMonthAdd6Qty")
    private int curMonthAdd6Qty;

    /** */
    @ApiModelProperty("day7qty")
    private int day7qty;

    /** */
    @ApiModelProperty("day14qty")
    private int day14qty;

    /** */
    @ApiModelProperty("day30qty")
    private int day30qty;

    /** */
    @ApiModelProperty("productSaleLevel")
    private String productSaleLevel;

    /**
     * 产品销售等级标签-team维度
     */
    @ApiModelProperty("productSaleLevelTeam")
    private String productSaleLevelTeam;

    /** */
    @ApiModelProperty("dayavgqty")
    private BigDecimal dayavgqty;

    /** */
    @ApiModelProperty("RECOM_DELIVERY_TYPE")
    private String recomDeliveryType;

    /** */
//    @ApiModelProperty("supplycle")
//    private BigDecimal supplycle;


    /** */
    @ApiModelProperty("safeSaledays")
    private int safeSaledays;

    /** */
    @ApiModelProperty("order_days")
    private int orderDays;

    /** */
    @ApiModelProperty("delivery_days")
    private int deliveryDays;

    /** */
    @ApiModelProperty("order_deal_day")
    private int orderDealDay;

    /** */
    @ApiModelProperty("OUTER_DEAL_DAY")
    private int outerDealDay;

    /** */
    @ApiModelProperty("innerDealDay")
    private int innerDealDay;

    /** */
    @ApiModelProperty("prepareDays")
    private int prepareDays;

    /** */
    @ApiModelProperty("recomBackOverDays")
    private Date recomBackOverDays;

    /** */
    @ApiModelProperty("preSaleQty")
    private int preSaleQty;

    /** */
    @ApiModelProperty("recomPreQty")
    private int recomPreQty;

    /** */
    @ApiModelProperty("operCurMonthQty")
    private int operCurMonthQty;

    /** */
    @ApiModelProperty("operCurMonthAdd1Qty")
    private int operCurMonthAdd1Qty;

    /** */
    @ApiModelProperty("operCurMonthAdd2Qty")
    private int operCurMonthAdd2Qty;

    /** */
    @ApiModelProperty("operCurMonthAdd3Qty")
    private int operCurMonthAdd3Qty;

    /** */
    @ApiModelProperty("operCurMonthAdd4Qty")
    private int operCurMonthAdd4Qty;

    /** */
    @ApiModelProperty("operCurMonthAdd5Qty")
    private int operCurMonthAdd5Qty;

    /** */
    @ApiModelProperty("operCurMonthAdd6Qty")
    private int operCurMonthAdd6Qty;

    /** */
    @ApiModelProperty("salesStockDays")
    private int salesStockDays;

    /** */
    @ApiModelProperty("operCoversSalesDate")
    private Date operCoversSalesDate;

    /** */
//    @ExcelProperty("")
//    @ApiModelProperty("")
//    private int extraDays;

    /** */
    @ExcelProperty("")
    @ApiModelProperty("")
    private int salesDemand;

    /** */
    @ExcelProperty("")
    @ApiModelProperty("")
    private int stockQtyArea;

    /** */
    @ApiModelProperty("")
    private int turnoverDaysArea;

    /**
     * 总备货天数
     */
    private int totalBackDays;

}
