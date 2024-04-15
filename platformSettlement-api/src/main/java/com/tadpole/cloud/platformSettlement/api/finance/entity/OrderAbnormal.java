package com.tadpole.cloud.platformSettlement.api.finance.entity;

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
*
* </p>
*
* @author cyt
* @since 2022-06-16
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("CW_ORDER_ABNORMAL")
@ExcelIgnoreUnannotated
public class OrderAbnormal implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 销售日期 */
    @TableField("PURCHASE_DATE")
    private String purchaseDate;

    /** 发货日期 */
    @TableField("SHIPPED_DATE")
    private String shippedDate;

    /** 退货日期 */
    @TableField("RETURN_DATE")
    private String returnDate;

    /** 结算日期 */
    @TableField("SETTLEMENT_DATE")
    private String settlementDate;

    /** 账号 */
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** 站点 */
    @TableField("SYS_SITE")
    private String sysSite;

    /** 交易ID */
    @TableField("ORDER_ID")
    private String orderId;

    /** 销售数量 */
    @TableField("SALE_QUANTITY")
    private BigDecimal saleQuantity;

    /** 亚马逊发货数量 */
    @TableField("SHIPPED_QUANTITY")
    private BigDecimal shippedQuantity;

    /** 销售出库发货数量 */
    @TableField("FBA_SHIPPED_QUANTITY")
    private BigDecimal fbaShippedQuantity;

    /** 销售退货数量 */
    @TableField("SALE_RETURN_QUANTITY")
    private BigDecimal saleReturnQuantity;

    /** 结算数量 */
    @TableField("SALE_SETTLEMENT_QUANTITY")
    private BigDecimal saleSettlementQuantity;

    /** 是否发货异常 */
    @TableField("IS_SHIPPED_ERROR")
    private String isShippedError;

    /** 是否退货异常 */
    @TableField("IS_RETURN_ERROR")
    private String isReturnError;

    /** 是否结算异常 */
    @TableField("IS_SETTLEMENT_ERROR")
    private String isSettlementError;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @TableField("CREATE_BY")
    private String createBy;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @TableField("UPDATE_BY")
    private String updateBy;

    /** 销售出库站点 */
    @TableField("OUT_SITE")
    private Date outSite;

    /** 是否异常 */
    @TableField("IS_ERROR")
    private String isError;

    /** 发货渠道 */
    @TableField("FULFILLMENT_CHANNEL")
    private String fulfillmentChannel;
}