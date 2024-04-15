package com.tadpole.cloud.product.modular.product.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 产品线管理 实体类
 * </p>
 *
 * @author S20210221
 * @since 2023-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_PL_MANAGE")
@ExcelIgnoreUnannotated
public class RdPlManage implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "ID", type = IdType.AUTO)
    @TableField("ID")
    @ApiModelProperty("产品线编号")
    private BigDecimal id;

    /** 系统信息-产品线编号 */
    @TableField("SYS_PL_CODE")
    @ApiModelProperty("产品线编号")
    private String sysPlCode;

    /** 系统信息-创建时间 */
    @TableField("SYS_C_DATE")
    @ApiModelProperty("创建时间")
    private Date sysCDate;

    /** 系统信息-最后更新时间 */
    @TableField("SYS_L_DATE")
    @ApiModelProperty("最后更新时间")
    private Date sysLDate;

    /** 系统信息-员工编号 */
    @TableField("SYS_PER_CODE")
    @ApiModelProperty("员工编号")
    private String sysPerCode;

    /** 系统信息-员工姓名 */
    @TableField("SYS_PER_NAME")
    @ApiModelProperty("员工姓名")
    private String sysPerName;

    /** 系统信息-状态 值域{"正常","禁用"} */
    @TableField("SYS_TA_LEVEL")
    @ApiModelProperty("状态")
    private String sysTaLevel;

    /** 产品线设定-产品线名称 */
    @TableField("SYS_PL_NAME")
    @ApiModelProperty("产品线名称")
    private String sysPlName;

    /** 产品线设定-产品经理编码 */
    @TableField("SYS_PM_PER_CODE")
    @ApiModelProperty("产品经理编码")
    private String sysPmPerCode;

    /** 产品线设定-产品经理名称 */
    @TableField("SYS_PM_PER_NAME")
    @ApiModelProperty("产品经理名称")
    private String sysPmPerName;

    /** 产品线设定-部门审核人编号 */
    @TableField("SYS_DEPT_EXAM_PER_CODE")
    @ApiModelProperty("部门审核人编号")
    private String sysDeptExamPerCode;

    /** 产品线设定-部门审核人名称 */
    @TableField("SYS_DEPT_EXAM_PER_NAME")
    @ApiModelProperty("部门审核人名称")
    private String sysDeptExamPerName;

    /** 产品线设定-审批人编号 */
    @TableField("SYS_APPR_PER_CODE")
    @ApiModelProperty("审批人编号")
    private String sysApprPerCode;

    /** 产品线设定-审批人名称 */
    @TableField("SYS_APPR_PER_NAME")
    @ApiModelProperty("审批人名称")
    private String sysApprPerName;

    /** 系统信息-关闭时间 */
    @TableField("SYS_PL_CLOSE_DATE")
    @ApiModelProperty("关闭时间")
    private Date sysPlCloseDate;



}