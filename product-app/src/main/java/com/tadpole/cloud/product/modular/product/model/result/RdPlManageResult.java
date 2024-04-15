package com.tadpole.cloud.product.modular.product.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 产品线管理 出参类
 * </p>
 *
 * @author S20210221
 * @since 2023-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("RD_PL_MANAGE")
public class RdPlManageResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("")
    private BigDecimal id;


    @ApiModelProperty("系统信息-产品线编号")
    @ExcelProperty(value ="产品线编号")
    private String sysPlCode;


    @ApiModelProperty("系统信息-创建时间")
    @ExcelProperty(value ="创建时间")
    private Date sysCDate;


    @ApiModelProperty("系统信息-最后更新时间")
    @ExcelProperty(value ="最后更新时间")
    private Date sysLDate;


    @ApiModelProperty("系统信息-员工编号")
    @ExcelProperty(value ="员工编号")
    private String sysPerCode;


    @ApiModelProperty("系统信息-员工姓名")
    @ExcelProperty(value ="员工姓名")
    private String sysPerName;


    @ApiModelProperty("系统信息-状态 值域{正常,禁用}")
    @ExcelProperty(value ="状态")
    private String sysTaLevel;

    /** 产品线设定-产品线名称 */
    @ApiModelProperty("产品线设定-运营大类")
    @ExcelProperty(value ="运营大类")
    private String sysPlName;


    @ApiModelProperty("产品线设定-产品经理编码")
    @ExcelProperty(value ="产品经理编码")
    private String sysPmPerCode;


    @ApiModelProperty("产品线设定-产品经理名称")
    @ExcelProperty(value ="产品经理名称")
    private String sysPmPerName;


    @ApiModelProperty("产品线设定-部门审核人编号")
    @ExcelProperty(value ="部门审核人编号")
    private String sysDeptExamPerCode;


    @ApiModelProperty("产品线设定-部门审核人名称")
    @ExcelProperty(value ="部门审核人名称")
    private String sysDeptExamPerName;


    @ApiModelProperty("产品线设定-审批人编号")
    @ExcelProperty(value ="审批人编号")
    private String sysApprPerCode;


    @ApiModelProperty("产品线设定-审批人名称")
    @ExcelProperty(value ="审批人名称")
    private String sysApprPerName;

    /** 系统信息-关闭时间 */
    @ApiModelProperty("系统信息-关闭时间")
    @ExcelProperty(value ="系统信息-关闭时间")
    private Date sysPlCloseDate;

    private List<RdPssManageResult> rdPssManageList;

}
