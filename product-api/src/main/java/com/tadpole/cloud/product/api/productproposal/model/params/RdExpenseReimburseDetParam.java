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
 * 提案-研发费报销明细 入参类
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
@TableName("RD_EXPENSE_REIMBURSE_DET")
public class RdExpenseReimburseDetParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 系统信息-系统编号 */
   @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 系统信息-报销申请编号 */
    @ApiModelProperty("系统信息-报销申请编号")
    private String sysSaeaCode;

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

    /** 申请信息-来源 */
    @ApiModelProperty("申请信息-来源")
    private String sysSaeaSource;

    /** 申请信息-编号 */
    @ApiModelProperty("申请信息-编号")
    private String sysSaeaSourceId;

    /** 申请信息-报销方式 值域{"支出","退款"} */
    @ApiModelProperty("申请信息-报销方式 值域{'支出','退款'}")
    private String sysSaeaType;

    /** 申请信息-日期(支付/退款日期) */
    @ApiModelProperty("申请信息-日期(支付/退款日期)")
    private Date sysSaeaAppDate;

    /** 申请信息-拿样方式 */
    @ApiModelProperty("申请信息-拿样方式")
    private String sysSaeaSampleMethod;

    /** 申请信息-拿样渠道 */
    @ApiModelProperty("申请信息-拿样渠道")
    private String sysSaeaSampleChannel;

    /** 申请信息-店铺名称 */
    @ApiModelProperty("申请信息-店铺名称")
    private String sysSaeaShopOrSuppName;

    /** 申请信息-订单凭证 */
    @ApiModelProperty("申请信息-订单凭证")
    private String sysSaeaOrderVoucher;

    /** 申请信息-采购负责人编号 */
    @ApiModelProperty("申请信息-采购负责人编号")
    private String sysSaeaPurCode;

    /** 申请信息-采购负责人姓名 */
    @ApiModelProperty("申请信息-采购负责人姓名")
    private String sysSaeaPurName;

    /** 申请信息-申请费用金额/预计退款金额 */
    @ApiModelProperty("申请信息-申请费用金额/预计退款金额")
    private BigDecimal sysSaeaAppAmount;

    /** 申请信息-实际支付金额/实际退款金额 */
    @ApiModelProperty("申请信息-实际支付金额/实际退款金额")
    private BigDecimal sysSaeaRealAmount;

    /** 申请信息-差额说明 */
    @ApiModelProperty("申请信息-差额说明")
    private String sysSaeaVarianceDesc;

    /** 申请信息-支付登记人姓名/退款确认人姓名 */
    @ApiModelProperty("申请信息-支付登记人姓名/退款确认人姓名")
    private String sysSaeaOprName;

    /** 申请信息-支付登记人编号/退款确认人编号 */
    @ApiModelProperty("申请信息-支付登记人编号/退款确认人编号")
    private String sysSaeaOprCode;

    /** 申请信息-样品数量 */
    @ApiModelProperty("申请信息-样品数量")
    private BigDecimal sysSaeaSampleQty;

}
