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
 * @desc : TBDWDailyTask-TBDWDailyTask
 */
@TableName("TB_DW_Daily_Task")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbDwDailyTask implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 每日任务编号 */
    @TableId(value = "sys_Dw_Daily_ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "每日任务编号")
    private BigDecimal sysDwDailyId ;
    
    /** 任务名称 */
    @TableField("dwdaily_Task_Name")
    @ApiModelProperty(value = "任务名称")
    private String dwdailyTaskName ;
    
    /** 数据对象编号 */
    @TableField("dw_Data_Obj_Num")
    @ApiModelProperty(value = "数据对象编号")
    private String dwDataObjNum ;
    
    /** 数据对象英文名 */
    @TableField("dw_Data_Obj_En_Name")
    @ApiModelProperty(value = "数据对象英文名")
    private String dwDataObjEnName ;
    
    /** 数据对象数据表 */
    @TableField("dw_Data_Table_Obj")
    @ApiModelProperty(value = "数据对象数据表")
    private String dwDataTableObj ;
    
    /** 下载规则类型 */
    @TableField("dw_Dl_Type")
    @ApiModelProperty(value = "下载规则类型")
    private String dwDlType ;
    
    /** 下载规则具体日 */
    @TableField("dw_Dl_Exeact_Date")
    @ApiModelProperty(value = "下载规则具体日")
    private String dwDlExeactDate ;
    
    /** 下载预计完成时间 */
    @TableField("dw_Dl_Complate_Time")
    @ApiModelProperty(value = "下载预计完成时间")
    private String dwDlComplateTime ;
    
    /** 存储规则方式 */
    @TableField("dw_Save_Type")
    @ApiModelProperty(value = "存储规则方式")
    private String dwSaveType ;
    
    /** 去重标识 */
    @TableField("dw_Handle_Det_Info")
    @ApiModelProperty(value = "去重标识")
    private String dwHandleDetInfo ;
    
    /** SellerID */
    @TableField("dw_Seller_ID")
    @ApiModelProperty(value = "SellerID")
    private String dwSellerId ;
    
    /** 账号 */
    @TableField("dw_Shop_Name_Simple")
    @ApiModelProperty(value = "账号")
    private String dwShopNameSimple ;
    
    /** 站点 */
    @TableField("dw_Country_Code")
    @ApiModelProperty(value = "站点")
    private String dwCountryCode ;
    
    /** 负责人编号 */
    @TableField("dw_Dl_Person")
    @ApiModelProperty(value = "负责人编号")
    private String dwDlPerson ;
    
    /** 负责人姓名 */
    @TableField("dw_Dl_Person_Name")
    @ApiModelProperty(value = "负责人姓名")
    private String dwDlPersonName ;
    
    /** 使用部门 */
    @TableField("dw_Use_Depart")
    @ApiModelProperty(value = "使用部门")
    private String dwUseDepart ;
    
    /** 任务生成时间 */
    @TableField("dw_Task_Create_Time")
    @ApiModelProperty(value = "任务生成时间")
    private Date dwTaskCreateTime ;
    
    /** 文件上传状态 */
    @TableField("dw_File_Upload_Status")
    @ApiModelProperty(value = "文件上传状态")
    private String dwFileUploadStatus ;
    
    /** 文件上传路径 */
    @TableField("dw_File_Upload_Path")
    @ApiModelProperty(value = "文件上传路径")
    private String dwFileUploadPath ;
    
    /** 文件上传时间 */
    @TableField("dw_File_Upload_Time")
    @ApiModelProperty(value = "文件上传时间")
    private Date dwFileUploadTime ;
    
    /** 文件上传结果 */
    @TableField("dw_File_Upload_Result")
    @ApiModelProperty(value = "文件上传结果")
    private String dwFileUploadResult ;
    
    /** 文件处理状态 */
    @TableField("dw_File_Handle_Status")
    @ApiModelProperty(value = "文件处理状态")
    private String dwFileHandleStatus ;
    
    /** 文件处理时间 */
    @TableField("dw_File_Handle_Time")
    @ApiModelProperty(value = "文件处理时间")
    private Date dwFileHandleTime ;
    
    /** 文件处理结果 */
    @TableField("dw_File_Handle_Result")
    @ApiModelProperty(value = "文件处理结果")
    private String dwFileHandleResult ;
    
    /** 数据对象类型 */
    @TableField("dw_Type")
    @ApiModelProperty(value = "数据对象类型")
    private String dwType ;
    
    /** 有无数据 */
    @TableField("dw_Has_Data")
    @ApiModelProperty(value = "有无数据")
    private BigDecimal dwHasData ;
    
    /** 报告类型枚举值 */
    @TableField("dw_Data_Report_Type_Enum")
    @ApiModelProperty(value = "报告类型枚举值")
    private String dwDataReportTypeEnum ;
    
    /** 是否计划类报告 */
    @TableField("dw_Is_Schedule")
    @ApiModelProperty(value = "是否计划类报告")
    private BigDecimal dwIsSchedule ;
    
    /** AWSAccessKeyID */
    @TableField("dw_AWS_Key")
    @ApiModelProperty(value = "AWSAccessKeyID")
    private String dwAwsKey ;
    
    /** 商城端点 */
    @TableField("dw_End_Point")
    @ApiModelProperty(value = "商城端点")
    private String dwEndPoint ;
    
    /** 商城站点编号 */
    @TableField("dw_Mark_Id_List")
    @ApiModelProperty(value = "商城站点编号")
    private String dwMarkIdList ;
    
    /** 是否自动下载 */
    @TableField("dw_Is_Auto_Download")
    @ApiModelProperty(value = "是否自动下载")
    private BigDecimal dwIsAutoDownload ;
    
    /** 报告请求ID */
    @TableField("dw_Report_Request_ID")
    @ApiModelProperty(value = "报告请求ID")
    private String dwReportRequestId ;
    
    /** 报告ID */
    @TableField("dw_Report_ID")
    @ApiModelProperty(value = "报告ID")
    private String dwReportId ;
    
    /** 报告数据日期开始日期 */
    @TableField("dw_Date_Begin")
    @ApiModelProperty(value = "报告数据日期开始日期")
    private Date dwDateBegin ;
    
    /** 报告数据日期结束日期 */
    @TableField("dw_Date_End")
    @ApiModelProperty(value = "报告数据日期结束日期")
    private Date dwDateEnd ;
    
    /** 任务下载状态 */
    @TableField("dw_Task_State")
    @ApiModelProperty(value = "任务下载状态")
    private String dwTaskState ;
    
    /** 任务执行结果 */
    @TableField("dw_Exec_Result")
    @ApiModelProperty(value = "任务执行结果")
    private String dwExecResult ;
    
    /** 任务执行开始时间 */
    @TableField("dw_Exec_Time")
    @ApiModelProperty(value = "任务执行开始时间")
    private Date dwExecTime ;
    
    /** 任务执行结束时间 */
    @TableField("dw_Com_Time")
    @ApiModelProperty(value = "任务执行结束时间")
    private Date dwComTime ;
    
    /** 任务执行服务器IP */
    @TableField("dw_Exec_Server_I_P")
    @ApiModelProperty(value = "任务执行服务器IP")
    private String dwExecServerIP ;
    
    /** 任务执行服务器MAC */
    @TableField("dw_Exec_Server_Mac")
    @ApiModelProperty(value = "任务执行服务器MAC")
    private String dwExecServerMac ;
    
    /** 平台 */
    @TableField("dw_Plat_Name")
    @ApiModelProperty(value = "平台")
    private String dwPlatName ;
    
    /** 是否推送到MQ 默认0否
1推送创建报告(NEW)，
2.推送下载报告(DONE)
3.解析推送MQ */
    @TableField("is_Push")
    @ApiModelProperty(value = "是否推送到MQ 默认0否 1推送创建报告(NEW)， 2.推送下载报告(DONE) 3.解析推送MQ")
    private BigDecimal isPush ;
    
    /** 创建报告成功返回的文档ID */
    @TableField("document_Id")
    @ApiModelProperty(value = "创建报告成功返回的文档ID")
    private String documentId ;
    
    /** NEW：初始化
CANCELLED：报告被取消。可以通过两种方式取消报告：在报告开始处理之前明确的取消请求，或者如果没有数据要返回则自动取消。
DONE：该报告已完成处理。
FATAL：报告因致命错误而中止。
IN_PROGRESS：正在处理报告。
IN_QUEUE:该报告尚未开始处理。它可能正在等待另一个 IN_PROGRESS 报告。
DOWNLOAD：文件下载完成 */
    @TableField("report_Status")
    @ApiModelProperty(value =
            "NEW：初始化 CANCELLED：报告被取消。可以通过两种方式取消报告：在报告开始处理之前明确的取消请求，或者如果没有数据要返回则自动取消。 " +
                    "DONE：该报告已完成处理。 FATAL：报告因致命错误而中止。 IN_PROGRESS：正在处理报告。 " +
                    "IN_QUEUE:该报告尚未开始处理。它可能正在等待另一个 IN_PROGRESS 报告。 DOWNLOAD：文件下载完成")
    private String reportStatus ;
    

}