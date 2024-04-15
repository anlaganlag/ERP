package com.tadpole.cloud.product.api.productplan.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
 * <p>
 * 预提案 实体类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_PRE_PROPOSAL")
@ExcelIgnoreUnannotated
public class RdPreProposal implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 预案系统-预案编号 生成规则YA-[年](4位)[月](2位)[流水号](3位) */
   @TableId(value = "SYS_YA_CODE", type = IdType.ASSIGN_ID)
    private String sysYaCode;

    /** 预案系统-创建时间 */
    @TableField("SYS_C_DATE")
    private Date sysCDate;

    /** 预案系统-最后更新时间 */
    @TableField("SYS_L_DATE")
    private Date sysLDate;

    /** 预案系统-部门编号 */
    @TableField("SYS_DEPT_CODE")
    private String sysDeptCode;

    /** 预案系统-部门名称 */
    @TableField("SYS_DEPT_NAME")
    private String sysDeptName;

    /** 预案系统-员工编号 */
    @TableField("SYS_PER_CODE")
    private String sysPerCode;

    /** 预案系统-员工姓名 */
    @TableField("SYS_PER_NAME")
    private String sysPerName;

    /** 预案系统-预案状态 值域{"待申请","待反馈","待审核","待审批","已归档"} */
    @TableField("SYS_YA_STATUS")
    private String sysYaStatus;

    /** 单据联系-产品线编号 */
    @TableField("SYS_PL_CODE")
    private String sysPlCode;

    /** 预案公共-预案情景 值域{"开发预案","需求预案","推样预案"} */
    @TableField("SYS_YA_SCENE")
    private String sysYaScene;

    /** 预案公共-产品来源 值域{"-","展会-开发","展会-采购"} */
    @TableField("SYS_PRO_SOURCE")
    private String sysProSource;

    /** 预案公共-原开发方式 值域{"全新品-现货","全新品-定制","派生品-现货","派生品-改良","派生品-拓新"} */
    @TableField("SYS_DEV_METHOND")
    private String sysDevMethond;

    /** 预案公共-SPU */
    @TableField("SYS_SPU")
    private String sysSpu;

    /** 预案公共-老品产品定义书编号 */
    @TableField("SYS_OLD_PRO_DEFINE_NUM")
    private String sysOldProDefineNum;

    /** 预案公共-老品产品定义书版本 */
    @TableField("SYS_OLD_PRO_DEFINE_VERSION")
    private String sysOldProDefineVersion;

    /** 预案公共-产品名称 */
    @TableField("SYS_PRO_NAME")
    private String sysProName;

    /** 预案公共-款式 */
    @TableField("SYS_STYLE")
    private String sysStyle;

    /** 预案公共-适用品牌或对象 */
    @TableField("SYS_BRAND")
    private String sysBrand;

    /** 预案公共-主材料 */
    @TableField("SYS_MAIN_MATERIAL")
    private String sysMainMaterial;

    /** 预案公共-型号 */
    @TableField("SYS_MODEL")
    private String sysModel;

    /** 预案推样-参考图片 */
    @TableField("SYS_COM_REFER_PIC")
    private String sysComReferPic;

    /** 预案推样-参考链接 */
    @TableField("SYS_COM_REFER_LINK")
    private String sysComReferLink;

    /** 预案推样-附件 */
    @TableField("SYS_ANNEX")
    private String sysAnnex;

    /** 预案推样-推样数量 */
    @TableField("SYS_PUSH_SAMP_QTY")
    private BigDecimal sysPushSampQty;

    /** 预案调研-卖点说明 */
    @TableField("SYS_YA_SELL_POINT_DESC")
    private String sysYaSellPointDesc;

    /** 预案调研-市场评估 */
    @TableField("SYS_YA_MARKET_ASSE")
    private String sysYaMarketAsse;

    /** 预案调研-主攻市场 值域{"","北美","欧洲","英国","日本"} */
    @TableField("SYS_MAIN_MARKET")
    private String sysMainMarket;

    /** 预案调研-季节标签 */
    @TableField("SYS_SEASON_LABEL")
    private String sysSeasonLabel;

    /** 预案调研-节日标签 */
    @TableField("SYS_FESTIVAL_LABEL")
    private String sysFestivalLabel;

    /** 预案设计-功能要求 */
    @TableField("SYS_YA_FUNC_REQUIRE")
    private String sysYaFuncRequire;

    /** 预案设计-尺寸要求 */
    @TableField("SYS_YA_SIZE_REQUIRE")
    private String sysYaSizeRequire;

    /** 预案设计-重量要求 */
    @TableField("SYS_YA_WEIGHT_REQUIRE")
    private String sysYaWeightRequire;

    /** 预案设计-材质要求 */
    @TableField("SYS_YA_MATERIAL_REQUIRE")
    private String sysYaMaterialRequire;

    /** 预案设计-图案要求 */
    @TableField("SYS_YA_PATTERN_REQUIRE")
    private String sysYaPatternRequire;

    /** 预案设计-配件要求 */
    @TableField("SYS_YA_PARTS_REQUIRE")
    private String sysYaPartsRequire;

    /** 预案设计-包装要求 */
    @TableField("SYS_YA_PACKAGE_REQUIRE")
    private String sysYaPackageRequire;

    /** 预案设计-合规要求 */
    @TableField("SYS_YA_COMPLIANCE_REQUIRE")
    private String sysYaComplianceRequire;

    /** 预案设计-认证要求 */
    @TableField("SYS_YA_CERTIFICATION_REQUIREX")
    private String sysYaCertificationRequirex;

    /** 预案设计-使用场景 */
    @TableField("SYS_YA_USAGE_SCENARIO")
    private String sysYaUsageScenario;

    /** 预案设计-使用人群 */
    @TableField("SYS_YA_USER_GROUP")
    private String sysYaUserGroup;

    /** 预案拓新-图案拓新需求 */
    @TableField("SYS_PATTERN_NEW_DEMAND")
    private String sysPatternNewDemand;

    /** 预案拓新-图案拓新需求参考图片 */
    @TableField("SYS_PATTERN_NEW_DEMAND_PIC")
    private String sysPatternNewDemandPic;

    /** 预案拓新-颜色拓新需求 */
    @TableField("SYS_COLOR_NEW_DEMAND")
    private String sysColorNewDemand;

    /** 预案拓新-颜色拓新需求参考图片 */
    @TableField("SYS_COLOR_NEW_DEMAND_PIC")
    private String sysColorNewDemandPic;

    /** 预案拓新-规格拓新需求 格式：长*宽*高 */
    @TableField("SYS_NORMS_NEW_DEMAND")
    private String sysNormsNewDemand;

    /** 预案拓新-包装数量拓新需求 */
    @TableField("SYS_PACKAGE_QTY_NEW_DEMAND")
    private String sysPackageQtyNewDemand;

    /** 预案运营-采购成本区间要求 */
    @TableField("SYS_PUR_COST_RANGE_REQ")
    private String sysPurCostRangeReq;

    /** 预案运营-底线采购价(CNY) */
    @TableField("SYS_BOT_LINE_PUR_PRICE")
    private BigDecimal sysBotLinePurPrice;

    /** 预案运营-预计售价(USD) */
    @TableField("SYS_EST_SELL_PRICE")
    private BigDecimal sysEstSellPrice;

    /** 预案运营-底线销售利润率(%) */
    @TableField("SYS_BOT_LINE_SELL_PROFIT")
    private BigDecimal sysBotLineSellProfit;

    /** 预案运营-预计下单量(pcs) */
    @TableField("SYS_EST_ORDER_QTY")
    private BigDecimal sysEstOrderQty;

    /** 预案其他-上架时间节点 */
    @TableField("SYS_LIST_TIME_NODE")
    private Date sysListTimeNode;

    /** 预案其他-研发建议 */
    @TableField("SYS_RD_SUGGEST")
    private String sysRdSuggest;

    /** 预案提交-提交时间 */
    @TableField("SYS_SUB_DATE")
    private Date sysSubDate;

    /** 预案提交-提交员工编号 */
    @TableField("SYS_SUB_PER_CODE")
    private String sysSubPerCode;

    /** 预案提交-提交员工姓名 */
    @TableField("SYS_SUB_PER_NAME")
    private String sysSubPerName;

    /** 预案指派-产品经理编号 */
    @TableField("SYS_PM_PER_CODE")
    private String sysPmPerCode;

    /** 预案指派-产品经理姓名 */
    @TableField("SYS_PM_PER_NAME")
    private String sysPmPerName;

    /** 预案指派-分派时间 */
    @TableField("SYS_ASSIGN_DATE")
    private Date sysAssignDate;

    /** 预案指派-分派责任人姓名 */
    @TableField("SYS_ASSIGN_PER_NAME")
    private String sysAssignPerName;

    /** 预案指派-分派责任人编号 */
    @TableField("SYS_ASSIGN_PER_CODE")
    private String sysAssignPerCode;

    /** 预案反馈-反馈结果 值域{"","立项","不立项"} */
    @TableField("SYS_FEBK_RESULT")
    private String sysFebkResult;

    /** 预案反馈-反馈内容 */
    @TableField("SYS_FEBK_CONTENT")
    private String sysFebkContent;

    /** 预案反馈-反馈时间 */
    @TableField("SYS_FEBK_DATE")
    private Date sysFebkDate;

    /** 预案反馈-新开发方式 值域{"全新品-现货","全新品-定制","派生品-现货","派生品-改良","派生品-拓新"} */
    @TableField("SYS_FEBK_NEW_DEV_METHOND")
    private String sysFebkNewDevMethond;

    /** 预案反馈-新产品名称 */
    @TableField("SYS_NEW_PRO_NAME")
    private String sysNewProName;

    /** 预案反馈-新款式 */
    @TableField("SYS_NEW_STYLE")
    private String sysNewStyle;

    /** 预案反馈-新适用品牌或对象 */
    @TableField("SYS_NEW_BRAND")
    private String sysNewBrand;

    /** 预案反馈-新主材料 */
    @TableField("SYS_NEW_MAIN_MATERIAL")
    private String sysNewMainMaterial;

    /** 预案反馈-新型号 */
    @TableField("SYS_NEW_MODEL")
    private String sysNewModel;

    /** 预案反馈-变更说明 */
    @TableField("SYS_FEBK_CHANGE_DESC")
    private String sysFebkChangeDesc;

    /** 预案反馈-首单采购数量评估 */
    @TableField("SYS_FEBK_FIRST_ORDER_QTY_EVA")
    private BigDecimal sysFebkFirstOrderQtyEva;

    /** 预案反馈-首单采购金额预估 */
    @TableField("SYS_FEBK_FIRST_ORDER_AMOUN_EVA")
    private BigDecimal sysFebkFirstOrderAmounEva;

    /** 预案反馈-提案等级 */
    @TableField("SYS_FEBK_PROPOSAL_LEVEL")
    private String sysFebkProposalLevel;

    /** 预案反馈-反馈员工姓名 */
    @TableField("SYS_FEBK_PER_NAME")
    private String sysFebkPerName;

    /** 预案反馈-反馈员工编号 */
    @TableField("SYS_FEBK_PER_CODE")
    private String sysFebkPerCode;

    /** 预案审核-审核结果 值域{"","同意","重新反馈","不同意"} */
    @TableField("SYS_EXAM_RESULT")
    private String sysExamResult;

    /** 预案审核-审核说明 */
    @TableField("SYS_EXAM_INSTRUCTE")
    private String sysExamInstructe;

    /** 预案审核-审核人编号 */
    @TableField("SYS_EXAM_PER_CODE")
    private String sysExamPerCode;

    /** 预案审核-审核人姓名 */
    @TableField("SYS_EXAM_PER_NAME")
    private String sysExamPerName;

    /** 预案审核-审核时间 */
    @TableField("SYS_EXAM_DATE")
    private Date sysExamDate;

    /** 预案审批-审批结果 值域{"","通过","不通过"} */
    @TableField("SYS_APPR_RESULT")
    private String sysApprResult;

    /** 预案审批-审批备注 */
    @TableField("SYS_APPR_REMARKS")
    private String sysApprRemarks;

    /** 预案审批-审批时间 */
    @TableField("SYS_APPR_DATE")
    private Date sysApprDate;

    /** 预案审批-审批人编号 */
    @TableField("SYS_APPR_PER_CODE")
    private String sysApprPerCode;

    /** 预案审批-审批人姓名 */
    @TableField("SYS_APPR_PER_NAME")
    private String sysApprPerName;

    /** 预案归档-归档类型 值域{"","撤销归档","反馈归档","审核归档","审批归档"} */
    @TableField("SYS_ARCH_TYPE")
    private String sysArchType;

    /** 预案归档-归档备注 */
    @TableField("SYS_ARCH_REMARKS")
    private String sysArchRemarks;

    /** 预案归档-归档时间 */
    @TableField("SYS_ARCH_DATE")
    private Date sysArchDate;

    /** 预案归档-归档员工姓名 */
    @TableField("SYS_ARCH_PER_NAME")
    private String sysArchPerName;

    /** 预案归档-归档员工编号 */
    @TableField("SYS_ARCH_PER_CODE")
    private String sysArchPerCode;

}