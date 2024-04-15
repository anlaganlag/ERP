package com.tadpole.cloud.platformSettlement.api.finance.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
* <p>
* 用户字典表
* </p>
*
* @author gal
* @since 2021-10-29
*/
@Data
@TableName("CW_SYS_DICT")
public class CwSysDict implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @TableField("ID")
    private BigDecimal id;

    /** 业务模块 */
    @TableField("MODULE_NAME")
    private String moduleName;

    /** 字典名称 */
    @TableField("DICT_NAME")
    private String dictName;

    /** 字典编码 */
    @TableField("DICT_CODE")
    private String dictCode;

}