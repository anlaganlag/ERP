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
 * 提案-购样申请 实体类
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
@TableName("RD_SAMPLE_PA")
@ExcelIgnoreUnannotated
public class RdSamplePa implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统信息-费用申请编号 */
   @TableId(value = "SYS_FEE_APP_CODE", type = IdType.ASSIGN_ID)
    private String sysFeeAppCode;

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

    /** 系统信息-申请状态 值域{"待提交","待审核","待审批","待上传","待支付","已支付","已撤销","已归档"} */
    @TableField("SYS_FEE_APP_STATUS")
    private String sysFeeAppStatus;

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

    /** 申请信息-拿样渠道 值域{"供应商","1688网站","淘宝网站"} */
    @TableField("SYS_FEE_APP_SC")
    private String sysFeeAppSc;

    /** 申请信息-店铺名称 */
    @TableField("SYS_FEE_APP_SHOP_NAME")
    private String sysFeeAppShopName;

    /** 申请信息-样品名称 */
    @TableField("SYS_FEE_APP_PRO_NAME")
    private String sysFeeAppProName;

    /** 申请信息-商品购买页面 */
    @TableField("SYS_FEE_APP_PRO_PUR_PAGE")
    private String sysFeeAppProPurPage;

    /** 申请信息-样品单价 */
    @TableField("SYS_FEE_APP_SUP")
    private BigDecimal sysFeeAppSup;

    /** 申请信息-样品采购数量 */
    @TableField("SYS_FEE_APP_PUR_QTY")
    private BigDecimal sysFeeAppPurQty;

    /** 申请信息-运费 */
    @TableField("SYS_FEE_APP_FREIGHT")
    private BigDecimal sysFeeAppFreight;

    /** 申请信息-费用合计 */
    @TableField("SYS_FEE_APP_TOTAL_FEE")
    private BigDecimal sysFeeAppTotalFee;

    /** 申请信息-商品图片 */
    @TableField("SYS_FEE_APP_PRO_PIC")
    private String sysFeeAppProPic;

    /** 申请信息-是否可退款 值域{"是","否"} */
    @TableField("SYS_FEE_APP_SUPPLIER_IR")
    private String sysFeeAppSupplierIr;

    /** 申请信息-退款方式 值域{"首单退款","订单量退款","订单金额退款"} */
    @TableField("SYS_FEE_APP_SUPPLIER_RM")
    private String sysFeeAppSupplierRm;

    /** 申请信息-退款条件 */
    @TableField("SYS_FEE_APP_SUPPLIER_RC")
    private BigDecimal sysFeeAppSupplierRc;

    /** 申请信息-供应商编号 */
    @TableField("SYS_FEE_APP_SUPPLIER_NUM")
    private String sysFeeAppSupplierNum;

    /** 申请信息-供应商名称 */
    @TableField("SYS_FEE_APP_SUPPLIER_NAME")
    private String sysFeeAppSupplierName;

    /** 申请信息-账户类型 */
    @TableField("SYS_FEE_APP_SUPPLIER_AT")
    private String sysFeeAppSupplierAt;

    /** 申请信息-收款方式 */
    @TableField("SYS_FEE_APP_SUPPLIER_PM")
    private String sysFeeAppSupplierPm;

    /** 申请信息-银行账号 */
    @TableField("SYS_FEE_APP_SUPPLIER_BAU")
    private String sysFeeAppSupplierBau;

    /** 申请信息-账号户名 */
    @TableField("SYS_FEE_APP_SUPPLIER_AN")
    private String sysFeeAppSupplierAn;

    /** 申请信息-开户行 */
    @TableField("SYS_FEE_APP_SUPPLIER_OB")
    private String sysFeeAppSupplierOb;

    /** 申请信息-支付宝账号 */
    @TableField("SYS_FEE_APP_SUPPLIER_AA")
    private String sysFeeAppSupplierAa;

    /** 申请信息-采购负责人姓名 */
    @TableField("SYS_FEE_APP_PUR_PER_NAME")
    private String sysFeeAppPurPerName;

    /** 申请信息-采购负责人编号 */
    @TableField("SYS_FEE_APP_PUR_PER_CODE")
    private String sysFeeAppPurPerCode;

    /** 申请信息-提交时间 */
    @TableField("SYS_FEE_APP_SUB_DATE")
    private Date sysFeeAppSubDate;

    /** 申请信息-补充信息-订单号 */
    @TableField("SYS_FEE_APP_ORDER_NUM")
    private String sysFeeAppOrderNum;

    /** 申请信息-补充信息-上传时间 */
    @TableField("SYS_FEE_APP_ORDER_UD")
    private Date sysFeeAppOrderUd;

    /** 申请信息-补充信息-上传员工姓名 */
    @TableField("SYS_FEE_APP_ORDER_UPN")
    private String sysFeeAppOrderUpn;

    /** 申请信息-补充信息-上传员工编号 */
    @TableField("SYS_FEE_APP_ORDER_UPC")
    private String sysFeeAppOrderUpc;

    /** 申请信息-补充信息-订单截图 */
    @TableField("SYS_FEE_APP_ORDER_PIC")
    private String sysFeeAppOrderPic;

    /** 审核信息-审核结果 值域{"同意","不同意"} */
    @TableField("SYS_FEE_APP_AUDIT_RESULT")
    private String sysFeeAppAuditResult;

    /** 审核信息-审核备注 */
    @TableField("SYS_FEE_APP_AUDIT_REMARKS")
    private String sysFeeAppAuditRemarks;

    /** 审核信息-审核时间 */
    @TableField("SYS_FEE_APP_AUDIT_DATE")
    private Date sysFeeAppAuditDate;

    /** 审核信息-审核员工姓名 */
    @TableField("SYS_FEE_APP_AUDIT_PER_NAME")
    private String sysFeeAppAuditPerName;

    /** 审核信息-审核员工编号 */
    @TableField("SYS_FEE_APP_AUDIT_PER_CODE")
    private String sysFeeAppAuditPerCode;

    /** 审批信息-审批结果 值域{"同意","不同意"} */
    @TableField("SYS_FEE_APP_APP_RESULT")
    private String sysFeeAppAppResult;

    /** 审批信息-审批备注 */
    @TableField("SYS_FEE_APP_APP_REMARKS")
    private String sysFeeAppAppRemarks;

    /** 审批信息-审批时间 */
    @TableField("SYS_FEE_APP_APP_DATE")
    private Date sysFeeAppAppDate;

    /** 审批信息-审批员工姓名 */
    @TableField("SYS_FEE_APP_APP_PER_NAME")
    private String sysFeeAppAppPerName;

    /** 审批信息-审批员工编号 */
    @TableField("SYS_FEE_APP_APP_PER_CODE")
    private String sysFeeAppAppPerCode;

    /** 支付信息-支付日期 */
    @TableField("SYS_FEE_APP_PAY_DATE")
    private Date sysFeeAppPayDate;

    /** 支付信息-实际支付金额 */
    @TableField("SYS_FEE_APP_PAY_AMOUNT")
    private BigDecimal sysFeeAppPayAmount;

    /** 支付信息-支付宝账户 */
    @TableField("SYS_FEE_APP_ALIPAY_ACCOUNT")
    private String sysFeeAppAlipayAccount;

    /** 支付信息-支付宝账户户名 */
    @TableField("SYS_FEE_APP_ALIPAY_AN")
    private String sysFeeAppAlipayAn;

    /** 支付信息-差额说明 */
    @TableField("SYS_FEE_APP_PAY_VD")
    private String sysFeeAppPayVd;

    /** 支付信息-支付登记时间 */
    @TableField("SYS_FEE_APP_PAY_RD")
    private Date sysFeeAppPayRd;

    /** 支付信息-支付登记员工姓名 */
    @TableField("SYS_FEE_APP_PAY_RPN")
    private String sysFeeAppPayRpn;

    /** 支付信息-支付登记员工编号 */
    @TableField("SYS_FEE_APP_PAY_RPC")
    private String sysFeeAppPayRpc;

    /** 撤销信息-撤销日期 */
    @TableField("SYS_FEE_APP_REVOKE_DATE")
    private Date sysFeeAppRevokeDate;

    /** 撤销信息-撤销原因 */
    @TableField("SYS_FEE_APP_REVOKE_REASON")
    private String sysFeeAppRevokeReason;

    /** 撤销信息-撤销员工姓名 */
    @TableField("SYS_FEE_APP_REVOKE_PER_NAME")
    private String sysFeeAppRevokePerName;

    /** 撤销信息-撤销员工编号 */
    @TableField("SYS_FEE_APP_REVOKE_PER_CODE")
    private String sysFeeAppRevokePerCode;

}