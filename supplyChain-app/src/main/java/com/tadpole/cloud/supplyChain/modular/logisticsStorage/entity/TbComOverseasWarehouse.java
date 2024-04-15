package com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity;

import java.io.Serializable;
import java.lang.*;
import lombok.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

 /**
 * 海外仓信息;
 * @author : LSY
 * @date : 2024-1-19
 */
@TableName("Tb_Com_Overseas_Warehouse")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComOverseasWarehouse implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 仓库名称 */
    @ApiModelProperty(value = "仓库名称")
    @TableId (value = "ow_Name", type = IdType.ASSIGN_ID)
    @TableField("ow_Name")
    private String owName ;
 
    /** 建仓日期 */
    @ApiModelProperty(value = "建仓日期")
    @TableField("ow_Creat_Date")
    private Date owCreatDate ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @TableField("country_Code")
    private String countryCode ;
 
    /** 仓库状态 */
    @ApiModelProperty(value = "仓库状态")
    @TableField("ow_State")
    private String owState ;
 
    /** 仓库地址 */
    @ApiModelProperty(value = "仓库地址")
    @TableField("ow_Address")
    private String owAddress ;
 
    /** 仓库联系人 */
    @ApiModelProperty(value = "仓库联系人")
    @TableField("ow_Person")
    private String owPerson ;
 
    /** 联系电话 */
    @ApiModelProperty(value = "联系电话")
    @TableField("ow_Tel")
    private String owTel ;
 
    /** 邮箱 */
    @ApiModelProperty(value = "邮箱")
    @TableField("ow_Email")
    private String owEmail ;
 
    /** 电商平台 */
    @ApiModelProperty(value = "电商平台")
    @TableField("ele_Platform_Name")
    private String elePlatformName ;


}