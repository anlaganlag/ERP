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
 * 发货单数据;
 * @author : LSY
 * @date : 2024-3-18
 */
@TableName("Tb_Bsc_Overseas_Way_Analysis_New_V2")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbBscOverseasWayAnalysisNewV2 implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @TableField("sku")
    private String sku ;
 
    /** 物料编码 */
    @ApiModelProperty(value = "物料编码")
    @TableField("mat_Code")
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
    @TableField("shipment_ID")
    private String shipmentId ;
 
    /** 运输方式 */
    @ApiModelProperty(value = "运输方式")
    @TableField("deliver_Type")
    private String deliverType ;
 
    /** 状态 */
    @ApiModelProperty(value = "状态")
    @TableField("status")
    private String status ;
 
    /** 货件实际状态 */
    @ApiModelProperty(value = "货件实际状态")
    @TableField("Shipment_Real_Status")
    private String shipmentRealStatus ;
 
    /** 出货数量 */
    @ApiModelProperty(value = "出货数量")
    @TableField("delivery_Num")
    private Integer deliveryNum ;
 
    /** 同步时间 */
    @ApiModelProperty(value = "同步时间")
    @TableField("sync_time")
    private Date syncTime ;


}