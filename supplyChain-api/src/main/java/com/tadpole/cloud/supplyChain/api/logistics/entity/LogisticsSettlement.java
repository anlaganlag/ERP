package com.tadpole.cloud.supplyChain.api.logistics.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: ty
 * @description: 物流实际结算
 * @date: 2022/11/14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LOGISTICS_SETTLEMENT")
public class LogisticsSettlement implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty(value = "发货日期")
    @TableField("SHIPMENT_DATE")
    private Date shipmentDate;

    @ApiModelProperty(value = "账号")
    @TableField("SHOPS_NAME")
    private String shopsName;

    @ApiModelProperty(value = "站点")
    @TableField("SITE")
    private String site;

    @ApiModelProperty(value = "合约号")
    @TableField("CONTRACT_NO")
    private String contractNo;

    @ApiModelProperty(value = "是否报关")
    @TableField("IS_CUSTOMS")
    private String isCustoms;

    @ApiModelProperty(value = "是否包税")
    @TableField("HAS_TAX")
    private String hasTax;

    @ApiModelProperty(value = "货运方式1")
    @TableField("FREIGHT_COMPANY")
    private String freightCompany;

    @ApiModelProperty(value = "运输方式")
    @TableField("TRANSPORT_TYPE")
    private String transportType;

    @ApiModelProperty(value = "物流渠道")
    @TableField("LOGISTICS_CHANNEL")
    private String logisticsChannel;

    @ApiModelProperty(value = "物流单类型：红单/蓝单")
    @TableField("ORDER_TYPE")
    private String orderType;

    @ApiModelProperty(value = "发货批次号")
    @TableField("SHIPMENT_NUM")
    private String shipmentNum;

    @ApiModelProperty(value = "物流单号")
    @TableField("LOGISTICS_ORDER")
    private String logisticsOrder;

    @ApiModelProperty(value = "发货件数")
    @TableField("SHIPMENT_UNIT")
    private Long shipmentUnit;

    @ApiModelProperty(value = "出仓重量（KG）")
    @TableField("WEIGHT")
    private BigDecimal weight;

    @ApiModelProperty(value = "出仓体积（CBM）")
    @TableField("VOLUME")
    private BigDecimal volume;

    @ApiModelProperty(value = "出仓体积体重（KG）")
    @TableField("VOLUME_WEIGHT")
    private BigDecimal volumeWeight;

    @ApiModelProperty(value = "确认计费类型：重量/体积")
    @TableField("CONFIRM_FEE_TYPE")
    private String confirmFeeType;

    @ApiModelProperty(value = "确认计费量")
    @TableField("CONFIRM_COUNT_FEE")
    private BigDecimal confirmCountFee;

    @ApiModelProperty(value = "预估使用的计费量")
    @TableField("PREDICT_COUNT_FEE")
    private BigDecimal predictCountFee;

    @ApiModelProperty(value = "shipmentID（多个用都好隔开）")
    @TableField("SHIPMENT_ID")
    private String shipmentId;

    @ApiModelProperty(value = "发货数量")
    @TableField("SHIPMENT_QUANTITY")
    private Long shipmentQuantity;

    @ApiModelProperty(value = "预估单价（CNY）")
    @TableField("PREDICT_UNIT_PRICE")
    private BigDecimal predictUnitPrice;

    @ApiModelProperty(value = "预估物流费（CNY）")
    @TableField("PREDICT_LOGISTICS_FEE")
    private BigDecimal predictLogisticsFee;

    @ApiModelProperty(value = "预估燃油附加费（CNY）")
    @TableField("PREDICT_OIL_FEE")
    private BigDecimal predictOilFee;

    @ApiModelProperty(value = "预估旺季附加费（CNY）")
    @TableField("PREDICT_BUSY_SEASON_FEE")
    private BigDecimal predictBusySeasonFee;

    @ApiModelProperty(value = "预估附加费及杂费（CNY）")
    @TableField("PREDICT_OTHERS_FEE")
    private BigDecimal predictOthersFee;

    @ApiModelProperty(value = "预估报关费（CNY）")
    @TableField("PREDICT_TARIFF_FEE")
    private BigDecimal predictTariffFee;

    @ApiModelProperty(value = "预估清关费（CNY）")
    @TableField("PREDICT_CLEAR_TARIFF_FEE")
    private BigDecimal predictClearTariffFee;

    @ApiModelProperty(value = "预估税费（CNY）")
    @TableField("PREDICT_TAX_FEE")
    private BigDecimal predictTaxFee;

    @ApiModelProperty(value = "预估类型：系统计算/人工维护")
    @TableField("PREDICT_TYPE")
    private String predictType;

    @ApiModelProperty(value = "预估总费用（CNY）")
    @TableField("PREDICT_TOTAL_FEE")
    private BigDecimal predictTotalFee;

    @ApiModelProperty(value = "对账状态：未对账/已对账")
    @TableField("BILL_STATUS")
    private String billStatus;

    @ApiModelProperty(value = "操作类型：完成对账/重新对账")
    @TableField("OPERATION_TYPE")
    private String operationType;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "币别")
    @TableField("CURRENCY")
    private String currency;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("CREATE_USER")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TIME")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    @TableField("UPDATE_USER")
    private String updateUser;

    @ApiModelProperty(value = "签收日期")
    @TableField("SIGN_DATE")
    private String signDate;
}
