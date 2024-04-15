package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
* <p>
* 新核算库存平均单价表
* </p>
*
* @author gal
* @since 2021-12-24
*/
@Data
@ApiModel
public class NewAveragePriceParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 会计期间 */
    @ApiModelProperty("FISCAL_PERIOD")
    private String fiscalPeriod;

    /** 账号 */
    @ApiModelProperty("SHOP_NAME")
    private String shopName;

    /** 站点 */
    @ApiModelProperty("SITE")
    private String site;

    @ApiModelProperty("MATERIAL_CODE")
    private String materialCode;

    @ApiModelProperty("LOGISTICS_UNIT_PRICE")
    private BigDecimal logisticsUnitPrice;

    @ApiModelProperty("PURCHASE_UNIT_PRICE")
    private BigDecimal purchaseUnitPrice;

    @ApiModelProperty("ADDITIONAL_UNIT_PRICE")
    private BigDecimal additionalUnitPrice;

    @ApiModelProperty("CONFIRM_DATE")
    private LocalDateTime confirmDate;

    @ApiModelProperty("CONFIRM_BY")
    private String confirmBy;

    @ApiModelProperty("CONFIRM_STATUS")
    private String confirmStatus;

    /** 创建人 */
    @ApiModelProperty("CREATE_BY")
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty("CREATE_AT")
    private LocalDateTime createAt;

    @ApiModelProperty("ID")
    private BigDecimal id;
    
    private List sites;

}