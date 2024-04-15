package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class K3PurchaseOrderApplyParam implements Serializable {

    @JSONField(name="FBillTypeID",ordinal = 1)
    private String fBillTypeID="CGSQD01_SYS";

    @JSONField(name="FCreateDate",ordinal = 2,format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date fCreateDate;

    @JSONField(name="FBillNo",ordinal = 3)
    private String fBillNo;

    @JSONField(name="FApplicationDate",ordinal = 4,format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date fApplicationDate;

    @JSONField(name="FApplicationOrgId",ordinal = 5)
    private String fApplicationOrgId="002";

    @JSONField(name="FBSC_Remark1",ordinal = 6)
    private String fBSCRemark1="";

    @JSONField(name="FBSC_Remark2",ordinal = 7)
    private String fBSCRemark2="";

    @JSONField(name="FBSC_Remark3",ordinal = 8)
    private String fBSCRemark3="";

    @JSONField(name="FCreatorId",ordinal = 9)
    private String fCreatorId;

    @JSONField(name="FApplicantId",ordinal = 10)
    private String fApplicantId;

    @JSONField(name="fNote",ordinal = 11)
    private String fNote;

    @JSONField(name="FEntity",ordinal = 12)
    private List<K3PurchaseOrderApplyItem> fEntity;

}
