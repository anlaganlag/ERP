package com.tadpole.cloud.operationManagement.modular.stock.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 新品采购申请
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
//@TableName("PROD_PURCHASE_REQUIRE")
public class ProdPurchaseRequireExtentsResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 采购申请编号 */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 物料编码 */
    @ApiModelProperty("MAT_CODE")
    @ExcelProperty("物料编码")
    private String matCode;

    /** 下单需求整理编号 */
    @ApiModelProperty("PROD_ORDER_DEMAND_ID")
    private String prodOrderDemandId;

    /** 创建部门编号 */
    @ApiModelProperty("CREATE_DEPT_CODE")
    private String createDeptCode;

    /** 创建部门名称 */
    @ApiModelProperty("CREATE_DEPT_NAME")
    private String createDeptName;

    /** 创建人编号 */
    @ApiModelProperty("CREATE_PER_CODE")
    private String createPerCode;

    /** 创建人姓名 */
    @ApiModelProperty("CREATE_PER_NAME")
    private String createPerName;

    /** 创建日期 */
    @ApiModelProperty("CREATE_DATE")
    private Date createDate;

    /** 生效日期 */
    @ApiModelProperty("EFFECTIVE_DATE")
    private Date effectiveDate;

    /** 采购申请单类型 */
    @ApiModelProperty("PUR_REQUIRE_TYPE")
    private String purRequireType;

    /** 事业部 */
    @ApiModelProperty("DEPARTMENT")
    @ExcelProperty("申请部门")
    private String department;

    /** Team组 */
    @ApiModelProperty("TEAM")
    @ExcelProperty("申请Team")
    private String team;

    /** 申请人编号 */
    @ApiModelProperty("APPLY_PER_CODE")
    private String applyPerCode;

    /** 申请人姓名 */
    @ApiModelProperty("APPLY_PER_NAME")
    private String applyPerName;

    /** 下单部门编号 */
    @ApiModelProperty("MAT_ORDER_DEPT_CODE")
    private String matOrderDeptCode;

    /** 下单部门名称 */
    @ApiModelProperty("MAT_ORDER_DEPT_NAME")
    private String matOrderDeptName;

    /** 运营申请数量 */
    @ApiModelProperty("OPR_APPLY_QTY")
    @ExcelProperty("申请数量")
    private BigDecimal oprApplyQty;

    /** 运营期望交期 */
    @ApiModelProperty("OPR_EXPECT_DELIVE_DATE")
    @ExcelProperty("期望交期")
    private Date oprExpectDeliveDate;

    /** 申请单状态 */
    @ApiModelProperty("APPLY_STATUS")
    @ExcelProperty("申请单状态")
    private String applyStatus;



    /** 是否含税 */
    @ApiModelProperty("IS_INCLUDE_TAX")
    @ExcelProperty("是否含税")
    private String isIncludeTax;

    /** 下单方式 */
    @ApiModelProperty("ORDER_METHOD")
    @ExcelProperty("下单方式")
    private String orderMethod;

    /** 复核部门编码 */
    @ApiModelProperty("CHECK_DEPT_CODE")
    private String checkDeptCode;

    /** 复核部门名称 */
    @ApiModelProperty("CHECK_DEPT_NAME")
    private String checkDeptName;

    /** 复核人编码 */
    @ApiModelProperty("CHECK_PER_CODE")
    private String checkPerCode;

    /** 复核人姓名 */
    @ApiModelProperty("CHECK_PER_NAME")
    private String checkPerName;

    /** 复核日期 */
    @ApiModelProperty("CHECK_DATE")
    private Date checkDate;

    /** 复核数量 */
    @ApiModelProperty("OPR_APPLY_QTY3")
    @ExcelProperty("复核数量")
    private BigDecimal oprApplyQty3;

    /** 采购预计交期 */
    @ApiModelProperty("PUR_EXPECT_DELIVE_DATE")
    @ExcelProperty("预计交期")
    private Date purExpectDeliveDate;

    /** 复核说明 */
    @ApiModelProperty("CHECK_EXPLAIN")
    @ExcelProperty("复核说明")
    private String checkExplain;

    /** PMC审批人编号 */
    @ApiModelProperty("PMC_APPROVE_PER_CODE")
    private String pmcApprovePerCode;

    /** PMC审批人姓名 */
    @ApiModelProperty("PMC_APPROVE_PER_NAME")
    @ExcelProperty("PMC审批人")
    private String pmcApprovePerName;

    /** PMC审批日期 */
    @ApiModelProperty("PMC_APPROVE_DATE")
    @ExcelProperty("PMC审批时间")
    private Date pmcApproveDate;

    /** PMC申请说明 */
    @ApiModelProperty("PMC_APPLY_EXPLAIN")
    @ExcelProperty("PMC审批说明")
    private String pmcApplyExplain;

    /** 下单编号 */
    @ApiModelProperty("ORDER_ID")
    @ExcelProperty("订单号")
    private String orderId;

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
    @ExcelProperty("申请人")
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
    @ExcelProperty("运营大类")
    private String matOperateCate;

    /** 类目编码 */
    @ApiModelProperty("MAT_CATE_CODE")
    private String matCateCode;

    /** 运营大类编码 */
    @ApiModelProperty("MAT_OPERATE_CATE_CODE")
    private String matOperateCateCode;

    /** 产品名称 */
    @ApiModelProperty("MAT_PRO_NAME")
    @ExcelProperty("产品名称")
    private String matProName;

    /** 款式 */
    @ApiModelProperty("MAT_STYLE")
    @ExcelProperty("款式")
    private String matStyle;

    /** 主材料 */
    @ApiModelProperty("MAT_MAIN_MATERIAL")
    @ExcelProperty("主材料")
    private String matMainMaterial;

    /** 图案 */
    @ApiModelProperty("MAT_PATTERN")
    @ExcelProperty("图案")
    private String matPattern;

    /** 公司品牌 */
    @ApiModelProperty("MAT_COM_BRAND")
    @ExcelProperty("公司品牌")
    private String matComBrand;

    /** 适用品牌或对象 */
    @ApiModelProperty("MAT_BRAND")
    @ExcelProperty("适用品牌或对象")
    private String matBrand;

    /** 型号 */
    @ApiModelProperty("MAT_MODEL")
    @ExcelProperty("型号")
    private String matModel;

    /** 颜色 */
    @ApiModelProperty("MAT_COLOR")
    @ExcelProperty("颜色")
    private String matColor;

    /** 尺码 */
    @ApiModelProperty("MAT_SIZE")
    @ExcelProperty("尺码")
    private String matSize;

    /** 包装数量 */
    @ApiModelProperty("MAT_PACK_QTY")
    @ExcelProperty("包装数量")
    private String matPackQty;

    /** 版本 默认V1.0 */
    @ApiModelProperty("MAT_VERSON")
    @ExcelProperty("版本")
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
    @ExcelProperty("物料属性")
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
    @ExcelProperty("采购员")
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
    @ExcelProperty("运营确认日期")
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
    @ExcelProperty("终审日期")
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


    /** 同步时间 */
    @ApiModelProperty("同步时间")
    private Date syncTime;

    /** 同步状态(0 ：同步失败,1：同步成功,-1待同步) */
    @ApiModelProperty("同步状态")
    @ExcelProperty("同步状态")
    private String syncStatus;

    /** 同步结果 */
    @ApiModelProperty("同步结果")
    @ExcelProperty("同步结果")
    private String syncResultMsg;

    /** 同步请求参数 */
    @ApiModelProperty("同步请求参数")
    @ExcelProperty("同步信息")
    private String syncRequestMsg;

    /** 入库段标签描述 */
    @ApiModelProperty("MAT_IN_SHORT_LABEL_DESC")
    private String matInShortLabelDesc;

    /** 前端采购人 */
    @ApiModelProperty("前端采购人")
    private String frontPurName;

    /** 前端采购人编号 */
    @ApiModelProperty("前端采购人编号")
    private String frontPurCode;


 /** 建议供应商 */
 @ApiModelProperty("建议供应商")
 private String suggestSupplier;

 /** 建议供应商编号 */
 @ApiModelProperty("建议供应商编号")
 private String suggestSupplierId;


}