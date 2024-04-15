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
 * 库存周转
 * </p>
 *
 * @author cyt
 * @since 2022-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("INVENTORY_TURNOVER")
@ExcelIgnoreUnannotated
public class InventoryTurnover implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    @ContentFontStyle(color = 10, fontName = "宋体", fontHeightInPoints = 12)
    @ExcelProperty(value = "导入错误备注")
    private String uploadRemark;
    /**
     * 库存周转ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    @ExcelProperty(value = "行ID")
    private BigDecimal id;

    /**
     * 库存周转序号
     */
    @TableField("IDX")
    @ExcelProperty(value = "序号")
    private Integer idx;

    /**
     * 平台
     */
    @TableField("PLATFORM")
    @ExcelProperty(value = "平台")
    private String platform;

    /**
     * 事业部
     */
    @TableField("DEPARTMENT")
    @ExcelProperty(value = "事业部")
    private String department;

    /**
     * Team
     */
    @TableField("TEAM")
    @ExcelProperty(value = "Team")
    private String team;

    /**
     * 运营大类
     */
    @TableField("PRODUCT_TYPE")
    @ExcelProperty(value = "运营大类")
    private String productType;

    /**
     * 销售品牌
     */
    @TableField("COMPANY_BRAND")
    @ExcelProperty(value = "销售品牌")
    private String companyBrand;

    /**
     * 确认年份
     */
    @TableField("YEAR")
    private String year;

    /**
     * 确认月份
     */
    @TableField("MONTH")
    private String month;

    /**
     * 1月
     */
    @TableField("MON1")
    @ExcelProperty(value = "1月")
    private String mon1;

    /**
     * 2月
     */
    @TableField("MON2")
    @ExcelProperty(value = "2月")
    private String mon2;

    /**
     * 3月
     */
    @TableField("MON3")
    @ExcelProperty(value = "3月")
    private String mon3;

    /**
     * 4月
     */
    @TableField("MON4")
    @ExcelProperty(value = "4月")
    private String mon4;

    /**
     * 5月
     */
    @TableField("MON5")
    @ExcelProperty(value = "5月")
    private String mon5;

    /**
     * 6月
     */
    @TableField("MON6")
    @ExcelProperty(value = "6月")
    private String mon6;

    /**
     * 7月
     */
    @TableField("MON7")
    @ExcelProperty(value = "7月")
    private String mon7;

    /**
     * 8月
     */
    @TableField("MON8")
    @ExcelProperty(value = "8月")
    private String mon8;

    /**
     * 9月
     */
    @TableField("MON9")
    @ExcelProperty(value = "9月")
    private String mon9;

    /**
     * 10月
     */
    @TableField("MON10")
    @ExcelProperty(value = "10月")
    private String mon10;

    /**
     * 11月
     */
    @TableField("MON11")
    @ExcelProperty(value = "11月")
    private String mon11;

    /**
     * 12月
     */
    @TableField("MON12")
    @ExcelProperty(value = "12月")
    private String mon12;

    /**
     * 确认状态
     */
    @TableField("COMFIRM_STATUS")
    private Integer comfirmStatus;

    /**
     * 确认时间
     */
    @TableField("COMFIRM_TIME")
    private Date comfirmTime;

    /**
     * 创建人
     */
    @TableField("CREATE_BY")
    private String createBy;


    @TableField("UPDATE_BY")
    private String updateBy;

    /**
     * 创建人工号
     */
    @TableField("CREATE_BY_CODE")
    private String createByCode;


    @TableField("UPDATE_BY_CODE")
    private String updateByCode;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;


    @TableField("CREATE_BY_DEPT")
    private String createByDept;

    @TableField("UPDATE_BY_DEPT")
    private String updateByDept;



}