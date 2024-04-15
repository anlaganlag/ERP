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
 * @desc : TbDwTaskAutoCreate-TbDWTaskAutoCreate
 */
@TableName("TbDWTaskAutoCreate")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbDwTaskAutoCreate implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 系统任务编号 */
    @TableId(value = "sysDwID", type = IdType.AUTO)
    @ApiModelProperty(value = "系统任务编号")
    private BigDecimal sysDwId ;
    
    /** 数据对象编号 */
    @TableField("dwDataObjNum")
    @ApiModelProperty(value = "数据对象编号")
    private String dwDataObjNum ;
    
    /** 任务名称 */
    @TableField("dwTaskName")
    @ApiModelProperty(value = "任务名称")
    private String dwTaskName ;
    
    /** 数据对象英文名 */
    @TableField("dwDataObjEnName")
    @ApiModelProperty(value = "数据对象英文名")
    private String dwDataObjEnName ;
    
    /** SellerID */
    @TableField("dwSellerID")
    @ApiModelProperty(value = "SellerID")
    private String dwSellerId ;
    
    /** 账号 */
    @TableField("dwShopNameSimple")
    @ApiModelProperty(value = "账号")
    private String dwShopNameSimple ;
    
    /** 站点 */
    @TableField("dwCountryCode")
    @ApiModelProperty(value = "站点")
    private String dwCountryCode ;
    
    /** 负责人编号 */
    @TableField("dwDlPerson")
    @ApiModelProperty(value = "负责人编号")
    private String dwDlPerson ;
    
    /** 负责人姓名 */
    @TableField("dwDlPersonName")
    @ApiModelProperty(value = "负责人姓名")
    private String dwDlPersonName ;
    
    /** 使用部门 */
    @TableField("dwUseDepart")
    @ApiModelProperty(value = "使用部门")
    private String dwUseDepart ;
    
    /** AWSAccessKeyID */
    @TableField("dwAWSKey")
    @ApiModelProperty(value = "AWSAccessKeyID")
    private String dwAwsKey ;
    
    /** 商城端点 */
    @TableField("dwEndPoint")
    @ApiModelProperty(value = "商城端点")
    private String dwEndPoint ;
    
    /** 商城站点编号 */
    @TableField("dwMarkIdList")
    @ApiModelProperty(value = "商城站点编号")
    private String dwMarkIdList ;
    
    /** 是否自动下载 */
    @TableField("dwIsAutoDownload")
    @ApiModelProperty(value = "是否自动下载")
    private BigDecimal dwIsAutoDownload ;
    
    /** 平台 */
    @TableField("dwPlatName")
    @ApiModelProperty(value = "平台")
    private String dwPlatName ;
    
    /** createTime */
    @TableField("createTime")
    @ApiModelProperty(value = "createTime")
    private Date createTime ;
    

}