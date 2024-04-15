package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelProperty;
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
 * <p>
 * 销量汇总订单数据
 * </p>
 *
 * @author ty
 * @since 2022-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class AmazonOrdersResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 订单号 */
    @ApiModelProperty("AMAZON_ORDER_ID")
    @ExcelProperty(value ="订单号")
    private String amazonOrderId;

    /** 卖家追踪码 */
    @ApiModelProperty("MERCHANT_ORDER_ID")
    @ExcelProperty(value ="卖家追踪码")
    private String merchantOrderId;

    /** 购买时间 */
    @ApiModelProperty("PURCHASE_DATE")
    @ExcelProperty(value ="购买时间")
    private Date purchaseDate;

    /** 上次更新时间 */
    @ApiModelProperty("LAST_UPDATED_DATE")
    @ExcelProperty(value ="上次更新时间")
    private Date lastUpdatedDate;

    /** 订单状态 */
    @ApiModelProperty("ORDER_STATUS")
    @ExcelProperty(value ="订单状态")
    private String orderStatus;

    /** 下单渠道 */
    @ApiModelProperty("SALES_CHANNEL")
    @ExcelProperty(value ="下单渠道")
    private String salesChannel;

    /** 配送方式 */
    @ApiModelProperty("FULFILLMENT_CHANNEL")
    @ExcelProperty(value ="配送方式")
    private String fulfillmentChannel;

    /** 买家期望的配送方式 */
    @ApiModelProperty("SHIP_SERVICE_LEVEL")
    @ExcelProperty(value ="买家期望的配送方式")
    private String shipServiceLevel;

    /** 城市 */
    @ApiModelProperty("CITY")
    @ExcelProperty(value ="城市")
    private String city;

    /** 州 */
    @ApiModelProperty("STATE")
    @ExcelProperty(value ="州")
    private String state;

    /** 邮编 */
    @ApiModelProperty("POSTAL_CODE")
    @ExcelProperty(value ="邮编")
    private String postalCode;

    /** 国家 */
    @ApiModelProperty("COUNTRY")
    @ExcelProperty(value ="国家")
    private String country;

    /** 是否是公司订单 */
    @ApiModelProperty("IS_BUSINESS_ORDER")
    @ExcelProperty(value ="是否是公司订单")
    private String isBusinessOrder;

    /** 是否是更换订单 */
    @ApiModelProperty("IS_REPLACEMENT_ORDER")
    @ExcelProperty(value ="是否是更换订单")
    private String isReplacementOrder;

    /** 入库时间 */
    @ApiModelProperty("CREATE_TIME")
    @ExcelProperty(value ="入库时间")
    private Date createTime;

    /** 报告日期 */
    @ApiModelProperty("UPLOAD_DATE")
    @ExcelProperty(value ="报告日期")
    private Date uploadDate;

    /** 商品销售平台 */
    @ApiModelProperty("PLATFORM")
    @ExcelProperty(value ="商品销售平台")
    private String platform;

    /** 订单类型 */
    @ApiModelProperty("TYPE")
    @ExcelProperty(value ="订单类型")
    private String type;

    /** 站点 */
    @ApiModelProperty("SYS_SITE")
    @ExcelProperty(value ="站点")
    private String sysSite;

    /** 账号简称 */
    @ApiModelProperty("SYS_SHOPS_NAME")
    @ExcelProperty(value ="账号简称")
    private String sysShopsName;

}