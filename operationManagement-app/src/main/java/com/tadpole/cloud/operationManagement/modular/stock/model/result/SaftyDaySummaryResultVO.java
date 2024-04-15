package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SaftyDayValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class SaftyDaySummaryResultVO implements Serializable, BaseValidatingParam {

    @ExcelProperty("Team")
    private static final long serialVersionUID = 1L;


    /** id */
    @TableId(value = "ID", type = IdType.AUTO)
    @ExcelProperty("id")
    private BigDecimal id;

    /** 平台 */
    @ApiModelProperty("平台")
    @ExcelProperty("平台")
    private String platformName;

    /** 区域 */
    @ApiModelProperty("区域")
    @ExcelProperty("区域")
    private String area;

    /** 事业部 */
    @ApiModelProperty("事业部")
    @ExcelProperty("事业部")
    private String department;

    /** Team */
    @ApiModelProperty("Team")
    @ExcelProperty("Team")
    private String team;

    /** 运营大类 */
    @ApiModelProperty("运营大类")
    @ExcelProperty("运营大类")
    private String productType;

    /** 产品名称 */
    @ApiModelProperty("产品名称")
    @ExcelProperty("产品名称")
    private String productName;

    /** 款式 */
    @ApiModelProperty("款式")
    @ExcelProperty("款式")
    private String style;

    /** 型号 */
    @ApiModelProperty("型号")
    @ExcelProperty("型号")
    private String model;

    /** 优先级 */
    @ApiModelProperty("优先级")
    @ExcelProperty("优先级")
    private int priority;

    /** 是否启用（0：禁用,1：启用）默认：1 */
    @ApiModelProperty("是否启用（0：禁用,1：启用）默认：1")
    @ExcelProperty("是否启用（0：禁用,1：启用）默认：1")
    private int isEnable;

    /** 是否启用（0：未删除,1：已删除）默认：0 */
    @ApiModelProperty("是否启用（0：未删除,1：已删除）默认：0")
    @ExcelProperty("是否启用（0：未删除,1：已删除）默认：0")
    private int isDelete;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    @ExcelProperty("创建人")
    private String createBy;

    /** 修改人 */
    @ApiModelProperty("UPDATE_BY")
    @ExcelProperty("修改人")
    private String updateBy;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    @ExcelProperty("创建时间")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    @ExcelProperty("更新时间")
    private Date updateTime;

    List<SaftyDayValue> valueList =new ArrayList<>();

}