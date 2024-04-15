package com.tadpole.cloud.platformSettlement.modular.sales.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 广告预算
 * </p>
 *
 * @author gal
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ADVERTISING_BUDGET")
public class AdvertisingBudgetParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "ID", type = IdType.AUTO)
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

    /** 广告占比 */
    @ApiModelProperty("ADVERTISING_PROPORTION")
    private BigDecimal advertisingProportion;

    /** 一季度占比 */
    @ApiModelProperty("SEASON_ONE_PROPORTION")
    private BigDecimal seasonOneProportion;

    /** 二季度占比 */
    @ApiModelProperty("SEASON_TWO_PROPORTION")
    private BigDecimal seasonTwoProportion;

    /** 三季度占比 */
    @ApiModelProperty("SEASON_THREE_PROPORTION")
    private BigDecimal seasonThreeProportion;

    /** 四季度占比 */
    @ApiModelProperty("SEASON_FOUR_PROPORTION")
    private BigDecimal seasonFourProportion;

    /** 一季度金额 */
    @ApiModelProperty("SEASON_ONE_MONEY")
    private BigDecimal seasonOneMoney;

    /** 二季度金额 */
    @ApiModelProperty("SEASON_TWO_MONEY")
    private BigDecimal seasonTwoMoney;

    /** 三季度金额 */
    @ApiModelProperty("SEASON_THREE_MONEY")
    private BigDecimal seasonThreeMoney;

    /** 四季度金额 */
    @ApiModelProperty("SEASON_FOUR_MONEY")
    private BigDecimal seasonFourMoney;

    /** 年度 */
    @ApiModelProperty("YEAR")
    private String year;

    /** 版本 */
    @ApiModelProperty("VERSION")
    private String version;

    /** 币种 */
    @ApiModelProperty("CURRENCY")
    private String currency;

    /** 确认状态 */
    @ApiModelProperty("CONFIRM_STATUS")
    private BigDecimal confirmStatus;

    /** 创建日期 */
    @ApiModelProperty("CREATE_AT")
    private Date createAt;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 确认日期 */
    @ApiModelProperty("CONFIRM_DATE")
    private Date confirmDate;

    /** 确认人 */
    @ApiModelProperty("CONFIRM_BY")
    private String confirmBy;

    /** 修改日期 */
    @ApiModelProperty("UPDATE_AT")
    private Date updateAt;

    /** 修改人 */
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

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