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
public class FBusinessInfo
{

    @JSONField(serializeUsing = TransferSerializer.class,name = "FSettleTypeId",ordinal = 1)
    @ApiModelProperty("FSettleTypeId")
    private String FSettleTypeId;

    @JSONField(name = "FVmiBusiness")
    private String FVmiBusiness;

    @JSONField(name = "FEnableSL")
    private String FEnableSL;




}
