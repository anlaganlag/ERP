package com.tadpole.cloud.product.api.productproposal.model.result;

import java.math.BigDecimal;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.tadpole.cloud.product.api.productproposal.entity.RdSampleManage;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 提案-退款登记 出参类
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
@TableName("RD_REF_REGIST")
public class RdRefRegistResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "SYS_REF_APP_CODE", type = IdType.ASSIGN_ID)
    private String sysRefAppCode;


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


    @ApiModelProperty("系统信息-退款申请状态 值域{'待退款-条件未达成','待退款-条件已达成','待退款确认','待指定账户','待上传凭证','退款失效','待打印','待验资','退款已确认'}")
    private String sysRefAppStatus;


    @ApiModelProperty("单据联系-产品线编号")
    private String sysPlCode;


    @ApiModelProperty("单据联系-SPU")
    private String sysSpu;


    @ApiModelProperty("单据联系-提案编号")
    private String sysTaCode;


    @ApiModelProperty("单据联系-拿样任务编号")
    private String sysTsTaskCode;


    @ApiModelProperty("单据联系-费用申请编号")
    private String sysFeeAppCode;


    @ApiModelProperty("单据联系-费用申请来源 值域{'购样申请','定制申请'}")
    private String sysFeeAppSource;


    @ApiModelProperty("退款记录信息-费用类型 值域{'购样费','打样费','开模费'}")
    private String sysRefFeeType;


    @ApiModelProperty("退款记录信息-退款费用")
    private BigDecimal sysRefFees;


    @ApiModelProperty("退款记录信息-供应商编码")
    private String sysRefSupplierCode;


    @ApiModelProperty("退款记录信息-供应商名称")
    private String sysRefSupplierName;


    @ApiModelProperty("退款记录信息-采购负责人编号")
    private String sysRefPurPerCode;


    @ApiModelProperty("退款记录信息-采购负责人姓名")
    private String sysRefPurPerName;


    @ApiModelProperty("退款记录信息-退款方式")
    private String sysRefType;


    @ApiModelProperty("退款记录信息-退款条件")
    private BigDecimal sysRefCondition;


    @ApiModelProperty("订单信息-已下单数量")
    private BigDecimal sysOrderQty;


    @ApiModelProperty("订单信息-已下单总额")
    private BigDecimal sysOrderAmount;


    @ApiModelProperty("订单信息-更新时间")
    private Date sysUpdate;


    @ApiModelProperty("退款反馈信息-实际退款结果 值域{'直接退款','订单扣款','退款失效-提案失败','退款失效-其它'}")
    private String sysRefActualRefResult;


    @ApiModelProperty("退款反馈信息-退款账户类型 值域{'个人账户','公司账户'}")
    private String sysRefAccountType;


    @ApiModelProperty("退款反馈信息-失效说明")
    private String sysRefInvalidExplain;


    @ApiModelProperty("退款反馈信息-退款反馈时间")
    private Date sysRefFebDate;


    @ApiModelProperty("指定账户信息-公司-公司实体公司名称")
    private String sysRefAppComp;


    @ApiModelProperty("指定账户信息-公司-账户性质")
    private String sysRefAppAp;


    @ApiModelProperty("指定账户信息-公司-账户")
    private String sysRefAppAccount;


    @ApiModelProperty("指定账户信息-公司-账户户名")
    private String sysRefAppAn;


    @ApiModelProperty("指定账户信息-公司-账户开户行")
    private String sysRefAppAob;


    @ApiModelProperty("指定账户信息-指定账户时间")
    private Date sysRefAppRd;


    @ApiModelProperty("指定账户信息-指定账户登记人编号")
    private String sysRefAppRPc;


    @ApiModelProperty("指定账户信息-指定账户登记人名称")
    private String sysRefAppRPn;


    @ApiModelProperty("凭证信息-实际退款金额")
    private BigDecimal sysRefAppAmount;


    @ApiModelProperty("凭证信息-差异说明")
    private String sysRefAppExplain;


    @ApiModelProperty("凭证信息-打款人")
    private String sysRefPayer;


    @ApiModelProperty("凭证信息-打款日期")
    private Date sysRefPayDate;


    @ApiModelProperty("凭证信息-退款凭证")
    private String sysRefVoucher;


    @ApiModelProperty("凭证信息-上传凭证日期")
    private Date sysRefUpVoucherDate;


    @ApiModelProperty("凭证信息-上传凭证操作人编号")
    private String sysRefUpVoucherPc;


    @ApiModelProperty("凭证信息-上传凭证操作人姓名")
    private String sysRefUpVoucherPn;


    @ApiModelProperty("退款打印-打印时间")
    private Date sysRefAppFirstPd;


    @ApiModelProperty("退款打印-打印人编号")
    private String sysRefAppFirstPpc;


    @ApiModelProperty("退款打印-打印人姓名")
    private String sysRefAppPpn;


    @ApiModelProperty("验资信息-验资时间")
    private Date sysRefAppEd;


    @ApiModelProperty("验资信息-验资人编号")
    private String sysRefAppEpc;


    @ApiModelProperty("验资信息-验资人姓名")
    private String sysRefAppEpn;

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

    @ApiModelProperty("同款设定-当前迭代版本")
    private String sysCurIteVersion;

    @ApiModelProperty("设定信息-拿样任务名称")
    private String sysTsTaskName;

    @ApiModelProperty("设定信息-拿样方式 值域{'现货拿样','定制拿样'}")
    private String sysTsSampleMethod;

    /** 收款信息 */
    @ApiModelProperty("账户类型 值域{'公户','私户'}")
    private String sysColAccountType;

    @ApiModelProperty("收款方式 值域{'银行卡','支付宝'}")
    private String sysColPayMethod;

    @ApiModelProperty("银行账号/支付宝账号")
    private String sysColAccount;

    @ApiModelProperty("账号户名")
    private String sysColAccountName;

    @ApiModelProperty("开户行")
    private String sysColBankName;

    @ApiModelProperty("定制反馈编号")
    private String sysCustFebkCode;

    @ApiModelProperty("申请日期")
    private Date sysAppDate;

    @ApiModelProperty("定制反馈信息-开模费用")
    private BigDecimal sysCfMoldOpenFee;

    @ApiModelProperty("定制反馈信息-打样费用")
    private BigDecimal sysCfSampleFee;

    /** 付款信息 */
    @ApiModelProperty("支付日期")
    private Date sysPayDate;

    @ApiModelProperty("付款方式 默认值 支付宝账号")
    private String sysPayType;

    @ApiModelProperty("付款账户")
    private String sysPayAccount;

    @ApiModelProperty("付款账户户名")
    private String sysPayAn;

    @ApiModelProperty("付款账户开户行")
    private String sysPayAob;

    @ApiModelProperty("开发样清单")
    private List<RdSampleManage> rdSampleManageList;

    @ApiModelProperty("标题")
    private String sysTitle;

    @ApiModelProperty("摘要")
    private String sysSummary;

    @ApiModelProperty("退款记录信息-退款条件界面展示")
    private String sysRefConditionView;

}
