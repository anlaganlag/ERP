package com.tadpole.cloud.supplyChain.api.logistics.model.params;

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
 * 物流费付款明细 入参类
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
public class LsLogisticsFeePaymentDetailParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 物流费编号 */
    @ApiModelProperty("物流费编号")
    private String logisticsFeeNo;

    /** 发货批次号 */
    @ApiModelProperty("发货批次号")
    private String shipmentNum;

    /** 物流对账单号 */
    @ApiModelProperty("物流对账单号")
    private String logisticsCheckOrder;

    /** 物流单号 */
    @ApiModelProperty("物流单号")
    private String logisticsNo;

    /** 单价 */
    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    /** 物流费 */
    @ApiModelProperty("物流费")
    private BigDecimal logisticsFee;

    /** 物流费币制 */
    @ApiModelProperty("物流费币制")
    private String logisticsCurrency;

    /** 报关费 */
    @ApiModelProperty("报关费")
    private BigDecimal customsFee;

    /** 清关费 */
    @ApiModelProperty("清关费")
    private BigDecimal clearCustomsFee;

    /** 燃油附加费 */
    @ApiModelProperty("燃油附加费")
    private BigDecimal oilFee;

    /** 旺季附加费 */
    @ApiModelProperty("旺季附加费")
    private BigDecimal busySeasonFee;

    /** 产品附加费（附加费及杂费） */
    @ApiModelProperty("产品附加费（附加费及杂费）")
    private BigDecimal othersFee;

    /** 燃油费率（%） */
    @ApiModelProperty("燃油费率（%）")
    private BigDecimal oilFeePercent;

    /** 杂费 */
    @ApiModelProperty("杂费")
    private BigDecimal sundryFee;

    /** DTP */
    @ApiModelProperty("DTP")
    private BigDecimal dtp;

    /** 流转税（VAT/TAX/205） */
    @ApiModelProperty("流转税（VAT/TAX/205）")
    private BigDecimal changeTax;

    /** 税费（DUTY/201） */
    @ApiModelProperty("税费（DUTY/201）")
    private BigDecimal taxFee;

    /** 税费币制 */
    @ApiModelProperty("税费币制")
    private String taxCurrency;

    /** 税号 */
    @ApiModelProperty("税号")
    private String taxOrder;

    /** C88单号 */
    @ApiModelProperty("C88单号")
    private String c88;

    /** C88备注 */
    @ApiModelProperty("C88备注")
    private String c88Remark;

    /** VAT原币金额 */
    @ApiModelProperty("VAT原币金额")
    private BigDecimal vat;

    /** 税费发票号码 */
    @ApiModelProperty("税费发票号码")
    private String taxInvoiceOrder;

    /** 合计（CNY） */
    @ApiModelProperty("合计（CNY）")
    private BigDecimal totalFee;

    /** 序号 */
    @ApiModelProperty("序号")
    private Integer orderNum;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updateUser;

}
