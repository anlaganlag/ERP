package com.tadpole.cloud.platformSettlement.modular.finance.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import oracle.sql.NUMBER;

import java.io.Serializable;
import java.util.Date;

/**
 * ;
 * @author : LSY
 * @date : 2023-12-19
 */
@ApiModel(value = "",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class PersonAllocResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** id */
    @ApiModelProperty(value = "id")
    @ExcelProperty(value ="id")
    private String id ;
 
    /** 期间 */
    @ApiModelProperty(value = "期间")
    @ExcelProperty(value ="期间")
    private String period ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String site ;
 
    /** 一级部门 */
    @ApiModelProperty(value = "一级部门")
    @ExcelProperty(value ="一级部门")
    private String dept1 ;
 
    /** 二级部门 */
    @ApiModelProperty(value = "二级部门")
    @ExcelProperty(value ="二级部门")
    private String dept2 ;
 
    /** 三级部门 */
    @ApiModelProperty(value = "三级部门")
    @ExcelProperty(value ="三级部门")
    private String dept3 ;
 
    /** 四级部门 */
    @ApiModelProperty(value = "四级部门")
    @ExcelProperty(value ="四级部门")
    private String dept4 ;
 
    /** 工号 */
    @ApiModelProperty(value = "工号")
    @ExcelProperty(value ="工号")
    private String personCode ;
 
    /** 姓名 */
    @ApiModelProperty(value = "姓名")
    @ExcelProperty(value ="姓名")
    private String personName ;
 
    /** 岗位 */
    @ApiModelProperty(value = "岗位")
    @ExcelProperty(value ="岗位")
    private String position ;
 
    /** 员工状态 */
    @ApiModelProperty(value = "员工状态")
    @ExcelProperty(value ="员工状态")
    private String status ;
 
    /** 平台 */
    @ApiModelProperty(value = "平台")
    @ExcelProperty(value ="平台")
    private String platform ;
 
    /** 待分摊三级部门 */
    @ApiModelProperty(value = "待分摊三级部门")
    @ExcelProperty(value ="待分摊三级部门")
    private String dept3Alloc ;
 
    /** 确认状态 */
    @ApiModelProperty(value = "确认状态")
    @ExcelProperty(value ="确认状态")
    private String confirm ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private Date updateTime ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String updateBy ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private Date createTime ;
 
    /**  */
    @ApiModelProperty(value = "")
    @ExcelProperty(value ="")
    private String createBy ;


}