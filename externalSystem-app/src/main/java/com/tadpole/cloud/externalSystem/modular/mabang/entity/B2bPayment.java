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
 * @desc : B2B客户付款信息
 */
@TableName("B2B_PAYMENT")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class B2bPayment implements Serializable{
 private static final long serialVersionUID = 1L;
    /** ID;默认订单号 */
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id ;
    
    /** 订单号;马帮【订单号】 */
    @TableField("platform_Order_Id")
    @ApiModelProperty(value = "订单号")
    private String platformOrderId ;
    
    /** 累计实收金额;根据销售订单号收款明细累计计算 */
    @TableField("amount_received")
    @ApiModelProperty(value = "累计实收金额")
    private BigDecimal amountReceived ;
    
    /** 待确认金额;待确认金额 */
    @TableField("amount_Unconfirmed")
    @ApiModelProperty(value = "待确认金额")
    private BigDecimal amountUnconfirmed ;
    
    /** 自定义单号;自定义单号 如阿里订单号 */
    @TableField("diy_Order_No")
    @ApiModelProperty(value = "自定义单号")
    private String diyOrderNo ;
    
    /** 销售员工号;金畅ERP最后登记人员工号 */
    @TableField("salesman_No")
    @ApiModelProperty(value = "销售员工号")
    private String salesmanNo ;
    
    /** 销售员;金畅ERP最后登记人员名称 */
    @TableField("salesman_Name")
    @ApiModelProperty(value = "销售员")
    private String salesmanName ;
    
    /** 收款明细是否含有未确认收款的记录;收款明细行的【确认收款状态】，如全部为-是，则此状态为-是，否则为-否 */
    @TableField("payment_detail_status")
    @ApiModelProperty(value = "收款明细是否含有未确认收款的记录（是，否）")
    private String paymentDetailStatus ;
    
    /** 马帮订单状态;订单状态 2.配货中 3.已发货 4.已完成 5.已作废", */
    @TableField("order_Status")
    @ApiModelProperty(value = "马帮订单状态")
    private String orderStatus ;
    
    /** 在MC的状态;正常关闭：应收金额=累计实收 系统自动 业务关闭：应收金额=累计实收 业务手动关单 财务关闭：差异金额，财务手动在K3做完凭证，手动关单 */
    @TableField("MC_Status")
    @ApiModelProperty(value = "在MC的状态")
    private String mcStatus ;
    
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
    

}