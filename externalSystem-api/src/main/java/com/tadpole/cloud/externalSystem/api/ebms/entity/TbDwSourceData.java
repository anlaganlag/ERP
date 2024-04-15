package com.tadpole.cloud.externalSystem.api.ebms.entity;

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
 * @desc : TbDWSourceData-TbDWSourceData
 */
@TableName("TbDWSourceData")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbDwSourceData implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 数据对象编号 */
    @TableId(value = "dwDataObjNum", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "数据对象编号")
    private String dwDataObjNum ;
    
    /** 平台编号 */
    @TableField("dwPlatNum")
    @ApiModelProperty(value = "平台编号")
    private String dwPlatNum ;
    
    /** 平台名称 */
    @TableField("dwPlatName")
    @ApiModelProperty(value = "平台名称")
    private String dwPlatName ;
    
    /** 分类编号 */
    @TableField("dwCategoryID")
    @ApiModelProperty(value = "分类编号")
    private String dwCategoryId ;
    
    /** 分类名称 */
    @TableField("dwCateGoryName")
    @ApiModelProperty(value = "分类名称")
    private String dwCateGoryName ;
    
    /** 数据对象英文名 */
    @TableField("dwDataObjEnName")
    @ApiModelProperty(value = "数据对象英文名")
    private String dwDataObjEnName ;
    
    /** 数据对象中文名 */
    @TableField("dwDataObjCnName")
    @ApiModelProperty(value = "数据对象中文名")
    private String dwDataObjCnName ;
    
    /** 数据对象数据表 */
    @TableField("dwDataTableObj")
    @ApiModelProperty(value = "数据对象数据表")
    private String dwDataTableObj ;
    
    /** 下载规则类型 */
    @TableField("dwDlType")
    @ApiModelProperty(value = "下载规则类型")
    private String dwDlType ;
    
    /** 下载规则具体日 */
    @TableField("dwDlExeactDate")
    @ApiModelProperty(value = "下载规则具体日")
    private String dwDlExeactDate ;
    
    /** 下载预计完成时间 */
    @TableField("dwDlComplateTime")
    @ApiModelProperty(value = "下载预计完成时间")
    private String dwDlComplateTime ;
    
    /** 存储规则方式 */
    @TableField("dwSaveType")
    @ApiModelProperty(value = "存储规则方式")
    private String dwSaveType ;
    
    /** 去重标识 */
    @TableField("dwHandleDetInfo")
    @ApiModelProperty(value = "去重标识")
    private String dwHandleDetInfo ;
    
    /** 数据对象创建日期 */
    @TableField("dwCreateDate")
    @ApiModelProperty(value = "数据对象创建日期")
    private Date dwCreateDate ;
    
    /** 数据对象类型
区分时间段 dwDlTime=n>0 下载当前时间起往前n天
分区间段 dwDlTime=n>0 下载前n天凌晨0.-24. */
    @TableField("dwType")
    @ApiModelProperty(value = "数据对象类型 区分时间段 dwDlTime=n>0 下载当前时间起往前n天 分区间段 dwDlTime=n>0 下载前n天凌晨0.-24.")
    private String dwType ;
    
    /** 报告类型枚举值 */
    @TableField("dwDataReportTypeEnum")
    @ApiModelProperty(value = "报告类型枚举值")
    private String dwDataReportTypeEnum ;
    
    /** 是否计划类报告 */
    @TableField("dwIsSchedule")
    @ApiModelProperty(value = "是否计划类报告")
    private BigDecimal dwIsSchedule ;
    
    /** 报告下载时长 */
    @TableField("dwDltime")
    @ApiModelProperty(value = "报告下载时长")
    private BigDecimal dwDltime ;
    
    /** 报告任务生成时间点 */
    @TableField("dwTaskCreateTime")
    @ApiModelProperty(value = "报告任务生成时间点")
    private String dwTaskCreateTime ;
    
    /** 1 启用
2 弃用 */
    @TableField("status")
    @ApiModelProperty(value = "1 启用 2 弃用")
    private Integer status ;
    
    /** 报告支持下载的区域
ALL
NA
EU
FE */
    @TableField("region")
    @ApiModelProperty(value = "报告支持下载的区域 ALL NA EU FE")
    private String region ;

    /** 报告下载合并维度;0 按站点下载 1 按区域合并下载，(按区域下载时：默认保存的站点 NA：US， EU：DE， FE ：JP ) */
    @TableField("dwMerge")
    @ApiModelProperty(value = "报告下载合并维度;0 按站点下载 1 按区域合并下载，(按区域下载时：默认保存的站点 NA：US， EU：DE， FE ：JP )")
    private Integer dwMerge ;
    

}