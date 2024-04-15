package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCustomsClearanceDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 物流管理--出货清单对应的清关费用计算请求入参 明细
 * </p>
 *
 * @author lsy
 * @since 2024-03-27
 */
@Data
public class TgCustomsClearanceCalcDetailParam extends TgCustomsClearanceDetail implements Serializable {


    /** 币制 */
    @ApiModelProperty("币制")
    private String currency;

    @ApiModelProperty("清关总金额")
    private BigDecimal totalClearanceAmount;

    @ApiModelProperty(value = "流转税（VAT/TAX/205）")
    private BigDecimal changeTax;

    @ApiModelProperty("关税")
    private BigDecimal taxRate;

    @ApiModelProperty("附加税率")
    private BigDecimal addTaxRate;

}