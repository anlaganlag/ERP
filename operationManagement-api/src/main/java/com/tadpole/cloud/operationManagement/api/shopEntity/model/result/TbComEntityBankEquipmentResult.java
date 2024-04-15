package com.tadpole.cloud.operationManagement.api.shopEntity.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 资源-公司实体银行设备;资源-公司实体银行设备
 * @author : LSY
 * @date : 2023-7-28
 */
@ApiModel(value = "资源-公司实体银行设备",description = "资源-公司实体银行设备")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbComEntityBankEquipmentResult  implements Serializable{
 private static final long serialVersionUID = 1L;
    /** 编号 */
    @ApiModelProperty(value = "编号")
    @ExcelProperty(value ="编号")
    private BigDecimal pkBeCode ;
    
    /** 公司名称（中文） */
    @ApiModelProperty(value = "公司名称（中文）")
    @ExcelProperty(value ="公司名称（中文）")
    private String comNameCn ;


    /** 公司来源 */
    @ApiModelProperty(value = "公司来源")
    @ExcelProperty(value ="公司来源")
    private String comSource;


    /** 公司状态 */
    @ApiModelProperty(value = "公司状态")
    @ExcelProperty(value ="公司状态")
    private String comState ;
    
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @ExcelProperty(value ="创建时间")
    private Date sysCreateTime ;
    
    /** 最后更新时间 */
    @ApiModelProperty(value = "最后更新时间")
    @ExcelProperty(value ="最后更新时间")
    private String busLastUpdDate ;
    
    /** 最后更新人 */
    @ApiModelProperty(value = "最后更新人")
    @ExcelProperty(value ="最后更新人")
    private String busLastUpdPerName ;
    
    /** 最后更新编号 */
    @ApiModelProperty(value = "最后更新编号")
    @ExcelProperty(value ="最后更新编号")
    private String busLastUpdPerCode ;


   /** 结算卡列表 */
   @ApiModelProperty(value = "结算卡列表")
   private List<TbComEntitySettlementCard> settlementCardList ;



   /** 回单卡列表 */
   @ApiModelProperty(value = "回单卡列表")
   private List<TbComEntityReceiptCard> receiptCardList ;


   /** 电子银行客户证书列表 */
   @ApiModelProperty(value = "电子银行客户证书列表")
   private List<TbComEntityEbankCustCertificate> ebankCustCertificateList ;


   /** 密码器列表 */
   @ApiModelProperty(value = "密码器列表")
   private List<TbComEntityCipher> cipherList ;



   /** 银行UKEY-经办列表 */
   @ApiModelProperty(value = "银行UKEY-经办列表")
   private List<TbComEntityUkeyHandle> ukeyHandleList ;



   /** 银行UKEY-授权列表 */
   @ApiModelProperty(value = "银行UKEY-授权列表")
   private List<TbComEntityUkeyAuthorize> ukeyAuthorizeList ;




   /** 税控UKEY列表 */
   @ApiModelProperty(value = "税控UKEY列表")
   private List<TbComEntityTaxControlUkey> taxControlUkeyList ;



   /** 税控盘列表 */
   @ApiModelProperty(value = "税控盘列表")
   private List<TbComEntityTaxControlPanel> taxControlPanelList ;


   /** 口岸卡-法人列表 */
   @ApiModelProperty(value = "口岸卡-法人列表")
   private List<TbComEntityPortCardLegal> portCardLegalList ;


   /** 口岸卡-操作员列表 */
   @ApiModelProperty(value = "口岸卡-操作员列表")
   private List<TbComEntityPortCardOperator> portCardOperatorList ;



   /** 法人CA证书列表 */
   @ApiModelProperty(value = "法人CA证书列表")
   private List<TbComEntityCorporateCaCertificate> corporateCACertificateList ;



   /** 公司数字证书列表 */
   @ApiModelProperty(value = "公司数字证书列表")
   private List<TbComEntityComDigitalCertificate> comDigitalCertificateList ;


    /** 公司法人 */
    @ApiModelProperty(value = "公司法人")
    @ExcelProperty(value ="公司法人")
    private String comLegPerson;

//    /** 结算卡 */
//    @ApiModelProperty(value = "结算卡")
//    @ExcelProperty(value ="结算卡")
//    private Integer busSettlementCard ;
//
//    /** 回单卡 */
//    @ApiModelProperty(value = "回单卡")
//    @ExcelProperty(value ="回单卡")
//    private Integer busReceiptCard ;
//
//    /** 电子银行客户证书 */
//    @ApiModelProperty(value = "电子银行客户证书")
//    @ExcelProperty(value ="电子银行客户证书")
//    private Integer busEbankCustomerCertificate ;
//
//    /** 密码器 */
//    @ApiModelProperty(value = "密码器")
//    @ExcelProperty(value ="密码器")
//    private Integer busCipher ;
//
//    /** 银行UKEY-经办 */
//    @ApiModelProperty(value = "银行UKEY-经办")
//    @ExcelProperty(value ="银行UKEY-经办")
//    private Integer busBankUkeyHandle ;
//
//    /** 银行UKEY-授权 */
//    @ApiModelProperty(value = "银行UKEY-授权")
//    @ExcelProperty(value ="银行UKEY-授权")
//    private Integer busBankUkeyAuthorize ;
//
//    /** 税控UKEY */
//    @ApiModelProperty(value = "税控UKEY")
//    @ExcelProperty(value ="税控UKEY")
//    private Integer busTaxControlUkey ;
//
//    /** 税控盘 */
//    @ApiModelProperty(value = "税控盘")
//    @ExcelProperty(value ="税控盘")
//    private Integer busTaxControlPanel ;
//
//    /** 口岸卡-法人 */
//    @ApiModelProperty(value = "口岸卡-法人")
//    @ExcelProperty(value ="口岸卡-法人")
//    private Integer busPortCardLegal ;
//
//    /** 口岸卡-操作员 */
//    @ApiModelProperty(value = "口岸卡-操作员")
//    @ExcelProperty(value ="口岸卡-操作员")
//    private Integer busPortCardOperator ;
//
//    /** 法人CA证书 */
//    @ApiModelProperty(value = "法人CA证书")
//    @ExcelProperty(value ="法人CA证书")
//    private Integer busCorporateCaCertificate ;
//
//    /** 公司数字证书 */
//    @ApiModelProperty(value = "公司数字证书")
//    @ExcelProperty(value ="公司数字证书")
//    private Integer busComDigitalCertificate ;
    

}