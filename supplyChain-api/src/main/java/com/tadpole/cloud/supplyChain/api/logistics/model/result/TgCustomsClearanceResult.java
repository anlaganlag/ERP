package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 清关单 出参类
 * </p>
 *
 * @author ty
 * @since 2023-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("TG_CUSTOMS_CLEARANCE")
public class TgCustomsClearanceResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty("发票号码")
    private String invoiceNo;

    @ApiModelProperty("发货公司名称（英文）")
    private String postCompanyNameEn;

    @ApiModelProperty("发货公司联系方式")
    private String postContactInfo;

    @ApiModelProperty("发货公司联系人")
    private String postContactUser;

    @ApiModelProperty("发货公司地址（英文）")
    private String postAddrEn;

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

    @ApiModelProperty("进口商公司名称（英文）")
    private String importCompanyNameEn;

    @ApiModelProperty("进口商公司地址（英文）")
    private String importAddrEn;

    @ApiModelProperty("税号")
    private String importTaxNum;

    @ApiModelProperty("清关号")
    private String importCustomsNum;

    @ApiModelProperty("运抵国编码")
    private String arrivalCountryCode;

    @ApiModelProperty("运抵国")
    private String arrivalCountryName;

    @ApiModelProperty("是否包税")
    private String importIncludeTax;

    @ApiModelProperty("进口商公司联系方式")
    private String importContactInfo;

    @ApiModelProperty("发货日期")
    private Date postDate;

    @ApiModelProperty("发货日期（年月日）")
    private String postDateStr;

    @ApiModelProperty("COUNTRY_OF_EXPORT")
    private String countryOfExport;

    @ApiModelProperty("DELIVERY_TERM")
    private String deliveryTerm;

    @ApiModelProperty("PAYMENT_METHORD")
    private String paymentMethord;

    @ApiModelProperty("运费")
    private BigDecimal transportCost;

    @ApiModelProperty("保费")
    private BigDecimal insureCost;

    @ApiModelProperty("币制")
    private String currency;

    @ApiModelProperty("数据类型 0：导入，1：关联")
    private String dataType;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

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

}
