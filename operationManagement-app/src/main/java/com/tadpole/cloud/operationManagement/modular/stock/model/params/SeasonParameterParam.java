package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
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
@TableName("STOCK_SEASON_PARAMETER")
public class SeasonParameterParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private BigDecimal id;

    /** 平台 */
    @ApiModelProperty("PLATFORM_NAME")
    private String platformName;

    /** 事业部 */
    @ApiModelProperty("DEPARTMENT")
    private String department;

    /** Team */
    @ApiModelProperty("TEAM")
    private String team;

    /** 区域 */
    @ApiModelProperty("AREA")
    private String area;

    /** 运营大类 */
    @ApiModelProperty("PRODUCT_TYPE")
    private String productType;

    /** 适用品牌 */
    @ApiModelProperty("BRAND")
    private String brand;

    /** 节日标签 */
    @ApiModelProperty("FESTIVAL_LABEL")
    private String festivalLabel;

    /** 季节标签 */
    @ApiModelProperty("SEASON")
    private String season;

    /** 产品名称 */
    @ApiModelProperty("PRODUCT_NAME")
    private String productName;

    /** 优先级 */
    @ApiModelProperty("PRIORITY")
    private int priority;

    /** 1月 */
    @ApiModelProperty("MON1")
    private BigDecimal mon1;

    /** 2月 */
    @ApiModelProperty("MON2")
    private BigDecimal mon2;

    /** 3月 */
    @ApiModelProperty("MON3")
    private BigDecimal mon3;

    /** 4月 */
    @ApiModelProperty("MON4")
    private BigDecimal mon4;

    /** 5月 */
    @ApiModelProperty("MON5")
    private BigDecimal mon5;

    /** 6月 */
    @ApiModelProperty("MON6")
    private BigDecimal mon6;

    /** 7月 */
    @ApiModelProperty("MON7")
    private BigDecimal mon7;

    /** 8月 */
    @ApiModelProperty("MON8")
    private BigDecimal mon8;

    /** 9月 */
    @ApiModelProperty("MON9")
    private BigDecimal mon9;

    /** 10月 */
    @ApiModelProperty("MON10")
    private BigDecimal mon10;

    /** 11月 */
    @ApiModelProperty("MON11")
    private BigDecimal mon11;

    /** 12月 */
    @ApiModelProperty("MON12")
    private BigDecimal mon12;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 修改人 */
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("UPDATE_TIME")
    private Date updateTime;

    /** 型号 */
    @ApiModelProperty("MODEL")
    private String model;

    /** 是否启用（0：未删除,1：已删除）默认：0 */
    @ApiModelProperty("是否启用（0：未删除,1：已删除）默认：0")
    private int isDelete;

}