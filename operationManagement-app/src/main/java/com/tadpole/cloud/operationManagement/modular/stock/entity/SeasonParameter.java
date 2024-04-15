package com.tadpole.cloud.operationManagement.modular.stock.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
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
* <p>
    * 季节系数参数表
    * </p>
*
* @author cyt
* @since 2022-07-20
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("STOCK_SEASON_PARAMETER")
@ExcelIgnoreUnannotated
public class SeasonParameter implements Serializable {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private BigDecimal id;

    /** 平台 */
    @TableField("PLATFORM_NAME")
    @ExcelProperty("平台")
    private String platformName;

    /** 事业部 */
    @TableField("DEPARTMENT")
    @ExcelProperty("事业部")
    private String department;

    /** Team */
    @TableField("TEAM")
    @ExcelProperty("Team")
    private String team;

    /** 区域 */
    @TableField("AREA")
    @ExcelProperty("区域")
    private String area;

    /** 运营大类 */
    @TableField("PRODUCT_TYPE")
    @ExcelProperty("运营大类")
    private String productType;

    /** 适用品牌 */
    @TableField("BRAND")
    @ExcelProperty("适用品牌")
    private String brand;

    /** 型号 */
    @TableField("MODEL")
    @ExcelProperty("型号")
    private String model;

    /** 节日标签 */
    @TableField("FESTIVAL_LABEL")
    @ExcelProperty("节日标签")
    private String festivalLabel;

    /** 季节标签 */
    @TableField("SEASON")
    @ExcelProperty("季节标签")
    private String season;

    /** 产品名称 */
    @TableField("PRODUCT_NAME")
    @ExcelProperty("产品名称")
    private String productName;

    /** 优先级 */
    @TableField("PRIORITY")
    @ExcelProperty("优先级")
    private int priority;

    /** 1月 */
    @TableField("MON1")
    @ExcelProperty("1月")
    private BigDecimal mon1;

    /** 2月 */
    @TableField("MON2")
    @ExcelProperty("2月")
    private BigDecimal mon2;

    /** 3月 */
    @TableField("MON3")
    @ExcelProperty("3月")
    private BigDecimal mon3;

    /** 4月 */
    @TableField("MON4")
    @ExcelProperty("4月")
    private BigDecimal mon4;

    /** 5月 */
    @TableField("MON5")
    @ExcelProperty("5月")
    private BigDecimal mon5;

    /** 6月 */
    @TableField("MON6")
    @ExcelProperty("6月")
    private BigDecimal mon6;

    /** 7月 */
    @TableField("MON7")
    @ExcelProperty("7月")
    private BigDecimal mon7;

    /** 8月 */
    @TableField("MON8")
    @ExcelProperty("8月")
    private BigDecimal mon8;

    /** 9月 */
    @TableField("MON9")
    @ExcelProperty("9月")
    private BigDecimal mon9;

    /** 10月 */
    @TableField("MON10")
    @ExcelProperty("10月")
    private BigDecimal mon10;

    /** 11月 */
    @TableField("MON11")
    @ExcelProperty("11月")
    private BigDecimal mon11;

    /** 12月 */
    @TableField("MON12")
    @ExcelProperty("12月")
    private BigDecimal mon12;

    /** 是否启用（0：未删除,1：已删除）默认：0 */
    @ApiModelProperty("是否启用（0：未删除,1：已删除）默认：0")
    @ExcelProperty("是否启用（0：未删除,1：已删除）默认：0")
    private int isDelete;

    /** 创建人 */
    @TableField("CREATE_BY")
    @ExcelProperty("创建人")
    private String createBy;

    /** 修改人 */
    @TableField("UPDATE_BY")
    @ExcelProperty("修改人")
    private String updateBy;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    @ExcelProperty("创建时间")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    @ExcelProperty("更新时间")
    private Date updateTime;



}