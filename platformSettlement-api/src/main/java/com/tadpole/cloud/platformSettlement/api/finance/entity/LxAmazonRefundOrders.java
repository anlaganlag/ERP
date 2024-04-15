package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *  亚马逊源报表-退货订单
 * </p>
 *
 * @author cyt
 * @since 2022-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("LX_AMAZON_REFUND_ORDERS")
public class LxAmazonRefundOrders implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 账号 */
    @TableField("SHOP_NAME")
    private String shopName;

    /** 站点 */
    @TableField("SITE")
    private String site;

    /** 店铺ID */
    @TableField("SID")
    private String sid;

    /** 订单号 */
    @TableField("ORDER_ID")
    private String orderId;

    /** 品名 */
    @TableField("PRODUCT_NAME")
    private String productName;

    /** MSKU */
    @TableField("SKU")
    private String sku;

    /** ASIN */
    @TableField("ASIN")
    private String asin;

    /** FNSKU */
    @TableField("FNSKU")
    private String fnsku;

    /** 数量 */
    @TableField("QUANTITY")
    private Long quantity;

    /** 退货时间 */
    @TableField("RETURN_DATE")
    private String returnDate;

    /** 退货站点时间 */
    @TableField("RETURN_DATE_LOCALE")
    private String returnDateLocale;

    /** 更新时间 */
    @TableField("GMT_MODIFIED")
    private Date gmtModified;

    /** 存储库存的运营中心 */
    @TableField("FULFILLMENT_CENTER_ID")
    private String fulfillmentCenterId;

    /** 商品状态属性 */
    @TableField("DETAILED_DISPOSITION")
    private String detailedDisposition;

    /** 退回原因 */
    @TableField("REASON")
    private String reason;

    /** 状态 */
    @TableField("STATUS")
    private String status;

    /** 唯一序列号 */
    @TableField("LICENSE_PLATE_NUMBER")
    private String licensePlateNumber;

    /** 买家评论 */
    @TableField("CUSTOMER_COMMENTS")
    private String customerComments;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

}