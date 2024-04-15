package com.tadpole.cloud.operationManagement.modular.shipment.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* <p>
    * 发货推荐
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("SHIPMENT_RECOMMENDATION")
public class ShipmentRecommendationShopSkuResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("账号")
    private String sysShopsName;

    @ApiModelProperty("店铺")
    private String shopName;


    @ApiModelProperty("运营大类")
    private String productType;


    @ApiModelProperty("SKU")
    private String sku;

    @ApiModelProperty("站点")
    private String site;


    @ExcelProperty("店铺近7天销量")
    @ApiModelProperty("店铺近7天销量")
    private BigDecimal shopDay7qty;


    @ExcelProperty("店铺近30天销量")
    @ApiModelProperty("店铺近30天销量")
    private BigDecimal shopDay30qty;


    @ExcelProperty("店铺海外总库存")
    @ApiModelProperty("店铺海外总库存")
    private BigDecimal shopAzOverseaTotalQty;


    @ExcelProperty("店铺可售数量")
    @ApiModelProperty("店铺可售数量")
    private BigDecimal shopAvailQty;



    @ExcelProperty("SKU近7天销量")
    @ApiModelProperty("SKU近7天销量")
    private BigDecimal skuDay7qty;


    @ExcelProperty("SKU近30天销量")
    @ApiModelProperty("SKU近30天销量")
    private BigDecimal skuDay30qty;


    @ExcelProperty("SKU海外总库存")
    @ApiModelProperty("SKU海外总库存")
    private BigDecimal skuAzOverseaTotalQty;


    @ExcelProperty("SKU可售数量")
    @ApiModelProperty("SKU可售数量")
    private BigDecimal skuAvailQty;



    @ApiModelProperty("发货模式")
    private String sysLogiMode;


    @ApiModelProperty("SKU标签类型")
    private String sysLabelType;


    @ApiModelProperty("区域")
    private String area;



}
