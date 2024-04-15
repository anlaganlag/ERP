package com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierAccountInfo;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.entity.SupplierContactInfo;
import com.tadpole.cloud.supplyChain.modular.PurchaseSupplier.service.ISupplierAccountInfoService;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 供应商-供应商信息 出参类
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
@ExcelIgnoreUnannotated
@TableName("SUPPLIER_INFO")
public class SupplierInfoResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private long id;

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


    @ApiModelProperty("系统信息-有效状态 值域{失效,生效}")
    private String sysValidStatus;


    @ApiModelProperty("系统信息-审核状态 值域{待提交,待管理审核,待资质审核,资质不通过,待审批,通过,归档}")
    private String sysAuditStatus;


    @ApiModelProperty("基本信息-供应商编码")
    private String sysSupCode;


    @ApiModelProperty("基本信息-供应商名称")
    private String sysSupName;


    @ApiModelProperty("基本信息-供应商简称")
    private String sysSupAbb;


    @ApiModelProperty("基本信息-准入类型 值域{购样,开模,成品}")
    private String sysAdmitType;


    @ApiModelProperty("基本信息-供应商等级 值域{临时供应商,一般供应商,重点供应商,核心供应商,淘汰供应商}")
    private String sysSupLevel;


    @ApiModelProperty("基本信息-省份")
    private String sysProvince;


    @ApiModelProperty("基本信息-市区")
    private String sysUrbanArea;


    @ApiModelProperty("基本信息-通讯地址")
    private String sysMailAddress;


    @ApiModelProperty("基本信息-行业")
    private String sysIndustry;


    @ApiModelProperty("基本信息-运营大类")
    private String sysOperateCate;


    @ApiModelProperty("基本信息-主营产品")
    private String sysMainProduct;


    @ApiModelProperty("基本信息-月产能")
    private String sysMonProductCapa;


    @ApiModelProperty("基本信息-信用代码")
    private String sysCreditCode;


    @ApiModelProperty("基本信息-注册地址")
    private String sysRegistAddress;


    @ApiModelProperty("基本信息-营业执照")
    private String sysBusinessLicense;


    @ApiModelProperty("基本信息-供应商承诺书")
    private String sysSupCommitLetter;


    @ApiModelProperty("基本信息-工厂图片")
    private String sysFactoryPic;


    @ApiModelProperty("基本信息-合作伙伴信息调查表")
    private String sysPatnerInfoQueste;


    @ApiModelProperty("基本信息-补充协议")
    private String sysSuppleAgreement;


    @ApiModelProperty("基本信息-框架协议")
    private String sysFwAgreement;


    @ApiModelProperty("基本信息-质量协议")
    private String sysQualityAgreement;


    @ApiModelProperty("基本信息-专利证明")
    private String sysPatentCertficate;


    @ApiModelProperty("基本信息-纳税等级")
    private String sysTaxLevel;


    @ApiModelProperty("基本信息-其他附件")
    private String sysOtherAttachment;


    @ApiModelProperty("财务信息-开票公司类型 值域{贸易公司,生产加工,...}")
    private String sysInvoCompType;


    @ApiModelProperty("财务信息-结算币别 值域{人民币,...}")
    private String sysSettlementCurrency;


    @ApiModelProperty("财务信息-结算方式 值域{电汇,支付宝,...}")
    private String sysSettlementMethod;


    @ApiModelProperty("财务信息-付款条件 值域{货到明细付款,半月结,月结,月结60天,每周结,预付30%，货到后70%,100%预付款,货好后100%预付款,网,预付定金15%，尾款85%月结,预付先20% 后80%,	预付30%，再预付60%，月结10%,预付30%,再预付70%,预付30%，月结70%,预付50%，尾款50%月结}")
    private String sysPaymentTerms;


    @ApiModelProperty("财务信息-税分类 值域{小规模纳税人,一般纳税人,个体工商户,...}")
    private String sysTaxClassify;


    @ApiModelProperty("财务信息-发票类型 值域{增值税专用发票,普通发票,无,...}")
    private String sysInvoiceType;


    @ApiModelProperty("财务信息-默认税率 值域{零税率,1%增值税,1%普通发票,3%增值税,13%增值税,3%普通发票,13%普通发票,无,...}")
    private String sysDefaultTaxRate;


    @ApiModelProperty("财务信息-开票资料")
    private String sysInvoiceInfo;


    @ApiModelProperty("财务信息-供应链公司")
    private String sysSupChainComp;


    @ApiModelProperty("管理信息-供应商分组")
    private String sysSupGroup;


    @ApiModelProperty("管理信息-负责部门名称")
    private String sysPurChargeDeptName;


    @ApiModelProperty("管理信息-负责部门编码")
    private String sysPurChargeDeptCode;


    @ApiModelProperty("管理信息-采购负责人员工姓名")
    private String sysPurChargePerName;


    @ApiModelProperty("管理信息-采购负责人员工编码")
    private String sysPurChargePerCode;


    @ApiModelProperty("管理信息-物控专员员工姓名")
    private String sysMcsPerName;


    @ApiModelProperty("管理信息-物控专员员工编码")
    private String sysMcsPerCode;


    @ApiModelProperty("管理信息-备注")
    private String sysRemarks;


    @ApiModelProperty("推荐信息-是否公司员工推荐 值域{是,否}")
    private String sysIsSugBycompStaf;


    @ApiModelProperty("推荐信息-推荐人姓名")
    private String sysSugPerName;


    @ApiModelProperty("推荐信息-与推荐人关系")
    private String sysRelateToSug;


    @ApiModelProperty("推荐信息-是否需要补充资料 值域{是,否}")
    private String sysIsRecomInfo;


    @ApiModelProperty("推荐信息-资料附件")
    private String sysDataAttachment;


    @ApiModelProperty("部门审核信息-部门审核结果 值域{,同意,不同意}")
    private String sysDeptExamResult;


    @ApiModelProperty("部门审核信息-部门审核说明")
    private String sysDeptExamInstructe;


    @ApiModelProperty("部门审核信息-部门审核时间")
    private Date sysDeptExamDate;


    @ApiModelProperty("部门审核信息-部门审核人姓名")
    private String sysDeptExamPerName;


    @ApiModelProperty("部门审核信息-部门审核人编码")
    private String sysDeptExamPerCode;


    @ApiModelProperty("资质审核信息-资质审核结果 值域{,同意,不同意}")
    private String sysQualExamResult;


    @ApiModelProperty("资质审核信息-资质审核说明")
    private String sysQualExamInstructe;


    @ApiModelProperty("资质审核信息-资质审核时间")
    private Date sysQualExamDate;


    @ApiModelProperty("资质审核信息-资质审核人姓名")
    private String sysQualExamPerName;


    @ApiModelProperty("资质审核信息-资质审核人编码")
    private String sysQualExamPerCode;


    @ApiModelProperty("审批信息-审批结果 值域{,同意,不同意}")
    private String sysApprResult;


    @ApiModelProperty("审批信息-审批说明")
    private String sysApprRemarks;


    @ApiModelProperty("审批信息-审批时间")
    private Date sysApprDate;


    @ApiModelProperty("审批信息-审批人姓名")
    private String sysApprPerName;


    @ApiModelProperty("审批信息-审批人编码")
    private String sysApprPerCode;

    /** 金蝶erp供应商id */
    @ApiModelProperty("金蝶id")
    private String supplierId;

    @ApiModelProperty("同步金蝶状态")
    private String syncStatus;

    @ApiModelProperty("同步金蝶")
    private String syncResultMsg;

    @ApiModelProperty("财务信息-税率补偿点数")
    private String taxRateConew;

    private List<SupplierAccountInfo> supplierAccountInfos;

    private List<SupplierContactInfo> supplierContactInfos;


}
