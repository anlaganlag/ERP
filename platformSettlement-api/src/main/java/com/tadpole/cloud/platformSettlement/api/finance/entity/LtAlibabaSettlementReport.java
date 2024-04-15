package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ;
 *
 * @author : LSY
 * @date : 2023-12-22
 */
@TableName("LT_ALIBABA_SETTLEMENT_REPORT")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LtAlibabaSettlementReport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty("ID")
    @TableField("ID")
    private String id;


    /**
     * 单据编号
     */
    @ApiModelProperty("单据编号")
    @ExcelProperty(value = "单据编号")
    @TableField("BILL_CODE")
    private String billCode;


    /**
     * 新物料代码
     */
    @ApiModelProperty("新物料代码")
    @ExcelProperty(value = "新物料代码")
    @TableField("NEW_MAT_CODE")
    private String newMatCode;

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
     * 公司品牌
     */
    @ApiModelProperty("公司品牌")
    @ExcelProperty(value = "公司品牌")
    @TableField("COMPANY_BRAND")
    private String companyBrand;

    /**
     * 适用品牌或对象
     */
    @ApiModelProperty("适用品牌或对象")
    @ExcelProperty(value = "适用品牌或对象")
    @TableField("FIT_BRAND")
    private String fitBrand;

    /**
     * 型号
     */
    @ApiModelProperty("型号")
    @ExcelProperty(value = "型号")
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
     * 销售品牌
     */
    @ApiModelProperty("销售品牌")
    @ExcelProperty(value = "销售品牌")
    @TableField("SALES_BRAND")
    private String salesBrand;

    /**
     * 订单号
     */
    @ApiModelProperty("订单号")
    @ExcelProperty(value = "订单号")
    @TableField("ORDER_NO")
    private String orderNo;

    /**
     * 数量
     */
    @ApiModelProperty("数量")
    @ExcelProperty(value = "数量")
    @TableField("QTY")
    private BigDecimal qty;


    /**
     * 销售额原币
     */
    @ApiModelProperty("销售额原币")
    @ExcelProperty(value = "销售额原币")
    @TableField("SALE_ORI")
    private BigDecimal saleOri;


    /**
     * 币种
     */
    @ApiModelProperty("币种")
    @ExcelProperty(value = "币种")
    @TableField("CURRENCY")
    private String currency;

    /**
     * 销售额美金
     */
    @ApiModelProperty("销售额美金")
    @ExcelProperty(value = "销售额美金")
    @TableField("SALE_USD")
    private BigDecimal saleUsd;


    /**
     * 手续费
     */
    @ApiModelProperty("手续费")
    @ExcelProperty(value = "手续费")
    @TableField("SERVICE_FEE")
    private BigDecimal serviceFee;

    /**
     * 小包物流费
     */
    @ApiModelProperty("小包物流费")
    @ExcelProperty(value = "小包物流费")
    @TableField("SMALL_PACK_LOG_FEE")
    private BigDecimal smallPackLogFee;

    /**
     * 头程物流费
     */
    @ApiModelProperty("头程物流费")
    @ExcelProperty(value = "头程物流费")
    @TableField("FIRST_TRIP_FEE")
    private BigDecimal firstTripFee;


    /**
     * FBA费用
     */
    @ApiModelProperty("FBA费用")
    @ExcelProperty(value = "FBA费用")
    @TableField("FBA_FEE")
    private BigDecimal fbaFee;

    /**
     * 成本
     */
    @ApiModelProperty("成本")
    @ExcelProperty(value = "成本")
    @TableField("COST")
    private BigDecimal cost;

    /**
     * 成本附加
     */
    @ApiModelProperty("成本附加")
    @ExcelProperty(value = "成本附加")
    @TableField("COST_ADD")
    private BigDecimal costAdd;

    /**
     * 滞销库存利息费
     */
    @ApiModelProperty("滞销库存利息费")
    @ExcelProperty(value = "滞销库存利息费")
    @TableField("DOMESTIC_UNSALABLE_INVENTORY")
    private BigDecimal domesticUnsalableInventory;

    /**
     * 利润
     */
    @ApiModelProperty("利润")
    @ExcelProperty(value = "利润")
    @TableField("PROFIT")
    private BigDecimal profit;

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

    /**
     * 鼓励金
     */
    @ApiModelProperty("鼓励金")
    @ExcelProperty(value = "鼓励金")
    @TableField("INCENTIVE_FUND")
    private BigDecimal incentiveFund;

    /**
     * Period
     */
    @ApiModelProperty("Period")
    @ExcelProperty(value = "Period")
    @TableField("PERIOD")
    private String period;


    /**
     * 事业部
    */
    @ExcelProperty(value = "事业部")
    private String department;

    /**
     * Team
    */
    @ExcelProperty(value = "Team")
    private String team;

    /**
     * 平台
     */
    @ApiModelProperty("平台")
    @ExcelProperty(value = "平台")
    private String platform;


    /**
     * 发货方式
     */
    @ApiModelProperty("发货方式")
    @ExcelProperty(value = "发货方式")
    @TableField("FULFILLMENT")
    private String fulfillment;

    /**
     * 成本单价
     */
    @ApiModelProperty("成本单价")
    @ExcelProperty(value = "成本单价")
    @TableField("COST_UNIT_PRICE")
    private BigDecimal costUnitPrice;

    /**
     * 含税单价
     */
    @ApiModelProperty("含税单价")
    @ExcelProperty(value = "含税单价")
    @TableField("TAX_PRICE")
    private BigDecimal taxPrice;

    /**
     * 客户
     */
    @ApiModelProperty("客户")
    @ExcelProperty(value = "客户")
    @TableField("CUSTOMER")
    private String customer;

    /**
     * 客户年份
     */
    @ApiModelProperty("客户年份")
    @ExcelProperty(value = "客户年份")
    @TableField("CUSTOMER_YEAR")
    private String customerYear;

    /**
     * 新老客户
     */
    @ApiModelProperty("新老客户")
    @ExcelProperty(value = "新老客户")
    @TableField("NEW_OLD_CUSTOMER")
    private String newOldCustomer;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @ExcelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    /**
     * 实收款
     */
    @ApiModelProperty("实收款")
    @ExcelProperty(value = "实收款")
    @TableField("REAL_COLLECTION")
    private BigDecimal realCollection;

    /**
     * 收款账户
     */
    @ApiModelProperty("收款账户")
    @ExcelProperty(value = "收款账户")
    @TableField("COLLECTION_ACCOUNT")
    private String collectionAccount;

    /**
     * 收款日期
     */
    @ApiModelProperty("收款日期")
    @ExcelProperty(value = "收款日期")
    @TableField("COLLECTION_DATE")
    private String collectionDate;

    /**
     * 回款备注
     */
    @ApiModelProperty("回款备注")
    @ExcelProperty(value = "回款备注")
    @TableField("PAYMENT_REMARK")
    private String paymentRemark;

    /**
     * Period_算年份
     */
    @ApiModelProperty("Period-算年份")
    @ExcelProperty(value = "Period-算年份")
    @TableField("PERIOD_CAL_YEAR")
    private String periodCalYear;

    /**
     * 销售员
     */
    @ApiModelProperty("销售员")
    @ExcelProperty(value = "销售员")
    @TableField("SALESMAN")
    private String salesman;


    private Date updateTime;
    private String updateBy;
    private Date createTime;
    private String CreateBy;

    @TableField("CONFIRM_STATUS")
    private BigDecimal confirmStatus;


}