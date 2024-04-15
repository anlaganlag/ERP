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
 * 提案-退货款记录 实体类
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
@TableName("RD_SAMPLE_RPR")
@ExcelIgnoreUnannotated
public class RdSampleRpr implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统信息-退款申请编号 */
   @TableId(value = "SYS_REF_CODE", type = IdType.ASSIGN_ID)
    private String sysRefCode;

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

    /** 退货申请信息-退货操作时间 */
    @TableField("SYS_REF_OP_DATE")
    private Date sysRefOpDate;

    /** 退货申请信息-退货数量 */
    @TableField("SYS_REF_QTY")
    private BigDecimal sysRefQty;

    /** 退货申请信息-预计退款金额 */
    @TableField("SYS_REF_PRE_AMOUNT")
    private BigDecimal sysRefPreAmount;

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

    /** 退款信息-差额说明 */
    @TableField("SYS_REF_VD")
    private String sysRefVd;

    /** 退款确认信息-支付宝账户 */
    @TableField("SYS_REF_ALIPAY_A")
    private String sysRefAlipayA;

    /** 退款确认信息-支付宝账户户名 */
    @TableField("SYS_REF_ALIPAY_AN")
    private String sysRefAlipayAn;

    /** 退款确认信息-退款日期 */
    @TableField("SYS_REF_DATE")
    private Date sysRefDate;

    /** 退款确认信息-实际退款金额 */
    @TableField("SYS_REF_REAL_AMOUNT")
    private BigDecimal sysRefRealAmount;

    /** 退款确认信息-退款登记时间 */
    @TableField("SYS_REF_RG_DATE")
    private Date sysRefRgDate;

    /** 退款确认信息-退款确认人编号 */
    @TableField("SYS_REF_RG_PER_CODE")
    private String sysRefRgPerCode;

    /** 退款确认信息-退款确认人姓名 */
    @TableField("SYS_REF_RG_PER_NAME")
    private String sysRefRgPerName;

}