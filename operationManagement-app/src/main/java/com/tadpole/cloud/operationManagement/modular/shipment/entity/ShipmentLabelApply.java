package com.tadpole.cloud.operationManagement.modular.shipment.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("SHIPMENT_LABEL_APPLY")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class ShipmentLabelApply implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** ID;明细ID */
    @TableId(value = "Id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private BigDecimal id ;
 
    /** 批次号 */
    @ApiModelProperty(value = "批次号")
    @TableField("BATCH_NO")
    private String batchNo ;
 
    /** 交货地点编号;交货地点编号 */
    @ApiModelProperty(value = "交货地点编号")
    @TableField("DELIVERYPOINT_NO")
    private String deliverypointNo ;
 
    /** 交货地点;默认联泰发货，还可以供应商发货 */
    @ApiModelProperty(value = "交货地点")
    @TableField("DELIVERYPOINT")
    private String deliverypoint ;
 
    /** 平台 */
    @ApiModelProperty(value = "平台")
    @TableField("PLATFORM")
    private String platform ;
 
    /** 区域 */
    @ApiModelProperty(value = "区域")
    @TableField("AREA")
    private String area ;
 
    /** 部门 */
    @ApiModelProperty(value = "部门")
    @TableField("DEPARTMENT")
    private String department ;
 
    /** Team */
    @ApiModelProperty(value = "Team")
    @TableField("TEAM")
    private String team ;
 
    /** 物料编码 */
    @ApiModelProperty(value = "物料编码")
    @TableField("MATERIAL_CODE")
    private String materialCode ;
 
    /** 物料信息;物料信息json字符串 */
    @ApiModelProperty(value = "物料信息")
    @TableField("MATERIAL_INFO")
    private String materialInfo ;
 
    /** 产品名称 */
    @ApiModelProperty(value = "产品名称")
    @TableField("PRODUCT_NAME")
    private String productName ;
 
    /** ASIN */
    @ApiModelProperty(value = "ASIN")
    @TableField("ASIN")
    private String asin ;
 
    /** 运营大类 */
    @ApiModelProperty(value = "运营大类")
    @TableField("PRODUCT_TYPE")
    private String productType ;
 
    /** 店铺名称+站点 */
    @ApiModelProperty(value = "店铺名称+站点")
    @TableField("SHOP_NAME")
    private String shopName ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @TableField("SYS_SITE")
    private String sysSite ;
 
    /** SKU */
    @ApiModelProperty(value = "SKU")
    @TableField("SKU")
    private String sku ;
 
    /** SKU标签类型 */
    @ApiModelProperty(value = "SKU标签类型")
    @TableField("SYS_LABEL_TYPE")
    private String sysLabelType ;
 
    /** 店铺名称-简称账号 */
    @ApiModelProperty(value = "店铺名称-简称账号")
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName ;
 
    /** FBA号 */
    @ApiModelProperty(value = "FBA号")
    @TableField("FBA_NO")
    private String fbaNo ;
 
    /** 调拨单号;同步到k3的调拨单号 */
    @ApiModelProperty(value = "调拨单号")
    @TableField("BILL_NO")
    private String billNo ;
 
    /** 调入仓库;接收仓库 */
    @ApiModelProperty(value = "调入仓库")
    @TableField("RECEIVE_WAREHOUSE")
    private String receiveWarehouse ;
 
    /** 调入仓库code */
    @ApiModelProperty(value = "调入仓库code")
    @TableField("RECEIVE_WAREHOUSE_CODE")
    private String receiveWarehouseCode ;
 
    /** Amazon_账号_站点_仓库 对应的组织编号 */
    @ApiModelProperty(value = "Amazon_账号_站点_仓库 对应的组织编号")
    @TableField("ORG_CODE")
    private String orgCode ;
 
    /** 标签打印数量 */
    @ApiModelProperty(value = "标签打印数量")
    @TableField("PRINT_QTY")
    private Integer printQty ;
 
    /** 发货方式;运输方式 */
    @ApiModelProperty(value = "发货方式")
    @TableField("TRANSPORTATION_TYPE")
    private String transportationType ;
 
    /** UNW类型 */
    @ApiModelProperty(value = "UNW类型")
    @TableField("UNW_TYPE")
    private String unwType ;
 
    /** 备注1 */
    @ApiModelProperty(value = "备注1")
    @TableField("REMARK1")
    private String remark1 ;
 
    /** 备注2 */
    @ApiModelProperty(value = "备注2")
    @TableField("REMARK2")
    private String remark2 ;
 
    /** 备注3 */
    @ApiModelProperty(value = "备注3")
    @TableField("REMARK3")
    private String remark3 ;
 
    /** 数据来源类型-BI推荐-手动选择-表格导入;数据来源类型-BI推荐-手动选择-表格导入 */
    @ApiModelProperty(value = "数据来源类型-BI推荐-手动选择-表格导入")
    @TableField("DATA_SOURCE_TYPE")
    private String dataSourceType ;
 
    /** 申请日期 */
    @ApiModelProperty(value = "申请日期")
    @TableField("APPLY_DATE")
    private Date applyDate ;
 
    /** 申请状态;数据初始化为0--已提交1 */
    @ApiModelProperty(value = "申请状态")
    @TableField("APPLY_STATUS")
    private Integer applyStatus ;
 
    /** 申请人 */
    @ApiModelProperty(value = "申请人")
    @TableField("APLLY_PERSON")
    private String apllyPerson ;
 
    /** 申请人编号 */
    @ApiModelProperty(value = "申请人编号")
    @TableField("APLLY_PERSON_NO")
    private String apllyPersonNo ;
 
    /** 同步时间 */
    @ApiModelProperty(value = "同步时间")
    @TableField("SYNC_TIME")
    private Date syncTime ;
 
    /** 同步K3状态;数据初始化为0-调用K3接口成功1--失败2 */
    @ApiModelProperty(value = "同步K3状态")
    @TableField("SYNC_STATUS")
    private Integer syncStatus ;
 
    /** 同步请求参数 */
    @ApiModelProperty(value = "同步请求参数")
    @TableField("SYNC_REQUEST_MSG")
    private String syncRequestMsg ;
 
    /** 同步结果 */
    @ApiModelProperty(value = "同步结果")
    @TableField("SYNC_RESULT_MSG")
    private String syncResultMsg ;
 
    /** 已同步次数，默认0 */
    @ApiModelProperty(value = "已同步次数，默认0")
    @TableField("SYNC_COUNT")
    private Integer syncCount ;
 
    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    @TableField("CREATED_BY")
    private String createdBy ;
 
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @TableField("CREATED_TIME")
    private Date createdTime ;
 
    /** 更新人 */
    @ApiModelProperty(value = "更新人")
    @TableField("UPDATED_BY")
    private String updatedBy ;
 
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATED_TIME")
    private Date updatedTime ;


}