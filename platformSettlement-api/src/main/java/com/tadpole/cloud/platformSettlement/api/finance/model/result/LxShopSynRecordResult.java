package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.*;
import com.baomidou.mybatisplus.annotation.TableName;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
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
@ExcelIgnoreUnannotated
@TableName("LX_SHOP_SYN_RECORD")
public class LxShopSynRecordResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @TableField("SID")
    @ExcelProperty(value ="")
    private BigDecimal sid;

    @ApiModelProperty("SYN_TYPE")
    @ExcelProperty(value ="")
    private String synType;

    @ApiModelProperty("SYN_DATE")
    @ExcelProperty(value ="")
    private String synDate;

    @ApiModelProperty("SYN_STATUS")
    @ExcelProperty(value ="")
    private String synStatus;

    @ApiModelProperty("CREATE_DATE")
    @ExcelProperty(value ="")
    private Date createDate;

    @ApiModelProperty("UPDATE_DATE")
    @ExcelProperty(value ="")
    private Date updateDate;

}