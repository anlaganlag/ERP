package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * ERP固定汇率
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
@Data
@ApiModel
public class SettlementDetailListingResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /**
     * 会计期间
     */
    @ApiModelProperty("FISCAL_PERIOD")
    @ExcelProperty(value = "会计期间")
    private String fiscalPeriod;

    /**
     * 结算id
     */
    @ApiModelProperty("SETTLEMENT_ID")
    @ExcelProperty(value = "结算ID")
    private String settlementId;

    /**
     * 报告类型
     */
    @ApiModelProperty("REPORT_TYPE")
    @ExcelProperty(value = "报告类型")
    private String reportType;

    /**
     * 收入确认类型
     */
    @ApiModelProperty("INCOME_TYPE")
    @ExcelProperty(value = "收入确认类型")
    private String incomeType;

    /**
     * 账号
     */
    @ApiModelProperty("SHOP_NAME")
    @ExcelProperty(value = "账号")
    private String shopName;

    /**
     * 站点
     */
    @ApiModelProperty("SITE")
    @ExcelProperty(value = "站点")
    private String site;

    /**
     * 单据编号
     */
    @ApiModelProperty("SKU")
    @ExcelProperty(value = "SKU")
    private String sku;

    /**
     * 原币
     */
    @ApiModelProperty("ORIGINAL_CURRENCY")
    @ExcelProperty(value = "原币")
    private String originalCurrency;

    @ApiModelProperty("DEPARTMENT")
    @ExcelProperty(value = "事业部")
    private String department;

    @ApiModelProperty("TEAM")
    @ExcelProperty(value = "Team")
    private String team;

    @ApiModelProperty("MATERIAL_CODE")
    @ExcelProperty(value = "物料编码")
    private String materialCode;

    @ApiModelProperty("SALES_BRAND")
    @ExcelProperty(value = "销售品牌")
    private String salesBrand;

    @ApiModelProperty("VOLUME_NORMAL")
    @ExcelProperty(value = "Volume(正常发货)")
    private BigDecimal volumeNormal;

    @ApiModelProperty("VOLUME_REISSUE")
    @ExcelProperty(value = "Volume(补发货)")
    private BigDecimal volumeReissue;

    /** sales_total */
    @ApiModelProperty("SALES_EXCLUDING_TAX")
    private BigDecimal salesExcludingTax;

    @ApiModelProperty("TAX")
    private BigDecimal tax;

    /** sales_promotion */
    @ApiModelProperty("SALES_PROMOTION")
    private BigDecimal salesPromotion;

    /** refund_total */
    @ApiModelProperty("REFUND")
    private BigDecimal refund;

    @ApiModelProperty("REFUND_PROMOTION")
    private BigDecimal refundPromotion;

    /** giftwarp_shipping */
    @ApiModelProperty("GIFTWARP_SHIPPING")
    private BigDecimal giftwarpShipping;

    /** commission_total */
    @ApiModelProperty("COMMISSION")
    private BigDecimal commission;

    @ApiModelProperty("REFUND_COMMISSION")
    private BigDecimal refundCommission;

    /** goodwill */
    @ApiModelProperty("GOODWILL")
    private BigDecimal goodwill;

    /** amazon_fee_total非小平台 */
    @ApiModelProperty("AMAZON_FEES")
    private BigDecimal amazonFees;

    /** storage_fee */
    @ApiModelProperty("STORAGE_FEE")
    private BigDecimal storageFee;

    /** reimbursement */
    @ApiModelProperty("REIMBURSEMENT")
    private BigDecimal reimbursement;

    /** other */
    @ApiModelProperty("OTHER")
    private BigDecimal other;

    /** withheld_tax */
    @ApiModelProperty("WITHHELD_TAX")
    private BigDecimal withheldTax;

    /** lightning_deal */
    @ApiModelProperty("REMOVAL_DEAL")
    @ExcelProperty(value = "移除费")
    private BigDecimal removalDeal;

    @ApiModelProperty("DISPOSE_FEE")
    @ExcelProperty(value = "销毁费")
    private BigDecimal disposeFee;

    /** advertising */
    @ApiModelProperty("ADVERTISING")
    private BigDecimal advertising;

    @ApiModelProperty("CONFIRM_DATE")
    @ExcelProperty(value = "确认时间")
    private String confirmDate;

    @ApiModelProperty("CONFIRM_BY")
    @ExcelProperty(value = "确认人员")
    private String confirmBy;

    @ApiModelProperty("CONFIRM_STATUS")
    @ExcelProperty(value = "确认状态")
    private String confirmStatus;

    @ApiModelProperty("CATEGORY")
    @ExcelProperty(value = "类目")
    private String category;

    @ApiModelProperty("PRODUCT_TYPE")
    @ExcelProperty(value = "运营大类")
    private String productType;

    @ApiModelProperty("PRODUCT_NAME")
    @ExcelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty("STYLE")
    @ExcelProperty(value = "款式")
    private String style;

    @ApiModelProperty("MAIN_MATERIAL")
    @ExcelProperty(value = "主材料")
    private String mainMaterial;

    @ApiModelProperty("DESIGN")
    @ExcelProperty(value = "图案")
    private String design;

    @ApiModelProperty("COMPANY_BRAND")
    @ExcelProperty(value = "公司品牌")
    private String companyBrand;

    @ApiModelProperty("FIT_BRAND")
    @ExcelProperty(value = "适用品牌或对象")
    private String fitBrand;

    @ApiModelProperty("MODEL")
    @ExcelProperty(value = "型号")
    private String model;

    @ApiModelProperty("COLOR")
    @ExcelProperty(value = "颜色")
    private String color;

    @ApiModelProperty("SIZES")
    @ExcelProperty(value = "尺码")
    private String sizes;

    @ApiModelProperty("PACKING")
    @ExcelProperty(value = "包装数量")
    private String packing;

    @ApiModelProperty("VERSION")
    @ExcelProperty(value = "版本")
    private String version;

    @ApiModelProperty("TYPE")
    @ExcelProperty(value = "适用机型")
    private String type;

    @ApiModelProperty("BUYER")
    @ExcelProperty(value = "采购员")
    private String buyer;

    @ApiModelProperty("DEVELOPER")
    @ExcelProperty(value = "开发人员")
    private String developer;

    @ApiModelProperty("ACCOUNT_DATE")
    @ExcelProperty(value = "核算日期")
    private String accountDate;

    @ApiModelProperty("CREATE_AT")
    @ExcelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty("LATEST_DATE")
    @ExcelProperty(value = "最晚时间")
    private Date latestDate;

}