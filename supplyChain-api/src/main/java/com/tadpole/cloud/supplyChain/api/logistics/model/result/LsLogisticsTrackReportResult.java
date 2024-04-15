package com.tadpole.cloud.supplyChain.api.logistics.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: ty
 * @description: 物流跟踪报表出参类
 * @date: 2023/12/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LsLogisticsTrackReportResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(value ="发货日期")
    @ApiModelProperty("发货日期")
    private Date shipmentDate;

    @ExcelProperty(value ="平台")
    @ApiModelProperty("平台")
    private String platform;

    @ExcelProperty(value ="账号")
    @ApiModelProperty("账号")
    private String sysShopsName;

    @ExcelProperty(value ="站点")
    @ApiModelProperty("站点")
    private String sysSite;

    @ExcelProperty(value ="合约号")
    @ApiModelProperty("合约号（物流账号）")
    private String lcCode;

    @ExcelProperty(value ="物流商名称")
    @ApiModelProperty("物流商名称")
    private String lpName;

    @ExcelProperty(value ="是否报关")
    @ApiModelProperty("是否报关：是，否")
    private String isCustoms;

    @ExcelProperty(value ="是否包税")
    @ApiModelProperty("是否包税")
    private String hasTax;

    @ExcelProperty(value ="发货方式1")
    @ApiModelProperty("发货方式1")
    private String freightCompany;

    @ExcelProperty(value ="运输方式")
    @ApiModelProperty("运输方式")
    private String transportType;

    @ExcelProperty(value ="物流渠道")
    @ApiModelProperty("物流渠道")
    private String logisticsChannel;

    @ExcelProperty(value ="红蓝单")
    @ApiModelProperty("红蓝单")
    private String orderType;

    @ExcelProperty(value ="发货批次号")
    @ApiModelProperty("发货批次号")
    private String shipmentNum;

    @ExcelProperty(value ="物流单号")
    @ApiModelProperty("物流单号")
    private String logisticsNo;

    @ExcelProperty(value ="发货件数")
    @ApiModelProperty("发货件数（出仓件数）")
    private Long shipmentUnit;

    @ExcelProperty(value ="出仓重量（KG）")
    @ApiModelProperty("出仓重量（KG）")
    private BigDecimal weight;

    @ApiModelProperty("计费量")
    private BigDecimal confirmCountFee;

    @ExcelProperty(value ="预估计费量")
    @ApiModelProperty("预估计费量")
    private BigDecimal predictCountFee;

    @ExcelProperty(value ="时效")
    @ApiModelProperty("时效")
    private Integer diffDay;

    @ExcelProperty(value ="出货清单号")
    @ApiModelProperty("出货清单号")
    private String orderNos;

    @ExcelProperty(value ="ShipmentID")
    @ApiModelProperty("ShipmentID")
    private String shipmentId;

    @ExcelProperty(value ="发货数量")
    @ApiModelProperty("发货数量")
    private Long shipmentQuantity;

    @ExcelProperty(value ="收货仓")
    @ApiModelProperty("收货仓")
    private String receiveWarehouse;

    @ExcelProperty(value ="签收日期")
    @ApiModelProperty("签收日期")
    private Date signDate;

    @ExcelProperty(value ="是否递延")
    @ApiModelProperty("是否递延：是，否")
    private String isDefer;

    @ExcelProperty(value ="发货仓")
    @ApiModelProperty("发货仓")
    private String shipmentWarehouse;

    @ExcelProperty(value ="备注")
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("计费方式")
    private String confirmFeeType;

    @ExcelProperty(value ="预估计费方式")
    @ApiModelProperty("预估计费方式")
    private String predictFeeType;

    @ExcelProperty(value ="预估单价")
    @ApiModelProperty("预估单价")
    private BigDecimal predictUnitPrice;

    @ExcelProperty(value ="预估单价类型")
    @ApiModelProperty("预估单价类型")
    private String predictUnitPriceType;

    @ExcelProperty(value ="预估燃油附加费")
    @ApiModelProperty("预估燃油附加费")
    private BigDecimal predictOilFee;

    @ExcelProperty(value ="预估旺季附加费")
    @ApiModelProperty("预估旺季附加费")
    private BigDecimal predictBusySeasonFee;

    @ExcelProperty(value ="预估附加费及杂费")
    @ApiModelProperty("预估附加费及杂费")
    private BigDecimal predictOthersFee;

    @ExcelProperty(value ="附加费及杂费备注")
    @ApiModelProperty("附加费及杂费备注")
    private String othersFeeRemark;

    @ExcelProperty(value ="预估报关费")
    @ApiModelProperty("预估报关费")
    private BigDecimal predictCustomsFee;

    @ExcelProperty(value ="预估清关费")
    @ApiModelProperty("预估清关费")
    private BigDecimal predictClearCustomsFee;

    @ExcelProperty(value ="预估税费")
    @ApiModelProperty("预估税费")
    private BigDecimal predictTaxFee;

    @ExcelProperty(value ="预估总费用")
    @ApiModelProperty("预估总费用")
    private BigDecimal predictTotalFee;

    @ExcelProperty(value ="总费用")
    @ApiModelProperty("总费用")
    private BigDecimal totalFee;

}
