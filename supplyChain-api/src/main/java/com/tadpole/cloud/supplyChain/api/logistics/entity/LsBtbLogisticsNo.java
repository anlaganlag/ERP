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
 *  BTB物流单实体类
 * </p>
 *
 * @author ty
 * @since 2023-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LS_BTB_LOGISTICS_NO")
@ExcelIgnoreUnannotated
public class LsBtbLogisticsNo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 发货批次号 */
    @TableField("SHIPMENT_NUM")
    private String shipmentNum;

    /** 平台 */
    @TableField("PLATFORM")
    private String platform;

    /** 账号 */
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** 站点 */
    @TableField("SYS_SITE")
    private String sysSite;

    /** 发货仓 */
    @TableField("SHIPMENT_WAREHOUSE")
    private String shipmentWarehouse;

    /** 物流单号 */
    @TableField("LOGISTICS_NO")
    private String logisticsNo;

    /** 物流账号 */
    @TableField("LC_CODE")
    private String lcCode;

    /** 物流商编码 */
    @TableField("LP_CODE")
    private String lpCode;

    /** 物流商名称 */
    @TableField("LP_NAME")
    private String lpName;

    /** 物流商简称 */
    @TableField("LP_SIMPLE_NAME")
    private String lpSimpleName;

    /** 发货日期 */
    @TableField("SHIPMENT_DATE")
    private Date shipmentDate;

    /** 签收日期 */
    @TableField("SIGN_DATE")
    private Date signDate;

    /** 签收人 */
    @TableField("SIGN_USER")
    private String signUser;

    /** 是否报关：是，否 */
    @TableField("IS_CUSTOMS")
    private String isCustoms;

    /** 报关公司 */
    @TableField("CUSTOMS_COMPANY")
    private String customsCompany;

    /** 是否递延：是，否 */
    @TableField("IS_DEFER")
    private String isDefer;

    /** 国家分区号 */
    @TableField("LSP_NUM")
    private String lspNum;

    /** 收货分区 */
    @TableField("COUNTRY_AREA_NAME")
    private String countryAreaName;

    /** 收货国家中文名称 */
    @TableField("RECEIVE_COUNTRY_NAME_CN")
    private String receiveCountryNameCn;

    /** 收货国家英文名称 */
    @TableField("RECEIVE_COUNTRY_NAME_EN")
    private String receiveCountryNameEn;

    /** 收货国家编码 */
    @TableField("RECEIVE_COUNTRY_CODE")
    private String receiveCountryCode;

    /** 收件人 */
    @TableField("BUYER_NAME")
    private String buyerName;

    /** 联系电话 */
    @TableField("PHONE1")
    private String phone1;

    /** 城市 */
    @TableField("CITY")
    private String city;

    /** 州/省/郡 */
    @TableField("PROVINCE")
    private String province;

    /** 地址1 */
    @TableField("STREET1")
    private String street1;

    /** 地址2 */
    @TableField("STREET2")
    private String street2;

    /** 邮编 */
    @TableField("POST_CODE")
    private String postCode;

    /** 发货方式1 */
    @TableField("FREIGHT_COMPANY")
    private String freightCompany;

    /** 运输方式 */
    @TableField("TRANSPORT_TYPE")
    private String transportType;

    /** 物流渠道 */
    @TableField("LOGISTICS_CHANNEL")
    private String logisticsChannel;

    /** 红蓝单 */
    @TableField("ORDER_TYPE")
    private String orderType;

    /** 货物特性 */
    @TableField("GOODS_PROPERTY")
    private String goodsProperty;

    /** 计费类型 */
    @TableField("CONFIRM_FEE_TYPE")
    private String confirmFeeType;

    /** 计费量 */
    @TableField("CONFIRM_COUNT_FEE")
    private BigDecimal confirmCountFee;

    /** 物流费币制 */
    @TableField("LOGISTICS_CURRENCY")
    private String logisticsCurrency;

    /** 是否包税 */
    @TableField("HAS_TAX")
    private String hasTax;

    /** 出仓件数 */
    @TableField("SHIPMENT_UNIT")
    private Long shipmentUnit;

    /** 发货数量 */
    @TableField("SHIPMENT_QUANTITY")
    private Long shipmentQuantity;

    /** 出仓重量（KG） */
    @TableField("WEIGHT")
    private BigDecimal weight;

    /** 出仓体积（CBM） */
    @TableField("VOLUME")
    private BigDecimal volume;

    /** 出仓体积重（KG） */
    @TableField("VOLUME_WEIGHT")
    private BigDecimal volumeWeight;

    /** 单价 */
    @TableField("UNIT_PRICE")
    private BigDecimal unitPrice;

    /** 物流费 */
    @TableField("LOGISTICS_FEE")
    private BigDecimal logisticsFee;

    /** 燃油附加费 */
    @TableField("OIL_FEE")
    private BigDecimal oilFee;

    /** 旺季附加费 */
    @TableField("BUSY_SEASON_FEE")
    private BigDecimal busySeasonFee;

    /** 附加费及杂费 */
    @TableField("OTHERS_FEE")
    private BigDecimal othersFee;

    /** 附加费及杂费备注 */
    @TableField("OTHERS_FEE_REMARK")
    private String othersFeeRemark;

    /** 报关费 */
    @TableField("CUSTOMS_FEE")
    private BigDecimal customsFee;

    /** 清关费 */
    @TableField("CLEAR_CUSTOMS_FEE")
    private BigDecimal clearCustomsFee;

    /** 税费 */
    @TableField("TAX_FEE")
    private BigDecimal taxFee;

    /** 备注 */
    @TableField("REMARK")
    private String remark;

    /** 总费用（人工维护） */
    @TableField("TOTAL_FEE")
    private BigDecimal totalFee;

    /** 预估单价 */
    @TableField("PREDICT_UNIT_PRICE")
    private BigDecimal predictUnitPrice;

    /** 预估物流费 */
    @TableField("PREDICT_LOGISTICS_FEE")
    private BigDecimal predictLogisticsFee;

    /** 预估燃油附加费 */
    @TableField("PREDICT_OIL_FEE")
    private BigDecimal predictOilFee;

    /** 预估旺季附加费 */
    @TableField("PREDICT_BUSY_SEASON_FEE")
    private BigDecimal predictBusySeasonFee;

    /** 预估附加费及杂费 */
    @TableField("PREDICT_OTHERS_FEE")
    private BigDecimal predictOthersFee;

    /** 预估报关费 */
    @TableField("PREDICT_CUSTOMS_FEE")
    private BigDecimal predictCustomsFee;

    /** 预估清关费 */
    @TableField("PREDICT_CLEAR_CUSTOMS_FEE")
    private BigDecimal predictClearCustomsFee;

    /** 预估税费 */
    @TableField("PREDICT_TAX_FEE")
    private BigDecimal predictTaxFee;

    /** 预估总费用 */
    @TableField("PREDICT_TOTAL_FEE")
    private BigDecimal predictTotalFee;

    /** 总费用差异 */
    @TableField("DIFF_TOTAL_FEE")
    private BigDecimal diffTotalFee;

    /** 物流状态：已发货，已完结 */
    @TableField("LOGISTICS_STATUS")
    private String logisticsStatus;

    /** 发货类型：物流部发货，业务部发货 */
    @TableField("SHIPMENT_TYPE")
    private String shipmentType;

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

    /** 预估计费类型 */
    @TableField("PREDICT_FEE_TYPE")
    private String predictFeeType;

    /** 预估计费量 */
    @TableField("PREDICT_COUNT_FEE")
    private BigDecimal predictCountFee;

}