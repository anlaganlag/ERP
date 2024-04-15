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
 * 物流费K3付款申请单 入参类
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
public class LsK3PaymentApplyParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 单据编号 */
    @ApiModelProperty("单据编号")
    private String paymentApplyNo;

    /** 单据类型 */
    @ApiModelProperty("单据类型")
    private String billType;

    /** ERP申请日期 */
    @ApiModelProperty("ERP申请日期")
    private Date erpApplyDate;

    /** 往来单位类型 */
    @ApiModelProperty("往来单位类型")
    private String companyType;

    /** 往来单位编码 */
    @ApiModelProperty("往来单位编码")
    private String companyCode;

    /** 往来单位 */
    @ApiModelProperty("往来单位")
    private String companyName;

    /** 收款单位类型 */
    @ApiModelProperty("收款单位类型")
    private String receiveCompanyType;

    /** 收款单位编码 */
    @ApiModelProperty("收款单位编码")
    private String receiveCompanyCode;

    /** 收款单位 */
    @ApiModelProperty("收款单位")
    private String receiveCompanyName;

    /** 是否有发票 0：否，1：是 */
    @ApiModelProperty("是否有发票 0：否，1：是")
    private String hasBill;

    /** 付款类型编码 */
    @ApiModelProperty("付款类型编码")
    private String payTypeCode;

    /** 付款类型 */
    @ApiModelProperty("付款类型")
    private String payType;

    /** 是否预付 */
    @ApiModelProperty("是否预付")
    private String isPrePay;

    /** 币制编码 */
    @ApiModelProperty("币制编码")
    private String currencyCode;

    /** 币制 */
    @ApiModelProperty("币制")
    private String currency;

    /** 汇率 */
    @ApiModelProperty("汇率")
    private BigDecimal currencyRate;

    /** 结算币制编码 */
    @ApiModelProperty("结算币制编码")
    private String settlementCurrencyCode;

    /** 结算币制 */
    @ApiModelProperty("结算币制")
    private String settlementCurrency;

    /** 结算汇率 */
    @ApiModelProperty("结算汇率")
    private BigDecimal settlementCurrencyRate;

    /** 供应链公司编码 */
    @ApiModelProperty("供应链公司编码")
    private String supplierCode;

    /** 供应链公司 */
    @ApiModelProperty("供应链公司")
    private String supplierName;

    /** 申请组织 */
    @ApiModelProperty("申请组织")
    private String applyOrg;

    /** 申请组织编码 */
    @ApiModelProperty("申请组织编码")
    private String applyOrgCode;

    /** 结算组织 */
    @ApiModelProperty("结算组织")
    private String settlementOrg;

    /** 结算组织编码 */
    @ApiModelProperty("结算组织编码")
    private String settlementCode;

    /** 付款组织 */
    @ApiModelProperty("付款组织")
    private String paymentOrg;

    /** 付款组织编码 */
    @ApiModelProperty("付款组织编码")
    private String paymentOrgCode;

    /** 申请人名称 */
    @ApiModelProperty("申请人名称")
    private String applyName;

    /** 申请人工号 */
    @ApiModelProperty("申请人工号")
    private String applyAccount;

    /** 是否需要补充发票 0：否，1：是 */
    @ApiModelProperty("是否需要补充发票 0：否，1：是")
    private String isSupplementBill;

    /** 摘要 */
    @ApiModelProperty("摘要")
    private String remark;

    /** 同步K3状态 */
    @ApiModelProperty("同步K3状态")
    private String syncK3Status;

    /** 同步K3时间 */
    @ApiModelProperty("同步K3时间")
    private Date syncK3Date;

    /** 同步K3响应信息 */
    @ApiModelProperty("同步K3响应信息")
    private String syncResultMsg;

    /** K3返回单据ID */
    @ApiModelProperty("K3返回单据ID")
    private String fId;

    /** K3返回单据编号 */
    @ApiModelProperty("K3返回单据编号")
    private String fBillNo;

    /** 物流商编码 */
    @ApiModelProperty("物流商编码")
    private String lpCode;

    /** 物流商名称 */
    @ApiModelProperty("物流商名称")
    private String lpName;

    /** 物流商简称 */
    @ApiModelProperty("物流商简称")
    private String lpSimpleName;

    /** 是否预付款支付 */
    @ApiModelProperty("是否预付款支付")
    private String isDepositPrepayment;

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
