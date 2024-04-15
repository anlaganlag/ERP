package com.tadpole.cloud.operationManagement.modular.stock.model.params;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class K3PurchaseOrderApplyItem implements Serializable {

    @JSONField(name="FRequireOrgId",ordinal = 1)
    private String fRequireOrgId="002";

    @JSONField(name="FRequireDeptId",ordinal = 2)
    private String fRequireDeptId="";

    @JSONField(name="F_PAEZ_Base",ordinal = 3)
    private String fPaezBase;//team

    @JSONField(name="F_PAEZ_Base2",ordinal = 4)
    private String fPaezBase2;//-- 需求team主管工号

    @JSONField(name="FMaterialId",ordinal = 5)
    private String fMaterialId;//物料编码

    @JSONField(name="FReqQty",ordinal = 6)
    private Integer fReqQty;//审核数量

    @JSONField(name="FUnitId",ordinal = 7)
    private String fUnitId="Pcs";

    @JSONField(name="FArrivalDate",ordinal = 8)
    private String fArrivalDate="";

    @JSONField(name="FPurchaseOrgId",ordinal = 9)
    private String fPurchaseOrgId="002";

    @JSONField(name="FPurchaserId",ordinal = 10)
    private String fPurchaserId;//采购员工号

    @JSONField(name="FSuggestSupplierId",ordinal = 11)
    private String fSuggestSupplierId;//供应商编码

    @JSONField(name="FREQSTOCKUNITID",ordinal = 12)
    private String fREQSTOCKUNITID="Pcs";

    @JSONField(name="FEntryNote",ordinal = 13)
    private String fEntryNote;//PMC备注

    @JSONField(name="F_BSC_HDate",ordinal = 14,format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date fBscHdate;//期望日期

    @JSONField(name="F_BSC_EntryRemark1",ordinal = 15)
    private String fBSCEntryRemark1="";

    @JSONField(name="F_BSC_EntryRemark2",ordinal = 16)
    private String fBSCEntryRemark2="";

    @JSONField(name="F_BSC_EntryRemark3",ordinal = 17)
    private String fBSCEntryRemark3="";


/*      *     '002' FRequireOrgId,
            *     '' FRequireDeptId,
            *     a.Team F_PAEZ_Base,    -- Team
         *     a.require_team_by F_PAEZ_Base2,   -- 需求team主管工号
         *     a.material_code FMaterialId,   -- 物料编码
         *     sum(a.verify_number) FReqQty,  --  审核数量
         *     'Pcs' FUnitId,
            *     '' FArrivalDate,
            *     '002' FPurchaseOrgId,
            *     purchase_by_code FPurchaserId,  --- 采购员工号
         *     supplier_code FSuggestSupplierId, --- 供应商编码
         *     'Pcs' FREQSTOCKUNITID,
            *     p.pmc_remark FEntryNote,  -- PMC备注
         *     a.expected_date F_BSC_HDate,   --期望日期
         *     '' F_BSC_EntryRemark1,
            *     '' F_BSC_EntryRemark2,
            *     '' F_BSC_EntryRemark3*/
}
