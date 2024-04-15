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
import java.util.List;

import lombok.*;

/**
 * <p>
 * 提案-退款登记 入参类
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
@TableName("RD_REF_REGIST")
public class RdRefRegistParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统信息-退款申请编号 */
   @TableId(value = "SYS_REF_APP_CODE", type = IdType.ASSIGN_ID)
    private String sysRefAppCode;

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

    /** 系统信息-退款申请状态 值域{"待退款-条件未达成","待退款-条件已达成","待退款确认","待指定账户","待上传凭证","退款失效","待打印","待验资","退款已确认"} */
    @ApiModelProperty("系统信息-退款申请状态 值域{'待退款-条件未达成','待退款-条件已达成','待退款确认','待指定账户','待上传凭证','退款失效','待打印','待验资','退款已确认'}")
    private String sysRefAppStatus;

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

    /** 单据联系-费用申请编号 */
    @ApiModelProperty("单据联系-费用申请编号")
    private String sysFeeAppCode;

    /** 单据联系-费用申请来源 值域{"购样申请","定制申请"} */
    @ApiModelProperty("单据联系-费用申请来源 值域{'购样申请','定制申请'}")
    private String sysFeeAppSource;

    /** 退款记录信息-费用类型 值域{"购样费","打样费",“开模费”} */
    @ApiModelProperty("退款记录信息-费用类型 值域{'购样费','打样费','开模费'}")
    private String sysRefFeeType;

    /** 退款记录信息-退款费用 */
    @ApiModelProperty("退款记录信息-退款费用")
    private BigDecimal sysRefFees;

    /** 退款记录信息-供应商编码 */
    @ApiModelProperty("退款记录信息-供应商编码")
    private String sysRefSupplierCode;

    /** 退款记录信息-供应商名称 */
    @ApiModelProperty("退款记录信息-供应商名称")
    private String sysRefSupplierName;

    /** 退款记录信息-采购负责人编号 */
    @ApiModelProperty("退款记录信息-采购负责人编号")
    private String sysRefPurPerCode;

    /** 退款记录信息-采购负责人姓名 */
    @ApiModelProperty("退款记录信息-采购负责人姓名")
    private String sysRefPurPerName;

    /** 退款记录信息-退款方式 */
    @ApiModelProperty("退款记录信息-退款方式")
    private String sysRefType;

    /** 退款记录信息-退款条件 */
    @ApiModelProperty("退款记录信息-退款条件")
    private BigDecimal sysRefCondition;

    /** 订单信息-已下单数量 */
    @ApiModelProperty("订单信息-已下单数量")
    private BigDecimal sysOrderQty;

    /** 订单信息-已下单总额 */
    @ApiModelProperty("订单信息-已下单总额")
    private BigDecimal sysOrderAmount;

    /** 订单信息-更新时间 */
    @ApiModelProperty("订单信息-更新时间")
    private Date sysUpdate;

    /** 退款反馈信息-实际退款结果 值域{"直接退款","订单扣款","退款失效-提案失败","退款失效-其它"} */
    @ApiModelProperty("退款反馈信息-实际退款结果 值域{'直接退款','订单扣款','退款失效-提案失败','退款失效-其它'}")
    private String sysRefActualRefResult;

    /** 退款反馈信息-退款账户类型 值域{"个人账户","公司账户"} */
    @ApiModelProperty("退款反馈信息-退款账户类型 值域{'个人账户','公司账户'}")
    private String sysRefAccountType;

    /** 退款反馈信息-失效说明 */
    @ApiModelProperty("退款反馈信息-失效说明")
    private String sysRefInvalidExplain;

    /** 退款反馈信息-退款反馈时间 */
    @ApiModelProperty("退款反馈信息-退款反馈时间")
    private Date sysRefFebDate;

    /** 指定账户信息-公司-公司实体公司名称 */
    @ApiModelProperty("指定账户信息-公司-公司实体公司名称")
    private String sysRefAppComp;

    /** 指定账户信息-公司-账户性质 */
    @ApiModelProperty("指定账户信息-公司-账户性质")
    private String sysRefAppAp;

    /** 指定账户信息-公司-账户 */
    @ApiModelProperty("指定账户信息-公司-账户")
    private String sysRefAppAccount;

    /** 指定账户信息-公司-账户户名 */
    @ApiModelProperty("指定账户信息-公司-账户户名")
    private String sysRefAppAn;

    /** 指定账户信息-公司-账户开户行 */
    @ApiModelProperty("指定账户信息-公司-账户开户行")
    private String sysRefAppAob;

    /** 指定账户信息-指定账户时间 */
    @ApiModelProperty("指定账户信息-指定账户时间")
    private Date sysRefAppRd;

    /** 指定账户信息-指定账户登记人编号 */
    @ApiModelProperty("指定账户信息-指定账户登记人编号")
    private String sysRefAppRPc;

    /** 指定账户信息-指定账户登记人名称 */
    @ApiModelProperty("指定账户信息-指定账户登记人名称")
    private String sysRefAppRPn;

    /** 凭证信息-实际退款金额 */
    @ApiModelProperty("凭证信息-实际退款金额")
    private BigDecimal sysRefAppAmount;

    /** 凭证信息-差异说明 */
    @ApiModelProperty("凭证信息-差异说明")
    private String sysRefAppExplain;

    /** 凭证信息-打款人 */
    @ApiModelProperty("凭证信息-打款人")
    private String sysRefPayer;

    /** 凭证信息-打款日期 */
    @ApiModelProperty("凭证信息-打款日期")
    private Date sysRefPayDate;

    /** 凭证信息-退款凭证 */
    @ApiModelProperty("凭证信息-退款凭证")
    private String sysRefVoucher;

    /** 凭证信息-上传凭证日期 */
    @ApiModelProperty("凭证信息-上传凭证日期")
    private Date sysRefUpVoucherDate;

    /** 凭证信息-上传凭证操作人编号 */
    @ApiModelProperty("凭证信息-上传凭证操作人编号")
    private String sysRefUpVoucherPc;

    /** 凭证信息-上传凭证操作人姓名 */
    @ApiModelProperty("凭证信息-上传凭证操作人姓名")
    private String sysRefUpVoucherPn;

    /** 退款打印-打印时间 */
    @ApiModelProperty("退款打印-打印时间")
    private Date sysRefAppFirstPd;

    /** 退款打印-打印人编号 */
    @ApiModelProperty("退款打印-打印人编号")
    private String sysRefAppFirstPpc;

    /** 退款打印-打印人姓名 */
    @ApiModelProperty("退款打印-打印人姓名")
    private String sysRefAppPpn;

    /** 验资信息-验资时间 */
    @ApiModelProperty("验资信息-验资时间")
    private Date sysRefAppEd;

    /** 验资信息-验资人编号 */
    @ApiModelProperty("验资信息-验资人编号")
    private String sysRefAppEpc;

    /** 验资信息-验资人姓名 */
    @ApiModelProperty("验资信息-验资人姓名")
    private String sysRefAppEpn;

    /** 单据联系-费用申请编号集合 */
    @ApiModelProperty("单据联系-费用申请编号集合")
    private List<String> sysFeeAppCodeList;

    /** 系统信息-退款申请状态 值域{"待退款-条件未达成","待退款-条件已达成","待退款确认","待指定账户","待上传凭证","退款失效","待打印","待验资","退款已确认"} */
    @ApiModelProperty("系统信息-退款申请状态 值域{'待退款-条件未达成','待退款-条件已达成','待退款确认','待指定账户','待上传凭证','退款失效','待打印','待验资','退款已确认'}")
    private List<String> sysRefAppStatusList;
}
