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
@TableName("LT_ALIEXPRESS_SETTLEMENT_REPORT")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LtAliexpressSettlementReport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty("ID")
    @TableField("ID")
    private String id;

    /**
     * 物流跟踪单号
     */
    @ApiModelProperty("平台")
    @ExcelProperty(value = "平台")
    private String platform;


    /**
     * 物流跟踪单号
     */
    @ApiModelProperty("物流跟踪单号")
    @ExcelProperty(value = "物流跟踪单号")
    @TableField("LOGISTICS_TRACK_NO")
    private String logisticsTrackNo;


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
     * 国家
     */
    @ApiModelProperty("国家")
    @ExcelProperty(value = "国家")
    @TableField("COUNTRY")
    private String country;

    /**
     * 订单号
     */
    @ApiModelProperty("订单号")
    @ExcelProperty(value = "订单号")
    @TableField("ORDER_NO")
    private String orderNo;

    /**
     * 销售数量
     */
    @ApiModelProperty("销售数量")
    @ExcelProperty(value = "销售数量")
    @TableField("SALE_QTY")
    private BigDecimal saleQty;

    /**
     * 订单金额
     */
    @ApiModelProperty("订单金额")
    @ExcelProperty(value = "订单金额")
    @TableField("ORDER_AOMOUNT")
    private String orderAomount;

    /**
     * 平台佣金
     */
    @ApiModelProperty("平台佣金")
    @ExcelProperty(value = "平台佣金")
    @TableField("PLATFORM_FEE")
    private BigDecimal platformFee;

    /**
     * 推广联盟佣金
     */
    @ApiModelProperty("推广联盟佣金")
    @ExcelProperty(value = "推广联盟佣金")
    @TableField("PROMOTE_ALLIANCE_FEE")
    private BigDecimal promoteAllianceFee;

    /**
     * 物流费
     */
    @ApiModelProperty("物流费")
    @ExcelProperty(value = "物流费")
    @TableField("LOGISTICS_FEE")
    private BigDecimal logisticsFee;

    /**
     * 成本乘以一
     */
    @ApiModelProperty("成本*1")
    @ExcelProperty(value = "成本*1")
    @TableField("COST1")
    private BigDecimal cost1;

    /**
     * 成本乘以阶梯
     */
    @ApiModelProperty("成本*阶梯")
    @ExcelProperty(value = "成本*阶梯")
    @TableField("COST_STEP")
    private BigDecimal costStep;

    /**
     * 销毁成本_采购成本
     */
    @ApiModelProperty("销毁成本-采购成本")
    @ExcelProperty(value = "销毁成本-采购成本")
    @TableField("DISPOSE_PURCHASE_FEE")
    private BigDecimal disposePurchaseFee;

    /**
     * 滞销库存利息费
     */
    @ApiModelProperty("滞销库存利息费")
    @ExcelProperty(value = "滞销库存利息费")
    @TableField("DOMESTIC_UNSALABLE_INVENTORY")
    private BigDecimal domesticUnsalableInventory;

    /**
     * profit
     */
    @ApiModelProperty("profit")
    @ExcelProperty(value = "profit")
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
     * 出库时间
     */
    @ApiModelProperty("出库时间")
    @ExcelProperty(value = "出库时间")
    @TableField("OUT_TIME")
    private String outTime;

    /**
     * 单位成本_CNY
     */
    @ApiModelProperty("单位成本-CNY")
    @ExcelProperty(value = "单位成本-CNY")
    @TableField("UNIT_COST_CNY")
    private BigDecimal unitCostCny;

    /**
     * 含税单价_CNY
     */
    @ApiModelProperty("含税单价-CNY")
    @ExcelProperty(value = "含税单价-CNY")
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
     * 放款金额
     */
    @ApiModelProperty("放款金额")
    @ExcelProperty(value = "放款金额")
    @TableField("GIVE_AMOUNT")
    private BigDecimal giveAmount;

    /**
     * 回款账户
     */
    @ApiModelProperty("回款账户")
    @ExcelProperty(value = "回款账户")
    @TableField("COLLECT_ACCOUNT")
    private String collectAccount;

    /**
     * 回款币种
     */
    @ApiModelProperty("回款币种")
    @ExcelProperty(value = "回款币种")
    @TableField("PAYMENT_CURRENCY")
    private String paymentCurrency;

    /**
     * 备注2
     */
    @ApiModelProperty("备注2")
    @ExcelProperty(value = "备注2")
    @TableField("REMARK2")
    private String remark2;


    private Date updateTime;
    private String updateBy;
    private Date createTime;
    private String CreateBy;


    @TableField("CONFIRM_STATUS")
    private BigDecimal confirmStatus;


}