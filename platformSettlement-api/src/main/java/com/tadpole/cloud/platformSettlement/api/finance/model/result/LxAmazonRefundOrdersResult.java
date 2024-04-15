package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 亚马逊源报表-退货订单
 * </p>
 *
 * @author cyt
 * @since 2022-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("LX_AMAZON_REFUND_ORDERS")
public class LxAmazonRefundOrdersResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 账号 */
    @ApiModelProperty("账号")
    private String shopName;

    /** 站点 */
    @ApiModelProperty("站点")
    private String site;

    /** 店铺ID */
    @ApiModelProperty("店铺ID")
    private String sid;

    /** 订单号 */
    @ApiModelProperty("订单号")
    private String orderId;

    /** 品名 */
    @ApiModelProperty("品名")
    private String productName;

    /** MSKU */
    @ApiModelProperty("SKU")
    private String sku;

    /** ASIN */
    @ApiModelProperty("ASIN")
    private String asin;

    /** FNSKU */
    @ApiModelProperty("FNSKU")
    private String fnsku;

    /** 数量 */
    @ApiModelProperty("数量")
    private Long quantity;

    /** 退货时间 */
    @ApiModelProperty("退货时间")
    private String returnDate;

    /** 退货站点时间 */
    @ApiModelProperty("退货站点时间")
    private String returnDateLocale;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date gmtModified;

    /** 存储库存的运营中心 */
    @ApiModelProperty("存储库存的运营中心")
    private String fulfillmentCenterId;

    /** 商品状态属性 */
    @ApiModelProperty("商品状态属性")
    private String detailedDisposition;

    /** 退回原因 */
    @ApiModelProperty("退回原因")
    private String reason;

    /** 状态 */
    @ApiModelProperty("状态")
    private String status;

    /** 唯一序列号 */
    @ApiModelProperty("唯一序列号")
    private String licensePlateNumber;

    /** 买家评论 */
    @ApiModelProperty("买家评论")
    private String customerComments;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

}