package com.tadpole.cloud.supplyChain.api.logistics.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 物流费K3付款申请单 实体类
 * </p>
 *
 * @author ty
 * @since 2023-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LS_K3_PAYMENT_APPLY")
@ExcelIgnoreUnannotated
public class LsK3PaymentApply implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 单据编号 */
    @ApiModelProperty("单据编号")
    @TableField("PAYMENT_APPLY_NO")
    private String paymentApplyNo;

    /** 单据类型 */
    @ApiModelProperty("单据类型")
    @TableField("BILL_TYPE")
    private String billType;

    /** ERP申请日期 */
    @ApiModelProperty("ERP申请日期")
    @TableField("ERP_APPLY_DATE")
    private Date erpApplyDate;

    /** 往来单位类型 */
    @ApiModelProperty("往来单位类型")
    @TableField("COMPANY_TYPE")
    private String companyType;

    /** 往来单位编码 */
    @ApiModelProperty("往来单位编码")
    @TableField("COMPANY_CODE")
    private String companyCode;

    /** 往来单位 */
    @ApiModelProperty("往来单位")
    @TableField("COMPANY_NAME")
    private String companyName;

    /** 收款单位类型 */
    @ApiModelProperty("收款单位类型")
    @TableField("RECEIVE_COMPANY_TYPE")
    private String receiveCompanyType;

    /** 收款单位编码 */
    @ApiModelProperty("收款单位编码")
    @TableField("RECEIVE_COMPANY_CODE")
    private String receiveCompanyCode;

    /** 收款单位 */
    @ApiModelProperty("收款单位")
    @TableField("RECEIVE_COMPANY_NAME")
    private String receiveCompanyName;

    /** 是否有发票 0：否，1：是 */
    @ApiModelProperty("是否有发票 0：否，1：是")
    @TableField("HAS_BILL")
    private String hasBill;

    /** 付款类型编码 */
    @ApiModelProperty("付款类型编码")
    @TableField("PAY_TYPE_CODE")
    private String payTypeCode;

    /** 付款类型 */
    @ApiModelProperty("付款类型")
    @TableField("PAY_TYPE")
    private String payType;

    /** 是否预付 */
    @ApiModelProperty("是否预付")
    @TableField("IS_PRE_PAY")
    private String isPrePay;

    /** 币制编码 */
    @ApiModelProperty("币制编码")
    @TableField("CURRENCY_CODE")
    private String currencyCode;

    /** 币制 */
    @ApiModelProperty("币制")
    @TableField("CURRENCY")
    private String currency;

    /** 汇率 */
    @ApiModelProperty("汇率")
    @TableField("CURRENCY_RATE")
    private BigDecimal currencyRate;

    /** 结算币制编码 */
    @ApiModelProperty("结算币制编码")
    @TableField("SETTLEMENT_CURRENCY_CODE")
    private String settlementCurrencyCode;

    /** 结算币制 */
    @ApiModelProperty("结算币制")
    @TableField("SETTLEMENT_CURRENCY")
    private String settlementCurrency;

    /** 结算汇率 */
    @ApiModelProperty("结算汇率")
    @TableField("SETTLEMENT_CURRENCY_RATE")
    private BigDecimal settlementCurrencyRate;

    /** 供应链公司编码 */
    @ApiModelProperty("供应链公司编码")
    @TableField("SUPPLIER_CODE")
    private String supplierCode;

    /** 供应链公司 */
    @ApiModelProperty("供应链公司")
    @TableField("SUPPLIER_NAME")
    private String supplierName;

    /** 申请组织 */
    @ApiModelProperty("申请组织")
    @TableField("APPLY_ORG")
    private String applyOrg;

    /** 申请组织编码 */
    @ApiModelProperty("申请组织编码")
    @TableField("APPLY_ORG_CODE")
    private String applyOrgCode;

    /** 结算组织 */
    @ApiModelProperty("结算组织")
    @TableField("SETTLEMENT_ORG")
    private String settlementOrg;

    /** 结算组织编码 */
    @ApiModelProperty("结算组织编码")
    @TableField("SETTLEMENT_CODE")
    private String settlementCode;

    /** 付款组织 */
    @ApiModelProperty("付款组织")
    @TableField("PAYMENT_ORG")
    private String paymentOrg;

    /** 付款组织编码 */
    @ApiModelProperty("付款组织编码")
    @TableField("PAYMENT_ORG_CODE")
    private String paymentOrgCode;

    /** 申请人名称 */
    @ApiModelProperty("申请人名称")
    @TableField("APPLY_NAME")
    private String applyName;

    /** 申请人工号 */
    @ApiModelProperty("申请人工号")
    @TableField("APPLY_ACCOUNT")
    private String applyAccount;

    /** 是否需要补充发票 0：否，1：是 */
    @ApiModelProperty("是否需要补充发票 0：否，1：是")
    @TableField("IS_SUPPLEMENT_BILL")
    private String isSupplementBill;

    /** 摘要 */
    @ApiModelProperty("摘要")
    @TableField("REMARK")
    private String remark;

    /** 同步K3状态 */
    @ApiModelProperty("同步K3状态")
    @TableField("SYNC_K3_STATUS")
    private String syncK3Status;

    /** 同步K3时间 */
    @ApiModelProperty("同步K3时间")
    @TableField("SYNC_K3_DATE")
    private Date syncK3Date;

    /** 同步K3响应信息 */
    @ApiModelProperty("同步K3响应信息")
    @TableField("SYNC_RESULT_MSG")
    private String syncResultMsg;

    /** K3返回单据ID */
    @ApiModelProperty("K3返回单据ID")
    @TableField("F_ID")
    private String fId;

    /** K3返回单据编号 */
    @ApiModelProperty("K3返回单据编号")
    @TableField("F_BILL_NO")
    private String fBillNo;

    /** 物流商编码 */
    @TableField("LP_CODE")
    private String lpCode;

    /** 物流商名称 */
    @TableField("LP_NAME")
    private String lpName;

    /** 物流商简称 */
    @TableField("LP_SIMPLE_NAME")
    private String lpSimpleName;

    /** 是否预付款支付 */
    @TableField("IS_DEPOSIT_PREPAYMENT")
    private String isDepositPrepayment;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    @TableField("CREATE_USER")
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    @TableField("UPDATE_USER")
    private String updateUser;

    /** 更新人工号 */
    @ApiModelProperty("更新人工号")
    @TableField(exist = false)
    private String updateUserAccount;

}