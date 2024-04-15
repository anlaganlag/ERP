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
 * 提案-购样申请 出参类
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
@TableName("RD_SAMPLE_PA")
public class RdSamplePaResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "SYS_FEE_APP_CODE", type = IdType.AUTO)
    private String sysFeeAppCode;


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


    @ApiModelProperty("系统信息-申请状态 值域{'待提交','待审核','待审批','待上传','待支付','已支付','已撤销','已归档'}")
    private String sysFeeAppStatus;


    @ApiModelProperty("单据联系-产品线编号")
    private String sysPlCode;


    @ApiModelProperty("单据联系-SPU")
    private String sysSpu;


    @ApiModelProperty("单据联系-提案编号")
    private String sysTaCode;


    @ApiModelProperty("单据联系-拿样任务编号")
    private String sysTsTaskCode;


    @ApiModelProperty("申请信息-拿样渠道 值域{'供应商','1688网站','淘宝网站'}")
    private String sysFeeAppSc;


    @ApiModelProperty("申请信息-店铺名称")
    private String sysFeeAppShopName;


    @ApiModelProperty("申请信息-样品名称")
    private String sysFeeAppProName;


    @ApiModelProperty("申请信息-商品购买页面")
    private String sysFeeAppProPurPage;


    @ApiModelProperty("申请信息-样品单价")
    private BigDecimal sysFeeAppSup;


    @ApiModelProperty("申请信息-样品采购数量")
    private BigDecimal sysFeeAppPurQty;


    @ApiModelProperty("申请信息-运费")
    private BigDecimal sysFeeAppFreight;


    @ApiModelProperty("申请信息-费用合计")
    private BigDecimal sysFeeAppTotalFee;


    @ApiModelProperty("申请信息-商品图片")
    private String sysFeeAppProPic;


    @ApiModelProperty("申请信息-是否可退款 值域{'是','否'}")
    private String sysFeeAppSupplierIr;


    @ApiModelProperty("申请信息-退款方式 值域{'首单退款','订单量退款','订单金额退款'}")
    private String sysFeeAppSupplierRm;


    @ApiModelProperty("申请信息-退款条件")
    private BigDecimal sysFeeAppSupplierRc;


    @ApiModelProperty("申请信息-供应商编号")
    private String sysFeeAppSupplierNum;


    @ApiModelProperty("申请信息-供应商名称")
    private String sysFeeAppSupplierName;


    @ApiModelProperty("申请信息-账户类型")
    private String sysFeeAppSupplierAt;


    @ApiModelProperty("申请信息-收款方式")
    private String sysFeeAppSupplierPm;


    @ApiModelProperty("申请信息-银行账号")
    private String sysFeeAppSupplierBau;


    @ApiModelProperty("申请信息-账号户名")
    private String sysFeeAppSupplierAn;


    @ApiModelProperty("申请信息-开户行")
    private String sysFeeAppSupplierOb;


    @ApiModelProperty("申请信息-支付宝账号")
    private String sysFeeAppSupplierAa;


    @ApiModelProperty("申请信息-采购负责人姓名")
    private String sysFeeAppPurPerName;


    @ApiModelProperty("申请信息-采购负责人编号")
    private String sysFeeAppPurPerCode;


    @ApiModelProperty("申请信息-提交时间")
    private Date sysFeeAppSubDate;


    @ApiModelProperty("申请信息-补充信息-订单号")
    private String sysFeeAppOrderNum;


    @ApiModelProperty("申请信息-补充信息-上传时间")
    private Date sysFeeAppOrderUd;


    @ApiModelProperty("申请信息-补充信息-上传员工姓名")
    private String sysFeeAppOrderUpn;


    @ApiModelProperty("申请信息-补充信息-上传员工编号")
    private String sysFeeAppOrderUpc;


    @ApiModelProperty("申请信息-补充信息-订单截图")
    private String sysFeeAppOrderPic;


    @ApiModelProperty("审核信息-审核结果 值域{'同意','不同意'}")
    private String sysFeeAppAuditResult;


    @ApiModelProperty("审核信息-审核备注")
    private String sysFeeAppAuditRemarks;


    @ApiModelProperty("审核信息-审核时间")
    private Date sysFeeAppAuditDate;


    @ApiModelProperty("审核信息-审核员工姓名")
    private String sysFeeAppAuditPerName;


    @ApiModelProperty("审核信息-审核员工编号")
    private String sysFeeAppAuditPerCode;


    @ApiModelProperty("审批信息-审批结果 值域{'同意','不同意'}")
    private String sysFeeAppAppResult;


    @ApiModelProperty("审批信息-审批备注")
    private String sysFeeAppAppRemarks;


    @ApiModelProperty("审批信息-审批时间")
    private Date sysFeeAppAppDate;


    @ApiModelProperty("审批信息-审批员工姓名")
    private String sysFeeAppAppPerName;


    @ApiModelProperty("审批信息-审批员工编号")
    private String sysFeeAppAppPerCode;


    @ApiModelProperty("支付信息-支付日期")
    private Date sysFeeAppPayDate;


    @ApiModelProperty("支付信息-实际支付金额")
    private BigDecimal sysFeeAppPayAmount;


    @ApiModelProperty("支付信息-支付宝账户")
    private String sysFeeAppAlipayAccount;


    @ApiModelProperty("支付信息-支付宝账户户名")
    private String sysFeeAppAlipayAn;


    @ApiModelProperty("支付信息-差额说明")
    private String sysFeeAppPayVd;


    @ApiModelProperty("支付信息-支付登记时间")
    private Date sysFeeAppPayRd;


    @ApiModelProperty("支付信息-支付登记员工姓名")
    private String sysFeeAppPayRpn;


    @ApiModelProperty("支付信息-支付登记员工编号")
    private String sysFeeAppPayRpc;


    @ApiModelProperty("撤销信息-撤销日期")
    private Date sysFeeAppRevokeDate;


    @ApiModelProperty("撤销信息-撤销原因")
    private String sysFeeAppRevokeReason;


    @ApiModelProperty("撤销信息-撤销员工姓名")
    private String sysFeeAppRevokePerName;


    @ApiModelProperty("撤销信息-撤销员工编号")
    private String sysFeeAppRevokePerCode;

    @ApiModelProperty("样品信息-已登记样品数量")
    private BigDecimal sysRegistSampleQty;

    @ApiModelProperty("立项信息-产品经理编号")
    private String sysPmPerCode;

    @ApiModelProperty("立项信息-产品经理姓名")
    private String sysPmPerName;

    @ApiModelProperty("产品线名称")
    private String sysPlName;

    /** 产品线设定-产品线简码 */
    @ApiModelProperty("产品线简码")
    private String sysShortCode;

    /** 产品线设定-团队负责人编码 */
    @ApiModelProperty("产品线设定-团队负责人编码")
    private String sysPlPmPerCode;

    /** 产品线设定-团队负责人名称 */
    @ApiModelProperty("产品线设定-团队负责人名称")
    private String sysPlPmPerName;

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

    @ApiModelProperty("立项信息-开发方式")
    private String sysDevMethond;

    @ApiModelProperty("立项信息-提案等级 值域{'S','A','B','C','D'}")
    private String sysTaLevel;

    @ApiModelProperty("系统信息-提案状态 值域{'新提案','已归档'}")
    private String sysTaStatus;

    @ApiModelProperty("立项信息-提案提案立项日期")
    private Date sysTaPaDate;

    @ApiModelProperty("立项信息-版本")
    private String sysVersion;

    @ApiModelProperty("归档信息-归档时间")
    private Date sysTaArchDate;

    @ApiModelProperty("设定信息-拿样任务名称")
    private String sysTsTaskName;

    @ApiModelProperty("设定信息-拿样方式 值域{'现货拿样','定制拿样'}")
    private String sysTsSampleMethod;

    @ApiModelProperty("关闭信息-关闭时间")
    private Date sysTsCloseDate;

    @ApiModelProperty("系统信息-拿样任务状态 值域{'待发布','拿样中','关闭'}")
    private String sysTsTaskStatus;

    @ApiModelProperty("要求信息-发布时间")
    private Date sysTsRelieaseDate;

    @ApiModelProperty("设定信息-任务截止时间")
    private Date sysTsDeadline;

    @ApiModelProperty("要求信息-款式要求")
    private String sysTsStyleReq;

    @ApiModelProperty("要求信息-适用品牌或对象要求")
    private String sysTsBrandReq;

    @ApiModelProperty("要求信息-材质要求")
    private String sysTsMaterialReq;

    @ApiModelProperty("要求信息-图案要求")
    private String sysTsPatternReq;

    @ApiModelProperty("要求信息-颜色要求")
    private String sysTsColorReq;

    @ApiModelProperty("要求信息-尺寸要求")
    private String sysTsSizeReq;

    @ApiModelProperty("要求信息-重量要求")
    private String sysTsWeightReq;

    @ApiModelProperty("要求信息-包装数量要求")
    private String sysTsPackQtyReq;

    @ApiModelProperty("要求信息-功能要求")
    private String sysTsFunctionReq;

    @ApiModelProperty("要求信息-配件要求")
    private String sysTsPartsReq;

    @ApiModelProperty("要求信息-包装要求")
    private String sysTsPackReq;

    @ApiModelProperty("要求信息-合规要求")
    private String sysTsComplianceReq;

    @ApiModelProperty("要求信息-认证要求")
    private String sysTsCertificationReq;

    @ApiModelProperty("要求信息-设计文档")
    private String sysTsDesignDoc;

    @ApiModelProperty("要求信息-底线采购价")
    private BigDecimal sysTsBotLinePurPrice;

    @ApiModelProperty("要求信息-补充说明")
    private String sysTsFootnote;

    @ApiModelProperty("要求信息-Logo设计位置")
    private String sysTsLogoDesPos;

    @ApiModelProperty("设定信息-指定品牌")
    private String sysTsDevBrand;

    @ApiModelProperty("退款确认信息-退款日期")
    private Date sysRefDate;

    @ApiModelProperty("退款确认信息-退款确认人编号")
    private String sysRefPerCode;

    @ApiModelProperty("退款确认信息-退款确认人姓名")
    private String sysRefPerName;

    @ApiModelProperty("退款确认信息-实际退款金额")
    private BigDecimal sysRefRealAmount;

    @ApiModelProperty("样品信息-待提交样品数量")
    private BigDecimal sysWaitSumbitSampleQty;

    @ApiModelProperty("产品开发阶段")
    private String sysRdStage;

    @ApiModelProperty("申请费用类型")
    private String sysAppFeeType;
}
