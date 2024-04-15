package com.tadpole.cloud.product.api.productproposal.model.result;

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
 * 提案-开模费付款申请 出参类
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
@ExcelIgnoreUnannotated
@TableName("RD_MOLD_OPEN_PFA")
public class RdMoldOpenPfaResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @TableId(value = "SYS_MOF_CODE", type = IdType.ASSIGN_ID)
    private String sysMofCode;


    @ApiModelProperty("系统信息-创建时间")
    private Date sysCDate;


    @ApiModelProperty("系统信息-最后更新时间")
    private Date sysLDate;


    @ApiModelProperty("系统信息-部门编号")
    private String sysDeptCode;


    @ApiModelProperty("系统信息-部门名称")
    private String sysDeptName;


    @ApiModelProperty("系统信息-员工编号")
    private String sysPerCode;


    @ApiModelProperty("系统信息-员工姓名")
    private String sysPerName;


    @ApiModelProperty("系统信息-申请状态 值域{'待提交','待审核','待打印','待付款','待上传票据','待复核','已归档'}")
    private String sysMofStatus;


    @ApiModelProperty("单据联系-产品线编号")
    private String sysPlCode;


    @ApiModelProperty("单据联系-SPU")
    private String sysSpu;


    @ApiModelProperty("单据联系-提案编号")
    private String sysTaCode;


    @ApiModelProperty("单据联系-拿样任务编号")
    private String sysTsTaskCode;


    @ApiModelProperty("单据联系-定制反馈编号")
    private String sysCustFebkCode;


    @ApiModelProperty("系统信息-费用申请编号")
    private String sysFeeAppCode;


    @ApiModelProperty("单据联系-开模付款申请编号(首款)")
    private String sysFeeAppCodeFirst;


    @ApiModelProperty("付款申请信息-产品经理编号")
    private String sysPmPerCode;


    @ApiModelProperty("付款申请信息-产品经理姓名")
    private String sysPmPerName;


    @ApiModelProperty("付款申请信息-供应商编码")
    private String sysCfSupplierNum;


    @ApiModelProperty("付款申请信息-供应商名称")
    private String sysCfSupplierName;


    @ApiModelProperty("付款申请信息-采购负责人编号")
    private String sysCfPurPerCode;


    @ApiModelProperty("付款申请信息-采购负责人姓名")
    private String sysCfPurPerName;


    @ApiModelProperty("付款申请信息-提供票据类型")
    private String sysCfTicketType;


    @ApiModelProperty("付款申请信息-供应商-账户类型")
    private String sysCfAccountType;


    @ApiModelProperty("付款申请信息-供应商-收款方式")
    private String sysCfPayMethod;


    @ApiModelProperty("付款申请信息-供应商-账号户名")
    private String sysCfAccountName;


    @ApiModelProperty("付款申请信息-供应商-银行账号")
    private String sysCfBankAccount;


    @ApiModelProperty("付款申请信息-供应商-开户行")
    private String sysCfBankName;


    @ApiModelProperty("付款申请信息-供应商-支付宝账号")
    private String sysCfAlipayAccount;


    @ApiModelProperty("付款申请信息-合同金额")
    private BigDecimal sysSaContractAmount;


    @ApiModelProperty("付款申请信息-盖章合同文件")
    private String sysSaStaContractDoc;


    @ApiModelProperty("付款申请信息-付款申请-标题 ='CW28-付款单-【采购负责人】-【年】-【月】-【日】'")
    private String sysMofTitle;


    @ApiModelProperty("付款申请信息-付款申请-摘要 ='【提案编号】>【产品名称】 开模费用-【付款金额】 【付款方式】'")
    private String sysMofSummary;


    @ApiModelProperty("付款申请信息-提交时间")
    private Date sysMofSubDate;


    @ApiModelProperty("付款申请信息-提交人编号")
    private String sysMofSubPc;


    @ApiModelProperty("付款申请信息-提交人姓名")
    private String sysMofSubPn;


    @ApiModelProperty("付款信息-付款账号类型")
    private String sysMofPayAt;


    @ApiModelProperty("付款信息-付款账户")
    private String sysMofPayAccount;


    @ApiModelProperty("付款信息-付款账户户名")
    private String sysMofPayAn;


    @ApiModelProperty("付款信息-付款账户开户行")
    private String sysMofPayAob;


    @ApiModelProperty("审核信息-审核时间")
    private Date sysMofAuditDate;


    @ApiModelProperty("审核信息-审核结果")
    private String sysMofAuditResult;


    @ApiModelProperty("审核信息-审核说明")
    private String sysMofAuditExplain;


    @ApiModelProperty("审核信息-审核员工姓名")
    private String sysMofAuditPerName;


    @ApiModelProperty("审核信息-审核员工编号")
    private String sysMofAuditPerCode;


    @ApiModelProperty("打印信息-打印时间")
    private Date sysMofPrintDate;


    @ApiModelProperty("打印信息-打印次数")
    private BigDecimal sysMofPrintCount;


    @ApiModelProperty("打印信息-打印员工姓名")
    private String sysMofPrintPerName;


    @ApiModelProperty("打印信息-打印员工编号")
    private String sysMofPrintPerCode;


    @ApiModelProperty("付款信息-付款日期")
    private Date sysMofPayDate;


    @ApiModelProperty("付款申请信息-付款申请-付款方式")
    private String sysMofPayMethod;


    @ApiModelProperty("付款申请信息-付款申请-实际付款金额")
    private BigDecimal sysMofPayAmount;


    @ApiModelProperty("付款申请信息-付款申请-付款说明")
    private String sysMofPayExplain;


    @ApiModelProperty("付款申请信息-付款申请-付款公司")
    private String sysMofPayComp;


    @ApiModelProperty("付款信息-付款账号性质")
    private String sysMofPayAp;


    @ApiModelProperty("付款信息-付款登记时间")
    private Date sysMofPayRd;


    @ApiModelProperty("付款信息-付款登记员工姓名")
    private String sysMofPayRPerName;


    @ApiModelProperty("付款信息-付款登记员工编号")
    private String sysMofPayRPerCode;


    @ApiModelProperty("票据信息-发票号码")
    private String sysMofInvoiceNum;


    @ApiModelProperty("票据信息-票据文件")
    private String sysMofTicketFile;


    @ApiModelProperty("票据信息-票据上传时间")
    private Date sysMofTicketUd;


    @ApiModelProperty("票据信息-票据上传员工姓名")
    private String sysMofTicketUpn;


    @ApiModelProperty("票据信息-票据上传员工编号")
    private String sysMofTicketUpc;


    @ApiModelProperty("复核信息-复核时间")
    private Date sysMofReviewDate;


    @ApiModelProperty("复核信息-复核结果")
    private String sysMofReviewResult;


    @ApiModelProperty("复核信息-复核说明")
    private String sysMofReviewExplain;


    @ApiModelProperty("复核信息-复核员工姓名")
    private String sysMofReviewPerName;


    @ApiModelProperty("复核信息-复核员工编号")
    private String sysMofReviewPerCode;


    @ApiModelProperty("归档信息-归档时间")
    private Date sysMofArchiveDate;

    @ApiModelProperty("产品线名称")
    private String sysPlName;

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

    /** 同款设定-当前迭代版本 */
    @ApiModelProperty("同款设定-当前迭代版本")
    private String sysCurIteVersion;

    @ApiModelProperty("合同信息-合同文件(协议)")
    private String sysSaContractDoc;

    @ApiModelProperty("定制反馈信息-是否可退款 值域{'是','否'}")
    private String sysCfIsRefund;

    @ApiModelProperty("凭证信息-打款日期")
    private Date sysRefPayDate;

    @ApiModelProperty("付款申请信息-首款提交时间")
    private Date sysMofFirstSubDate;

}
