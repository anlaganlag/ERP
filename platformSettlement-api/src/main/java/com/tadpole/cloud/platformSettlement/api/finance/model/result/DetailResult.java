package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
* <p>
* AZ结算报告审核
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@ApiModel
@ExcelIgnoreUnannotated
public class DetailResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CLASSIFICATION_ID")
    @ExcelProperty(value= "ID")
    private BigDecimal classificationId;

    /** 平台 */
    @ApiModelProperty("COST_NAME")
    @ExcelProperty(value= "费用名称")
    private String costName;

    /** 平台 */
    @ApiModelProperty("COST_NAME_CN")
    @ExcelProperty(value= "费用中文名称")
    private String costNameCn;

    /** 平台 */
    @ApiModelProperty("TRANSACTION_TYPE")
    @ExcelProperty(value= "交易类型")
    private String transactionType;

    /** 账号 */
    @ApiModelProperty("AMOUNT_TYPE")
    @ExcelProperty(value= "金额类型")
    private String amountType;

    /** 站点 */
    @ApiModelProperty("AMOUNT_DESCRIPTION")
    @ExcelProperty(value= "费用描述")
    private String amountDescription;

    /** 站点 */
    @ApiModelProperty("SITE")
    @ExcelProperty(value= "站点")
    private String site;

    /** 站点 */
    @ApiModelProperty("FINANCIAL_CLASSIFICATION")
    @ExcelProperty(value= "财务分类")
    private String financialClassification;

    /** 站点 */
    @ApiModelProperty("SUBJECT_NAME")
    @ExcelProperty(value= "科目名称")
    private String subjectName;

    /** 站点 */
    @ApiModelProperty("SUBJECT_CLASSIFICATION_ONE")
    @ExcelProperty(value= "科目分类一")
    private String subjectClassificationOne;

    /** 站点 */
    @ApiModelProperty("SUBJECT_CLASSIFICATION_TWO")
    @ExcelProperty(value= "科目分类二")
    private String subjectClassificationTwo;

    /** 分类类型 */
    @ApiModelProperty("CLASSIFICATION_TYPE")
    @ExcelProperty(value= "分类类型")
    private String classificationType;

    /** 结算id */
    @ApiModelProperty("SETTLEMENT_ID")
    @ExcelProperty(value= "结算单id")
    private String settlementId;

    /** 开始时间 */
    @ApiModelProperty("ORDER_ID")
    @ExcelProperty(value= "ORDER_ID")
    private String orderId;

    /** 结束时间 */
    @ApiModelProperty("ORDER_ITEM_CODE")
    @ExcelProperty(value= "ORDER_ITEM_CODE")
    private String orderItemCode;

    /** 结束时间 */
    @ApiModelProperty("MERCHANT_ORDER_ITEM_ID")
    @ExcelProperty(value= "MERCHANT_ORDER_ITEM_ID")
    private String merchantOrderItemId;

    /** 结束时间 */
    @ApiModelProperty("MERCHANT_ADJUSTMENT_ITEM_ID")
    @ExcelProperty(value= "MERCHANT_ADJUSTMENT_ITEM_ID")
    private String merchantAdjustItemId;

    /** 结束时间 */
    @ApiModelProperty("SKU")
    @ExcelProperty(value= "SKU")
    private String sku;

    /** 结束时间 */
    @ApiModelProperty("QUANTITY_PURCHASED")
    @ExcelProperty(value= "QUANTITY_PURCHASED")
    private String quantityPurchased;

    /** 结束时间 */
    @ApiModelProperty("MERCHANT_ORDER_ID")
    @ExcelProperty(value= "MERCHANT_ORDER_ID")
    private String merchantOrderId;

    /** 结束时间 */
    @ApiModelProperty("ADJUSTMENT_ID")
    @ExcelProperty(value= "ADJUSTMENT_ID")
    private String adjustmentId;

    /** 结束时间 */
    @ApiModelProperty("SHIPMENT_ID")
    @ExcelProperty(value= "SHIPMENT_ID")
    private String shipmentId;

    /** 结束时间 */
    @ApiModelProperty("MARKETPLACE_NAME")
    @ExcelProperty(value= "MARKETPLACE_NAME")
    private String marketplaceName;

    /** 结束时间 */
    @ApiModelProperty("FULFILLMENT_ID")
    @ExcelProperty(value= "FULFILLMENT_ID")
    private String fulfillmentId;

    /** 结束时间 */
    @ApiModelProperty("POSTED_DATE")
    @ExcelProperty(value= "POSTED_DATE")
    private String postedDate;

    /** 结束时间 */
    @ApiModelProperty("PLATFORM")
    @ExcelProperty(value= "PLATFORM")
    private String platform;

    /** 结束时间 */
    @ApiModelProperty("TYPE")
    @ExcelProperty(value= "TYPE")
    private String type;

    /** 结束时间 */
    @ApiModelProperty("AMOUNT")
    @ExcelProperty(value= "AMOUNT")
    private String amount;

    /** DEPOSIT_DATE */
    @ApiModelProperty("DEPOSIT_DATE")
    @ExcelProperty(value= "DEPOSIT_DATE")
    private String depositDate;

    /** SYS_SITE */
    @ApiModelProperty("SYS_SITE")
    @ExcelProperty(value= "SYS_SITE")
    private String sysSite;

    /** SYS_SHOPS_NAME */
    @ApiModelProperty("SYS_SHOPS_NAME")
    @ExcelProperty(value= "SYS_SHOPS_NAME")
    private String sysShopsName;

    /** CURRENCY_CODE */
    @ApiModelProperty("CURRENCY_CODE")
    @ExcelProperty(value= "CURRENCY_CODE")
    private String currencyCode;

    /** AMT_SOURCE */
    @ApiModelProperty("AMT_SOURCE")
    @ExcelProperty(value= "AMT_SOURCE")
    private String amtSource;

    /** TOTAL_AMOUNT */
    @ApiModelProperty("TOTAL_AMOUNT")
    @ExcelProperty(value= "TOTAL_AMOUNT")
    private String totalAmount;

    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 使用状态 */
    @ApiModelProperty("STATUS")
    @ExcelProperty(value= "使用状态")
    private String status;

    /** 编辑状态 */
    @ApiModelProperty("EDIT_STATUS")
    @ExcelProperty(value= "编辑状态")
    private String editStatus;

    /** 审核状态 */
    @ApiModelProperty("VERIFY_STATUS")
    @ExcelProperty(value= "审核状态")
    private String verifyStatus;

}