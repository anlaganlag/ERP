package com.tadpole.cloud.platformSettlement.api.inventory.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
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
@ExcelIgnoreUnannotated
@TableName("AMAZON_INVENTORY_LIST")
public class AmazonInventoryListResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private Long lv;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("")
    private String year;

    @ApiModelProperty("月份")
    private String month;

    @ApiModelProperty("平台")
    private String shopName;

    @ApiModelProperty("账号")
    private String site;

    @ApiModelProperty("站点")
    private String platform;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("库存状态")
    private String inventoryStatus;

    @ApiModelProperty("SKU")
    private String sku;

    @ApiModelProperty("物料编码")
    private String materialCode;

    @ApiModelProperty("事业部")
    private String department;

    @ApiModelProperty("Team")
    private String team;

    @ApiModelProperty("销售组织")
    private String salesOrg;

    @ApiModelProperty("类目")
    private String category;

    @ApiModelProperty("运营大类")
    private String productType;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("款式")
    private String style;

    @ApiModelProperty("主材料")
    private String mainMaterial;

    @ApiModelProperty("图案")
    private String design;

    @ApiModelProperty("公司品牌")
    private String companyBrand;

    @ApiModelProperty("适用品牌或对象")
    private String fitBrand;

    @ApiModelProperty("型号")
    private String model;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("尺码")
    private String sizes;

    @ApiModelProperty("包装数量")
    private String packing;

    @ApiModelProperty("版本")
    private String version;

    @ApiModelProperty("适用机型")
    private String type;

    @ApiModelProperty("组合属性")
    private String specmodel;

    @ApiModelProperty("首单日期")
    private Date firstOrderDate;

    @ApiModelProperty("期初数量-期初数量合计")
    private Long openingTotal;

    @ApiModelProperty("期初数量在途数量")
    private Long openingInTransit;

    @ApiModelProperty("期初数量物流待发数量")
    private Long openingLogistics;

    @ApiModelProperty("期初数量在库数量")
    private Long openingInStock;

    @ApiModelProperty("入库数量-入库数量合计")
    private Long inboundTotal;

    @ApiModelProperty("入库数量- 国内发FBA")
    private Long inboundDomesticFba;

    @ApiModelProperty("入库数量-欧洲站点调拨")
    private Long inboundEuTransfer;

    @ApiModelProperty("入库数量-海外仓发FBA")
    private Long inboundOwToFba;

    @ApiModelProperty("出库数量-出库数量合计")
    private Long outgoingTotal;

    @ApiModelProperty("出库数量-当月销售数量")
    private Long outgoingMonthlySales;

    @ApiModelProperty("出库数量-当月退货数量")
    private Long outgoingMonthlyReturned;

    @ApiModelProperty("出库数量-当月销毁的数量")
    private Long outgoingMonthlyDestoryed;

    @ApiModelProperty("出库数量-当月移除的数量")
    private Long outgoingMonthlyRemoval;

    @ApiModelProperty("出库数量-Amazon库存调增")
    private Long outgoingInventoryIncrease;

    @ApiModelProperty("出库数量-Amazon库存调减")
    private Long outgoingInventoryReduction;

    @ApiModelProperty("期末数量账面数")
    private Long closingQuantityBook;

    @ApiModelProperty("期末数量-店铺库存数-店铺库存数合计")
    private Long closingStoreInventoryTotal;

    @ApiModelProperty("期末数量-店铺库存数-在途数量")
    private Long closingStoreInventoryInTransit;

    @ApiModelProperty("期末数量-店铺库存数-物流待发数量")
    private Long closingStoreInventoryLogistics;

    @ApiModelProperty("期末数量-店铺库存数-在库数量")
    private Long closingStoreInventoryInStock;

    @ApiModelProperty("盘盈盘亏数量-盘盈数量")
    private Long inventorySurplus;

    @ApiModelProperty("盘盈盘亏数量-盘亏数量")
    private Long inventoryLoss;

    @ApiModelProperty("")
    private Date createTime;
}
