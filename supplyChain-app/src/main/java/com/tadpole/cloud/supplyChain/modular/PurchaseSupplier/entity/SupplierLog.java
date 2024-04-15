package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 * 供应商-日志 实体类
 * </p>
 *
 * @author S20190109
 * @since 2023-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SUPPLIER_LOG")
@ExcelIgnoreUnannotated
public class SupplierLog implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统编号 */
    @TableId(value = "ID", type = IdType.AUTO)
    @TableField("ID")
    private long ID;

    /** 供应商编号 */
    @TableField("SYS_SUP_CODE")
    private String sysSupCode;

    /** 更新种类 值域{"基本信息","财务信息","账户信息","合同模板","管理信息","联系人信息","其它信息"} */
    @TableField("SYS_UPDATE_TYPE")
    private String sysUpdateType;

    /** 更新时间 */
    @TableField("SYS_UPDATE_DATE")
    private Date sysUpdateDate;

    /** 更新内容 */
    @TableField("SYS_UPDATE_CONTENT")
    private String sysUpdateContent;

    /** 变更人姓名 */
    @TableField("SYS_UPDATE_PER_NAME")
    private String sysUpdatePerName;

    /** 变更人编号 */
    @TableField("SYS_UPDATE_PER_CODE")
    private String sysUpdatePerCode;

    /** 部门审核时间 */
    @TableField("SYS_DEPT_EXAM_DATE")
    private Date sysDeptExamDate;

    /** 部门审核人姓名 */
    @TableField("SYS_DEPT_EXAM_PER_NAME")
    private String sysDeptExamPerName;

    /** 部门审核人编号 */
    @TableField("SYS_DEPT_EXAM_PER_CODE")
    private String sysDeptExamPerCode;

    /** 资质审核时间 */
    @TableField("SYS_QUAL_EXAM_DATE")
    private Date sysQualExamDate;

    /** 资质审核人姓名 */
    @TableField("SYS_QUAL_EXAM_PER_NAME")
    private String sysQualExamPerName;

    /** 资质审核人编号 */
    @TableField("SYS_QUAL_EXAM_PER_CODE")
    private String sysQualExamPerCode;

    /** 审批时间 */
    @TableField("SYS_APPR_DATE")
    private Date sysApprDate;

    /** 审批人姓名 */
    @TableField("SYS_APPR_PER_NAME")
    private String sysApprPerName;

    /** 审批人编号 */
    @TableField("SYS_APPR_PER_CODE")
    private String sysApprPerCode;

}