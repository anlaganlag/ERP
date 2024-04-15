package com.tadpole.cloud.supplyChain.modular.logistics.entity;

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
import oracle.sql.DATE;
import oracle.sql.NUMBER;

import java.util.Date;
import java.util.List;

/**
 * 物流费用测算;
 * @author : LSY
 * @date : 2024-3-14
 */
@TableName("EST_LOGISTICS_ESTIMATE_FEE")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class EstLogisticsEstimateFee implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 测算id */
    @ApiModelProperty(value = "测算id")
    @TableId (value = "EST_ID", type = IdType.ASSIGN_ID)
    @TableField("EST_ID")
    private String estId ;
 
    /**  */
    @ApiModelProperty(value = "")
    @TableField("per_code")
    private String perCode ;
 
    /**  */
    @ApiModelProperty(value = "")
    @TableField("per_name")
    private String perName ;
 
    /**  */
    @ApiModelProperty(value = "")
    @TableField("updated_time")
    private Date updatedTime ;
 
    /**  */
    @ApiModelProperty(value = "")
    @TableField("updated_by")
    private String updatedBy ;
 
    /**  */
    @ApiModelProperty(value = "")
    @TableField("created_by")
    private String createdBy ;
 
    /**  */
    @ApiModelProperty(value = "")
    @TableField("created_time")
    private Date createdTime ;
 
    /**  */
    @ApiModelProperty(value = "")
    @TableField("total_qty")
    private Double totalQty ;
 
    /**  */
    @ApiModelProperty(value = "")
    @TableField("total_fee")
    private Double totalFee ;
 
    /**  */
    @ApiModelProperty(value = "")
    @TableField("est_date")
    private Date estDate ;
 
    /**  */



   /** 测算明细 */
   @TableField(exist = false)
   @ApiModelProperty(value = "测算明细")
   private List<EstLogisticsEstimateFeeDet> detailList ;

}