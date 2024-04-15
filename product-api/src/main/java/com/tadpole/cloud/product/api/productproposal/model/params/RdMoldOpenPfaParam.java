package com.tadpole.cloud.product.api.productproposal.model.params;

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
 * 提案-开模费付款申请 入参类
 * </p>
 *
 * @author S20190096
 * @since 2023-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_MOLD_OPEN_PFA")
public class RdMoldOpenPfaParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统信息-开模付款申请编号 */
   @TableId(value = "SYS_MOF_CODE", type = IdType.ASSIGN_ID)
    private String sysMofCode;

    /** 系统信息-创建时间 */
    @ApiModelProperty("系统信息-创建时间")
    private Date sysCDate;

    /** 系统信息-最后更新时间 */
    @ApiModelProperty("系统信息-最后更新时间")
    private Date sysLDate;

    /** 系统信息-部门编号 */
    @ApiModelProperty("系统信息-部门编号")
    private String sysDeptCode;

    /** 系统信息-部门名称 */
    @ApiModelProperty("系统信息-部门名称")
    private String sysDeptName;

    /** 系统信息-员工编号 */
    @ApiModelProperty("系统信息-员工编号")
    private String sysPerCode;

    /** 系统信息-员工姓名 */
    @ApiModelProperty("系统信息-员工姓名")
    private String sysPerName;

    /** 系统信息-申请状态 值域{"待提交","待审核","待打印","待付款","待上传票据","待复核","已归档"} */
    @ApiModelProperty("系统信息-申请状态 值域{'待提交','待审核','待打印','待付款','待上传票据','待复核','已归档'}")
    private String sysMofStatus;

    /** 单据联系-产品线编号 */
    @ApiModelProperty("单据联系-产品线编号")
    private String sysPlCode;

    /** 单据联系-SPU */
    @ApiModelProperty("单据联系-SPU")
    private String sysSpu;

    /** 单据联系-提案编号 */
    @ApiModelProperty("单据联系-提案编号")
    private String sysTaCode;

    /** 单据联系-拿样任务编号 */
    @ApiModelProperty("单据联系-拿样任务编号")
    private String sysTsTaskCode;

    /** 单据联系-定制反馈编号 */
    @ApiModelProperty("单据联系-定制反馈编号")
    private String sysCustFebkCode;

    /** 系统信息-费用申请编号 */
    @ApiModelProperty("系统信息-费用申请编号")
    private String sysFeeAppCode;

    /** 单据联系-开模付款申请编号(首款) */
    @ApiModelProperty("单据联系-开模付款申请编号(首款)")
    private String sysFeeAppCodeFirst;

    /** 付款申请信息-产品经理编号 */
    @ApiModelProperty("付款申请信息-产品经理编号")
    private String sysPmPerCode;

    /** 付款申请信息-产品经理姓名 */
    @ApiModelProperty("付款申请信息-产品经理姓名")
    private String sysPmPerName;

    /** 付款申请信息-供应商编码 */
    @ApiModelProperty("付款申请信息-供应商编码")
    private String sysCfSupplierNum;

    /** 付款申请信息-供应商名称 */
    @ApiModelProperty("付款申请信息-供应商名称")
    private String sysCfSupplierName;

    /** 付款申请信息-采购负责人编号 */
    @ApiModelProperty("付款申请信息-采购负责人编号")
    private String sysCfPurPerCode;

    /** 付款申请信息-采购负责人姓名 */
    @ApiModelProperty("付款申请信息-采购负责人姓名")
    private String sysCfPurPerName;

    /** 付款申请信息-提供票据类型 */
    @ApiModelProperty("付款申请信息-提供票据类型")
    private String sysCfTicketType;

    /** 付款申请信息-供应商-账户类型 */
    @ApiModelProperty("付款申请信息-供应商-账户类型")
    private String sysCfAccountType;

    /** 付款申请信息-供应商-收款方式 */
    @ApiModelProperty("付款申请信息-供应商-收款方式")
    private String sysCfPayMethod;

    /** 付款申请信息-供应商-账号户名 */
    @ApiModelProperty("付款申请信息-供应商-账号户名")
    private String sysCfAccountName;

    /** 付款申请信息-供应商-银行账号 */
    @ApiModelProperty("付款申请信息-供应商-银行账号")
    private String sysCfBankAccount;

    /** 付款申请信息-供应商-开户行 */
    @ApiModelProperty("付款申请信息-供应商-开户行")
    private String sysCfBankName;

    /** 付款申请信息-供应商-支付宝账号 */
    @ApiModelProperty("付款申请信息-供应商-支付宝账号")
    private String sysCfAlipayAccount;

    /** 付款申请信息-合同金额 */
    @ApiModelProperty("付款申请信息-合同金额")
    private BigDecimal sysSaContractAmount;

    /** 付款申请信息-盖章合同文件 */
    @ApiModelProperty("付款申请信息-盖章合同文件")
    private String sysSaStaContractDoc;

    /** 付款申请信息-付款申请-标题 ="CW28-付款单-【采购负责人】-【年】-【月】-【日】" */
    @ApiModelProperty("付款申请信息-付款申请-标题 ='CW28-付款单-【采购负责人】-【年】-【月】-【日】'")
    private String sysMofTitle;

    /** 付款申请信息-付款申请-摘要 ="【提案编号】>【产品名称】 开模费用-【付款金额】 【付款方式】" */
    @ApiModelProperty("付款申请信息-付款申请-摘要 ='【提案编号】>【产品名称】 开模费用-【付款金额】 【付款方式】'")
    private String sysMofSummary;

    /** 付款申请信息-提交时间 */
    @ApiModelProperty("付款申请信息-提交时间")
    private Date sysMofSubDate;

    /** 付款申请信息-提交人编号 */
    @ApiModelProperty("付款申请信息-提交人编号")
    private String sysMofSubPc;

    /** 付款申请信息-提交人姓名 */
    @ApiModelProperty("付款申请信息-提交人姓名")
    private String sysMofSubPn;

    /** 付款信息-付款账号类型 */
    @ApiModelProperty("付款信息-付款账号类型")
    private String sysMofPayAt;

    /** 付款信息-付款账户 */
    @ApiModelProperty("付款信息-付款账户")
    private String sysMofPayAccount;

    /** 付款信息-付款账户户名 */
    @ApiModelProperty("付款信息-付款账户户名")
    private String sysMofPayAn;

    /** 付款信息-付款账户开户行 */
    @ApiModelProperty("付款信息-付款账户开户行")
    private String sysMofPayAob;

    /** 审核信息-审核时间 */
    @ApiModelProperty("审核信息-审核时间")
    private Date sysMofAuditDate;

    /** 审核信息-审核结果 */
    @ApiModelProperty("审核信息-审核结果")
    private String sysMofAuditResult;

    /** 审核信息-审核说明 */
    @ApiModelProperty("审核信息-审核说明")
    private String sysMofAuditExplain;

    /** 审核信息-审核员工姓名 */
    @ApiModelProperty("审核信息-审核员工姓名")
    private String sysMofAuditPerName;

    /** 审核信息-审核员工编号 */
    @ApiModelProperty("审核信息-审核员工编号")
    private String sysMofAuditPerCode;

    /** 打印信息-打印时间 */
    @ApiModelProperty("打印信息-打印时间")
    private Date sysMofPrintDate;

    /** 打印信息-打印次数 */
    @ApiModelProperty("打印信息-打印次数")
    private BigDecimal sysMofPrintCount;

    /** 打印信息-打印员工姓名 */
    @ApiModelProperty("打印信息-打印员工姓名")
    private String sysMofPrintPerName;

    /** 打印信息-打印员工编号 */
    @ApiModelProperty("打印信息-打印员工编号")
    private String sysMofPrintPerCode;

    /** 付款信息-付款日期 */
    @ApiModelProperty("付款信息-付款日期")
    private Date sysMofPayDate;

    /** 付款申请信息-付款申请-付款方式 值域{"全款","首款","尾款"} */
    @ApiModelProperty("付款申请信息-付款申请-付款方式")
    private String sysMofPayMethod;

    /** 付款申请信息-付款申请-实际付款金额 */
    @ApiModelProperty("付款申请信息-付款申请-实际付款金额")
    private BigDecimal sysMofPayAmount;

    /** 付款申请信息-付款申请-付款说明 */
    @ApiModelProperty("付款申请信息-付款申请-付款说明")
    private String sysMofPayExplain;

    /** 付款申请信息-付款申请-付款公司 */
    @ApiModelProperty("付款申请信息-付款申请-付款公司")
    private String sysMofPayComp;

    /** 付款信息-付款账号性质 */
    @ApiModelProperty("付款信息-付款账号性质")
    private String sysMofPayAp;

    /** 付款信息-付款登记时间 */
    @ApiModelProperty("付款信息-付款登记时间")
    private Date sysMofPayRd;

    /** 付款信息-付款登记员工姓名 */
    @ApiModelProperty("付款信息-付款登记员工姓名")
    private String sysMofPayRPerName;

    /** 付款信息-付款登记员工编号 */
    @ApiModelProperty("付款信息-付款登记员工编号")
    private String sysMofPayRPerCode;

    /** 票据信息-发票号码 */
    @ApiModelProperty("票据信息-发票号码")
    private String sysMofInvoiceNum;

    /** 票据信息-票据文件 */
    @ApiModelProperty("票据信息-票据文件")
    private String sysMofTicketFile;

    /** 票据信息-票据上传时间 */
    @ApiModelProperty("票据信息-票据上传时间")
    private Date sysMofTicketUd;

    /** 票据信息-票据上传员工姓名 */
    @ApiModelProperty("票据信息-票据上传员工姓名")
    private String sysMofTicketUpn;

    /** 票据信息-票据上传员工编号 */
    @ApiModelProperty("票据信息-票据上传员工编号")
    private String sysMofTicketUpc;

    /** 复核信息-复核时间 */
    @ApiModelProperty("复核信息-复核时间")
    private Date sysMofReviewDate;

    /** 复核信息-复核结果 */
    @ApiModelProperty("复核信息-复核结果")
    private String sysMofReviewResult;

    /** 复核信息-复核说明 */
    @ApiModelProperty("复核信息-复核说明")
    private String sysMofReviewExplain;

    /** 复核信息-复核员工姓名 */
    @ApiModelProperty("复核信息-复核员工姓名")
    private String sysMofReviewPerName;

    /** 复核信息-复核员工编号 */
    @ApiModelProperty("复核信息-复核员工编号")
    private String sysMofReviewPerCode;

    /** 归档信息-归档时间 */
    @ApiModelProperty("归档信息-归档时间")
    private Date sysMofArchiveDate;

    /** 功能操作-保存/发布/删除 */
    @ApiModelProperty("功能操作-保存/提交")
    private String sysFuncOpr;

    /** 界面操作-新增/编辑 */
    @ApiModelProperty("界面操作-新增/编辑")
    private String sysPageOpr;

    /** 功能操作-创建申请/直接申请 */
    @ApiModelProperty("功能操作-创建申请/直接申请")
    private String sysAppOpr;

    /** 付款信息-付款日期集合 */
    @ApiModelProperty("付款信息-付款日期集合")
    private List<String> sysMofPayDateList;

    /** 系统信息-开模付款申请编号集合 */
    @ApiModelProperty("系统信息-开模付款申请编号集合")
    private List<String> sysMofCodeList;

    /** 付款申请信息-产品经理编号 */
    @ApiModelProperty("付款申请信息-产品经理编号")
    private List<String> sysPmPerCodeList;

    /** 付款申请信息-产品经理姓名 */
    @ApiModelProperty("付款申请信息-产品经理姓名")
    private List<String> sysPmPerNameList;

    /** 付款申请信息-采购负责人编号 */
    @ApiModelProperty("付款申请信息-采购负责人编号")
    private List<String> sysCfPurPerCodeList;

    /** 付款申请信息-采购负责人姓名 */
    @ApiModelProperty("付款申请信息-采购负责人姓名")
    private List<String> sysCfPurPerNameList;

    /** 系统信息-申请状态集合 值域{"待提交","待审核","待打印","待付款","待上传票据","待复核","已归档"} */
    @ApiModelProperty("系统信息-申请状态集合 值域{'待提交','待审核','待打印','待付款','待上传票据','待复核','已归档'}")
    private List<String> sysMofStatusList;
}
