package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * 亚马逊货件;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "亚马逊货件",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPackListResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    @ExcelProperty(value ="ID")
    private BigDecimal id ;
 
    /** 票单号 */
    @ApiModelProperty(value = "票单号")
    @ExcelProperty(value ="票单号")
    private String packCode ;
 
    /** PlanName */
    @ApiModelProperty(value = "PlanName")
    @ExcelProperty(value ="PlanName")
    private String planName ;
 
    /** 模板上传日期 */
    @ApiModelProperty(value = "模板上传日期")
    @ExcelProperty(value ="模板上传日期")
    private Date packTempUpDate ;
 
    /** 模板上传员工姓名 */
    @ApiModelProperty(value = "模板上传员工姓名")
    @ExcelProperty(value ="模板上传员工姓名")
    private String packTempUpPerName ;
 
    /** 模板文件名称 */
    @ApiModelProperty(value = "模板文件名称")
    @ExcelProperty(value ="模板文件名称")
    private String packTempName ;
 
    /** Shipment ID */
    @ApiModelProperty(value = "Shipment ID")
    @ExcelProperty(value ="Shipment ID")
    private String shipmentId ;
 
    /** Plan ID */
    @ApiModelProperty(value = "Plan ID")
    @ExcelProperty(value ="Plan ID")
    private String planId ;
 
    /** Ship To */
    @ApiModelProperty(value = "Ship To")
    @ExcelProperty(value ="Ship To")
    private String shipTo ;
 
    /** ToTal SKUs */
    @ApiModelProperty(value = "ToTal SKUs")
    @ExcelProperty(value ="ToTal SKUs")
    private Integer toTalSkus ;
 
    /** Total Units */
    @ApiModelProperty(value = "Total Units")
    @ExcelProperty(value ="Total Units")
    private Integer totalUnits ;
 
    /** 文件生成日期 */
    @ApiModelProperty(value = "文件生成日期")
    @ExcelProperty(value ="文件生成日期")
    private Date packGenDate ;
 
    /** 生成操作员工姓名 */
    @ApiModelProperty(value = "生成操作员工姓名")
    @ExcelProperty(value ="生成操作员工姓名")
    private String packGenPerName ;
 
    /** 是否上传 */
    @ApiModelProperty(value = "是否上传")
    @ExcelProperty(value ="是否上传")
    private Integer isUpload ;
 
    /** Amazon接收状态 */
    @ApiModelProperty(value = "Amazon接收状态")
    @ExcelProperty(value ="Amazon接收状态")
    private String packAmaRecState ;
 
    /** packAmaRecStatePerName */
    @ApiModelProperty(value = "packAmaRecStatePerName")
    @ExcelProperty(value ="packAmaRecStatePerName")
    private String packAmaRecStatePerName ;
 
    /** packAmaRecStateDatetime */
    @ApiModelProperty(value = "packAmaRecStateDatetime")
    @ExcelProperty(value ="packAmaRecStateDatetime")
    private Date packAmaRecStateDatetime ;
 
    /** 国家编码 */
    @ApiModelProperty(value = "国家编码")
    @ExcelProperty(value ="国家编码")
    private String countryCode ;
 
    /** 店铺简称 */
    @ApiModelProperty(value = "店铺简称")
    @ExcelProperty(value ="店铺简称")
    private String shopNameSimple ;
 
    /** 出货仓类型 */
    @ApiModelProperty(value = "出货仓类型")
    @ExcelProperty(value ="出货仓类型")
    private String comWarehouseType ;
 
    /** 出货仓名称 */
    @ApiModelProperty(value = "出货仓名称")
    @ExcelProperty(value ="出货仓名称")
    private String comWarehouseName ;
 
    /** MaxBoXNum */
    @ApiModelProperty(value = "MaxBoXNum")
    @ExcelProperty(value ="MaxBoXNum")
    private Integer maxBoXnum ;
 
    /** 货件实际状态 */
    @ApiModelProperty(value = "货件实际状态")
    @ExcelProperty(value ="货件实际状态")
    private String packShipmentRealStatus ;
 
    /** 备注(人工导入|系统生成) */
    @ApiModelProperty(value = "备注(人工导入|系统生成)")
    @ExcelProperty(value ="备注(人工导入|系统生成)")
    private String busPackRemark ;
 
    /** Shipment Name */
    @ApiModelProperty(value = "Shipment Name")
    @ExcelProperty(value ="Shipment Name")
    private String busShipmentName ;
 
    /** API处理状态(1-上传excel,2-上传xml,3-完成创建,4-下载箱标,5-完成) */
    @ApiModelProperty(value = "API处理状态(1-上传excel,2-上传xml,3-完成创建,4-下载箱标,5-完成)")
    @ExcelProperty(value ="API处理状态(1-上传excel,2-上传xml,3-完成创建,4-下载箱标,5-完成)")
    private Integer busApiProcessStatus ;
 
    /** 最后更新日期 */
    @ApiModelProperty(value = "最后更新日期")
    @ExcelProperty(value ="最后更新日期")
    private Date sysUpdateDate ;
 
    /** 系统标识字段新版导入文件 */
    @ApiModelProperty(value = "系统标识字段新版导入文件")
    @ExcelProperty(value ="系统标识字段新版导入文件")
    private String packListCode ;
 
    /** 亚马逊货件接收总数量 */
    @ApiModelProperty(value = "亚马逊货件接收总数量")
    @ExcelProperty(value ="亚马逊货件接收总数量")
    private Integer busReceivedQty ;


 }