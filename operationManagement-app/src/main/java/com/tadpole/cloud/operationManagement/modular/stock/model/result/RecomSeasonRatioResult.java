package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
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
@ExcelIgnoreUnannotated
@TableName("RECOM_SEASON_RATIO")
public class RecomSeasonRatioResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 物料编码;不包含禁用物料 */
    @ApiModelProperty("物料编码;不包含禁用物料")
    private String materialCode;

    /** 平台 */
    @ApiModelProperty("平台")
    private String platform;

    /** 事业部 */
    @ApiModelProperty("事业部")
    private String department;

    /** Team */
    @ApiModelProperty("Team")
    private String team;

    /** 区域 */
    @ApiModelProperty("区域")
    private String area;

    @ApiModelProperty("ASIN")
    private String asin;

    /** 日期 */
    @ApiModelProperty("日期")
    private Date bizdate;

    /** 运营大类 */
    @ApiModelProperty("运营大类")
    private String productType;

    /** 产品名称 */
    @ApiModelProperty("产品名称")
    private String productName;

    /** 型号 */
    @ApiModelProperty("型号")
    private String model;

    /** 款式 */
    @ApiModelProperty("款式")
    private String style;

    /** 月份 */
    @ApiModelProperty("月份")
    private String month;

    /** 本月季节系数 */
    @ApiModelProperty("本月季节系数")
    private BigDecimal seasonRatio;

    /** 【本月+1】季节系数 */
    @ApiModelProperty("【本月+1】季节系数")
    private BigDecimal seasonAdd1Ratio;

    /** 【本月+2】季节系数 */
    @ApiModelProperty("【本月+2】季节系数")
    private BigDecimal seasonAdd2Ratio;

    /** 【本月+3】季节系数 */
    @ApiModelProperty("【本月+3】季节系数")
    private BigDecimal seasonAdd3Ratio;

    /** 【本月+4】季节系数 */
    @ApiModelProperty("【本月+4】季节系数")
    private BigDecimal seasonAdd4Ratio;

    /** 【本月+5】季节系数 */
    @ApiModelProperty("【本月+5】季节系数")
    private BigDecimal seasonAdd5Ratio;

    /** 【本月+6】季节系数 */
    @ApiModelProperty("【本月+6】季节系数")
    private BigDecimal seasonAdd6Ratio;

    @ApiModelProperty("TYPE")
    private String type;

    /** 等级 */
    @ApiModelProperty("等级")
    private BigDecimal lev;

    /** 颜色 */
    @ApiModelProperty("颜色")
    private String color;

    /** 季节 */
    @ApiModelProperty("季节")
    private String season;

    /** 生命周期 */
    @ApiModelProperty("生命周期")
    private String lifecycle;

    /** 建议物流方式 */
    @ApiModelProperty("建议物流方式")
    private String deliveryType;

}