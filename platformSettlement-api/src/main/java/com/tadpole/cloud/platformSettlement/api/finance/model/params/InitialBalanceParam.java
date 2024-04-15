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
* 设置期初余额
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@ApiModel
public class InitialBalanceParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty("ID")
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

    /** 会计期间 */
    @ApiModelProperty("FISCAL_PERIOD")
    private String fiscalPeriod;

    /** 期初余额 */
    @ApiModelProperty("INITIAL_BALANCE")
    private BigDecimal initialBalance;

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