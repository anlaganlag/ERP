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
public class FBaseInfo
{
    @JSONField(serializeUsing = TransferSerializer.class,name = "FCountry",ordinal = 1)
    @ApiModelProperty("F_COUNTRY")
    private String FCountry;

    @JSONField(serializeUsing = TransferSerializer.class,name = "FProvincial",ordinal = 2)
    @ApiModelProperty("F_PROVINCIAL")
    private String FProvincial;

    @JSONField(name = "FAddress")
    private String FAddress;

    @JSONField(name = "FZip")
    private String FZip;

    @JSONField(name = "FWebSite")
    private String FWebSite;

    @JSONField(name = "FFoundDate")
    private String FFoundDate;

    @JSONField(name = "FLegalPerson")
    private String FLegalPerson;

    @JSONField(name = "FRegisterFund")
    private int FRegisterFund;

    @JSONField(name = "FRegisterCode")
    private String FRegisterCode;

    @JSONField(name = "FSOCIALCRECODE")
    private String FSOCIALCRECODE;

    @JSONField(name = "FTendPermit")
    private String FTendPermit;

    @JSONField(name = "FRegisterAddress")
    private String FRegisterAddress;

    @JSONField(name = "FSupplyClassify")
    private String FSupplyClassify;

    @JSONField(serializeUsing = TransferSerializer.class,name = "FDeptId",ordinal = 1)
    private String FDeptId;

    @JSONField(serializeUsing = TransferSerializer.class,name = "FStaffId",ordinal = 2)
    private String FStaffId;

    @JSONField(name = "F_Obsolete_suppliers")
    private String F_Obsolete_suppliers;

    @JSONField(name = "F_PAEZ_Remark")
    private String F_PAEZ_Remark;

    @JSONField(serializeUsing = TransferSerializer.class,name = "FPayCondition",ordinal = 3)
    @ApiModelProperty("FPayCondition")
    private String FPayCondition;
    //行业
    @JSONField(serializeUsing = TransferSerializer.class,name = "FTrade",ordinal = 4)
    private String FTrade;

}
