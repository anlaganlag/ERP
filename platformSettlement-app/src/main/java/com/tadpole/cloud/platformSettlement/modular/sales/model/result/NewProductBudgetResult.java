package com.tadpole.cloud.platformSettlement.modular.sales.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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
@ExcelIgnoreUnannotated
@TableName("NEW_PRODUCT_BUDGET")
public class NewProductBudgetResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 部门 */
    @ApiModelProperty("DEPARTMENT")
    @ExcelProperty(value ="部门")
    private String department;

    /** 二级类目 */
    @ApiModelProperty("SECOND_LABEL")
    @ExcelProperty(value ="二级类目")
    private String secondLabel;

    /** 运营大类 */
    @ApiModelProperty("PRODUCT_TYPE")
    @ExcelProperty(value ="运营大类")
    private String productType;

    /** 一季度 */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SEASON_ONE")
    @ExcelProperty(value ="Q1-新品销售收入")
    private BigDecimal seasonOne;

    /** 二季度 */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SEASON_TWO")
    @ExcelProperty(value ="Q2-新品销售收入")
    private BigDecimal seasonTwo;

    /** 三季度 */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SEASON_THREE")
    @ExcelProperty(value ="Q3-新品销售收入")
    private BigDecimal seasonThree;

    /** 四季度 */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SEASON_FOUR")
    @ExcelProperty(value ="Q4-新品销售收入")
    private BigDecimal seasonFour;

    /** 产品线一季度 */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SEASON_ONE_LINE")
    @ExcelProperty(value ="Q1-产品线销售收入")
    private BigDecimal seasonOneLine;

    /** 产品线二季度 */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SEASON_TWO_LINE")
    @ExcelProperty(value ="Q2-产品线销售收入")
    private BigDecimal seasonTwoLine;

    /** 产品线三季度 */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SEASON_THREE_LINE")
    @ExcelProperty(value ="Q3-产品线销售收入")
    private BigDecimal seasonThreeLine;

    /** 产品线四季度 */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SEASON_FOUR_LINE")
    @ExcelProperty(value ="Q4-产品线销售收入")
    private BigDecimal seasonFourLine;

    /** 一季度占比 */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SEASON_ONE_PROPORTION")
    @ExcelProperty(value ="一季度占比")
    private BigDecimal seasonOneProportion;

    /** 二季度占比 */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SEASON_TWO_PROPORTION")
    @ExcelProperty(value ="二季度占比")
    private BigDecimal seasonTwoProportion;

    /** 三季度占比 */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SEASON_THREE_PROPORTION")
    @ExcelProperty(value ="三季度占比")
    private BigDecimal seasonThreeProportion;

    /** 四季度占比 */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SEASON_FOUR_PROPORTION")
    @ExcelProperty(value ="四季度占比")
    private BigDecimal seasonFourProportion;

    @ContentStyle(dataFormat = 2)
    @ExcelProperty(value = "年度预算-新品销售收入")
    private BigDecimal yearTarget;

    @ContentStyle(dataFormat = 2)
    @ExcelProperty(value = "产品线销售收入目标全年汇总")
    private BigDecimal yearTargetLine;

    @ContentStyle(dataFormat = 2)
    @ExcelProperty(value = "年度占比")
    private BigDecimal yearProportion;

    /** 年度 */
    @ApiModelProperty("YEAR")
    @ExcelProperty(value ="年度")
    private BigDecimal year;

    /** 版本 */
    @ApiModelProperty("VERSION")
    @ExcelProperty(value ="版本")
    private String version;

    /** 币种 */
    @ApiModelProperty("CURRENCY")
    @ExcelProperty(value ="币种")
    private String currency;

    /** 确认状态 */
    @ApiModelProperty("CONFIRM_STATUS")
    private BigDecimal confirmStatus;

    /** 创建日期 */
    @ApiModelProperty("CREATE_AT")
    @ExcelProperty(value ="创建日期")
    private Date createAt;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    @ExcelProperty(value ="创建人")
    private String createBy;

    /**
     * 确认状态中文信息 已确认和未确认
     */
    @ApiModelProperty("CONFIRM_STATUS_TXT")
    @ExcelProperty(value = "确认状态文本")
    private String confirmStatusTxt;

    /** 确认日期 */
    @ApiModelProperty("CONFIRM_DATE")
    @ExcelProperty(value ="确认日期")
    private Date confirmDate;

    /** 确认人 */
    @ApiModelProperty("CONFIRM_BY")
    @ExcelProperty(value ="确认人")
    private String confirmBy;

    /** 修改日期 */
    @ApiModelProperty("UPDATE_AT")
    @ExcelProperty(value ="修改日期")
    private Date updateAt;

    /** 修改人 */
    @ApiModelProperty("UPDATE_BY")
    @ExcelProperty(value ="修改人")
    private String updateBy;
}