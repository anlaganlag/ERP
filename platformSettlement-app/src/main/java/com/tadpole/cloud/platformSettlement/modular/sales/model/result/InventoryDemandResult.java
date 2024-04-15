package com.tadpole.cloud.platformSettlement.modular.sales.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 存货需求
 * </p>
 *
 * @author gal
 * @since 2022-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("INVENTORY_DEMAND")
@ExcelIgnoreUnannotated
public class InventoryDemandResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /**
     * 平台
     */
    @ApiModelProperty("PLATFORM")
    @ExcelProperty(value = "平台")
    private String platform;

    /**
     * 事业部
     */
    @ApiModelProperty("DEPARTMENT")
    @ExcelProperty(value = "事业部")
    private String department;

    /**
     * Team
     */
    @ApiModelProperty("team")
    @ExcelProperty(value = "Team")
    private String team;

    /**
     * Team
     */
    @ApiModelProperty("product_type")
    @ExcelProperty(value = "运营大类")
    private String productType;

    /**
     * Team
     */
    @ApiModelProperty("company_brand")
    @ExcelProperty(value = "销售品牌")
    private String companyBrand;

    @ContentStyle(dataFormat = 2)
    @ExcelProperty(value = "年度存货预算")
    private BigDecimal yearTarget;

    /**
     * 一季度
     */
    @ContentStyle(dataFormat = 1)
    @ApiModelProperty("SEASON_ONE")
    @ExcelProperty(value = "一季度")
    private BigDecimal seasonOne;

    /**
     * 二季度
     */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SEASON_TWO")
    @ExcelProperty(value = "二季度")
    private BigDecimal seasonTwo;

    /**
     * 三季度
     */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SEASON_THREE")
    @ExcelProperty(value = "三季度")
    private BigDecimal seasonThree;

    /**
     * 四季度
     */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("SEASON_FOUR")
    @ExcelProperty(value = "四季度")
    private BigDecimal seasonFour;

    /**
     * 年初库存
     */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("OPENING_INVENTORY")
    @ExcelProperty(value = "年初库存")
    private BigDecimal openingInventory;

    /**
     * 目标库存
     */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("TARGET_INVENTORY")
    @ExcelProperty(value = "目标库存")
    private BigDecimal targetInventory;

    /**
     * 目标库销比
     */
    @ContentStyle(dataFormat = 2)
    @ApiModelProperty("TARGET_INVENTORY_SALES_RATIO")
    @ExcelProperty(value = "目标库销比")
    private BigDecimal targetInventorySalesRatio;

    /**
     * 年度
     */
    @ApiModelProperty("YEAR")
    @ExcelProperty(value = "年度")
    private BigDecimal year;

    /**
     * 版本
     */
    @ApiModelProperty("VERSION")
    @ExcelProperty(value = "版本")
    private String version;

    /**
     * 币种
     */
    @ApiModelProperty("CURRENCY")
    @ExcelProperty(value = "币种")
    private String currency;

    /**
     * 确认状态
     */
    @ApiModelProperty("CONFIRM_STATUS")
    private BigDecimal confirmStatus;

    /**
     * 确认状态中文信息 已确认和未确认
     */
    @ApiModelProperty("CONFIRM_STATUS")
    @ExcelProperty(value = "确认状态文本")
    private String confirmStatusTxt;

    /**
     * 创建日期
     */
    @ApiModelProperty("CREATE_AT")
    @ExcelProperty(value = "创建日期")
    private Date createAt;

    /**
     * 创建人
     */
    @ApiModelProperty("CREATE_BY")
    @ExcelProperty(value = "创建人")
    private String createBy;

    /**
     * 确认日期
     */
    @ApiModelProperty("CONFIRM_DATE")
    @ExcelProperty(value = "确认日期")
    private Date confirmDate;

    /**
     * 确认人
     */
    @ApiModelProperty("CONFIRM_BY")
    @ExcelProperty(value = "确认人")
    private String confirmBy;

    /**
     * 修改日期
     */
    @ApiModelProperty("UPDATE_AT")
    @ExcelProperty(value = "修改日期")
    private Date updateAt;

    /**
     * 修改人
     */
    @ApiModelProperty("UPDATE_BY")
    @ExcelProperty(value = "修改人")
    private String updateBy;

    @ApiModelProperty("ID")
    private BigDecimal id;

    @ApiModelProperty("TARGET_INVENTORY_TOTAL")
    private BigDecimal targetInventoryTotal;
}