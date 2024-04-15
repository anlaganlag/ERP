package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 安全天数概要表
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
@TableName("STOCK_SAFTY_DAY_SUMMARY")
public class SaftyDaySummaryResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /**
     * 平台
     */
    @ApiModelProperty("平台")
    @ExcelProperty("平台")
    private String platformName;

    /**
     * 区域
     */
    @ApiModelProperty("区域")
    @ExcelProperty("区域")
    private String area;

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
     * 运营大类
     */
    @ApiModelProperty("运营大类")
    @ExcelProperty("运营大类")
    private String productType;

    /**
     * 产品名称
     */
    @ApiModelProperty("产品名称")
    @ExcelProperty("产品名称")
    private String productName;

    /**
     * 款式
     */
    @ApiModelProperty("款式")
    @ExcelProperty("款式")
    private String style;

    /**
     * 型号
     */
    @ApiModelProperty("型号")
    @ExcelProperty("型号")
    private String model;

    /**
     * 优先级
     */
    @ApiModelProperty("优先级")
    @ExcelProperty("优先级")
    private int priority;





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
     * STOCK_SAFTY_DAY_SUMMARY表对应数据id
     */
    @ApiModelProperty("STOCK_SAFTY_DAY_SUMMARY表对应数据id")
    @ExcelProperty("STOCK_SAFTY_DAY_SUMMARY表对应数据id")
    private BigDecimal summaryId;

    /**
     * 排序编号
     */
    @ApiModelProperty("排序编号")
    @ExcelProperty("排序编号")
    private BigDecimal sortNum;

    /**
     * 日均销量低
     */
    @ApiModelProperty("日均销量低")
    @ExcelProperty("日均销量低")
    private BigDecimal salesLow;

    /**
     * 日均销量高
     */
    @ApiModelProperty("日均销量高")
    @ExcelProperty("日均销量高")
    private BigDecimal salesHigh;

    /**
     * 安全销售天数
     */
    @ApiModelProperty("安全销售天数")
    @ExcelProperty("安全销售天数")
    private BigDecimal safeSaleDays;

    /**
     * 订货天数
     */
    @ApiModelProperty("订货天数")
    @ExcelProperty("订货天数")
    private BigDecimal orderGoodsDays;

    /**
     * 是否启用（0：禁用,1：启用）默认：1
     */
    @ApiModelProperty("是否启用（0：禁用,1：启用）默认：1")
    @ExcelProperty("是否启用（0：禁用,1：启用）默认：1")
    private int isEnable;

    /**
     * 是否启用（0：未删除,1：已删除）默认：0
     */
    @ApiModelProperty("是否启用（0：未删除,1：已删除）默认：0")
    @ExcelProperty("是否启用（0：未删除,1：已删除）默认：0")
    private int isDelete;

}