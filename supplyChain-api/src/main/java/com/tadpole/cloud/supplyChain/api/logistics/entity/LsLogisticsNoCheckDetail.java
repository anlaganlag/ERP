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
 * 物流单对账明细 实体类
 * </p>
 *
 * @author ty
 * @since 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LS_LOGISTICS_NO_CHECK_DETAIL")
@ExcelIgnoreUnannotated
public class LsLogisticsNoCheckDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 发货批次号 */
    @TableField("SHIPMENT_NUM")
    private String shipmentNum;

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

    /** 产品附加费（附加费及杂费）备注 */
    @TableField("OTHERS_FEE_REMARK")
    private String othersFeeRemark;

    /** DUTY/201 */
    @TableField("TAX_FEE")
    private BigDecimal taxFee;

    /** 合计（CNY） */
    @TableField("TOTAL_FEE")
    private BigDecimal totalFee;

    /** DTP */
    @TableField("DTP")
    private BigDecimal dtp;

    /** 燃油费率（%） */
    @TableField("OIL_FEE_PERCENT")
    private BigDecimal oilFeePercent;

    /** 杂费 */
    @TableField("SUNDRY_FEE")
    private BigDecimal sundryFee;

    /** 物流对账单号 */
    @TableField("LOGISTICS_CHECK_ORDER")
    private String logisticsCheckOrder;

    /** 流转税（VAT/TAX/205） */
    @TableField("CHANGE_TAX")
    private BigDecimal changeTax;

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

    /** 物流费ERP申请日期 */
    @TableField("LOGISTICS_ERP_DATE")
    private Date logisticsErpDate;

    /** 物流费单据编号 */
    @TableField("LOGISTICS_BILL_ORDER")
    private String logisticsBillOrder;

    /** 税费ERP申请日期 */
    @TableField("TAX_ERP_DATE")
    private Date taxErpDate;

    /** 税费单据编号 */
    @TableField("TAX_BILL_ORDER")
    private String taxBillOrder;

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