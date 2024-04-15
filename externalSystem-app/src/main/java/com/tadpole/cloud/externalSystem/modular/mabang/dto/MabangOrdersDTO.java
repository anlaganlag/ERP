package com.tadpole.cloud.externalSystem.modular.mabang.dto;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 马帮订单列表生成销售出库
 * </p>
 *
 * @author cyt
 * @since 2022-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class MabangOrdersDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableField("ID")
    private String id;

    /**
     * 订单编号
     */
    @TableField("PLATFORM_ORDER_ID")
    private String platformOrderId;

    /**
     * 订单状态 2.配货中 3.已发货 4.已完成 5.已作废
     */
    @TableField("ORDER_STATUS")
    private String orderStatus;

    /**
     * 店铺编号
     */
    @TableField("SHOP_ID")
    private String shopId;

    /**
     * MCMS含义:国家简码（波黑为3字码，其余二字码）-----马帮含义:国家二字码
     */
    @TableField("COUNTRY_CODE")
    private String countryCode;


    /**
     * MCMS含义:平台编码-----马帮含义:来源平台
     */
    @TableField("PLATFORM_ID")
    private String platformId;

    /**
     * 发货时间
     */
    @TableField("EXPRESS_TIME")
    private Date expressTime;

    /**
     * MCMS含义:城市名称-----马帮含义:买家城市
     */
    @TableField("CITY")
    private String city;

    /**
     * 币种
     */
    @TableField("CURRENCY_ID")
    private String currencyId;

    /**
     * MCMS含义:币种汇率-----马帮含义:汇率
     */
    @TableField("CURRENCY_RATE")
    private BigDecimal currencyRate;

    /**
     * 店铺名称
     */
    @TableField("SHOP_NAME")
    private String shopName;

    /**
     * MCMS含义:马帮ERP系统订单编号-----马帮含义:ERP系统订单编号   注意：字段首字母大写，字段赋值可能为空
     */
    @TableField("ERP_ORDER_ID")
    private String erpOrderId;

    @TableField("PLAT_ORD_ID")
    private String platOrdId;


    /**
     * 是否创建跨组织调拨单 （0 ：默认0未创建,1：已创建 2:海外仓 3.空单身 4.海外仓且空单身 5.非限定平台)
     */
    @TableField("CREATE_CROSS_TRANSFER")
    private BigDecimal createCrossTransfer;

    /**
     * 是否创建销售出库单（0 ：默认0未创建,1：已创建,5：非限定平台)
     */
    @TableField("CREATE_SALE_OUT_ORDER")
    private BigDecimal createSaleOutOrder;

    /**
     * MCMS含义:商品编号(从平台抓取的商品编号)---马帮含义:马帮订单商品编号
     */
    @TableField("ORDER_ITEM_ID")
    private String orderItemId;

    /**
     * MCMS含义:马帮商品编号(马帮表中的商品编号ID)---马帮含义:马帮订单商品编号
     */
    @TableField("ERP_ORDER_ITEM_ID")
    private String erpOrderItemId;

    /**
     * MCMS含义:ERP系统关联订单编号，对应订单信息中的erpOrderId(主外键)---马帮含义:ERP系统关联订单编号，对应订单信息中的erpOrderId
     */
    @TableField("ORIGIN_ORDER_ID")
    private String originOrderId;

    /**
     * MCMS含义:商品数量(从平台拉取的商品数量)---马帮含义:商品数量
     */
    @TableField("PLATFORM_QUANTITY")
    private BigDecimal platformQuantity;

    /**
     * MCMS含义:平台SKU-----马帮含义:平台sku
     */
    @TableField("PLATFORM_SKU")
    private String platformSku;


    /**
     * MCMS含义:商品数量(马帮ERP系统生成的商品数量，正常情况下与platformQuantity字段值是一样的)---马帮含义:商品数量
     */
    @TableField("QUANTITY")
    private BigDecimal quantity;

    /**
     * MCMS含义:库存sku-----马帮含义:库存sku
     */
    @TableField("STOCK_SKU")
    private String stockSku;


    /**
     * MCMS含义:商品仓库编号-----马帮含义:商品仓库编号
     */
    @TableField("STOCK_WAREHOUSE_ID")
    private String stockWarehouseId;


    /**
     * MCMS含义:商品仓库名称-----马帮含义:商品仓库名称
     */
    @TableField("STOCK_WAREHOUSE_NAME")
    private String stockWarehouseName;

    /** 金蝶组织编码:马帮ERP中完整带小尾巴的金蝶组织编码 */
    @TableField("FINANCE_CODE")
    private String financeCode;

    /** 马帮店铺原始财务编码-拆分出K3财务编码 */
    @TableField("ORIGINAL_FINANCE_CODE")
    private String originalFinanceCode;

    /** 年份 */
    @TableField("YEAR")
    private String year;

    /** 月份 */
    @TableField("MONTH")
    private String month;

    /**
     * 马帮订单拉取回来，在mcms系统的订单明细数据ID   MABANG_ORDER_ITEM表的ID字段
     */
    @TableField("ORDER_DETAIL_ID")
    private String OrderDetailId;

}