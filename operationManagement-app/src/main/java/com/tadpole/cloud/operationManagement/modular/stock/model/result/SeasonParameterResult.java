package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
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
@ExcelIgnoreUnannotated
@TableName("STOCK_SEASON_PARAMETER")
public class SeasonParameterResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /**
     * 平台`
     */
    @ApiModelProperty("平台")
    @ExcelProperty("平台")
    private String platformName;

    /**
     * 事业部
     */
    @ApiModelProperty("事业部")
    @ExcelProperty("事业部")
    private String department;

    /**
     * Team
     */
    @ApiModelProperty("Team")
    @ExcelProperty("Team")
    private String team;

    /**
     * 区域
     */
    @ApiModelProperty("区域")
    @ExcelProperty("区域")
    private String area;

    /**
     * 运营大类
     */
    @ApiModelProperty("运营大类")
    @ExcelProperty("运营大类")
    private String productType;

    /**
     * 适用品牌
     */
    @ApiModelProperty("适用品牌")
    @ExcelProperty("适用品牌")
    private String brand;

    /**
     * 型号
     */
    @ApiModelProperty("型号")
    @ExcelProperty("型号")
    private String model;

    /**
     * 节日标签
     */
    @ApiModelProperty("节日标签")
    @ExcelProperty("节日标签")
    private String festivalLabel;

    /**
     * 季节标签
     */
    @ApiModelProperty("季节标签")
    @ExcelProperty("季节标签")
    private String season;

    /** 产品名称 */
    @ApiModelProperty("产品名称")
    @ExcelProperty("产品名称")
    private String productName;

    /**
     * 优先级
     */
    @ApiModelProperty("优先级")
    @ExcelProperty("优先级")
    private int priority;

    /**
     * 1月
     */
    @ApiModelProperty("1月")
    @ExcelProperty("1月")
    private BigDecimal mon1;

    /**
     * 2月
     */
    @ApiModelProperty("2月")
    @ExcelProperty("2月")
    private BigDecimal mon2;

    /**
     * 3月
     */
    @ApiModelProperty("3月")
    @ExcelProperty("3月")
    private BigDecimal mon3;

    /**
     * 4月
     */
    @ApiModelProperty("4月")
    @ExcelProperty("4月")
    private BigDecimal mon4;

    /**
     * 5月
     */
    @ApiModelProperty("5月")
    @ExcelProperty("5月")
    private BigDecimal mon5;

    /**
     * 6月
     */
    @ApiModelProperty("6月")
    @ExcelProperty("6月")
    private BigDecimal mon6;

    /**
     * 7月
     */
    @ApiModelProperty("7月")
    @ExcelProperty("7月")
    private BigDecimal mon7;

    /**
     * 8月
     */
    @ApiModelProperty("8月")
    @ExcelProperty("8月")
    private BigDecimal mon8;

    /**
     * 9月
     */
    @ApiModelProperty("9月")
    @ExcelProperty("9月")
    private BigDecimal mon9;

    /**
     * 10月
     */
    @ApiModelProperty("10月")
    @ExcelProperty("10月")
    private BigDecimal mon10;

    /**
     * 11月
     */
    @ApiModelProperty("11月")
    @ExcelProperty("11月")
    private BigDecimal mon11;

    /**
     * 12月
     */
    @ApiModelProperty("12月")
    @ExcelProperty("12月")
    private BigDecimal mon12;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    @ExcelProperty("创建人")
    private String createBy;

    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    @ExcelProperty("修改人")
    private String updateBy;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @ExcelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @ExcelProperty("更新时间")
    private Date updateTime;



    /**
     * 是否启用（0：未删除,1：已删除）默认：0
     */
    @ApiModelProperty("是否启用（0：未删除,1：已删除）默认：0")
    @ExcelProperty("是否启用（0：未删除,1：已删除）默认：0")
    private int isDelete;

}