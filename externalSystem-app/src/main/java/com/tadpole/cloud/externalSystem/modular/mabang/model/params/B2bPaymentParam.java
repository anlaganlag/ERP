package com.tadpole.cloud.externalSystem.modular.mabang.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * B2B客户付款信息;
 * @author : LSY
 * @date : 2023-9-14
 */
@ApiModel(value = "B2B客户付款信息",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class B2bPaymentParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** ID;默认订单号 */
    @ApiModelProperty(value = "ID")
    private String id ;
    
    /** 订单号;马帮【订单号】 */
    @ApiModelProperty(value = "订单号")
    private String platformOrderId ;
    
    /** 累计实收金额;根据销售订单号收款明细累计计算 */
    @ApiModelProperty(value = "累计实收金额")
    private BigDecimal amountReceived ;
    
    /** 待确认金额;待确认金额 */
    @ApiModelProperty(value = "待确认金额")
    private BigDecimal amountUnconfirmed ;
    
    /** 自定义单号;自定义单号 如阿里订单号 */
    @ApiModelProperty(value = "自定义单号")
    private String diyOrderNo ;
    
    /** 销售员工号;金畅ERP最后登记人员工号 */
    @ApiModelProperty(value = "销售员工号")
    private String salesmanNo ;

   @ApiModelProperty(value = "销售员工号List")
   private List<String> salesmanNoList ;
    
    /** 销售员;金畅ERP最后登记人员名称 */
    @ApiModelProperty(value = "销售员")
    private String salesmanName ;

    /** 销售员;金畅ERP最后登记人员名称 */
    @ApiModelProperty(value = "销售员List")
    private List<String> salesmanNameList ;
    
    /** 收款明细是否含有未确认收款的记录;收款明细行的【确认收款状态】，如全部为-是，则此状态为-是，否则为-否 */
    @ApiModelProperty(value = "收款明细是否含有未确认收款的记录")
    private String paymentDetailStatus ;
    
    /** 马帮订单状态;订单状态 2.配货中 3.已发货 4.已完成 5.已作废", */
    @ApiModelProperty(value = "马帮订单状态")
    private String orderStatus ;
    
    /** 在MC的状态;正常关闭：应收金额=累计实收 系统自动 业务关闭：应收金额=累计实收 业务手动关单 财务关闭：差异金额，财务手动在K3做完凭证，手动关单 */
    @ApiModelProperty(value = "在MC的状态")
    private String mcStatus ;

    /** 在MC的状态;正常关闭：应收金额=累计实收 系统自动 业务关闭：应收金额=累计实收 业务手动关单 财务关闭：差异金额，财务手动在K3做完凭证，手动关单 */
    @ApiModelProperty(value = "在MC的状态List")
    private List<String> mcStatusList ;

    
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

    /** MCMS含义:客户ID-----马帮含义:买家账号 */
    @ApiModelProperty(value = "MCMS含义:客户ID-----马帮含义:买家账号")
    private String buyerUserId ;

    /** MCMS含义:客户姓名-----马帮含义:买家姓名 */
    @ApiModelProperty(value = "MCMS含义:客户姓名-----马帮含义:买家姓名")
    private String buyerName ;

    /** MCMS含义:客户姓名-----马帮含义:买家姓名 */
    @ApiModelProperty(value = "客户名称")
    private String companyStreet ;

    /** 店铺名称 */
    @ApiModelProperty(value = "店铺名称")
    private List<String> shopNameList ;

    /** 店铺编号 */
    @ApiModelProperty(value = "店铺编号")
    private List<String> shopIdList ;

    /** 财务编码List */
    @ApiModelProperty(value = "财务编码List")
    private List<String> financeCodeList;


   /**
    * 创建时间开始时间
    */
   @JSONField(format = "yyyy-MM-dd")
   @ApiModelProperty(value = "创建时间开始时间")
   private LocalDateTime createDateStart;

   /**
    * 创建时间结束时间
    */
   @JSONField(format = "yyyy-MM-dd")
   @ApiModelProperty(value = "创建时间结束时间")
   private LocalDateTime createDateEnd;


}