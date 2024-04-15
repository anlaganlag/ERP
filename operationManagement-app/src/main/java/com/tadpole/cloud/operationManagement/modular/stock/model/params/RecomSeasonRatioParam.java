package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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
public class RecomSeasonRatioParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 物料编码;不包含禁用物料 */
    @ApiModelProperty("MATERIAL_CODE")
    private String materialCode;

    /** 平台 */
    @ApiModelProperty("PLATFORM")
    private String platform;

    /** 事业部 */
    @ApiModelProperty("DEPARTMENT")
    private String department;

    /** Team */
    @ApiModelProperty("TEAM")
    private String team;

    /** 区域 */
    @ApiModelProperty("AREA")
    private String area;

    @ApiModelProperty("ASIN")
    private String asin;

    /** 日期 */
    @ApiModelProperty("BIZDATE")
    private Date bizdate;

    /** 运营大类 */
    @ApiModelProperty("PRODUCT_TYPE")
    private String productType;

    /** 产品名称 */
    @ApiModelProperty("PRODUCT_NAME")
    private String productName;

    /** 型号 */
    @ApiModelProperty("MODEL")
    private String model;

    /** 款式 */
    @ApiModelProperty("STYLE")
    private String style;

    /** 月份 */
    @ApiModelProperty("MONTH")
    private String month;

    /** 本月季节系数 */
    @ApiModelProperty("SEASON_RATIO")
    private BigDecimal seasonRatio;

    /** 【本月+1】季节系数 */
    @ApiModelProperty("SEASON_ADD1_RATIO")
    private BigDecimal seasonAdd1Ratio;

    /** 【本月+2】季节系数 */
    @ApiModelProperty("SEASON_ADD2_RATIO")
    private BigDecimal seasonAdd2Ratio;

    /** 【本月+3】季节系数 */
    @ApiModelProperty("SEASON_ADD3_RATIO")
    private BigDecimal seasonAdd3Ratio;

    /** 【本月+4】季节系数 */
    @ApiModelProperty("SEASON_ADD4_RATIO")
    private BigDecimal seasonAdd4Ratio;

    /** 【本月+5】季节系数 */
    @ApiModelProperty("SEASON_ADD5_RATIO")
    private BigDecimal seasonAdd5Ratio;

    /** 【本月+6】季节系数 */
    @ApiModelProperty("SEASON_ADD6_RATIO")
    private BigDecimal seasonAdd6Ratio;

    @ApiModelProperty("TYPE")
    private String type;

    /** 等级 */
    @ApiModelProperty("LEV")
    private BigDecimal lev;

    /** 颜色 */
    @ApiModelProperty("COLOR")
    private String color;

    /** 季节 */
    @ApiModelProperty("SEASON")
    private String season;

    /** 生命周期 */
    @ApiModelProperty("LIFECYCLE")
    private String lifecycle;

    /** 建议物流方式 */
    @ApiModelProperty("DELIVERY_TYPE")
    private String deliveryType;

}