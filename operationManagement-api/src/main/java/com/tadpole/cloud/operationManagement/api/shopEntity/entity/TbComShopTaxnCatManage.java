package com.tadpole.cloud.operationManagement.api.shopEntity.entity;

import java.io.Serializable;
import java.util.Date;
import java.lang.*;
import lombok.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

 /**
 * @author : LSY
 * @date : 2023-7-28
 * @desc : 资源-税号类别管理-资源-税号类别管理
 */
@TableName("Tb_Com_Shop_Taxn_Cat_Manage")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComShopTaxnCatManage implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 收款银行账号变更编号 */
    @TableId(value = "sys_Id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "收款银行账号变更编号")
    private BigDecimal sysId ;
    
    /** 店铺名称 */
    @TableField("shop_Name")
    @ApiModelProperty(value = "店铺名称")
    private String shopName ;
    
    /** 操作人姓名;接收人姓名 */
    @TableField("sys_Operate_Per_Name")
    @ApiModelProperty(value = "操作人姓名")
    private String sysOperatePerName ;
    
    /** 操作人编号;接收人编号 */
    @TableField("sys_Operate_Per_Code")
    @ApiModelProperty(value = "操作人编号")
    private String sysOperatePerCode ;
    
    /** 操作人时间;接收人时间 */
    @TableField("sys_Operate_Date")
    @ApiModelProperty(value = "操作人时间")
    private Date sysOperateDate ;
    
    /** 税号类别 */
    @TableField("taxn_Type")
    @ApiModelProperty(value = "税号类别")
    private String taxnType ;
    
    /** 有效范围(开始) */
    @TableField("taxn_Effect_Range_Start")
    @ApiModelProperty(value = "有效范围(开始)")
    private Date taxnEffectRangeStart ;
    
    /** 有效范围(结束) */
    @TableField("taxn_Effect_Range_End")
    @ApiModelProperty(value = "有效范围(结束)")
    private Date taxnEffectRangeEnd ;
    

}