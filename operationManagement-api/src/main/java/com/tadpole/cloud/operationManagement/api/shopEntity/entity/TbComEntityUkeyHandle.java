package com.tadpole.cloud.operationManagement.api.shopEntity.entity;

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

 /**
 * @author : LSY
 * @date : 2023-7-28
 * @desc : 资源-公司实体银行设备银行UKEY经办信息-资源-公司实体银行设备银行UKEY经办信息
 */
@TableName("Tb_Com_Entity_Ukey_Handle")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComEntityUkeyHandle implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 编号 */
    @TableId(value = "pk_Code", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private BigDecimal pkCode ;
    
    /** 编号2;编号 */
    @TableField("pk_BE_Code")
    @ApiModelProperty(value = "编号2")
    private BigDecimal pkBeCode ;
    
    /** 银行UKEY-经办的开户行 */
    @TableField("bus_Bank_Ukey_Handle_Bank")
    @ApiModelProperty(value = "银行UKEY-经办的开户行")
    private String busBankUkeyHandleBank ;
    
    /** 银行UKEY-经办的编号 */
    @TableField("bus_Bank_Ukey_Handle_No")
    @ApiModelProperty(value = "银行UKEY-经办的编号")
    private String busBankUkeyHandleNo ;
    
    /** 银行UKEY-经办的状态 */
    @TableField("bus_Bank_Ukey_Handle_Status")
    @ApiModelProperty(value = "银行UKEY-经办的状态")
    private String busBankUkeyHandleStatus ;
    

}