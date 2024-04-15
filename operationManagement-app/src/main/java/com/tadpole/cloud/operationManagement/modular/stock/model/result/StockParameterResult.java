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
@ExcelIgnoreUnannotated
@TableName("STOCK_PARAMETER")
public class StockParameterResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 数据id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 参数名称 */
    @ApiModelProperty("PARAMETER_NAME")
    private String parameterName;

    /** 参数编码 */
    @ApiModelProperty("PARAMETER_CODE")
    private String parameterCode;

    /** 参数值 */
    @ApiModelProperty("PARAMETER_VALUE")
    private String parameterValue;

    /** 成熟度范围 */
    @ApiModelProperty("MATURITY_RANGE")
    private String maturityRange;

    /** 备注 */
    @ApiModelProperty("REMARK")
    private String remark;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private Date createBy;

    /** 更新人 */
    @ApiModelProperty("UPDATE_BY")
    private Date updateBy;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

}