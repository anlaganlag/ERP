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
 * 提案-研发费报销明细 实体类
 * </p>
 *
 * @author S20190096
 * @since 2024-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_EXPENSE_REIMBURSE_DET")
@ExcelIgnoreUnannotated
public class RdExpenseReimburseDet implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统信息-系统编号 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 系统信息-报销申请编号 */
    @TableField("SYS_SAEA_CODE")
    private String sysSaeaCode;

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

    /** 单据联系-定制反馈编号 */
    @TableField("SYS_CUST_FEBK_CODE")
    private String sysCustFebkCode;

    /** 申请信息-来源 */
    @TableField("SYS_SAEA_SOURCE")
    private String sysSaeaSource;

    /** 申请信息-编号 */
    @TableField("SYS_SAEA_SOURCE_ID")
    private String sysSaeaSourceId;

    /** 申请信息-报销方式 值域{"支出","退款"} */
    @TableField("SYS_SAEA_TYPE")
    private String sysSaeaType;

    /** 申请信息-日期(支付/退款日期) */
    @TableField("SYS_SAEA_APP_DATE")
    private Date sysSaeaAppDate;

    /** 申请信息-拿样方式 */
    @TableField("SYS_SAEA_SAMPLE_METHOD")
    private String sysSaeaSampleMethod;

    /** 申请信息-拿样渠道 */
    @TableField("SYS_SAEA_SAMPLE_CHANNEL")
    private String sysSaeaSampleChannel;

    /** 申请信息-店铺名称 */
    @TableField("SYS_SAEA_SHOP_OR_SUPP_NAME")
    private String sysSaeaShopOrSuppName;

    /** 申请信息-订单凭证 */
    @TableField("SYS_SAEA_ORDER_VOUCHER")
    private String sysSaeaOrderVoucher;

    /** 申请信息-采购负责人编号 */
    @TableField("SYS_SAEA_PUR_CODE")
    private String sysSaeaPurCode;

    /** 申请信息-采购负责人姓名 */
    @TableField("SYS_SAEA_PUR_NAME")
    private String sysSaeaPurName;

    /** 申请信息-申请费用金额/预计退款金额 */
    @TableField("SYS_SAEA_APP_AMOUNT")
    private BigDecimal sysSaeaAppAmount;

    /** 申请信息-实际支付金额/实际退款金额 */
    @TableField("SYS_SAEA_REAL_AMOUNT")
    private BigDecimal sysSaeaRealAmount;

    /** 申请信息-差额说明 */
    @TableField("SYS_SAEA_VARIANCE_DESC")
    private String sysSaeaVarianceDesc;

    /** 申请信息-支付登记人姓名/退款确认人姓名 */
    @TableField("SYS_SAEA_OPR_NAME")
    private String sysSaeaOprName;

    /** 申请信息-支付登记人编号/退款确认人编号 */
    @TableField("SYS_SAEA_OPR_CODE")
    private String sysSaeaOprCode;

    /** 申请信息-样品数量 */
    @TableField("SYS_SAEA_SAMPLE_QTY")
    private BigDecimal sysSaeaSampleQty;

}