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
 * 存货需求
 * </p>
 *
 * @author gal
 * @since 2022-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("INVENTORY_DEMAND")
public class InventoryDemandParam implements Serializable, BaseValidatingParam {

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


    /** 一季度 */
    @ApiModelProperty("SEASON_ONE")
    private BigDecimal seasonOne;

    /** 二季度 */
    @ApiModelProperty("SEASON_TWO")
    private BigDecimal seasonTwo;

    /** 三季度 */
    @ApiModelProperty("SEASON_THREE")
    private BigDecimal seasonThree;

    /** 四季度 */
    @ApiModelProperty("SEASON_FOUR")
    private BigDecimal seasonFour;

    /** 年初库存 */
    @ApiModelProperty("OPENING_INVENTORY")
    private BigDecimal openingInventory;

    /** 目标库存 */
    @ApiModelProperty("TARGET_INVENTORY")
    private BigDecimal targetInventory;

    /** 目标库销比 */
    @ApiModelProperty("TARGET_INVENTORY_SALES_RATIO")
    private BigDecimal targetInventorySalesRatio;

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

    @ApiModelProperty("SHOP_NAME")
    private String shopName;

    @ApiModelProperty("RETRACT_LINE")
    private String retractLine;

    @ApiModelProperty("NEWOLD_PRODUCT")
    private String newoldProduct;

    @ApiModelProperty("season")
    private String season;
}