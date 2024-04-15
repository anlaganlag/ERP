package com.tadpole.cloud.operationManagement.modular.shipment.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

 /**
 * 发货标签申请;
 * @author : LSY
 * @date : 2024-3-21
 */
@ApiModel(value = "发货标签申请",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class ShipmentLabelApplyResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** ID;明细ID */
    @ApiModelProperty(value = "ID")
    @ExcelProperty(value ="ID")
    private BigDecimal id ;
 
    /** 批次号 */
    @ApiModelProperty(value = "批次号")
    @ExcelProperty(value ="批次号")
    private String batchNo ;
 
    /** 交货地点编号;交货地点编号 */
    @ApiModelProperty(value = "交货地点编号")
    @ExcelProperty(value ="交货地点编号")
    private String deliverypointNo ;
 
    /** 交货地点;默认联泰发货，还可以供应商发货 */
    @ApiModelProperty(value = "交货地点")
    @ExcelProperty(value ="交货地点")
    private String deliverypoint ;
 
    /** 平台 */
    @ApiModelProperty(value = "平台")
    @ExcelProperty(value ="平台")
    private String platform ;
 
    /** 区域 */
    @ApiModelProperty(value = "区域")
    @ExcelProperty(value ="区域")
    private String area ;
 
    /** 部门 */
    @ApiModelProperty(value = "部门")
    @ExcelProperty(value ="部门")
    private String department ;
 
    /** Team */
    @ApiModelProperty(value = "Team")
    @ExcelProperty(value ="Team")
    private String team ;
 
    /** 物料编码 */
    @ApiModelProperty(value = "物料编码")
    @ExcelProperty(value ="物料编码")
    private String materialCode ;
 
    /** 物料信息;物料信息json字符串 */
    @ApiModelProperty(value = "物料信息")
    @ExcelProperty(value ="物料信息")
    private String materialInfo ;
 
    /** 产品名称 */
    @ApiModelProperty(value = "产品名称")
    @ExcelProperty(value ="产品名称")
    private String productName ;
 
    /** ASIN */
    @ApiModelProperty(value = "ASIN")
    @ExcelProperty(value ="ASIN")
    private String asin ;
 
    /** 运营大类 */
    @ApiModelProperty(value = "运营大类")
    @ExcelProperty(value ="运营大类")
    private String productType ;
 
    /** 店铺名称+站点 */
    @ApiModelProperty(value = "店铺名称+站点")
    @ExcelProperty(value ="店铺名称+站点")
    private String shopName ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @ExcelProperty(value ="站点")
    private String sysSite ;
 
    /** SKU */
    @ApiModelProperty(value = "SKU")
    @ExcelProperty(value ="SKU")
    private String sku ;
 
    /** SKU标签类型 */
    @ApiModelProperty(value = "SKU标签类型")
    @ExcelProperty(value ="SKU标签类型")
    private String sysLabelType ;
 
    /** 店铺名称-简称账号 */
    @ApiModelProperty(value = "店铺名称-简称账号")
    @ExcelProperty(value ="店铺名称-简称账号")
    private String sysShopsName ;
 
    /** FBA号 */
    @ApiModelProperty(value = "FBA号")
    @ExcelProperty(value ="FBA号")
    private String fbaNo ;
 
    /** 调拨单号;同步到k3的调拨单号 */
    @ApiModelProperty(value = "调拨单号")
    @ExcelProperty(value ="调拨单号")
    private String billNo ;
 
    /** 调入仓库;接收仓库 */
    @ApiModelProperty(value = "调入仓库")
    @ExcelProperty(value ="调入仓库")
    private String receiveWarehouse ;
 
    /** 调入仓库code */
    @ApiModelProperty(value = "调入仓库code")
    @ExcelProperty(value ="调入仓库code")
    private String receiveWarehouseCode ;
 
    /** Amazon_账号_站点_仓库 对应的组织编号 */
    @ApiModelProperty(value = "Amazon_账号_站点_仓库 对应的组织编号")
    @ExcelProperty(value ="Amazon_账号_站点_仓库 对应的组织编号")
    private String orgCode ;
 
    /** 标签打印数量 */
    @ApiModelProperty(value = "标签打印数量")
    @ExcelProperty(value ="标签打印数量")
    private Integer printQty ;
 
    /** 发货方式;运输方式 */
    @ApiModelProperty(value = "发货方式")
    @ExcelProperty(value ="发货方式")
    private String transportationType ;
 
    /** UNW类型 */
    @ApiModelProperty(value = "UNW类型")
    @ExcelProperty(value ="UNW类型")
    private String unwType ;
 
    /** 备注1 */
    @ApiModelProperty(value = "备注1")
    @ExcelProperty(value ="备注1")
    private String remark1 ;
 
    /** 备注2 */
    @ApiModelProperty(value = "备注2")
    @ExcelProperty(value ="备注2")
    private String remark2 ;
 
    /** 备注3 */
    @ApiModelProperty(value = "备注3")
    @ExcelProperty(value ="备注3")
    private String remark3 ;
 
    /** 数据来源类型-BI推荐-手动选择-表格导入;数据来源类型-BI推荐-手动选择-表格导入 */
    @ApiModelProperty(value = "数据来源类型-BI推荐-手动选择-表格导入")
    @ExcelProperty(value ="数据来源类型-BI推荐-手动选择-表格导入")
    private String dataSourceType ;
 
    /** 申请日期 */
    @ApiModelProperty(value = "申请日期")
    @ExcelProperty(value ="申请日期")
    private Date applyDate ;
 
    /** 申请状态;数据初始化为0--已提交1 */
    @ApiModelProperty(value = "申请状态")
    @ExcelProperty(value ="申请状态")
    private Integer applyStatus ;
 
    /** 申请人 */
    @ApiModelProperty(value = "申请人")
    @ExcelProperty(value ="申请人")
    private String apllyPerson ;
 
    /** 申请人编号 */
    @ApiModelProperty(value = "申请人编号")
    @ExcelProperty(value ="申请人编号")
    private String apllyPersonNo ;
 
    /** 同步时间 */
    @ApiModelProperty(value = "同步时间")
    @ExcelProperty(value ="同步时间")
    private Date syncTime ;
 
    /** 同步K3状态;数据初始化为0-调用K3接口成功1--失败2 */
    @ApiModelProperty(value = "同步K3状态")
    @ExcelProperty(value ="同步K3状态")
    private Integer syncStatus ;
 
    /** 同步请求参数 */
    @ApiModelProperty(value = "同步请求参数")
    @ExcelProperty(value ="同步请求参数")
    private String syncRequestMsg ;
 
    /** 同步结果 */
    @ApiModelProperty(value = "同步结果")
    @ExcelProperty(value ="同步结果")
    private String syncResultMsg ;
 
    /** 已同步次数，默认0 */
    @ApiModelProperty(value = "已同步次数，默认0")
    @ExcelProperty(value ="已同步次数，默认0")
    private Integer syncCount ;
 
    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    @ExcelProperty(value ="创建人")
    private String createdBy ;
 
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @ExcelProperty(value ="创建时间")
    private Date createdTime ;
 
    /** 更新人 */
    @ApiModelProperty(value = "更新人")
    @ExcelProperty(value ="更新人")
    private String updatedBy ;
 
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @ExcelProperty(value ="更新时间")
    private Date updatedTime ;


}