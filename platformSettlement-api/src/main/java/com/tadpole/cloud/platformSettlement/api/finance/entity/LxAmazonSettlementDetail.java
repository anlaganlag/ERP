package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 领星Settlement数据
 * </p>
 *
 * @author ty
 * @since 2022-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("LX_AMAZON_SETTLEMENT_DETAIL")
@ApiModel(value="LxAmazonSettlementDetail对象", description="领星Settlement数据")
@ExcelIgnoreUnannotated
public class LxAmazonSettlementDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;


    @ApiModelProperty(value = "结算ID")
    private String settlementId;

    private String transactionType;

    private String orderId;

    private String merchantOrderId;

    private String adjustmentId;

    private String shipmentId;

    private String marketplaceName;

    private String amountType;

    private String amountDescription;

    private BigDecimal amount;

    private String fulfillmentId;

    private Date postedDate;

    private Date postedDateTime;

    private String orderItemCode;

    private String merchantOrderItemId;

    private String merchantAdjustmentItemId;

    private String sku;

    private Long quantityPurchased;

    private Date createTime;

    private Date updateTime;

    private String shopName;

    private String site;
}