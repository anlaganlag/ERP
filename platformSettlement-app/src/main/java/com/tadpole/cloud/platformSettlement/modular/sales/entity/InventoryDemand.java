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
public class InventoryDemand implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
    @ExcelProperty(value= "导入错误备注")
    private String uploadRemark;

    /** 平台 */
    @TableField("PLATFORM")
    @ExcelProperty(value= "平台")
    private String platform;

    /** 事业部 */
    @TableField("DEPARTMENT")
    @ExcelProperty(value= "事业部")
    private String department;

    /** Team */
    @TableField("TEAM")
    @ExcelProperty(value= "Team")
    private String team;

    /** 运营大类 */
    @TableField("PRODUCT_TYPE")
    @ExcelProperty(value= "运营大类")
    private String productType;

    /** 销售品牌 */
    @TableField("COMPANY_BRAND")
    @ExcelProperty(value= "销售品牌")
    private String companyBrand;


    /** 一季度 */
    @TableField("SEASON_ONE")
    @ExcelProperty(value= "Q1")
    private BigDecimal seasonOne;

    /** 二季度 */
    @TableField("SEASON_TWO")
    @ExcelProperty(value= "Q2")
    private BigDecimal seasonTwo;

    /** 三季度 */
    @TableField("SEASON_THREE")
    @ExcelProperty(value= "Q3")
    private BigDecimal seasonThree;

    /** 四季度 */
    @TableField("SEASON_FOUR")
    @ExcelProperty(value= "Q4")
    private BigDecimal seasonFour;

    /** 年初库存 */
    @TableField("OPENING_INVENTORY")
    @ExcelProperty(value= "年初库存")
    private BigDecimal openingInventory;

    /** 目标库存 */
    @TableField("TARGET_INVENTORY")
    private BigDecimal targetInventory;

    /** 目标库销比 */
    @TableField("TARGET_INVENTORY_SALES_RATIO")
    @ExcelProperty(value= "目标库销比")
    private BigDecimal targetInventorySalesRatio;

    /** 币种 */
    @TableField("CURRENCY")
    private String currency;

    /** 年度 */
    @TableField("YEAR")
    private String year;

    /** 版本 */
    @TableField("VERSION")
    private String version;


    /** 确认状态 */
    @TableField("CONFIRM_STATUS")
    private BigDecimal confirmStatus;

    /** 创建日期 */
    @TableField("CREATE_AT")
    private Date createAt;

    /** 创建人 */
    @TableField("CREATE_BY")
    private String createBy;

    /** 确认日期 */
    @TableField("CONFIRM_DATE")
    private Date confirmDate;

    /** 确认人 */
    @TableField("CONFIRM_BY")
    private String confirmBy;

    /** 修改日期 */
    @TableField("UPDATE_AT")
    private Date updateAt;

    /** 修改人 */
    @TableField("UPDATE_BY")
    private String updateBy;




    /** id */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;



}