package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: ty
 * @description: BTB订单申请发货入参
 * @date: 2023/11/17
 */
@Data
public class BtbPackApplyShipmentParam implements Serializable {


    private static final long serialVersionUID = 1L;

    /** BTB订单申请发货明细 */
    @ApiModelProperty("BTB订单申请发货明细")
    private List<BtbPackApplyShipmentDetailParam> detailList;

    /** 物流单号 */
    @ApiModelProperty("物流单号")
    private String logisticsNo;

    /** 物流账号 */
    @ApiModelProperty("物流账号")
    private String lcCode;

    @ApiModelProperty("物流商编码")
    private String lpCode;

    @ApiModelProperty("物流商名称")
    private String lpName;

    /** 物流商简称 */
    @ApiModelProperty("物流商简称")
    private String lpSimpleName;

    /** 发货日期 */
    @ApiModelProperty("发货日期")
    private Date shipmentDate;

    /** 是否报关：是，否 */
    @ApiModelProperty("是否报关：是，否")
    private String isCustoms;

    /** 报关公司 */
    @ApiModelProperty("报关公司")
    private String customsCompany;

    /** 是否递延：是，否 */
    @ApiModelProperty("是否递延：是，否")
    private String isDefer;

    /** 国家分区号 */
    @ApiModelProperty("国家分区号")
    private String lspNum;

    /** 收货分区 */
    @ApiModelProperty("收货分区")
    private String countryAreaName;

    /** 收货国家中文名称 */
    @ApiModelProperty("收货国家中文名称")
    private String receiveCountryNameCn;

    /** 收货国家英文名称 */
    @ApiModelProperty("收货国家英文名称")
    private String receiveCountryNameEn;

    /** 收货国家编码 */
    @ApiModelProperty("收货国家编码")
    private String receiveCountryCode;

    /** 收件人 */
    @ApiModelProperty("收件人")
    private String buyerName;

    /** 联系电话 */
    @ApiModelProperty("联系电话")
    private String phone1;

    /** 城市 */
    @ApiModelProperty("城市")
    private String city;

    /** 州/省/郡 */
    @ApiModelProperty("州/省/郡")
    private String province;

    /** 地址1 */
    @ApiModelProperty("地址1")
    private String street1;

    /** 地址2 */
    @ApiModelProperty("地址2")
    private String street2;

    /** 邮编 */
    @ApiModelProperty("邮编")
    private String postCode;

    /** 发货方式1 */
    @ApiModelProperty("发货方式1")
    private String freightCompany;

    /** 运输方式 */
    @ApiModelProperty("运输方式")
    private String transportType;

    /** 物流渠道 */
    @ApiModelProperty("物流渠道")
    private String logisticsChannel;

    /** 红蓝单 */
    @ApiModelProperty("红蓝单")
    private String orderType;

    /** 货物特性 */
    @ApiModelProperty("货物特性")
    private String goodsProperty;

    /** 计费类型 */
    @ApiModelProperty("计费类型")
    private String confirmFeeType;

    /** 计费量 */
    @ApiModelProperty("计费量")
    private BigDecimal confirmCountFee;

    /** 物流费币制 */
    @ApiModelProperty("物流费币制")
    private String logisticsCurrency;

    /** 是否包税 */
    @ApiModelProperty("是否包税")
    private String hasTax;

    /** 单价 */
    @ApiModelProperty("单价")
    private BigDecimal unitPrice;

    /** 燃油附加费 */
    @ApiModelProperty("燃油附加费")
    private BigDecimal oilFee;

    /** 旺季附加费 */
    @ApiModelProperty("旺季附加费")
    private BigDecimal busySeasonFee;

    /** 附加费及杂费 */
    @ApiModelProperty("附加费及杂费")
    private BigDecimal othersFee;

    /** 附加费及杂费备注 */
    @ApiModelProperty("附加费及杂费备注")
    private String othersFeeRemark;

    /** 报关费 */
    @ApiModelProperty("报关费")
    private BigDecimal customsFee;

    /** 清关费 */
    @ApiModelProperty("清关费")
    private BigDecimal clearCustomsFee;

    /** 税费 */
    @ApiModelProperty("税费")
    private BigDecimal taxFee;

    /** 备注 */
    @ApiModelProperty("备注")
    private String remark;

    /** 物流费（业务部发货） */
    @ApiModelProperty("物流费（业务部发货）")
    private BigDecimal totalFee;

    /** 发货类型：物流部发货，业务部发货 */
    @ApiModelProperty("发货类型：物流部发货，业务部发货")
    private String shipmentType;

    /** BTB物流单ID */
    @ApiModelProperty("BTB物流单ID")
    private BigDecimal btbLogisticsNoId;

}
