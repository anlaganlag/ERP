package com.tadpole.cloud.operationManagement.api.shopEntity.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * 资源-店铺申请银行收款任务;资源-店铺申请银行收款任务
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-店铺申请银行收款任务",description = "资源-店铺申请银行收款任务")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComShopApplyBankCollectTaskResult  implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 收款账号任务编号 */
    @ApiModelProperty(value = "收款账号任务编号")
    @ExcelProperty(value ="收款账号任务编号")
    private BigDecimal sysApplyDetBctId ;
    
    /** 申请详情编号 */
    @ApiModelProperty(value = "申请详情编号")
    @ExcelProperty(value ="申请详情编号")
    private BigDecimal sysApplyDetId ;
    
    /** 最后更新人姓名 */
    @ApiModelProperty(value = "最后更新人姓名")
    @ExcelProperty(value ="最后更新人姓名")
    private String sysLastUpdPerName ;
    
    /** 最后更新人编号 */
    @ApiModelProperty(value = "最后更新人编号")
    @ExcelProperty(value ="最后更新人编号")
    private String sysLastUpdPerCode ;
    
    /** 最后更新时间 */
    @ApiModelProperty(value = "最后更新时间")
    @ExcelProperty(value ="最后更新时间")
    private Date sysLastUpdDate ;
    
    /** 店铺名称 */
    @ApiModelProperty(value = "店铺名称")
    @ExcelProperty(value ="店铺名称")
    private String shopName ;
    
    /** 店铺收款银行 */
    @ApiModelProperty(value = "店铺收款银行")
    @ExcelProperty(value ="店铺收款银行")
    private String shopColAccBank ;
    
    /** 店铺收款银行主体 */
    @ApiModelProperty(value = "店铺收款银行主体")
    @ExcelProperty(value ="店铺收款银行主体")
    private String shopColAccBankMainBody ;
    
    /** 店铺收款账号 */
    @ApiModelProperty(value = "店铺收款账号")
    @ExcelProperty(value ="店铺收款账号")
    private String shopColAccNo ;
    
    /** 店铺收款币种 */
    @ApiModelProperty(value = "店铺收款币种")
    @ExcelProperty(value ="店铺收款币种")
    private String shopColCurrency ;
    
    /** 收款账号邮箱 */
    @ApiModelProperty(value = "收款账号邮箱")
    @ExcelProperty(value ="收款账号邮箱")
    private String shopColAccEmail ;
    
    /** SWIFT Code(BIC) */
    @ApiModelProperty(value = "SWIFT Code(BIC)")
    @ExcelProperty(value ="SWIFT Code(BIC)")
    private String shopBic ;
    
    /** Routing number */
    @ApiModelProperty(value = "Routing number")
    @ExcelProperty(value ="Routing number")
    private String shopRoutingNumber ;
    
    /** Bank State Branch(BSB) */
    @ApiModelProperty(value = "Bank State Branch(BSB)")
    @ExcelProperty(value ="Bank State Branch(BSB)")
    private String shopBsb ;
    
    /** Bank Code */
    @ApiModelProperty(value = "Bank Code")
    @ExcelProperty(value ="Bank Code")
    private String shopBankCode ;
    
    /** Branch Code */
    @ApiModelProperty(value = "Branch Code")
    @ExcelProperty(value ="Branch Code")
    private String shopBranchCode ;
    
    /** Account Type */
    @ApiModelProperty(value = "Account Type")
    @ExcelProperty(value ="Account Type")
    private String shopAccountType ;
    
    /** Sort code */
    @ApiModelProperty(value = "Sort code")
    @ExcelProperty(value ="Sort code")
    private String shopSortCode ;
    
    /** IBAN */
    @ApiModelProperty(value = "IBAN")
    @ExcelProperty(value ="IBAN")
    private String shopIban ;

    /** 收款账号任务状态;收款账号任务状态(未完成、完成) */
    @ApiModelProperty(value = "收款账号任务状态")
    @ExcelProperty(value ="收款账号任务状态")
    private String sysApplyDetBctState ;
    
    /** 账户持有人 */
    @ApiModelProperty(value = "账户持有人")
    @ExcelProperty(value ="账户持有人")
    private String shopAccountHoldName ;
    
    /** 店铺扣款信用卡号 */
    @ApiModelProperty(value = "店铺扣款信用卡号")
    @ExcelProperty(value ="店铺扣款信用卡号")
    private String shopAccountNo ;
    
    /** 信用卡开户行 */
    @ApiModelProperty(value = "信用卡开户行")
    @ExcelProperty(value ="信用卡开户行")
    private String shopBankName ;
    
    /** 有效期(开始) */
    @ApiModelProperty(value = "有效期(开始)")
    @ExcelProperty(value ="有效期(开始)")
    private Date shopEffeRangeStart ;
    
    /** 有效期(结束) */
    @ApiModelProperty(value = "有效期(结束)")
    @ExcelProperty(value ="有效期(结束)")
    private Date shopEffeRangeEnd ;
    
    /** 店铺公司地址（中文） */
    @ApiModelProperty(value = "店铺公司地址（中文）")
    @ExcelProperty(value ="店铺公司地址（中文）")
    private String shopComAddrCn ;
    
    /** 法人（中文） */
    @ApiModelProperty(value = "法人（中文）")
    @ExcelProperty(value ="法人（中文）")
    private String shopLegPersonCn ;
    
    /** 公司注册国家 */
    @ApiModelProperty(value = "公司注册国家")
    @ExcelProperty(value ="公司注册国家")
    private String shopComRegCountry ;
    

}