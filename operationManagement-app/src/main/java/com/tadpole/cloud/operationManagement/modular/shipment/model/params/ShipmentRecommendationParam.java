package com.tadpole.cloud.operationManagement.modular.shipment.model.params;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
@TableName("SHIPMENT_RECOMMENDATION")
    public class ShipmentRecommendationParam extends BaseRequest implements Serializable, BaseValidatingParam {


    private static final long serialVersionUID = 1L;


    /** ID */
    @ExcelProperty("ID")
    @ApiModelProperty("ID") 
    private BigDecimal id;

    /** 推荐日期 */
    @ExcelProperty("推荐日期")
    @ApiModelProperty("推荐日期") 
    private Date bizdate;

    /** 平台 */
    @ExcelProperty("平台")
    @ApiModelProperty("平台") 
    private String platform;

    /** 事业部 */
    @ExcelProperty("事业部")
    @ApiModelProperty("事业部") 
    private String department;

    /** Team */
    @ExcelProperty("Team")
    @ApiModelProperty("Team") 
    private String team;

    /** 物料编码 */
    @ExcelProperty("物料编码")
    @ApiModelProperty("物料编码") 
    private String materialCode;

    /** 区域 */
    @ExcelProperty("区域")
    @ApiModelProperty("区域") 
    private String preArea;

    /** ASIN */
    @ExcelProperty("ASIN")
    @ApiModelProperty("ASIN") 
    private String asin;

    /** AZ可售数量 */
    @ExcelProperty("AZ可售数量")
    @ApiModelProperty("AZ可售数量") 
    private BigDecimal azAvailQty;

    /** AZ预留数量 */
    @ExcelProperty("AZ预留数量")
    @ApiModelProperty("AZ预留数量") 
    private BigDecimal azReservedQty;

    /** AZ物流待发数量 */
    @ExcelProperty("AZ物流待发数量")
    @ApiModelProperty("AZ物流待发数量") 
    private BigDecimal azWaitSendQty;

    /** AZ空运来货数量 */
    @ExcelProperty("AZ空运来货数量")
    @ApiModelProperty("AZ空运来货数量") 
    private BigDecimal azAirQty;

    /** AZ海运来货数量 */
    @ExcelProperty("AZ海运来货数量")
    @ApiModelProperty("AZ海运来货数量") 
    private BigDecimal azShipQty;

    /** AZ铁运来货数量 */
    @ExcelProperty("AZ铁运来货数量")
    @ApiModelProperty("AZ铁运来货数量") 
    private BigDecimal azTrainQty;

    /** AZ卡航来货数量 */
    @ExcelProperty("AZ卡航来货数量")
    @ApiModelProperty("AZ卡航来货数量") 
    private BigDecimal azCarQty;

    /** 海外仓库存数量 */
    @ExcelProperty("海外仓库存数量")
    @ApiModelProperty("海外仓库存数量") 
    private BigDecimal overseaInvQty;

    /** 海外仓在途数量 */
    @ExcelProperty("海外仓在途数量")
    @ApiModelProperty("海外仓在途数量") 
    private BigDecimal overseaOnwayQty;

    /** 海外仓总库存 */
    @ExcelProperty("海外仓总库存")
    @ApiModelProperty("海外仓总库存") 
    private BigDecimal overseaTotalQty;

    /** AZ店铺即时库存总数 */
    @ExcelProperty("AZ店铺即时库存总数")
    @ApiModelProperty("AZ店铺即时库存总数") 
    private BigDecimal azTotalQty;

    /** AZ海外总库存 */
    @ExcelProperty("AZ海外总库存")
    @ApiModelProperty("AZ海外总库存") 
    private BigDecimal azOverseaTotalQty;

    /** 【本月】销量 */
    @ExcelProperty("【本月】销量")
    @ApiModelProperty("【本月】销量") 
    private BigDecimal curqty;

    /** 【本月+1】销量 */
    @ExcelProperty("【本月+1】销量")
    @ApiModelProperty("【本月+1】销量") 
    private BigDecimal nextmMon1qty;

    /** 【本月+2】销量 */
    @ExcelProperty("【本月+2】销量")
    @ApiModelProperty("【本月+2】销量") 
    private BigDecimal nextmMon2qty;

    /** 【本月+3】销量 */
    @ExcelProperty("【本月+3】销量")
    @ApiModelProperty("【本月+3】销量") 
    private BigDecimal nextmMon3qty;

    /** 【本月+4】销量 */
    @ExcelProperty("【本月+4】销量")
    @ApiModelProperty("【本月+4】销量") 
    private BigDecimal nextmMon4qty;

    /** 【本月+5】销量 */
    @ExcelProperty("【本月+5】销量")
    @ApiModelProperty("【本月+5】销量") 
    private BigDecimal nextmMon5qty;

    /** 【本月+6】销量 */
    @ExcelProperty("【本月+6】销量")
    @ApiModelProperty("【本月+6】销量") 
    private BigDecimal nextmMon6qty;

    /** 7天销售数量 */
    @ExcelProperty("7天销售数量")
    @ApiModelProperty("7天销售数量") 
    private BigDecimal day7qty;

    /** 14天销售数量 */
    @ExcelProperty("14天销售数量")
    @ApiModelProperty("14天销售数量") 
    private BigDecimal day14qty;

    /** 30天销售数量 */
    @ExcelProperty("30天销售数量")
    @ApiModelProperty("30天销售数量") 
    private BigDecimal day30qty;

    /** 日均销量 */
    @ExcelProperty("日均销量")
    @ApiModelProperty("日均销量") 
    private BigDecimal dayavgsell;

    /** 备货用生产周期 */
    @ExcelProperty("备货用生产周期")
    @ApiModelProperty("备货用生产周期") 
    private BigDecimal restockProductionCycle;

    /** 推荐运输方式 */
    @ExcelProperty("推荐运输方式")
    @ApiModelProperty("推荐运输方式") 
    private String recommTransportation;

    /** 国内仓处理天数 */
    @ExcelProperty("国内仓处理天数")
    @ApiModelProperty("国内仓处理天数") 
    private BigDecimal domesticWarehouseHandleDays;

    /** 运输天数 */
    @ExcelProperty("运输天数")
    @ApiModelProperty("运输天数") 
    private BigDecimal deliveryDays;

    /** 海外仓处理天数 */
    @ExcelProperty("海外仓处理天数")
    @ApiModelProperty("海外仓处理天数") 
    private BigDecimal overseaWarehouserHandleDays;

    /** 安全销售天数 */
    @ExcelProperty("安全销售天数")
    @ApiModelProperty("安全销售天数") 
    private BigDecimal saleSafelyDays;

    /** 发货间隔天数 */
    @ExcelProperty("发货间隔天数")
    @ApiModelProperty("发货间隔天数") 
    private BigDecimal sendGapDays;

    /** 国内可用库存 */
    @ExcelProperty("国内可用库存")
    @ApiModelProperty("国内可用库存") 
    private BigDecimal domesticAvailQty;

    /** 国内仓可调拨数量 */
    @ExcelProperty("国内仓可调拨数量")
    @ApiModelProperty("国内仓可调拨数量") 
    private BigDecimal domesticTransferAvailQty;

    /** 国内待质检数量 */
    @ExcelProperty("国内待质检数量")
    @ApiModelProperty("国内待质检数量") 
    private BigDecimal domesticUncheckQty;

    /** 总发货天数 */
    @ExcelProperty("总发货天数")
    @ApiModelProperty("总发货天数") 
    private BigDecimal sendTotalDays;

    /** 总发货天数覆盖销售日期 */
    @ExcelProperty("总发货天数覆盖销售日期")
    @ApiModelProperty("总发货天数覆盖销售日期") 
    private Date canSaleDate;

    /** 发货总销售需求 */
    @ExcelProperty("发货总销售需求")
    @ApiModelProperty("发货总销售需求") 
    private BigDecimal requirementSendTotalQty;

    /** 发货前周转天数 */
    @ExcelProperty("发货前周转天数")
    @ApiModelProperty("发货前周转天数") 
    private BigDecimal turnoverBeforeSendDays;

    /** 推荐国内发货数量 */
    @ExcelProperty("推荐国内发货数量")
    @ApiModelProperty("推荐国内发货数量") 
    private BigDecimal domesticRecommSendQty;

    /** 在途数量供应情况 */
    @ExcelProperty("在途数量供应情况")
    @ApiModelProperty("在途数量供应情况") 
    private String isCutGoods;

    /** 类目 */
    @ExcelProperty("类目")
    @ApiModelProperty("类目") 
    private String category;

    /** 运营大类 */
    @ExcelProperty("运营大类")
    @ApiModelProperty("运营大类") 
    private String productType;

    /** 产品名称 */
    @ExcelProperty("产品名称")
    @ApiModelProperty("产品名称") 
    private String productName;

    /** 款式 */
    @ExcelProperty("款式")
    @ApiModelProperty("款式") 
    private String style;

    /** 主材料 */
    @ExcelProperty("主材料")
    @ApiModelProperty("主材料") 
    private String mainMaterial;

    /** 图案 */
    @ExcelProperty("图案")
    @ApiModelProperty("图案") 
    private String pattern;

    /** 公司品牌 */
    @ExcelProperty("公司品牌")
    @ApiModelProperty("公司品牌") 
    private String companyBrand;

    /** 适用品牌或对象 */
    @ExcelProperty("适用品牌或对象")
    @ApiModelProperty("适用品牌或对象") 
    private String brand;

    /** 型号 */
    @ExcelProperty("型号")
    @ApiModelProperty("型号") 
    private String model;

    /** 颜色 */
    @ExcelProperty("颜色")
    @ApiModelProperty("颜色") 
    private String color;

    /** 尺码 */
    @ExcelProperty("尺码")
    @ApiModelProperty("尺码") 
    private String sizes;

    /** 包装数量 */
    @ExcelProperty("包装数量")
    @ApiModelProperty("包装数量") 
    private String packing;



    /** 适用机型 */
    @ExcelProperty("适用机型")
    @ApiModelProperty("适用机型") 
    private String type;

    /** 二级类目 */
    @ExcelProperty("二级类目")
    @ApiModelProperty("二级类目") 
    private String matstylesecondlabel;


    /** 节日标签 */
    @ExcelProperty("节日标签")
    @ApiModelProperty("节日标签")
    private String festivalLabel;


    /** 季节标签 */
    @ExcelProperty("季节标签")
    @ApiModelProperty("季节标签")
    private String seasonLabel;


    /** 未完成订单-;7天内预计到货数量 */
    @ExcelProperty("未完成订单-;7天内预计到货数量")
    @ApiModelProperty("未完成订单-;7天内预计到货数量") 
    private BigDecimal undoneIn7daysQty;

    /** 未完成订单-;8-14天预计到货数量 */
    @ExcelProperty("未完成订单-;8-14天预计到货数量")
    @ApiModelProperty("未完成订单-;8-14天预计到货数量") 
    private BigDecimal undone8to14daysQty;

    /** 未完成订单-;15天后预计到货数量 */
    @ExcelProperty("未完成订单-;15天后预计到货数量")
    @ApiModelProperty("未完成订单-;15天后预计到货数量") 
    private BigDecimal undoneAfter15daysQty;

    /** 同步时间;从BI同步的时间 */
    @ExcelProperty("同步时间;从BI同步的时间")
    @ApiModelProperty("同步时间;从BI同步的时间") 
    private Date syncTime;

    /** 状态;申请状态 */
    @ExcelProperty("状态;申请状态")
    @ApiModelProperty("状态;申请状态") 
    private Integer status;



    /** asin列表 */
    @ExcelProperty("asin列表")
    @ApiModelProperty("asin列表") 
    private List<String> asinList;


    /** 事业部列表 */
    @ExcelProperty("事业部列表")
    @ApiModelProperty("事业部列表") 
    private List<String> departmentList;


    /** team列表 */
    @ExcelProperty("team列表")
    @ApiModelProperty("team列表") 
    private List<String> teamList;


    /** 区域列表 */
    @ExcelProperty("区域列表")
    @ApiModelProperty("区域列表") 
    private List<String> preAreaList;





    /** 只显示无申请记录 */
    @ExcelProperty("只显示无申请记录")
    @ApiModelProperty("只显示无申请记录") 
    private Boolean isNoApply;





    /** 十四级属性 */


    @ApiModelProperty("物料编码集合List")
    private List<String> materialCodeList;



    @ApiModelProperty("类目集合List")
    private List<String> categoryList;


    @ApiModelProperty("运营大类集合List")
    private List<String> productTypeList;



    @ApiModelProperty("产品名称集合List")
    private List<String> productNameList;



    @ApiModelProperty("款式集合List")
    private List<String> styleList;




    @ApiModelProperty("主材料集合List")
    private List<String> mainMaterialList;



    @ApiModelProperty("图案集合List")
    private List<String> patternList;



    @ApiModelProperty("公司品牌合")
    private List<String> companyBrandList;



    @ApiModelProperty("适用品牌或对象集合List")
    private List<String> brandList;



    @ApiModelProperty("型号集合List")
    private List<String> modelList;




    @ApiModelProperty("颜色集合List")
    private List<String> colorList;



    @ApiModelProperty("尺码集合List")
    private List<String> sizesList;



    @ApiModelProperty("包装数量集合List")
    private List<String> packingList;


    @ApiModelProperty("适用机型集合List")
    private List<String> typeList;

    @ApiModelProperty("版本集合")
    private String version;
    @ApiModelProperty("版本集合List")
    private List<String> versionList;


    /** 版本描述 */
    @ExcelProperty("版本描述")
    @ApiModelProperty("版本描述")
    private String versionDes;

    @ApiModelProperty("版本描述List")
    private List<String> versionDesList;

    @ApiModelProperty("款式二级标签")
    private String styleSecondLabel;
    @ApiModelProperty("款式二级标签List")
    private List<String> styleSecondLabelList;



    public String getPk() {
        return platform+preArea+department+team+materialCode+asin;
    }

}
