package com.tadpole.cloud.operationManagement.modular.shipment.model.result;


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
    * 发货推荐数据快照
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
@TableName("SHIPMENT_RECOM_SNAPSHOT")
public class ShipmentRecomSnapshotResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("ID")
    private BigDecimal id;


    @ApiModelProperty("推荐日期")
    private Date bizdate;


    @ApiModelProperty("平台")
    private String platform;


    @ApiModelProperty("事业部")
    private String department;


    @ApiModelProperty("Team")
    private String team;


    @ApiModelProperty("物料编码")
    private String materialCode;


    @ApiModelProperty("区域")
    private String preArea;


    @ApiModelProperty("ASIN")
    private String asin;


    @ApiModelProperty("AZ可售数量")
    private BigDecimal azAvailQty;


    @ApiModelProperty("AZ预留数量")
    private BigDecimal azReservedQty;


    @ApiModelProperty("AZ物流待发数量")
    private BigDecimal azWaitSendQty;


    @ApiModelProperty("AZ空运来货数量")
    private BigDecimal azAirQty;


    @ApiModelProperty("AZ海运来货数量")
    private BigDecimal azShipQty;


    @ApiModelProperty("AZ铁运来货数量")
    private BigDecimal azTrainQty;


    @ApiModelProperty("AZ卡航来货数量")
    private BigDecimal azCarQty;


    @ApiModelProperty("海外仓库存数量")
    private BigDecimal overseaInvQty;


    @ApiModelProperty("海外仓在途数量")
    private BigDecimal overseaOnwayQty;


    @ApiModelProperty("海外仓总库存")
    private BigDecimal overseaTotalQty;


    @ApiModelProperty("AZ店铺即时库存总数")
    private BigDecimal azTotalQty;


    @ApiModelProperty("AZ海外总库存")
    private BigDecimal azOverseaTotalQty;


    @ApiModelProperty("【本月】销量")
    private BigDecimal curqty;


    @ApiModelProperty("【本月+1】销量")
    private BigDecimal nextmMon1qty;


    @ApiModelProperty("【本月+2】销量")
    private BigDecimal nextmMon2qty;


    @ApiModelProperty("【本月+3】销量")
    private BigDecimal nextmMon3qty;


    @ApiModelProperty("【本月+4】销量")
    private BigDecimal nextmMon4qty;


    @ApiModelProperty("【本月+5】销量")
    private BigDecimal nextmMon5qty;


    @ApiModelProperty("【本月+6】销量")
    private BigDecimal nextmMon6qty;


    @ApiModelProperty("7天销售数量")
    private BigDecimal day7qty;


    @ApiModelProperty("14天销售数量")
    private BigDecimal day14qty;


    @ApiModelProperty("30天销售数量")
    private BigDecimal day30qty;


    @ApiModelProperty("日均销量")
    private BigDecimal dayavgsell;


    @ApiModelProperty("备货用生产周期")
    private BigDecimal restockProductionCycle;


    @ApiModelProperty("推荐运输方式")
    private String recommTransportation;


    @ApiModelProperty("申请物流方式")
    private String applyTransportation;


    @ApiModelProperty("国内仓处理天数")
    private BigDecimal domesticWarehouseHandleDays;


    @ApiModelProperty("运输天数")
    private BigDecimal deliveryDays;


    @ApiModelProperty("海外仓处理天数")
    private BigDecimal overseaWarehouserHandleDays;


    @ApiModelProperty("安全销售天数")
    private BigDecimal saleSafelyDays;


    @ApiModelProperty("发货间隔天数")
    private BigDecimal sendGapDays;


    @ApiModelProperty("国内可用库存")
    private BigDecimal domesticAvailQty;


    @ApiModelProperty("国内仓可调拨数量")
    private BigDecimal domesticTransferAvailQty;


    @ApiModelProperty("国内待质检数量")
    private BigDecimal domesticUncheckQty;


    @ApiModelProperty("总发货天数")
    private BigDecimal sendTotalDays;


    @ApiModelProperty("总发货天数覆盖销售日期")
    private Date canSaleDate;


    @ApiModelProperty("发货总销售需求")
    private BigDecimal requirementSendTotalQty;


    @ApiModelProperty("发货后周转天数")
    private BigDecimal turnoverAfterSendDays;


    @ApiModelProperty("发货前周转天数")
    private BigDecimal turnoverBeforeSendDays;


    @ApiModelProperty("推荐国内发货数量")
    private BigDecimal domesticRecommSendQty;


    @ApiModelProperty("申请发货数量")
    private BigDecimal applySendQty;


    @ApiModelProperty("在途数量供应情况")
    private String isCutGoods;


    @ApiModelProperty("类目")
    private String category;


    @ApiModelProperty("运营大类")
    private String productType;


    @ApiModelProperty("产品名称")
    private String productName;


    @ApiModelProperty("款式")
    private String style;


    @ApiModelProperty("主材料")
    private String mainMaterial;


    @ApiModelProperty("图案")
    private String pattern;


    @ApiModelProperty("公司品牌")
    private String companyBrand;


    @ApiModelProperty("适用品牌或对象")
    private String brand;


    @ApiModelProperty("型号")
    private String model;


    @ApiModelProperty("颜色")
    private String color;


    @ApiModelProperty("尺码")
    private String sizes;


    @ApiModelProperty("包装数量")
    private String packing;


    @ApiModelProperty("版本描述")
    private String versionDes;


    @ApiModelProperty("适用机型")
    private String type;


    @ApiModelProperty("二级类目")
    private String matstylesecondlabel;


    /** 节日标签 */
    @ApiModelProperty("节日标签")
    private String festivalLabel;


    /** 季节标签 */
    @ApiModelProperty("季节标签")
    private String seasonLabel;


    @ApiModelProperty("未完成订单-;7天内预计到货数量")
    private BigDecimal undoneIn7daysQty;


    @ApiModelProperty("未完成订单-;8-14天预计到货数量")
    private BigDecimal undone8to14daysQty;


    @ApiModelProperty("未完成订单-;15天后预计到货数量")
    private BigDecimal undoneAfter15daysQty;


    @ApiModelProperty("同步时间;从BI同步的时间")
    private Date syncTime;


    @ApiModelProperty("状态;申请状态")
    private Integer status;

    /*** 源净重量*/
    @ApiModelProperty("源净重量")
    private BigDecimal netWeightOrg;

    /*** 源毛重*/
    @ApiModelProperty("源毛重")
    private BigDecimal boxWeight2Org;


    /*** 源包装体积*/
    @ApiModelProperty("源包装体积")
    private BigDecimal boxVolumeOrg;

}
