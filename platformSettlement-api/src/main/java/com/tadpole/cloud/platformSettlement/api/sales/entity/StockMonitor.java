package com.tadpole.cloud.platformSettlement.api.sales.entity;

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
    * 备货监控
    * </p>
*
* @author cyt
* @since 2022-07-21
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("STOCK_MONITOR")
@ExcelIgnoreUnannotated
public class StockMonitor implements Serializable {

    private static final long serialVersionUID = 1L;


    /** id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 平台 */
    @TableField("PLATFORM_NAME")
    private String platformName;

    /** 事业部 */
    @TableField("DEPARTMENT")
    private String department;

    /** Team */
    @TableField("TEAM")
    private String team;

    /** 运营大类 */
    @TableField("PRODUCT_TYPE")
    private String productType;

    /** 年度销售目标yearl sales target */
    @TableField("YEAR_TARGET")
    private BigDecimal yearTarget;

    /** 季度销售目标quarter_targe */
    @TableField("QUARTER_TARGE")
    private BigDecimal quarterTarge;

    /** 年度采买预算year_purchase_budget */
    @TableField("YEAR_PURCHASE_BUDGET")
    private BigDecimal yearPurchaseBudget;

    /** 年度已使用采买预算year_used_purchase_budget */
    @TableField("YEAR_USED_PUR_BUDGET")
    private BigDecimal yearUsedPurBudget;

    /** 本季度采买预算cur_quarter_pur_budget */
    @TableField("CUR_QUARTER_PUR_BUDGET")
    private BigDecimal curQuarterPurBudget;

    /** 本季度已使用采买预算cur_used_quarter_pur_budget */
    @TableField("CUR_USED_QUARTER_PUR_BUDGET")
    private BigDecimal curUsedQuarterPurBudget;

    /** 月度库存周转目标Mon_turn_target */
    @TableField("MON_TURN_TARGET")
    private BigDecimal monTurnTarget;

    /** 年度已售数量year_sale_qty */
    @TableField("YEAR_SALE_QTY")
    private BigDecimal yearSaleQty;

    /** 季度已售数量quarter_sale_qty */
    @TableField("QUARTER_SALE_QTY")
    private BigDecimal quarterSaleQty;

    /** 年度销售目标达成度year_sale_target_per */
    @TableField("YEAR_SALE_TARGET_PER")
    private BigDecimal yearSaleTargetPer;

    /** 本季度销售目标达成度cur_quarter_sale_target_per */
    @TableField("CUR_QUARTER_SALE_TARGET_PER")
    private BigDecimal curQuarterSaleTargetPer;

    /** 上季度库销比pre_quarter_inv_sale_per */
    @TableField("PRE_QUARTER_INV_SALE_PER")
    private BigDecimal preQuarterInvSalePer;

    /**
     * 上月库销比
     */
    @TableField("PRE_MONTH_STOCK_SALE_RATE")
    private BigDecimal preMonthStockSaleRate;

    /**
     * 去年上月库销比
     */
    @TableField("LASTPRE_MONTH_STOCK_SALE_RATE")
    private BigDecimal lastpreMonthStockSaleRate;

 /** 本次采买数量cur_pur_qty */
    @TableField("CUR_PUR_QTY")
    private BigDecimal curPurQty;

    /** 年度采买预算使用度year_purchaset_per */
    @TableField("YEAR_PURCHASET_PER")
    private BigDecimal yearPurchasetPer;

    /** 本季度采买预算使用度cur_used_quarter_pur_per */
    @TableField("CUR_USED_QUARTER_PUR_PER")
    private BigDecimal curUsedQuarterPurPer;

    /** 本季度剩余采买预算cur_quarter_left_pur */
    @TableField("CUR_QUARTER_LEFT_PUR")
    private BigDecimal curQuarterLeftPur;

    /** 年度剩余采买预算year_left_pur_budget */
    @TableField("YEAR_LEFT_PUR_BUDGET")
    private BigDecimal yearLeftPurBudget;

    /** 平均每周剩余采买预算avg_week_left_pur */
    @TableField("AVG_WEEK_LEFT_PUR")
    private BigDecimal avgWeekLeftPur;

    /** 当前库存周转天数turn_over_day */
    @TableField("TURN_OVER_DAY")
    private BigDecimal turnOverDay;

    /** 目标数据版本target_version */
    @TableField("TARGET_VERSION")
    private BigDecimal targetVersion;

    /** 数据版本（当前数据生成版本号，每重新生成一次加1）data_version */
    @TableField("DATA_VERSION")
    private String dataVersion;

    /** 是否启用（0：未删除,1：已删除）默认：0 */
    @TableField("IS_DELETE")
    private BigDecimal isDelete;

    /** 创建人 */
    @TableField("CREATE_BY")
    private String createBy;

    /** 修改人 */
    @TableField("UPDATE_BY")
    private String updateBy;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

}