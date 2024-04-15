package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.k3;

import com.alibaba.fastjson.annotation.JSONField;
import com.tadpole.cloud.externalSystem.api.k3.annotation.TransferSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FFinanceInfo
{
    @JSONField(serializeUsing = TransferSerializer.class,name = "FPayCurrencyId",ordinal = 1)
    @ApiModelProperty("F_PAY_CURRENCY_ID")
    private String FPayCurrencyId;

    @JSONField(serializeUsing = TransferSerializer.class,name = "FTaxType",ordinal = 2)
    private String FTaxType;

    @JSONField(name = "FInvoiceType")
    private String FInvoiceType;

    @JSONField(serializeUsing = TransferSerializer.class,name = "FTaxRateId",ordinal = 3)
    @ApiModelProperty("F_TAX_RATE_ID")
    private String FTaxRateId;

    @JSONField(serializeUsing = TransferSerializer.class,name = "FPayCondition",ordinal = 4)
    @ApiModelProperty("FPayCondition")
    private String FPayCondition;

}
