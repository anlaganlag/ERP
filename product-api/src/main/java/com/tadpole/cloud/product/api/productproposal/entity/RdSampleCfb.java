package com.tadpole.cloud.product.api.productproposal.entity;

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
 * 提案-定制反馈 实体类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_SAMPLE_CFB")
@ExcelIgnoreUnannotated
public class RdSampleCfb implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 定制系统信息-定制反馈编号 */
   @TableId(value = "SYS_CUST_FEBK_CODE", type = IdType.ASSIGN_ID)
    private String sysCustFebkCode;

    /** 定制系统信息-创建时间 */
    @TableField("SYS_C_DATE")
    private Date sysCDate;

    /** 定制系统信息-最后更新时间 */
    @TableField("SYS_L_DATE")
    private Date sysLDate;

    /** 定制系统信息-部门编号 */
    @TableField("SYS_DEPT_CODE")
    private String sysDeptCode;

    /** 定制系统信息-部门名称 */
    @TableField("SYS_DEPT_NAME")
    private String sysDeptName;

    /** 定制系统信息-员工编号 */
    @TableField("SYS_PER_CODE")
    private String sysPerCode;

    /** 定制系统信息-员工姓名 */
    @TableField("SYS_PER_NAME")
    private String sysPerName;

    /** 定制系统信息-反馈状态 值域{“待提交”,"待开发反馈","开发已反馈"} */
    @TableField("SYS_CF_STATUS")
    private String sysCfStatus;

    /** 单据联系-产品线编号 */
    @TableField("SYS_PL_CODE")
    private String sysPlCode;

    /** 单据联系-SPU */
    @TableField("SYS_SPU")
    private String sysSpu;

    /** 单据联系-提案编号 */
    @TableField("SYS_TA_CODE")
    private String sysTaCode;

    /** 单据联系-拿样任务编号 */
    @TableField("SYS_TS_TASK_CODE")
    private String sysTsTaskCode;

    /** 定制反馈信息-供应商编号 */
    @TableField("SYS_CF_SUPPLIER_NUM")
    private String sysCfSupplierNum;

    /** 定制反馈信息-供应商名称 */
    @TableField("SYS_CF_SUPPLIER_NAME")
    private String sysCfSupplierName;

    /** 定制反馈信息-货源地 */
    @TableField("SYS_CF_GOODS_SOURCE")
    private String sysCfGoodsSource;

    /** 定制反馈信息-开模费用 */
    @TableField("SYS_CF_MOLD_OPEN_FEE")
    private BigDecimal sysCfMoldOpenFee;

    /** 定制反馈信息-费用合计 */
    @TableField("SYS_CF_FEE_TOTAL")
    private BigDecimal sysCfFeeTotal;

    /** 定制反馈信息-打样费用 */
    @TableField("SYS_CF_SAMPLE_FEE")
    private BigDecimal sysCfSampleFee;

    /** 定制反馈信息-是否开模 值域{"是","否"} */
    @TableField("SYS_CF_IS_MOLD_OPEN")
    private String sysCfIsMoldOpen;

    /** 定制反馈信息-是否可退款 值域{"是","否"} */
    @TableField("SYS_CF_IS_REFUND")
    private String sysCfIsRefund;

    /** 定制反馈信息-退款方式 值域{"首单退款","订单量退款","订单金额退款"} */
    @TableField("SYS_CF_REFUND_TYPE")
    private String sysCfRefundType;

    /** 定制反馈信息-退款条件 */
    @TableField("SYS_CF_REFUND_CONDITION")
    private BigDecimal sysCfRefundCondition;

    /** 定制反馈信息-初始报价 */
    @TableField("SYS_CF_INIT_QUOTE")
    private BigDecimal sysCfInitQuote;

    /** 定制反馈信息-起订量要求 */
    @TableField("SYS_CF_MIN_ORDER_QTY_REQ")
    private BigDecimal sysCfMinOrderQtyReq;

    /** 定制反馈信息-生产周期 */
    @TableField("SYS_CF_PRODUCT_CYCLE")
    private BigDecimal sysCfProductCycle;

    /** 定制反馈信息-定制用时 */
    @TableField("SYS_CF_CUST_TIME")
    private BigDecimal sysCfCustTime;

    /** 定制反馈信息-附加条件 */
    @TableField("SYS_CF_ADDIT_CONDITION")
    private String sysCfAdditCondition;

    /** 定制反馈信息-票据类型 值域{"专用发票","普通发票","收据"} */
    @TableField("SYS_CF_TICKET_TYPE")
    private String sysCfTicketType;

    /** 定制反馈信息-账户类型 值域{"公户","私户"} */
    @TableField("SYS_CF_ACCOUNT_TYPE")
    private String sysCfAccountType;

    /** 定制反馈信息-收款方式 值域{"银行卡","支付宝"} */
    @TableField("SYS_CF_PAY_METHOD")
    private String sysCfPayMethod;

    /** 定制反馈信息-银行账号 */
    @TableField("SYS_CF_BANK_ACCOUNT")
    private String sysCfBankAccount;

    /** 定制反馈信息-账号户名 */
    @TableField("SYS_CF_ACCOUNT_NAME")
    private String sysCfAccountName;

    /** 定制反馈信息-开户行 */
    @TableField("SYS_CF_BANK_NAME")
    private String sysCfBankName;

    /** 定制反馈信息-支付宝账号 */
    @TableField("SYS_CF_ALIPAY_ACCOUNT")
    private String sysCfAlipayAccount;

    /** 定制反馈信息-支付宝账号户名 */
    @TableField("SYS_CF_ALIPAY_AN")
    private String sysCfAlipayAn;

    /** 定制反馈信息-采购负责人编号 */
    @TableField("SYS_CF_PUR_PER_CODE")
    private String sysCfPurPerCode;

    /** 定制反馈信息-采购负责人姓名 */
    @TableField("SYS_CF_PUR_PER_NAME")
    private String sysCfPurPerName;

    /** 定制反馈信息-提交员工姓名 */
    @TableField("SYS_CF_SUB_PER_NAME")
    private String sysCfSubPerName;

    /** 定制反馈信息-提交员工编号 */
    @TableField("SYS_CF_SUB_PER_CODE")
    private String sysCfSubPerCode;

    /** 定制反馈信息-提交时间 */
    @TableField("SYS_CF_SUB_DATE")
    private Date sysCfSubDate;

    /** 开发反馈信息-不采纳说明 */
    @TableField("SYS_CF_DEV_EXPLAIN")
    private String sysCfDevExplain;

    /** 开发反馈信息-采纳结果 值域{"采纳","不采纳"} */
    @TableField("SYS_CF_DEV_RESULT")
    private String sysCfDevResult;

    /** 开发反馈信息-开发反馈时间 */
    @TableField("SYS_CF_DEV_DATE")
    private Date sysCfDevDate;

    /** 开发反馈信息-开发反馈员工姓名 */
    @TableField("SYS_CF_DEV_PER_NAME")
    private String sysCfDevPerName;

    /** 开发反馈信息-开发反馈员工编号 */
    @TableField("SYS_CF_DEV_PER_CODE")
    private String sysCfDevPerCode;

    /** 定制反馈信息-模具归属 值域{"公司","共有","供应商"} */
    @TableField("SYS_CF_MOLD_OWNERSHIP")
    private String sysCfMoldOwnership;

    /** 定制反馈信息-打样数量 */
    @TableField("SYS_CF_SAMPLE_QTY")
    private BigDecimal sysCfSampleQty;
}