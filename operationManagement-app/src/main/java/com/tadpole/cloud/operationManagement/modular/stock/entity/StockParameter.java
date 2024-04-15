package com.tadpole.cloud.operationManagement.modular.stock.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 备货通用参数
    * </p>
*
* @author cyt
* @since 2022-07-19
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("STOCK_PARAMETER")
@ExcelIgnoreUnannotated
public class StockParameter implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 数据id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 参数名称 */
    @TableField("PARAMETER_NAME")
    private String parameterName;

    /** 参数编码 */
    @TableField("PARAMETER_CODE")
    private String parameterCode;

    /** 参数值 */
    @TableField("PARAMETER_VALUE")
    private String parameterValue;

    /** 成熟度范围 */
    @TableField("MATURITY_RANGE")
    private String maturityRange;

    /** 备注 */
    @TableField("REMARK")
    private String remark;

    /** 创建人 */
    @TableField("CREATE_BY")
    private String createBy;

    /** 更新人 */
    @TableField("UPDATE_BY")
    private String updateBy;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

}