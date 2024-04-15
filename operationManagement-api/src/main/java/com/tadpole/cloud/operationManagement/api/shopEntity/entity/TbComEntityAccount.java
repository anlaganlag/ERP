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

/**
 * @author : LSY
 * @date : 2023-7-28
 * @desc : 资源-公司实体账户信息-资源-公司实体账户信息
 */
@TableName("Tb_Com_Entity_Account")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComEntityAccount implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 账户编号 */
    @TableId(value = "com_Acc_ID", type = IdType.AUTO)
    @ApiModelProperty(value = "账户编号")
    private BigDecimal comAccId ;
    
    /** 公司名称（中文） */
    @TableField("com_Name_Cn")
    @ApiModelProperty(value = "公司名称（中文）")
    private String comNameCn ;
    
    /** 账户类型 */
    @TableField("com_Acc_Type")
    @ApiModelProperty(value = "账户类型")
    private String comAccType ;
    
    /** 一般户开户行 */
    @TableField("com_Acc_Bank")
    @ApiModelProperty(value = "一般户开户行")
    private String comAccBank ;
    
    /** 一般户银行账号 */
    @TableField("com_Acc_No")
    @ApiModelProperty(value = "一般户银行账号")
    private String comAccNo ;
    
    /** 币种 */
    @TableField("com_Acc_Currency")
    @ApiModelProperty(value = "币种")
    private String comAccCurrency ;
    

}