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
 * 物流费用名称;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_charge_name")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsChargeName implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    @TableId (value = "id", type = IdType.AUTO)
    @TableField("id")
    private BigDecimal id ;
 
    /** EBMS字典标准费用名称 */
    @ApiModelProperty(value = "EBMS字典标准费用名称")
    @TableField("standard_name")
    private String standardName ;
 
    /** 物流商费用名称 */
    @ApiModelProperty(value = "物流商费用名称")
    @TableField("logistics_name")
    private String logisticsName ;
 
    /** 费用类型 */
    @ApiModelProperty(value = "费用类型")
    @TableField("charge_type")
    private String chargeType ;


}