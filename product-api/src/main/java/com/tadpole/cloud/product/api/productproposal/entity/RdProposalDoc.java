package com.tadpole.cloud.product.api.productproposal.entity;

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
 * 提案-提案管理-设计文档 实体类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_PROPOSAL_DOC")
@ExcelIgnoreUnannotated
public class RdProposalDoc implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统编号 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 提案编号 */
    @TableField("SYS_PER_NAME")
    private String sysPerName;

    /** 上传日期 */
    @TableField("SYS_TA_CODE")
    private Date sysTaCode;

    /** 上传人编号 */
    @TableField("SYS_C_DATE")
    private String sysCDate;

    /** 上传人姓名 */
    @TableField("SYS_L_DATE")
    private String sysLDate;

    /** 上传方式 */
    @TableField("SYS_DEPT_CODE")
    private String sysDeptCode;

    /** 设计文档文件名称 */
    @TableField("SYS_DEPT_NAME")
    private String sysDeptName;

    /** 设计文档文件路径 */
    @TableField("SYS_PER_CODE")
    private String sysPerCode;

}