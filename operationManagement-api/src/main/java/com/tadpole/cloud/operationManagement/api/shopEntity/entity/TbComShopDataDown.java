package com.tadpole.cloud.operationManagement.api.shopEntity.entity;

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
 * @date : 2023-8-11
 * @desc : 资源-店铺数据下载管理-资源-税号管理
 */
@TableName("Tb_Com_Shop_Data_Down")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComShopDataDown implements Serializable{
 private static final long serialVersionUID = 1L;
    /** ID */
    @TableId(value = "Id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private BigDecimal id ;
    
    /** 电商平台 */
    @TableField("ele_Platform_Name")
    @ApiModelProperty(value = "电商平台")
    private String elePlatformName ;
    
    /** 店铺名称;平台_账号_站点 */
    @TableField("shop_Name")
    @ApiModelProperty(value = "店铺名称")
    private String shopName ;
    
    /** SellerID */
    @TableField("Seller_ID")
    @ApiModelProperty(value = "SellerID")
    private String sellerId ;

    /** vc店铺编码 */
    @TableField("VC_SHOP_CODE")
    @ApiModelProperty(value = "vc店铺编码")
    private String vcShopCode ;
    
    /** 账号 */
    @TableField("shop_Name_Simple")
    @ApiModelProperty(value = "账号")
    private String shopNameSimple ;
    
    /** 区域 */
    @TableField("area")
    @ApiModelProperty(value = "区域")
    private String area ;
    
    /** 站点 */
    @TableField("country_Code")
    @ApiModelProperty(value = "站点")
    private String countryCode ;
    
    /** 数据对象编号 */
    @TableField("dw_Data_Obj_Num")
    @ApiModelProperty(value = "数据对象编号")
    private String dwDataObjNum ;
    
    /** 数据对象英文名 */
    @TableField("dw_Data_Obj_En_Name")
    @ApiModelProperty(value = "数据对象英文名")
    private String dwDataObjEnName ;
    
    /** 数据对象中文名 */
    @TableField("dw_Data_Obj_Cn_Name")
    @ApiModelProperty(value = "数据对象中文名")
    private String dwDataObjCnName ;
    
    /** 数据对象数据表 */
    @TableField("dw_Data_Table_Obj")
    @ApiModelProperty(value = "数据对象数据表")
    private String dwDataTableObj ;
    
    /** 报告支持下载的区域;ALL，NA， EU， FE */
    @TableField("region")
    @ApiModelProperty(value = "报告支持下载的区域")
    private String region ;
    
    /** 报告下载合并维度;0 按站点下载 1 按区域合并下载，(按区域下载时：默认保存的站点 NA：US， EU：DE， FE ：JP ) */
    @TableField("dw_Merge")
    @ApiModelProperty(value = "报告下载合并维度")
    private Integer dwMerge ;
    
    /** 任务配置状态;正常,未配置 */
    @TableField("task_state")
    @ApiModelProperty(value = "任务配置状态")
    private String taskState ;
    
    /** 任务频率 */
    @TableField("task_frequency")
    @ApiModelProperty(value = "任务频率")
    private String taskFrequency ;
    
    /** 店铺API授权;店铺API 0 未授权 1 授权正常 2 授权失效 */
    @TableField("auth_status")
    @ApiModelProperty(value = "店铺API授权")
    private BigDecimal authStatus ;
    
    /** -1：不下载店铺数据 (运营确认不需要拉取数据), 0：数据初始化，默认值, 1：需要创建下载店铺数据任务,  2：已创建店铺数据下载任务，3：缺少必要条件，暂停创建下载任务, 4: EBMS创建下载任务失败, */
    @TableField("create_task")
    @ApiModelProperty(value = "创建下载任务")
    private BigDecimal createTask ;
    
    /** 创建下载任务时间 */
    @TableField("create_task_time")
    @ApiModelProperty(value = "创建下载任务时间")
    private Date createTaskTime ;
    
    /** 任务ID;TBDWDailyTask 表 sysDwDailyID */
    @TableField("task_id")
    @ApiModelProperty(value = "任务ID")
    private BigDecimal taskId ;
    
    /** 备注 */
    @TableField("remark")
    @ApiModelProperty(value = "备注")
    private String remark ;
    
    /** 数据刷新同步时间 */
    @TableField("sync_time")
    @ApiModelProperty(value = "数据刷新同步时间")
    private Date syncTime ;
    
    /** 创建人 */
    @TableField("create_by")
    @ApiModelProperty(value = "创建人")
    private String createBy ;
    
    /** 创建时间;SYSDATE */
    @TableField("create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime ;
    
    /** 更新人 */
    @TableField("update_by")
    @ApiModelProperty(value = "更新人")
    private String updateBy ;
    
    /** 更新时间 */
    @TableField("update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime ;

    @TableField("AWS_Access_Key_ID")
    @ApiModelProperty(value = "AWSAccessKeyID")
    private String awsAccessKeyId ;
    

}