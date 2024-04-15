package com.tadpole.cloud.externalSystem.modular.ebms.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * @author : LSY
 * @date : 2023-8-14
 * @desc : TbDWAnalyRule-TbDWAnalyRule
 */
@TableName("Tb_DW_Analy_Rule")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbDwAnalyRule implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 系统编号 */
    @TableId(value = "sys_Dw_Rule_ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "系统编号")
    private BigDecimal sysDwRuleId ;
    
    /** 数据对象编号 */
    @TableField("dw_Data_Obj_Num")
    @ApiModelProperty(value = "数据对象编号")
    private String dwDataObjNum ;
    
    /** 数据对象数据表 */
    @TableField("dw_Data_Table_Obj")
    @ApiModelProperty(value = "数据对象数据表")
    private String dwDataTableObj ;
    
    /** 报告列名 */
    @TableField("dw_Report_Property")
    @ApiModelProperty(value = "报告列名")
    private String dwReportProperty ;
    
    /** 源仓表字段名 */
    @TableField("dw_Unity_Property")
    @ApiModelProperty(value = "源仓表字段名")
    private String dwUnityProperty ;
    
    /** 实体对象属性名 */
    @TableField("dw_Model_Name")
    @ApiModelProperty(value = "实体对象属性名")
    private String dwModelName ;
    
    /** 中文名称 */
    @TableField("dw_Comments")
    @ApiModelProperty(value = "中文名称")
    private String dwComments ;
    
    /** 标注 */
    @TableField("dw_Remark")
    @ApiModelProperty(value = "标注")
    private String dwRemark ;
    
    /** 状态 */
    @TableField("dw_Status")
    @ApiModelProperty(value = "状态")
    private BigDecimal dwStatus ;
    
    /** 创建时间 */
    @TableField("dw_Create_Time")
    @ApiModelProperty(value = "创建时间")
    private Date dwCreateTime ;
    
    /** 最后更新时间 */
    @TableField("dw_Last_Update_Time")
    @ApiModelProperty(value = "最后更新时间")
    private Date dwLastUpdateTime ;
    

}