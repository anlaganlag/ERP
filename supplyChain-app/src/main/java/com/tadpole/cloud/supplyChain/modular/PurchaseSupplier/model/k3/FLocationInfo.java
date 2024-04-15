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
public class FLocationInfo
{
    @JSONField(name = "FLocName")
    private String FLocName;

    @JSONField(serializeUsing = TransferSerializer.class,name = "FLocNewContact",ordinal = 1)
    @ApiModelProperty("FLocNewContact")
    private String FLocNewContact;

    @JSONField(name = "FLocAddress")
    private String FLocAddress;

    @JSONField(name = "FLocMobile")
    private String FLocMobile;





}
