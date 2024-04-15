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
 * 追踪明细项-出货清单
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
@TableName("TRACKING_SHIPPING")
public class TrackingFlatVO implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("ID;明细ID")
    private BigDecimal id;


    @ApiModelProperty("申请批次")
    @ExcelProperty(value = "申请批次")
    private String applyBatchNo;


    @ApiModelProperty("创建方式")
    @ExcelProperty(value = "创建方式")
    private String dataSourceType;


    @ApiModelProperty("申请日期")
    @ExcelProperty(value = "申请日期")
    private Date applyDate;


    @ApiModelProperty("申请人")
    @ExcelProperty(value = "申请人")
    private String apllyPerson;


    @ApiModelProperty("申请人编号")
    private String apllyPersonNo;


    @ApiModelProperty("平台")
    @ExcelProperty(value = "平台")
    private String platform;


    @ApiModelProperty("区域")
    @ExcelProperty(value = "区域")
    private String area;


    @ApiModelProperty("事业部")
    @ExcelProperty(value = "事业部")
    private String department;


    @ApiModelProperty("Team")
    @ExcelProperty(value = "Team")
    private String team;


    @ApiModelProperty("物料编码")
    @ExcelProperty(value = "物料编码")
    private String materialCode;

    @ApiModelProperty("ASIN")
    @ExcelProperty(value = "ASIN")
    private String asin;

    @ApiModelProperty("类目")
    @ExcelProperty(value = "类目")
    private String category;


    @ApiModelProperty("运营大类")
    @ExcelProperty(value = "运营大类")
    private String productType;


    @ApiModelProperty("产品名称")
    @ExcelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty("款式")
    @ExcelProperty(value = "款式")
    private String style;

    @ApiModelProperty("主材料")
    @ExcelProperty(value = "主材料")
    private String mainMaterial;

    @ApiModelProperty("图案")
    @ExcelProperty(value = "图案")
    private String design;


    @ApiModelProperty("公司品牌")
    @ExcelProperty(value = "公司品牌")
    private String companyBrand;


    @ApiModelProperty("适用品牌或对象")
    @ExcelProperty(value = "适用品牌或对象")
    private String brand;


    @ApiModelProperty("型号")
    @ExcelProperty(value = "型号")
    private String model;


    @ApiModelProperty("颜色")
    @ExcelProperty(value = "颜色")
    private String color;


    @ApiModelProperty("尺码")
    @ExcelProperty(value = "尺码")
    private String sizes;

    @ApiModelProperty("包装数量")
    @ExcelProperty(value = "包装数量")
    private String packing;


    @ApiModelProperty("版本描述")
    @ExcelProperty("版本描述")
    private String versionDes;

    @ApiModelProperty("适用机型")
    @ExcelProperty(value = "适用机型")
    private String type;


    @ApiModelProperty("申请发货数量合计")
    @ExcelProperty(value = "申请发货数量合计")
    private BigDecimal sendQty;

    @ApiModelProperty("申请发货方式")
    @ExcelProperty("申请发货方式")
    private String batchTransportationType;


    @ApiModelProperty("审核状态")
    @ExcelProperty("审核状态")
    private String checkStatusName;

    @ApiModelProperty("审核日期")
    @ExcelProperty(value = "审核日期")
    private Date checkDate;


    @ApiModelProperty("审核人")
    @ExcelProperty(value = "审核人")
    private String checkPerson;


    @ApiModelProperty("不通过原因")
    @ExcelProperty(value = "不通过原因")
    private String checkReason;


    @ApiModelProperty("审核状态")
    private Integer checkStatus;


    @ApiModelProperty("跟踪状态")
    @ExcelProperty(value = "跟踪状态")
    private String trackingStatus;


    @ApiModelProperty("实际发货数量合计")
    @ExcelProperty("实际发货数量合计")
    private BigDecimal batchActualSendQty;


    @ApiModelProperty("批次审核日期")
    private Date batchCheckDate;


    @ApiModelProperty("审核人编号")
    private String checkPersonNo;


    @ApiModelProperty("实际到货数量合计")
    @ExcelProperty(value = "实际到货数量合计")
    private BigDecimal actualArrivalQty;

    @ApiModelProperty("调拨申请单号")
    @ExcelProperty(value = "调拨申请单号")
    private String billNo;

    @ApiModelProperty("业务类型")
    @ExcelProperty(value = "业务类型")
    private String unwType;

    @ApiModelProperty("账号")
    @ExcelProperty(value = "账号")
    private String sysShopsName;

    @ApiModelProperty("站点")
    @ExcelProperty(value = "站点")
    private String sysSite;

    @ApiModelProperty("SKU")
    @ExcelProperty(value = "SKU")
    private String sku;

    @ApiModelProperty("FBA号")
    @ExcelProperty(value = "FBA号")
    private String fbaNo;

    @ApiModelProperty("调入仓库")
    @ExcelProperty(value = "调入仓库")
    private String receiveWarehouse;

    @ApiModelProperty("申请发货数量")
    @ExcelProperty(value = "申请发货数量")
    private BigDecimal applySendQty;

    @ApiModelProperty("申请发货方式")
    @ExcelProperty(value = "申请发货方式")
    private String transportationType;

    @ApiModelProperty("申请执行状态")
    @ExcelProperty("申请执行状态")
    private String applyTrackStatusName;


    @ApiModelProperty("申请状态")
    private Integer applyStatus;


    @ApiModelProperty("同步日期")
    @ExcelProperty("同步日期")
    private Date syncTime;

    @ApiModelProperty("拣货单号")
    @ExcelProperty(value = "拣货单号")
    private String pickListCode;

    @ApiModelProperty("拣货数量")
    @ExcelProperty(value = "拣货数量")
    private BigDecimal pickQty;

    @ApiModelProperty("拣货完成日期")
    @ExcelProperty(value = "拣货完成日期")
    private Date pickFinishDate;

    @ApiModelProperty("总装箱数量")
    @ExcelProperty(value = "总装箱数量")
    private BigDecimal actualSendQty;

    @ApiModelProperty("返仓数量")
    @ExcelProperty(value = "返仓数量")
    private BigDecimal returnQty;


    @ApiModelProperty("出货清单号")
    @ExcelProperty(value = "出货清单号")
    private String shippingListCode;

    @ApiModelProperty("打包结票日期")
    @ExcelProperty(value = "打包结票日期")
    private Date packingFinishDate;

    @ApiModelProperty("ShipmentID")
    @ExcelProperty(value = "ShipmentID")
    private String shipmentId;


    @ApiModelProperty("同步K3状态;数据初始化为0-调用K3接口成功1--失败2")
    private Integer syncStatus;


    @ApiModelProperty("同步请求参数")
    private String syncRequestMsg;


    @ApiModelProperty("同步结果")
    private String syncResultMsg;


    @ApiModelProperty("是否需要追踪;0:需要追踪，1：不需要追踪")
    private Integer needTrack;


    @ApiModelProperty("创建人")
    private String createdBy;


    @ApiModelProperty("创建时间")
    private Date createdTime;


    @ApiModelProperty("更新人")
    private String updatedBy;


    @ApiModelProperty("更新时间")
    private Date updatedTime;


    @ApiModelProperty("店铺名称")
    private String shopName;


    @ApiModelProperty("SKU标签类型")
    private String sysLabelType;


    @ApiModelProperty("申请追踪状态")
    private Integer applyTrackStatus;


    @ApiModelProperty("出货清单-出货数量")
    private String shipingQty;


    @ApiModelProperty("发货批次号;一个出货清单对应多个发货批次号，一个发货批次号对应1个物料单号")
    private String sendBatchNo;


    @ApiModelProperty("差异数量")
    private BigDecimal deviationQty;


    @ApiModelProperty("ERP-数据状态")
    private String fDocumentStatus;


    @ApiModelProperty("物料信息(JSON)")
    private String materialInfo;


    @ApiModelProperty("物流单号")
    @ExcelProperty(value = "物流单号")
    private String logisticsNumber;


    @ApiModelProperty("发货日期")
    @ExcelProperty(value = "发货日期")
    private Date logisticsSendDate;


    @ApiModelProperty("运输方式")
    @ExcelProperty(value = "运输方式")
    private String logisticsMode;


    @ApiModelProperty("发货数量")
    @ExcelProperty(value = "发货数量")
    private BigDecimal logisticsSendQty;


    @ApiModelProperty("物流状态")
    @ExcelProperty(value = "物流状态")
    private String logisticsState;


    @ApiModelProperty("签收完结日期")
    @ExcelProperty(value = "签收完结日期")
    private Date signDate;


    @ApiModelProperty("签收数量")
    @ExcelProperty(value = "签收数量")
    private BigDecimal signQty;


    @ApiModelProperty("预计到达日期")
    @ExcelProperty(value = "预计到达日期")
    private String preArriveDate;

    @ApiModelProperty("备注")
    @ExcelProperty(value = "备注")
    private String remark;


    @ApiModelProperty("接收完结日期")
    @ExcelProperty(value = "接收完结日期")
    private Date receiveEndDate;


    @ApiModelProperty("接收数量")
    @ExcelProperty(value = "接收数量")
    private BigDecimal receiveQty;


    @ApiModelProperty("撤销人")
    @ExcelProperty(value = "撤销人")
    private String revokePerson;

    @ApiModelProperty("撤销日期")
    @ExcelProperty(value = "撤销日期")
    private Date revokeDate;

    @ApiModelProperty("撤销人编号")
    private String revokePersonNo;


    @ApiModelProperty("撤销原因")
    @ExcelProperty(value = "撤销原因")
    private String revokeReason;


    @ApiModelProperty("ERP撤销响应结果")
    @ExcelProperty(value = "ERP撤销响应结果")
    private String revokeErpResult;


}
