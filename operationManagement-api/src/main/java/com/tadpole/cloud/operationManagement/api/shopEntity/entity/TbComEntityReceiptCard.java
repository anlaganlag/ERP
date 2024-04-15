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
 * @desc : 资源-公司实体银行设备回单卡-资源-公司实体银行设备回单卡
 */
@TableName("Tb_Com_Entity_Receipt_Card")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComEntityReceiptCard implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 编号 */
    @TableId(value = "pk_Code", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private BigDecimal pkCode ;
    
    /** 编号2;编号 */
    @TableField("pk_BE_Code")
    @ApiModelProperty(value = "编号2")
    private BigDecimal pkBeCode ;
    
    /** 回单卡的开户行 */
    @TableField("bus_Receipt_Card_Brank")
    @ApiModelProperty(value = "回单卡的开户行")
    private String busReceiptCardBrank ;
    
    /** 回单卡的卡号 */
    @TableField("bus_Receipt_Card_No")
    @ApiModelProperty(value = "回单卡的卡号")
    private String busReceiptCardNo ;
    
    /** 回单卡的状态 */
    @TableField("bus_Receipt_Card_Status")
    @ApiModelProperty(value = "回单卡的状态")
    private String busReceiptCardStatus ;
    

}