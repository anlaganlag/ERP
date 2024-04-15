package com.tadpole.cloud.externalSystem.modular.mabang.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
@ExcelIgnoreUnannotated
public class B2bPaymentResult  implements Serializable{
 private static final long serialVersionUID = 1L;
    /** ID;默认订单号 */
    @ApiModelProperty(value = "ID")
    @ExcelProperty(value ="ID")
    private String id ;
    
    /** 订单号;马帮【订单号】 */
    @ApiModelProperty(value = "订单号")
    @ExcelProperty(value ="订单号")
    private String platformOrderId ;
    
    /** 累计实收金额;根据销售订单号收款明细累计计算 */
    @ApiModelProperty(value = "累计实收金额")
    @ExcelProperty(value ="累计实收金额")
    private BigDecimal amountReceived ;
    
    /** 待确认金额;待确认金额 */
    @ApiModelProperty(value = "待确认金额")
    @ExcelProperty(value ="待确认金额")
    private BigDecimal amountUnconfirmed ;
    
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
    
    /** 收款明细是否含有未确认收款的记录;收款明细行的【确认收款状态】，如全部为-是，则此状态为-是，否则为-否 */
    @ApiModelProperty(value = "收款明细是否含有未确认收款的记录")
    @ExcelProperty(value ="收款明细是否含有未确认收款的记录")
    private String paymentDetailStatus ;
    
    /** 马帮订单状态;订单状态 2.配货中 3.已发货 4.已完成 5.已作废", */
    @ApiModelProperty(value = "马帮订单状态")
    @ExcelProperty(value ="马帮订单状态")
    private String orderStatus ;
    
    /** 在MC的状态;正常关闭：应收金额=累计实收 系统自动 业务关闭：应收金额=累计实收 业务手动关单 财务关闭：差异金额，财务手动在K3做完凭证，手动关单 */
    @ApiModelProperty(value = "在MC的状态")
    @ExcelProperty(value ="在MC的状态")
    private String mcStatus ;
    
    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    @ExcelProperty(value ="创建人")
    private String createdBy ;
    
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @ExcelProperty(value ="创建时间")
    private Date createdTime ;
    
    /** 更新人 */
    @ApiModelProperty(value = "更新人")
    @ExcelProperty(value ="更新人")
    private String updatedBy ;
    
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @ExcelProperty(value ="更新时间")
    private Date updatedTime ;

}