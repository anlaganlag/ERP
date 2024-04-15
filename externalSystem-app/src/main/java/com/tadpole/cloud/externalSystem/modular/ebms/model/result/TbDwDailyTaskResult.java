package com.tadpole.cloud.externalSystem.modular.ebms.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * TBDWDailyTask;TBDWDailyTask
 * @author : LSY
 * @date : 2023-8-14
 */
@ApiModel(value = "TBDWDailyTask",description = "TBDWDailyTask")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbDwDailyTaskResult  implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 每日任务编号 */
    @ApiModelProperty(value = "每日任务编号")
    @ExcelProperty(value ="每日任务编号")
    private BigDecimal sysDwDailyId ;
    
    /** 任务名称 */
    @ApiModelProperty(value = "任务名称")
    @ExcelProperty(value ="任务名称")
    private String dwdailyTaskName ;
    
    /** 数据对象编号 */
    @ApiModelProperty(value = "数据对象编号")
    @ExcelProperty(value ="数据对象编号")
    private String dwDataObjNum ;
    
    /** 数据对象英文名 */
    @ApiModelProperty(value = "数据对象英文名")
    @ExcelProperty(value ="数据对象英文名")
    private String dwDataObjEnName ;
    
    /** 数据对象数据表 */
    @ApiModelProperty(value = "数据对象数据表")
    @ExcelProperty(value ="数据对象数据表")
    private String dwDataTableObj ;
    
    /** 下载规则类型 */
    @ApiModelProperty(value = "下载规则类型")
    @ExcelProperty(value ="下载规则类型")
    private String dwDlType ;
    
    /** 下载规则具体日 */
    @ApiModelProperty(value = "下载规则具体日")
    @ExcelProperty(value ="下载规则具体日")
    private String dwDlExeactDate ;
    
    /** 下载预计完成时间 */
    @ApiModelProperty(value = "下载预计完成时间")
    @ExcelProperty(value ="下载预计完成时间")
    private String dwDlComplateTime ;
    
    /** 存储规则方式 */
    @ApiModelProperty(value = "存储规则方式")
    @ExcelProperty(value ="存储规则方式")
    private String dwSaveType ;
    
    /** 去重标识 */
    @ApiModelProperty(value = "去重标识")
    @ExcelProperty(value ="去重标识")
    private String dwHandleDetInfo ;
    
    /** SellerID */
    @ApiModelProperty(value = "SellerID")
    @ExcelProperty(value ="SellerID")
    private String dwSellerId ;
    
    /** 账号 */
    @ApiModelProperty(value = "账号")
    @ExcelProperty(value ="账号")
    private String dwShopNameSimple ;
    
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @ExcelProperty(value ="站点")
    private String dwCountryCode ;
    
    /** 负责人编号 */
    @ApiModelProperty(value = "负责人编号")
    @ExcelProperty(value ="负责人编号")
    private String dwDlPerson ;
    
    /** 负责人姓名 */
    @ApiModelProperty(value = "负责人姓名")
    @ExcelProperty(value ="负责人姓名")
    private String dwDlPersonName ;
    
    /** 使用部门 */
    @ApiModelProperty(value = "使用部门")
    @ExcelProperty(value ="使用部门")
    private String dwUseDepart ;
    
    /** 任务生成时间 */
    @ApiModelProperty(value = "任务生成时间")
    @ExcelProperty(value ="任务生成时间")
    private Date dwTaskCreateTime ;
    
    /** 文件上传状态 */
    @ApiModelProperty(value = "文件上传状态")
    @ExcelProperty(value ="文件上传状态")
    private String dwFileUploadStatus ;
    
    /** 文件上传路径 */
    @ApiModelProperty(value = "文件上传路径")
    @ExcelProperty(value ="文件上传路径")
    private String dwFileUploadPath ;
    
    /** 文件上传时间 */
    @ApiModelProperty(value = "文件上传时间")
    @ExcelProperty(value ="文件上传时间")
    private Date dwFileUploadTime ;
    
    /** 文件上传结果 */
    @ApiModelProperty(value = "文件上传结果")
    @ExcelProperty(value ="文件上传结果")
    private String dwFileUploadResult ;
    
    /** 文件处理状态 */
    @ApiModelProperty(value = "文件处理状态")
    @ExcelProperty(value ="文件处理状态")
    private String dwFileHandleStatus ;
    
    /** 文件处理时间 */
    @ApiModelProperty(value = "文件处理时间")
    @ExcelProperty(value ="文件处理时间")
    private Date dwFileHandleTime ;
    
    /** 文件处理结果 */
    @ApiModelProperty(value = "文件处理结果")
    @ExcelProperty(value ="文件处理结果")
    private String dwFileHandleResult ;
    
    /** 数据对象类型 */
    @ApiModelProperty(value = "数据对象类型")
    @ExcelProperty(value ="数据对象类型")
    private String dwType ;
    
    /** 有无数据 */
    @ApiModelProperty(value = "有无数据")
    @ExcelProperty(value ="有无数据")
    private BigDecimal dwHasData ;
    
    /** 报告类型枚举值 */
    @ApiModelProperty(value = "报告类型枚举值")
    @ExcelProperty(value ="报告类型枚举值")
    private String dwDataReportTypeEnum ;
    
    /** 是否计划类报告 */
    @ApiModelProperty(value = "是否计划类报告")
    @ExcelProperty(value ="是否计划类报告")
    private BigDecimal dwIsSchedule ;
    
    /** AWSAccessKeyID */
    @ApiModelProperty(value = "AWSAccessKeyID")
    @ExcelProperty(value ="AWSAccessKeyID")
    private String dwAwsKey ;
    
    /** 商城端点 */
    @ApiModelProperty(value = "商城端点")
    @ExcelProperty(value ="商城端点")
    private String dwEndPoint ;
    
    /** 商城站点编号 */
    @ApiModelProperty(value = "商城站点编号")
    @ExcelProperty(value ="商城站点编号")
    private String dwMarkIdList ;
    
    /** 是否自动下载 */
    @ApiModelProperty(value = "是否自动下载")
    @ExcelProperty(value ="是否自动下载")
    private BigDecimal dwIsAutoDownload ;
    
    /** 报告请求ID */
    @ApiModelProperty(value = "报告请求ID")
    @ExcelProperty(value ="报告请求ID")
    private String dwReportRequestId ;
    
    /** 报告ID */
    @ApiModelProperty(value = "报告ID")
    @ExcelProperty(value ="报告ID")
    private String dwReportId ;
    
    /** 报告数据日期开始日期 */
    @ApiModelProperty(value = "报告数据日期开始日期")
    @ExcelProperty(value ="报告数据日期开始日期")
    private Date dwDateBegin ;
    
    /** 报告数据日期结束日期 */
    @ApiModelProperty(value = "报告数据日期结束日期")
    @ExcelProperty(value ="报告数据日期结束日期")
    private Date dwDateEnd ;
    
    /** 任务下载状态 */
    @ApiModelProperty(value = "任务下载状态")
    @ExcelProperty(value ="任务下载状态")
    private String dwTaskState ;
    
    /** 任务执行结果 */
    @ApiModelProperty(value = "任务执行结果")
    @ExcelProperty(value ="任务执行结果")
    private String dwExecResult ;
    
    /** 任务执行开始时间 */
    @ApiModelProperty(value = "任务执行开始时间")
    @ExcelProperty(value ="任务执行开始时间")
    private Date dwExecTime ;
    
    /** 任务执行结束时间 */
    @ApiModelProperty(value = "任务执行结束时间")
    @ExcelProperty(value ="任务执行结束时间")
    private Date dwComTime ;
    
    /** 任务执行服务器IP */
    @ApiModelProperty(value = "任务执行服务器IP")
    @ExcelProperty(value ="任务执行服务器IP")
    private String dwExecServerIP ;
    
    /** 任务执行服务器MAC */
    @ApiModelProperty(value = "任务执行服务器MAC")
    @ExcelProperty(value ="任务执行服务器MAC")
    private String dwExecServerMac ;
    
    /** 平台 */
    @ApiModelProperty(value = "平台")
    @ExcelProperty(value ="平台")
    private String dwPlatName ;
    
    /** 是否推送到MQ 默认0否
1推送创建报告(NEW)，
2.推送下载报告(DONE)
3.解析推送MQ */
    @ApiModelProperty(value = "是否推送到MQ 默认0否 1推送创建报告(NEW)，2.推送下载报告(DONE) 3.解析推送MQ")
    @ExcelProperty(value ="是否推送到MQ 默认0否 1推送创建报告(NEW)，2.推送下载报告(DONE) 3.解析推送MQ")
    private BigDecimal isPush ;
    
    /** 创建报告成功返回的文档ID */
    @ApiModelProperty(value = "创建报告成功返回的文档ID")
    @ExcelProperty(value ="创建报告成功返回的文档ID")
    private String documentId ;
    
    /** NEW：初始化
CANCELLED：报告被取消。可以通过两种方式取消报告：在报告开始处理之前明确的取消请求，或者如果没有数据要返回则自动取消。
DONE：该报告已完成处理。
FATAL：报告因致命错误而中止。
IN_PROGRESS：正在处理报告。
IN_QUEUE:该报告尚未开始处理。它可能正在等待另一个 IN_PROGRESS 报告。
DOWNLOAD：文件下载完成 */
    @ApiModelProperty(value = "NEW：初始化 CANCELLED：报告被取消。可以通过两种方式取消报告：在报告开始处理之前明确的取消请求，或者如果没有数据要返回则自动取消。DONE：该报告已完成处理。FATAL：报告因致命错误而中止。IN_PROGRESS：正在处理报告。IN_QUEUE:该报告尚未开始处理。它可能正在等待另一个 IN_PROGRESS 报告。DOWNLOAD：文件下载完成")
    @ExcelProperty(value ="NEW：初始化 CANCELLED：报告被取消。可以通过两种方式取消报告：在报告开始处理之前明确的取消请求，或者如果没有数据要返回则自动取消。DONE：该报告已完成处理。FATAL：报告因致命错误而中止。IN_PROGRESS：正在处理报告。IN_QUEUE:该报告尚未开始处理。它可能正在等待另一个 IN_PROGRESS 报告。DOWNLOAD：文件下载完成")
    private String reportStatus ;
    

}