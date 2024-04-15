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
 * 物流账单税务文件;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_bill_tax_files")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsBillTaxFiles implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    @TableId (value = "id", type = IdType.AUTO)
    @TableField("id")
    private BigDecimal id ;
 
    /** 物流对账单号 */
    @ApiModelProperty(value = "物流对账单号")
    @TableField("bill_num")
    private String billNum ;
 
    /** 物流跟踪单号（头程物流单号） */
    @ApiModelProperty(value = "物流跟踪单号（头程物流单号）")
    @TableField("lhr_odd_num")
    private String lhrOddNum ;
 
    /** 税费单号 */
    @ApiModelProperty(value = "税费单号")
    @TableField("tax_num")
    private String taxNum ;
 
    /** 税费文件类型 */
    @ApiModelProperty(value = "税费文件类型")
    @TableField("tax_type")
    private String taxType ;
 
    /** 源文件名称 */
    @ApiModelProperty(value = "源文件名称")
    @TableField("tax_origin_file_name")
    private String taxOriginFileName ;
 
    /** 税费文件名称 */
    @ApiModelProperty(value = "税费文件名称")
    @TableField("tax_file_name")
    private String taxFileName ;
 
    /** 添加日期 */
    @ApiModelProperty(value = "添加日期")
    @TableField("add_date")
    private Date addDate ;
 
    /** 操作人 */
    @ApiModelProperty(value = "操作人")
    @TableField("operator")
    private String operator ;
 
    /** 工号 */
    @ApiModelProperty(value = "工号")
    @TableField("operator_code")
    private String operatorCode ;
 
    /** 存储路径 */
    @ApiModelProperty(value = "存储路径")
    @TableField("file_path_full")
    private String filePathFull ;


}