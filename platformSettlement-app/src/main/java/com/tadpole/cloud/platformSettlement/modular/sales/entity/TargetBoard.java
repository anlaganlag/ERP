package com.tadpole.cloud.platformSettlement.modular.sales.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 *
 * </p>
 *
 * @author gal
 * @since 2022-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("TARGET_BOARD")
@ExcelIgnoreUnannotated
public class TargetBoard implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableField("PLATFORM")
    @ExcelProperty(value ="")
    private String platform;

    @TableField("TEAM")
    @ExcelProperty(value ="")
    private String team;

    @TableField("SALES_TARGET")
    @ExcelProperty(value ="")
    private BigDecimal salesTarget;

    @TableField("SALES_VOLUME_TARGET")
    @ExcelProperty(value ="")
    private BigDecimal salesVolumeTarget;

    @TableField("COLLECTION_TARGET")
    @ExcelProperty(value ="")
    private BigDecimal collectionTarget;

    @TableField("PROFIT_TARGET")
    @ExcelProperty(value ="")
    private BigDecimal profitTarget;

    @TableField("ADVERTISING_PROPORTION_BUDGET")
    @ExcelProperty(value ="")
    private BigDecimal advertisingProportionBudget;

    @TableField("PURCHASE_BUDGET")
    @ExcelProperty(value ="")
    private BigDecimal purchaseBudget;

    @TableField("ID")
    @ExcelProperty(value ="")
    private BigDecimal id;

}