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
public class FSupplierContact
{
    @ApiModelProperty("FContactNumber")
    private String FContactNumber;

    @ApiModelProperty("FContact")
    private String FContact;

    @JSONField(name = "FPost")
    private String FPost;

    @JSONField(name = "FMobile")
    private String FMobile;

    @JSONField(name = "FEMail")
    private String FEMail;

    @JSONField(name = "FContactDescription")
    private String FContactDescription;

    @JSONField(serializeUsing = TransferSerializer1.class,name = "FGender",ordinal = 1)
    private String FGender;

    @JSONField(name = "FContactIsDefault")
    private String FContactIsDefault;



}
