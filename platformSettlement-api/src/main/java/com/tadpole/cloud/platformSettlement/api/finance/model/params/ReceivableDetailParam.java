package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
* <p>
* 应收明细
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@TableName("CW_RECEIVABLE_DETAIL")
public class ReceivableDetailParam implements Serializable {

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

    /** 本期应收合计 */
    @ApiModelProperty("RECEIVABLE_AMOUNT")
    private BigDecimal receivableAmount;

    /** 本期收款合计 */
    @ApiModelProperty("RECEIVE_AMOUNT")
    private BigDecimal receiveAmount;

    /** total_amount */
    @ApiModelProperty("TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    /** 期末应收金额 */
    @ApiModelProperty("ENDTERM_RECEIVABLE_AMOUNT")
    private BigDecimal endtermReceivableAmount;

    /** 期初应收金额 */
    @ApiModelProperty("INITIAL_RECEIVE_AMOUNT")
    private BigDecimal initialReceiveAmount;

    /** 审核人 */
    @ApiModelProperty("VERIFY_BY")
    private String verifyBy;

    /** 状态 */
    @ApiModelProperty("STATUS")
    private String status;

    private List<String> shopNames;

    private List<String> sites;

    private List<String> MonthRange;

}