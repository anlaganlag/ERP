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
 * 物流燃料费率;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_fuel_rate")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsFuelRate implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** lfrID */
    @ApiModelProperty(value = "lfrID")
    @TableId (value = "lfr_id", type = IdType.AUTO)
    @TableField("lfr_id")
    private BigDecimal lfrId ;
 
    /** 添加时间 */
    @ApiModelProperty(value = "添加时间")
    @TableField("sys_add_date")
    private Date sysAddDate ;
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    @TableField("lp_code")
    private String lpCode ;
 
    /** 燃油费率 */
    @ApiModelProperty(value = "燃油费率")
    @TableField("lfr_ruel_rate")
    private BigDecimal lfrRuelRate ;
 
    /** 生效日期 */
    @ApiModelProperty(value = "生效日期")
    @TableField("effective_date")
    private Date effectiveDate ;


}