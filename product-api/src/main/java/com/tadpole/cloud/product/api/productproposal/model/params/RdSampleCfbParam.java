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
 * 提案-定制反馈 入参类
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
@TableName("RD_SAMPLE_CFB")
public class RdSampleCfbParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 定制系统信息-定制反馈编号 */
    @TableId(value = "SYS_CUST_FEBK_CODE", type = IdType.ASSIGN_ID)
    private String sysCustFebkCode;

    /** 定制系统信息-创建时间 */
    @ApiModelProperty("定制系统信息-创建时间")
    private Date sysCDate;

    /** 定制系统信息-最后更新时间 */
    @ApiModelProperty("定制系统信息-最后更新时间")
    private Date sysLDate;

    /** 定制系统信息-部门编号 */
    @ApiModelProperty("定制系统信息-部门编号")
    private String sysDeptCode;

    /** 定制系统信息-部门名称 */
    @ApiModelProperty("定制系统信息-部门名称")
    private String sysDeptName;

    /** 定制系统信息-员工编号 */
    @ApiModelProperty("定制系统信息-员工编号")
    private String sysPerCode;

    /** 定制系统信息-员工姓名 */
    @ApiModelProperty("定制系统信息-员工姓名")
    private String sysPerName;

    /** 定制系统信息-反馈状态 值域{“待提交”,"待开发反馈","开发已反馈"} */
    @ApiModelProperty("定制系统信息-反馈状态 值域{'待提交','待开发反馈','开发已反馈'}")
    private String sysCfStatus;

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

    /** 定制反馈信息-供应商编号 */
    @ApiModelProperty("定制反馈信息-供应商编号")
    private String sysCfSupplierNum;

    /** 定制反馈信息-供应商名称 */
    @ApiModelProperty("定制反馈信息-供应商名称")
    private String sysCfSupplierName;

    /** 定制反馈信息-货源地 */
    @ApiModelProperty("定制反馈信息-货源地")
    private String sysCfGoodsSource;

    /** 定制反馈信息-开模费用 */
    @ApiModelProperty("定制反馈信息-开模费用")
    private BigDecimal sysCfMoldOpenFee;

    /** 定制反馈信息-费用合计 */
    @ApiModelProperty("定制反馈信息-费用合计")
    private BigDecimal sysCfFeeTotal;

    /** 定制反馈信息-打样费用 */
    @ApiModelProperty("定制反馈信息-打样费用")
    private BigDecimal sysCfSampleFee;

    /** 定制反馈信息-是否开模 值域{"是","否"} */
    @ApiModelProperty("定制反馈信息-是否开模 值域{'是','否'}")
    private String sysCfIsMoldOpen;

    /** 定制反馈信息-是否可退款 值域{"是","否"} */
    @ApiModelProperty("定制反馈信息-是否可退款 值域{'是','否'}")
    private String sysCfIsRefund;

    /** 定制反馈信息-退款方式 值域{"首单退款","订单量退款","订单金额退款"} */
    @ApiModelProperty("定制反馈信息-退款方式 值域{'首单退款','订单量退款','订单金额退款'}")
    private String sysCfRefundType;

    /** 定制反馈信息-退款条件 */
    @ApiModelProperty("定制反馈信息-退款条件")
    private BigDecimal sysCfRefundCondition;

    /** 定制反馈信息-初始报价 */
    @ApiModelProperty("定制反馈信息-初始报价")
    private BigDecimal sysCfInitQuote;

    /** 定制反馈信息-起订量要求 */
    @ApiModelProperty("定制反馈信息-起订量要求")
    private BigDecimal sysCfMinOrderQtyReq;

    /** 定制反馈信息-生产周期 */
    @ApiModelProperty("定制反馈信息-生产周期")
    private BigDecimal sysCfProductCycle;

    /** 定制反馈信息-定制用时 */
    @ApiModelProperty("定制反馈信息-定制用时")
    private BigDecimal sysCfCustTime;

    /** 定制反馈信息-附加条件 */
    @ApiModelProperty("定制反馈信息-附加条件")
    private String sysCfAdditCondition;

    /** 定制反馈信息-票据类型 值域{"专用发票","普通发票","收据"} */
    @ApiModelProperty("定制反馈信息-票据类型 值域{'专用发票','普通发票','收据'}")
    private String sysCfTicketType;

    /** 定制反馈信息-账户类型 值域{"公户","私户"} */
    @ApiModelProperty("定制反馈信息-账户类型 值域{'公户','私户'}")
    private String sysCfAccountType;

    /** 定制反馈信息-收款方式 值域{"银行卡","支付宝"} */
    @ApiModelProperty("定制反馈信息-收款方式 值域{'银行卡','支付宝'}")
    private String sysCfPayMethod;

    /** 定制反馈信息-银行账号 */
    @ApiModelProperty("定制反馈信息-银行账号")
    private String sysCfBankAccount;

    /** 定制反馈信息-账号户名 */
    @ApiModelProperty("定制反馈信息-账号户名")
    private String sysCfAccountName;

    /** 定制反馈信息-开户行 */
    @ApiModelProperty("定制反馈信息-开户行")
    private String sysCfBankName;

    /** 定制反馈信息-支付宝账号 */
    @ApiModelProperty("定制反馈信息-支付宝账号")
    private String sysCfAlipayAccount;

    /** 定制反馈信息-支付宝账号户名 */
    @ApiModelProperty("定制反馈信息-支付宝账号户名")
    private String sysCfAlipayAn;

    /** 定制反馈信息-采购负责人编号 */
    @ApiModelProperty("定制反馈信息-采购负责人编号")
    private String sysCfPurPerCode;

    /** 定制反馈信息-采购负责人姓名 */
    @ApiModelProperty("定制反馈信息-采购负责人姓名")
    private String sysCfPurPerName;

    /** 定制反馈信息-提交员工姓名 */
    @ApiModelProperty("定制反馈信息-提交员工姓名")
    private String sysCfSubPerName;

    /** 定制反馈信息-提交员工编号 */
    @ApiModelProperty("定制反馈信息-提交员工编号")
    private String sysCfSubPerCode;

    /** 定制反馈信息-提交时间 */
    @ApiModelProperty("定制反馈信息-提交时间")
    private Date sysCfSubDate;

    /** 开发反馈信息-不采纳说明 */
    @ApiModelProperty("开发反馈信息-不采纳说明")
    private String sysCfDevExplain;

    /** 开发反馈信息-采纳结果 值域{"采纳","不采纳"} */
    @ApiModelProperty("开发反馈信息-采纳结果 值域{'采纳','不采纳'}")
    private String sysCfDevResult;

    /** 开发反馈信息-开发反馈时间 */
    @ApiModelProperty("开发反馈信息-开发反馈时间")
    private Date sysCfDevDate;

    /** 开发反馈信息-开发反馈员工姓名 */
    @ApiModelProperty("开发反馈信息-开发反馈员工姓名")
    private String sysCfDevPerName;

    /** 开发反馈信息-开发反馈员工编号 */
    @ApiModelProperty("开发反馈信息-开发反馈员工编号")
    private String sysCfDevPerCode;

    /** 定制反馈信息-模具归属 值域{"公司","共有","供应商"} */
    @ApiModelProperty("定制反馈信息-模具归属")
    private String sysCfMoldOwnership;

    /** 定制反馈信息-打样数量 */
    @ApiModelProperty("定制反馈信息-打样数量")
    private BigDecimal sysCfSampleQty;

    /** 功能操作-保存/提交 */
    @ApiModelProperty("功能操作-保存/提交")
    private String sysFuncOpr;

    /** 界面操作-新增/编辑 */
    @ApiModelProperty("界面操作-新增/编辑")
    private String sysPageOpr;

    /** 是否提案管理界面操作-是/否 */
    @ApiModelProperty("是否提案管理界面操作-是/否")
    private String sysIsRdPageOpr;

    /** 是否未完成工作 */
    @ApiModelProperty("是否未完成工作")
    private String sysInComplete;
}
