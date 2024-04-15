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
 * 提案-定制反馈 出参类
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
@TableName("RD_SAMPLE_CFB")
public class RdSampleCfbResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "SYS_CUST_FEBK_CODE", type = IdType.ASSIGN_ID)
    private String sysCustFebkCode;


    @ApiModelProperty("定制系统信息-创建时间")
    private Date sysCDate;


    @ApiModelProperty("定制系统信息-最后更新时间")
    private Date sysLDate;


    @ApiModelProperty("定制系统信息-部门编号")
    private String sysDeptCode;


    @ApiModelProperty("定制系统信息-部门名称")
    private String sysDeptName;


    @ApiModelProperty("定制系统信息-员工编号")
    private String sysPerCode;


    @ApiModelProperty("定制系统信息-员工姓名")
    private String sysPerName;


    @ApiModelProperty("定制系统信息-反馈状态 值域{'待提交','待开发反馈','开发已反馈'}")
    private String sysCfStatus;


    @ApiModelProperty("单据联系-产品线编号")
    private String sysPlCode;


    @ApiModelProperty("单据联系-SPU")
    private String sysSpu;


    @ApiModelProperty("单据联系-提案编号")
    private String sysTaCode;


    @ApiModelProperty("单据联系-拿样任务编号")
    private String sysTsTaskCode;


    @ApiModelProperty("定制反馈信息-供应商编号")
    private String sysCfSupplierNum;


    @ApiModelProperty("定制反馈信息-供应商名称")
    private String sysCfSupplierName;


    @ApiModelProperty("定制反馈信息-货源地")
    private String sysCfGoodsSource;


    @ApiModelProperty("定制反馈信息-开模费用")
    private BigDecimal sysCfMoldOpenFee;


    @ApiModelProperty("定制反馈信息-费用合计")
    private BigDecimal sysCfFeeTotal;


    @ApiModelProperty("定制反馈信息-打样费用")
    private BigDecimal sysCfSampleFee;


    @ApiModelProperty("定制反馈信息-是否开模 值域{'是','否'}")
    private String sysCfIsMoldOpen;


    @ApiModelProperty("定制反馈信息-是否可退款 值域{'是','否'}")
    private String sysCfIsRefund;


    @ApiModelProperty("定制反馈信息-退款方式 值域{'首单退款','订单量退款','订单金额退款'}")
    private String sysCfRefundType;


    @ApiModelProperty("定制反馈信息-退款条件")
    private BigDecimal sysCfRefundCondition;


    @ApiModelProperty("定制反馈信息-初始报价")
    private BigDecimal sysCfInitQuote;


    @ApiModelProperty("定制反馈信息-起订量要求")
    private BigDecimal sysCfMinOrderQtyReq;


    @ApiModelProperty("定制反馈信息-生产周期")
    private BigDecimal sysCfProductCycle;


    @ApiModelProperty("定制反馈信息-定制用时")
    private BigDecimal sysCfCustTime;


    @ApiModelProperty("定制反馈信息-附加条件")
    private String sysCfAdditCondition;


    @ApiModelProperty("定制反馈信息-票据类型 值域{'专用发票','普通发票','收据'}")
    private String sysCfTicketType;


    @ApiModelProperty("定制反馈信息-账户类型 值域{'公户','私户'}")
    private String sysCfAccountType;


    @ApiModelProperty("定制反馈信息-收款方式 值域{'银行卡','支付宝'}")
    private String sysCfPayMethod;


    @ApiModelProperty("定制反馈信息-银行账号")
    private String sysCfBankAccount;


    @ApiModelProperty("定制反馈信息-账号户名")
    private String sysCfAccountName;


    @ApiModelProperty("定制反馈信息-开户行")
    private String sysCfBankName;


    @ApiModelProperty("定制反馈信息-支付宝账号")
    private String sysCfAlipayAccount;


    @ApiModelProperty("定制反馈信息-支付宝账号户名")
    private String sysCfAlipayAn;


    @ApiModelProperty("定制反馈信息-采购负责人编号")
    private String sysCfPurPerCode;


    @ApiModelProperty("定制反馈信息-采购负责人姓名")
    private String sysCfPurPerName;


    @ApiModelProperty("定制反馈信息-提交员工姓名")
    private String sysCfSubPerName;


    @ApiModelProperty("定制反馈信息-提交员工编号")
    private String sysCfSubPerCode;


    @ApiModelProperty("定制反馈信息-提交时间")
    private Date sysCfSubDate;


    @ApiModelProperty("开发反馈信息-不采纳说明")
    private String sysCfDevExplain;


    @ApiModelProperty("开发反馈信息-采纳结果 值域{'采纳','不采纳'}")
    private String sysCfDevResult;


    @ApiModelProperty("开发反馈信息-开发反馈时间")
    private Date sysCfDevDate;


    @ApiModelProperty("开发反馈信息-开发反馈员工姓名")
    private String sysCfDevPerName;


    @ApiModelProperty("开发反馈信息-开发反馈员工编号")
    private String sysCfDevPerCode;


    @ApiModelProperty("定制反馈信息-模具归属")
    private String sysCfMoldOwnership;


    @ApiModelProperty("定制反馈信息-打样数量")
    private BigDecimal sysCfSampleQty;

    @ApiModelProperty("系统信息-定制申请编号")
    private String sysFeeAppCode;

    @ApiModelProperty("系统信息-定制申请状态 值域{'待上传合同','合同待审核','待更新合同','待审批','待支付','已归档','已完结'}")
    private String sysSaApStatus;

    @ApiModelProperty("审批信息-审批备注")
    private String sysSaAppRemarks;

    @ApiModelProperty("申请信息-申请时间")
    private Date sysSaApDate;

    @ApiModelProperty("申请信息-申请员工姓名")
    private String sysSaApPerName;

    @ApiModelProperty("申请信息-申请员工编号")
    private String sysSaApPerCode;

    @ApiModelProperty("合同信息-合同金额")
    private BigDecimal sysSaContractAmount;

    @ApiModelProperty("合同信息-合同文件(协议)")
    private String sysSaContractDoc;

    @ApiModelProperty("合同信息-合同备注(协议)")
    private String sysSaContractRemarks;

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

    @ApiModelProperty("样品信息-已登记样品数量")
    private BigDecimal sysRegistSampleQty;

    @ApiModelProperty("立项信息-开发方式")
    private String sysDevMethond;

    @ApiModelProperty("立项信息-提案等级 值域{'S','A','B','C','D'}")
    private String sysTaLevel;

    @ApiModelProperty("系统信息-提案状态 值域{'新提案','已归档'}")
    private String sysTaStatus;

    @ApiModelProperty("立项信息-提案提案立项日期")
    private Date sysTaPaDate;

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

    @ApiModelProperty("设定信息-反馈截止时间")
    private Date sysTsFebkDeadline;

    @ApiModelProperty("要求信息-Logo设计位置")
    private String sysTsLogoDesPos;

    @ApiModelProperty("设定信息-指定品牌")
    private String sysTsDevBrand;
}
