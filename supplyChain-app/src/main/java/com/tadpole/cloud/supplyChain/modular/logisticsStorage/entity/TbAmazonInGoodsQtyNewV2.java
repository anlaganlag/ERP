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
 * Amazon在途库存报表;
 * @author : LSY
 * @date : 2024-3-18
 */
@TableName("Tb_Amazon_In_Goods_Qty_New_V2")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbAmazonInGoodsQtyNewV2 implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    @TableId (value = "ID", type = IdType.AUTO)
    @TableField("ID")
    private BigDecimal id ;
 
    /** SKU */
    @ApiModelProperty(value = "SKU")
    @TableField("SKU")
    private String sku ;
 
    /** 物料编码 */
    @ApiModelProperty(value = "物料编码")
    @TableField("MAT_CODE")
    private String matCode ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    @TableField("shop_Name_Simple")
    private String shopNameSimple ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @TableField("country_Code")
    private String countryCode ;
 
    /** FBA号 */
    @ApiModelProperty(value = "FBA号")
    @TableField("Shipment_ID")
    private String shipmentId ;
 
    /** 统计日期 */
    @ApiModelProperty(value = "统计日期")
    @TableField("sys_Statistical_Date")
    private Date sysStatisticalDate ;
 
    /** 总来货数量 */
    @ApiModelProperty(value = "总来货数量")
    @TableField("all_Qty")
    private Integer allQty ;
 
    /** 物流待发数 */
    @ApiModelProperty(value = "物流待发数")
    @TableField("stay_Deliver_Qty")
    private Integer stayDeliverQty ;
 
    /** 海运 */
    @ApiModelProperty(value = "海运")
    @TableField("shipping_Qty")
    private Integer shippingQty ;
 
    /** 铁运 */
    @ApiModelProperty(value = "铁运")
    @TableField("train_Qty")
    private Integer trainQty ;
 
    /** 卡航 */
    @ApiModelProperty(value = "卡航")
    @TableField("car_Qty")
    private Integer carQty ;
 
    /** 空运 */
    @ApiModelProperty(value = "空运")
    @TableField("air_Qty")
    private Integer airQty ;
 
    /** 同步时间 */
    @ApiModelProperty(value = "同步时间")
    @TableField("sync_time")
    private Date syncTime ;


}