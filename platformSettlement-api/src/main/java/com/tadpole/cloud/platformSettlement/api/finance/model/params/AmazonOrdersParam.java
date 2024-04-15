package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: ty
 * @description: 销量汇总订单数据请求参数
 * @date: 2022/5/6
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class AmazonOrdersParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 订单号 */
    @ApiModelProperty("订单号")
    private String amazonOrderId;

    /** 卖家追踪码 */
    @ApiModelProperty("卖家追踪码")
    private String merchantOrderId;

    /** 购买时间 */
    @ApiModelProperty("购买时间")
    private Date purchaseDate;

    /** 上次更新时间 */
    @ApiModelProperty("上次更新时间")
    private Date lastUpdatedDate;

    /** 订单状态 */
    @ApiModelProperty("订单状态")
    private String orderStatus;

    /** 下单渠道 */
    @ApiModelProperty("下单渠道")
    private String salesChannel;

    /** 配送方式 */
    @ApiModelProperty("配送方式")
    private String fulfillmentChannel;

    /** 买家期望的配送方式 */
    @ApiModelProperty("买家期望的配送方式")
    private String shipServiceLevel;

    /** 城市 */
    @ApiModelProperty("城市")
    private String city;

    /** 州 */
    @ApiModelProperty("州")
    private String state;

    /** 邮编 */
    @ApiModelProperty("邮编")
    private String postalCode;

    /** 国家 */
    @ApiModelProperty("国家")
    private String country;

    /** 是否是公司订单 */
    @ApiModelProperty("是否是公司订单")
    private String isBusinessOrder;

    /** 是否是更换订单 */
    @ApiModelProperty("是否是更换订单")
    private String isReplacementOrder;

    /** 入库时间 */
    @ApiModelProperty("入库时间")
    private Date createTime;

    /** 报告日期 */
    @ApiModelProperty("报告日期")
    private Date uploadDate;

    /** 商品销售平台 */
    @ApiModelProperty("商品销售平台")
    private String platform;

    /** 订单类型 */
    @ApiModelProperty("订单类型")
    private String type;

    /** 站点 */
    @ApiModelProperty("站点")
    private String sysSite;

    /** 账号简称 */
    @ApiModelProperty("账号简称")
    private String sysShopsName;

}