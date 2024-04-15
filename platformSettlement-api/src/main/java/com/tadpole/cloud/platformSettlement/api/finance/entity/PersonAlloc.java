package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * ;
 * @author : LSY
 * @date : 2023-12-19
 */
@TableName("PERSON_ALLOC")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class PersonAlloc implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** id */
    @ApiModelProperty("id")
    @ExcelProperty(value="id")
    @TableField("ID")
    private String id ;
 
    /** 期间 */
    @ApiModelProperty("期间")
    @ExcelProperty(value="期间")
    @TableField("PERIOD")
    private String period ;
 
    /**  */

 
    /** 一级部门 */
    @ApiModelProperty("一级部门")
    @ExcelProperty(value="一级部门")
    @TableField("DEPT1")
    private String dept1 ;
 
    /** 二级部门 */
    @ApiModelProperty("二级部门")
    @ExcelProperty(value="二级部门")
    @TableField("DEPT2")
    private String dept2 ;
 
    /** 三级部门 */
    @ApiModelProperty("三级部门")
    @ExcelProperty(value="三级部门")
    @TableField("DEPT3")
    private String dept3 ;
 
    /** 四级部门 */
    @ApiModelProperty("四级部门")
    @ExcelProperty(value="四级部门")
    @TableField("DEPT4")
    private String dept4 ;
 
    /** 工号 */
    @ApiModelProperty("工号")
    @ExcelProperty(value="工号")
    @TableField("PERSON_CODE")
    private String personCode ;
 
    /** 姓名 */
    @ApiModelProperty("姓名")
    @ExcelProperty(value="姓名")
    @TableField("PERSON_NAME")
    private String personName ;
 
    /** 岗位 */
    @ApiModelProperty("岗位")
    @ExcelProperty(value="岗位")
    @TableField("POSITION")
    private String position ;
 
    /** 员工状态 */
    @ApiModelProperty("员工状态")
    @ExcelProperty(value="员工状态")
    @TableField("STATUS")
    private String status ;
 
    /** 平台 */
    @ApiModelProperty("平台")
    @ExcelProperty(value="平台")
    @TableField("PLATFORM")
    private String platform ;
 
    /** 待分摊三级部门 */
    @ApiModelProperty("待分摊三级部门")
    @ExcelProperty(value="待分摊三级部门")
    @TableField("DEPT3_ALLOC")
    private String dept3Alloc ;
 
    /** 确认状态 */
    @ApiModelProperty("确认状态")
    @ExcelProperty(value="确认状态")
    @TableField("CONFIRM")
    private String confirm ;
 
    /**  */
    @ApiModelProperty("")
    @ExcelProperty(value="更新时间")
    @TableField("UPDATE_TIME")
    private Date updateTime ;
 
    /**  */
    @ApiModelProperty("")
    @ExcelProperty(value="更新人")
    @TableField("UPDATE_BY")
    private String updateBy ;
 
    /**  */
    @ApiModelProperty("")
    @ExcelProperty(value="创建时间")
    @TableField("CREATE_TIME")
    private Date createTime ;
 
    /**  */
    @ApiModelProperty("")
    @ExcelProperty(value="创建人")
    @TableField("CREATE_BY")
    private String createBy ;

   @TableField(exist = false)
   private String allocDimension;

   public String getAllocDimension() {
      return period + dept3;
   }

   private String confirmBy ;
   private Date confirmDate ;



}