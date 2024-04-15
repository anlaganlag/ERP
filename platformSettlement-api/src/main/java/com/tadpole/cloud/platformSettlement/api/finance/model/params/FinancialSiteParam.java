package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
* <p>
* 财务站点信息
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@ApiModel
public class FinancialSiteParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 平台 */
    @ApiModelProperty("PLATFORM_NAME")
    private String platformName;

    /** 站点 */
    @ApiModelProperty("SITE")
    private String site;

    /** 原币 */
    @ApiModelProperty("ORIGINAL_CURRENCY")
    private String originalCurrency;

    /** 税率 */
    @ApiModelProperty("TAX_RATE")
    private BigDecimal taxRate;

    /** 创建时间 */
    @ApiModelProperty("CREATE_AT")
    private LocalDateTime createAt;

    /** 修改时间 */
    @ApiModelProperty("UPDATE_AT")
    private LocalDateTime updateAt;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 修改人 */
    @ApiModelProperty("UPDATE_BY")
    private String updateBy;

}