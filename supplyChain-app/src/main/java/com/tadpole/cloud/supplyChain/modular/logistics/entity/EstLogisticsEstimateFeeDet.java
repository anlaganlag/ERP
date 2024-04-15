package com.tadpole.cloud.supplyChain.modular.logistics.entity;

import java.io.Serializable;
import java.lang.*;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import oracle.sql.DATE;
import oracle.sql.NUMBER;

import java.util.Date;

 /**
 * ;
 * @author : LSY
 * @date : 2024-3-14
 */
@TableName("EST_LOGISTICS_ESTIMATE_FEE_DET")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class EstLogisticsEstimateFeeDet implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /**  */
    @ApiModelProperty(value = "ID")
    @TableId (value = "id", type = IdType.ASSIGN_ID)
    @TableField("id")
    private String id ;
 
    /**  */
    @ApiModelProperty(value = "主行ID")
    @TableField("EST_ID")
    private String estId ;
 
    /**  */
    @ApiModelProperty(value = "FBA配置费CNY")
    @TableField("fbaconfig_fee")
    private Double fbaconfigFee ;
 
    /**  */
    @ApiModelProperty(value = "FBA配置费币种")
    @TableField("fbaconfig_currency")
    private String fbaconfigCurrency ;
 
    /**  */
    @ApiModelProperty(value = "物流商名称")
    @TableField("lpName")
    private String lpname ;
 
    /**  */
    @ApiModelProperty(value = "站点")
    @TableField("Site")
    private String site ;
 
    /**  */
    @ApiModelProperty(value = "货运方式1")
    @TableField("freight_Company")
    private String freightCompany ;
 
    /**  */
    @ApiModelProperty(value = "运输方式")
    @TableField("transport_Type")
    private String transportType ;
 
    /**  */
    @ApiModelProperty(value = "物流渠道")
    @TableField("logistics_Channel")
    private String logisticsChannel ;
 
    /**  */
    @ApiModelProperty(value = "红蓝单")
    @TableField("order_Type")
    private String orderType ;
 
    /**  */
    @ApiModelProperty(value = "是否含税")
    @TableField("has_Tax")
    private String hasTax ;
 
    /**  */
    @ApiModelProperty(value = "SHIPMENT_ID")
    @TableField("shipment_Id")
    private String shipmentId ;
 
    /**  */
    @ApiModelProperty(value = "SHIP_TO")
    @TableField("Ship_to")
    private String shipTo ;
 
    /**  */
    @ApiModelProperty(value = "国家分区号")
    @TableField("lsp_Num")
    private String lspNum ;
 
    /**  */
    @ApiModelProperty(value = "Packlist")
    @TableField("Packlist")
    private String packlist ;
 
    /**  */
    @ApiModelProperty(value = "邮编")
    @TableField("postCode")
    private String postcode ;
 
    /**  */
    @ApiModelProperty(value = "重量KG")
    @TableField("weight_kg")
    private Double weightKg ;
 
    /**  */
    @ApiModelProperty(value = "体积重KG")
    @TableField("volweight_kg")
    private Double volweightKg ;
 
    /**  */
    @ApiModelProperty(value = "计费类型")
    @TableField("confirm_fee_type")
    private String confirmFeeType ;

    /**  */
    @ApiModelProperty(value = "计费量KG")
    @TableField("confirm_count_fee")
    private Double confirmCountFee ;
 
    /**  */
    @ApiModelProperty(value = "物流费CNY")
    @TableField("logistics_Fee")
    private Double logisticsFee ;
 
    /**  */
    @ApiModelProperty(value = "燃油附加费CNY")
    @TableField("oilFee")
    private Double oilfee ;
 
    /**  */
    @ApiModelProperty(value = "旺季附加费CNY")
    @TableField("busySeason_Fee")
    private Double busyseasonFee ;
 
    /**  */
    @ApiModelProperty(value = "附加费及杂费CNY")
    @TableField("others_Fee")
    private Double othersFee ;
 
    /**  */
    @ApiModelProperty(value = "报关费CNY")
    @TableField("apply_customsFee")
    private Double applyCustomsfee ;
 
    /**  */
    @ApiModelProperty(value = "清关费CNY")
    @TableField("clear_CustomsFee")
    private Double clearCustomsfee ;
 
    /**  */
    @ApiModelProperty(value = "费用合计CNY")
    @TableField("total_fee")
    private Double totalFee ;
 
    /**  */
    @ApiModelProperty(value = "更新时间")
    @TableField("updated_time")
    private Date updatedTime ;
 
    /**  */
    @ApiModelProperty(value = "更新人")
    @TableField("updated_by")
    private String updatedBy ;
 
    /**  */
    @ApiModelProperty(value = "创建人")
    @TableField("created_by")
    private String createdBy ;
 
    /**  */
    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Date createdTime ;
 


    /**  */
    @ApiModelProperty(value = "操作人")
    @TableField("per_name")
    private String perName ;
 
    /**  */
    @ApiModelProperty(value = "操作人工号")
    @TableField("per_code")
    private String perCode ;
 
    /**  */


    @ApiModelProperty(value = "测算日期")
    @TableField("est_date")
    private Date estDate ;



    @ApiModelProperty(value = "箱号")
    @TableField(value ="box_num")
    private String boxNum ;

    @ApiModelProperty(value = "箱数")
    @TableField(value ="box_count")
    private Double boxCount ;


   /** 商品数量 */
   @ApiModelProperty(value = "商品数量")
   private Double qty ;


    @ApiModelProperty(value = "人工录入单价")
    private Integer isEntryUnitPrice;

    @ApiModelProperty(value = "录入单价费用(CNY/KG)")
    private Double entryUnitPrice;
}