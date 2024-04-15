package com.tadpole.cloud.externalSystem.api.k3.model.params;

import com.alibaba.fastjson.annotation.JSONField;
import com.tadpole.cloud.externalSystem.api.k3.annotation.TransferSerializer;
import com.tadpole.cloud.externalSystem.api.k3.annotation.TransferSerializer1;
import com.tadpole.cloud.externalSystem.api.k3.annotation.TransferSerializerFid;
import com.tadpole.cloud.externalSystem.api.k3.annotation.TransferSerializerStaff;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public  class K3TransferApplyParam {
    @JSONField(name = "FID",ordinal = 0)
    private Integer fid;



    //单据类型：FBillTypeID  (必填项)
    @JSONField(name="FBillTypeID", serializeUsing = TransferSerializer1.class,ordinal = 1)
    private String fBillTypeID="DBSQD01_SYS"; //DBSQD01_SYS

    @JSONField(name = "FBillNo",ordinal = 2)
    private String billNo;

    @JSONField(name = "FDate",ordinal = 3)
    private String fDate;//2024-03-22 00:00:00

    //申请组织：FAPPORGID  (必填项)
    @JSONField(name="FAPPORGID", serializeUsing = TransferSerializer.class,ordinal = 4)
    private String fapporgid;//018601


    //调拨类型：FTRANSTYPE  (必填项)
    @JSONField(name = "FTRANSTYPE",ordinal = 5)
    private String ftranstype="OverOrgTransfer";//OverOrgTransfer

    //调出货主类型：FOwnerTypeIdHead  (必填项)
    @JSONField(name = "FOwnerTypeIdHead",ordinal = 6)
    private String fOwnerTypeIdHead="BD_OwnerOrg";//BD_OwnerOrg

    //调入货主类型：FOwnerTypeInIdHead  (必填项)
    @JSONField(name = "FOwnerTypeInIdHead",ordinal = 7)
    private String fOwnerTypeInIdHead="BD_OwnerOrg";//BD_OwnerOrg

    //拣货状态：FPickingStatus  (必填项)
    @JSONField(name = "FPickingStatus",ordinal = 8)
    private String fPickingStatus="A";//A

    @JSONField(name = "F_UNW_Text",ordinal = 9)
    private String fUnwText="ADS";//

    @JSONField(name = "F_UNW_ECOMMERCEPLATFORM",ordinal = 10)
    private String fUnwEcommerceplatform="Amazon";//

    @JSONField(name="F_UNW_DELIVERYPOINTID", serializeUsing = TransferSerializer1.class,ordinal = 11)
    private String fUnwDeliverypointid="FHD03";//FHD03

    @JSONField(name = "FISCOLLECTED",ordinal = 12)
    private String fiscollected="A";//A

    @JSONField(name = "FEntity",ordinal = 13)
    private List<K3TransferApplyItemParam> fEntity = new ArrayList<>();

    @JSONField(name = "F_UNW_Combo",ordinal = 14)
    private String unwCombo;



    @NoArgsConstructor
    @Data
    public static class K3TransferApplyItemParam {

        @JSONField(name="F_BSC_Base", serializeUsing = TransferSerializerFid.class,ordinal = 1)
        private String fBscBase;//SKU  Mmb-MNE-BathMirr-20-28in-TKK-SBA-wht

        @JSONField(name = "F_BSC_Text",ordinal = 2)
        private String fBscText;//X00431KXEB


        //物料编码：FMATERIALID  (必填项)
        @JSONField(name="FMATERIALID", serializeUsing = TransferSerializer.class,ordinal = 3)
        private String fmaterialid;//FHC230435


        //需求team：F_REQUIRETEAM  (必填项)
        @JSONField(name="F_REQUIRETEAM", serializeUsing = TransferSerializer1.class,ordinal = 4)
        private String fRequireteam;//家具组


        //需求人员：F_REQUIREPER  (必填项)
        @JSONField(name="F_REQUIREPER", serializeUsing = TransferSerializerStaff.class,ordinal = 5)
        private String fRequireper;//S20230143

        //单位：FUNITID  (必填项)
        @JSONField(name="FUNITID", serializeUsing = TransferSerializer.class,ordinal = 6)
        private String funitid="Pcs";//Pcs

        @JSONField(name = "FQty",ordinal = 7)
        private Integer fQty;


        //调出组织：FStockOrgId  (必填项)
        @JSONField(name="FStockOrgId", serializeUsing = TransferSerializer.class,ordinal = 8)
        private String fStockOrgId="002";//002

        //调入组织：FStockOrgInId  (必填项)
        @JSONField(name="FStockOrgInId", serializeUsing = TransferSerializer.class,ordinal = 9)
        private String fStockOrgInId;//018601

        @JSONField(name="FStockInId", serializeUsing = TransferSerializer1.class,ordinal = 10)
        private String fStockInId;//018601


        @JSONField(name="FStockStatusInId", serializeUsing = TransferSerializer1.class,ordinal = 11)
        private String fStockStatusInId="KCZT01_SYS";//KCZT01_SYS

        //调出货主类型：FOwnerTypeId  (必填项)
        @JSONField(name = "FOwnerTypeId",ordinal = 12)
        private String fOwnerTypeId="BD_OwnerOrg";//BD_OwnerOrg

        @JSONField(name="FOwnerId", serializeUsing = TransferSerializer.class,ordinal = 13)
        private String fOwnerId="002";//002

        //调入货主类型：FOwnerTypeInId  (必填项)
        @JSONField(name = "FOwnerTypeInId",ordinal = 14)
        private String fOwnerTypeInId="BD_OwnerOrg";//BD_OwnerOrg

        @JSONField(name="FOwnerInId", serializeUsing = TransferSerializer.class,ordinal = 15)
        private String fOwnerInId;//018601

        //基本单位：FBaseUnitID  (必填项)
        @JSONField(name="FBaseUnitID", serializeUsing = TransferSerializer.class,ordinal = 16)
        private String fBaseUnitID="Pcs";//Pcs

        @JSONField(name = "F_JCT_Color",ordinal = 17)
        private String fJctColor;

        @JSONField(name = "F_SHORT_DESC",ordinal = 18)
        private String fShortDesc;

        @JSONField(name = "F_JCT_Template",ordinal = 19)
        private String fJctTemplate;

        @JSONField(name = "F_STATION",ordinal = 20)
        private String fStation;

        @JSONField(name = "F_JCT_MRP",ordinal = 21)
        private String fJctMrp;

        @JSONField(name = "F_JCT_QTY",ordinal = 22)
        private String fJctQty;

        @JSONField(name = "F_JCT_box_c",ordinal = 23)
        private String fJctBoxC;

        @JSONField(name="F_REQUIREDEP", serializeUsing = TransferSerializer.class,ordinal = 24)
        private String fRequiredep;

        @JSONField(name = "F_UNW_Remark1",ordinal = 25)
        private String remark1 ;

        @JSONField(name = "F_UNW_Remark2",ordinal = 26)
        private String remark2 ;

        @JSONField(name = "F_UNW_Remark3",ordinal = 27)
        private String remark3;

        @JSONField(name = "FShippingMethod",ordinal = 28)
        private String shippingMethod;


    }
}