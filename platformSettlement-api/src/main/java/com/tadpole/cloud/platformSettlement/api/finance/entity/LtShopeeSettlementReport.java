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
@TableName("LT_SHOPEE_SETTLEMENT_REPORT")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LtShopeeSettlementReport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    /**
     * 平台
     */
    @ApiModelProperty("ID")
    @TableField("ID")
    private String id;


    @ApiModelProperty("平台")
    @ExcelProperty(value = "平台")
    private String platform;


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
     * 原币
     */
    @ApiModelProperty("原币")
    @ExcelProperty(value = "原币")
    @TableField("ORIGINAL_CURRENCY")
    private BigDecimal originalCurrency;

    /**
     * 币种
     */
    @ApiModelProperty("币种")
    @ExcelProperty(value = "币种")
    @TableField("CURRENCY")
    private String currency;

    /**
     * 订单金额
     */
    @ApiModelProperty("订单金额")
    @ExcelProperty(value = "订单金额")
    @TableField("ORDER_FEE")
    private BigDecimal orderFee;

    /**
     * 买方运费
     */
    @ApiModelProperty("买方运费")
    @ExcelProperty(value = "买方运费")
    @TableField("BUYER_SHIPPING_FEE")
    private BigDecimal buyerShippingFee;

    /**
     * 店铺优惠券
     */
    @ApiModelProperty("店铺优惠券")
    @ExcelProperty(value = "店铺优惠券")
    @TableField("SHOP_VOUCHER")
    private BigDecimal shopVoucher;

    /**
     * 交易费
     */
    @ApiModelProperty("交易费")
    @ExcelProperty(value = "交易费")
    @TableField("TRANSACTION_FEE")
    private BigDecimal transactionFee;

    /**
     * 佣金
     */
    @ApiModelProperty("佣金")
    @ExcelProperty(value = "佣金")
    @TableField("PLATFORM_FEE")
    private BigDecimal platformFee;

    /**
     * 服务费
     */
    @ApiModelProperty("服务费")
    @ExcelProperty(value = "服务费")
    @TableField("SERVICE_CHARGE")
    private BigDecimal serviceCharge;

    /**
     * 运费总额
     */
    @ApiModelProperty("运费总额")
    @ExcelProperty(value = "运费总额")
    @TableField("SHIPPING_FEE_TOTAL")
    private BigDecimal shippingFeeTotal;

    /**
     * 关税
     */
    @ApiModelProperty("关税")
    @ExcelProperty(value = "关税")
    @TableField("CUSTOMS_RATE")
    private BigDecimal customsRate;

    /**
     * 其他费用
     */
    @ApiModelProperty("其他费用")
    @ExcelProperty(value = "其他费用")
    @TableField("OTHER_FEE")
    private BigDecimal otherFee;

    /**
     * 销毁成本_采购成本
     */
    @ApiModelProperty("销毁成本-采购成本")
    @ExcelProperty(value = "销毁成本-采购成本")
    @TableField("DISPOSE_PURCHASE_FEE")
    private BigDecimal disposePurchaseFee;

    /**
     * 成本_USD
     */
    @ApiModelProperty("成本-USD")
    @ExcelProperty(value = "成本-USD")
    @TableField("COST_USD")
    private BigDecimal costUsd;

    /**
     * 成本附加_USD
     */
    @ApiModelProperty("成本附加-USD")
    @ExcelProperty(value = "成本附加-USD")
    @TableField("COST_ADD_USD")
    private BigDecimal costAddUsd;

    /**
     * 利润
     */
    /**
     * 销量占比
     */
    @ApiModelProperty("利润")
    @ExcelProperty(value = "利润")
    @TableField("PROFIT")
    private BigDecimal profit;



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

    @ApiModelProperty("站点")
    @ExcelProperty(value = "站点")
    @TableField("site")
    private String site;



    @ExcelProperty(value = "事业部")
    private String department;



    @ExcelProperty(value = "Team")
    private String team;


    /**
     * 事业部
     */
    /**
    /**
     * Team
     */
    /**
    /**
     * 单价CNY
     */

    @ApiModelProperty("单价CNY")
    @ExcelProperty(value = "单价CNY")
    @TableField("UNIT_COST_CNY")
    private BigDecimal unitCostCny;

    /**
     * 含税成本单价CNY
     */
    @ApiModelProperty("含税成本单价CNY")
    @ExcelProperty(value = "含税成本单价CNY")
    @TableField("TAX_PRICE_CNY")
    private BigDecimal taxPriceCny;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @ExcelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    /**
     * 拨款金额
     */
    @ApiModelProperty("拨款金额")
    @ExcelProperty(value = "拨款金额")
    @TableField("APPROP_AMOUNT")
    private BigDecimal appropAmount;

    /**
     * 拨款时间
     */
    @ApiModelProperty("拨款时间")
    @ExcelProperty(value = "拨款时间")
    @TableField("APPROP_TIME")
    private String appropTime;

    /**
     * 问题ipaid
     */
    @ApiModelProperty("问题ipaid")
    @ExcelProperty(value = "问题ipaid")
    @TableField("PROBLEM_IPAID")
    private String problemIpaid;

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


    @TableField("CONFIRM_STATUS")
    private BigDecimal confirmStatus;


}