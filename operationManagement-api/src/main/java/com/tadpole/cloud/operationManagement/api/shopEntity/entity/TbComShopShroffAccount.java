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
 * @date : 2023-8-3
 * @desc : 资源-信用卡账号管理-资源-税号管理
 */
@TableName("Tb_Com_Shop_Shroff_Account")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComShopShroffAccount implements Serializable{
 private static final long serialVersionUID = 1L;
    /** ID */
    @TableId(value = "sys_Id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private BigDecimal sysId ;
    
    /** 店铺名称;平台_账号_站点 */
    @TableField("shop_Name")
    @ApiModelProperty(value = "店铺名称")
    private String shopName ;
    
    /** 操作人员名 */
    @TableField("sys_Operate_Per_Name")
    @ApiModelProperty(value = "操作人员名")
    private String sysOperatePerName ;
    
    /** 操作人员编号 */
    @TableField("sys_Operate_Per_Code")
    @ApiModelProperty(value = "操作人员编号")
    private String sysOperatePerCode ;
    
    /** 操作日期 */
    @TableField("sys_Operate_Date")
    @ApiModelProperty(value = "操作日期")
    private Date sysOperateDate ;
    
    /** 信用卡号 */
    @TableField("shop_Account_No")
    @ApiModelProperty(value = "信用卡号")
    private String shopAccountNo ;
    
    /** 有效日期-开始 */
    @TableField("shop_Effe_Range_Start")
    @ApiModelProperty(value = "有效日期-开始")
    private Date shopEffeRangeStart ;
    
    /** 有效日期-截止 */
    @TableField("shop_Effe_Range_End")
    @ApiModelProperty(value = "有效日期-截止")
    private Date shopEffeRangeEnd ;
    
    /** 信用卡开户行 */
    @TableField("shop_Bank_Name")
    @ApiModelProperty(value = "信用卡开户行")
    private String shopBankName ;
    

}