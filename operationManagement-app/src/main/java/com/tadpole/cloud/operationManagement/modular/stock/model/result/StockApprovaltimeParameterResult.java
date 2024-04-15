package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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
@ExcelIgnoreUnannotated
@TableName("STOCK_APPROVALTIME_PARAMETER")
public class StockApprovaltimeParameterResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 数据id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 审核节点 */
    @ApiModelProperty("AUDITOR_NAME")
    private String auditorName;

    /** 审核节点编码 */
    @ApiModelProperty("AUDITOR_CODE")
    private String auditorCode;

    /** 参数天数 */
    @ApiModelProperty("PARAMETER_DAY")
    private String parameterDay;

    /** 参数时间 */
    @ApiModelProperty("PARAMETER_TIME")
    private String parameterTime;

    /** 参数结果 */
    @ApiModelProperty("PARAMETER_RESULT")
    private String parameterResult;

    /** 备注 */
    @ApiModelProperty("REMARK")
    private String remark;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 更新人 */
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

}