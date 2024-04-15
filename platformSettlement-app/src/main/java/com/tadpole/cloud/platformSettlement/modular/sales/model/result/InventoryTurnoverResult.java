package com.tadpole.cloud.platformSettlement.modular.sales.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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
@ExcelIgnoreUnannotated
@TableName("INVENTORY_TURNOVER")
public class InventoryTurnoverResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @ExcelProperty(value = "行ID")
    private BigDecimal id;

    @ApiModelProperty("IDX")
    @ExcelProperty(value = "序号")
    private String idx;

    /**
     * 确认年份
     */
    @ApiModelProperty("YEAR")
    @ExcelProperty(value = "年份")
    private String year;

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
    @ApiModelProperty("TEAM")
    @ExcelProperty(value = "Team")
    private String team;

    /**
     * 运营大类
     */
    @ApiModelProperty("PRODUCT_TYPE")
    @ExcelProperty(value = "运营大类")
    private String productType;

    /**
     * 销售品牌
     */
    @ApiModelProperty("COMPANY_BRAND")
    @ExcelProperty(value = "销售品牌")
    private String companyBrand;

    /**
     * 确认月份
     */
    @ApiModelProperty("MONTH")
    private String month;

    /**
     * 1月
     */
    @ApiModelProperty("MON1")
    @ExcelProperty(value = "1月")
    private String mon1;

    /**
     * 2月
     */
    @ApiModelProperty("MON2")
    @ExcelProperty(value = "2月")
    private String mon2;

    /**
     * 3月
     */
    @ApiModelProperty("MON3")
    @ExcelProperty(value = "3月")
    private String mon3;

    /**
     * 4月
     */
    @ApiModelProperty("MON4")
    @ExcelProperty(value = "4月")
    private String mon4;

    /**
     * 5月
     */
    @ApiModelProperty("MON5")
    @ExcelProperty(value = "5月")
    private String mon5;

    /**
     * 6月
     */
    @ApiModelProperty("MON6")
    @ExcelProperty(value = "6月")
    private String mon6;

    /**
     * 6月
     */
    @ApiModelProperty("MON7")
    @ExcelProperty(value = "7月")
    private String mon7;

    /**
     * 8月
     */
    @ApiModelProperty("MON8")
    @ExcelProperty(value = "8月")
    private String mon8;

    /**
     * 9月
     */
    @ApiModelProperty("MON9")
    @ExcelProperty(value = "9月")
    private String mon9;

    /**
     * 10月
     */
    @ApiModelProperty("MON10")
    @ExcelProperty(value = "10月")
    private String mon10;

    /**
     * 11月
     */
    @ApiModelProperty("MON11")
    @ExcelProperty(value = "11月")
    private String mon11;

    /**
     * 12月
     */
    @ApiModelProperty("MON12")
    @ExcelProperty(value = "12月")
    private String mon12;

    /**
     * 确认状态
     */
    @ApiModelProperty("COMFIRM_STATUS")
    private String comfirmStatus;

    /**
     * 确认时间
     */
    @ApiModelProperty("COMFIRM_TIME")
    private Date comfirmTime;

    /**
     * 创建人
     */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /**
     * 创建人工号
     */
    @ApiModelProperty("CREATE_BY_CODE")
    private String createByCode;

    /**
     * 创建时间
     */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;
}