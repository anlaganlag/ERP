package com.tadpole.cloud.supplyChain.api.logistics.entity;

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
 * 物流费付款明细 实体类
 * </p>
 *
 * @author ty
 * @since 2023-12-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LS_LOGISTICS_FEE_PAYMENT_DETAIL")
@ExcelIgnoreUnannotated
public class LsLogisticsFeePaymentDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 物流费编号 */
    @TableField("LOGISTICS_FEE_NO")
    private String logisticsFeeNo;

    /** 发货批次号 */
    @TableField("SHIPMENT_NUM")
    private String shipmentNum;

    /** 物流对账单号 */
    @TableField("LOGISTICS_CHECK_ORDER")
    private String logisticsCheckOrder;

    /** 物流单号 */
    @TableField("LOGISTICS_NO")
    private String logisticsNo;

    /** 单价 */
    @TableField("UNIT_PRICE")
    private BigDecimal unitPrice;

    /** 物流费 */
    @TableField("LOGISTICS_FEE")
    private BigDecimal logisticsFee;

    /** 物流费币制 */
    @TableField("LOGISTICS_CURRENCY")
    private String logisticsCurrency;

    /** 报关费 */
    @TableField("CUSTOMS_FEE")
    private BigDecimal customsFee;

    /** 清关费 */
    @TableField("CLEAR_CUSTOMS_FEE")
    private BigDecimal clearCustomsFee;

    /** 燃油附加费 */
    @TableField("OIL_FEE")
    private BigDecimal oilFee;

    /** 旺季附加费 */
    @TableField("BUSY_SEASON_FEE")
    private BigDecimal busySeasonFee;

    /** 产品附加费（附加费及杂费） */
    @TableField("OTHERS_FEE")
    private BigDecimal othersFee;

    /** 燃油费率（%） */
    @TableField("OIL_FEE_PERCENT")
    private BigDecimal oilFeePercent;

    /** 杂费 */
    @TableField("SUNDRY_FEE")
    private BigDecimal sundryFee;

    /** DTP */
    @TableField("DTP")
    private BigDecimal dtp;

    /** 流转税（VAT/TAX/205） */
    @TableField("CHANGE_TAX")
    private BigDecimal changeTax;

    /** 税费（DUTY/201） */
    @TableField("TAX_FEE")
    private BigDecimal taxFee;

    /** 税费币制 */
    @TableField("TAX_CURRENCY")
    private String taxCurrency;

    /** 税号 */
    @TableField("TAX_ORDER")
    private String taxOrder;

    /** C88单号 */
    @TableField("C88")
    private String c88;

    /** C88备注 */
    @TableField("C88_REMARK")
    private String c88Remark;

    /** VAT原币金额 */
    @TableField("VAT")
    private BigDecimal vat;

    /** 税费发票号码 */
    @TableField("TAX_INVOICE_ORDER")
    private String taxInvoiceOrder;

    /** 合计（CNY） */
    @TableField("TOTAL_FEE")
    private BigDecimal totalFee;

    /** 序号 */
    @TableField("ORDER_NUM")
    private Integer orderNum;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @TableField("CREATE_USER")
    private String createUser;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @TableField("UPDATE_USER")
    private String updateUser;

}