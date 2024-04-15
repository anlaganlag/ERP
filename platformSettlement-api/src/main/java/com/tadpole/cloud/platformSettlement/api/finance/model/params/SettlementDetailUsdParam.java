package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * ERP固定汇率
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
@Data
@ApiModel
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettlementDetailUsdParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty("id")
    private BigDecimal id;

    /**
     * 会计期间
     */
    @ApiModelProperty("FISCAL_PERIOD")
    private String fiscalPeriod;

    /**
     * 结算id
     */
    @ApiModelProperty("SETTLEMENT_ID")
    private String settlementId;

    /**
     * 报告类型
     */
    @ApiModelProperty("REPORT_TYPE")
    private String reportType;

    /**
     * 收入确认类型
     */
    @ApiModelProperty("INCOME_TYPE")
    private String incomeType;

    /**
     * 账号
     */
    @ApiModelProperty("SHOP_NAME")
    private String shopName;

    private List shopNames;


    /**
     * 站点
     */
    @ApiModelProperty("SITE")
    private String site;

    @ApiModelProperty("SKU")
    private String sku;

    @ApiModelProperty("DEPARTMENT")
    private String department;

    @ApiModelProperty("TEAM")
    private String team;

    @ApiModelProperty("CONFIRM_STATUS")
    private String confirmStatus;

    @ApiModelProperty("PRODUCT_TYPE")
    private String productType;

    private Boolean isDefect;

    private List skus;

    private List sites;

    @ApiModelProperty("确认时间")
    private Date confirmDate;

    @ApiModelProperty("确认人")
    private String confirmBy;
}