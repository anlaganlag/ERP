package com.tadpole.cloud.operationManagement.modular.shipment.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
public class ShipmentRecommendationTemplateResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("ID")
    private BigDecimal id;


    @ExcelProperty("推荐日期")
    @ApiModelProperty("推荐日期")
    private Date bizdate;


    @ExcelProperty("平台")
    @ApiModelProperty("平台")
    private String platform;

    @ExcelProperty("区域")
    @ApiModelProperty("区域")
    private String preArea;


    @ExcelProperty("事业部")
    @ApiModelProperty("事业部")
    private String department;


    @ExcelProperty("Team")
    @ApiModelProperty("Team")
    private String team;


    @ExcelProperty("物料编码")
    @ApiModelProperty("物料编码")
    private String materialCode;




    @ExcelProperty("ASIN")
    @ApiModelProperty("ASIN")
    private String asin;


    @ExcelProperty("类目")
    @ApiModelProperty("类目")
    private String category;

    @ExcelProperty("运营大类")
    @ApiModelProperty("运营大类")
    private String productType;

    @ExcelProperty("产品名称")
    @ApiModelProperty("产品名称")
    private String productName;


    @ExcelProperty("款式")
    @ApiModelProperty("款式")
    private String style;


    @ExcelProperty("主材料")
    @ApiModelProperty("主材料")
    private String mainMaterial;


    @ExcelProperty("图案")
    @ApiModelProperty("图案")
    private String pattern;


    @ExcelProperty("公司品牌")
    @ApiModelProperty("公司品牌")
    private String companyBrand;


    @ExcelProperty("适用品牌或对象")
    @ApiModelProperty("适用品牌或对象")
    private String brand;


    @ExcelProperty("型号")
    @ApiModelProperty("型号")
    private String model;


    @ExcelProperty("颜色")
    @ApiModelProperty("颜色")
    private String color;


    @ExcelProperty("尺码")
    @ApiModelProperty("尺码")
    private String sizes;


    @ExcelProperty("包装数量")
    @ApiModelProperty("包装数量")
    private String packing;


    @ExcelProperty("建议物流方式")
    @ApiModelProperty("建议物流方式")
    private String deliveryType;



    @ExcelProperty("版本描述")
    @ApiModelProperty("版本描述")
    private String versionDes;


    @ExcelProperty("适用机型")
    @ApiModelProperty("适用机型")
    private String type;


    @ExcelProperty("二级类目")
    @ApiModelProperty("二级类目")
    private String matstylesecondlabel;


    @ExcelProperty("未完成订单-;7天内预计到货数量")
    @ApiModelProperty("未完成订单-;7天内预计到货数量")
    private BigDecimal undoneIn7daysQty;


    @ExcelProperty("未完成订单-;8-14天预计到货数量")
    @ApiModelProperty("未完成订单-;8-14天预计到货数量")
    private BigDecimal undone8to14daysQty;


    @ExcelProperty("未完成订单-;15天后预计到货数量")
    @ApiModelProperty("未完成订单-;15天后预计到货数量")
    private BigDecimal undoneAfter15daysQty;


    @ExcelProperty("AZ可售数量")
    @ApiModelProperty("AZ可售数量")
    private BigDecimal azAvailQty;


    @ExcelProperty("AZ预留数量")
    @ApiModelProperty("AZ预留数量")
    private BigDecimal azReservedQty;


    @ExcelProperty("AZ物流待发数量")
    @ApiModelProperty("AZ物流待发数量")
    private BigDecimal azWaitSendQty;


    @ExcelProperty("AZ空运来货数量")
    @ApiModelProperty("AZ空运来货数量")
    private BigDecimal azAirQty;


    @ExcelProperty("AZ海运来货数量")
    @ApiModelProperty("AZ海运来货数量")
    private BigDecimal azShipQty;


    @ExcelProperty("AZ铁运来货数量")
    @ApiModelProperty("AZ铁运来货数量")
    private BigDecimal azTrainQty;


    @ExcelProperty("AZ卡航来货数量")
    @ApiModelProperty("AZ卡航来货数量")
    private BigDecimal azCarQty;


    @ExcelProperty("海外仓库存数量")
    @ApiModelProperty("海外仓库存数量")
    private BigDecimal overseaInvQty;


    @ExcelProperty("海外仓在途数量")
    @ApiModelProperty("海外仓在途数量")
    private BigDecimal overseaOnwayQty;


    @ExcelProperty("海外仓总库存")
    @ApiModelProperty("海外仓总库存")
    private BigDecimal overseaTotalQty;


    @ExcelProperty("AZ店铺即时库存总数")
    @ApiModelProperty("AZ店铺即时库存总数")
    private BigDecimal azTotalQty;


    @ExcelProperty("【本月】销量")
    @ApiModelProperty("【本月】销量")
    private BigDecimal curqty;


    @ExcelProperty("【本月+1】销量")
    @ApiModelProperty("【本月+1】销量")
    private BigDecimal nextmMon1qty;


    @ExcelProperty("【本月+2】销量")
    @ApiModelProperty("【本月+2】销量")
    private BigDecimal nextmMon2qty;


    @ExcelProperty("【本月+3】销量")
    @ApiModelProperty("【本月+3】销量")
    private BigDecimal nextmMon3qty;


    @ExcelProperty("【本月+4】销量")
    @ApiModelProperty("【本月+4】销量")
    private BigDecimal nextmMon4qty;


    @ExcelProperty("【本月+5】销量")
    @ApiModelProperty("【本月+5】销量")
    private BigDecimal nextmMon5qty;


    @ExcelProperty("【本月+6】销量")
    @ApiModelProperty("【本月+6】销量")
    private BigDecimal nextmMon6qty;


    @ExcelProperty("7天销售数量")
    @ApiModelProperty("7天销售数量")
    private BigDecimal day7qty;


    @ExcelProperty("14天销售数量")
    @ApiModelProperty("14天销售数量")
    private BigDecimal day14qty;


    @ExcelProperty("30天销售数量")
    @ApiModelProperty("30天销售数量")
    private BigDecimal day30qty;


    @ExcelProperty("日均销量")
    @ApiModelProperty("日均销量")
    private BigDecimal dayavgsell;


    @ExcelProperty("备货用生产周期")
    @ApiModelProperty("备货用生产周期")
    private BigDecimal restockProductionCycle;


    @ExcelProperty("AZ海外总库存D6")
    @ApiModelProperty("AZ海外总库存D6")
    private BigDecimal azOverseaTotalQty;


    @ExcelProperty("推荐运输方式")
    @ApiModelProperty("推荐运输方式")
    private String recommTransportation;


    @ExcelProperty("国内仓处理天数")
    @ApiModelProperty("国内仓处理天数")
    private BigDecimal domesticWarehouseHandleDays;


    @ExcelProperty("运输天数")
    @ApiModelProperty("运输天数")
    private BigDecimal deliveryDays;


    @ExcelProperty("海外仓处理天数")
    @ApiModelProperty("海外仓处理天数")
    private BigDecimal overseaWarehouserHandleDays;


    @ExcelProperty("安全销售天数")
    @ApiModelProperty("安全销售天数")
    private BigDecimal saleSafelyDays;


    @ExcelProperty("发货间隔天数")
    @ApiModelProperty("发货间隔天数")
    private BigDecimal sendGapDays;


    @ExcelProperty("国内可用库存")
    @ApiModelProperty("国内可用库存")
    private BigDecimal domesticAvailQty;


    @ExcelProperty("国内仓可调拨数量")
    @ApiModelProperty("国内仓可调拨数量")
    private BigDecimal domesticTransferAvailQty;


    @ExcelProperty("国内待质检数量")
    @ApiModelProperty("国内待质检数量")
    private BigDecimal domesticUncheckQty;


    @ExcelProperty("总发货天数")
    @ApiModelProperty("总发货天数")
    private BigDecimal sendTotalDays;


    @ExcelProperty("总发货天数覆盖销售日期")
    @ApiModelProperty("总发货天数覆盖销售日期")
    private Date canSaleDate;


    @ExcelProperty("发货总销售需求")
    @ApiModelProperty("发货总销售需求")
    private BigDecimal requirementSendTotalQty;


    @ExcelProperty("发货前周转天数")
    @ApiModelProperty("发货前周转天数")
    private BigDecimal turnoverBeforeSendDays;


    @ExcelProperty("推荐国内发货数量")
    @ApiModelProperty("推荐国内发货数量")
    private BigDecimal domesticRecommSendQty;


    @ExcelProperty("在途数量供应情况")
    @ApiModelProperty("在途数量供应情况")
    private String isCutGoods;

    @ExcelProperty("同步时间;从BI同步的时间")
    @ApiModelProperty("同步时间;从BI同步的时间")
    private Date syncTime;


    @ExcelProperty("状态;申请状态")
    @ApiModelProperty("状态;申请状态")
    private Integer status;


    @ExcelProperty("账号")
    @ApiModelProperty("账号")
    private String sysShopsName;

    @ExcelProperty("站点")
    @ApiModelProperty("站点")
    private String sysSite;


    @ExcelProperty("SKU")
    @ApiModelProperty("SKU")
    private String sku;



    @ExcelProperty("店铺名称")
    @ApiModelProperty("店铺名称")
    private String shopName;

    @ExcelProperty("FBA号")
    @ApiModelProperty("FBA号")
    private String fbaNo;


    @ExcelProperty("调入仓库")
    @ApiModelProperty("调入仓库")
    private String receiveWarehouse;

    @ExcelProperty("发货数量")
    @ApiModelProperty("发货数量")
    private BigDecimal sendQty;

    @ExcelProperty("发货方式")
    @ApiModelProperty("发货方式")
    private String transportationType;


    @ExcelProperty("UNW类型")
    @ApiModelProperty("UNW类型")
    private String unwType;


    @ExcelProperty("备注1")
    @ApiModelProperty("备注1")
    private String remark1;

    @ExcelProperty("备注2")
    @ApiModelProperty("备注2")
    private String remark2;


    @ExcelProperty("备注3")
    @ApiModelProperty("备注3")
    private String remark3;






}
