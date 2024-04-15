package com.tadpole.cloud.product.modular.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 *  实体类
 * </p>
 *
 * @author S20210221
 * @since 2023-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_MANAGE_UP_RECORD")
@ExcelIgnoreUnannotated
public class RdManageUpRecord implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableField("SYS_TYPE")
    private String sysType;

    @TableField("SYS_L_DATE")
    private Date sysLDate;

    @TableField("SYS_PER_CODE")
    private String sysPerCode;

    @TableField("SYS_PER_NAME")
    private String sysPerName;

    @TableField("SYS_MODIFY_RECORD_NUM")
    private String sysModifyRecordNum;

    @TableField("SYS_MODIFY_CONTENT")
    private String sysModifyContent;

}