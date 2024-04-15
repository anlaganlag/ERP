package com.tadpole.cloud.externalSystem.modular.ebms.model.param;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * TbDWSourceData;TbDWSourceData
 * @author : LSY
 * @date : 2023-8-14
 */
@ApiModel(value = "TbDWSourceData",description = "TbDWSourceData")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbDwSourceDataParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** 数据对象编号 */
    @ApiModelProperty(value = "数据对象编号")
    private String dwDataObjNum ;
    
    /** 平台编号 */
    @ApiModelProperty(value = "平台编号")
    private String dwPlatNum ;
    
    /** 平台名称 */
    @ApiModelProperty(value = "平台名称")
    private String dwPlatName ;
    
    /** 分类编号 */
    @ApiModelProperty(value = "分类编号")
    private String dwCategoryId ;
    
    /** 分类名称 */
    @ApiModelProperty(value = "分类名称")
    private String dwCateGoryName ;
    
    /** 数据对象英文名 */
    @ApiModelProperty(value = "数据对象英文名")
    private String dwDataObjEnName ;
    
    /** 数据对象中文名 */
    @ApiModelProperty(value = "数据对象中文名")
    private String dwDataObjCnName ;
    
    /** 数据对象数据表 */
    @ApiModelProperty(value = "数据对象数据表")
    private String dwDataTableObj ;
    
    /** 下载规则类型 */
    @ApiModelProperty(value = "下载规则类型")
    private String dwDlType ;
    
    /** 下载规则具体日 */
    @ApiModelProperty(value = "下载规则具体日")
    private String dwDlExeactDate ;
    
    /** 下载预计完成时间 */
    @ApiModelProperty(value = "下载预计完成时间")
    private String dwDlComplateTime ;
    
    /** 存储规则方式 */
    @ApiModelProperty(value = "存储规则方式")
    private String dwSaveType ;
    
    /** 去重标识 */
    @ApiModelProperty(value = "去重标识")
    private String dwHandleDetInfo ;
    
    /** 数据对象创建日期 */
    @ApiModelProperty(value = "数据对象创建日期")
    private Date dwCreateDate ;
    
    /** 数据对象类型
区分时间段 dwDlTime=n>0 下载当前时间起往前n天
分区间段 dwDlTime=n>0 下载前n天凌晨0.-24. */
    @ApiModelProperty(value = "数据对象类型 区分时间段 dwDlTime=n>0 下载当前时间起往前n天 分区间段 dwDlTime=n>0 下载前n天凌晨0.-24.")
    private String dwType ;
    
    /** 报告类型枚举值 */
    @ApiModelProperty(value = "报告类型枚举值")
    private String dwDataReportTypeEnum ;
    
    /** 是否计划类报告 */
    @ApiModelProperty(value = "是否计划类报告")
    private BigDecimal dwIsSchedule ;
    
    /** 报告下载时长 */
    @ApiModelProperty(value = "报告下载时长")
    private BigDecimal dwDltime ;
    
    /** 报告任务生成时间点 */
    @ApiModelProperty(value = "报告任务生成时间点")
    private String dwTaskCreateTime ;
    
    /** 1 启用2 弃用 */
    @ApiModelProperty(value = "1 启用 2 弃用")
    private BigDecimal status ;
    
    /** 报告支持下载的区域
ALL
NA
EU
FE */
    @ApiModelProperty(value = "报告支持下载的区域 ALL NA EU FE")
    private String region ;
    

}