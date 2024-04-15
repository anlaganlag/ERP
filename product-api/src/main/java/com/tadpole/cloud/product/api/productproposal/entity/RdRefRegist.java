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
 * 提案-退款登记 实体类
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
@TableName("RD_REF_REGIST")
@ExcelIgnoreUnannotated
public class RdRefRegist implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统信息-退款申请编号 */
   @TableId(value = "SYS_REF_APP_CODE", type = IdType.ASSIGN_ID)
    private String sysRefAppCode;

    /** 系统信息-创建时间 */
    @TableField("SYS_C_DATE")
    private Date sysCDate;

    /** 系统信息-最后更新时间 */
    @TableField("SYS_L_DATE")
    private Date sysLDate;

    /** 系统信息-部门编号 */
    @TableField("SYS_DEPT_CODE")
    private String sysDeptCode;

    /** 系统信息-部门名称 */
    @TableField("SYS_DEPT_NAME")
    private String sysDeptName;

    /** 系统信息-员工编号 */
    @TableField("SYS_PER_CODE")
    private String sysPerCode;

    /** 系统信息-员工姓名 */
    @TableField("SYS_PER_NAME")
    private String sysPerName;

    /** 系统信息-退款申请状态 值域{"待退款-条件未达成","待退款-条件已达成","待退款确认","待指定账户","待上传凭证","退款失效","待打印","待验资","退款已确认"} */
    @TableField("SYS_REF_APP_STATUS")
    private String sysRefAppStatus;

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

    /** 单据联系-费用申请编号 */
    @TableField("SYS_FEE_APP_CODE")
    private String sysFeeAppCode;

    /** 单据联系-费用申请来源 值域{"购样申请","定制申请"} */
    @TableField("SYS_FEE_APP_SOURCE")
    private String sysFeeAppSource;

    /** 退款记录信息-费用类型 值域{"购样费","打样费",“开模费”} */
    @TableField("SYS_REF_FEE_TYPE")
    private String sysRefFeeType;

    /** 退款记录信息-退款费用 */
    @TableField("SYS_REF_FEES")
    private BigDecimal sysRefFees;

    /** 退款记录信息-供应商编码 */
    @TableField("SYS_REF_SUPPLIER_CODE")
    private String sysRefSupplierCode;

    /** 退款记录信息-供应商名称 */
    @TableField("SYS_REF_SUPPLIER_NAME")
    private String sysRefSupplierName;

    /** 退款记录信息-采购负责人编号 */
    @TableField("SYS_REF_PUR_PER_CODE")
    private String sysRefPurPerCode;

    /** 退款记录信息-采购负责人姓名 */
    @TableField("SYS_REF_PUR_PER_NAME")
    private String sysRefPurPerName;

    /** 退款记录信息-退款方式 */
    @TableField("SYS_REF_TYPE")
    private String sysRefType;

    /** 退款记录信息-退款条件 */
    @TableField("SYS_REF_CONDITION")
    private BigDecimal sysRefCondition;

    /** 订单信息-已下单数量 */
    @TableField("SYS_ORDER_QTY")
    private BigDecimal sysOrderQty;

    /** 订单信息-已下单总额 */
    @TableField("SYS_ORDER_AMOUNT")
    private BigDecimal sysOrderAmount;

    /** 订单信息-更新时间 */
    @TableField("SYS_UPDATE")
    private Date sysUpdate;

    /** 退款反馈信息-实际退款结果 值域{"直接退款","订单扣款","退款失效-提案失败","退款失效-其它"} */
    @TableField("SYS_REF_ACTUAL_REF_RESULT")
    private String sysRefActualRefResult;

    /** 退款反馈信息-退款账户类型 值域{"个人账户","公司账户"} */
    @TableField("SYS_REF_ACCOUNT_TYPE")
    private String sysRefAccountType;

    /** 退款反馈信息-失效说明 */
    @TableField("SYS_REF_INVALID_EXPLAIN")
    private String sysRefInvalidExplain;

    /** 退款反馈信息-退款反馈时间 */
    @TableField("SYS_REF_FEB_DATE")
    private Date sysRefFebDate;

    /** 指定账户信息-公司-公司实体公司名称 */
    @TableField("SYS_REF_APP_COMP")
    private String sysRefAppComp;

    /** 指定账户信息-公司-账户性质 */
    @TableField("SYS_REF_APP_AP")
    private String sysRefAppAp;

    /** 指定账户信息-公司-账户 */
    @TableField("SYS_REF_APP_ACCOUNT")
    private String sysRefAppAccount;

    /** 指定账户信息-公司-账户户名 */
    @TableField("SYS_REF_APP_AN")
    private String sysRefAppAn;

    /** 指定账户信息-公司-账户开户行 */
    @TableField("SYS_REF_APP_AOB")
    private String sysRefAppAob;

    /** 指定账户信息-指定账户时间 */
    @TableField("SYS_REF_APP_RD")
    private Date sysRefAppRd;

    /** 指定账户信息-指定账户登记人编号 */
    @TableField("SYS_REF_APP_R_PC")
    private String sysRefAppRPc;

    /** 指定账户信息-指定账户登记人名称 */
    @TableField("SYS_REF_APP_R_PN")
    private String sysRefAppRPn;

    /** 凭证信息-实际退款金额 */
    @TableField("SYS_REF_APP_AMOUNT")
    private BigDecimal sysRefAppAmount;

    /** 凭证信息-差异说明 */
    @TableField("SYS_REF_APP_EXPLAIN")
    private String sysRefAppExplain;

    /** 凭证信息-打款人 */
    @TableField("SYS_REF_PAYER")
    private String sysRefPayer;

    /** 凭证信息-打款日期 */
    @TableField("SYS_REF_PAY_DATE")
    private Date sysRefPayDate;

    /** 凭证信息-退款凭证 */
    @TableField("SYS_REF_VOUCHER")
    private String sysRefVoucher;

    /** 凭证信息-上传凭证日期 */
    @TableField("SYS_REF_UP_VOUCHER_DATE")
    private Date sysRefUpVoucherDate;

    /** 凭证信息-上传凭证操作人编号 */
    @TableField("SYS_REF_UP_VOUCHER_PC")
    private String sysRefUpVoucherPc;

    /** 凭证信息-上传凭证操作人姓名 */
    @TableField("SYS_REF_UP_VOUCHER_PN")
    private String sysRefUpVoucherPn;

    /** 退款打印-打印时间 */
    @TableField("SYS_REF_APP_FIRST_PD")
    private Date sysRefAppFirstPd;

    /** 退款打印-打印人编号 */
    @TableField("SYS_REF_APP_FIRST_PPC")
    private String sysRefAppFirstPpc;

    /** 退款打印-打印人姓名 */
    @TableField("SYS_REF_APP_PPN")
    private String sysRefAppPpn;

    /** 验资信息-验资时间 */
    @TableField("SYS_REF_APP_ED")
    private Date sysRefAppEd;

    /** 验资信息-验资人编号 */
    @TableField("SYS_REF_APP_EPC")
    private String sysRefAppEpc;

    /** 验资信息-验资人姓名 */
    @TableField("SYS_REF_APP_EPN")
    private String sysRefAppEpn;

}