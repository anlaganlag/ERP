package com.tadpole.cloud.platformSettlement.api.finance.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 销量汇总订单数据
 * </p>
 *
 * @author ty
 * @since 2022-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("AMAZON_ORDERS")
@ApiModel(value="AmazonOrders对象", description="销量汇总订单数据")
@ExcelIgnoreUnannotated
public class AmazonOrders implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty(value = "订单号")
    private String amazonOrderId;

    @ApiModelProperty(value = "卖家追踪码")
    private String merchantOrderId;

    @ApiModelProperty(value = "购买时间")
    private Date purchaseDate;

    @ApiModelProperty(value = "上次更新时间")
    private Date lastUpdatedDate;

    @ApiModelProperty(value = "订单状态")
    private String orderStatus;

    @ApiModelProperty(value = "下单渠道")
    private String salesChannel;

    @ApiModelProperty(value = "配送方式")
    private String fulfillmentChannel;

    @ApiModelProperty(value = "买家期望的配送方式")
    private String shipServiceLevel;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "州")
    private String state;

    @ApiModelProperty(value = "邮编")
    private String postalCode;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "是否是公司订单")
    private String isBusinessOrder;

    @ApiModelProperty(value = "是否是更换订单")
    private String isReplacementOrder;

    @ApiModelProperty(value = "入库时间")
    private Date createTime;

    @ApiModelProperty(value = "报告日期")
    private Date uploadDate;

    @ApiModelProperty(value = "商品销售平台")
    private String platform;

    @ApiModelProperty(value = "订单类型")
    private String type;

    @ApiModelProperty(value = "站点")
    private String sysSite;

    @ApiModelProperty(value = "账号简称")
    private String sysShopsName;
}