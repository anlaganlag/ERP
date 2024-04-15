package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: ty
 * @description: 生成清关发票导出-sheet0:清关发票表头
 * @date: 2023/6/28
 */
@Data
public class TgCustomsClearanceExport0Result implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("发货公司名称（英文）")
    private String postCompanyNameEn;

    @ApiModelProperty("发货公司地址（英文）")
    private String postAddrEn;

    @ApiModelProperty("发货公司联系方式")
    private String postContactInfo;

    @ApiModelProperty("发货日期（年月日）")
    private String postDateStr;

    @ApiModelProperty("收货公司名称")
    private String receiveCompanyNameCn;

    @ApiModelProperty("收货公司地址")
    private String receiveAddrCn;

    @ApiModelProperty("收货公司联系人")
    private String receiveContactUser;

    @ApiModelProperty("收货公司邮箱")
    private String receiveEmail;

    @ApiModelProperty("收货公司电话")
    private String receivePhone;

    @ApiModelProperty("COUNTRY_OF_EXPORT")
    private String countryOfExport;

    @ApiModelProperty("进口商公司名称（英文）")
    private String importCompanyNameEn;

    @ApiModelProperty("进口商公司地址（英文）")
    private String importAddrEn;

    @ApiModelProperty("运抵国")
    private String arrivalCountryName;

    @ApiModelProperty("清关号")
    private String importCustomsNum;

    @ApiModelProperty("税号")
    private String importTaxNum;

    @ApiModelProperty("DELIVERY_TERM")
    private String deliveryTerm;

    @ApiModelProperty("PAYMENT_METHORD")
    private String paymentMethord;

    @ApiModelProperty("运费")
    private BigDecimal transportCost;

    @ApiModelProperty("保费")
    private BigDecimal insureCost;

    @ApiModelProperty("shipmentID")
    private String shipmentID;

    @ApiModelProperty("币制")
    private String currency;

    @ApiModelProperty("总价合计")
    private String sumTotalAmountStr;

    @ApiModelProperty("货箱总件数")
    private BigDecimal totalBoxNum;

    @ApiModelProperty("货箱总重量")
    private BigDecimal sumWeight;

    @ApiModelProperty("货箱总重量")
    private String sumWeightStr;

}
