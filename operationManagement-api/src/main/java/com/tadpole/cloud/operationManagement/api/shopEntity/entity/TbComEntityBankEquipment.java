package com.tadpole.cloud.operationManagement.api.shopEntity.entity;

import java.io.Serializable;
import java.util.Date;
import java.lang.*;
import lombok.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;

 /**
 * @author : LSY
 * @date : 2023-7-28
 * @desc : 资源-公司实体银行设备-资源-公司实体银行设备
 */
@TableName("Tb_Com_Entity_Bank_Equipment")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComEntityBankEquipment implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 编号 */
    @TableId(value = "pk_BE_Code", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    private BigDecimal pkBeCode ;
    
    /** 公司名称（中文） */
    @TableField("com_Name_Cn")
    @ApiModelProperty(value = "公司名称（中文）")
    private String comNameCn ;
    
    /** 创建时间 */
    @TableField("sys_Create_Time")
    @ApiModelProperty(value = "创建时间")
    private Date sysCreateTime ;
    
    /** 最后更新时间 */
    @TableField("bus_Last_Upd_Date")
    @ApiModelProperty(value = "最后更新时间")
    private String busLastUpdDate ;
    
    /** 最后更新人 */
    @TableField("bus_Last_Upd_Per_Name")
    @ApiModelProperty(value = "最后更新人")
    private String busLastUpdPerName ;
    
    /** 最后更新编号 */
    @TableField("bus_Last_Upd_Per_Code")
    @ApiModelProperty(value = "最后更新编号")
    private String busLastUpdPerCode ;
    
    /** 结算卡 */
    @TableField("bus_Settlement_Card")
    @ApiModelProperty(value = "结算卡")
    private BigDecimal busSettlementCard ;
    
    /** 回单卡 */
    @TableField("bus_Receipt_Card")
    @ApiModelProperty(value = "回单卡")
    private BigDecimal busReceiptCard ;
    
    /** 电子银行客户证书 */
    @TableField("bus_Ebank_Customer_Certificate")
    @ApiModelProperty(value = "电子银行客户证书")
    private BigDecimal busEbankCustomerCertificate ;
    
    /** 密码器 */
    @TableField("bus_Cipher")
    @ApiModelProperty(value = "密码器")
    private BigDecimal busCipher ;
    
    /** 银行UKEY-经办 */
    @TableField("bus_Bank_Ukey_Handle")
    @ApiModelProperty(value = "银行UKEY-经办")
    private BigDecimal busBankUkeyHandle ;
    
    /** 银行UKEY-授权 */
    @TableField("bus_Bank_Ukey_Authorize")
    @ApiModelProperty(value = "银行UKEY-授权")
    private BigDecimal busBankUkeyAuthorize ;
    
    /** 税控UKEY */
    @TableField("bus_Tax_Control_Ukey")
    @ApiModelProperty(value = "税控UKEY")
    private BigDecimal busTaxControlUkey ;
    
    /** 税控盘 */
    @TableField("bus_Tax_Control_Panel")
    @ApiModelProperty(value = "税控盘")
    private BigDecimal busTaxControlPanel ;
    
    /** 口岸卡-法人 */
    @TableField("bus_Port_Card_Legal")
    @ApiModelProperty(value = "口岸卡-法人")
    private BigDecimal busPortCardLegal ;
    
    /** 口岸卡-操作员 */
    @TableField("bus_Port_Card_Operator")
    @ApiModelProperty(value = "口岸卡-操作员")
    private BigDecimal busPortCardOperator ;
    
    /** 法人CA证书 */
    @TableField("bus_Corporate_CA_Certificate")
    @ApiModelProperty(value = "法人CA证书")
    private BigDecimal busCorporateCaCertificate ;
    
    /** 公司数字证书 */
    @TableField("bus_Com_Digital_Certificate")
    @ApiModelProperty(value = "公司数字证书")
    private BigDecimal busComDigitalCertificate ;
    

}