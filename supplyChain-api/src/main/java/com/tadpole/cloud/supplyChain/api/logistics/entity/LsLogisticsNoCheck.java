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
 * 物流单对账 实体类
 * </p>
 *
 * @author ty
 * @since 2023-11-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LS_LOGISTICS_NO_CHECK")
@ExcelIgnoreUnannotated
public class LsLogisticsNoCheck implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 发货批次号 */
    @TableField("SHIPMENT_NUM")
    private String shipmentNum;

    /** 发货日期 */
    @TableField("SHIPMENT_DATE")
    private Date shipmentDate;

    /** 出货仓名称（多个用英文逗号分隔） */
    @TableField("OUT_WAREHOUSE_NAME")
    private String outWarehouseName;

    /** 物流单号 */
    @TableField("LOGISTICS_NO")
    private String logisticsNo;

    /** 平台 */
    @TableField("PLATFORM")
    private String platform;

    /** 账号 */
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** 站点 */
    @TableField("SYS_SITE")
    private String sysSite;

    /** 合约号（物流账号） */
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

    /** 是否报关：是，否 */
    @TableField("IS_CUSTOMS")
    private String isCustoms;

    /** 是否包税：是，否 */
    @TableField("HAS_TAX")
    private String hasTax;

    /** 货运方式1 */
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

    /** 发货件数 */
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

    /** 计费类型 */
    @TableField("CONFIRM_FEE_TYPE")
    private String confirmFeeType;

    /** 计费量 */
    @TableField("CONFIRM_COUNT_FEE")
    private BigDecimal confirmCountFee;

    /** ShipmentID */
    @TableField("SHIPMENT_ID")
    private String shipmentId;

    /** 预估单价（CNY） */
    @TableField("PREDICT_UNIT_PRICE")
    private BigDecimal predictUnitPrice;

    /** 预估物流费（CNY） */
    @TableField("PREDICT_LOGISTICS_FEE")
    private BigDecimal predictLogisticsFee;

    /** 预估燃油附加费（CNY） */
    @TableField("PREDICT_OIL_FEE")
    private BigDecimal predictOilFee;

    /** 预估旺季附加费（CNY） */
    @TableField("PREDICT_BUSY_SEASON_FEE")
    private BigDecimal predictBusySeasonFee;

    /** 预估附加费及杂费（CNY） */
    @TableField("PREDICT_OTHERS_FEE")
    private BigDecimal predictOthersFee;

    /** 预估报关费（CNY） */
    @TableField("PREDICT_CUSTOMS_FEE")
    private BigDecimal predictCustomsFee;

    /** 预估清关费（CNY） */
    @TableField("PREDICT_CLEAR_CUSTOMS_FEE")
    private BigDecimal predictClearCustomsFee;

    /** 预估税费（CNY） */
    @TableField("PREDICT_TAX_FEE")
    private BigDecimal predictTaxFee;

    /** 预估总费用（CNY） */
    @TableField("PREDICT_TOTAL_FEE")
    private BigDecimal predictTotalFee;

    /** 备注 */
    @TableField("REMARK")
    private String remark;

    /** 签收日期 */
    @TableField("SIGN_DATE")
    private Date signDate;

    /** 签收人 */
    @TableField("SIGN_USER")
    private String signUser;

    /** 支付次数 */
    @TableField("PAYMENT_COUNT")
    private Long paymentCount;

    /** 对账状态：对账中，对账完成 */
    @TableField("CHECK_STATUS")
    private String checkStatus;

    /** 锁定状态：锁定，未锁定 */
    @TableField("LOCK_STATUS")
    private String lockStatus;

    /** 总费用差异（CNY） */
    @TableField("DIFF_TOTAL_FEE")
    private BigDecimal diffTotalFee;

    /** 数据来源：EBMS，JCERP */
    @TableField("DATA_SOURCE")
    private String dataSource;

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