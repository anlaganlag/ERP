package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: ty
 * @description: 接收EBMS物流单数据入参类
 * @date: 2023/12/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EbmsLogisticsCheckParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "发货日期不能为空")
    @ApiModelProperty(value = "发货日期")
    private Date shipmentDate;

    @ApiModelProperty("出货仓名称（多个用英文逗号分隔）")
    private String outWarehouseName;

    @ApiModelProperty("平台")
    private String platform;

    @NotNull(message = "账号不能为空")
    @ApiModelProperty(value = "账号")
    private String sysShopsName;

    @NotNull(message = "站点不能为空")
    @ApiModelProperty(value = "站点")
    private String sysSite;

    @ApiModelProperty("合约号（物流账号）")
    private String lcCode;

    /** 物流商编码 */
    @ApiModelProperty("物流商编码")
    private String lpCode;

    /** 物流商名称 */
    @ApiModelProperty("物流商名称")
    private String lpName;

    /** 物流商简称 */
    @ApiModelProperty("物流商简称")
    private String lpSimpleName;

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

    @NotNull(message = "发货批次号不能为空")
    @ApiModelProperty(value = "发货批次号")
    private String shipmentNum;

    @ApiModelProperty(value = "物流单号")
    private String logisticsNo;

    @NotNull(message = "发货件数不能为空")
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

    @ApiModelProperty(value = "预估计费类型：重量/体积")
    private String predictFeeType;

    @ApiModelProperty(value = "预估计费量")
    private BigDecimal predictCountFee;

    @ApiModelProperty(value = "shipmentID（多个用都好隔开）")
    private String shipmentId;

    @ApiModelProperty(value = "发货数量")
    private Long shipmentQuantity;

    @NotNull(message = "预估单价不能为空")
    @ApiModelProperty(value = "预估单价（CNY）")
    private BigDecimal predictUnitPrice;

    @NotNull(message = "预估物流费不能为空")
    @ApiModelProperty(value = "预估物流费（CNY）")
    private BigDecimal predictLogisticsFee;

    @NotNull(message = "预估燃油附加费不能为空")
    @ApiModelProperty(value = "预估燃油附加费（CNY）")
    private BigDecimal predictOilFee;

    @NotNull(message = "预估旺季附加费不能为空")
    @ApiModelProperty(value = "预估旺季附加费（CNY）")
    private BigDecimal predictBusySeasonFee;

    @NotNull(message = "预估附加费及杂费不能为空")
    @ApiModelProperty(value = "预估附加费及杂费（CNY）")
    private BigDecimal predictOthersFee;

    @NotNull(message = "预估报关费不能为空")
    @ApiModelProperty("预估报关费（CNY）")
    private BigDecimal predictCustomsFee;

    @NotNull(message = "预估清关费不能为空")
    @ApiModelProperty("预估清关费（CNY）")
    private BigDecimal predictClearCustomsFee;

    @NotNull(message = "预估税费不能为空")
    @ApiModelProperty(value = "预估税费（CNY）")
    private BigDecimal predictTaxFee;

    @ApiModelProperty(value = "预估类型：系统计算/人工维护")
    private String predictType;

    @NotNull(message = "预估总费用不能为空")
    @ApiModelProperty(value = "预估总费用（CNY）")
    private BigDecimal predictTotalFee;

    @NotNull(message = "物流费币制不能为空")
    @ApiModelProperty("物流费币制")
    private String logisticsCurrency;

    /** 单价 */
    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    /** 物流费 */
    @ApiModelProperty("物流费")
    private BigDecimal logisticsFee;
    /** 报关费 */
    @ApiModelProperty("报关费")
    private BigDecimal customsFee;

    /** 清关费 */
    @ApiModelProperty("清关费")
    private BigDecimal clearCustomsFee;

    /** 燃油附加费 */
    @ApiModelProperty("燃油附加费")
    private BigDecimal oilFee;

    /** 旺季附加费 */
    @ApiModelProperty("旺季附加费")
    private BigDecimal busySeasonFee;

    /** 产品附加费（附加费及杂费） */
    @ApiModelProperty("产品附加费（附加费及杂费）")
    private BigDecimal othersFee;

    /** 产品附加费（附加费及杂费）备注 */
    @ApiModelProperty("附加费及杂费备注")
    private String othersFeeRemark;

    /** DUTY/201 */
    @ApiModelProperty("DUTY/201")
    private BigDecimal taxFee;

    /** 合计（CNY） */
    @ApiModelProperty("合计（CNY）")
    private BigDecimal totalFee;

    /** 总费用差异（CNY） */
    @ApiModelProperty("总费用差异（CNY）")
    private BigDecimal diffTotalFee;

    /** 税费币制 */
    @ApiModelProperty("税费币制")
    private String taxCurrency;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    /**
     * 直接汇率
     */
    @ApiModelProperty(hidden = true)
    private BigDecimal directRate;

}
