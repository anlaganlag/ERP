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
 * @desc : 资源-公司实体银行设备结算卡-资源-公司实体银行设备结算卡
 */
@TableName("Tb_Com_Entity_Settlement_Card")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComEntitySettlementCard implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 编号 */
    @TableId(value = "pk_Code", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private BigDecimal pkCode ;
    
    /** 编号2;编号 */
    @TableField("pk_BE_Code")
    @ApiModelProperty(value = "编号2")
    private BigDecimal pkBeCode ;
    
    /** 结算卡的开户行 */
    @TableField("bus_Settlement_Card_Bank")
    @ApiModelProperty(value = "结算卡的开户行")
    private String busSettlementCardBank ;
    
    /** 结算卡的卡号 */
    @TableField("bus_Settlement_Card_No")
    @ApiModelProperty(value = "结算卡的卡号")
    private String busSettlementCardNo ;
    
    /** 结算卡的状态 */
    @TableField("bus_Settlement_Card_Status")
    @ApiModelProperty(value = "结算卡的状态")
    private String busSettlementCardStatus ;
    

}