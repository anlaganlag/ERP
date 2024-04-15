package com.tadpole.cloud.externalSystem.modular.ebms.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
    * 
    * </p>
*
* @author gal
* @since 2022-10-25
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("TbComMateriel")
public class TbComMateriel implements Serializable {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private Integer ID;

    /** 物料编码 */
    @TableField("matCode")
    private String matCode;

    /** 物料名称 */
    @TableField("matName")
    private String matName;

    /** 内部代码 */
    @TableField("matInteCode")
    private String matInteCode;

    /** K3代码 */
    @TableField("matK3Code")
    private String matK3Code;

    /** 类目 */
    @TableField("matCate")
    private String matCate;

    /** 运营大类 */
    @TableField("matOperateCate")
    private String matOperateCate;

    /** 公司品牌 */
    @TableField("matComBrand")
    private String matComBrand;

    /** 产品名称 */
    @TableField("matProName")
    private String matProName;

    /** 适用品牌或对象 */
    @TableField("matBrand")
    private String matBrand;

    /** 型号 */
    @TableField("matModel")
    private String matModel;

    /** 款式 */
    @TableField("matStyle")
    private String matStyle;

    /** 颜色 */
    @TableField("matColor")
    private String matColor;

    /** 规格单位 */
    @TableField("matNormUnit")
    private String matNormUnit;

    /** 长 */
    @TableField("matNetLength")
    private Double matNetLength;

    /** 宽 */
    @TableField("matNetWidth")
    private Double matNetWidth;

    /** 高 */
    @TableField("matNetHeight")
    private Double matNetHeight;

    /** 包装长 */
    @TableField("matBoxLong")
    private Double matBoxLong;

    /** 包装宽 */
    @TableField("matBoxWidth")
    private Double matBoxWidth;

    /** 包装高 */
    @TableField("matBoxHeight")
    private Double matBoxHeight;

    /** 直径 */
    @TableField("matDiameter")
    private Double matDiameter;

    /** 重量单位 */
    @TableField("matWeightUnit")
    private String matWeightUnit;

    /** 净重量 */
    @TableField("matNetWeight")
    private Double matNetWeight;

    /** 包装重量 */
    @TableField("matBoxWeight")
    private Double matBoxWeight;

    /** 运输重量 */
    @TableField("matTranportWeight")
    private Double matTranportWeight;

    /** 容量单位 */
    @TableField("matCapacityUnit")
    private String matCapacityUnit;

    /** 容量 */
    @TableField("matCapacity")
    private Double matCapacity;

    /** 物料禁用状态 */
    @TableField("matStatus")
    private String matStatus;

    /** 物料编辑状态 */
    @TableField("matEditStatus")
    private String matEditStatus;

    /** 最后更新日期 */
    @TableField("matLastUpdateDate")
    private Date matLastUpdateDate;

    /** 套装属性 */
    @TableField("matSetAttributes")
    private String matSetAttributes;

    /** 数量单位 */
    @TableField("matSetAttributes")
    private String matQtyUnit;

    /** 开票规格型号 */
    @TableField("matInvoiceNorm")
    private String matInvoiceNorm;

    /** 开票品名 */
    @TableField("matInvoiceProName")
    private String matInvoiceProName;

    /** 首单实际交期 */
    @TableField("matFirstOrderDate")
    private Date matFirstOrderDate;

    /** 上新模式 */
    @TableField("matNewestModel")
    private String matNewestModel;

    /** 体积 */
    @TableField("matVolume")
    private Double matVolume;

    /** 包装体积 */
    @TableField("matBoxVolume")
    private Double matBoxVolume;

    /** 报关材质 */
    @TableField("matClearMaterial")
    private String matClearMaterial;

    /** 海关编码 */
    @TableField("matCustomsCode")
    private String matCustomsCode;

    /** 退税率 */
    @TableField("matTaxRefund")
    private String matTaxRefund;

    /** 开发人员 */
    @TableField("matDeveloper")
    private String matDeveloper;

    /** 采购人员 */
    @TableField("matProcurementStaff")
    private String matProcurementStaff;

    /** 采购订货量 */
    @TableField("matMinOrderQty")
    private Integer matMinOrderQty;

    /** 采购起订量备注 */
    @TableField("matMoqNote")
    private String matMoqNote;

    /** 生产周期 */
    @TableField("matProCycle")
    private String matProCycle;

    /** 采购价格 */
    @TableField("matPurchasePrice")
    private Double matPurchasePrice;

    /** 规格型号 */
    @TableField("matModeSpec")
    private String matModeSpec;

    /** 禁用时间 */
    @TableField("matDisableTime")
    private Date matDisableTime;

    /** 是否带电 */
    @TableField("matIsCharged")
    private String matIsCharged;

    /** 营销模式 */
    @TableField("matMarketModel")
    private String matMarketModel;

    /** 立项编号 */
    @TableField("matProjectNo")
    private String matProjectNo;

    /** 首单预估交期 */
    @TableField("matFirstOrderDeliDate")
    private Date matFirstOrderDeliDate;

    /** 首单数量 */
    @TableField("matFirstOrderNum")
    private Integer matFirstOrderNum;

    /** 采购-样品信息 */
    @TableField("matSampleInfo")
    private String matSampleInfo;

    /** 新品状态-开发 */
    @TableField("matNewStateToDevelope")
    private Boolean matNewStateToDevelope;

    /** 新品状态-采购 */
    @TableField("matNewStateToPurchase")
    private Boolean matNewStateToPurchase;

    /** 新品状态-品牌 */
    @TableField("matNewStateToBrand")
    private Boolean matNewStateToBrand;

    /** 新品状态-卖点文案图片 */
    @TableField("matNewStateToBulPoint")
    private Boolean matNewStateToBulPoint;

    /** 新品状态-图文描述 */
    @TableField("matNewStateToImgText")
    private Boolean matNewStateToImgText;

    /** 新品状态-运营 */
    @TableField("matNewStateToOperate")
    private Boolean matNewStateToOperate;

    /** 新品状态-Listing */
    @TableField("matNewStateToListing")
    private Boolean matNewStateToListing;

    /** 新品状态-图片文案 */
    @TableField("matNewStateToCopy")
    private Boolean matNewStateToCopy;

    /** 物料创建日期 */
    @TableField("matCreatDate")
    private Date matCreatDate;

    /** 新品状态-产品文案 */
    @TableField("matNewStateToProductCopy")
    private Boolean matNewStateToProductCopy;

    /** 新品状态-产品图片 */
    @TableField("matNewStateToProductImage")
    private Boolean matNewStateToProductImage;

    /** 新品状态-EBC文案 */
    @TableField("matNewStateToEBCCopy")
    private Boolean matNewStateToEBCCopy;

    /** 新品状态-EBC图片 */
    @TableField("matNewStateToEBCImages")
    private Boolean matNewStateToEBCImages;

    /** 材质 */
    @TableField("matMaterial")
    private String matMaterial;

    /** 源净长 */
    @TableField("matNetLengthOrg")
    private Double matNetLengthOrg;

    /** 源净宽 */
    @TableField("matNetWidthOrg")
    private Double matNetWidthOrg;

    /** 源净高 */
    @TableField("matNetHeightOrg")
    private Double matNetHeightOrg;

    /** 源包装长 */
    @TableField("matBoxLongOrg")
    private Double matBoxLongOrg;

    /** 源包装宽 */
    @TableField("matBoxWidthOrg")
    private Double matBoxWidthOrg;

    /** 源包装高 */
    @TableField("matBoxHeightOrg")
    private Double matBoxHeightOrg;

    /** 源净重量 */
    @TableField("matNetWeightOrg")
    private Double matNetWeightOrg;

    /** 源包装重量 */
    @TableField("matBoxWeightOrg")
    private Double matBoxWeightOrg;

    /** 源运输重量 */
    @TableField("matTranportWeightOrg")
    private Double matTranportWeightOrg;

    /** 源重量单位 */
    @TableField("mateWeightUnitOrg")
    private String mateWeightUnitOrg;

    /** 源规格单位 */
    @TableField("matNormUnitOrg")
    private String matNormUnitOrg;

    /** 采购-图片信息 */
    @TableField("matImagePath")
    private String matImagePath;

    /** 主材料 */
    @TableField("matMainMaterial")
    private String matMainMaterial;

    /** 图案 */
    @TableField("matPattern")
    private String matPattern;

    /** 尺码 */
    @TableField("matSize")
    private String matSize;

    /** 包装数量 */
    @TableField("matPackQty")
    private String matPackQty;

    /** 版本 */
    @TableField("matVerson")
    private String matVerson;

    /** 适用机型 */
    @TableField("matCompatibleModel")
    private String matCompatibleModel;

    /** 类目编码 */
    @TableField("matCateCode")
    private String matCateCode;

    /** 运营大类编码 */
    @TableField("matOperateCateCode")
    private String matOperateCateCode;

    /** 产品名称编码 */
    @TableField("matProNameCode")
    private String matProNameCode;

    /** 款式编码 */
    @TableField("matStyleCode")
    private String matStyleCode;

    /** 主材料编码 */
    @TableField("matMainMaterialCode")
    private String matMainMaterialCode;

    /** 图案编码 */
    @TableField("matPatternCode")
    private String matPatternCode;

    /** SPU */
    @TableField("matSPU")
    private String matSPU;

    /** NBDU */
    @TableField("matNBDU")
    private String matNBDU;

    /** 新品业绩核算日期 */
    @TableField("matAccountingDate")
    private Date matAccountingDate;

    /** 版本描述 */
    @TableField("matVersonDesc")
    private String matVersonDesc;

    /** 报关型号 */
    @TableField("matCustDeclarModel")
    private String matCustDeclarModel;

    /** 分类属性编码 */
    @TableField("matClassAttrCode")
    private String matClassAttrCode;

    /** 开发人员编号 */
    @TableField("matDeveloperCode")
    private String matDeveloperCode;

    /** 采购人员编号 */
    @TableField("matProcurementStaffCode")
    private String matProcurementStaffCode;

    /** 最大订货量 */
    @TableField("matMaxOrderQty")
    private Integer matMaxOrderQty;

    /** 新品状态-初期销售规划 */
    @TableField("matNweStateToPlanning")
    private Boolean matNweStateToPlanning;

    /** 源毛重 */
    @TableField("matBoxWeight2Org")
    private Double matBoxWeight2Org;

    /** 新品状态-初期销售规划 */
    @TableField("matNewStateToInitialSalesPlan")
    private Boolean matNewStateToInitialSalesPlan;

    /** Logo可替换 */
    @TableField("matLogoReplace")
    private String matLogoReplace;

    /** 节日标签 */
    @TableField("matFestLabel")
    private String matFestLabel;

    /** 色号 */
    @TableField("matColourNumber")
    private String matColourNumber;

    /** 开发理由 */
    @TableField("matDevReason")
    private String matDevReason;

    /** 入库标签短描述 */
    @TableField("matInShortLabelDesc")
    private String matInShortLabelDesc;

    /** 是否CE类 */
    @TableField("matIsCEClass")
    private String matIsCEClass;

    /** 虚拟运营人员 */
    @TableField("matVirtualOperators")
    private String matVirtualOperators;

    /** 具体运营人员 */
    @TableField("matSpecificOperators")
    private String matSpecificOperators;

    /** 具体运营人员编号 */
    @TableField("matSpecificOperatorsCode")
    private String matSpecificOperatorsCode;

    /** 虚拟运营人员2 */
    @TableField("matVirtualOperators2")
    private String matVirtualOperators2;

    /** 具体运营人员2 */
    @TableField("matSpecificOperators2")
    private String matSpecificOperators2;

    /** 具体运营人员2编码 */
    @TableField("matSpecificOperators2Code")
    private String matSpecificOperators2Code;

    /** 终审人 */
    @TableField("matFinalJudgment")
    private String matFinalJudgment;

    /** 毛利率 */
    @TableField("matGrossProfitRate")
    private String matGrossProfitRate;

    /** 待补尺寸信息 */
    @TableField("matSizeBeFilled")
    private String matSizeBeFilled;

    /** 修改理由 */
    @TableField("matEditReason")
    private String matEditReason;

    /** 能否开票 */
    @TableField("matIsMakeInvoice")
    private String matIsMakeInvoice;

    /** 源体积 */
    @TableField("matVolumeOrg")
    private Double matVolumeOrg;

    /** 源包装体积 */
    @TableField("matBoxVolumeOrg")
    private Double matBoxVolumeOrg;

    /** 默认税率 */
    @TableField("matDefTaxRate")
    private String matDefTaxRate;

    /** 季节标签 */
    @TableField("matSeasonLabel")
    private String matSeasonLabel;

    /** ERP同步状态 */
    @TableField("matSyncStatus")
    private String matSyncStatus;

    /** 包装重量 */
    @TableField("matBoxWeight2")
    private Double matBoxWeight2;

    /** 材质明细 */
    @TableField("matMaterialDet")
    private String matMaterialDet;

    /** 建议物流方式 */
    @TableField("matTransport")
    private String matTransport;

    /** ERP同步时间 */
    @TableField("matSyncTime")
    private Date matSyncTime;

    /** 品检要点 */
    @TableField("matQualityCheckPoint")
    private String matQualityCheckPoint;

    /** 开发最后更新时间 */
    @TableField("matDevLastUpdDate")
    private Date matDevLastUpdDate;

    /** 采购最后更新时间 */
    @TableField("matPurLastUpdDate")
    private Date matPurLastUpdDate;

    /** 产品文案最后更新时间 */
    @TableField("matProCopyLastUpdDate")
    private Date matProCopyLastUpdDate;

    /** 产品图片最后更新时间 */
    @TableField("matProImageLastUpdDate")
    private Date matProImageLastUpdDate;

    /** EBC文案最后更新时间 */
    @TableField("matEBCCopyLastUpdDate")
    private Date matEBCCopyLastUpdDate;

    /** EBC图片最后更新时间 */
    @TableField("matEBCImageLastUpdDate")
    private Date matEBCImageLastUpdDate;

    /** 运营最后更新时间 */
    @TableField("matOperateLastUpdDate")
    private Date matOperateLastUpdDate;

    /** Listing最后更新时间 */
    @TableField("matListingLastUpdDate")
    private Date matListingLastUpdDate;

    /** 物料标记状态 */
    @TableField("matMarkStatus")
    private String matMarkStatus;

    /** 二级类目 */
    @TableField("matStyleSecondLabel")
    private String matStyleSecondLabel;

    /** 拼单起订量 */
    @TableField("matSpellOrderNum")
    private Integer matSpellOrderNum;

    /** 拼单起订量备注 */
    @TableField("matSpellOrderNumRemark")
    private String matSpellOrderNumRemark;

    /** 拼单起订量类别 */
    @TableField("matSpellOrderNumCategory")
    private String matSpellOrderNumCategory;

    /** 是否有阶梯价 */
    @TableField("matIsStepPrice")
    private String matIsStepPrice;

    /** 供应商识别码 */
    @TableField("matSupplierIdenCode")
    private String matSupplierIdenCode;

    /** 源开发人员姓名 */
    @TableField("matInitialDeveloperName")
    private String matInitialDeveloperName;

    /** 源开发人员编号 */
    @TableField("matInitialDeveloperCode")
    private String matInitialDeveloperCode;

    /** 物料属性 */
    @TableField("matPropertity")
    private String matPropertity;

    /** 是否可售 */
    @TableField("matIsAvailableSale")
    private String matIsAvailableSale;

    /** 源物料编码 */
    @TableField("matCodeOrg")
    private String matCodeOrg;

    /** 创建部门编号 */
    @TableField("matCreateDeptCode")
    private String matCreateDeptCode;

    /** 创建部门名称 */
    @TableField("matCreateDeptName")
    private String matCreateDeptName;

    /** 创建人编号 */
    @TableField("matCreatePerCode")
    private String matCreatePerCode;

    /** 创建人姓名 */
    @TableField("matCreatePerName")
    private String matCreatePerName;

    /** 提案来源 */
    @TableField("matProposalSource")
    private String matProposalSource;

    /** 来源编号 */
    @TableField("matSourceId")
    private String matSourceId;

    /** 开发方式 */
    @TableField("matDevType")
    private String matDevType;

    /** 拓新类型 */
    @TableField("matNewExpandType")
    private String matNewExpandType;

    /** 产品要点 */
    @TableField("matKeyPoint")
    private String matKeyPoint;

    /** 质量要求点 */
    @TableField("matQualityRequire")
    private String matQualityRequire;

    /** 物料运营负责人编号 */
    @TableField("matOprPerCode")
    private String matOprPerCode;

    /** 物料运营负责人姓名 */
    @TableField("matOprPerName")
    private String matOprPerName;

    /** ERP组合同步状态 */
    @TableField("matCombineSyncStatus")
    private String matCombineSyncStatus;

    /** ERP组合同步时间 */
    @TableField("matCombineSyncTime")
    private Date matCombineSyncTime;

    /** 存储类型 */
    @TableField("matStorageType")
    private String matStorageType;

}