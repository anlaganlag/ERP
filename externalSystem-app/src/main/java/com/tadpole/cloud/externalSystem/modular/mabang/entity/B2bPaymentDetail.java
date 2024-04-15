package com.tadpole.cloud.externalSystem.modular.mabang.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * @author : LSY
 * @date : 2023-9-14
 * @desc : B2B客户付款明细
 */
@TableName("B2B_PAYMENT_DETAIL")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class B2bPaymentDetail implements Serializable{
 private static final long serialVersionUID = 1L;
    /** ID */
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private BigDecimal id ;
    
    /** 订单号;马帮【订单号】 */
    @TableField("platform_Order_Id")
    @ApiModelProperty(value = "订单号")
    private String platformOrderId ;
    
    /** 币别;本次付款使用币种 */
    @TableField("currency")
    @ApiModelProperty(value = "币别")
    private String currency ;
    
    /** 汇率 */
    @TableField("CURRENCY_RATE")
    @ApiModelProperty(value = "汇率")
    private BigDecimal currencyRate ;
    
    /** 付款方式 */
    @TableField("PAY_TYPE")
    @ApiModelProperty(value = "付款方式")
    private String payType ;
    
    /** 付款比例;存小数 */
    @TableField("Pay_ratio")
    @ApiModelProperty(value = "付款比例")
    private BigDecimal payRatio ;
    
    /** 付款比例显示名称;针对订单的付款比例 */
    @TableField("Pay_ratio_title")
    @ApiModelProperty(value = "付款比例显示名称")
    private String payRatioTitle ;
    
    /** 待确认金额 */
    @TableField("amount_Unconfirmed")
    @ApiModelProperty(value = "待确认金额")
    private BigDecimal amountUnconfirmed ;

    /** 收款账号名称 */
    @TableField("account_name")
    @ApiModelProperty(value = "收款账号名称")
    private String accountName ;
    
    /** 收款账号;业务员填写收款账号 */
    @TableField("account_number")
    @ApiModelProperty(value = "收款账号")
    private String accountNumber ;
    
    /** 已确认金额-财务 */
    @TableField("amount_confirmed")
    @ApiModelProperty(value = "已确认金额-财务")
    private BigDecimal amountConfirmed ;

    /** 已确认手续费-财务 */
    @TableField("amount_Commission")
    @ApiModelProperty(value = "已确认手续费-财务")
    private BigDecimal amountCommission ;


    
    /** 发票号;使用PayPal支付时的发票号-建立订单的时候填写的 */
    @TableField("inv_Number")
    @ApiModelProperty(value = "发票号")
    private String invNumber ;
    
    /** 资金在银行到账;默认财务填写银行到账时间，不做强制要求，财务人员自己填写时间可以变化 */
    @TableField("Bank_arrival_time")
    @ApiModelProperty(value = "资金在银行到账")
    private Date bankArrivalTime ;
    
    /** 运营提交状态;运营对数据的暂存或者提交（暂存，已提交） */
    @TableField("operate_submit")
    @ApiModelProperty(value = "运营提交状态")
    private String operateSubmit ;
    
    /** 财务确认状态;未确认,已确认 */
    @TableField("confirm_status")
    @ApiModelProperty(value = "财务确认状态")
    private String confirmStatus ;
    
    /** 财务人员姓名 */
    @TableField("confirm_person")
    @ApiModelProperty(value = "财务人员姓名")
    private String confirmPerson ;
    
    /** 财务人员工号 */
    @TableField("confirm_person_no")
    @ApiModelProperty(value = "财务人员工号")
    private String confirmPersonNo ;
    
    /** 财务确认操作时间 */
    @TableField("confirm_time")
    @ApiModelProperty(value = "财务确认操作时间")
    private Date confirmTime ;
    
    /** K3凭证号;k3创建凭证成功后接口返回 */
    @TableField("VOUCHER_NO")
    @ApiModelProperty(value = "K3凭证号")
    private String voucherNo ;
    
    /** 同步方式 */
    @TableField("SYNC_TYPE")
    @ApiModelProperty(value = "同步方式")
    private String syncType ;
    
    /** 同步状态;-1：数据初始化，0：同步失败，1：同步成功 */
    @TableField("SYNC_STATUS")
    @ApiModelProperty(value = "同步状态")
    private BigDecimal syncStatus ;
    
    /** 同步时间 */
    @TableField("SYNC_TIME")
    @ApiModelProperty(value = "同步时间")
    private Date syncTime ;
    
    /** 同步请求参数内容 */
    @TableField("SYNC_REQUST_PAR")
    @ApiModelProperty(value = "同步请求参数内容")
    private String syncRequstPar ;
    
    /** 同步结果消息内容 */
    @TableField("SYNC_RESULT_MSG")
    @ApiModelProperty(value = "同步结果消息内容")
    private String syncResultMsg ;
    
    /** 创建人 */
    @TableField("CREATED_BY")
    @ApiModelProperty(value = "创建人")
    private String createdBy ;
    
    /** 创建时间 */
    @TableField("CREATED_TIME")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime ;
    
    /** 更新人 */
    @TableField("UPDATED_BY")
    @ApiModelProperty(value = "更新人")
    private String updatedBy ;
    
    /** 更新时间 */
    @TableField("UPDATED_TIME")
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime ;
    
    /** 备注 */
    @TableField("REMARK")
    @ApiModelProperty(value = "备注")
    private String remark ;
    

}