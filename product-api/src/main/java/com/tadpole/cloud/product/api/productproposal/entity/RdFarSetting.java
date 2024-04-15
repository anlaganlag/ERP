package com.tadpole.cloud.product.api.productproposal.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 * 提案-设置-研发费用自动过审设置 实体类
 * </p>
 *
 * @author S20190096
 * @since 2023-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_FAR_SETTING")
@ExcelIgnoreUnannotated
public class RdFarSetting implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统编号 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 系统实时时间 */
    @TableField("SYS_C_DATE")
    private Date sysCDate;

    /** 系统实时时间 */
    @TableField("SYS_L_DATE")
    private Date sysLDate;

    /** 登陆用户员工编号 */
    @TableField("SYS_PER_CODE")
    private String sysPerCode;

    /** 登陆用户员工姓名 */
    @TableField("SYS_PER_NAME")
    private String sysPerName;

    /** 拿样方式 值域{“现货拿样”,"定制拿样"} */
    @TableField("SYS_SAMPLE_METHOD")
    private String sysSampleMethod;

    /** 审核环节 */
    @TableField("SYS_AUDIT_PROCESS")
    private String sysAuditProcess;

    /** 单次申请金额 */
    @TableField("SYS_SINGLE_APP_AMOUNT")
    private BigDecimal sysSingleAppAmount;

    /** 累积申请金额 */
    @TableField("SYS_APP_TOTAL_AMOUNT")
    private BigDecimal sysAppTotalAmount;

    /** 任务次数 */
    @TableField("SYS_TASK_QTY")
    private BigDecimal sysTaskQty;

}