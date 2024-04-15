package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.k3;

import com.alibaba.fastjson.annotation.JSONField;
import com.tadpole.cloud.externalSystem.api.k3.annotation.TransferSerializer;
import com.tadpole.cloud.externalSystem.api.k3.annotation.TransferSerializer1;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FBankInfo
{
    @JSONField(serializeUsing = TransferSerializer.class,name = "FBankCountry",ordinal = 1)
    @ApiModelProperty("F_BANK_COUNTRY")
    private String FBankCountry;

    @JSONField(name = "FBankCode")
    private String FBankCode;

    @JSONField(name = "FBankHolder")
    private String FBankHolder;

    @JSONField(serializeUsing = TransferSerializer1.class,name = "FBankDetail",ordinal = 2)
    private String FBankDetail;

    @JSONField(name = "FOpenAddressRec")
    private String FOpenAddressRec;

    @JSONField(name = "FOpenBankName")
    private String FOpenBankName;

    @JSONField(name = "FCNAPS")
    private String FCNAPS;

    @JSONField(name = "FBankIsDefault")
    private String FBankIsDefault;

    @JSONField(serializeUsing = TransferSerializer.class,name = "FBankCurrencyId",ordinal = 3)
    @ApiModelProperty("F_BANK_CURRENCY_ID")
    private String FBankCurrencyId;


}
