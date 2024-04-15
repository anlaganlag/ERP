package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* 领星Settlement源文件汇总
* </p>
*
* @author cyt
* @since 2022-05-13
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LxAmazonSettlementParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 结算单号 */
    @ApiModelProperty("SETTLEMENT_ID")
    private String settlementId;

    /** 结算开始日期 */
    @ApiModelProperty("SETTLEMENT_START_DATE")
    private Date settlementStartDate;

    /** 结算截止日期 */
    @ApiModelProperty("SETTLEMENT_END_DATE")
    private Date settlementEndDate;

    /** 银行汇款日期 */
    @ApiModelProperty("DEPOSIT_DATE")
    private Date depositDate;

    /** 总额 */
    @ApiModelProperty("TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    /** 货币 */
    @ApiModelProperty("CURRENCY")
    private String currency;

    /** 站点 */
    @ApiModelProperty("SITE")
    private String site;

    /** 账号简称 */
    @ApiModelProperty("SHOP_NAME")
    private String shopName;

    /** 创建时间 */
    @ApiModelProperty("CREATE_TIME")
    private Date createTime;

}