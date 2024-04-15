package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Shopify小平台结算报告;
 *
 * @author : LSY
 * @date : 2023-12-23
 */
@TableName("LT_SHOPIFY_SETTLEMENT_REPORT")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class LtShopifySettlementReport implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    /**
     * 平台
     */

    @ApiModelProperty("id")
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;





    @ApiModelProperty("平台")
    @ExcelProperty(value="平台")
    private String platform;

    /**
     * sku
     */

    @ApiModelProperty("sku")
    @ExcelProperty(value = "SKU")
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
     * 公司品牌
     */

    @ApiModelProperty("公司品牌")
    @ExcelProperty(value = "公司品牌")
    @TableField("COMPANY_BRAND")
    private String companyBrand;

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
     * Volume_正常发货
     */

    @ApiModelProperty("Volume（正常发货）")
    @ExcelProperty(value = "Volume（正常发货）")
    @TableField("VOLUME_NORMAL")
    private BigDecimal volumeNormal;

    /**
     * Sales_excluding_tax
     */

    @ApiModelProperty("Sales_excluding_tax")
    @ExcelProperty(value = "Sales_excluding_tax")
    @TableField("SALES_EXCLUDING_TAX")
    private BigDecimal salesExcludingTax;

    /**
     * Tax
     */

    @ApiModelProperty("Tax")
    @ExcelProperty(value = "Tax")
    @TableField("TAX")
    private BigDecimal tax;

    /**
     * Refund
     */

    @ApiModelProperty("Refund")
    @ExcelProperty(value = "Refund")
    @TableField("REFUND")
    private BigDecimal refund;

    /**
     * Giftwarp_Shipping
     */

    @ApiModelProperty("Giftwarp&Shipping")
    @ExcelProperty(value = "Giftwarp_Shipping")
    @TableField("GIFTWARP_SHIPPING")
    private BigDecimal giftwarpShipping;

    /**
     * Amazon_Fees
     */

    @ApiModelProperty("Amazon Fees")
    @ExcelProperty(value = "Amazon_Fees")
    @TableField("AMAZON_FEES")
    private BigDecimal amazonFees;

    /**
     * 其他站外广告
     */

    @ApiModelProperty("其他站外广告")
    @ExcelProperty(value = "其他站外广告")
    @TableField("OTHER_ADVERTISEMENTS")
    private BigDecimal otherAdvertisements;

    /**
     * Other
     */

    @ApiModelProperty("Other")
    @ExcelProperty(value = "Other")
    @TableField("OTHER")
    private BigDecimal other;

    /**
     * 回款
     */

    @ApiModelProperty("回款")
    @ExcelProperty(value = "回款")
    @TableField("COLLECTION")
    private BigDecimal collection;

    /**
     * 成本核算金额
     */

    @ApiModelProperty("成本核算金额")
    @ExcelProperty(value = "成本核算金额")
    @TableField("COST_ACCOUNTING_FEE")
    private BigDecimal costAccountingFee;

    /**
     * 附加成本金额
     */

    @ApiModelProperty("附加成本金额")
    @ExcelProperty(value = "附加成本金额")
    @TableField("ADDITIONAL_COST_AMOUNT")
    private BigDecimal additionalCostAmount;

    /**
     * 头程物流费
     */

    @ApiModelProperty("头程物流费")
    @ExcelProperty(value = "头程物流费")
    @TableField("FIRST_TRIP_FEE")
    private BigDecimal firstTripFee;

    /**
     * 当地物流费
     */

    @ApiModelProperty("当地物流费")
    @ExcelProperty(value = "当地物流费")
    @TableField("LOCAL_LOGISTICS_FEE")
    private BigDecimal localLogisticsFee;

    /**
     * Profit
     */
    /**
     * 销量占比
     */
    /**
     * 人数
     */
    /**
     * 人工成本
     */

    @ApiModelProperty("Profit")
    @ExcelProperty(value = "Profit")
    @TableField("PROFIT")
    private BigDecimal profit;


    @ApiModelProperty("销量占比")
    @ExcelProperty(value="销量占比")
    @TableField("REVENUE_RATION")
    private BigDecimal revenueRation ;

    /** 人数 */
    @ApiModelProperty("人数")
    @ExcelProperty(value="人数")
    @TableField("PEOPLE_NUM")
    private BigDecimal peopleNum ;

    /** 人工成本 */
    @ApiModelProperty("人工成本")
    @ExcelProperty(value="人工成本")
    @TableField("PEOPLE_COST")
    private BigDecimal peopleCost ;

    /**
     * 站点
     */

    @ApiModelProperty("站点")
    @ExcelProperty(value = "站点")
    @TableField("SITE")
    private String site;

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
    /**
     * 事业部
     */
    /**
     * Team
     */

    @ApiModelProperty("账号")
    @ExcelProperty(value = "账号")
    @TableField("SHOP_NAME")
    private String shopName;

    @ExcelProperty(value="事业部")
    private String department;

    @ExcelProperty(value="Team")
    private String team;



    /**
     * 发货方式
     */

    @ApiModelProperty("发货方式")
    @ExcelProperty(value = "发货方式")
    @TableField("DELIVERY_MODE")
    private String deliveryMode;

    /**
     * 订单号
     */

    @ApiModelProperty("订单号")
    @ExcelProperty(value = "订单号")
    @TableField("ORDER_NUM")
    private String orderNum;

    /**
     * PayPal_Transaction_ID
     */

    @ApiModelProperty("PayPal Transaction ID")
    @ExcelProperty(value = "PayPal_Transaction_ID")
    @TableField("PAYPAL_TRANSACTION_ID")
    private String paypalTransactionId;

    /**
     * 费用类型辅助说明
     */

    @ApiModelProperty("费用类型辅助说明")
    @ExcelProperty(value = "费用类型辅助说明")
    @TableField("COST_AUXILIARY_DESCRIPTION")
    private String costAuxiliaryDescription;




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