package com.tadpole.cloud.externalSystem.modular.mabang.model.result.ma;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * B2B马帮订单列表;
 * @author : LSY
 * @date : 2023-9-13
 */
@ApiModel(value = "B2B马帮订单列表",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class B2bMabangOrdersExportResult implements Serializable{
 private static final long serialVersionUID = 1L;

    
    /** 订单编号 */
    @ApiModelProperty(value = "订单编号")
    @ExcelProperty(value ="订单编号")
    private String platformOrderId ;
    


    /** MCMS含义:客户ID-----马帮含义:买家账号 */
    @ApiModelProperty(value = "MCMS含义:客户ID-----马帮含义:买家账号")
    @ExcelProperty(value ="客户编码")
    private String buyerUserId ;
    
    /** MCMS含义:客户姓名-----马帮含义:买家姓名 */
//    @ApiModelProperty(value = "MCMS含义:客户姓名-----马帮含义:买家姓名")
//    @ExcelProperty(value ="客户名称")
//    private String buyerName ;

    /** MCMS含义:客户姓名-----马帮含义:买家姓名 */
    @ApiModelProperty(value = "MCMS含义:公司名称-----马帮含义:公司名称")
    @ExcelProperty(value ="客户名称")
    private String companyStreet ;


    /** 店铺名称 */
    @ApiModelProperty(value = "店铺名称")
    @ExcelProperty(value ="店铺名称")
    private String shopName ;

    /** 店铺编号 */
    @ApiModelProperty(value = "店铺编号")
    @ExcelProperty(value ="店铺编号")
    private String shopId ;

    /** 财务编码 */
    @ApiModelProperty(value = "财务编码")
    @ExcelProperty(value ="财务编码")
    private String financeCode;
    

    /** 平台交易号 */
    @ApiModelProperty(value = "平台交易号")
    @ExcelProperty(value ="平台交易号")
    private String salesRecordNumber ;
    
    /** 订单金额 */
    @ApiModelProperty(value = "订单金额")
    @ExcelProperty(value ="应收金额")
    private BigDecimal orderFee ;
    
    /** MCMS含义:平台编码-----马帮含义:来源平台 */
    @ApiModelProperty(value = "MCMS含义:平台编码-----马帮含义:来源平台")
    @ExcelProperty(value ="平台编码")
    private String platformId ;
    
    /** 发货时间 */
//    @ApiModelProperty(value = "发货时间")
//    @ExcelProperty(value ="发货时间")
//    private Date expressTime ;


    /** 币种 */
    @ApiModelProperty(value = "币种")
    @ExcelProperty(value ="币别")
    private String currencyId ;

//
//    /** 商品总售价 */
//    @ApiModelProperty(value = "商品总售价")
//    @ExcelProperty(value ="商品总售价")
//    private BigDecimal itemTotal ;
//
//    /** 商品原始总售价 */
//    @ApiModelProperty(value = "商品原始总售价")
//    @ExcelProperty(value ="商品原始总售价")
//    private BigDecimal itemTotalOrigin ;



    /** MCMS含义:马帮ERP系统订单编号-----马帮含义:ERP系统订单编号   注意：字段首字母大写，字段赋值可能为空 */
//    @ApiModelProperty(value = "MCMS含义:马帮ERP系统订单编号-----马帮含义:ERP系统订单编号   注意：字段首字母大写，字段赋值可能为空")
//    @ExcelProperty(value ="马帮ERP系统订单编号")
//    private String erpOrderId ;

    
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @ExcelProperty(value ="业务日期")
    private Date createTime ;
    


    /** 累计实收金额;根据销售订单号收款明细累计计算 */
    @ApiModelProperty(value = "累计实收金额")
    private BigDecimal amountReceived ;

    /** 在MC的状态;正常关闭：应收金额=累计实收 系统自动 业务关闭：应收金额=累计实收 业务手动关单 财务关闭：差异金额，财务手动在K3做完凭证，手动关单 */
    @ApiModelProperty(value = "订单状态")
    @ExcelProperty(value ="订单状态")
    private String mcStatus ;


    /** 收款明细是否含有未确认收款的记录;收款明细行的【确认收款状态】，如全部为-是，则此状态为-是，否则为-否 */
    @ApiModelProperty(value = "收款明细是否含有未确认收款的记录")
    @ExcelProperty(value ="收款状态")
    private String paymentDetailStatus ;


    /** 自定义单号;自定义单号 如阿里订单号 */
    @ApiModelProperty(value = "自定义单号")
    @ExcelProperty(value ="自定义单号")
    private String diyOrderNo ;


    /** 销售员工号;金畅ERP最后登记人员工号 */
    @ApiModelProperty(value = "销售员工号")
    @ExcelProperty(value ="销售员工号")
    private String salesmanNo ;

    /** 销售员;金畅ERP最后登记人员名称 */
    @ApiModelProperty(value = "销售员")
    @ExcelProperty(value ="销售员")
    private String salesmanName ;


   /////////////////////////////////


   /** 币别;本次付款使用币种 */
   @ApiModelProperty(value = "币别")
   @ExcelProperty(value ="收款币别")
   private String currency ;

   /** 汇率 */
   @ApiModelProperty(value = "汇率")
   @ExcelProperty(value ="汇率")
   private BigDecimal currencyRate ;

   /** 付款方式 */
   @ApiModelProperty(value = "付款方式")
   @ExcelProperty(value ="收付款方式")
   private String payType ;

   /** 付款比例;存小数 */
//   @ApiModelProperty(value = "付款比例")
//   @ExcelProperty(value ="付款比例")
//   private BigDecimal payRatio ;

   /** 付款比例显示名称;针对订单的付款比例 */
//   @ApiModelProperty(value = "付款比例显示名称")
//   @ExcelProperty(value ="付款比例显示名称")
//   private String payRatioTitle ;

   /** 待确认金额 */
   @ApiModelProperty(value = "待确认金额")
   @ExcelProperty(value ="登记收款金额")
   private BigDecimal amountUnconfirmed ;

   /** 收款账号;业务员填写收款账号 */
   @ApiModelProperty(value = "收款账号")
   @ExcelProperty(value ="收款账号")
   private String accountNumber ;

   /** 已确认金额-财务 */
   @ApiModelProperty(value = "已确认金额-财务")
   @ExcelProperty(value ="银行存入金额")
   private BigDecimal amountConfirmed ;

   /** 已确认手续费-财务 */
   @ApiModelProperty(value = "已确认手续费-财务")
   @ExcelProperty(value ="银行类费用")
   private BigDecimal amountCommission ;

   /** 发票号;使用PayPal支付时的发票号-建立订单的时候填写的 */
   @ApiModelProperty(value = "发票号")
   @ExcelProperty(value ="发票号码")
   private String invNumber ;

   /** 资金在银行到账;默认财务填写银行到账时间，不做强制要求，财务人员自己填写时间可以变化 */
   @ApiModelProperty(value = "资金在银行到账时间")
   @ExcelProperty(value ="资金在银行到账时间")
   private Date bankArrivalTime ;

   /** 运营提交状态;运营对数据的暂存或者提交（暂存，提交） */
   @ApiModelProperty(value = "运营提交状态")
   @ExcelProperty(value ="运营提交状态")
   private String operateSubmit ;

   /** 财务确认状态;未确认,已确认 */
   @ApiModelProperty(value = "财务确认状态")
   @ExcelProperty(value ="财务确认收款状态")
   private String confirmStatus ;

   /** 财务人员姓名 */
   @ApiModelProperty(value = "财务人员姓名")
   @ExcelProperty(value ="财务人员姓名")
   private String confirmPerson ;

   /** 财务人员工号 */
   @ApiModelProperty(value = "财务人员工号")
   @ExcelProperty(value ="财务人员工号")
   private String confirmPersonNo ;

   /** 财务确认操作时间 */
   @ApiModelProperty(value = "财务确认操作时间")
   @ExcelProperty(value ="财务确认操作时间")
   private Date confirmTime ;

   /** K3凭证号;k3创建凭证成功后接口返回 */
   @ApiModelProperty(value = "K3凭证号")
   @ExcelProperty(value ="K3凭证号")
   private String voucherNo ;



   /** 同步状态;-1：数据初始化，0：同步失败，1：同步成功 */
   @ApiModelProperty(value = "同步状态")
   @ExcelProperty(value ="K3凭证状态")
   private BigDecimal syncStatus ;

   /** 同步时间 */
   @ApiModelProperty(value = "同步时间")
   @ExcelProperty(value ="同步时间")
   private Date syncTime ;


   /** 创建人 */
   @ApiModelProperty(value = "创建人")
   @ExcelProperty(value ="创建人")
   private String createdBy ;

   /** 创建时间 */
   @ApiModelProperty(value = "创建时间")
   @ExcelProperty(value ="创建时间")
   private Date createdTime ;


}