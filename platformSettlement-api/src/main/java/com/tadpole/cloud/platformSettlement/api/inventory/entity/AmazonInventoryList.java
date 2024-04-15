package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
* <p>
* 庫存列表3.0
* </p>
*
* @author S20190161
* @since 2022-12-20
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("AMAZON_INVENTORY_LIST")
@ExcelIgnoreUnannotated
public class AmazonInventoryList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private Long lv;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ExcelProperty("年份")
    @TableField("YEAR")
    private String year;

    @ExcelProperty("月份")
    @TableField("MONTH")
    private String month;

    @ExcelProperty("账号")
    @TableField("SHOP_NAME")
    private String shopName;

    @ExcelProperty("站点")
    @TableField("SITE")
    private String site;

    @ExcelProperty("平台")
    @TableField("PLATFORM")
    private String platform;

    @ExcelProperty("仓库名称")
    @TableField("WAREHOUSE_NAME")
    private String warehouseName;

    @ExcelProperty("库存状态")
    @TableField("INVENTORY_STATUS")
    private String inventoryStatus;

    @ExcelProperty("SKU")
    @TableField("SKU")
    private String sku;

    @ExcelProperty("物料编码")
    @TableField("MATERIAL_CODE")
    private String materialCode;

    @ExcelProperty("事业部")
    @TableField("DEPARTMENT")
    private String department;

    @ExcelProperty("Team")
    @TableField("TEAM")
    private String team;

    @ExcelProperty("销售组织")
    @TableField("SALES_ORG")
    private String salesOrg;

    @ExcelProperty("类目")
    @TableField("CATEGORY")
    private String category;

    @ExcelProperty("运营大类")
    @TableField("PRODUCT_TYPE")
    private String productType;

    @ExcelProperty("产品名称")
    @TableField("PRODUCT_NAME")
    private String productName;

    @ExcelProperty("款式")
    @TableField("STYLE")
    private String style;

    @ExcelProperty("主材料")
    @TableField("MAIN_MATERIAL")
    private String mainMaterial;

    @ExcelProperty("图案")
    @TableField("DESIGN")
    private String design;

    @ExcelProperty("公司品牌")
    @TableField("COMPANY_BRAND")
    private String companyBrand;

    @ExcelProperty("适用品牌或对象")
    @TableField("FIT_BRAND")
    private String fitBrand;

    @ExcelProperty("型号")
    @TableField("MODEL")
    private String model;

    @ExcelProperty("颜色")
    @TableField("COLOR")
    private String color;

    @ExcelProperty("尺码")
    @TableField("SIZES")
    private String sizes;

    @ExcelProperty("包装数量")
    @TableField("PACKING")
    private String packing;

    @ExcelProperty("版本")
    @TableField("VERSION")
    private String version;

    @ExcelProperty("适用机型")
    @TableField("TYPE")
    private String type;

    @ExcelProperty("组合属性")
    @TableField("SPECMODEL")
    private String specmodel;

    @ExcelProperty("首单日期")
    @TableField("FIRST_ORDER_DATE")
    private Date firstOrderDate;

    @ExcelProperty({"期初数量","期初数量合计"})
    @TableField("OPENING_TOTAL")
    private Long openingTotal;

    @ExcelProperty({"期初数量","在途数量"})
    @TableField("OPENING_IN_TRANSIT")
    private Long openingInTransit;

    @ExcelProperty({"期初数量","物流待发数量"})
    @TableField("OPENING_LOGISTICS")
    private Long openingLogistics;

    @ExcelProperty({"期初数量","在库数量"})
    @TableField("OPENING_IN_STOCK")
    private Long openingInStock;

    @ExcelProperty({"入库数量","入库数量合计"})
    @TableField("INBOUND_TOTAL")
    private Long inboundTotal;

    @ExcelProperty({"入库数量","国内发FBA"})
    @TableField("INBOUND_DOMESTIC_FBA")
    private Long inboundDomesticFba;

    @ExcelProperty({"入库数量","欧洲站点调拨"})
    @TableField("INBOUND_EU_TRANSFER")
    private Long inboundEuTransfer;

    @ExcelProperty({"入库数量","海外仓发FBA"})
    @TableField("INBOUND_OW_TO_FBA")
    private Long inboundOwToFba;

    @ExcelProperty({"出库数量","出库数量合计"})
    @TableField("OUTGOING_TOTAL")
    private Long outgoingTotal;

    @ExcelProperty({"出库数量","当月销售数量"})
    @TableField("OUTGOING_MONTHLY_SALES")
    private Long outgoingMonthlySales;

    @ExcelProperty({"出库数量","当月退货数量"})
    @TableField("OUTGOING_MONTHLY_RETURNED")
    private Long outgoingMonthlyReturned;

    @ExcelProperty({"出库数量","当月销毁的数量"})
    @TableField("OUTGOING_MONTHLY_DESTORYED")
    private Long outgoingMonthlyDestoryed;

    @ExcelProperty({"出库数量","当月移除的数量"})
    @TableField("OUTGOING_MONTHLY_REMOVAL")
    private Long outgoingMonthlyRemoval;

    @ExcelProperty({"出库数量","Amazon库存调增"})
    @TableField("OUTGOING_INVENTORY_INCREASE")
    private Long outgoingInventoryIncrease;

    @ExcelProperty({"出库数量","Amazon库存调减"})
    @TableField("OUTGOING_INVENTORY_REDUCTION")
    private Long outgoingInventoryReduction;

    @ExcelProperty("期末数量账面数")
    @TableField("CLOSING_QUANTITY_BOOK")
    private Long closingQuantityBook;

    @ExcelProperty({"期末数量-店铺库存数","店铺库存数合计"})
    @TableField("CLOSING_STORE_INVENTORY_TOTAL")
    private Long closingStoreInventoryTotal;

    @ExcelProperty({"期末数量-店铺库存数","在途数量"})
    @TableField("CLOSING_STORE_INVENTORY_IN_TRANSIT")
    private Long closingStoreInventoryInTransit;

    @ExcelProperty({"期末数量-店铺库存数","物流待发数量"})
    @TableField("CLOSING_STORE_INVENTORY_LOGISTICS")
    private Long closingStoreInventoryLogistics;

    @ExcelProperty({"期末数量-店铺库存数","在库数量"})
    @TableField("CLOSING_STORE_INVENTORY_IN_STOCK")
    private Long closingStoreInventoryInStock;

    @ExcelProperty({"盘盈盘亏数量","盘盈数量"})
    @TableField("INVENTORY_SURPLUS")
    private Long inventorySurplus;

    @ExcelProperty({"盘盈盘亏数量","盘亏数量"})
    @TableField("INVENTORY_LOSS")
    private Long inventoryLoss;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField(exist = false)
    private List<AmazonInventoryListOrg> orgDetails;
}
