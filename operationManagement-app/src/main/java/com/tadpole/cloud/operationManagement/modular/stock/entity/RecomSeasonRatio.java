package com.tadpole.cloud.operationManagement.modular.stock.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 季节系数规则表-最细维度匹配
    * </p>
*
* @author lsy
* @since 2022-06-23
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RECOM_SEASON_RATIO")
@ExcelIgnoreUnannotated
public class    RecomSeasonRatio implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 物料编码;不包含禁用物料 */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /** 平台 */
    @TableField("PLATFORM")
    private String platform;

    /** 事业部 */
    @TableField("DEPARTMENT")
    private String department;

    /** Team */
    @TableField("TEAM")
    private String team;

    /** 区域 */
    @TableField("AREA")
    private String area;

    @TableField("ASIN")
    private String asin;

    /** 日期 */
    @TableField("BIZDATE")
    private Date bizdate;

    /** 运营大类 */
    @TableField("PRODUCT_TYPE")
    private String productType;

    /** 产品名称 */
    @TableField("PRODUCT_NAME")
    private String productName;

    /** 型号 */
    @TableField("MODEL")
    private String model;

    /** 款式 */
    @TableField("STYLE")
    private String style;

    /** 月份 */
    @TableField("MONTH")
    private String month;

    /** 本月季节系数 */
    @TableField("SEASON_RATIO")
    private BigDecimal seasonRatio;

    /** 【本月+1】季节系数 */
    @TableField("SEASON_ADD1_RATIO")
    private BigDecimal seasonAdd1Ratio;

    /** 【本月+2】季节系数 */
    @TableField("SEASON_ADD2_RATIO")
    private BigDecimal seasonAdd2Ratio;

    /** 【本月+3】季节系数 */
    @TableField("SEASON_ADD3_RATIO")
    private BigDecimal seasonAdd3Ratio;

    /** 【本月+4】季节系数 */
    @TableField("SEASON_ADD4_RATIO")
    private BigDecimal seasonAdd4Ratio;

    /** 【本月+5】季节系数 */
    @TableField("SEASON_ADD5_RATIO")
    private BigDecimal seasonAdd5Ratio;

    /** 【本月+6】季节系数 */
    @TableField("SEASON_ADD6_RATIO")
    private BigDecimal seasonAdd6Ratio;

    @TableField("TYPE")
    private String type;

    /** 等级 */
    @TableField("LEV")
    private BigDecimal lev;

    /** 颜色 */
    @TableField("COLOR")
    private String color;

    /** 季节 */
    @TableField("SEASON")
    private String season;

    /** 生命周期 */
    @TableField("LIFECYCLE")
    private String lifecycle;

    /** 建议物流方式 */
    @TableField("DELIVERY_TYPE")
    private String deliveryType;

}