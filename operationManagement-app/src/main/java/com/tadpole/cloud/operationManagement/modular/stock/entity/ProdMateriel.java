package com.tadpole.cloud.operationManagement.modular.stock.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 物料信息
    * </p>
*
* @author gal
* @since 2022-10-20
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("PROD_MATERIEL")
@ExcelIgnoreUnannotated
public class ProdMateriel implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 物料编码 自动生成规则：类目编码（1位）+运营大类编码（2位）+年份（2位）+流水号（4位）； */
   @TableId(value = "MAT_CODE", type = IdType.ASSIGN_ID)
    private String matCode;

    /** 源物料编码 */
    @TableField("MAT_CODE_ORG")
    private String matCodeOrg;

    /** 创建日期 */
    @TableField("SYS_MAT_CREATE_DATE")
    private Date sysMatCreateDate;

    /** 创建部门编号 */
    @TableField("SYS_MAT_CREATE_DEPT_CODE")
    private String sysMatCreateDeptCode;

    /** 创建部门名称 */
    @TableField("SYS_MAT_CREATE_DEPT_NAME")
    private String sysMatCreateDeptName;

    /** 创建人编号 */
    @TableField("SYS_MAT_CREATE_PER_CODE")
    private String sysMatCreatePerCode;

    /** 创建人姓名 */
    @TableField("SYS_MAT_CREATE_PER_NAME")
    private String sysMatCreatePerName;

    /** 物料禁用状态 */
    @TableField("MAT_STATUS")
    private String matStatus;

    /** 禁用时间 */
    @TableField("MAT_DISABLE_TIME")
    private Date matDisableTime;

    /** ERP同步状态 */
    @TableField("MAT_SYNC_STATUS")
    private String matSyncStatus;

    /** ERP同步时间 */
    @TableField("MAT_SYNC_TIME")
    private Date matSyncTime;

    /** ERP同步状态 */
    @TableField("MAT_COMBINE_SYNC_STATUS")
    private String matCombineSyncStatus;

    /** ERP同步时间 */
    @TableField("MAT_COMBINE_SYNC_TIME")
    private Date matCombineSyncTime;

    /** 物料编辑状态 */
    @TableField("MAT_EDIT_STATUS")
    private String matEditStatus;

    /** 最后更新日期 */
    @TableField("MAT_LAST_UPDATE_DATE")
    private Date matLastUpdateDate;

    /** 提案编号->立项编号 */
    @TableField("MAT_PROJECT_NO")
    private String matProjectNo;

    /** 提案来源 值域{""，"采购推样"，"新品开发需求"，"开发预提案"}； */
    @TableField("PROPOSAL_SOURCE")
    private String proposalSource;

    /** 来源编号 值域{""，"推样编码/新品开发需求编码/预提案编号"}； */
    @TableField("sourceid")
    private String sourceid;

    /** 上新模式 值域{选品/精品/自主设计}； */
    @TableField("UP_NEW_MODE")
    private String upNewMode;

    /** 开发方式 值域{"全新品","老品拓新"}； */
    @TableField("DEV_TYPE")
    private String devType;

    /** 拓新类型 值域{"老品改良","拓新设计"}； */
    @TableField("NEW_EXPAND_TYPE")
    private String newExpandType;

    /** 营销模式 值域{"A","B","C","D"} */
    @TableField("MAT_MARKET_MODE")
    private String matMarketMode;

    /** 开发理由 */
    @TableField("MAT_DEV_REASON")
    private String matDevReason;

    /** 产品要点 */
    @TableField("MAT_KEY_POINT")
    private String matKeyPoint;

    /** 质量要求点 */
    @TableField("MAT_QUALITY_REQUIRE")
    private String matQualityRequire;

    /** 类目 */
    @TableField("MAT_CATE")
    private String matCate;

    /** 运营大类 */
    @TableField("MAT_OPERATE_CATE")
    private String matOperateCate;

    /** 产品名称 */
    @TableField("MAT_PRO_NAME")
    private String matProName;

    /** 款式 */
    @TableField("MAT_STYLE")
    private String matStyle;

    /** 主材料 */
    @TableField("MAT_MAIN_MATERIAL")
    private String matMainMaterial;

    /** 图案 */
    @TableField("MAT_PATTERN")
    private String matPattern;

    /** 类目编码 */
    @TableField("MAT_CATE_CODE")
    private String matCateCode;

    /** 运营大类编码 */
    @TableField("MAT_OPERATE_CATE_CODE")
    private String matOperateCateCode;

    /** 产品名称编码 */
    @TableField("MAT_PRO_NAME_CODE")
    private String matProNameCode;

    /** 款式编码 */
    @TableField("MAT_STYLE_CODE")
    private String matStyleCode;

    /** 主材料编码 */
    @TableField("MAT_MAIN_MATERIAL_CODE")
    private String matMainMaterialCode;

    /** 图案编码 */
    @TableField("MAT_PATTERN_CODE")
    private String matPatternCode;

    /** 分类属性编码 */
    @TableField("MAT_CLASS_ATTR_CODE")
    private String matClassAttrCode;

    /** 公司品牌 */
    @TableField("MAT_COM_BRAND")
    private String matComBrand;

    /** 适用品牌或对象 */
    @TableField("MAT_BRAND")
    private String matBrand;

    /** 型号 */
    @TableField("MAT_MODEL")
    private String matModel;

    /** 颜色 */
    @TableField("MAT_COLOR")
    private String matColor;

    /** 尺码 */
    @TableField("MAT_SIZE")
    private String matSize;

    /** 包装数量 */
    @TableField("MAT_PACK_QTY")
    private String matPackQty;

    /** 版本 默认V1.0 */
    @TableField("MAT_VERSON")
    private String matVerson;

    /** 适用机型 */
    @TableField("MAT_COMPATIBLE_MODEL")
    private String matCompatibleModel;

    /** 版本描述 */
    @TableField("MAT_VERSION_DESC")
    private String matVersionDesc;

    /** Logo可替换 */
    @TableField("MAT_LOGO_REPLACE")
    private String matLogoReplace;

    /** 节日标签 */
    @TableField("MAT_FEST_LABEL")
    private String matFestLabel;

    /** 色号 */
    @TableField("MAT_COLOUR_NUMBER")
    private String matColourNumber;

    /** 季节标签 */
    @TableField("MAT_SEASON_LABEL")
    private String matSeasonLabel;

    /** 建议物流方式 值域{"空运","海运","快递","空运普货","卡航","铁运"} */
    @TableField("MAT_TRANSPORT")
    private String matTransport;

    /** 二级类目 */
    @TableField("MAT_SECONDARY_CATE")
    private String matSecondaryCate;

    /** 材质 */
    @TableField("MAT_MATERIAL")
    private String matMaterial;

    /** 是否带电 值域{"是","否"} */
    @TableField("MAT_IS_CHARGED")
    private String matIsCharged;

    /** 是否CE类 值域{"是","否"} */
    @TableField("MAT_IS_CE_CLASS")
    private String matIsCeClass;

    /** 物料属性 值域{"自制","外购"}；默认"外购" */
    @TableField("MAT_PROPERTITY")
    private String matPropertity;

    /** 开发负责人编号 */
    @TableField("MAT_DEVELOPER_CODE")
    private String matDeveloperCode;

    /** 开发负责人姓名 */
    @TableField("MAT_DEVELOPER")
    private String matDeveloper;

    /** 采购负责人编号 */
    @TableField("MAT_PROCUREMENT_STAFF_CODE")
    private String matProcurementStaffCode;

    /** 采购负责人姓名 */
    @TableField("MAT_PROCUREMENT_STAFF")
    private String matProcurementStaff;

    /** 物料运营负责人编号 */
    @TableField("MAT_OPR_PER_CODE")
    private String matOprPerCode;

    /** 物料运营负责人姓名 */
    @TableField("MAT_OPR_PER_NAME")
    private String matOprPerName;

    /** 终审人编号 */
    @TableField("MAT_FINAL_JUDGMENT_CODE")
    private String matFinalJudgmentCode;

    /** 终审人姓名 */
    @TableField("MAT_FINAL_JUDGMENT")
    private String matFinalJudgment;

    /** 入库段标签描述 */
    @TableField("MAT_IN_SHORT_LABEL_DESC")
    private String matInShortLabelDesc;

    /** 规格型号 */
    @TableField("MAT_MODE_SPEC")
    private String matModeSpec;

    /** SPU */
    @TableField("MAT_SPU")
    private String matSpu;

    /** NBDU */
    @TableField("MAT_NBDU")
    private String matNbdu;

    /** 物料名称 */
    @TableField("MAT_NAME")
    private String matName;

    /** 新品业绩核算日期 */
    @TableField("MAT_ACCOUNTING_DATE")
    private Date matAccountingDate;

    /** 源规则单位 默认cm； */
    @TableField("MAT_NORM_UNIT_ORG")
    private String matNormUnitOrg;

    /** 源净长 */
    @TableField("MAT_NET_LENGTH_ORG")
    private BigDecimal matNetLengthOrg;

    /** 源净宽 */
    @TableField("MAT_NET_WIDTH_ORG")
    private BigDecimal matNetWidthOrg;

    /** 源净高 */
    @TableField("MAT_NET_HEIGHT_ORG")
    private BigDecimal matNetHeightOrg;

    /** 源净体积 默认值=源净长*源净宽*源净高 */
    @TableField("MAT_VOLUME_ORG")
    private BigDecimal matVolumeOrg;

    /** 源包装长 */
    @TableField("MAT_BOX_LENGTH_ORG")
    private BigDecimal matBoxLengthOrg;

    /** 源包装宽 */
    @TableField("MAT_BOX_WIDTH_ORG")
    private BigDecimal matBoxWidthOrg;

    /** 源包装高 */
    @TableField("MAT_BOX_HEIGHT_ORG")
    private BigDecimal matBoxHeightOrg;

    /** 源包装体积 默认值=源包装长*源包装宽*源包装高 */
    @TableField("MAT_BOX_VOLUME_ORG")
    private BigDecimal matBoxVolumeOrg;

    /** 源重量单位 默认g； */
    @TableField("MAT_WEIGHT_UNIT_ORG")
    private String matWeightUnitOrg;

    /** 源净重 */
    @TableField("MAT_NET_WEIGHT_ORG")
    private BigDecimal matNetWeightOrg;

    /** 源毛重 */
    @TableField("MAT_GROSS_WEIGHT_ORG")
    private BigDecimal matGrossWeightOrg;


    /** 前端采购人 */
    @TableField("FRONT_PUR_NAME")
    private String frontPurName;

    /** 前端采购人编号 */
    @TableField("FRONT_PUR_CODE")
    private String frontPurCode;

    /** 设计人员编号 */
    @TableField("DESIGN_PER_CODE")
    private String designPerCode;

    /** 设计人员姓名 */
    @TableField("DESIGN_PER_NAME")
    private String designPerName;
}