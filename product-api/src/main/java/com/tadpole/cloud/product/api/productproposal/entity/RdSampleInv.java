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
 * 提案-开发样盘点 实体类
 * </p>
 *
 * @author S20190096
 * @since 2024-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_SAMPLE_INV")
@ExcelIgnoreUnannotated
public class RdSampleInv implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统信息-盘点编号 */
    @TableId(value = "SYS_INV_CODE", type = IdType.ASSIGN_ID)
    private String sysInvCode;

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

    /** 系统信息-盘点开始时间 */
    @TableField("SYS_INV_START_DATE")
    private Date sysInvStartDate;

    /** 系统信息-盘点结束时间 */
    @TableField("SYS_INV_END_DATE")
    private Date sysInvEndDate;

    /** 系统信息-盘点负责人编号 */
    @TableField("SYS_INV_PER_CODE")
    private String sysInvPerCode;

    /** 系统信息-盘点负责人姓名 */
    @TableField("SYS_INV_PER_NAME")
    private String sysInvPerName;

    /** 系统信息-盘点状态 值域{“盘点中”,"已完结"} */
    @TableField("SYS_INV_STATUS")
    private String sysInvStatus;

}