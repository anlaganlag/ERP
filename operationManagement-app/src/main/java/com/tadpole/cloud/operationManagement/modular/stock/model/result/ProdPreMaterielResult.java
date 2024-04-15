package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 预建物料信息
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
@ExcelIgnoreUnannotated
@TableName("PROD_PRE_MATERIEL")
public class ProdPreMaterielResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 物料编码 自动生成规则：类目编码（1位）+运营大类编码（2位）+年份（2位）+流水号（4位）； */
   @TableId(value = "MAT_CODE", type = IdType.ASSIGN_ID)
    private String matCode;

    /** 源物料编码 */
    @ApiModelProperty("MAT_CODE_ORG")
    private String matCodeOrg;

    /** 创建日期 */
    @ApiModelProperty("SYS_MAT_CREATE_DATE")
    private Date sysMatCreateDate;

    /** 创建部门编号 */
    @ApiModelProperty("SYS_MAT_CREATE_DEPT_CODE")
    private String sysMatCreateDeptCode;

    /** 创建部门名称 */
    @ApiModelProperty("SYS_MAT_CREATE_DEPT_NAME")
    private String sysMatCreateDeptName;

    /** 创建人编号 */
    @ApiModelProperty("SYS_MAT_CREATE_PER_CODE")
    private String sysMatCreatePerCode;

    /** 创建人姓名 */
    @ApiModelProperty("SYS_MAT_CREATE_PER_NAME")
    private String sysMatCreatePerName;

    /** 提案编号Propose ID */
    @ApiModelProperty("PROPOSE_ID")
    private String proposeId;

    /** 提案来源 值域{""，"采购推样"，"新品开发需求"，"开发预提案"}； */
    @ApiModelProperty("PROPOSAL_SOURCE")
    private String proposalSource;

    /** 来源编号 值域{""，"推样编码/新品开发需求编码/预提案编号"}； */
    @ApiModelProperty("sourceid")
    private String sourceid;

    /** 类目 */
    @ApiModelProperty("MAT_CATE")
    private String matCate;

    /** 运营大类 */
    @ApiModelProperty("MAT_OPERATE_CATE")
    private String matOperateCate;

    /** 类目编码 */
    @ApiModelProperty("MAT_CATE_CODE")
    private String matCateCode;

    /** 运营大类编码 */
    @ApiModelProperty("MAT_OPERATE_CATE_CODE")
    private String matOperateCateCode;

    /** 产品名称 */
    @ApiModelProperty("MAT_PRO_NAME")
    private String matProName;

    /** 款式 */
    @ApiModelProperty("MAT_STYLE")
    private String matStyle;

    /** 主材料 */
    @ApiModelProperty("MAT_MAIN_MATERIAL")
    private String matMainMaterial;

    /** 图案 */
    @ApiModelProperty("MAT_PATTERN")
    private String matPattern;

    /** 公司品牌 */
    @ApiModelProperty("MAT_COM_BRAND")
    private String matComBrand;

    /** 适用品牌或对象 */
    @ApiModelProperty("MAT_BRAND")
    private String matBrand;

    /** 型号 */
    @ApiModelProperty("MAT_MODEL")
    private String matModel;

    /** 颜色 */
    @ApiModelProperty("MAT_COLOR")
    private String matColor;

    /** 尺码 */
    @ApiModelProperty("MAT_SIZE")
    private String matSize;

    /** 包装数量 */
    @ApiModelProperty("MAT_PACK_QTY")
    private String matPackQty;

    /** 版本 默认V1.0 */
    @ApiModelProperty("MAT_VERSON")
    private String matVerson;

    /** 上新模式 值域{选品/精品/自主设计}； */
    @ApiModelProperty("UP_NEW_MODE")
    private String upNewMode;

    /** 开发方式 值域{"全新品","老品拓新"}； */
    @ApiModelProperty("DEV_TYPE")
    private String devType;

    /** 拓新类型 值域{"老品改良","拓新设计"}； */
    @ApiModelProperty("NEW_EXPAND_TYPE")
    private String newExpandType;

    /** 营销模式 值域{"A","B","C","D"} */
    @ApiModelProperty("MAT_MARKET_MODE")
    private String matMarketMode;

    /** 开发理由 */
    @ApiModelProperty("MAT_DEV_REASON")
    private String matDevReason;

    /** 产品要点 */
    @ApiModelProperty("MAT_KEY_POINT")
    private String matKeyPoint;

    /** 质量要求点 */
    @ApiModelProperty("MAT_QUALITY_REQUIRE")
    private String matQualityRequire;

    /** 适用机型 */
    @ApiModelProperty("MAT_COMPATIBLE_MODEL")
    private String matCompatibleModel;

    /** 版本描述 */
    @ApiModelProperty("MAT_VERSION_DESC")
    private String matVersionDesc;

    /** Logo可替换 */
    @ApiModelProperty("MAT_LOGO_REPLACE")
    private String matLogoReplace;

    /** 节日标签 */
    @ApiModelProperty("MAT_FEST_LABEL")
    private String matFestLabel;

    /** 色号 */
    @ApiModelProperty("MAT_COLOUR_NUMBER")
    private String matColourNumber;

    /** 季节标签 */
    @ApiModelProperty("MAT_SEASON_LABEL")
    private String matSeasonLabel;

    /** 建议物流方式 值域{"空运","海运","快递","空运普货","卡航","铁运"} */
    @ApiModelProperty("MAT_TRANSPORT")
    private String matTransport;

    /** 二级类目 */
    @ApiModelProperty("MAT_SECONDARY_CATE")
    private String matSecondaryCate;

    /** 材质 */
    @ApiModelProperty("MAT_MATERIAL")
    private String matMaterial;

    /** 是否带电 值域{"是","否"} */
    @ApiModelProperty("MAT_IS_CHARGED")
    private String matIsCharged;

    /** 是否CE类 值域{"是","否"} */
    @ApiModelProperty("MAT_IS_CE_CLASS")
    private String matIsCeClass;

    /** 物料属性 值域{"自制","外购"}；默认"外购" */
    @ApiModelProperty("MAT_PROPERTITY")
    private String matPropertity;

    /** 开发审核状态 值域{"待提交","已提交","归档"}；默认"待提交"； */
    @ApiModelProperty("MAT_DEV_EXAMINE_STATUS")
    private String matDevExamineStatus;

    /** 采购审核状态 值域{"待提交","已提交","归档"}；默认"待提交"； */
    @ApiModelProperty("MAT_PUR_EXAMINE_STATUS")
    private String matPurExamineStatus;

    /** 运营审核状态 值域{"待审核","已审核","归档"}；默认"待审核"； */
    @ApiModelProperty("MAT_OPR_EXAMINE_STATUS")
    private String matOprExamineStatus;

    /** 终审状态 值域{"待审核","通过","归档"}；默认"待审核"； */
    @ApiModelProperty("MAT_FINAL_EXAMINE_STATUS")
    private String matFinalExamineStatus;

    /** 开发负责人编号 */
    @ApiModelProperty("MAT_DEV_PER_CODE")
    private String matDevPerCode;

    /** 开发负责人姓名 */
    @ApiModelProperty("MAT_DEV_PER_NAME")
    private String matDevPerName;

    /** 开发提交日期 */
    @ApiModelProperty("MAT_DEV_SUBMIT_DATE")
    private Date matDevSubmitDate;

    /** 采购负责人编号 */
    @ApiModelProperty("MAT_PUR_PER_CODE")
    private String matPurPerCode;

    /** 采购负责人姓名 */
    @ApiModelProperty("MAT_PUR_PER_NAME")
    private String matPurPerName;

    /** 采购提交日期 */
    @ApiModelProperty("MAT_PUR_SUBMIT_DATE")
    private Date matPurSubmitDate;

    /** 物料运营负责人编号 */
    @ApiModelProperty("MAT_OPR_PER_CODE")
    private String matOprPerCode;

    /** 物料运营负责人姓名 */
    @ApiModelProperty("MAT_OPR_PER_NAME")
    private String matOprPerName;

    /** 运营提交日期 */
    @ApiModelProperty("MAT_OPR_SUBMIT_DATE")
    private Date matOprSubmitDate;

    /** 运营审核批注 */
    @ApiModelProperty("MAT_OPR_EXAMINE_NOTE")
    private String matOprExamineNote;

    /** 终审人编号 */
    @ApiModelProperty("MAT_FINAL_PER_CODE")
    private String matFinalPerCode;

    /** 终审人姓名 */
    @ApiModelProperty("MAT_FINAL_PER_NAME")
    private String matFinalPerName;

    /** 终审日期 */
    @ApiModelProperty("MAT_FINAL_SUBMIT_DATE")
    private Date matFinalSubmitDate;

    /** 终审结果 值域{"驳回至开发","驳回至采购","全部驳回","通过"}； */
    @ApiModelProperty("MAT_FINAL_EXAMINE_RESULT")
    private String matFinalExamineResult;

    /** 开发驳回原因 */
    @ApiModelProperty("MAT_REJECT_DEV_REASON")
    private String matRejectDevReason;

    /** 采购驳回原因 */
    @ApiModelProperty("MAT_REJECT_PUR_REASON")
    private String matRejectPurReason;

    /** 备注 */
    @ApiModelProperty("MAT_REMARKS")
    private String matRemarks;

    /** 归档人编号 */
    @ApiModelProperty("MAT_ARCHIVE_PER_CODE")
    private String matArchivePerCode;

    /** 归档人姓名 */
    @ApiModelProperty("MAT_ARCHIVE_PER_NAME")
    private String matArchivePerName;

    /** 归档日期 */
    @ApiModelProperty("MAT_ARCHIVE_DATE")
    private Date matArchiveDate;

    /** 归档说明 */
    @ApiModelProperty("MAT_ARCHIVE_EXPLAIN")
    private String matArchiveExplain;

    /** 规则单位 默认cm； */
    @ApiModelProperty("MAT_NORM_UNIT_ORG")
    private String matNormUnitOrg;

    /** 净长 */
    @ApiModelProperty("MAT_NET_LENGTH_ORG")
    private BigDecimal matNetLengthOrg;

    /** 净宽 */
    @ApiModelProperty("MAT_NET_WIDTH_ORG")
    private BigDecimal matNetWidthOrg;

    /** 净高 */
    @ApiModelProperty("MAT_NET_HEIGHT_ORG")
    private BigDecimal matNetHeightOrg;

    /** 净体积 默认值=净长*净宽*净高 */
    @ApiModelProperty("MAT_VOLUME_ORG")
    private BigDecimal matVolumeOrg;

    /** 包装长 */
    @ApiModelProperty("MAT_BOX_LENGTH_ORG")
    private BigDecimal matBoxLengthOrg;

    /** 包装宽 */
    @ApiModelProperty("MAT_BOX_WIDTH_ORG")
    private BigDecimal matBoxWidthOrg;

    /** 包装高 */
    @ApiModelProperty("MAT_BOX_HEIGHT_ORG")
    private BigDecimal matBoxHeightOrg;

    /** 包装体积 默认值=包装长*包装宽*包装高 */
    @ApiModelProperty("MAT_BOX_VOLUME_ORG")
    private BigDecimal matBoxVolumeOrg;

    /** 重量单位 默认g； */
    @ApiModelProperty("MAT_WEIGHT_UNIT_ORG")
    private String matWeightUnitOrg;

    /** 净重 */
    @ApiModelProperty("MAT_NET_WEIGHT_ORG")
    private BigDecimal matNetWeightOrg;

    /** 毛重 */
    @ApiModelProperty("MAT_GROSS_WEIGHT_ORG")
    private BigDecimal matGrossWeightOrg;

    /** 同步EBMS状态 */
    @TableField("MAT_PRE_SYNC_STATUS")
    private String matPreSyncStatus;

    /** 同步时间 */
    @TableField("MAT_PRE_SYNC_DATE")
    private Date matPreSyncDate;

    /** 同步失败原因 */
    @TableField("MAT_PRE_SYNC_FAIL_REASON")
    private String matPreSyncFailReason;

    /** 入库段标签描述 */
    @TableField("MAT_IN_SHORT_LABEL_DESC")
    private String matInShortLabelDesc;


    /** 前端采购人 */
    @ApiModelProperty("前端采购人")
    private String frontPurName;

    /** 前端采购人编号 */
    @ApiModelProperty("前端采购人编号")
    private String frontPurCode;


    /** 前端采购人编号 */
    @ApiModelProperty("前端采购人编号")
    private String designPerCode;

    /** 设计人员姓名 */
    @ApiModelProperty("设计人员姓名")
    private String designPerName;
}