package com.tadpole.cloud.operationManagement.api.shopEntity.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * 资源-店铺数据下载管理;资源-税号管理
 * @author : LSY
 * @date : 2023-8-11
 */
@ApiModel(value = "资源-店铺数据下载管理",description = "资源-税号管理")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComShopDataDownResult  implements Serializable{
 private static final long serialVersionUID = 1L;
    /** ID */
    @ApiModelProperty(value = "ID")
    @ExcelProperty(value ="ID")
    private BigDecimal id ;
    
    /** 电商平台 */
    @ApiModelProperty(value = "电商平台")
    @ExcelProperty(value ="电商平台")
    private String elePlatformName ;
    
    /** 店铺名称;平台_账号_站点 */
    @ApiModelProperty(value = "店铺名称")
    @ExcelProperty(value ="店铺名称")
    private String shopName ;
    
    /** SellerID */
    @ApiModelProperty(value = "SellerID")
    @ExcelProperty(value ="SellerID")
    private String sellerId ;

    /** vc店铺编码 */
    @ApiModelProperty(value = "vc店铺编码")
    @ExcelProperty(value ="vc店铺编码")
    private String vcShopCode ;
    
    /** 账号 */
    @ApiModelProperty(value = "账号")
    @ExcelProperty(value ="账号")
    private String shopNameSimple ;
    
    /** 区域 */
    @ApiModelProperty(value = "区域")
    @ExcelProperty(value ="区域")
    private String area ;
    
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @ExcelProperty(value ="站点")
    private String countryCode ;
    
    /** 数据对象编号 */
    @ApiModelProperty(value = "数据对象编号")
    @ExcelProperty(value ="数据对象编号")
    private String dwDataObjNum ;
    
    /** 数据对象英文名 */
    @ApiModelProperty(value = "数据对象英文名")
    @ExcelProperty(value ="数据对象英文名")
    private String dwDataObjEnName ;
    
    /** 数据对象中文名 */
    @ApiModelProperty(value = "数据对象中文名")
    @ExcelProperty(value ="数据对象中文名")
    private String dwDataObjCnName ;
    
    /** 数据对象数据表 */
    @ApiModelProperty(value = "数据对象数据表")
    @ExcelProperty(value ="数据对象数据表")
    private String dwDataTableObj ;
    
    /** 报告支持下载的区域;ALL，NA， EU， FE */
    @ApiModelProperty(value = "报告支持下载的区域")
    @ExcelProperty(value ="报告支持下载的区域")
    private String region ;
    
    /** 报告下载合并维度;0 按站点下载 1 按区域合并下载，(按区域下载时：默认保存的站点 NA：US， EU：DE， FE ：JP ) */
    @ApiModelProperty(value = "报告下载合并维度")
    @ExcelProperty(value ="报告下载合并维度")
    private Integer dwMerge ;
    
    /** 任务配置状态;正常,未配置 */
    @ApiModelProperty(value = "任务配置状态")
    @ExcelProperty(value ="任务配置状态")
    private String taskState ;
    
    /** 任务频率 */
    @ApiModelProperty(value = "任务频率")
    @ExcelProperty(value ="任务频率")
    private String taskFrequency ;
    
    /** 店铺API授权;店铺API 0 未授权 1 授权正常 2 授权失效 */
    @ApiModelProperty(value = "店铺API授权")
    @ExcelProperty(value ="店铺API授权")
    private BigDecimal authStatus ;
    
    /** -1：不下载店铺数据 (运营确认不需要拉取数据), 0：数据初始化，默认值, 1：需要创建下载店铺数据任务,  2：已创建店铺数据下载任务，3：缺少必要条件，暂停创建下载任务, 4: EBMS创建下载任务失败, */
    @ApiModelProperty(value = "创建下载任务")
    @ExcelProperty(value ="创建下载任务")
    private BigDecimal createTask ;
    
    /** 创建下载任务时间 */
    @ApiModelProperty(value = "创建下载任务时间")
    @ExcelProperty(value ="创建下载任务时间")
    private Date createTaskTime ;
    
    /** 任务ID;TBDWDailyTask 表 sysDwDailyID */
    @ApiModelProperty(value = "任务ID")
    @ExcelProperty(value ="任务ID")
    private BigDecimal taskId ;
    
    /** 备注 */
    @ApiModelProperty(value = "备注")
    @ExcelProperty(value ="备注")
    private String remark ;
    
    /** 数据刷新同步时间 */
    @ApiModelProperty(value = "数据刷新同步时间")
    @ExcelProperty(value ="数据刷新同步时间")
    private Date syncTime ;
    
    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    @ExcelProperty(value ="创建人")
    private String createBy ;
    
    /** 创建时间;SYSDATE */
    @ApiModelProperty(value = "创建时间")
    @ExcelProperty(value ="创建时间")
    private Date createTime ;
    
    /** 更新人 */
    @ApiModelProperty(value = "更新人")
    @ExcelProperty(value ="更新人")
    private String updateBy ;
    
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @ExcelProperty(value ="更新时间")
    private Date updateTime ;

    @ExcelProperty("AWS_Access_Key_ID")
    @ApiModelProperty(value = "AWSAccessKeyID")
    private String awsAccessKeyId ;
}