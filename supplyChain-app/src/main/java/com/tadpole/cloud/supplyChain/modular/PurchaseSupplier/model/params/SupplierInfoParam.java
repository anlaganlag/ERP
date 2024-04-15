package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.params;

import cn.stylefeng.guns.cloud.model.param.BaseParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierAccountInfo;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierContactInfo;
import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

import lombok.*;

/**
 * <p>
 * 供应商-供应商信息 入参类
 * </p>
 *
 * @author S20190109
 * @since 2023-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SUPPLIER_INFO")
public class SupplierInfoParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("系统编号")
    private long id;

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

    /** 系统信息-有效状态 值域{"失效","生效"} */
    @ApiModelProperty("系统信息-有效状态 值域{失效,生效}")
    private String sysValidStatus;

    /** 系统信息-审核状态 值域{"待提交","待管理审核","待资质审核","资质不通过","通过","归档"} */
    @ApiModelProperty("系统信息-审核状态 值域{待提交,待管理审核,待资质审核,资质不通过,待审批,通过,归档}")
    private String sysAuditStatus;

    /** 基本信息-供应商编码 */
    @ApiModelProperty("基本信息-供应商编码")
    private String sysSupCode;

    /** 基本信息-供应商名称 */
    @ApiModelProperty("基本信息-供应商名称")
    private String sysSupName;

    /** 基本信息-供应商简称 */
    @ApiModelProperty("基本信息-供应商简称")
    private String sysSupAbb;

    /** 基本信息-准入类型 值域{"购样","开模","成品"} */
    @ApiModelProperty("基本信息-准入类型 值域{购样,开模,成品}")
    private String sysAdmitType;

    /** 基本信息-供应商等级 值域{"临时供应商","一般供应商","重点供应商","核心供应商","淘汰供应商"} */
    @ApiModelProperty("基本信息-供应商等级 值域{临时供应商,一般供应商,重点供应商,核心供应商,淘汰供应商}")
    private String sysSupLevel;

    /** 基本信息-省份 */
    @ApiModelProperty("基本信息-省份")
    private String sysProvince;

    /** 基本信息-市区 */
    @ApiModelProperty("基本信息-市区")
    private String sysUrbanArea;

    /** 基本信息-通讯地址 */
    @ApiModelProperty("基本信息-通讯地址")
    private String sysMailAddress;

    /** 基本信息-行业 */
    @ApiModelProperty("基本信息-行业")
    private String sysIndustry;

    /** 基本信息-运营大类 */
    @ApiModelProperty("基本信息-运营大类")
    private String sysOperateCate;

    /** 基本信息-主营产品 */
    @ApiModelProperty("基本信息-主营产品")
    private String sysMainProduct;

    /** 基本信息-月产能 */
    @ApiModelProperty("基本信息-月产能")
    private String sysMonProductCapa;

    /** 基本信息-信用代码 */
    @ApiModelProperty("基本信息-信用代码")
    private String sysCreditCode;

    /** 基本信息-注册地址 */
    @ApiModelProperty("基本信息-注册地址")
    private String sysRegistAddress;

    /** 基本信息-营业执照 */
    @ApiModelProperty("基本信息-营业执照")
    private String sysBusinessLicense;

    /** 基本信息-供应商承诺书 */
    @ApiModelProperty("基本信息-供应商承诺书")
    private String sysSupCommitLetter;

    /** 基本信息-工厂图片 */
    @ApiModelProperty("基本信息-工厂图片")
    private String sysFactoryPic;

    /** 基本信息-合作伙伴信息调查表 */
    @ApiModelProperty("基本信息-合作伙伴信息调查表")
    private String sysPatnerInfoQueste;

    /** 基本信息-补充协议 */
    @ApiModelProperty("基本信息-补充协议")
    private String sysSuppleAgreement;

    /** 基本信息-框架协议 */
    @ApiModelProperty("基本信息-框架协议")
    private String sysFwAgreement;

    /** 基本信息-质量协议 */
    @ApiModelProperty("基本信息-质量协议")
    private String sysQualityAgreement;

    /** 基本信息-专利证明 */
    @ApiModelProperty("基本信息-专利证明")
    private String sysPatentCertficate;

    /** 基本信息-纳税等级 */
    @ApiModelProperty("基本信息-纳税等级")
    private String sysTaxLevel;

    /** 基本信息-其他附件 */
    @ApiModelProperty("基本信息-其他附件")
    private String sysOtherAttachment;

    /** 财务信息-开票公司类型 值域{"贸易公司","生产加工",...} */
    @ApiModelProperty("财务信息-开票公司类型 值域{贸易公司,生产加工,...}")
    private String sysInvoCompType;

    /** 财务信息-结算币别 值域{"人民币",...} */
    @ApiModelProperty("财务信息-结算币别 值域{人民币,...}")
    private String sysSettlementCurrency;

    /** 财务信息-结算方式 值域{"电汇","支付宝",...} */
    @ApiModelProperty("财务信息-结算方式 值域{电汇,支付宝,...}")
    private String sysSettlementMethod;

    /** 财务信息-付款条件 值域{"货到明细付款",	"半月结",	"月结",	"月结60天",	"每周结",	"预付30%，货到后70%",	"100%预付款",	"货好后100%预付款",	"网拍",	"预付定金15%，尾款85%月结",	"预付先20% 后80%",	"预付30%，再预付60%，月结10%",	"预付30%,再预付70%",	"预付30%，月结70%",	"预付50%，尾款50%月结"} */
    @ApiModelProperty("财务信息-付款条件 值域{货到明细付款,	半月结,	月结,	月结60天,每周结,预付30%，货到后70%,100%预付款,	货好后100%预付款,	网拍,预付定金15%，尾款85%月结,预付先20% 后80%,预付30%，再预付60%，月结10%,预付30%,再预付70%,预付30%，月结70%,预付50%，尾款50%月结}")
    private String sysPaymentTerms;

    /** 财务信息-税分类 值域{"小规模纳税人","一般纳税人","个体工商户",...} */
    @ApiModelProperty("财务信息-税分类 值域{小规模纳税人,一般纳税人,个体工商户,...}")
    private String sysTaxClassify;

    /** 财务信息-发票类型 值域{"增值税专用发票","普通发票","无",...} */
    @ApiModelProperty("财务信息-发票类型 值域{增值税专用发票,普通发票,无,...}")
    private String sysInvoiceType;

    /** 财务信息-默认税率 值域{"零税率","1%增值税","1%普通发票","3%增值税","13%增值税","3%普通发票","13%普通发票","无",...} */
    @ApiModelProperty("财务信息-默认税率 值域{零税率,1%增值税,1%普通发票,3%增值税,13%增值税,3%普通发票,13%普通发票,无,...}")
    private String sysDefaultTaxRate;

    /** 财务信息-开票资料 */
    @ApiModelProperty("财务信息-开票资料")
    private String sysInvoiceInfo;

    /** 财务信息-供应链公司 */
    @ApiModelProperty("财务信息-供应链公司")
    private String sysSupChainComp;

    /** 管理信息-供应商分组 */
    @ApiModelProperty("管理信息-供应商分组")
    private String sysSupGroup;

    /** 管理信息-负责部门名称 */
    @ApiModelProperty("管理信息-负责部门名称")
    private String sysPurChargeDeptName;

    /** 管理信息-负责部门编码 */
    @ApiModelProperty("管理信息-负责部门编码")
    private String sysPurChargeDeptCode;

    /** 管理信息-采购负责人员工姓名 */
    @ApiModelProperty("管理信息-采购负责人员工姓名")
    private String sysPurChargePerName;

    /** 管理信息-采购负责人员工编码 */
    @ApiModelProperty("管理信息-采购负责人员工编码")
    private String sysPurChargePerCode;

    /** 管理信息-物控专员员工姓名 */
    @ApiModelProperty("管理信息-物控专员员工姓名")
    private String sysMcsPerName;

    /** 管理信息-物控专员员工编码 */
    @ApiModelProperty("管理信息-物控专员员工编码")
    private String sysMcsPerCode;

    /** 管理信息-备注 */
    @ApiModelProperty("管理信息-备注")
    private String sysRemarks;

    /** 推荐信息-是否公司员工推荐 值域{"是","否"} */
    @ApiModelProperty("推荐信息-是否公司员工推荐 值域{是,否}")
    private String sysIsSugBycompStaf;

    /** 推荐信息-推荐人姓名 */
    @ApiModelProperty("推荐信息-推荐人姓名")
    private String sysSugPerName;

    /** 推荐信息-与推荐人关系 */
    @ApiModelProperty("推荐信息-与推荐人关系")
    private String sysRelateToSug;

    /** 推荐信息-是否需要补充资料 值域{"是","否"} */
    @ApiModelProperty("推荐信息-是否需要补充资料 值域{是,否}")
    private String sysIsRecomInfo;

    /** 推荐信息-资料附件 */
    @ApiModelProperty("推荐信息-资料附件")
    private String sysDataAttachment;

    /** 部门审核信息-部门审核结果 值域{"","同意","不同意"} */
    @ApiModelProperty("部门审核信息-部门审核结果 值域{,同意,不同意}")
    private String sysDeptExamResult;

    /** 部门审核信息-部门审核说明 */
    @ApiModelProperty("部门审核信息-部门审核说明")
    private String sysDeptExamInstructe;

    /** 部门审核信息-部门审核时间 */
    @ApiModelProperty("部门审核信息-部门审核时间")
    private Date sysDeptExamDate;

    /** 部门审核信息-部门审核人姓名 */
    @ApiModelProperty("部门审核信息-部门审核人姓名")
    private String sysDeptExamPerName;

    /** 部门审核信息-部门审核人编码 */
    @ApiModelProperty("部门审核信息-部门审核人编码")
    private String sysDeptExamPerCode;

    /** 资质审核信息-资质审核结果 值域{"","同意","不同意"} */
    @ApiModelProperty("资质审核信息-资质审核结果 值域{,同意,不同意}")
    private String sysQualExamResult;

    /** 资质审核信息-资质审核说明 */
    @ApiModelProperty("资质审核信息-资质审核说明")
    private String sysQualExamInstructe;

    /** 资质审核信息-资质审核时间 */
    @ApiModelProperty("资质审核信息-资质审核时间")
    private Date sysQualExamDate;

    /** 资质审核信息-资质审核人姓名 */
    @ApiModelProperty("资质审核信息-资质审核人姓名")
    private String sysQualExamPerName;

    /** 资质审核信息-资质审核人编码 */
    @ApiModelProperty("资质审核信息-资质审核人编码")
    private String sysQualExamPerCode;

    /** 审批信息-审批结果 值域{"","同意","不同意"} */
    @ApiModelProperty("审批信息-审批结果 值域{,同意,不同意}")
    private String sysApprResult;

    /** 审批信息-审批说明 */
    @ApiModelProperty("审批信息-审批说明")
    private String sysApprRemarks;

    /** 审批信息-审批时间 */
    @ApiModelProperty("审批信息-审批时间")
    private Date sysApprDate;

    /** 审批信息-审批人姓名 */
    @ApiModelProperty("审批信息-审批人姓名")
    private String sysApprPerName;

    /** 审批信息-审批人编码 */
    @ApiModelProperty("审批信息-审批人编码")
    private String sysApprPerCode;

    @ApiModelProperty("财务信息-税率补偿点数")
    private String taxRateConew;

    private List<SupplierAccountInfo> supplierAccountInfos;

    private List<SupplierContactInfo> supplierContactInfos;

    private List<String> sysSupCodeList;

    private List<String> sysSupNameList;

    private List<String> sysSupAbbList;

    private List<String> sysAdmitTypeList;

    private List<String> sysSupLevelList;

    private List<String> sysOperateCateList;
}
