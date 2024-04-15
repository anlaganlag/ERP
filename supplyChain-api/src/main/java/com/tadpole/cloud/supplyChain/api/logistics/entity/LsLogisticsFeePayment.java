package com.tadpole.cloud.supplyChain.api.logistics.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 物流费付款 实体类
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
@TableName("LS_LOGISTICS_FEE_PAYMENT")
@ExcelIgnoreUnannotated
public class LsLogisticsFeePayment implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 物流费编号 */
    @TableField("LOGISTICS_FEE_NO")
    private String logisticsFeeNo;

    /** 物流商编码 */
    @TableField("LP_CODE")
    private String lpCode;

    /** 物流商名称 */
    @TableField("LP_NAME")
    private String lpName;

    /** 物流商简称 */
    @TableField("LP_SIMPLE_NAME")
    private String lpSimpleName;

    /** 物流费 */
    @TableField("TOTAL_LOGISTICS_FEE")
    private BigDecimal totalLogisticsFee;

    /** 税费 */
    @TableField("TOTAL_TAX_FEE")
    private BigDecimal totalTaxFee;

    /** 付款费用 */
    @TableField("TOTAL_PAYMENT_FEE")
    private BigDecimal totalPaymentFee;

    /** 付款申请状态：未申请，申请成功，申请失败 */
    @TableField("PAYMENT_APPLY_STATUS")
    private String paymentApplyStatus;

    /** ERP申请日期 */
    @TableField("ERP_APPLY_DATE")
    private Date erpApplyDate;

    /** 付款申请编号 */
    @TableField("PAYMENT_APPLY_NO")
    private String paymentApplyNo;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @TableField("CREATE_USER")
    private String createUser;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @TableField("UPDATE_USER")
    private String updateUser;

}