package com.tadpole.cloud.product.modular.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 * 产品同款版本 实体类
 * </p>
 *
 * @author S20210221
 * @since 2024-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_PSS_MANAGE_VERSION")
@ExcelIgnoreUnannotated
public class RdPssManageVersion implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 系统编号 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 系统信息-SPU */
    @TableField("SYS_SPU")
    private String sysSpu;

    /** 同款设定-当前应用版本 */
    @TableField("SYS_CUR_APP_VERSION")
    private String sysCurAppVersion;

    /** 同款设定-当前迭代版本 */
    @TableField("SYS_CUR_ITE_VERSION")
    private String sysCurIteVersion;

    /** 同款设定-状态 值域{"进行中","已落地"} */
    @TableField("SYS_VERSION_STATUS")
    private String sysVersionStatus;

}