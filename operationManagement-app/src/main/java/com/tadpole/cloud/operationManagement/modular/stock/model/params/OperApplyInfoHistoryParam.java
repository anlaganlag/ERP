package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
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
 * 备货申请信息历史记录
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
@TableName("STOCK_OPER_APPLY_INFO_HISTORY")
public class OperApplyInfoHistoryParam implements Serializable, BaseValidatingParam {

 private static final long serialVersionUID = 1L;


 /** 申请数据id */
 @TableId(value = "ID", type = IdType.AUTO)
 private BigDecimal id;

 /** 标记此推荐记录是否已进行备货申请操作，值域{"0:待申请,1:已提交,2:已申请"}默认值：待申请 */
 @ApiModelProperty("STOCK_STATUS")
 private Integer stockStatus;

 /** 默认值：当前操作者员工编号 */
 @ApiModelProperty("APPLY_PERSON_NO")
 private String applyPersonNo;

 /** 默认值：当前操作者姓名 */
 @ApiModelProperty("APPLY_PERSON_NAME")
 private String applyPersonName;

 /** 默认值：getdate */
 @ApiModelProperty("APPLY_DATE")
 private Date applyDate;

 /** 默认值：推荐的【本月】销量D6；运营申请填写的字段 */
 @ApiModelProperty("OPER_CUR_MONTH_QTY")
 private BigDecimal operCurMonthQty;

 /** 默认值：推荐的【本月+1】销量D6；运营申请填写的字段 */
 @ApiModelProperty("OPER_CUR_MONTH_ADD1_QTY")
 private BigDecimal operCurMonthAdd1Qty;

 /** 默认值：推荐的【本月+2】销量D6；运营申请填写的字段 */
 @ApiModelProperty("OPER_CUR_MONTH_ADD2_QTY")
 private BigDecimal operCurMonthAdd2Qty;

 /** 默认值：推荐的【本月+3】销量D6；运营申请填写的字段 */
 @ApiModelProperty("OPER_CUR_MONTH_ADD3_QTY")
 private BigDecimal operCurMonthAdd3Qty;

 /** 默认值：推荐的【本月+4】销量D6；运营申请填写的字段 */
 @ApiModelProperty("OPER_CUR_MONTH_ADD4_QTY")
 private BigDecimal operCurMonthAdd4Qty;

 /** 默认值：推荐的【本月+5】销量D6；运营申请填写的字段 */
 @ApiModelProperty("OPER_CUR_MONTH_ADD5_QTY")
 private BigDecimal operCurMonthAdd5Qty;

 /** 默认值：推荐的【本月+6】销量D6；运营申请填写的字段 */
 @ApiModelProperty("OPER_CUR_MONTH_ADD6_QTY")
 private BigDecimal operCurMonthAdd6Qty;

 /** 销售需求备货天数1 */
 @ApiModelProperty("SALES_STOCK_DAYS")
 private BigDecimal salesStockDays;

 /** 基于销售需求备货天数计算出来的可销售日期取值来源：推荐日期+销售需求备货天数1 */
 @ApiModelProperty("OPER_COVERS_SALES_DATE")
 private Date operCoversSalesDate;

 /** 额外备货天数->实时计算：销售需求备货天数1-总备货天数D6 */
 @ApiModelProperty("EXTRA_DAYS")
 private BigDecimal extraDays;

 /** 销售需求：(设：运营需求覆盖销售日期.month(-推荐日期.month(-1 为N；
  取值来源：
  (【本月】销量 * (（30-推荐日期.day(/30 +
  【本月+1】销量+…+
  【本月+N】销量+
  (【本月+N+1】销量*运营需求覆盖销售日期.day(/30 */
 @ApiModelProperty("SALES_DEMAND")
 private BigDecimal salesDemand;

 /**申请区域备货数量： 取值来源：销售需求-全流程总库存 * */
 @ApiModelProperty("STOCK_QTY_AREA")
 private BigDecimal stockQtyArea;

 /**申请区域备货后周转天数： 取值来源：销售需求/日均销量D6 * */
 @ApiModelProperty("TURNOVER_DAYS_AREA")
 private BigDecimal turnoverDaysArea;

 /** 申请备货原因1 */
 @ApiModelProperty("STOCK_REASON")
 private String stockReason;

 /** 是否超时不备货，根据系统配置，初始化该值 */
 @ApiModelProperty("OVER_TIME_NOT")
 private BigDecimal overTimeNot;

 /** 备货类型--备货类型:RCBH日常备货,JJBH紧急备货,XMBH项目备货,XPBH新品备货 */
 @ApiModelProperty("BILL_TYPE")
 private String billType;

 /** 值域{"自动"/"人工"} */
 @ApiModelProperty("COMIT_TYPE")
 private String comitType;

 /** 当前维度下的其他申请是否都提交 ： 0 未提交或部分提交，1：全部提交 */
 @ApiModelProperty("ALL_COMIT")
 private BigDecimal allComit;

 /** 如已申请，记录本推荐记录对应的Team审核记录编号当运营人员申请提交时，反写 */
 @ApiModelProperty("TEAM_VERIF_NO")
 private String teamVerifNo;

 /** 如已申请，记录本推荐记录对应的事业部审核记录编号当Team审核提交时，反写 */
 @ApiModelProperty("DEPT_TEAM_VERIF_NO")
 private String deptTeamVerifNo;

 /** 推荐日期-按天，最新数据为当前日期 */
 @ApiModelProperty("BIZDATE")
 private Date bizdate;

 /** 区域-备货区域，备货区域和物理仓关联，目前合并的备货区域只有EU和UX，EU的不同站点间可以互相发货；
  墨西哥MX没有可用的物理仓，从US发货，所以就将US和MX合并为北美UX */
 @ApiModelProperty("AREA")
 private String area;

 /** 平台 */
 @ApiModelProperty("PLATFORM")
 private String platform;

 /** ASIN */
 @ApiModelProperty("ASIN")
 private String asin;

 /** 事业部 */
 @ApiModelProperty("DEPARTMENT")
 private String department;

 /** TEAM */
 @ApiModelProperty("TEAM")
 private String team;

 /** 物料编码 */
 @ApiModelProperty("MATERIAL_CODE")
 private String materialCode;

 /** 物料创建日期 */
 @ApiModelProperty("CREATE_DATE")
 private Date createDate;

 /** 推荐运输方式 */
 @ApiModelProperty("RECOM_DELIVERY_TYPE")
 private String recomDeliveryType;

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

 /** 颜色 */
 @ApiModelProperty("COLOR")
 private String color;

 /** 尺码 */
 @ApiModelProperty("SIZES")
 private String sizes;

 /** 包装数量 */
 @ApiModelProperty("PACKING")
 private String packing;

 /** 版本描述 */
 @ApiModelProperty("VERSION_DES")
 private String versionDes;

 /** 二级类目 */
 @ApiModelProperty("MATSTYLESECONDLABEL")
 private String matstylesecondlabel;

 /** 生产周期 */
 @ApiModelProperty("MATPROCYCLE")
 private Integer matprocycle;

 /** 适用机型 */
 @ApiModelProperty("TYPE")
 private String type;

 /** 采购起订量 */
 @ApiModelProperty("MINPOQTY")
 private Long minpoqty;

 /** SPU */
 @ApiModelProperty("SPU")
 private String spu;

 /** NBDU */
 @ApiModelProperty("NBDU")
 private String nbdu;

 /** 节日标签 */
 @ApiModelProperty("FESTIVAL_LABEL")
 private String festivalLabel;

 /** 建议物流方式 */
 @ApiModelProperty("DELIVERY_TYPE")
 private String deliveryType;

 /** 备货用生产周期 */
 @ApiModelProperty("SUPPLYCLE")
 private Integer supplycle;

 /** 国内可用库存 */
 @ApiModelProperty("CANUSEQTY")
 private BigDecimal canuseqty;

 /** 采购未完成数量 */
 @ApiModelProperty("UNPURCHASE")
 private BigDecimal unpurchase;

 /** 未拣货数量 */
 @ApiModelProperty("NOPICK")
 private BigDecimal nopick;

 /** 已拣货未打包数量 */
 @ApiModelProperty("UNPACK")
 private BigDecimal unpack;

 /** 已打包未发货数量 */
 @ApiModelProperty("UNSEND")
 private BigDecimal unsend;

 /** 国内待发数量 */
 @ApiModelProperty("INTERNAL_STAY_DELIVER_QTY")
 private BigDecimal internalStayDeliverQty;

 /** Amazon可售数量 */
 @ApiModelProperty("AMAZON_AVAILABLE_QTY")
 private BigDecimal amazonAvailableQty;

 /** Amazon预留数量 */
 @ApiModelProperty("AMAZON_RESERVED_QTY")
 private BigDecimal amazonReservedQty;

 /** Amazon物流待发数量 */
 @ApiModelProperty("AMAZON_STAY_DELIVER_QTY")
 private BigDecimal amazonStayDeliverQty;

 /** 空运数量/Amazon发货数量 */
 @ApiModelProperty("AIR_QTY")
 private BigDecimal airQty;

 /** 海运数量 */
 @ApiModelProperty("SHIPPING_QTY")
 private BigDecimal shippingQty;

 /** 铁运数量 */
 @ApiModelProperty("TRAIN_QTY")
 private BigDecimal trainQty;

 /** 卡运数量 */
 @ApiModelProperty("CAR_QTY")
 private BigDecimal carQty;

 /** 在库库存数量=可售数量+预留数量 */
 @ApiModelProperty("IN_STOCK_QTY")
 private BigDecimal inStockQty;

 /** 店铺即时库存总数=AZ在库库存+物流待发数+空运来货数量+海运来货数量+铁运来货数量+卡航来货数量 */
 @ApiModelProperty("STORE_ONTIME_TOTAL_QTY")
 private BigDecimal storeOntimeTotalQty;

 /** 海外仓在途数量 */
 @ApiModelProperty("DELIVERY_NUM_OVERSEA")
 private BigDecimal deliveryNumOversea;

 /** 海外仓库存数量 */
 @ApiModelProperty("INVENTORY_NUM_OVERSEA")
 private BigDecimal inventoryNumOversea;

 /** 海外仓总库存数量=海外仓在途数量+海外仓在库数量 */
 @ApiModelProperty("TOTAL_INVENTORY_NUM_OVERSEA")
 private BigDecimal totalInventoryNumOversea;

 /** 超180天库龄数量=180-270天库龄数量+270-365天库龄数量+365天以上库龄数量 */
 @ApiModelProperty("OVER_D180_INV_AGE_QTY")
 private BigDecimal overD180InvAgeQty;

 /** OVER_D180_INV_AGE_QTY_RATE */
 @ApiModelProperty("OVER_D180_INV_AGE_QTY_RATE")
 private BigDecimal overD180InvAgeQtyRate;

 /** 头程在途预计到货时间:从国内仓到fba仓的头程在途最小到货日期 */
 @ApiModelProperty("FIRST_ROUTE_INSTOCK_DATE")
 private Date firstRouteInstockDate;

 /** 头程在途到货数量:从国内仓到fba仓的头程在途最小到货日期那天的所有在途数量和 */
 @ApiModelProperty("FIRST_ROUTE_DELIVERY_QTY")
 private BigDecimal firstRouteDeliveryQty;

 /** 7天销量 */
 @ApiModelProperty("DAY7QTY")
 private BigDecimal day7qty;

 /** 14天销量 */
 @ApiModelProperty("DAY14QTY")
 private BigDecimal day14qty;

 /** 30天销量 */
 @ApiModelProperty("DAY30QTY")
 private BigDecimal day30qty;

 /** 今年上月销量,MC界面不展示，计划部审批界面需要用 */
 @ApiModelProperty("CUR_PRE_MONTH_QTY")
 private BigDecimal curPreMonthQty;

 /** 去年上月销量,MC界面不展示，计划部审批界面需要用 */
 @ApiModelProperty("LAST_PRE_MONTH_QTY")
 private BigDecimal lastPreMonthQty;

 /** 今年上月/去年上月:去年本月销量/去年上月销量 */
 @ApiModelProperty("CUR_PRE_MONTH_LAST_PRE_MONTH")
 private BigDecimal curPreMonthLastPreMonth;

 /** --【本月-3】月销量=（本月-3）月对应维度的月销售数量和， */
 @ApiModelProperty("CUR_MONTH_MINUS3_QTY")
 private BigDecimal curMonthMinus3Qty;

 /** --【本月-2】月销量=（本月-2）对应维度的月销售数量和 */
 @ApiModelProperty("CUR_MONTH_MINUS2_QTY")
 private BigDecimal curMonthMinus2Qty;

 /** --【本月-1】月销量=（本月-1）对应维度的月销售数量和 */
 @ApiModelProperty("CUR_MONTH_MINUS1_QTY")
 private BigDecimal curMonthMinus1Qty;

 /** 【本月】销量=本月1号到（推荐日期-2）这段日期范围销售数量和+（本月天数-推荐日期+2）*日均销量*本月季节系数，默认本月季节系数为1 */
 @ApiModelProperty("CUR_MONTH_QTY")
 private BigDecimal curMonthQty;

 /** 【本月+1】销量=(本月+2)月份的天数*日均销量*A1 */
 @ApiModelProperty("CUR_MONTH_ADD1_QTY")
 private BigDecimal curMonthAdd1Qty;

 /** 【本月+2】销量=(本月+2)月份的天数*日均销量*A1*A2 */
 @ApiModelProperty("CUR_MONTH_ADD2_QTY")
 private BigDecimal curMonthAdd2Qty;

 /** 【本月+3】销量=(本月+2)月份的天数*日均销量*A1*A2*A3 */
 @ApiModelProperty("CUR_MONTH_ADD3_QTY")
 private BigDecimal curMonthAdd3Qty;

 /** 【本月+4】销量=(本月+2)月份的天数*日均销量*A1*A2*A3*A4 */
 @ApiModelProperty("CUR_MONTH_ADD4_QTY")
 private BigDecimal curMonthAdd4Qty;

 /** 【本月+5】销量=(本月+2)月份的天数*日均销量*A1*A2*A3*A4*A5 */
 @ApiModelProperty("CUR_MONTH_ADD5_QTY")
 private BigDecimal curMonthAdd5Qty;

 /** 【本月+6】销量=(本月+2)月份的天数*日均销量*A1*A2*A3*A4*A5*A6 */
 @ApiModelProperty("CUR_MONTH_ADD6_QTY")
 private BigDecimal curMonthAdd6Qty;

 /** 产品销售等级标签 */
 @ApiModelProperty("PRODUCT_SALE_LEVEL")
 private String productSaleLevel;

 /** 日均销量 */
 @ApiModelProperty("DAYAVGQTY")
 private BigDecimal dayavgqty;

 /** Amazon店铺库存供货天数 */
 @ApiModelProperty("SHOP_SUPPLYDAYS")
 private BigDecimal shopSupplydays;

 /** 建议备货数量 */
 @ApiModelProperty("RECOM_PRE_QTY")
 private BigDecimal recomPreQty;

 /** 季节标签 */
 @ApiModelProperty("SEASON_LABEL")
 private String seasonLabel;

 /** VERSION */
 @ApiModelProperty("VERSION")
 private String version;

 /** FLAG */
 @ApiModelProperty("FLAG")
 private BigDecimal flag;

 /** 可售库存供货天数 */
 @ApiModelProperty("SELLABLE_SUPPLYDAYS")
 private BigDecimal sellableSupplydays;

 /** 类目 */
 @ApiModelProperty("CATEGORY")
 private String category;

 /** 采购起订量备注 */
 @ApiModelProperty("MINPOQTY_NOTES")
 private String minpoqtyNotes;

 /** 拼单起订量 */
 @ApiModelProperty("SPELL_ORDERNUM")
 private Long spellOrdernum;


 /** 拼单起订量备注 */
 @ApiModelProperty("SPELL_ORDERNUM_REMARK ")
 private String spellOrdernumRemark;

 /** FBA最早接收日期 */
 @ApiModelProperty("FBA_FIRST_RECEIVEDATE")
 private Date fbaFirstReceivedate;

 /** 备注 */
 @ApiModelProperty("NOTE")
 private String note;

 /** 安全销售天数 */
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

 /** 总备货天数 */
 @ApiModelProperty("TOTAL_BACK_DAYS")
 private BigDecimal totalBackDays;

 /** 日均销量计算公式 */
 @ApiModelProperty("FORMULA")
 private String formula;

 /** 日均销量计算明细 */
 @ApiModelProperty("FORMULA_NUM")
 private String formulaNum;

 /** 总备货天数 */
 @ApiModelProperty("PREPARE_DAYS")
 private BigDecimal prepareDays;

 /** 推荐备货数量公式 */
 @ApiModelProperty("FORMULA_PREQTY")
 private String formulaPreqty;

 /** TURNOVER_DAYS */
 @ApiModelProperty("TURNOVER_DAYS")
 private BigDecimal turnoverDays;

 /** 申请审核中数量 */
 @ApiModelProperty("APPROVE_QTY")
 private BigDecimal approveQty;

 /** 推荐备货覆盖销售日期=推荐日期+总备货天数 */
 @ApiModelProperty("RECOM_BACK_OVER_DAYS")
 private Date recomBackOverDays;

 /** 预估销售数量 */
 @ApiModelProperty("PRE_SALE_QTY")
 private BigDecimal preSaleQty;

 /** 创建时间 */
 @ApiModelProperty("CREATE_TIME")
 private Date createTime;

 /** 更新时间 */
 @ApiModelProperty("UPDATE_TIME")
 private Date updateTime;

 /** 期望交期 */
 @ApiModelProperty("EXPECTED_DELIVERY_DATE")
 private Date expectedDeliveryDate;

 /** AZ海外总库存 */
 @ApiModelProperty("TOTAL_VOLUME")
 private BigDecimal totalVolume;

 /** 运营期望交期 */
 @ApiModelProperty("OPER_EXPECTED_DATE")
 private Date operExpectedDate;

}