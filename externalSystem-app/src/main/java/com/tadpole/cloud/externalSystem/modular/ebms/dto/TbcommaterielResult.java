package com.tadpole.cloud.externalSystem.modular.ebms.dto;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.IdType;
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
* @since 2022-10-24
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("TbComMateriel")
public class TbcommaterielResult implements Serializable, BaseValidatingParam {

 private static final long serialVersionUID = 1L;


 /** ID */
 @TableId(value = "ID", type = IdType.AUTO)
 private Integer ID;

 /** 物料编码 */
 private String matCode;

 /** 物料名称 */
 private String matName;

 /** 内部代码 */
 private String matInteCode;

 /** K3代码 */
 private String matK3Code;

 /** 类目 */
 private String matCate;

 /** 运营大类 */
 private String matOperateCate;

 /** 公司品牌 */
 private String matComBrand;

 /** 产品名称 */
 private String matProName;

 /** 适用品牌或对象 */
 private String matBrand;

 /** 型号 */
 private String matModel;

 /** 款式 */
 private String matStyle;

 /** 颜色 */
 private String matColor;

 /** 规格单位 */
 private String matNormUnit;

 /** 长 */
 private Double matNetLength;

 /** 宽 */
 private Double matNetWidth;

 /** 高 */
 private Double matNetHeight;

 /** 包装长 */
 private Double matBoxLong;

 /** 包装宽 */
 private Double matBoxWidth;

 /** 包装高 */
 private Double matBoxHeight;

 /** 直径 */
 private Double matDiameter;

 /** 重量单位 */
 private String matWeightUnit;

 /** 净重量 */
 private Double matNetWeight;

 /** 包装重量 */
 private Double matBoxWeight;

 /** 运输重量 */
 private Double matTranportWeight;

 /** 容量单位 */
 private String matCapacityUnit;

 /** 容量 */
 private Double matCapacity;

 /** 物料禁用状态 */
 private String matStatus;

 /** 物料编辑状态 */
 private String matEditStatus;

 /** 最后更新日期 */
 private Date matLastUpdateDate;

 /** 套装属性 */
 private String matSetAttributes;

 /** 数量单位 */
 private String matQtyUnit;

 /** 开票规格型号 */
 private String matInvoiceNorm;

 /** 开票品名 */
 private String matInvoiceProName;

 /** 首单实际交期 */
 private Date matFirstOrderDate;

 /** 上新模式 */
 private String matNewestModel;

 /** 体积 */
 private Double matVolume;

 /** 包装体积 */
 private Double matBoxVolume;

 /** 报关材质 */
 private String matClearMaterial;

 /** 海关编码 */
 private String matCustomsCode;

 /** 退税率 */
 private String matTaxRefund;

 /** 开发人员 */
 private String matDeveloper;

 /** 采购人员 */
 private String matProcurementStaff;

 /** 采购订货量 */
 private Integer matMinOrderQty;

 /** 采购起订量备注 */
 private String matMoqNote;

 /** 生产周期 */
 private String matProCycle;

 /** 采购价格 */
 private Double matPurchasePrice;

 /** 规格型号 */
 private String matModeSpec;

 /** 禁用时间 */
 private Date matDisableTime;

 /** 是否带电 */
 private String matIsCharged;

 /** 营销模式 */
 private String matMarketModel;

 /** 立项编号 */
 private String matProjectNo;

 /** 首单预估交期 */
 private Date matFirstOrderDeliDate;

 /** 首单数量 */
 private Integer matFirstOrderNum;

 /** 采购-样品信息 */
 private String matSampleInfo;

 /** 新品状态-开发 */
 private Boolean matNewStateToDevelope;

 /** 新品状态-采购 */
 private Boolean matNewStateToPurchase;

 /** 新品状态-品牌 */
 private Boolean matNewStateToBrand;

 /** 新品状态-卖点文案图片 */
 private Boolean matNewStateToBulPoint;

 /** 新品状态-图文描述 */
 private Boolean matNewStateToImgText;

 /** 新品状态-运营 */
 private Boolean matNewStateToOperate;

 /** 新品状态-Listing */
 private Boolean matNewStateToListing;

 /** 新品状态-图片文案 */
 private Boolean matNewStateToCopy;

 /** 物料创建日期 */
 private Date matCreatDate;

 /** 新品状态-产品文案 */
 private Boolean matNewStateToProductCopy;

 /** 新品状态-产品图片 */
 private Boolean matNewStateToProductImage;

 /** 新品状态-EBC文案 */
 private Boolean matNewStateToEBCCopy;

 /** 新品状态-EBC图片 */
 private Boolean matNewStateToEBCImages;

 /** 材质 */
 private String matMaterial;

 /** 源净长 */
 private Double matNetLengthOrg;

 /** 源净宽 */
 private Double matNetWidthOrg;

 /** 源净高 */
 private Double matNetHeightOrg;

 /** 源包装长 */
 private Double matBoxLongOrg;

 /** 源包装宽 */
 private Double matBoxWidthOrg;

 /** 源包装高 */
 private Double matBoxHeightOrg;

 /** 源净重量 */
 private Double matNetWeightOrg;

 /** 源包装重量 */
 private Double matBoxWeightOrg;

 /** 源运输重量 */
 private Double matTranportWeightOrg;

 /** 源重量单位 */
 private String mateWeightUnitOrg;

 /** 源规格单位 */
 private String matNormUnitOrg;

 /** 采购-图片信息 */
 private String matImagePath;

 /** 主材料 */
 private String matMainMaterial;

 /** 图案 */
 private String matPattern;

 /** 尺码 */
 private String matSize;

 /** 包装数量 */
 private String matPackQty;

 /** 版本 */
 private String matVerson;

 /** 适用机型 */
 private String matCompatibleModel;

 /** 类目编码 */
 private String matCateCode;

 /** 运营大类编码 */
 private String matOperateCateCode;

 /** 产品名称编码 */
 private String matProNameCode;

 /** 款式编码 */
 private String matStyleCode;

 /** 主材料编码 */
 private String matMainMaterialCode;

 /** 图案编码 */
 private String matPatternCode;

 /** SPU */
 private String matSPU;

 /** NBDU */
 private String matNBDU;

 /** 新品业绩核算日期 */
 private Date matAccountingDate;

 /** 版本描述 */
 private String matVersonDesc;

 /** 报关型号 */
 private String matCustDeclarModel;

 /** 分类属性编码 */
 private String matClassAttrCode;

 /** 开发人员编号 */
 private String matDeveloperCode;

 /** 采购人员编号 */
 private String matProcurementStaffCode;

 /** 最大订货量 */
 private Integer matMaxOrderQty;

 /** 新品状态-初期销售规划 */
 private Boolean matNweStateToPlanning;

 /** 源毛重 */
 private Double matBoxWeight2Org;

 /** 新品状态-初期销售规划 */
 private Boolean matNewStateToInitialSalesPlan;

 /** Logo可替换 */
 private String matLogoReplace;

 /** 节日标签 */
 private String matFestLabel;

 /** 色号 */
 private String matColourNumber;

 /** 开发理由 */
 private String matDevReason;

 /** 入库标签短描述 */
 private String matInShortLabelDesc;

 /** 是否CE类 */
 private String matIsCEClass;

 /** 虚拟运营人员 */
 private String matVirtualOperators;

 /** 具体运营人员 */
 private String matSpecificOperators;

 /** 具体运营人员编号 */
 private String matSpecificOperatorsCode;

 /** 虚拟运营人员2 */
 private String matVirtualOperators2;

 /** 具体运营人员2 */
 private String matSpecificOperators2;

 /** 具体运营人员2编码 */
 private String matSpecificOperators2Code;

 /** 终审人 */
 private String matFinalJudgment;

 /** 毛利率 */
 private String matGrossProfitRate;

 /** 待补尺寸信息 */
 private String matSizeBeFilled;

 /** 修改理由 */
 private String matEditReason;

 /** 能否开票 */
 private String matIsMakeInvoice;

 /** 源体积 */
 private Double matVolumeOrg;

 /** 源包装体积 */
 private Double matBoxVolumeOrg;

 /** 默认税率 */
 private String matDefTaxRate;

 /** 季节标签 */
 private String matSeasonLabel;

 /** ERP同步状态 */
 private String matSyncStatus;

 /** 包装重量 */
 private Double matBoxWeight2;

 /** 材质明细 */
 private String matMaterialDet;

 /** 建议物流方式 */
 private String matTransport;

 /** ERP同步时间 */
 private Date matSyncTime;

 /** 品检要点 */
 private String matQualityCheckPoint;

 /** 开发最后更新时间 */
 private Date matDevLastUpdDate;

 /** 采购最后更新时间 */
 private Date matPurLastUpdDate;

 /** 产品文案最后更新时间 */
 private Date matProCopyLastUpdDate;

 /** 产品图片最后更新时间 */
 private Date matProImageLastUpdDate;

 /** EBC文案最后更新时间 */
 private Date matEBCCopyLastUpdDate;

 /** EBC图片最后更新时间 */
 private Date matEBCImageLastUpdDate;

 /** 运营最后更新时间 */
 private Date matOperateLastUpdDate;

 /** Listing最后更新时间 */
 private Date matListingLastUpdDate;

 /** 物料标记状态 */
 private String matMarkStatus;

 /** 二级类目 */
 private String matStyleSecondLabel;

 /** 拼单起订量 */
 private Integer matSpellOrderNum;

 /** 拼单起订量备注 */
 private String matSpellOrderNumRemark;

 /** 拼单起订量类别 */
 private String matSpellOrderNumCategory;

 /** 是否有阶梯价 */
 private String matIsStepPrice;

 /** 供应商识别码 */
 private String matSupplierIdenCode;

 /** 源开发人员姓名 */
 private String matInitialDeveloperName;

 /** 源开发人员编号 */
 private String matInitialDeveloperCode;

 /** 物料属性 */
 private String matPropertity;

 /** 是否可售 */
 private String matIsAvailableSale;


}