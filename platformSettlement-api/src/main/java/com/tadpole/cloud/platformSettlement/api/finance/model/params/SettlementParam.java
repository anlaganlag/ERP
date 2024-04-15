package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

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
public class SettlementParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty("ID")
    private int id;

    /** 结算id */
    @ApiModelProperty("SETTLEMENT_ID")
    private String settlementId;

    /** 开始时间 */
    @ApiModelProperty("SETTLEMENT_START_DATE")
    private String settlementStartDate;

    /** 结束时间 */
    @ApiModelProperty("SETTLEMENT_END_DATE")
    private String settlementEndDate;

    /** 结束时间 */
    @ApiModelProperty("DEPOSIT_DATE")
    private String depositDate;

    /** 结束时间 */
    @ApiModelProperty("TOTAL_AMOUNT")
    private String totalAmount;

    /** 结束时间 */
    @ApiModelProperty("CURRENCY")
    private String currency;

    /** 结束时间 */
    @ApiModelProperty("STATUS")
    private int status;

    /** 账号 */
    @ApiModelProperty("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** 账号 */
    @ApiModelProperty("CREATE_TIME")
    private String createTime;

    /** 账号 */
    @ApiModelProperty("UPLOAD_DATE")
    private String uploadDate;

    /** 站点 */
    @ApiModelProperty("SYS_SITE")
    private String sysSite;

}