package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
* <p>
*
* </p>
*
* @author gal
* @since 2022-04-28
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LingxingDatarangeParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    private BigDecimal id;

    private Long sid;

    /** 平台 */
    @ApiModelProperty("PLATFORM_NAME")
    private String platformName;

    /** 账号 */
    @ApiModelProperty("SHOP_NAME")
    private String shopName;

    /** 站点 */
    @ApiModelProperty("SITE")
    private String site;

    /** 结算id */
    @ApiModelProperty("SETTLEMENT_ID")
    private String settlementId;

    /** 开始时间 */
    @ApiModelProperty("START_TIME")
    private String startTime;

    /** 结束时间 */
    @ApiModelProperty("END_TIME")
    private String endTime;

    /** 单据类型 */
    @ApiModelProperty("BILL_TYPE")
    private String billType;

    /** 报告类型 */
    @ApiModelProperty("REPORT_TYPE")
    private String reportType;

    /** 解析状态 */
    @ApiModelProperty("PARSE_STATUS")
    private String parseStatus;

    private String yearMonth;

}