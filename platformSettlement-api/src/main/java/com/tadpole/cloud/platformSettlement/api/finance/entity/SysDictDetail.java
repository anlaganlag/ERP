package com.tadpole.cloud.platformSettlement.api.finance.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
* <p>
* 用户字典明细
* </p>
*
* @author gal
* @since 2021-10-29
*/
@Data
@TableName("CW_SYS_DICT_DETAIL")
public class SysDictDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value="ID",type= IdType.AUTO)
    private BigDecimal id;

    /** 字典id */
    @TableField("DICT_ID")
    private BigDecimal dictId;

    /** 字典值 */
    @TableField("DICT_VALUE")
    private String dictValue;

    /** 字典值 */
    @TableField("DICT_CODE")
    private String dictCode;


    /** 符号 */
    @TableField("SYMBOL")
    private String symbol;

    /** 备注 */
    @TableField("REMARK")
    private String remark;

}