package com.tadpole.cloud.operationManagement.api.shopEntity.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;

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
public class TbComEntityBankEquipmentParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
    /** 编号 */
    @ApiModelProperty(value = "编号")
    private BigDecimal pkBeCode ;
    
    /** 公司名称（中文） */
    @ApiModelProperty(value = "公司名称（中文）")
    private String comNameCn ;


    /** 公司来源 */
    @ApiModelProperty(value = "公司来源")
    private String comSource;


    @ApiModelProperty(value = "状态")
    private String comState ;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date sysCreateTime ;
    
    /** 最后更新时间 */
    @ApiModelProperty(value = "最后更新时间")
    private String busLastUpdDate ;
    
    /** 最后更新人 */
    @ApiModelProperty(value = "最后更新人")
    private String busLastUpdPerName ;
    
    /** 最后更新编号 */
    @ApiModelProperty(value = "最后更新编号")
    private String busLastUpdPerCode ;
    
    /** 结算卡 */
    @ApiModelProperty(value = "结算卡")
    private BigDecimal busSettlementCard ;
    
    /** 回单卡 */
    @ApiModelProperty(value = "回单卡")
    private BigDecimal busReceiptCard ;
    
    /** 电子银行客户证书 */
    @ApiModelProperty(value = "电子银行客户证书")
    private BigDecimal busEbankCustomerCertificate ;
    
    /** 密码器 */
    @ApiModelProperty(value = "密码器")
    private BigDecimal busCipher ;
    
    /** 银行UKEY-经办 */
    @ApiModelProperty(value = "银行UKEY-经办")
    private BigDecimal busBankUkeyHandle ;
    
    /** 银行UKEY-授权 */
    @ApiModelProperty(value = "银行UKEY-授权")
    private BigDecimal busBankUkeyAuthorize ;
    
    /** 税控UKEY */
    @ApiModelProperty(value = "税控UKEY")
    private BigDecimal busTaxControlUkey ;
    
    /** 税控盘 */
    @ApiModelProperty(value = "税控盘")
    private BigDecimal busTaxControlPanel ;
    
    /** 口岸卡-法人 */
    @ApiModelProperty(value = "口岸卡-法人")
    private BigDecimal busPortCardLegal ;
    
    /** 口岸卡-操作员 */
    @ApiModelProperty(value = "口岸卡-操作员")
    private BigDecimal busPortCardOperator ;
    
    /** 法人CA证书 */
    @ApiModelProperty(value = "法人CA证书")
    private BigDecimal busCorporateCaCertificate ;
    
    /** 公司数字证书 */
    @ApiModelProperty(value = "公司数字证书")
    private BigDecimal busComDigitalCertificate ;
    

}