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
    * 日常备货流程审核节点参数设置
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
@TableName("STOCK_APPROVALTIME_PARAMETER")
@ExcelIgnoreUnannotated
public class StockApprovaltimeParameter implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 数据id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 审核节点 */
    @TableField("AUDITOR_NAME")
    private String auditorName;

    /** 审核节点编码 */
    @TableField("AUDITOR_CODE")
    private String auditorCode;

    /** 参数天数 */
    @TableField("PARAMETER_DAY")
    private BigDecimal parameterDay;

    /** 参数时间 */
    @TableField("PARAMETER_TIME")
    private String parameterTime;

    /** 参数结果 */
    @TableField("PARAMETER_RESULT")
    private String parameterResult;

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