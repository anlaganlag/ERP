package com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity;

import java.io.Serializable;
import java.lang.*;
import lombok.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

 /**
 * 亚马逊货件-明细-按SKU的汇总;
 * @author : LSY
 * @date : 2023-12-29
 */
@TableName("tb_logistics_pack_list_det")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPackListDet implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 编号 */
    @ApiModelProperty(value = "编号")
    @TableId (value = "ship_det_id", type = IdType.AUTO)
    @TableField("ship_det_id")
    private BigDecimal shipDetId ;
 
    /** 票单号 */
    @ApiModelProperty(value = "票单号")
    @TableField("pack_code")
    private String packCode ;
 
    /** Shipment ID */
    @ApiModelProperty(value = "Shipment ID")
    @TableField("shipment_id")
    private String shipmentId ;
 
    /** MerchantSKU */
    @ApiModelProperty(value = "MerchantSKU")
    @TableField("merchant_sku")
    private String merchantSku ;
 
    /** ASIN */
    @ApiModelProperty(value = "ASIN")
    @TableField("asin")
    private String asin ;
 
    /** Title */
    @ApiModelProperty(value = "Title")
    @TableField("title")
    private String title ;
 
    /** FNSKU */
    @ApiModelProperty(value = "FNSKU")
    @TableField("fnsku")
    private String fnsku ;
 
    /** ExternalID */
    @ApiModelProperty(value = "ExternalID")
    @TableField("external_id")
    private String externalId ;
 
    /** PrepType */
    @ApiModelProperty(value = "PrepType")
    @TableField("prep_type")
    private String prepType ;
 
    /** WhoWillLable */
    @ApiModelProperty(value = "WhoWillLable")
    @TableField("who_will_lable")
    private String whoWillLable ;
 
    /** ExpectedQTY */
    @ApiModelProperty(value = "ExpectedQTY")
    @TableField("expected_qty")
    private Integer expectedQty ;
 
    /** BoxedQTY */
    @ApiModelProperty(value = "BoxedQTY")
    @TableField("boxed_qty")
    private Integer boxedQty ;
 
    /** MaxBoxNum */
    @ApiModelProperty(value = "MaxBoxNum")
    @TableField("max_box_num")
    private Integer maxBoxNum ;
 
    /** 货件实际状态 */
    @ApiModelProperty(value = "货件实际状态")
    @TableField("shipment_real_status")
    private String shipmentRealStatus ;
 
    /** 系统标识字段新版导入文件 */
    @ApiModelProperty(value = "系统标识字段新版导入文件")
    @TableField("pack_list_code")
    private String packListCode ;
 
    /** 接收数量 */
    @ApiModelProperty(value = "接收数量")
    @TableField("bus_received_qty")
    private Integer busReceivedQty ;
 
    /** 发货数量 */
    @ApiModelProperty(value = "发货数量")
    @TableField("bus_shipped_qty")
    private Integer busShippedQty ;
 
    /** 最新更新时间 */
    @ApiModelProperty(value = "最新更新时间")
    @TableField("bus_sys_update_date")
    private Date busSysUpdateDate ;


}