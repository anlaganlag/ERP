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
 * 提案-开发样任务进度明细 实体类
 * </p>
 *
 * @author S20190096
 * @since 2024-02-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_SAMPLE_TASK_PROGRESS_DET")
@ExcelIgnoreUnannotated
public class RdSampleTaskProgressDet implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统信息-系统编号 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 系统信息-拿样任务编号 */
    @TableField("SYS_TS_TASK_CODE")
    private String sysTsTaskCode;

    /** 系统信息-创建时间 */
    @TableField("SYS_C_DATE")
    private Date sysCDate;

    /** 系统信息-员工编号 */
    @TableField("SYS_PER_CODE")
    private String sysPerCode;

    /** 系统信息-员工姓名 */
    @TableField("SYS_PER_NAME")
    private String sysPerName;

    /** 系统信息-明细内容 */
    @TableField("SYS_TS_CONTENT")
    private String sysTsContent;

}