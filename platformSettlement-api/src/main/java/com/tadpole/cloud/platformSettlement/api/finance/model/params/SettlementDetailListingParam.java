package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class SettlementDetailListingParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty("id")
    private BigDecimal id;

    /**
     * 会计期间
     */
    @ApiModelProperty("会计期间")
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

    @ApiModelProperty("MATERIAL_CODE")
    private String materialCode;
    /**
     * 账号
     */
    @ApiModelProperty("账号")
    private String shopName;

    /**
     * 站点
     */
    @ApiModelProperty("SITE")
    private String site;

    @ApiModelProperty("SKU")
    private String sku;

    @ApiModelProperty("确认状态 0：未确认，1：已确认")
    private String confirmStatus;

    @ApiModelProperty("LATEST_DATE")
    private LocalDateTime latestDate;

    @ApiModelProperty("确认类型 0：正常，1：异常且超时，2：异常未超时")
    private int confirmType;

    private Boolean isDefect;

    private Boolean isSkuZero;

    private List skus;

    private List sites;

    private List materialCodes;

    private List<String> emailList;

    @ApiModelProperty("确认时间")
    private Date confirmDate;

    @ApiModelProperty("确认人")
    private String confirmBy;
}