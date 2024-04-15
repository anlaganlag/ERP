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
 * @desc : 资源-公司实体银行设备税控盘-资源-公司实体银行设备税控盘
 */
@TableName("Tb_Com_Entity_Tax_Control_Panel")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComEntityTaxControlPanel implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 编号 */
    @TableId(value = "pk_Code", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private BigDecimal pkCode ;
    
    /** 编号2;编号 */
    @TableField("pk_BE_Code")
    @ApiModelProperty(value = "编号2")
    private BigDecimal pkBeCode ;
    
    /** 税控盘的编号 */
    @TableField("bus_Tax_Control_Panel_No")
    @ApiModelProperty(value = "税控盘的编号")
    private String busTaxControlPanelNo ;
    

}