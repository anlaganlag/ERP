package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.k3;

import com.alibaba.fastjson.annotation.JSONField;
import com.tadpole.cloud.externalSystem.api.k3.annotation.TransferSerializer;
import com.tadpole.cloud.externalSystem.api.k3.annotation.TransferSerializer1;
import com.tadpole.cloud.externalSystem.api.k3.annotation.TransferSerializer2;
import com.tadpole.cloud.externalSystem.api.k3.annotation.TransferSerializer3;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class K3SupplierParamMap {
    /* id **/
    @JSONField(name = "FSupplierId")
    private String FSupplierId;
    /* 创建组织 **/
    @JSONField(serializeUsing = TransferSerializer.class,name = "FCreateOrgId",ordinal = 1)
    private String  FCreateOrgId;
    /* 使用组织 **/
    @JSONField(serializeUsing = TransferSerializer.class,name = "FUseOrgId",ordinal = 1)
    private String  FUseOrgId;
    /* 名称 **/
    @JSONField(name = "FName")
    private String FName;
    /* 简称 **/
    @JSONField(name = "FShortName")
    private String FShortName;
    /* 编码 **/
    @JSONField(name = "FNumber")
    private String  FNumber;
    /* 分组 **/
    @JSONField(serializeUsing = TransferSerializer.class,name = "FGroup",ordinal = 1)
    private String  FGroup;
    /* 供货产品 **/
    @JSONField(name = "F_PAEZ_Remark2")
    private String  F_PAEZ_Remark2;
    /* 是否公司员工推荐 **/
    @JSONField(name = "F_PAEZ_Combo")
    private String  F_PAEZ_Combo;
    /* 是否公司员工推荐 **/
    @JSONField(name = "F_BSC_Combo")
    private String  F_BSC_Combo;
    /* 开票公司类型 **/
    @JSONField(name = "F_BSC_Combo2")
    private String  F_BSC_Combo2;
    /* 具体关系 **/
    @JSONField(name = "F_PAEZ_Remark3")
    private String  F_PAEZ_Remark3;
    /* 省份 **/
    @JSONField(serializeUsing = TransferSerializer.class,name = "F_BSC_Assistant",ordinal = 1)
    private String  F_BSC_Assistant;
    /* 市区 **/
    @JSONField(serializeUsing = TransferSerializer.class,name = "F_BSC_Assistant1",ordinal = 2)
    private String  F_BSC_Assistant1;
    /* 通讯地址 **/
    @JSONField(name = "FAddress")
    private String  FAddress;
    /* 负责部门 **/
    @JSONField(serializeUsing = TransferSerializer.class,name = "FDeptId",ordinal = 3)
    private String  FDeptId;
    /* 统一社会信用代码 **/
    @JSONField(name = "FSOCIALCRECODE")
    private String  FSOCIALCRECODE;
    /* 被淘汰的供应商 **/
    @JSONField(name = "F_Obsolete_suppliers")
    private String  F_Obsolete_suppliers;
    /* 结算方式 **/
    @JSONField(name = "FSettleTypeId")
    private String  FObsoletesuppliers;
    /* 税分类 **/
    @JSONField(serializeUsing = TransferSerializer.class,name = "FTaxType",ordinal = 4)
    private String  FTaxType;
    /* 付款条件 **/
    @JSONField(serializeUsing = TransferSerializer.class,name = "FPayCondition",ordinal = 5)
    @ApiModelProperty("FPayCondition")
    private String FPayCondition;

    @JSONField(serializeUsing = TransferSerializer2.class,name = "FCREATORID",ordinal = 6)
    private String FCREATORID;

    @JSONField(serializeUsing = TransferSerializer2.class,name = "FMODIFIERID",ordinal = 7)
    private String FMODIFIERID;

    @JSONField(serializeUsing = TransferSerializer3.class,name = "F_BSC_Base",ordinal = 8)
    private String F_BSC_Base;

    /* 供应链公司 **/
    @JSONField(serializeUsing = TransferSerializer1.class,name = "F_BSC_Base1",ordinal = 9)
    private String  F_BSC_Base1;

    @JSONField(name = "F_BSC_TAXRATECONEW",ordinal = 6)
    private String F_BSC_TAXRATECONEW;

    /* 基础信息 **/
    @JSONField(name = "FBaseInfo",ordinal = 1)
    private FBaseInfo FBaseInfo;

    @JSONField(name = "FFinanceInfo",ordinal = 2)
    private FFinanceInfo FFinanceInfo;

    @JSONField(name = "FBankInfo",ordinal = 3)
    private List<FBankInfo> FBankInfo;

    @JSONField(name = "FSupplierContact",ordinal = 4)
    private List<FSupplierContact> FSupplierContact;

    @JSONField(name = "FLocationInfo",ordinal = 5)
    private List<FLocationInfo> FLocationInfo;


    @JSONField(name = "FBusinessInfo",ordinal = 6)
    private FBusinessInfo FBusinessInfo;

}
