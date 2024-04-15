package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 站内自动分摊
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
public class StationAutoAllocationParam extends BaseRequest implements Serializable, BaseValidatingParam {

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
     * 报告类型
     */
    @ApiModelProperty("REPORT_TYPE")
    private String reportType;

    /**
     * 账号
     */
    @ApiModelProperty("SHOP_NAME")
    private String shopName;

    /**
     * 站点
     */
    @ApiModelProperty("SITE")
    private String site;

    /**
     * 事业部
     */
    @ApiModelProperty("DEPARTMENT")
    private String department;

    /**
     * Team
     */
    @ApiModelProperty("TEAM")
    private String team;

    /**
     * 确认状态
     */
    @ApiModelProperty("CONFIRM_STATUS")
    private String confirmStatus;

    /**
     * 是否手动分摊
     */
    @ApiModelProperty("IS_MANUAL")
    private String iSManual;

    private List sites;

    private String confirmBy;


}