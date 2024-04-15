package com.tadpole.cloud.supplyChain.api.logistics.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 清关单 实体类
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
@TableName("TG_CUSTOMS_CLEARANCE")
@ExcelIgnoreUnannotated
public class TgCustomsClearance implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 发票号码 */
    @TableField("INVOICE_NO")
    private String invoiceNo;

    /** 发货公司名称（英文） */
    @TableField("POST_COMPANY_NAME_EN")
    private String postCompanyNameEn;

    /** 发货公司联系方式 */
    @TableField("POST_CONTACT_INFO")
    private String postContactInfo;

    /** 发货公司联系人 */
    @TableField("POST_CONTACT_USER")
    private String postContactUser;

    /** 发货公司地址（英文） */
    @TableField("POST_ADDR_EN")
    private String postAddrEn;

    /** 收货公司名称 */
    @TableField("RECEIVE_COMPANY_NAME_CN")
    private String receiveCompanyNameCn;

    /** 收货公司地址 */
    @TableField("RECEIVE_ADDR_CN")
    private String receiveAddrCn;

    /** 收货公司联系人 */
    @TableField("RECEIVE_CONTACT_USER")
    private String receiveContactUser;

    /** 收货公司邮箱 */
    @TableField("RECEIVE_EMAIL")
    private String receiveEmail;

    /** 收货公司电话 */
    @TableField("RECEIVE_PHONE")
    private String receivePhone;

    /** 进口商公司名称（英文） */
    @TableField("IMPORT_COMPANY_NAME_EN")
    private String importCompanyNameEn;

    /** 进口商公司地址（英文） */
    @TableField("IMPORT_ADDR_EN")
    private String importAddrEn;

    /** 税号 */
    @TableField("IMPORT_TAX_NUM")
    private String importTaxNum;

    /** 清关号 */
    @TableField("IMPORT_CUSTOMS_NUM")
    private String importCustomsNum;

    /** 运抵国编码 */
    @TableField("ARRIVAL_COUNTRY_CODE")
    private String arrivalCountryCode;

    /** 运抵国 */
    @TableField("ARRIVAL_COUNTRY_NAME")
    private String arrivalCountryName;

    /** 是否包税 */
    @TableField("IMPORT_INCLUDE_TAX")
    private String importIncludeTax;

    /** 进口商公司联系方式 */
    @TableField("IMPORT_CONTACT_INFO")
    private String importContactInfo;

    /** 发货日期 */
    @TableField("POST_DATE")
    private Date postDate;

    /** COUNTRY_OF_EXPORT */
    @TableField("COUNTRY_OF_EXPORT")
    private String countryOfExport;

    /** DELIVERY_TERM */
    @TableField("DELIVERY_TERM")
    private String deliveryTerm;

    /** PAYMENT_METHORD */
    @TableField("PAYMENT_METHORD")
    private String paymentMethord;

    /** 运费 */
    @TableField("TRANSPORT_COST")
    private BigDecimal transportCost;

    /** 保费 */
    @TableField("INSURE_COST")
    private BigDecimal insureCost;

    /** 币制 */
    @TableField("CURRENCY")
    private String currency;

    /** 数据类型 0：导入，1：关联 */
    @TableField("DATA_TYPE")
    private String dataType;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @TableField("CREATE_USER")
    private String createUser;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @TableField("UPDATE_USER")
    private String updateUser;

    /** shipmentID */
    @TableField("SHIPMENT_ID")
    private String shipmentID;

    /** 价格类型 */
    @TableField("PRICE_TYPE")
    private String priceType;

    /** 清关折算率 */
    @TableField("CUSTOMS_COEFF")
    private BigDecimal customsCoeff;

}