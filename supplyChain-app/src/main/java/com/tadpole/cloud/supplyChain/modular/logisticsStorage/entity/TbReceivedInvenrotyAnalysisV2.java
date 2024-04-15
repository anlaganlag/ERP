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
 * 来货报告;
 * @author : LSY
 * @date : 2024-3-18
 */
@TableName("Tb_Received_Invenroty_Analysis_V2")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbReceivedInvenrotyAnalysisV2 implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @TableField("Country_Code")
    private String countryCode ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    @TableField("Shop_Name_Simple")
    private String shopNameSimple ;
 
    /** sku */
    @ApiModelProperty(value = "sku")
    @TableField("Item_Sku")
    private String itemSku ;
 
    /** ShipmentID */
    @ApiModelProperty(value = "ShipmentID")
    @TableField("Shipment_ID")
    private String shipmentId ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    @TableField("Quantity")
    private Integer quantity ;
 
    /** 同步时间 */
    @ApiModelProperty(value = "同步时间")
    @TableField("sync_time")
    private Date syncTime ;


}