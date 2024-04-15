package com.tadpole.cloud.operationManagement.api.shopEntity.model.result;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BankEquipmentCountResult {

    @ApiModelProperty(value ="公司中文名")
    @ExcelProperty(value ="公司中文名")
    private String comNameCN;
    @ApiModelProperty(value ="状态")
    @ExcelProperty(value ="状态")
    private String comState;
    @ApiModelProperty(value ="结算卡")
    @ExcelProperty(value ="结算卡")
    private Integer busSettlementCardCount;
    @ApiModelProperty(value ="回单卡")
    @ExcelProperty(value ="回单卡")
    private Integer busReceiptCardCount;
    @ApiModelProperty(value ="电子银行客户证书")
    @ExcelProperty(value ="电子银行客户证书")
    private Integer busEbankCustomerCertificateCount;
    @ApiModelProperty(value ="密码器")
    @ExcelProperty(value ="密码器")
    private Integer busCipherCount;
    @ApiModelProperty(value ="银行UKEY-经办")
    @ExcelProperty(value ="银行UKEY-经办")
    private Integer busBankUkeyHandleCount;
    @ApiModelProperty(value ="银行UKEY-授权")
    @ExcelProperty(value ="银行UKEY-授权")
    private Integer busBankUkeyAuthorizeCount;
    @ApiModelProperty(value ="税控UKEY")
    @ExcelProperty(value ="税控UKEY")
    private Integer busTaxControlUkeyCount;
    @ApiModelProperty(value ="税控盘")
    @ExcelProperty(value ="税控盘")
    private Integer busTaxControlPanelCount;
    @ApiModelProperty(value ="口岸卡-法人")
    @ExcelProperty(value ="口岸卡-法人")
    private Integer busPortCardLegalCount;
    @ApiModelProperty(value ="口岸卡-操作员")
    @ExcelProperty(value ="口岸卡-操作员")
    private Integer busPortCardOperatorCount;
    @ApiModelProperty(value ="法人CA证书")
    @ExcelProperty(value ="法人CA证书")
    private Integer busCorporateCACertificateCount;
    @ApiModelProperty(value ="公司数字证书")
    @ExcelProperty(value ="公司数字证书")
    private Integer busComDigitalCertificateCount;
}
