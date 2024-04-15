package com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity;

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
 * 亚马逊货件;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_pack_list")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPackList implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    @TableId (value = "ID", type = IdType.AUTO)
    @TableField("ID")
    private BigDecimal id ;
 
    /** 票单号 */
    @ApiModelProperty(value = "票单号")
    @TableField("pack_code")
    private String packCode ;
 
    /** PlanName */
    @ApiModelProperty(value = "PlanName")
    @TableField("plan_name")
    private String planName ;
 
    /** 模板上传日期 */
    @ApiModelProperty(value = "模板上传日期")
    @TableField("pack_temp_up_date")
    private Date packTempUpDate ;
 
    /** 模板上传员工姓名 */
    @ApiModelProperty(value = "模板上传员工姓名")
    @TableField("pack_temp_up_per_name")
    private String packTempUpPerName ;
 
    /** 模板文件名称 */
    @ApiModelProperty(value = "模板文件名称")
    @TableField("pack_temp_name")
    private String packTempName ;
 
    /** Shipment ID */
    @ApiModelProperty(value = "Shipment ID")
    @TableField("shipment_id")
    private String shipmentId ;
 
    /** Plan ID */
    @ApiModelProperty(value = "Plan ID")
    @TableField("plan_id")
    private String planId ;
 
    /** Ship To */
    @ApiModelProperty(value = "Ship To")
    @TableField("ship_to")
    private String shipTo ;
 
    /** ToTal SKUs */
    @ApiModelProperty(value = "ToTal SKUs")
    @TableField("to_tal_skus")
    private Integer toTalSkus ;
 
    /** Total Units */
    @ApiModelProperty(value = "Total Units")
    @TableField("total_units")
    private Integer totalUnits ;
 
    /** 文件生成日期 */
    @ApiModelProperty(value = "文件生成日期")
    @TableField("pack_gen_date")
    private Date packGenDate ;
 
    /** 生成操作员工姓名 */
    @ApiModelProperty(value = "生成操作员工姓名")
    @TableField("pack_gen_per_name")
    private String packGenPerName ;
 
    /** 是否上传 */
    @ApiModelProperty(value = "是否上传")
    @TableField("is_upload")
    private Integer isUpload ;
 
    /** Amazon接收状态 */
    @ApiModelProperty(value = "Amazon接收状态")
    @TableField("pack_ama_rec_state")
    private String packAmaRecState ;
 
    /** packAmaRecStatePerName */
    @ApiModelProperty(value = "packAmaRecStatePerName")
    @TableField("pack_ama_rec_state_per_name")
    private String packAmaRecStatePerName ;
 
    /** packAmaRecStateDatetime */
    @ApiModelProperty(value = "packAmaRecStateDatetime")
    @TableField("pack_ama_rec_state_datetime")
    private Date packAmaRecStateDatetime ;
 
    /** 国家编码 */
    @ApiModelProperty(value = "国家编码")
    @TableField("country_code")
    private String countryCode ;
 
    /** 店铺简称 */
    @ApiModelProperty(value = "店铺简称")
    @TableField("shop_name_simple")
    private String shopNameSimple ;
 
    /** 出货仓类型 */
    @ApiModelProperty(value = "出货仓类型")
    @TableField("com_warehouse_type")
    private String comWarehouseType ;
 
    /** 出货仓名称 */
    @ApiModelProperty(value = "出货仓名称")
    @TableField("com_warehouse_name")
    private String comWarehouseName ;
 
    /** MaxBoXNum */
    @ApiModelProperty(value = "MaxBoXNum")
    @TableField("max_bo_xnum")
    private Integer maxBoXnum ;
 
    /** 货件实际状态 */
    @ApiModelProperty(value = "货件实际状态")
    @TableField("pack_shipment_real_status")
    private String packShipmentRealStatus ;
 
    /** 备注(人工导入|系统生成) */
    @ApiModelProperty(value = "备注(人工导入|系统生成)")
    @TableField("bus_pack_remark")
    private String busPackRemark ;
 
    /** Shipment Name */
    @ApiModelProperty(value = "Shipment Name")
    @TableField("bus_shipment_name")
    private String busShipmentName ;
 
    /** API处理状态(1-上传excel,2-上传xml,3-完成创建,4-下载箱标,5-完成) */
    @ApiModelProperty(value = "API处理状态(1-上传excel,2-上传xml,3-完成创建,4-下载箱标,5-完成)")
    @TableField("bus_api_process_status")
    private Integer busApiProcessStatus ;
 
    /** 最后更新日期 */
    @ApiModelProperty(value = "最后更新日期")
    @TableField("sys_update_date")
    private Date sysUpdateDate ;
 
    /** 系统标识字段新版导入文件 */
    @ApiModelProperty(value = "系统标识字段新版导入文件")
    @TableField("pack_list_code")
    private String packListCode ;
 
    /** 亚马逊货件接收总数量 */
    @ApiModelProperty(value = "亚马逊货件接收总数量")
    @TableField("bus_received_qty")
    private Integer busReceivedQty ;


}