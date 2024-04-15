package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: ty
 * @description: 物流实际结算出参类
 * @date: 2022/11/14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsSettlementResult implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private BigDecimal id;

    @ApiModelProperty(value = "发货日期")
    private String shipmentDate;

    @ApiModelProperty(value = "账号")
    private String shopsName;

    @ApiModelProperty(value = "站点")
    private String site;

    @ApiModelProperty(value = "合约号")
    private String contractNo;

    @ApiModelProperty(value = "是否报关")
    private String isCustoms;

    @ApiModelProperty(value = "是否包税")
    private String hasTax;

    @ApiModelProperty(value = "货运方式1")
    private String freightCompany;

    @ApiModelProperty(value = "运输方式")
    private String transportType;

    @ApiModelProperty(value = "物流渠道")
    private String logisticsChannel;

    @ApiModelProperty(value = "物流单类型：红单/蓝单")
    private String orderType;

    @ApiModelProperty(value = "发货批次号")
    private String shipmentNum;

    @ApiModelProperty(value = "物流单号")
    private String logisticsOrder;

    @ApiModelProperty(value = "发货件数")
    private Long shipmentUnit;

    @ApiModelProperty(value = "出仓重量（KG）")
    private BigDecimal weight;

    @ApiModelProperty(value = "出仓体积（CBM）")
    private BigDecimal volume;

    @ApiModelProperty(value = "出仓体积体重（KG）")
    private BigDecimal volumeWeight;

    @ApiModelProperty(value = "确认计费类型：重量/体积")
    private String confirmFeeType;

    @ApiModelProperty(value = "确认计费量")
    private BigDecimal confirmCountFee;

    @ApiModelProperty(value = "预估使用的计费量")
    private BigDecimal predictCountFee;

    @ApiModelProperty(value = "物流计费重")
    private BigDecimal logisticsCountFee;

    @ApiModelProperty(value = "物流计费量差异")
    private BigDecimal diffCountFee;

    @ApiModelProperty(value = "ShipmentID（多个用都好隔开）")
    private String shipmentId;

    @ApiModelProperty(value = "发货数量")
    private Long shipmentQuantity;

    @ApiModelProperty(value = "预估单价（CNY）")
    private BigDecimal predictUnitPrice;

    @ApiModelProperty(value = "预估物流费（CNY）")
    private BigDecimal predictLogisticsFee;

    @ApiModelProperty(value = "预估燃油附加费（CNY）")
    private BigDecimal predictOilFee;

    @ApiModelProperty(value = "预估旺季附加费（CNY）")
    private BigDecimal predictBusySeasonFee;

    @ApiModelProperty(value = "预估附加费及杂费（CNY）")
    private BigDecimal predictOthersFee;

    @ApiModelProperty(value = "预估报关费（CNY）")
    private BigDecimal predictTariffFee;

    @ApiModelProperty(value = "预估清关费（CNY）")
    private BigDecimal predictClearTariffFee;

    @ApiModelProperty(value = "预估税费（CNY）")
    private BigDecimal predictTaxFee;

    @ApiModelProperty(value = "预估类型：系统计算/人工维护")
    private String predictType;

    @ApiModelProperty(value = "预估总费用（CNY）")
    private BigDecimal predictTotalFee;

    @ApiModelProperty(value = "总差异费用（CNY）")
    private BigDecimal diffTotalFee;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "对账状态：未对账/已对账")
    private String billStatus;

    @ApiModelProperty(value = "操作类型：完成对账/重新对账")
    private String operationType;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "签收日期")
    private String signDate;
}
