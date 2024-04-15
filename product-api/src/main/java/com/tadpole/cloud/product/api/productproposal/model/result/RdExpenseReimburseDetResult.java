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
 * 提案-研发费报销明细 出参类
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
@ExcelIgnoreUnannotated
@TableName("RD_EXPENSE_REIMBURSE_DET")
public class RdExpenseReimburseDetResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "ID", type = IdType.AUTO)
    private String id;


    @ApiModelProperty("系统信息-报销申请编号")
    private String sysSaeaCode;


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


    @ApiModelProperty("申请信息-来源")
    private String sysSaeaSource;


    @ApiModelProperty("申请信息-编号")
    private String sysSaeaSourceId;


    @ApiModelProperty("申请信息-报销方式 值域{'支出','退款'}")
    private String sysSaeaType;


    @ApiModelProperty("申请信息-日期(支付/退款日期)")
    private Date sysSaeaAppDate;


    @ApiModelProperty("申请信息-拿样方式")
    private String sysSaeaSampleMethod;


    @ApiModelProperty("申请信息-拿样渠道")
    private String sysSaeaSampleChannel;


    @ApiModelProperty("申请信息-店铺名称")
    private String sysSaeaShopOrSuppName;


    @ApiModelProperty("申请信息-订单凭证")
    private String sysSaeaOrderVoucher;


    @ApiModelProperty("申请信息-采购负责人编号")
    private String sysSaeaPurCode;


    @ApiModelProperty("申请信息-采购负责人姓名")
    private String sysSaeaPurName;


    @ApiModelProperty("申请信息-申请费用金额/预计退款金额")
    private BigDecimal sysSaeaAppAmount;


    @ApiModelProperty("申请信息-实际支付金额/实际退款金额")
    private BigDecimal sysSaeaRealAmount;


    @ApiModelProperty("申请信息-差额说明")
    private String sysSaeaVarianceDesc;


    @ApiModelProperty("申请信息-支付登记人姓名/退款确认人姓名")
    private String sysSaeaOprName;


    @ApiModelProperty("申请信息-支付登记人编号/退款确认人编号")
    private String sysSaeaOprCode;


    @ApiModelProperty("申请信息-样品数量")
    private BigDecimal sysSaeaSampleQty;

}
