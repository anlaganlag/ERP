package com.tadpole.cloud.platformSettlement.modular.sales.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 新品预算
 * </p>
 *
 * @author gal
 * @since 2022-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("NEW_PRODUCT_BUDGET")
@ExcelIgnoreUnannotated
public class NewProductBudget implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
    @ExcelProperty(value= "导入错误备注")
    private String uploadRemark;

    /** 部门 */
    @TableField("DEPARTMENT")
    @ExcelProperty(value ="部门")
    private String department;


    /** 运营大类 */
    @TableField("PRODUCT_TYPE")
    @ExcelProperty(value ="运营大类")
    private String productType;


    /** 二级类目 */
    @TableField("SECOND_LABEL")
    @ExcelProperty(value ="二级类目")
    private String secondLabel;



    /** 一季度 */
    @TableField("SEASON_ONE")
    @ExcelProperty(value ="Q1-新品销售收入")
    private BigDecimal seasonOne;

    /** 二季度 */
    @TableField("SEASON_TWO")
    @ExcelProperty(value ="Q2-新品销售收入")
    private BigDecimal seasonTwo;

    /** 三季度 */
    @TableField("SEASON_THREE")
    @ExcelProperty(value ="Q3-新品销售收入")
    private BigDecimal seasonThree;

    /** 四季度 */
    @TableField("SEASON_FOUR")
    @ExcelProperty(value ="Q4-新品销售收入")
    private BigDecimal seasonFour;

    /** 产品线一季度 */
    @TableField("SEASON_ONE_LINE")
    @ExcelProperty(value ="Q1-产品线销售收入")
    private BigDecimal seasonOneLine;

    /** 产品线二季度 */
    @TableField("SEASON_TWO_LINE")
    @ExcelProperty(value ="Q2-产品线销售收入")
    private BigDecimal seasonTwoLine;

    /** 产品线三季度 */
    @TableField("SEASON_THREE_LINE")
    @ExcelProperty(value ="Q3-产品线销售收入")
    private BigDecimal seasonThreeLine;

    /** 产品线四季度 */
    @TableField("SEASON_FOUR_LINE")
    @ExcelProperty(value ="Q4-产品线销售收入")
    private BigDecimal seasonFourLine;


    /** 产品线销售收入目标全年汇总 */
    @TableField("LINE_YEAR_TARGET")
    @ExcelProperty(value ="产品线销售收入目标全年汇总")
    private BigDecimal lineYearTarget;



    /** 一季度占比 */
    @TableField("SEASON_ONE_PROPORTION")
    @ExcelProperty(value ="一季度占比")
    private BigDecimal seasonOneProportion;

    /** 二季度占比 */
    @TableField("SEASON_TWO_PROPORTION")
    @ExcelProperty(value ="二季度占比")
    private BigDecimal seasonTwoProportion;

    /** 三季度占比 */
    @TableField("SEASON_THREE_PROPORTION")
    @ExcelProperty(value ="三季度占比")
    private BigDecimal seasonThreeProportion;

    /** 四季度占比 */
    @TableField("SEASON_FOUR_PROPORTION")
    @ExcelProperty(value ="四季度占比")
    private BigDecimal seasonFourProportion;

    /** 年度 */
    @TableField("YEAR")
    @ExcelProperty(value ="年度")
    private String year;

    /** 版本 */
    @TableField("VERSION")
    @ExcelProperty(value ="版本")
    private String version;

    /** 币种 */
    @TableField("CURRENCY")
    @ExcelProperty(value ="币种")
    private String currency;

    /** 确认状态 */
    @TableField("CONFIRM_STATUS")
    private BigDecimal confirmStatus;

    /** 创建日期 */
    @TableField("CREATE_AT")
    @ExcelProperty(value ="创建日期")
    private Date createAt;

    /** 创建人 */
    @TableField("CREATE_BY")
    @ExcelProperty(value ="创建人")
    private String createBy;

    /** 确认日期 */
    @TableField("CONFIRM_DATE")
    @ExcelProperty(value ="确认日期")
    private Date confirmDate;

    /** 确认人 */
    @TableField("CONFIRM_BY")
    @ExcelProperty(value ="确认人")
    private String confirmBy;

    /** 修改日期 */
    @TableField("UPDATE_AT")
    @ExcelProperty(value ="修改日期")
    private Date updateAt;

    /** 修改人 */
    @TableField("UPDATE_BY")
    @ExcelProperty(value ="修改人")
    private String updateBy;



    /** id */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;









}