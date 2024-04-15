package com.tadpole.cloud.platformSettlement.api.finance.entity;

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
*
* </p>
*
* @author ty
* @since 2022-05-17
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LX_SHOP_SYN_RECORD")
@ExcelIgnoreUnannotated
public class LxShopSynRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @TableField("SID")
    @ExcelProperty(value ="")
    private BigDecimal sid;

    @TableField("SYN_TYPE")
    @ExcelProperty(value ="")
    private String synType;

    @TableField("SYN_DATE")
    @ExcelProperty(value ="")
    private String synDate;

    @TableField("SYN_STATUS")
    @ExcelProperty(value ="")
    private String synStatus;

    @TableField("CREATE_DATE")
    @ExcelProperty(value ="")
    private Date createDate;

    @TableField("UPDATE_DATE")
    @ExcelProperty(value ="")
    private Date updateDate;

}