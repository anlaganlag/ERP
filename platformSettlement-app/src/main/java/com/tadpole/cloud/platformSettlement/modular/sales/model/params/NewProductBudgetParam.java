package com.tadpole.cloud.platformSettlement.modular.sales.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class NewProductBudgetParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

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
    private String productType;

    /** 一季度 */
    @ApiModelProperty("SEASON_ONE")
    private BigDecimal seasonOne;

    /** 二季度 */
    @ApiModelProperty("SEASON_TWO")
    private BigDecimal seasonTwo;

    /** 三季度 */
    @ApiModelProperty("SEASON_THREE")
    private BigDecimal seasonThree;

    /** 四季度 */
    @ApiModelProperty("SEASON_FOUR")
    private BigDecimal seasonFour;

    /** 产品线一季度 */
    @ApiModelProperty("SEASON_ONE_LINE")
    private BigDecimal seasonOneLine;

    /** 产品线二季度 */
    @ApiModelProperty("SEASON_TWO_LINE")
    private BigDecimal seasonTwoLine;

    /** 产品线三季度 */
    @ApiModelProperty("SEASON_THREE_LINE")
    private BigDecimal seasonThreeLine;

    /** 产品线四季度 */
    @ApiModelProperty("SEASON_FOUR_LINE")
    private BigDecimal seasonFourLine;

    /** 产品线销售收入目标全年汇总 */
    @ApiModelProperty("LINE_YEAR_TARGET")
    private BigDecimal lineYearTarget;

    /** 产品线销售收入目标全年汇总 */
    @ApiModelProperty("YEAR_TARGET_LINE")
    private BigDecimal yearTargetLine;

    /** 一季度占比 */
    @ApiModelProperty("SEASON_ONE_PROPORTION")
    private BigDecimal seasonOneProportion;

    /** 二季度占比 */
    @ApiModelProperty("SEASON_TWO_PROPORTION")
    private BigDecimal seasonTwoProportion;

    /** 三季度占比 */
    @ApiModelProperty("SEASON_THREE_PROPORTION")
    private BigDecimal seasonThreeProportion;

    /** 四季度占比 */
    @ApiModelProperty("SEASON_FOUR_PROPORTION")
    private BigDecimal seasonFourProportion;

    /** 年度 */
    @ApiModelProperty("YEAR")
    private String year;

    /** 版本 */
    @ApiModelProperty("VERSION")
    private String version;

    /** 币种 */
    @ApiModelProperty("CURRENCY")
    private String currency;

    /** 确认状态 */
    @ApiModelProperty("CONFIRM_STATUS")
    private BigDecimal confirmStatus;

    /** 创建日期 */
    @ApiModelProperty("CREATE_AT")
    private Date createAt;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 确认日期 */
    @ApiModelProperty("CONFIRM_DATE")
    private Date confirmDate;

    /** 确认人 */
    @ApiModelProperty("CONFIRM_BY")
    private String confirmBy;

    /** 修改日期 */
    @ApiModelProperty("UPDATE_AT")
    private Date updateAt;

    /** 修改人 */
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

    @ApiModelProperty("ID")
    private BigDecimal id;

    @ApiModelProperty("TEAM")
    private String team;

    @ApiModelProperty("COMPANY_BRAND")
    private String companyBrand;

    private List platforms;

    private List departments;

    private List teams;

    private List productTypes;

    private List companyBrands;

    private List secondLabels;
}