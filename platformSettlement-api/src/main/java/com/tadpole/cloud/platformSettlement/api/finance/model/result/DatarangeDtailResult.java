package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
* <p>
* AZ结算报告审核
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@ApiModel
public class DatarangeDtailResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty("id")
    private BigDecimal id;

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

}