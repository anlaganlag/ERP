package com.tadpole.cloud.operationManagement.api.shopEntity.model.result;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 印章统计返回结果
 */
@NoArgsConstructor
@Data
public class CertificateSealCountResult {
    @ApiModelProperty(value ="公司中文名")
    @ExcelProperty(value ="公司中文名")
    private String comNameCN;
    @ApiModelProperty(value ="公司來源")
    @ExcelProperty(value ="公司來源")
    private String comSource;
    @ApiModelProperty(value ="状态")
    @ExcelProperty(value ="状态")
    private String comState;
    @ApiModelProperty(value = "营业执照（正本）")
    @ExcelProperty(value ="营业执照（正本）")
    private BigDecimal busBusinessLicenseOrigCount;
    @ApiModelProperty(value = "营业执照（副本）")
    @ExcelProperty(value ="营业执照（副本）")
    private BigDecimal busBusinessLicenseDuplCount;
    @ApiModelProperty(value = "基本存款账户信息")
    @ExcelProperty(value ="基本存款账户信息")
    private BigDecimal busBasicAccountInfoCount;
    @ApiModelProperty(value = "外经贸备案登记表")
    @ExcelProperty(value ="外经贸备案登记表")
    private BigDecimal busForeignTradeRegistCount;
    @ApiModelProperty(value = "海关注册证书")
    @ExcelProperty(value ="海关注册证书")
    private BigDecimal busCustomsRegistCertificateCount;
    @ApiModelProperty(value = "公章")
    @ExcelProperty(value ="公章")
    private BigDecimal busOfficialSealCount;
    @ApiModelProperty(value = "财务章")
    @ExcelProperty(value ="财务章")
    private BigDecimal busFinancialChapterCount;
    @ApiModelProperty(value = "法人私章")
    @ExcelProperty(value ="法人私章")
    private BigDecimal busLegalPersonSealCount;
    @ApiModelProperty(value = "发票章")
    @ExcelProperty(value ="发票章")
    private BigDecimal busInvoiceSealCount;
    @ApiModelProperty(value = "合同专用章")
    @ExcelProperty(value ="合同专用章")
    private BigDecimal busContractSpecialSealCount;
    @ApiModelProperty(value = "仓库专用章")
    @ExcelProperty(value ="仓库专用章")
    private BigDecimal busWarehouseSpecialSealCount;
}
