package com.tadpole.cloud.product.api.productplan.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 预提案 出参类
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
@ExcelIgnoreUnannotated
@TableName("RD_PRE_PROPOSAL")
public class RdPreProposalResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "SYS_YA_CODE", type = IdType.AUTO)
    private String sysYaCode;


    @ApiModelProperty("预案系统-创建时间")
    private Date sysCDate;


    @ApiModelProperty("预案系统-最后更新时间")
    private Date sysLDate;


    @ApiModelProperty("预案系统-部门编号")
    private String sysDeptCode;


    @ApiModelProperty("预案系统-部门名称")
    private String sysDeptName;


    @ApiModelProperty("预案系统-员工编号")
    private String sysPerCode;


    @ApiModelProperty("预案系统-员工姓名")
    private String sysPerName;


    @ApiModelProperty("预案系统-预案状态 值域{'待申请','待反馈','待审核','待审批','已归档'}")
    private String sysYaStatus;


    @ApiModelProperty("单据联系-产品线编号")
    private String sysPlCode;


    @ApiModelProperty("预案公共-预案情景 值域{'开发预案','需求预案','推样预案'}")
    private String sysYaScene;


    @ApiModelProperty("预案公共-产品来源 值域{'-','展会-开发','展会-采购'}")
    private String sysProSource;


    @ApiModelProperty("预案公共-原开发方式 值域{'全新品-现货','全新品-定制','派生品-现货','派生品-改良','派生品-拓新'}")
    private String sysDevMethond;


    @ApiModelProperty("预案公共-SPU")
    private String sysSpu;


    @ApiModelProperty("预案公共-老品产品定义书编号")
    private String sysOldProDefineNum;


    @ApiModelProperty("预案公共-老品产品定义书版本")
    private String sysOldProDefineVersion;


    @ApiModelProperty("预案公共-产品名称")
    private String sysProName;


    @ApiModelProperty("预案公共-款式")
    private String sysStyle;


    @ApiModelProperty("预案公共-适用品牌或对象")
    private String sysBrand;


    @ApiModelProperty("预案公共-主材料")
    private String sysMainMaterial;


    @ApiModelProperty("预案公共-型号")
    private String sysModel;


    @ApiModelProperty("预案推样-参考图片")
    private String sysComReferPic;


    @ApiModelProperty("预案推样-参考链接")
    private String sysComReferLink;


    @ApiModelProperty("预案推样-附件")
    private String sysAnnex;


    @ApiModelProperty("预案推样-推样数量")
    private BigDecimal sysPushSampQty;


    @ApiModelProperty("预案调研-卖点说明")
    private String sysYaSellPointDesc;


    @ApiModelProperty("预案调研-市场评估")
    private String sysYaMarketAsse;


    @ApiModelProperty("预案调研-主攻市场 值域{'','北美','欧洲','英国','日本'}")
    private String sysMainMarket;


    @ApiModelProperty("预案调研-季节标签")
    private String sysSeasonLabel;


    @ApiModelProperty("预案调研-节日标签")
    private String sysFestivalLabel;


    @ApiModelProperty("预案设计-功能要求")
    private String sysYaFuncRequire;


    @ApiModelProperty("预案设计-尺寸要求")
    private String sysYaSizeRequire;


    @ApiModelProperty("预案设计-重量要求")
    private String sysYaWeightRequire;


    @ApiModelProperty("预案设计-材质要求")
    private String sysYaMaterialRequire;


    @ApiModelProperty("预案设计-图案要求")
    private String sysYaPatternRequire;


    @ApiModelProperty("预案设计-配件要求")
    private String sysYaPartsRequire;


    @ApiModelProperty("预案设计-包装要求")
    private String sysYaPackageRequire;


    @ApiModelProperty("预案设计-合规要求")
    private String sysYaComplianceRequire;


    @ApiModelProperty("预案设计-认证要求")
    private String sysYaCertificationRequirex;


    @ApiModelProperty("预案设计-使用场景")
    private String sysYaUsageScenario;


    @ApiModelProperty("预案设计-使用人群")
    private String sysYaUserGroup;


    @ApiModelProperty("预案拓新-图案拓新需求")
    private String sysPatternNewDemand;


    @ApiModelProperty("预案拓新-图案拓新需求参考图片")
    private String sysPatternNewDemandPic;


    @ApiModelProperty("预案拓新-颜色拓新需求")
    private String sysColorNewDemand;


    @ApiModelProperty("预案拓新-颜色拓新需求参考图片")
    private String sysColorNewDemandPic;


    @ApiModelProperty("预案拓新-规格拓新需求 格式：长*宽*高")
    private String sysNormsNewDemand;


    @ApiModelProperty("预案拓新-包装数量拓新需求")
    private String sysPackageQtyNewDemand;


    @ApiModelProperty("预案运营-采购成本区间要求")
    private String sysPurCostRangeReq;


    @ApiModelProperty("预案运营-底线采购价(CNY)")
    private BigDecimal sysBotLinePurPrice;


    @ApiModelProperty("预案运营-预计售价(USD)")
    private BigDecimal sysEstSellPrice;


    @ApiModelProperty("预案运营-底线销售利润率(%)")
    private BigDecimal sysBotLineSellProfit;


    @ApiModelProperty("预案运营-预计下单量(pcs)")
    private BigDecimal sysEstOrderQty;


    @ApiModelProperty("预案其他-上架时间节点")
    private Date sysListTimeNode;


    @ApiModelProperty("预案其他-研发建议")
    private String sysRdSuggest;


    @ApiModelProperty("预案提交-提交时间")
    private Date sysSubDate;


    @ApiModelProperty("预案提交-提交员工编号")
    private String sysSubPerCode;


    @ApiModelProperty("预案提交-提交员工姓名")
    private String sysSubPerName;


    @ApiModelProperty("预案指派-产品经理编号")
    private String sysPmPerCode;


    @ApiModelProperty("预案指派-产品经理姓名")
    private String sysPmPerName;


    @ApiModelProperty("预案指派-分派时间")
    private Date sysAssignDate;


    @ApiModelProperty("预案指派-分派责任人姓名")
    private String sysAssignPerName;


    @ApiModelProperty("预案指派-分派责任人编号")
    private String sysAssignPerCode;


    @ApiModelProperty("预案反馈-反馈结果 值域{'','立项','不立项'}")
    private String sysFebkResult;


    @ApiModelProperty("预案反馈-反馈内容")
    private String sysFebkContent;


    @ApiModelProperty("预案反馈-反馈时间")
    private Date sysFebkDate;


    @ApiModelProperty("预案反馈-新开发方式 值域{'全新品-现货','全新品-定制','派生品-现货','派生品-改良','派生品-拓新'}")
    private String sysFebkNewDevMethond;


    @ApiModelProperty("预案反馈-新产品名称")
    private String sysNewProName;


    @ApiModelProperty("预案反馈-新款式")
    private String sysNewStyle;


    @ApiModelProperty("预案反馈-新适用品牌或对象")
    private String sysNewBrand;


    @ApiModelProperty("预案反馈-新主材料")
    private String sysNewMainMaterial;


    @ApiModelProperty("预案反馈-新型号")
    private String sysNewModel;


    @ApiModelProperty("预案反馈-变更说明")
    private String sysFebkChangeDesc;


    @ApiModelProperty("预案反馈-首单采购数量评估")
    private BigDecimal sysFebkFirstOrderQtyEva;


    @ApiModelProperty("预案反馈-首单采购金额预估")
    private BigDecimal sysFebkFirstOrderAmounEva;


    @ApiModelProperty("预案反馈-提案等级")
    private String sysFebkProposalLevel;


    @ApiModelProperty("预案反馈-反馈员工姓名")
    private String sysFebkPerName;


    @ApiModelProperty("预案反馈-反馈员工编号")
    private String sysFebkPerCode;


    @ApiModelProperty("预案审核-审核结果 值域{'','同意','重新反馈','不同意'}")
    private String sysExamResult;


    @ApiModelProperty("预案审核-审核说明")
    private String sysExamInstructe;


    @ApiModelProperty("预案审核-审核人编号")
    private String sysExamPerCode;


    @ApiModelProperty("预案审核-审核人姓名")
    private String sysExamPerName;


    @ApiModelProperty("预案审核-审核时间")
    private Date sysExamDate;


    @ApiModelProperty("预案审批-审批结果 值域{'','通过','不通过'}")
    private String sysApprResult;


    @ApiModelProperty("预案审批-审批备注")
    private String sysApprRemarks;


    @ApiModelProperty("预案审批-审批时间")
    private Date sysApprDate;


    @ApiModelProperty("预案审批-审批人编号")
    private String sysApprPerCode;


    @ApiModelProperty("预案审批-审批人姓名")
    private String sysApprPerName;


    @ApiModelProperty("预案归档-归档类型 值域{'','撤销归档','反馈归档','审核归档','审批归档'}")
    private String sysArchType;


    @ApiModelProperty("预案归档-归档备注")
    private String sysArchRemarks;


    @ApiModelProperty("预案归档-归档时间")
    private Date sysArchDate;


    @ApiModelProperty("预案归档-归档员工姓名")
    private String sysArchPerName;


    @ApiModelProperty("预案归档-归档员工编号")
    private String sysArchPerCode;

}
