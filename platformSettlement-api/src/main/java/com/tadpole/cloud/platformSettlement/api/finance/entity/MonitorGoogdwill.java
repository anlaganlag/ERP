package com.tadpole.cloud.platformSettlement.api.finance.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
* <p>
    * goodwill监控表
    * </p>
*
* @author S20190161
* @since 2023-07-17
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("MONITOR_GOOGDWILL")
@ExcelIgnoreUnannotated
public class MonitorGoogdwill implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    @ExcelProperty("会计期")
    @TableField("FISCAL_PERIOD")
    private String fiscalPeriod;
    @ExcelProperty("账号")
    @TableField("ACCOUNT_NAME")
    private String accountName;
    @ExcelProperty("站点")
    @TableField("COUNTRY_CODE")
    private String countryCode;


    @ExcelProperty("订单ID")
    @TableField("ORDER_ID")
    private String orderId;
    @ExcelProperty("SKU")
    @TableField("SKU")
    private String sku;

    @TableField("POSTED_DATE")
    private Date postedDate;


    @ExcelProperty("订单发货数量")
    @TableField("SHIP_NUM")
    private Long shipNum;

    @ExcelProperty("settlementId")
    @TableField("SETTLEMENT_ID")
    private String settlementId;
    @ExcelProperty("金额")
    @TableField("AMOUNT")
    private BigDecimal amount;


    @ExcelProperty("退货入库数量")
    @TableField("STORAGE_NUM")
    private Long storageNum;

    @ExcelProperty("是否需要索赔 0否 是")
    /** 是否需要索赔	0否	1是	若订单发货数量大于退货入库数量，则为“是”，二者相等则为“否”，数量小于退货入库数量，则提示错误 */
    @TableField("IS_CLAIM")
    private Integer isClaim;

    @ExcelProperty("审核状态 0 未确认 1 已确认 ")
    /** 审核状态	0 未确认	1 已确认	汇总本表内“账号+站点+会计期”内所有【order-id】+【SKU】的[金额]，与AZ结算对账-收入记录表 中的同条件（账号+站点+会计期，求和多个结算ID）的金额【goodwill】对比，二者相等，则将本表内“账号+站点+会计期”内所有order-id+SKU的状态变更为“已确认”；否则为“未确认”		 */
    @TableField("STATUS")
    private Integer status;

    @TableField("REMARK")
    private String remark;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("UPDATE_TIME")
    private Date updateTime;
}
