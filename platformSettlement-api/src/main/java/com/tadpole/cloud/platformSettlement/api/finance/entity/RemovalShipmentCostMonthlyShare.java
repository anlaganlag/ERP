package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
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
* 销毁移除成本月分摊表
* </p>
*
* @author ty
* @since 2022-05-19
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("REMOVAL_SHIPMENT_COST_MONTHLY_SHARE")
@ExcelIgnoreUnannotated
public class  RemovalShipmentCostMonthlyShare implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 请求时间 */
    @TableField("REQUEST_DATE")
    @ExcelProperty(value ="请求时间")
    private Date requestDate;

    /** 订单ID */
    @TableField("ORDER_ID")
    @ExcelProperty(value ="订单ID")
    private String orderId;

    /** 开始期间 */
    @TableField("START_DATE")
    @ExcelProperty(value ="开始期间")
    private Date startDate;

    /** 结束期间 */
    @TableField("END_DATE")
    @ExcelProperty(value ="结束期间")
    private Date endDate;

    /** 订单类型 */
    @TableField("ORDER_TYPE")
    @ExcelProperty(value ="订单类型")
    private String orderType;

    /** 账号 */
    @TableField("SYS_SHOPS_NAME")
    @ExcelProperty(value ="账号")
    private String sysShopsName;

    /** 站点 */
    @TableField("SYS_SITE")
    @ExcelProperty(value ="站点")
    private String sysSite;

    /** SKU */
    @TableField("SKU")
    @ExcelProperty(value ="SKU")
    private String sku;

    /** 部门 */
    @TableField("DEPARTMENT")
    @ExcelProperty(value ="部门")
    private String department;

    /** TEAM */
    @TableField("TEAM")
    @ExcelProperty(value ="TEAM")
    private String team;

    /** 物料编码 */
    @TableField("MATERIAL_CODE")
    @ExcelProperty(value ="物料编码")
    private String materialCode;

    /** 币种 */
    @TableField("CURRENCY")
    @ExcelProperty(value ="币种")
    private String currency;

    /** 摊销期 */
    @TableField("SHARE_NUM")
    @ExcelProperty(value ="摊销期")
    private BigDecimal shareNum;

    /** 总分摊额销毁成本-采购成本 */
    @TableField("ALL_SHARE_PURCHASE_COST")
    @ExcelProperty(value ="总分摊额销毁成本-采购成本")
    private BigDecimal allSharePurchaseCost;

    /** 总分摊额销毁成本-头程物流成本 */
    @TableField("ALL_SHARE_LOGISTICS_COST")
    @ExcelProperty(value ="总分摊额销毁成本-头程物流成本")
    private BigDecimal allShareLogisticsCost;

    /** 累计分摊额销毁成本-采购成本 */
    @TableField("ALREADY_SHARE_PURCHASE_COST")
    @ExcelProperty(value ="累计分摊额销毁成本-采购成本")
    private BigDecimal alreadySharePurchaseCost;

    /** 累计分摊额销毁成本-头程物流成本 */
    @TableField("ALREADY_SHARE_LOGISTICS_COST")
    @ExcelProperty(value ="累计分摊额销毁成本-头程物流成本")
    private BigDecimal alreadyShareLogisticsCost;

    /** 剩余分摊额销毁成本-采购成本 */
    @TableField("NOT_SHARE_PURCHASE_COST")
    @ExcelProperty(value ="剩余分摊额销毁成本-采购成本")
    private BigDecimal notSharePurchaseCost;

    /** 剩余分摊额销毁成本-头程物流成本 */
    @TableField("NOT_SHARE_LOGISTICS_COST")
    @ExcelProperty(value ="剩余分摊额销毁成本-头程物流成本")
    private BigDecimal notShareLogisticsCost;

    /** 月摊额销毁成本-采购成本 */
    @TableField("MONTHLY_SHARE_PURCHASE_COST")
    @ExcelProperty(value ="月摊额销毁成本-采购成本")
    private BigDecimal monthlySharePurchaseCost;

    /** 月摊额销毁成本-头程物流成本 */
    @TableField("MONTHLY_SHARE_LOGISTICS_COST")
    @ExcelProperty(value ="月摊额销毁成本-头程物流成本")
    private BigDecimal monthlyShareLogisticsCost;

    /** 本期摊额销毁成本-采购成本 */
    @TableField("NOW_SHARE_PURCHASE_COST")
    @ExcelProperty(value ="本期摊额销毁成本-采购成本")
    private BigDecimal nowSharePurchaseCost;

    /** 本期摊额销毁成本-头程物流成本 */
    @TableField("NOW_SHARE_LOGISTICS_COST")
    @ExcelProperty(value ="本期摊额销毁成本-头程物流成本")
    private BigDecimal nowShareLogisticsCost;

    /** 确认状态 0：未确认，1：已确认 */
    @TableField("CONFIRM_STATUS")
    @ExcelProperty(value ="确认状态 0：未确认，1：已确认，2已作废")
    private String confirmStatus;

    /** 确认人 */
    @TableField("CONFIRM_USER")
    @ExcelProperty(value ="确认人")
    private String confirmUser;

    /** 确认时间 */
    @TableField("CONFIRM_TIME")
    @ExcelProperty(value ="确认时间")
    private Date confirmTime;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    @ExcelProperty(value ="创建时间")
    private Date createTime;

    /** 创建人 */
    @TableField("CREATE_BY")
    @ExcelProperty(value ="创建人")
    private String createBy;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    @ExcelProperty(value ="更新时间")
    private Date updateTime;

    /** 更新人 */
    @TableField("UPDATE_BY")
    @ExcelProperty(value ="更新人")
    private String updateBy;

    /** 运营大类 */
    @TableField("PRODUCT_TYPE")
    @ExcelProperty(value ="运营大类")
    private String productType;

    /** 累计最新分摊年月 */
    @TableField("LAST_SHARE_DATE")
    @ExcelProperty(value ="累计最新分摊年月")
    private String lastShareDate;

    /** 销售品牌 */
    @TableField("SALES_BRAND")
    @ExcelProperty(value ="销售品牌")
    private String salesBrand;

    /** 销毁数量 */
    @TableField("SHIPPED_QUANTITY")
    @ExcelProperty(value ="销毁数量")
    private BigDecimal shippedQuantity;

    /** 采购单价 */
    @TableField("PURCHASE_UNIT_PRICE")
    @ExcelProperty(value ="采购单价")
    private BigDecimal purchaseUnitPrice;

    /** 物流单价 */
    @TableField("LOGISTICS_UNIT_PRICE")
    @ExcelProperty(value ="物流单价")
    private BigDecimal logisticsUnitPrice;

    /** 汇率 */
    @TableField("DIRECT_RATE")
    @ExcelProperty(value ="汇率")
    private BigDecimal directRate;

}