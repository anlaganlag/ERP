package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: lsy
 * @description: 物流管理--出货清单对应的清关费用计算请求入参
 * @date: 2023/6/20
 */
@Data
public class TgCustomsClearanceCalcParam extends BaseRequest implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("出货清单号集合packCodeList")
    List<String> packCodeList;

    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 发票号码 */
    @ApiModelProperty("发票号码")
    private String invoiceNo;

    /** 发货公司名称（英文） */
    @ApiModelProperty("发货公司名称（英文）")
    private String postCompanyNameEn;

    /** 发货公司联系方式 */
    @ApiModelProperty("发货公司联系方式")
    private String postContactInfo;

    /** 发货公司联系人 */
    @ApiModelProperty("发货公司联系人")
    private String postContactUser;

    /** 发货公司地址（英文） */
    @ApiModelProperty("发货公司地址（英文）")
    private String postAddrEn;

    /** 收货公司名称 */
    @ApiModelProperty("收货公司名称")
    private String receiveCompanyNameCn;

    /** 收货公司地址 */
    @ApiModelProperty("收货公司地址")
    private String receiveAddrCn;

    /** 收货公司联系人 */
    @ApiModelProperty("收货公司联系人")
    private String receiveContactUser;

    /** 收货公司邮箱 */
    @ApiModelProperty("收货公司邮箱")
    private String receiveEmail;

    /** 收货公司电话 */
    @ApiModelProperty("收货公司电话")
    private String receivePhone;

    /** 进口商公司名称（英文） */
    @ApiModelProperty("进口商公司名称（英文）")
    private String importCompanyNameEn;

    /** 进口商公司地址（英文） */
    @ApiModelProperty("进口商公司地址（英文）")
    private String importAddrEn;

    /** 税号 */
    @ApiModelProperty("税号")
    private String importTaxNum;

    /** 清关号 */
    @ApiModelProperty("清关号")
    private String importCustomsNum;

    /** 运抵国编码 */
    @ApiModelProperty("运抵国编码")
    private String arrivalCountryCode;

    /** 运抵国 */
    @ApiModelProperty("运抵国")
    private String arrivalCountryName;

    /** 是否包税 */
    @ApiModelProperty("是否包税")
    private String importIncludeTax;

    /** 进口商公司联系方式 */
    @ApiModelProperty("进口商公司联系方式")
    private String importContactInfo;

    /** 发货日期 */
    @ApiModelProperty("发货日期")
    private Date postDate;

    /** COUNTRY_OF_EXPORT */
    @ApiModelProperty("COUNTRY_OF_EXPORT")
    private String countryOfExport;

    /** DELIVERY_TERM */
    @ApiModelProperty("DELIVERY_TERM")
    private String deliveryTerm;

    /** PAYMENT_METHORD */
    @ApiModelProperty("PAYMENT_METHORD")
    private String paymentMethord;

    /** 运费 */
    @ApiModelProperty("运费")
    private BigDecimal transportCost;

    /** 保费 */
    @ApiModelProperty("保费")
    private BigDecimal insureCost;

    /** 币制 */
    @ApiModelProperty("币制")
    private String currency;

    /** 数据类型 0：导入，1：关联 */
    @ApiModelProperty("数据类型 0：导入，1：关联")
    private String dataType;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updateUser;

    /** shipmentID */
    @ApiModelProperty("shipmentID")
    private String shipmentID;

    /** 价格类型 */
    @ApiModelProperty("价格类型")
    private String priceType;

    /** 清关折算率 */
    @ApiModelProperty("清关折算率")
    private BigDecimal customsCoeff;

    /** 清关明细集合 */
    @ApiModelProperty("清关明细集合")
    private List<TgCustomsClearanceCalcDetailParam> clearanceDetails;

    /** 报关单编辑导入明细保存是否为新导入数据 0：否，1：是 */
    @ApiModelProperty("报关单编辑导入明细保存是否为新导入数据 0：否，1：是")
    private String importNew;

}
