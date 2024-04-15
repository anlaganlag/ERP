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
 * 提案-研发费报销 入参类
 * </p>
 *
 * @author S20190096
 * @since 2024-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("RD_EXPENSE_REIMBURSE")
public class RdExpenseReimburseParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统信息-报销申请编号 */
   @TableId(value = "SYS_SAEA_CODE", type = IdType.AUTO)
    private String sysSaeaCode;

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

    /** 系统信息-报销申请状态 值域{"待提交","待审核","待打样","待付款","已归档"} */
    @ApiModelProperty("系统信息-报销申请状态 值域{'待提交','待审核','待打样','待付款','已归档'}")
    private String sysSaeaStatus;

    /** 申请信息-支付账户 */
    @ApiModelProperty("申请信息-支付账户")
    private String sysSaeaAccount;

    /** 申请信息-实时金额 */
    @ApiModelProperty("申请信息-实时金额")
    private BigDecimal sysSaeaRtAmount;

    /** 申请信息-报销范围(开始) */
    @ApiModelProperty("申请信息-报销范围(开始)")
    private Date sysSaeaStartDate;

    /** 申请信息-报销范围(结束) */
    @ApiModelProperty("申请信息-报销范围(结束)")
    private Date sysSaeaEndDate;

    /** 申请信息-报销单标题 */
    @ApiModelProperty("申请信息-报销单标题")
    private String sysSaeaTitle;

    /** 申请信息-报销单摘要 */
    @ApiModelProperty("申请信息-报销单摘要")
    private String sysSaeaSummary;

    /** 申请信息-购样费用合计 */
    @ApiModelProperty("申请信息-购样费用合计")
    private BigDecimal sysSaeaBuyAmount;

    /** 申请信息-打样费用合计 */
    @ApiModelProperty("申请信息-打样费用合计")
    private BigDecimal sysSaeaSampleAmount;

    /** 申请信息-样品退货数量 */
    @ApiModelProperty("申请信息-样品退货数量")
    private BigDecimal sysSaeaSampleRefQty;

    /** 申请信息-样品退货退款合计 */
    @ApiModelProperty("申请信息-样品退货退款合计")
    private BigDecimal sysSaeaRefAmount;

    /** 申请信息-报销金额 */
    @ApiModelProperty("申请信息-报销金额")
    private BigDecimal sysSaeaAmount;

    /** 申请信息-支付凭证上传 */
    @ApiModelProperty("申请信息-支付凭证上传")
    private String sysSaeaIncAndExpVoucher;

    /** 申请信息-账户余额截图 */
    @ApiModelProperty("申请信息-账户余额截图")
    private String sysSaeaAccBalance;

    /** 申请信息-收款账号 */
    @ApiModelProperty("申请信息-收款账号")
    private String sysSaeaRecAccount;

    /** 申请信息-收款人 */
    @ApiModelProperty("申请信息-收款人")
    private String sysSaeaRecPn;

    /** 申请信息-收款开户行 */
    @ApiModelProperty("申请信息-收款开户行")
    private String sysSaeaRecAn;

    /** 申请信息-报销员工姓名 */
    @ApiModelProperty("申请信息-报销员工姓名")
    private String sysSaeaPerName;

    /** 申请信息-报销员工编号 */
    @ApiModelProperty("申请信息-报销员工编号")
    private String sysSaeaPerCode;

    /** 申请信息-报销申请时间 */
    @ApiModelProperty("申请信息-报销申请时间")
    private Date sysSaeaAppDate;

    /** 审核信息-审核结果 值域{"通过","退回"} */
    @ApiModelProperty("审核信息-审核结果 值域{'通过','退回'}")
    private String sysSaeaAuditResult;

    /** 审核信息-退回说明 */
    @ApiModelProperty("审核信息-退回说明")
    private String sysSaeaAuditExplain;

    /** 审核信息-审核时间 */
    @ApiModelProperty("审核信息-审核时间")
    private Date sysSaeaAuditDate;

    /** 审核信息-审核员工姓名 */
    @ApiModelProperty("审核信息-审核员工姓名")
    private String sysSaeaAuditPn;

    /** 审核信息-审核员工编号 */
    @ApiModelProperty("审核信息-审核员工编号")
    private String sysSaeaAuditPc;

    /** 打印信息-首次打印时间 */
    @ApiModelProperty("打印信息-首次打印时间")
    private Date sysSaeaPrintDate;

    /** 打印信息-首次打印员工姓名 */
    @ApiModelProperty("打印信息-首次打印员工姓名")
    private String sysSaeaPrintPn;

    /** 打印信息-首次打印员工编号 */
    @ApiModelProperty("打印信息-首次打印员工编号")
    private String sysSaeaPrintPc;

    /** 打印信息-打印次数 */
    @ApiModelProperty("打印信息-打印次数")
    private BigDecimal sysSaeaPrintQty;

    /** 付款信息-实际付款日期 */
    @ApiModelProperty("付款信息-实际付款日期")
    private Date sysSaeaPayDate;

    /** 付款信息-实际付款金额 */
    @ApiModelProperty("付款信息-实际付款金额")
    private BigDecimal sysSaeaPayAmount;

    /** 付款信息-报销费用差额说明 */
    @ApiModelProperty("付款信息-报销费用差额说明")
    private String sysSaeaBalanceExplain;

    /** 付款信息-付款公司 */
    @ApiModelProperty("付款信息-付款公司")
    private String sysSaeaPayComp;

    /** 付款信息-付款账户性质 默认值{"银行卡"} */
    @ApiModelProperty("付款信息-付款账户性质 默认值{'银行卡'}")
    private String sysSaeaPayAp;

    /** 付款信息-付款账户归属 值域{"公司账户","个人账户"} */
    @ApiModelProperty("付款信息-付款账户归属 值域{'公司账户','个人账户'}")
    private String sysSaeaPayAo;

    /** 付款信息-付款账户类型 */
    @ApiModelProperty("付款信息-付款账户类型")
    private String sysSaeaPayAt;

    /** 付款信息-付款账户 */
    @ApiModelProperty("付款信息-付款账户")
    private String sysSaeaPayAccount;

    /** 付款信息-付款账户户名 */
    @ApiModelProperty("付款信息-付款账户户名")
    private String sysSaeaPayAn;

    /** 付款信息-付款开户行 */
    @ApiModelProperty("付款信息-付款开户行")
    private String sysSaeaPayAob;

    /** 付款信息-付款登记时间 */
    @ApiModelProperty("付款信息-付款登记时间")
    private Date sysSaeaPayRd;

    /** 付款信息-付款登记员工姓名 */
    @ApiModelProperty("付款信息-付款登记员工姓名")
    private String sysSaeaPayRpn;

    /** 付款信息-付款登记员工编号 */
    @ApiModelProperty("付款信息-付款登记员工编号")
    private String sysSaeaPayRpc;

    /** 归档信息-归档时间 */
    @ApiModelProperty("归档信息-归档时间")
    private Date sysSaeaArchiveDate;

    /** 付款信息-实际付款日期集合 */
    @ApiModelProperty("付款信息-实际付款日期集合")
    private List<String> sysSaeaPayDateList;

    /** 系统信息-报销申请编号集合 */
    @ApiModelProperty("系统信息-报销申请编号集合")
    private List<String> sysSaeaCodeList;

    /** 申请信息-报销员工姓名集合 */
    @ApiModelProperty("申请信息-报销员工姓名集合")
    private List<String> sysSaeaPerNameList;

    /** 申请信息-支付账户集合 */
    @ApiModelProperty("申请信息-支付账户集合")
    private List<String> sysSaeaAccountList;

    /** 申请信息-报销金额集合 */
    @ApiModelProperty("申请信息-报销金额集合")
    private List<BigDecimal> sysSaeaAmountList;

    /** 申请信息-报销明细集合 */
    @ApiModelProperty("申请信息-报销明细集合")
    private List<RdExpenseReimburseDetParam> rdExpenseReimburseDetParams;

    /** 申请信息-报销范围 */
    @ApiModelProperty("申请信息-报销范围")
    private List<String> sysSaeaDateList;

    /** 系统信息-报销申请状态集合 值域{"待提交","待审核","待打样","待付款","已归档"} */
    @ApiModelProperty("系统信息-报销申请状态集合 值域{'待提交','待审核','待打样','待付款','已归档'}")
    private List<String> sysSaeaStatusList;

}
