package com.tadpole.cloud.platformSettlement.modular.sales.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 利润目标
 * </p>
 *
 * @author gal
 * @since 2022-03-07
 */
@Data
@ApiModel
public class ProfitTargetParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty("id")
    private BigDecimal id;

    /** 平台 */
    @ApiModelProperty("PLATFORM")
    private String platform;

    /** 事业部 */
    @ApiModelProperty("DEPARTMENT")
    private String department;

    /** Team */
    @ApiModelProperty("TEAM")
    private String team;

    /** 运营大类 */
    @ApiModelProperty("PRODUCT_TYPE")
    private String productType;

    /** 销售品牌 */
    @ApiModelProperty("COMPANY_BRAND")
    private String companyBrand;

    /** 年度 */
    @ApiModelProperty("YEAR")
    private String year;

    /** 版本 */
    @ApiModelProperty("VERSION")
    private String version;

    /** 币种 */
    @ApiModelProperty("CURRENCY")
    private String currency;

    private List platforms;

    private List departments;

    private List teams;

    private List productTypes;

    private List companyBrands;

    /** 店铺 */
    @ApiModelProperty("SHOP_NAME")
    private String shopName;

    /** 新旧品 */
    @ApiModelProperty("NEWOLD_PRODUCT")
    private String newoldProduct;

    /** 收缩线 */
    @ApiModelProperty("RETRACT_LINE")
    private String retractLine;

    private List newoldProducts;

    private List retractLines;
}