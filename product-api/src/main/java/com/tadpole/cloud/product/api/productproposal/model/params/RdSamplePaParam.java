package com.tadpole.cloud.product.api.productproposal.model.params;

import io.swagger.annotations.ApiModelProperty;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;

/**
 * <p>
 * 提案-购样申请 入参类
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
@TableName("RD_SAMPLE_PA")
public class RdSamplePaParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统信息-费用申请编号 */
   @TableId(value = "SYS_FEE_APP_CODE", type = IdType.ASSIGN_ID)
    private String sysFeeAppCode;

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

    /** 系统信息-申请状态 值域{"待提交","待审核","待审批","待上传","待支付","已支付","已撤销","已归档"} */
    @ApiModelProperty("系统信息-申请状态 值域{'待提交','待审核','待审批','待上传','待支付','已支付','已撤销','已归档'}")
    private String sysFeeAppStatus;

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

    /** 申请信息-拿样渠道 值域{"供应商","1688网站","淘宝网站"} */
    @ApiModelProperty("申请信息-拿样渠道 值域{'供应商','1688网站','淘宝网站'}")
    private String sysFeeAppSc;

    /** 申请信息-店铺名称 */
    @ApiModelProperty("申请信息-店铺名称")
    private String sysFeeAppShopName;

    /** 申请信息-样品名称 */
    @ApiModelProperty("申请信息-样品名称")
    private String sysFeeAppProName;

    /** 申请信息-商品购买页面 */
    @ApiModelProperty("申请信息-商品购买页面")
    private String sysFeeAppProPurPage;

    /** 申请信息-样品单价 */
    @ApiModelProperty("申请信息-样品单价")
    private BigDecimal sysFeeAppSup;

    /** 申请信息-样品采购数量 */
    @ApiModelProperty("申请信息-样品采购数量")
    private BigDecimal sysFeeAppPurQty;

    /** 申请信息-运费 */
    @ApiModelProperty("申请信息-运费")
    private BigDecimal sysFeeAppFreight;

    /** 申请信息-费用合计 */
    @ApiModelProperty("申请信息-费用合计")
    private BigDecimal sysFeeAppTotalFee;

    /** 申请信息-商品图片 */
    @ApiModelProperty("申请信息-商品图片")
    private String sysFeeAppProPic;

    /** 申请信息-是否可退款 值域{"是","否"} */
    @ApiModelProperty("申请信息-是否可退款 值域{'是','否'}")
    private String sysFeeAppSupplierIr;

    /** 申请信息-退款方式 值域{"首单退款","订单量退款","订单金额退款"} */
    @ApiModelProperty("申请信息-退款方式 值域{'首单退款','订单量退款','订单金额退款'}")
    private String sysFeeAppSupplierRm;

    /** 申请信息-退款条件 */
    @ApiModelProperty("申请信息-退款条件")
    private BigDecimal sysFeeAppSupplierRc;

    /** 申请信息-供应商编号 */
    @ApiModelProperty("申请信息-供应商编号")
    private String sysFeeAppSupplierNum;

    /** 申请信息-供应商名称 */
    @ApiModelProperty("申请信息-供应商名称")
    private String sysFeeAppSupplierName;

    /** 申请信息-账户类型 */
    @ApiModelProperty("申请信息-账户类型")
    private String sysFeeAppSupplierAt;

    /** 申请信息-收款方式 */
    @ApiModelProperty("申请信息-收款方式")
    private String sysFeeAppSupplierPm;

    /** 申请信息-银行账号 */
    @ApiModelProperty("申请信息-银行账号")
    private String sysFeeAppSupplierBau;

    /** 申请信息-账号户名 */
    @ApiModelProperty("申请信息-账号户名")
    private String sysFeeAppSupplierAn;

    /** 申请信息-开户行 */
    @ApiModelProperty("申请信息-开户行")
    private String sysFeeAppSupplierOb;

    /** 申请信息-支付宝账号 */
    @ApiModelProperty("申请信息-支付宝账号")
    private String sysFeeAppSupplierAa;

    /** 申请信息-采购负责人姓名 */
    @ApiModelProperty("申请信息-采购负责人姓名")
    private String sysFeeAppPurPerName;

    /** 申请信息-采购负责人编号 */
    @ApiModelProperty("申请信息-采购负责人编号")
    private String sysFeeAppPurPerCode;

    /** 申请信息-提交时间 */
    @ApiModelProperty("申请信息-提交时间")
    private Date sysFeeAppSubDate;

    /** 申请信息-补充信息-订单号 */
    @ApiModelProperty("申请信息-补充信息-订单号")
    private String sysFeeAppOrderNum;

    /** 申请信息-补充信息-上传时间 */
    @ApiModelProperty("申请信息-补充信息-上传时间")
    private Date sysFeeAppOrderUd;

    /** 申请信息-补充信息-上传员工姓名 */
    @ApiModelProperty("申请信息-补充信息-上传员工姓名")
    private String sysFeeAppOrderUpn;

    /** 申请信息-补充信息-上传员工编号 */
    @ApiModelProperty("申请信息-补充信息-上传员工编号")
    private String sysFeeAppOrderUpc;

    /** 申请信息-补充信息-订单截图 */
    @ApiModelProperty("申请信息-补充信息-订单截图")
    private String sysFeeAppOrderPic;

    /** 审核信息-审核结果 值域{"同意","不同意"} */
    @ApiModelProperty("审核信息-审核结果 值域{'同意','不同意'}")
    private String sysFeeAppAuditResult;

    /** 审核信息-审核备注 */
    @ApiModelProperty("审核信息-审核备注")
    private String sysFeeAppAuditRemarks;

    /** 审核信息-审核时间 */
    @ApiModelProperty("审核信息-审核时间")
    private Date sysFeeAppAuditDate;

    /** 审核信息-审核员工姓名 */
    @ApiModelProperty("审核信息-审核员工姓名")
    private String sysFeeAppAuditPerName;

    /** 审核信息-审核员工编号 */
    @ApiModelProperty("审核信息-审核员工编号")
    private String sysFeeAppAuditPerCode;

    /** 审批信息-审批结果 值域{"同意","不同意"} */
    @ApiModelProperty("审批信息-审批结果 值域{'同意','不同意'}")
    private String sysFeeAppAppResult;

    /** 审批信息-审批备注 */
    @ApiModelProperty("审批信息-审批备注")
    private String sysFeeAppAppRemarks;

    /** 审批信息-审批时间 */
    @ApiModelProperty("审批信息-审批时间")
    private Date sysFeeAppAppDate;

    /** 审批信息-审批员工姓名 */
    @ApiModelProperty("审批信息-审批员工姓名")
    private String sysFeeAppAppPerName;

    /** 审批信息-审批员工编号 */
    @ApiModelProperty("审批信息-审批员工编号")
    private String sysFeeAppAppPerCode;

    /** 支付信息-支付日期 */
    @ApiModelProperty("支付信息-支付日期")
    private Date sysFeeAppPayDate;

    /** 支付信息-实际支付金额 */
    @ApiModelProperty("支付信息-实际支付金额")
    private BigDecimal sysFeeAppPayAmount;

    /** 支付信息-支付宝账户 */
    @ApiModelProperty("支付信息-支付宝账户")
    private String sysFeeAppAlipayAccount;

    /** 支付信息-支付宝账户户名 */
    @ApiModelProperty("支付信息-支付宝账户户名")
    private String sysFeeAppAlipayAn;

    /** 支付信息-差额说明 */
    @ApiModelProperty("支付信息-差额说明")
    private String sysFeeAppPayVd;

    /** 支付信息-支付登记时间 */
    @ApiModelProperty("支付信息-支付登记时间")
    private Date sysFeeAppPayRd;

    /** 支付信息-支付登记员工姓名 */
    @ApiModelProperty("支付信息-支付登记员工姓名")
    private String sysFeeAppPayRpn;

    /** 支付信息-支付登记员工编号 */
    @ApiModelProperty("支付信息-支付登记员工编号")
    private String sysFeeAppPayRpc;

    /** 撤销信息-撤销日期 */
    @ApiModelProperty("撤销信息-撤销日期")
    private Date sysFeeAppRevokeDate;

    /** 撤销信息-撤销原因 */
    @ApiModelProperty("撤销信息-撤销原因")
    private String sysFeeAppRevokeReason;

    /** 撤销信息-撤销员工姓名 */
    @ApiModelProperty("撤销信息-撤销员工姓名")
    private String sysFeeAppRevokePerName;

    /** 撤销信息-撤销员工编号 */
    @ApiModelProperty("撤销信息-撤销员工编号")
    private String sysFeeAppRevokePerCode;

    /** 功能操作-保存/提交 */
    @ApiModelProperty("功能操作-保存/提交")
    private String sysFuncOpr;

    /** 界面操作-新增/编辑 */
    @ApiModelProperty("界面操作-新增/编辑")
    private String sysPageOpr;

    /** 是否未完成工作 */
    @ApiModelProperty("是否未完成工作")
    private String sysInComplete;
}
