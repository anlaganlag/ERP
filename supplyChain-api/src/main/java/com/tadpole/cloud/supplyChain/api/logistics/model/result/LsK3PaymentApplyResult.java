package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
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
 * 物流费K3付款申请单 出参类
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
@ExcelIgnoreUnannotated
@TableName("LS_K3_PAYMENT_APPLY")
public class LsK3PaymentApplyResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("单据编号")
    private String paymentApplyNo;

    @ApiModelProperty("单据类型")
    private String billType;

    @ApiModelProperty("ERP申请日期")
    private Date erpApplyDate;

    @ApiModelProperty("往来单位类型")
    private String companyType;

    /** 往来单位编码 */
    @ApiModelProperty("往来单位编码")
    private String companyCode;

    @ApiModelProperty("往来单位")
    private String companyName;

    @ApiModelProperty("收款单位类型")
    private String receiveCompanyType;

    @ApiModelProperty("收款单位编码")
    private String receiveCompanyCode;

    @ApiModelProperty("收款单位")
    private String receiveCompanyName;

    @ApiModelProperty("是否有发票 0：否，1：是")
    private String hasBill;

    @ApiModelProperty("付款类型编码")
    private String payTypeCode;

    @ApiModelProperty("付款类型")
    private String payType;

    @ApiModelProperty("是否预付")
    private String isPrePay;

    @ApiModelProperty("币制编码")
    private String currencyCode;

    @ApiModelProperty("币制")
    private String currency;

    @ApiModelProperty("汇率")
    private BigDecimal currencyRate;

    @ApiModelProperty("结算币制编码")
    private String settlementCurrencyCode;

    @ApiModelProperty("结算币制")
    private String settlementCurrency;

    @ApiModelProperty("结算汇率")
    private BigDecimal settlementCurrencyRate;

    @ApiModelProperty("供应链公司编码")
    private String supplierCode;

    @ApiModelProperty("供应链公司")
    private String supplierName;

    @ApiModelProperty("申请组织")
    private String applyOrg;

    @ApiModelProperty("申请组织编码")
    private String applyOrgCode;

    @ApiModelProperty("结算组织")
    private String settlementOrg;

    @ApiModelProperty("结算组织编码")
    private String settlementCode;

    @ApiModelProperty("付款组织")
    private String paymentOrg;

    @ApiModelProperty("付款组织编码")
    private String paymentOrgCode;

    @ApiModelProperty("申请人名称")
    private String applyName;

    @ApiModelProperty("申请人工号")
    private String applyAccount;

    @ApiModelProperty("是否需要补充发票 0：否，1：是")
    private String isSupplementBill;

    @ApiModelProperty("摘要")
    private String remark;

    @ApiModelProperty("同步K3状态")
    private String syncK3Status;

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

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateUser;

}
