package com.tadpole.cloud.externalSystem.modular.mabang.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * B2B客户付款明细;
 * @author : LSY
 * @date : 2023-9-14
 */
@ApiModel(value = "B2B客户付款明细",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class B2bPaymentDetailParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** ID */
    @ApiModelProperty(value = "ID")
    private BigDecimal id ;
    
    /** 订单号;马帮【订单号】 */
    @ApiModelProperty(value = "订单号")
    private String platformOrderId ;
    
    /** 币别;本次付款使用币种 */
    @ApiModelProperty(value = "币别")
    private String currency ;
    
    /** 汇率 */
    @ApiModelProperty(value = "汇率")
    private BigDecimal currencyRate ;
    
    /** 付款方式 */
    @ApiModelProperty(value = "付款方式")
    private String payType ;
    
    /** 付款比例;存小数 */
    @ApiModelProperty(value = "付款比例")
    private BigDecimal payRatio ;
    
    /** 付款比例显示名称;针对订单的付款比例 */
    @ApiModelProperty(value = "付款比例显示名称")
    private String payRatioTitle ;
    
    /** 待确认金额 */
    @ApiModelProperty(value = "待确认金额")
    private BigDecimal amountUnconfirmed ;

    /** 收款账号名称 */
    @ApiModelProperty(value = "收款账号名称")
    private String accountName ;
    
    /** 收款账号;业务员填写收款账号 */
    @ApiModelProperty(value = "收款账号")
    private String accountNumber ;
    
    /** 已确认金额-财务 */
    @ApiModelProperty(value = "已确认金额-财务")
    private BigDecimal amountConfirmed ;

    /** 已确认手续费-财务 */
    @ApiModelProperty(value = "已确认手续费-财务")
    private BigDecimal amountCommission ;
    
    /** 发票号;使用PayPal支付时的发票号-建立订单的时候填写的 */
    @ApiModelProperty(value = "发票号")
    private String invNumber ;
    
    /** 资金在银行到账;默认财务填写银行到账时间，不做强制要求，财务人员自己填写时间可以变化 */
    @ApiModelProperty(value = "资金在银行到账")
    private Date bankArrivalTime ;
    
    /** 运营提交状态;运营对数据的暂存或者提交（暂存，提交） */
    @ApiModelProperty(value = "运营提交状态")
    private String operateSubmit ;
    
    /** 财务确认状态;未确认,已确认 */
    @ApiModelProperty(value = "财务确认状态")
    private String confirmStatus ;
    
    /** 财务人员姓名 */
    @ApiModelProperty(value = "财务人员姓名")
    private String confirmPerson ;
    
    /** 财务人员工号 */
    @ApiModelProperty(value = "财务人员工号")
    private String confirmPersonNo ;
    
    /** 财务确认操作时间 */
    @ApiModelProperty(value = "财务确认操作时间")
    private Date confirmTime ;
    
    /** K3凭证号;k3创建凭证成功后接口返回 */
    @ApiModelProperty(value = "K3凭证号")
    private String voucherNo ;
    
    /** 同步方式 */
    @ApiModelProperty(value = "同步方式")
    private String syncType ;
    
    /** 同步状态;-1：数据初始化，0：同步失败，1：同步成功 */
    @ApiModelProperty(value = "同步状态")
    private BigDecimal syncStatus ;
    
    /** 同步时间 */
    @ApiModelProperty(value = "同步时间")
    private Date syncTime ;
    
    /** 同步请求参数内容 */
    @ApiModelProperty(value = "同步请求参数内容")
    private String syncRequstPar ;
    
    /** 同步结果消息内容 */
    @ApiModelProperty(value = "同步结果消息内容")
    private String syncResultMsg ;
    
    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    private String createdBy ;
    
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime ;
    
    /** 更新人 */
    @ApiModelProperty(value = "更新人")
    private String updatedBy ;
    
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime ;
    
    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark ;
    

}