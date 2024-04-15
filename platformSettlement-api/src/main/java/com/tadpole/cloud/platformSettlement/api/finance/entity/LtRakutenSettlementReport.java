package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ;
 *
 * @author : LSY
 * @date : 2023-12-22
 */
@TableName("LT_RAKUTEN_SETTLEMENT_REPORT")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LtRakutenSettlementReport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
    @ExcelProperty(value = "id")
    @TableField("ID")
    private String id;

    /**
     * sku
     */
    @ApiModelProperty("sku")
    @ExcelProperty(value = "sku")
    @TableField("SKU")
    private String sku;

    /**
     * 物料编码
     */
    @ApiModelProperty("物料编码")
    @ExcelProperty(value = "物料编码")
    @TableField("MAT_CODE")
    private String matCode;

    /**
     * 类目
     */
    @ApiModelProperty("类目")
    @ExcelProperty(value = "类目")
    @TableField("CATEGORY")
    private String category;

    /**
     * 运营大类
     */
    @ApiModelProperty("运营大类")
    @ExcelProperty(value = "运营大类")
    @TableField("PRODUCT_TYPE")
    private String productType;

    /**
     * 产品名称
     */
    @ApiModelProperty("产品名称")
    @ExcelProperty(value = "产品名称")
    @TableField("PRODUCT_NAME")
    private String productName;

    /**
     * 款式
     */
    @ApiModelProperty("款式")
    @ExcelProperty(value = "款式")
    @TableField("STYLE")
    private String style;

    /**
     * 主材料
     */
    @ApiModelProperty("主材料")
    @ExcelProperty(value = "主材料")
    @TableField("MAIN_MATERIAL")
    private String mainMaterial;

    /**
     * 图案
     */
    @ApiModelProperty("图案")
    @ExcelProperty(value = "图案")
    @TableField("DESIGN")
    private String design;

    /**
     * 适用品牌或对象
     */
    @ApiModelProperty("适用品牌或对象")
    @ExcelProperty(value = "适用品牌或对象")
    @TableField("COMPANY_BRAND")
    private String companyBrand;

    /**
     * 型号
     */
    @ApiModelProperty("型号")
    @ExcelProperty(value = "型号")
    @TableField("FIT_BRAND")
    private String fitBrand;

    /**
     * 公司品牌
     */
    @ApiModelProperty("公司品牌")
    @ExcelProperty(value = "公司品牌")
    @TableField("MODEL")
    private String model;

    /**
     * 颜色
     */
    @ApiModelProperty("颜色")
    @ExcelProperty(value = "颜色")
    @TableField("COLOR")
    private String color;

    /**
     * 尺码
     */
    @ApiModelProperty("尺码")
    @ExcelProperty(value = "尺码")
    @TableField("SIZES")
    private String sizes;

    /**
     * 包装数量
     */
    @ApiModelProperty("包装数量")
    @ExcelProperty(value = "包装数量")
    @TableField("PACKING")
    private String packing;

    /**
     * 版本
     */
    @ApiModelProperty("版本")
    @ExcelProperty(value = "版本")
    @TableField("VERSION")
    private String version;

    /**
     * 适用机型
     */
    @ApiModelProperty("适用机型")
    @ExcelProperty(value = "适用机型")
    @TableField("TYPE")
    private String type;

    /**
     * 款式二级标签
     */
    @ApiModelProperty("款式二级标签")
    @ExcelProperty(value = "款式二级标签")
    @TableField("STYLE_SECOND_LABEL")
    private String styleSecondLabel;

    /**
     * 组合属性
     */
    @ApiModelProperty("组合属性")
    @ExcelProperty(value = "组合属性")
    @TableField("PROPERTY_MERGE")
    private String propertyMerge;

    /**
     * 销售品牌
     */
    @ApiModelProperty("销售品牌")
    @ExcelProperty(value = "销售品牌")
    @TableField("SALES_BRAND")
    private String salesBrand;

    /**
     * 个数
     */
    @ApiModelProperty("个数")
    @ExcelProperty(value = "个数")
    @TableField("QTY")
    private BigDecimal qty;

    /**
     * 测评数量
     */
    @ApiModelProperty("测评数量")
    @ExcelProperty(value = "测评数量")
    @TableField("VOLUME_BILL_QUANTITY")
    private BigDecimal volumeBillQuantity;

    /**
     * 运费
     */
    @ApiModelProperty("运费")
    @ExcelProperty(value = "运费")
    @TableField("FREIGHT")
    private BigDecimal freight;

    /**
     * 不含税销售额
     */
    @ApiModelProperty("不含税销售额")
    @ExcelProperty(value = "不含税销售额")
    @TableField("TAX_FREE_SALE")
    private BigDecimal taxFreeSale;

    /**
     * 税额
     */
    @ApiModelProperty("税额")
    @ExcelProperty(value = "税额")
    @TableField("TAX")
    private BigDecimal tax;

    /**
     * 订单金额（含税）
     */
    @ApiModelProperty("订单金额（含税）")
    @ExcelProperty(value = "订单金额（含税）")
    @TableField("ORDER_FEE_IN_TAX")
    private BigDecimal orderFeeInTax;

    /**
     * Refund
     */
    @ApiModelProperty("Refund")
    @ExcelProperty(value = "Refund")
    @TableField("REFUND")
    private BigDecimal refund;

    /**
     * 乐天费用
     */
    @ApiModelProperty("乐天费用")
    @ExcelProperty(value = "乐天费用")
    @TableField("RATENKU_FEE")
    private BigDecimal ratenkuFee;

    /**
     * 积分费用
     */
    @ApiModelProperty("积分费用")
    @ExcelProperty(value = "积分费用")
    @TableField("CREDIT_FEE")
    private BigDecimal creditFee;

    /**
     * FBA费用
     */
    @ApiModelProperty("FBA费用")
    @ExcelProperty(value = "FBA费用")
    @TableField("FBA_FEE")
    private BigDecimal fbaFee;

    /**
     * 广告费
     */
    @ApiModelProperty("广告费")
    @ExcelProperty(value = "广告费")
    @TableField("ADVERTISING")
    private BigDecimal advertising;

    /**
     * 测评货值
     */
    @ApiModelProperty("测评货值")
    @ExcelProperty(value = "测评货值")
    @TableField("BRUSHING_VALUE")
    private BigDecimal brushingValue;

    /**
     * 测评手续费
     */
    @ApiModelProperty("测评手续费")
    @ExcelProperty(value = "测评手续费")
    @TableField("BRUSHING_SERVICE_CHARGE")
    private BigDecimal brushingServiceCharge;

    /**
     * Cost原始
     */
    @ApiModelProperty("Cost原始")
    @ExcelProperty(value = "Cost原始")
    @TableField("COST_ORI")
    private BigDecimal costOri;

    /**
     * 成本附加
     */
    @ApiModelProperty("成本附加")
    @ExcelProperty(value = "成本附加")
    @TableField("COST_ADD")
    private BigDecimal costAdd;

    /**
     * 物流
     */
    @ApiModelProperty("物流")
    @ExcelProperty(value = "物流")
    @TableField("LOGISTICS")
    private BigDecimal logistics;

    /**
     * 海外仓发货费用
     */
    @ApiModelProperty("海外仓发货费用")
    @ExcelProperty(value = "海外仓发货费用")
    @TableField("OVERSEA_WARE_DELI_FEE")
    private BigDecimal overseaWareDeliFee;

    /**
     * Storage_fee
     */
    @ApiModelProperty("Storage_fee")
    @ExcelProperty(value = "Storage_fee")
    @TableField("STORAGE_FEE")
    private BigDecimal storageFee;

    /**
     * 销毁费
     */
    @ApiModelProperty("销毁费")
    @ExcelProperty(value = "销毁费")
    @TableField("DISPOSE_FEE")
    private BigDecimal disposeFee;

    /**
     * 销毁成本-采购成本
     */
    @ApiModelProperty("销毁成本-采购成本")
    @ExcelProperty(value = "销毁成本-采购成本")
    @TableField("DISPOSE_PURCHASE_FEE")
    private BigDecimal disposePurchaseFee;

    /**
     * 销毁成本-头程物流成本
     */
    @ApiModelProperty("销毁成本-头程物流成本")
    @ExcelProperty(value = "销毁成本-头程物流成本")
    @TableField("DISPOSE_LOGISTICS_FEE")
    private BigDecimal disposeLogisticsFee;

    /**
     * 滞销库存利息费
     */
    @ApiModelProperty("滞销库存利息费")
    @ExcelProperty(value = "滞销库存利息费")
    @TableField("DOMESTIC_UNSALABLE_INVENTORY")
    private BigDecimal domesticUnsalableInventory;

    /**
     * Profit
     */
    @ApiModelProperty("Profit")
    @ExcelProperty(value = "Profit")
    @TableField("PROFIT")
    private BigDecimal profit;

    /**
     * 鼓励金
     */
    @ApiModelProperty("鼓励金")
    @ExcelProperty(value = "鼓励金")
    @TableField("INCENTIVE_FUND")
    private BigDecimal incentiveFund;

    /**
     * period
     */
    @ApiModelProperty("period")
    @ExcelProperty(value = "period")
    @TableField("PERIOD")
    private String period;

    /**
     * 账号
     */
    @ApiModelProperty("账号")
    @ExcelProperty(value = "账号")
    @TableField("SHOP_NAME")
    private String shopName;

    /**
     * 采购员
     */
    @ApiModelProperty("采购员")
    @ExcelProperty(value = "采购员")
    @TableField("BUYER")
    private String buyer;

    /**
     * 开发人员
     */
    @ApiModelProperty("开发人员")
    @ExcelProperty(value = "开发人员")
    @TableField("DEVELOPER")
    private String developer;

    /**
     * 最早下单日期
     */
    @ApiModelProperty("最早下单日期")
    @ExcelProperty(value = "最早下单日期")
    @TableField("FIRST_ORDER_DATE")
    private String firstOrderDate;

    /**
     * 事业部
     */
    @ApiModelProperty("事业部")
    @ExcelProperty(value = "事业部")
    @TableField("DEPARTMENT")
    private String department;

    /**
     * Team
     */
    @ApiModelProperty("Team")
    @ExcelProperty(value = "Team")
    @TableField("TEAM")
    private String team;

    /**
     * 是否新品
     */
    @ApiModelProperty("是否新品")
    @ExcelProperty(value = "是否新品")
    @TableField("IS_NEW")
    private String isNew;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @ExcelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    /**
     *
     */
    @ApiModelProperty("updateTime")
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /**
     *
     */
    @ApiModelProperty("updateBy")
    @TableField("UPDATE_BY")
    private String updateBy;

    /**
     *
     */
    @ApiModelProperty("createTime")
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     *
     */
    @ApiModelProperty("createBy")
    @TableField("CREATE_BY")
    private String createBy;

    /**
     * 销量占比
     */
    @ApiModelProperty("销量占比")
    @ExcelProperty(value = "销量占比")
    @TableField("REVENUE_RATION")
    private BigDecimal revenueRation;

    /**
     * 人数
     */
    @ApiModelProperty("人数")
    @ExcelProperty(value = "人数")
    @TableField("PEOPLE_NUM")
    private BigDecimal peopleNum;

    /**
     * 人工成本
     */
    @ApiModelProperty("人工成本")
    @ExcelProperty(value = "人工成本")
    @TableField("PEOPLE_COST")
    private BigDecimal peopleCost;

    @TableField("CONFIRM_STATUS")
    private BigDecimal confirmStatus;

    @ExcelProperty(value = "平台")
    private String platform;


}