package com.tadpole.cloud.externalSystem.api.k3.model.params;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class K3TransferApplyParam1 {

    @JSONField(name = "NeedUpDateFields",ordinal = 1)
    private List<String> needUpDateFields=new ArrayList<>();

    @JSONField(name = "NeedReturnFields",ordinal = 2)
    private List<String> needReturnFields=new ArrayList<>();

    @JSONField(name = "IsDeleteEntry",ordinal = 3)
    private String isDeleteEntry="true";

    @JSONField(name = "SubSystemId",ordinal = 4)
    private String subSystemId="";

    @JSONField(name = "IsVerifyBaseDataField",ordinal = 5)
    private String isVerifyBaseDataField="false";

    @JSONField(name = "IsEntryBatchFill",ordinal = 6)
    private String isEntryBatchFill="true";

    @JSONField(name = "ValidateFlag",ordinal = 7)
    private String validateFlag="true";

    @JSONField(name = "NumberSearch",ordinal = 8)
    private String numberSearch="true";

    @JSONField(name = "IsAutoAdjustField",ordinal = 8)
    private String isAutoAdjustField="false";

    @JSONField(name = "InterationFlags",ordinal = 10)
    private String interationFlags="";

    @JSONField(name = "IgnoreInterationFlag",ordinal = 11)
    private String ignoreInterationFlag="";

    @JSONField(name = "Model",ordinal = 12)
    private K3TransferApplyParam model =new K3TransferApplyParam();


}
