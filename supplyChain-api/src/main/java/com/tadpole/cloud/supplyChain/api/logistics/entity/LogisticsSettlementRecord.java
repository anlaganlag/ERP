package com.tadpole.cloud.supplyChain.api.logistics.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author: ty
 * @description: 物流实际结算明细
 * @date: 2022/11/14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LOGISTICS_SETTLEMENT_RECORD")
public class LogisticsSettlementRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty(value = "父类ID（物流实际结算明细表ID）")
    @TableField("PARENT_ID")
    private BigDecimal parentId;

    @ApiModelProperty(value = "物流计费重")
    @TableField("LOGISTICS_COUNT_FEE")
    private BigDecimal logisticsCountFee;

    @ApiModelProperty(value = "单价")
    @TableField("UNIT_PRICE")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "物流费")
    @TableField("LOGISTICS_FEE")
    private BigDecimal logisticsFee;

    @ApiModelProperty(value = "物流费币制")
    @TableField("LOGISTICS_CURRENCY")
    private String logisticsCurrency;

    @ApiModelProperty(value = "DTP（CNY）")
    @TableField("DTP")
    private BigDecimal dtp;

    @ApiModelProperty(value = "报关费(CNY)")
    @TableField("TARIFF_FEE")
    private BigDecimal tariffFee;

    @ApiModelProperty(value = "清关费（CNY）")
    @TableField("CLEAR_TARIFF_FEE")
    private BigDecimal clearTariffFee;

    @ApiModelProperty(value = "旺季附加费(CNY)")
    @TableField("BUSY_SEASON_FEE")
    private BigDecimal busySeasonFee;

    @ApiModelProperty(value = "燃油附加费(CNY)")
    @TableField("OIL_FEE")
    private BigDecimal oilFee;

    @ApiModelProperty(value = "燃油费率（%）")
    @TableField("OIL_FEE_PERCENT")
    private String oilFeePercent;

    @ApiModelProperty(value = "杂费(CNY)")
    @TableField("OTHERS_FEE")
    private BigDecimal othersFee;

    @ApiModelProperty(value = "产品附加费(CNY)")
    @TableField("PRODUCT_FEE")
    private BigDecimal productFee;

    @ApiModelProperty(value = "物流对账单号")
    @TableField("LOGISTICS_SETTLEMENT_ORDER")
    private String logisticsSettlementOrder;

    @ApiModelProperty(value = "流转税（VAT/TAX/205）")
    @TableField("CHANGE_TAX")
    private BigDecimal changeTax;

    @ApiModelProperty(value = "DUTY/201")
    @TableField("DUTY")
    private BigDecimal duty;

    @ApiModelProperty(value = "税费币制")
    @TableField("TAX_CURRENCY")
    private String taxCurrency;

    @ApiModelProperty(value = "税号")
    @TableField("TAX_ORDER")
    private String taxOrder;

    @ApiModelProperty(value = "C88单号")
    @TableField("C88")
    private String c88;

    @ApiModelProperty(value = "C88备注")
    @TableField("C88_REMARK")
    private String c88Remark;

    @ApiModelProperty(value = "VAT原币金额")
    @TableField("VAT")
    private BigDecimal vat;

    @ApiModelProperty(value = "税费发票号码")
    @TableField("TAX_INVOICE_ORDER")
    private String taxInvoiceOrder;

    @ApiModelProperty(value = "合计（CNY）")
    @TableField("TOTAL_FEE")
    private BigDecimal totalFee;

    @ApiModelProperty(value = "物流费ERP申请日期")
    @TableField("LOGISTICS_ERP_DATE")
    private Date logisticsErpDate;

    @ApiModelProperty(value = "物流费单据编号")
    @TableField("LOGISTICS_BILL_ORDER")
    private String logisticsBillOrder;

    @ApiModelProperty(value = "税费ERP申请日期")
    @TableField("TAX_ERP_DATE")
    private Date taxErpDate;

    @ApiModelProperty(value = "税费单据编号")
    @TableField("TAX_BILL_ORDER")
    private String taxBillOrder;

    @ApiModelProperty(value = "修改日期")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "操作类型")
    @TableField("OPERATION_TYPE")
    private String operationType;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATE_USER")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TIME")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    @TableField("UPDATE_USER")
    private String updateUser;

    @ApiModelProperty(value = "序号")
    @TableField("SEQ_NO")
    private Integer seqNo;

    @ApiModelProperty(value = "数据月份")
    @TableField("DATA_MONTHS")
    private String dataMonths;

    @ApiModelProperty(value = "发货批次号")
    @TableField("SHIPMENT_NUM")
    private String shipmentNum;

    @ApiModelProperty(value = "数据状态 0：删除，1：正常")
    @TableField("DATA_STATUS")
    private Integer dataStatus;
}
