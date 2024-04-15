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

/**
* <p>
    * 特殊备货申请信息历史记录
    * </p>
*
* @author lsy
* @since 2022-06-24
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("SPECIAL_APPLY_INFO_HISTORY")
public class SpecialApplyInfoHistoryResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 申请数据ID--申请数据id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 备货状态--标记此推荐记录是否已进行备货申请操作，值域{"0:待申请,1:已提交,2:已申请"}默认值：待申请 */
    @ApiModelProperty("STOCK_STATUS")
    private BigDecimal stockStatus;

    /** 申请人员工编号--默认值：当前操作者员工编号 */
    @ApiModelProperty("APPLY_PERSON_NO")
    private String applyPersonNo;

    /** 申请人姓名--默认值：当前操作者姓名 */
    @ApiModelProperty("APPLY_PERSON_NAME")
    private String applyPersonName;

    /** 申请日期--默认值：getdate */
    @ApiModelProperty("APPLY_DATE")
    private Date applyDate;

    /** 销售需求备货天数1--销售需求备货天数1 */
    @ApiModelProperty("SALES_STOCK_DAYS")
    private BigDecimal salesStockDays;

    /** 运营需求覆盖销售日期1--基于销售需求备货天数计算出来的可销售日期取值来源：推荐日期+销售需求备货天数1 */
    @ApiModelProperty("OPER_COVERS_SALES_DATE")
    private Date operCoversSalesDate;

    /** 销售需求1--(设：运营需求覆盖销售日期.month(-推荐日期.month(-1 为N；	取值来源：	  (【本月】销量 * (（30-推荐日期.day(/30 +	  【本月+1】销量+…+	  【本月+N】销量+	  (【本月+N+1】销量*运营需求覆盖销售日期.day(/30 */
    @ApiModelProperty("SALES_DEMAND")
    private BigDecimal salesDemand;

    /** 申请区域备货数量1--取值来源：销售需求-全流程总库存 */
    @ApiModelProperty("STOCK_QTY_AREA")
    private BigDecimal stockQtyArea;

    /** 申请区域备货后周转天数1--取值来源：销售需求/日均销量D6 */
    @ApiModelProperty("TURNOVER_DAYS_AREA")
    private BigDecimal turnoverDaysArea;

    /** 运营期望交期 */
    @ApiModelProperty("OPER_EXPECTED_DATE")
    private Date operExpectedDate;

    /** 需求运输方式 */
    @ApiModelProperty("DEMAND_DELIVERY_TYPE")
    private String demandDeliveryType;

    /** 申请备货原因1--申请备货原因1 */
    @ApiModelProperty("STOCK_REASON")
    private String stockReason;

    /** 是否超时不备货--是否超时不备货，根据系统配置，初始化该值 */
    @ApiModelProperty("OVER_TIME_NOT")
    private BigDecimal overTimeNot;

    /** 备货类型--备货类型:正常备货：0，特殊紧急备货：1 */
    @ApiModelProperty("BILL_TYPE")
    private String billType;

    /** 提交方式--值域{"自动"/"人工"} */
    @ApiModelProperty("COMIT_TYPE")
    private String comitType;

    /** 当前维度下的其他申请是否都提交--当前维度下的其他申请是否都提交 ： 0 未提交或部分提交，1：全部提交 */
    @ApiModelProperty("ALL_COMIT")
    private BigDecimal allComit;

    /** TEAM审核记录编号--如已申请，记录本推荐记录对应的Team审核记录编号当运营人员申请提交时，反写 */
    @ApiModelProperty("TEAM_VERIF_NO")
    private String teamVerifNo;

    /** 事业部审核记录编号--如已申请，记录本推荐记录对应的事业部审核记录编号当Team审核提交时，反写 */
    @ApiModelProperty("DEPT_TEAM_VERIF_NO")
    private String deptTeamVerifNo;

    /** 推荐日期--取值来源：每日备货推荐.推荐日期 */
    @ApiModelProperty("BIZDATE")
    private Date bizdate;

    /** 区域-备货区域，备货区域和物理仓关联，目前合并的备货区域只有EU和UX，EU的不同站点间可以互相发货；	墨西哥MX没有可用的物理仓，从US发货，所以就将US和MX合并为北美UX */
    @ApiModelProperty("AREA")
    private String area;

    /** 平台--取值来源：每日备货推荐.平台 */
    @ApiModelProperty("PLATFORM")
    private String platform;

    /** ASIN--取值来源：每日备货推荐.物料编码 */
    @ApiModelProperty("ASIN")
    private String asin;

    /** 事业部--取值来源：每日备货推荐.事业部 */
    @ApiModelProperty("DEPARTMENT")
    private String department;

    /** Team--取值来源：每日备货推荐.Team */
    @ApiModelProperty("TEAM")
    private String team;

    /** 物料编码--取值来源：每日备货推荐.物料编码 */
    @ApiModelProperty("MATERIAL_CODE")
    private String materialCode;

    /** 物料创建日期--取值来源：每日备货推荐.物料创建日期 */
    @ApiModelProperty("CREATE_DATE")
    private Date createDate;

    /** 推荐运输方式 */
    @ApiModelProperty("RECOM_DELIVERY_TYPE")
    private String recomDeliveryType;

    /** 运营大类--取值来源：每日备货推荐.运营大类 */
    @ApiModelProperty("PRODUCT_TYPE")
    private String productType;

    /** 产品名称--取值来源：每日备货推荐.产品名称 */
    @ApiModelProperty("PRODUCT_NAME")
    private String productName;

    /** 款式--取值来源：每日备货推荐.款式 */
    @ApiModelProperty("STYLE")
    private String style;

    /** 主材料--取值来源：每日备货推荐.主材料 */
    @ApiModelProperty("MAIN_MATERIAL")
    private String mainMaterial;

    /** 图案--取值来源：每日备货推荐.图案 */
    @ApiModelProperty("PATTERN")
    private String pattern;

    /** 公司品牌--取值来源：每日备货推荐.公司品牌 */
    @ApiModelProperty("COMPANY_BRAND")
    private String companyBrand;

    /** 适用品牌或对象--取值来源：每日备货推荐.适用品牌或对象 */
    @ApiModelProperty("BRAND")
    private String brand;

    /** 型号--取值来源：每日备货推荐.型号 */
    @ApiModelProperty("MODEL")
    private String model;

    /** 颜色--取值来源：每日备货推荐.颜色 */
    @ApiModelProperty("COLOR")
    private String color;

    /** 尺码--取值来源：每日备货推荐.尺码 */
    @ApiModelProperty("SIZES")
    private String sizes;

    /** 包装数量--取值来源：每日备货推荐.包装数量 */
    @ApiModelProperty("PACKING")
    private String packing;

    /** 版本描述--取值来源：每日备货推荐.版本描述 */
    @ApiModelProperty("VERSION_DES")
    private String versionDes;

    /** 二级类目--取值来源：每日备货推荐.二级类目 */
    @ApiModelProperty("MATSTYLESECONDLABEL")
    private String matstylesecondlabel;

    /** 适用机型--取值来源：每日备货推荐.适用机型 */
    @ApiModelProperty("TYPE")
    private String type;

    /** MOQ--取值来源：每日备货推荐.MOQ */
    @ApiModelProperty("MINPOQTY")
    private Long minpoqty;

    /** 节日标签--取值来源：每日备货推荐.节日标签 */
    @ApiModelProperty("FESTIVAL_LABEL")
    private String festivalLabel;

    /** 建议物流方式 */
    @ApiModelProperty("DELIVERY_TYPE")
    private String deliveryType;

    /** 备货用生产周期D6--取值来源：每日备货推荐.备货用生产周期D6 */
    @ApiModelProperty("SUPPLYCLE")
    private Integer supplycle;

    /** 国内可用库存--取值来源：每日备货推荐.国内可用库存 */
    @ApiModelProperty("CANUSEQTY")
    private BigDecimal canuseqty;

    /** 采购未完成数量--取值来源：每日备货推荐.采购未完成数量 */
    @ApiModelProperty("UNPURCHASE")
    private BigDecimal unpurchase;

    /** 国内待发货数量D6--取值来源：每日备货推荐.国内待发货数量D6 */
    @ApiModelProperty("INTERNAL_STAY_DELIVER_QTY")
    private BigDecimal internalStayDeliverQty;

    /** Az可售数量D6--取值来源：每日备货推荐.Az可售数量D6 */
    @ApiModelProperty("AMAZON_AVAILABLE_QTY")
    private BigDecimal amazonAvailableQty;

    /** Az预留数量D6--取值来源：每日备货推荐.Az预留数量D6 */
    @ApiModelProperty("AMAZON_RESERVED_QTY")
    private BigDecimal amazonReservedQty;

    /** Az物流待发数量D6--取值来源：每日备货推荐.Az物流待发数D6 */
    @ApiModelProperty("AMAZON_STAY_DELIVER_QTY")
    private BigDecimal amazonStayDeliverQty;

    /** Az空运来货数量D6--取值来源：每日备货推荐.Az空运来货数量D6 */
    @ApiModelProperty("AIR_QTY")
    private BigDecimal airQty;

    /** Az海运来货数量D6--取值来源：每日备货推荐.Az海运来货数量D6 */
    @ApiModelProperty("SHIPPING_QTY")
    private BigDecimal shippingQty;

    /** Az铁运来货数量D6--取值来源：每日备货推荐.Az铁运来货数量D6 */
    @ApiModelProperty("TRAIN_QTY")
    private BigDecimal trainQty;

    /** Az卡航来货数量D6--取值来源：每日备货推荐.Az卡航来货数量D6 */
    @ApiModelProperty("CAR_QTY")
    private BigDecimal carQty;

    /** Az在库库存D6--取值来源：每日备货推荐.Az在库库存D6 */
    @ApiModelProperty("IN_STOCK_QTY")
    private BigDecimal inStockQty;

    /** AZ店铺即时库存总数D6--取值来源：每日备货推荐.AZ店铺即时库存总数D6 */
    @ApiModelProperty("STORE_ONTIME_TOTAL_QTY")
    private BigDecimal storeOntimeTotalQty;

    /** 海外仓在途数量D6--取值来源：每日备货推荐.海外仓在途数量D6 */
    @ApiModelProperty("DELIVERY_NUM_OVERSEA")
    private BigDecimal deliveryNumOversea;

    /** 海外仓库存数量D6--取值来源：每日备货推荐.海外仓库存数量D6 */
    @ApiModelProperty("INVENTORY_NUM_OVERSEA")
    private BigDecimal inventoryNumOversea;

    /** 海外仓总库存D6--取值来源：每日备货推荐.海外仓总库存D6 */
    @ApiModelProperty("TOTAL_INVENTORY_NUM_OVERSEA")
    private BigDecimal totalInventoryNumOversea;

    /** AZ超180天库龄数量D6--取值来源：每日备货推荐.AZ超180天库龄数量D6 */
    @ApiModelProperty("OVER_D180_INV_AGE_QTY")
    private BigDecimal overD180InvAgeQty;

    /** AZ超180天库龄数量占比D6--取值来源：每日备货推荐.AZ超180天库龄数量占比D6 */
    @ApiModelProperty("OVER_D180_INV_AGE_QTY_RATE")
    private BigDecimal overD180InvAgeQtyRate;

    /** 头程在途预计到货时间D6--取值来源：每日备货推荐.头程在途预计到货时间D6 */
    @ApiModelProperty("FIRST_ROUTE_INSTOCK_DATE")
    private Date firstRouteInstockDate;

    /** 头程在途到货数量D6--取值来源：每日备货推荐.头程在途到货数量D6 */
    @ApiModelProperty("FIRST_ROUTE_DELIVERY_QTY")
    private BigDecimal firstRouteDeliveryQty;

    /** Az7天销售数量D6--取值来源：每日备货推荐.Az7天销售数量D6 */
    @ApiModelProperty("DAY7QTY")
    private BigDecimal day7qty;

    /** Az14天销售数量D6--取值来源：每日备货推荐.Az14天销售数量D6 */
    @ApiModelProperty("DAY14QTY")
    private BigDecimal day14qty;

    /** Az30天销售数量D6--取值来源：每日备货推荐.Az30天销售数量D6 */
    @ApiModelProperty("DAY30QTY")
    private BigDecimal day30qty;

    /** 今年上月合计D6--取值来源：每日备货推荐.今年上月合计D6 */
    @ApiModelProperty("CUR_PRE_MONTH_QTY")
    private BigDecimal curPreMonthQty;

    /** 去年上月合计D6--取值来源：每日备货推荐.去年上月合计D6 */
    @ApiModelProperty("LAST_PRE_MONTH_QTY")
    private BigDecimal lastPreMonthQty;

    /** 今年上月/去年上月D6--取值来源：每日备货推荐.今年上月/去年上月D6 */
    @ApiModelProperty("CUR_PRE_MONTH_LAST_PRE_MONTH")
    private BigDecimal curPreMonthLastPreMonth;

    /** 【本月-3】月销量D6--取值来源：每日备货推荐.【本月-3】月销量D6 */
    @ApiModelProperty("CUR_MONTH_MINUS3_QTY")
    private BigDecimal curMonthMinus3Qty;

    /** 【本月-2】月销量D6--取值来源：每日备货推荐.【本月-2】月销量D6 */
    @ApiModelProperty("CUR_MONTH_MINUS2_QTY")
    private BigDecimal curMonthMinus2Qty;

    /** 【本月-1】月销量D6--取值来源：每日备货推荐.【本月-1】月销量D6 */
    @ApiModelProperty("CUR_MONTH_MINUS1_QTY")
    private BigDecimal curMonthMinus1Qty;

    /** 【本月】销量D6--取值来源：每日备货推荐.【本月】销量D6 */
    @ApiModelProperty("CUR_MONTH_QTY")
    private BigDecimal curMonthQty;

    /** 【本月+1】销量D6--取值来源：每日备货推荐.【本月+1】销量D6 */
    @ApiModelProperty("CUR_MONTH_ADD1_QTY")
    private BigDecimal curMonthAdd1Qty;

    /** 【本月+2】销量D6--取值来源：每日备货推荐.【本月+2】销量D6 */
    @ApiModelProperty("CUR_MONTH_ADD2_QTY")
    private BigDecimal curMonthAdd2Qty;

    /** 【本月+3】销量D6--取值来源：每日备货推荐.【本月+3】销量D6 */
    @ApiModelProperty("CUR_MONTH_ADD3_QTY")
    private BigDecimal curMonthAdd3Qty;

    /** 【本月+4】销量D6--取值来源：每日备货推荐.【本月+4】销量D6 */
    @ApiModelProperty("CUR_MONTH_ADD4_QTY")
    private BigDecimal curMonthAdd4Qty;

    /** 【本月+5】销量D6--取值来源：每日备货推荐.【本月+5】销量D6 */
    @ApiModelProperty("CUR_MONTH_ADD5_QTY")
    private BigDecimal curMonthAdd5Qty;

    /** 【本月+6】销量D6--取值来源：每日备货推荐.【本月+6】销量D6 */
    @ApiModelProperty("CUR_MONTH_ADD6_QTY")
    private BigDecimal curMonthAdd6Qty;

    /** 销售等级标签D6--取值来源：每日备货推荐..销售等级标签D6 */
    @ApiModelProperty("PRODUCT_SALE_LEVEL")
    private String productSaleLevel;

   /**
    * 产品销售等级标签-team维度
    */
   @ApiModelProperty("productSaleLevelTeam")
   private String productSaleLevelTeam;

    /** 日均销量D6--取值来源：每日备货推荐.日均销量D6 */
    @ApiModelProperty("DAYAVGQTY")
    private BigDecimal dayavgqty;

    /** 推荐运输方式D6--取值来源：每日备货推荐.推荐运输方式D6 */
    @ApiModelProperty("RECOM_PRE_QTY")
    private BigDecimal recomPreQty;

    /** 季节标签--取值来源：每日备货推荐.季节标签 */
    @ApiModelProperty("SEASON_LABEL")
    private String seasonLabel;

    /** 总备货天数D6--取值来源：每日备货推荐.总备货天数D6 */
    @ApiModelProperty("TOTAL_BACK_DAYS")
    private BigDecimal totalBackDays;

    /** AZ海外总库存供货天数D6--取值来源：每日备货推荐.AZ海外总库存供货天数D6 */
    @ApiModelProperty("PREPARE_DAYS")
    private BigDecimal prepareDays;

    /** 申请审核中数量--取值来源：每日备货推荐.申请审核中数量 */
    @ApiModelProperty("APPROVE_QTY")
    private BigDecimal approveQty;

    /** 预估销售数量D6--取值来源：每日备货推荐.预估销售数量D6 */
    @ApiModelProperty("PRE_SALE_QTY")
    private BigDecimal preSaleQty;

    /** 期望交期 */
    @ApiModelProperty("EXPECTED_DELIVERY_DATE")
    private Date expectedDeliveryDate;

    /** AZ海外总库存 */
    @ApiModelProperty("TOTAL_VOLUME")
    private BigDecimal totalVolume;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;


    @ApiModelProperty("SAFE_SALEDAYS")
    private BigDecimal safeSaledays;

    /** 订货天数 */
    @ApiModelProperty("ORDER_DAYS")
    private BigDecimal orderDays;

    /** 运输天数 */
    @ApiModelProperty("DELIVERY_DAYS")
    private BigDecimal deliveryDays;

    /** 订单处理天数 */
    @ApiModelProperty("ORDER_DEAL_DAY")
    private BigDecimal orderDealDay;

    /** 海外仓处理天数 */
    @ApiModelProperty("OUTER_DEAL_DAY")
    private BigDecimal outerDealDay;

    /** 国内仓处理天数 */
    @ApiModelProperty("INNER_DEAL_DAY")
    private BigDecimal innerDealDay;

    /** 采购起订量备注 */
    @ApiModelProperty("MINPOQTY_NOTES")
    private String minpoqtyNotes;

 /** 拼单起订量 */
 @ApiModelProperty("SPELL_ORDERNUM")
 private Long spellOrdernum;


 /** 拼单起订量备注 */
 @ApiModelProperty("SPELL_ORDERNUM_REMARK ")
 private String spellOrdernumRemark;

}