package com.tadpole.cloud.operationManagement.api.shopEntity.model.param;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 调用k3的二开接口新建组织机构
 */
@NoArgsConstructor
@Data
public class CreateOrgParam {

    /**
     * 组织编码 OrganizeID,
     */
    @JSONField(name = "FNumber")
    private String fNumber;
    /**
     * 组织名称  ShopName,
     */
    @JSONField(name = "FName")
    private String fName;
    /**
     * 组织形态
     */
    @JSONField(name = "FOrgFormID")
    private Integer fOrgFormID = 3;
    /**
     * 核算组织
     */
    @JSONField(name = "FIsAccountOrg")
    private boolean fIsAccountOrg =true;
    /**
     * 业务组织
     */
    @JSONField(name = "FIsBusinessOrg")
    private boolean fIsBusinessOrg = true;
    /**
     * 所属法人
     */
    @JSONField(name = "FParentID")
    private String fParentID = "003";
    /**
     * 组织职能
     */
    @JSONField(name = "FOrgFunctions")
    private String fOrgFunctions = "销售职能,采购职能,库存职能,结算职能,收付职能";
    /**
     * 研发职能
     */
    @JSONField(name = "FDevelopmentBox")
    private boolean fDevelopmentBox = false;
    /**
     * 核算组织类型
     */
    @JSONField(name = "FAcctOrgType")
    private String fAcctOrgType = "利润中心";
    /**
     * 电商平台条码  .ElePlatformName,
     */
    @JSONField(name = "F_UNW_ECOMMERCEPLATFORM")
    private String fUnwEcommerceplatform;
    /**
     * 站点  CountryCode,
     */
    @JSONField(name = "F_BSC_B2CPort")
    private String fBscB2cport;
    /**
     * 备注1   ShopNameSimple,
     */
    @JSONField(name = "F_BSC_Remark1")
    private String fBscRemark1 ;
    /**
     * 备注2
     */
    @JSONField(name = "F_BSC_Remark2")
    private String fBscRemark2 = "";

    /**
     * 备注3
     */
    @JSONField(name = "F_BSC_Remark3")
    private String fBscRemark3 = "";
}
