package com.tadpole.cloud.platformSettlement.modular.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.util.Date;

import java.io.Serializable;
import java.math.BigDecimal;

 /**
 * ;
 * @author : LSY
 * @date : 2023-12-22
 */
@ApiModel(value = "",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LtShopeeSettlementReportParam extends BaseRequest implements Serializable,BaseValidatingParam {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private String id;

    /**
     * 新物料代码
     */
    @ApiModelProperty(value = "新物料代码")
    private String newMatCode;

    /**
     * 类目
     */
    @ApiModelProperty(value = "类目")
    private String category;

    /**
     * 运营大类
     */
    @ApiModelProperty(value = "运营大类")
    private String productType;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 款式
     */
    @ApiModelProperty(value = "款式")
    private String style;

    /**
     * 主材料
     */
    @ApiModelProperty(value = "主材料")
    private String mainMaterial;

    /**
     * 图案
     */
    @ApiModelProperty(value = "图案")
    private String design;

    /**
     * 公司品牌
     */
    @ApiModelProperty(value = "公司品牌")
    private String companyBrand;

    /**
     * 适用品牌或对象
     */
    @ApiModelProperty(value = "适用品牌或对象")
    private String fitBrand;

    /**
     * 型号
     */
    @ApiModelProperty(value = "型号")
    private String model;

    /**
     * 颜色
     */
    @ApiModelProperty(value = "颜色")
    private String color;

    /**
     * 尺码
     */
    @ApiModelProperty(value = "尺码")
    private String sizes;

    /**
     * 包装数量
     */
    @ApiModelProperty(value = "包装数量")
    private String packing;

    /**
     * 版本
     */
    @ApiModelProperty(value = "版本")
    private String version;

    /**
     * 适用机型
     */
    @ApiModelProperty(value = "适用机型")
    private String type;

    /**
     * 销售品牌
     */
    @ApiModelProperty(value = "销售品牌")
    private String salesBrand;

    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private BigDecimal qty;

    /**
     * 原币
     */
    @ApiModelProperty(value = "原币")
    private BigDecimal originalCurrency;

    /**
     * 币种
     */
    @ApiModelProperty(value = "币种")
    private String currency;

    /**
     * 订单金额
     */
    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderFee;

    /**
     * 买方运费
     */
    @ApiModelProperty(value = "买方运费")
    private BigDecimal buyerShippingFee;

    /**
     * 店铺优惠券
     */
    @ApiModelProperty(value = "店铺优惠券")
    private BigDecimal shopVoucher;

    /**
     * 交易费
     */
    @ApiModelProperty(value = "交易费")
    private BigDecimal transactionFee;

    /**
     * 佣金
     */
    @ApiModelProperty(value = "佣金")
    private BigDecimal platformFee;

    /**
     * 服务费
     */
    @ApiModelProperty(value = "服务费")
    private BigDecimal serviceCharge;

    /**
     * 运费总额
     */
    @ApiModelProperty(value = "运费总额")
    private BigDecimal shippingFeeTotal;

    /**
     * 关税
     */
    @ApiModelProperty(value = "关税")
    private BigDecimal customsRate;

    /**
     * 其他费用
     */
    @ApiModelProperty(value = "其他费用")
    private BigDecimal otherFee;

    /**
     * 销毁成本-采购成本
     */
    @ApiModelProperty(value = "销毁成本-采购成本")
    private BigDecimal disposePurchaseFee;

    /**
     * 成本-USD
     */
    @ApiModelProperty(value = "成本-USD")
    private BigDecimal costUsd;

    /**
     * 成本附加-USD
     */
    @ApiModelProperty(value = "成本附加-USD")
    private BigDecimal costAddUsd;

    /**
     * 利润
     */
    @ApiModelProperty(value = "利润")
    private BigDecimal profit;

    /**
     * 鼓励金
     */
    @ApiModelProperty(value = "鼓励金")
    private BigDecimal incentiveFund;

    /**
     * period
     */
    @ApiModelProperty(value = "period")
    private String period;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String shopName;


     /**
      * 账号
      */
     @ApiModelProperty("站点")
     @ExcelProperty(value = "站点")
     private String site;

    /**
     * 单价CNY
     */
    @ApiModelProperty(value = "单价CNY")
    private BigDecimal unitCostCny;

    /**
     * 含税成本单价CNY
     */
    @ApiModelProperty(value = "含税成本单价CNY")
    private BigDecimal taxPriceCny;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 拨款金额
     */
    @ApiModelProperty(value = "拨款金额")
    private BigDecimal appropAmount;

    /**
     * 拨款时间
     */
    @ApiModelProperty(value = "拨款时间")
    private Date appropTime;

    /**
     * 问题ipaid
     */
    @ApiModelProperty(value = "问题ipaid")
    private String problemIpaid;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date updateTime;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private String updateBy;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date createTime;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private String createBy;

    @ApiModelProperty("销量占比")
    private BigDecimal revenueRation;

    /**
     * 人数
     */
    @ApiModelProperty("人数")
    private BigDecimal peopleNum;

    /**
     * 人工成本
     */
    @ApiModelProperty("人工成本")
    private BigDecimal peopleCost;


    @ApiModelProperty("CONFIRM_STATUS")
    private BigDecimal confirmStatus;

     @ApiModelProperty(value = "")
     private String platform ;

     /**  */
     @ApiModelProperty(value = "")
     private String department ;


     /**  */
     @ApiModelProperty(value = "")
     private String team ;

 }