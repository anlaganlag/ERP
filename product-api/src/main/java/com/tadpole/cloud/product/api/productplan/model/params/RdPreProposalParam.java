package com.tadpole.cloud.product.api.productplan.model.params;

import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.*;

/**
 * <p>
 * 预提案 入参类
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
public class RdPreProposalParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 预案系统-预案编号 生成规则YA-[年](4位)[月](2位)[流水号](3位) */
   @TableId(value = "SYS_YA_CODE", type = IdType.AUTO)
    private String sysYaCode;

    /** 预案系统-创建时间 */
    @ApiModelProperty("预案系统-创建时间")
    private Date sysCDate;

    /** 预案系统-最后更新时间 */
    @ApiModelProperty("预案系统-最后更新时间")
    private Date sysLDate;

    /** 预案系统-部门编号 */
    @ApiModelProperty("预案系统-部门编号")
    private String sysDeptCode;

    /** 预案系统-部门名称 */
    @ApiModelProperty("预案系统-部门名称")
    private String sysDeptName;

    /** 预案系统-员工编号 */
    @ApiModelProperty("预案系统-员工编号")
    private String sysPerCode;

    /** 预案系统-员工姓名 */
    @ApiModelProperty("预案系统-员工姓名")
    private String sysPerName;

    /** 预案系统-预案状态 值域{"待申请","待反馈","待审核","待审批","已归档"} */
    @ApiModelProperty("预案系统-预案状态 值域{'待申请','待反馈','待审核','待审批','已归档'}")
    private String sysYaStatus;

    /** 单据联系-产品线编号 */
    @ApiModelProperty("单据联系-产品线编号")
    private String sysPlCode;

    /** 预案公共-预案情景 值域{"开发预案","需求预案","推样预案"} */
    @ApiModelProperty("预案公共-预案情景 值域{'开发预案','需求预案','推样预案'}")
    private String sysYaScene;

    /** 预案公共-产品来源 值域{"-","展会-开发","展会-采购"} */
    @ApiModelProperty("预案公共-产品来源 值域{'-','展会-开发','展会-采购'}")
    private String sysProSource;

    /** 预案公共-原开发方式 值域{"全新品-现货","全新品-定制","派生品-现货","派生品-改良","派生品-拓新"} */
    @ApiModelProperty("预案公共-原开发方式 值域{'全新品-现货','全新品-定制','派生品-现货','派生品-改良','派生品-拓新'}")
    private String sysDevMethond;

    /** 预案公共-SPU */
    @ApiModelProperty("预案公共-SPU")
    private String sysSpu;

    /** 预案公共-老品产品定义书编号 */
    @ApiModelProperty("预案公共-老品产品定义书编号")
    private String sysOldProDefineNum;

    /** 预案公共-老品产品定义书版本 */
    @ApiModelProperty("预案公共-老品产品定义书版本")
    private String sysOldProDefineVersion;

    /** 预案公共-产品名称 */
    @ApiModelProperty("预案公共-产品名称")
    private String sysProName;

    /** 预案公共-款式 */
    @ApiModelProperty("预案公共-款式")
    private String sysStyle;

    /** 预案公共-适用品牌或对象 */
    @ApiModelProperty("预案公共-适用品牌或对象")
    private String sysBrand;

    /** 预案公共-主材料 */
    @ApiModelProperty("预案公共-主材料")
    private String sysMainMaterial;

    /** 预案公共-型号 */
    @ApiModelProperty("预案公共-型号")
    private String sysModel;

    /** 预案推样-参考图片 */
    @ApiModelProperty("预案推样-参考图片")
    private String sysComReferPic;

    /** 预案推样-参考链接 */
    @ApiModelProperty("预案推样-参考链接")
    private String sysComReferLink;

    /** 预案推样-附件 */
    @ApiModelProperty("预案推样-附件")
    private String sysAnnex;

    /** 预案推样-推样数量 */
    @ApiModelProperty("预案推样-推样数量")
    private BigDecimal sysPushSampQty;

    /** 预案调研-卖点说明 */
    @ApiModelProperty("预案调研-卖点说明")
    private String sysYaSellPointDesc;

    /** 预案调研-市场评估 */
    @ApiModelProperty("预案调研-市场评估")
    private String sysYaMarketAsse;

    /** 预案调研-主攻市场 值域{"","北美","欧洲","英国","日本"} */
    @ApiModelProperty("预案调研-主攻市场 值域{'','北美','欧洲','英国','日本'}")
    private String sysMainMarket;

    /** 预案调研-季节标签 */
    @ApiModelProperty("预案调研-季节标签")
    private String sysSeasonLabel;

    /** 预案调研-节日标签 */
    @ApiModelProperty("预案调研-节日标签")
    private String sysFestivalLabel;

    /** 预案设计-功能要求 */
    @ApiModelProperty("预案设计-功能要求")
    private String sysYaFuncRequire;

    /** 预案设计-尺寸要求 */
    @ApiModelProperty("预案设计-尺寸要求")
    private String sysYaSizeRequire;

    /** 预案设计-重量要求 */
    @ApiModelProperty("预案设计-重量要求")
    private String sysYaWeightRequire;

    /** 预案设计-材质要求 */
    @ApiModelProperty("预案设计-材质要求")
    private String sysYaMaterialRequire;

    /** 预案设计-图案要求 */
    @ApiModelProperty("预案设计-图案要求")
    private String sysYaPatternRequire;

    /** 预案设计-配件要求 */
    @ApiModelProperty("预案设计-配件要求")
    private String sysYaPartsRequire;

    /** 预案设计-包装要求 */
    @ApiModelProperty("预案设计-包装要求")
    private String sysYaPackageRequire;

    /** 预案设计-合规要求 */
    @ApiModelProperty("预案设计-合规要求")
    private String sysYaComplianceRequire;

    /** 预案设计-认证要求 */
    @ApiModelProperty("预案设计-认证要求")
    private String sysYaCertificationRequirex;

    /** 预案设计-使用场景 */
    @ApiModelProperty("预案设计-使用场景")
    private String sysYaUsageScenario;

    /** 预案设计-使用人群 */
    @ApiModelProperty("预案设计-使用人群")
    private String sysYaUserGroup;

    /** 预案拓新-图案拓新需求 */
    @ApiModelProperty("预案拓新-图案拓新需求")
    private String sysPatternNewDemand;

    /** 预案拓新-图案拓新需求参考图片 */
    @ApiModelProperty("预案拓新-图案拓新需求参考图片")
    private String sysPatternNewDemandPic;

    /** 预案拓新-颜色拓新需求 */
    @ApiModelProperty("预案拓新-颜色拓新需求")
    private String sysColorNewDemand;

    /** 预案拓新-颜色拓新需求参考图片 */
    @ApiModelProperty("预案拓新-颜色拓新需求参考图片")
    private String sysColorNewDemandPic;

    /** 预案拓新-规格拓新需求 格式：长*宽*高 */
    @ApiModelProperty("预案拓新-规格拓新需求 格式：长*宽*高")
    private String sysNormsNewDemand;

    /** 预案拓新-包装数量拓新需求 */
    @ApiModelProperty("预案拓新-包装数量拓新需求")
    private String sysPackageQtyNewDemand;

    /** 预案运营-采购成本区间要求 */
    @ApiModelProperty("预案运营-采购成本区间要求")
    private String sysPurCostRangeReq;

    /** 预案运营-底线采购价(CNY) */
    @ApiModelProperty("预案运营-底线采购价(CNY)")
    private BigDecimal sysBotLinePurPrice;

    /** 预案运营-预计售价(USD) */
    @ApiModelProperty("预案运营-预计售价(USD)")
    private BigDecimal sysEstSellPrice;

    /** 预案运营-底线销售利润率(%) */
    @ApiModelProperty("预案运营-底线销售利润率(%)")
    private BigDecimal sysBotLineSellProfit;

    /** 预案运营-预计下单量(pcs) */
    @ApiModelProperty("预案运营-预计下单量(pcs)")
    private BigDecimal sysEstOrderQty;

    /** 预案其他-上架时间节点 */
    @ApiModelProperty("预案其他-上架时间节点")
    private Date sysListTimeNode;

    /** 预案其他-研发建议 */
    @ApiModelProperty("预案其他-研发建议")
    private String sysRdSuggest;

    /** 预案提交-提交时间 */
    @ApiModelProperty("预案提交-提交时间")
    private Date sysSubDate;

    /** 预案提交-提交员工编号 */
    @ApiModelProperty("预案提交-提交员工编号")
    private String sysSubPerCode;

    /** 预案提交-提交员工姓名 */
    @ApiModelProperty("预案提交-提交员工姓名")
    private String sysSubPerName;

    /** 预案指派-产品经理编号 */
    @ApiModelProperty("预案指派-产品经理编号")
    private String sysPmPerCode;

    /** 预案指派-产品经理姓名 */
    @ApiModelProperty("预案指派-产品经理姓名")
    private String sysPmPerName;

    /** 预案指派-分派时间 */
    @ApiModelProperty("预案指派-分派时间")
    private Date sysAssignDate;

    /** 预案指派-分派责任人姓名 */
    @ApiModelProperty("预案指派-分派责任人姓名")
    private String sysAssignPerName;

    /** 预案指派-分派责任人编号 */
    @ApiModelProperty("预案指派-分派责任人编号")
    private String sysAssignPerCode;

    /** 预案反馈-反馈结果 值域{"","立项","不立项"} */
    @ApiModelProperty("预案反馈-反馈结果 值域{'','立项','不立项'}")
    private String sysFebkResult;

    /** 预案反馈-反馈内容 */
    @ApiModelProperty("预案反馈-反馈内容")
    private String sysFebkContent;

    /** 预案反馈-反馈时间 */
    @ApiModelProperty("预案反馈-反馈时间")
    private Date sysFebkDate;

    /** 预案反馈-新开发方式 值域{"全新品-现货","全新品-定制","派生品-现货","派生品-改良","派生品-拓新"} */
    @ApiModelProperty("预案反馈-新开发方式 值域{'全新品-现货','全新品-定制','派生品-现货','派生品-改良','派生品-拓新'}")
    private String sysFebkNewDevMethond;

    /** 预案反馈-新产品名称 */
    @ApiModelProperty("预案反馈-新产品名称")
    private String sysNewProName;

    /** 预案反馈-新款式 */
    @ApiModelProperty("预案反馈-新款式")
    private String sysNewStyle;

    /** 预案反馈-新适用品牌或对象 */
    @ApiModelProperty("预案反馈-新适用品牌或对象")
    private String sysNewBrand;

    /** 预案反馈-新主材料 */
    @ApiModelProperty("预案反馈-新主材料")
    private String sysNewMainMaterial;

    /** 预案反馈-新型号 */
    @ApiModelProperty("预案反馈-新型号")
    private String sysNewModel;

    /** 预案反馈-变更说明 */
    @ApiModelProperty("预案反馈-变更说明")
    private String sysFebkChangeDesc;

    /** 预案反馈-首单采购数量评估 */
    @ApiModelProperty("预案反馈-首单采购数量评估")
    private BigDecimal sysFebkFirstOrderQtyEva;

    /** 预案反馈-首单采购金额预估 */
    @ApiModelProperty("预案反馈-首单采购金额预估")
    private BigDecimal sysFebkFirstOrderAmounEva;

    /** 预案反馈-提案等级 */
    @ApiModelProperty("预案反馈-提案等级")
    private String sysFebkProposalLevel;

    /** 预案反馈-反馈员工姓名 */
    @ApiModelProperty("预案反馈-反馈员工姓名")
    private String sysFebkPerName;

    /** 预案反馈-反馈员工编号 */
    @ApiModelProperty("预案反馈-反馈员工编号")
    private String sysFebkPerCode;

    /** 预案审核-审核结果 值域{"","同意","重新反馈","不同意"} */
    @ApiModelProperty("预案审核-审核结果 值域{'','同意','重新反馈','不同意'}")
    private String sysExamResult;

    /** 预案审核-审核说明 */
    @ApiModelProperty("预案审核-审核说明")
    private String sysExamInstructe;

    /** 预案审核-审核人编号 */
    @ApiModelProperty("预案审核-审核人编号")
    private String sysExamPerCode;

    /** 预案审核-审核人姓名 */
    @ApiModelProperty("预案审核-审核人姓名")
    private String sysExamPerName;

    /** 预案审核-审核时间 */
    @ApiModelProperty("预案审核-审核时间")
    private Date sysExamDate;

    /** 预案审批-审批结果 值域{"","通过","不通过"} */
    @ApiModelProperty("预案审批-审批结果 值域{'','通过','不通过'}")
    private String sysApprResult;

    /** 预案审批-审批备注 */
    @ApiModelProperty("预案审批-审批备注")
    private String sysApprRemarks;

    /** 预案审批-审批时间 */
    @ApiModelProperty("预案审批-审批时间")
    private Date sysApprDate;

    /** 预案审批-审批人编号 */
    @ApiModelProperty("预案审批-审批人编号")
    private String sysApprPerCode;

    /** 预案审批-审批人姓名 */
    @ApiModelProperty("预案审批-审批人姓名")
    private String sysApprPerName;

    /** 预案归档-归档类型 值域{"","撤销归档","反馈归档","审核归档","审批归档"} */
    @ApiModelProperty("预案归档-归档类型 值域{'','撤销归档','反馈归档','审核归档','审批归档'}")
    private String sysArchType;

    /** 预案归档-归档备注 */
    @ApiModelProperty("预案归档-归档备注")
    private String sysArchRemarks;

    /** 预案归档-归档时间 */
    @ApiModelProperty("预案归档-归档时间")
    private Date sysArchDate;

    /** 预案归档-归档员工姓名 */
    @ApiModelProperty("预案归档-归档员工姓名")
    private String sysArchPerName;

    /** 预案归档-归档员工编号 */
    @ApiModelProperty("预案归档-归档员工编号")
    private String sysArchPerCode;

    /** 预案系统-预案状态集合 */
    @ApiModelProperty("预案系统-预案状态 值域{'待申请','待反馈','待审核','待审批','已归档'}")
    private List<String> sysYaStatusList;

    /** 产品线设定-产品线名称集合 */
    @ApiModelProperty("产品线设定-产品线名称")
    private List<String> sysPlNameList;

    /** 产品线设定-产品线编码集合 */
    @ApiModelProperty("产品线设定-产品线编码集合")
    private List<String> sysPlCodeList;

    /** 预案公共-原开发方式 值域{"全新品-现货","全新品-定制","派生品-现货","派生品-改良","派生品-拓新"} */
    @ApiModelProperty("预案公共-原开发方式 值域{'全新品-现货','全新品-定制','派生品-现货','派生品-改良','派生品-拓新'}")
    private List<String> sysDevMethondList;

    /** 预案归档-归档时间-开始时间 */
    @ApiModelProperty("预案归档-归档时间-开始时间")
    private Date sysArchStartDate;

    /** 预案归档-归档时间-结束时间 */
    @ApiModelProperty("预案归档-归档时间-结束时间")
    private Date sysArchEndDate;

    /** 预案提交-申请时间-开始时间 */
    @ApiModelProperty("预案提交-申请时间-开始时间")
    private Date sysSubStartDate;

    /** 预案提交-申请时间-结束时间 */
    @ApiModelProperty("预案提交-申请时间-开始时间")
    private Date sysSubEndDate;

    /** 预案系统-预案编号集合 */
    @ApiModelProperty("预案系统-预案编号集合")
    private List<String> sysYaCodeList;

    /** 预案提交-提交员工姓名集合 */
    @ApiModelProperty("预案提交-提交员工姓名")
    private List<String> sysSubPerNameList;

    /** 预案指派-产品经理姓名集合 */
    @ApiModelProperty("预案指派-产品经理姓名")
    private List<String> sysPmPerNameList;

    @ApiModelProperty("预案-改良")
    private List<RdPreProposalUpParam> rdPreProposalUpParams;

    /** 界面操作-新增/编辑 */
    @ApiModelProperty("界面操作-新增/编辑")
    private String sysPageOpr;

    /** 功能操作-保存/提交 */
    @ApiModelProperty("界面操作-保存/提交")
    private String sysFuncOpr;

    /** 预案公共-预案情景集合 */
    @ApiModelProperty("预案公共-预案情景 值域{'开发预案','需求预案','推样预案'}")
    private List<String> sysYaSceneList;


     /** 预案提交-提交时间集合 */
     @ApiModelProperty("预案提交-提交时间集合")
     private List<String> sysSubDateList;

     /** 预案归档-归档时间集合 */
     @ApiModelProperty("预案归档-归档时间集合")
     private List<String> sysArchDateList;

}
