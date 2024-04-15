package com.tadpole.cloud.supplyChain.modular.logistics.model.params;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import cn.stylefeng.guns.cloud.model.web.request.BaseRequest;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * ;
 * @author : LSY
 * @date : 2024-3-14
 */
@ApiModel(value = "",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstLogisticsEstimateFeeDetParam extends BaseRequest implements Serializable,BaseValidatingParam{
 private static final long serialVersionUID = 1L;
 
    /**  */
    @ApiModelProperty(value = "明细ID")
    private String id ;
 
    /**  */
    @ApiModelProperty(value = "主行ID")
    private String estId ;
 
    /**  */
    @ApiModelProperty(value = "FBA配置费")
    private String fbaconfigFee ;
 
    /**  */
    @ApiModelProperty(value = "FBA币种")
    private String fbaconfigCurrency ;
 
    /**  */
    @ApiModelProperty(value = "物流商名称")
    private String lpname ;
 
    /**  */
    @ApiModelProperty(value = "站点")
    private String site ;
 
    /**  */
    @ApiModelProperty(value = "货运方式1")
    private String freightCompany ;
 
    /**  */
    @ApiModelProperty(value = "运输方式")
    private String transportType ;
 
    /**  */
    @ApiModelProperty(value = "物流渠道")
    private String logisticsChannel ;
 
    /**  */
    @ApiModelProperty(value = "红蓝单")
    private String orderType ;
 
    /**  */
    @ApiModelProperty(value = "是否含税")
    private String hasTax ;
 
    /**  */
    @ApiModelProperty(value = "shipmentId")
    private String shipmentId ;
 
    /**  */
    @ApiModelProperty(value = "shipTo")
    private String shipTo ;
 
    /**  */
    @ApiModelProperty(value = "分区号")
    private String lspNum ;
 
    /**  */
    @ApiModelProperty(value = "packlist")
    private String packlist ;
 
    /**  */
    @ApiModelProperty(value = "邮编")
    private String postcode ;
 
    /**  */
    @ApiModelProperty(value = "重量KG")
    private Double weightKg ;
 
    /**  */
    @ApiModelProperty(value = "体积重KG")
    private Double volweightKg ;
 
    /**  */
    @ApiModelProperty(value = "计费类型")
    private String confirmFeeType ;
 
    /**  */
    @ApiModelProperty(value = "计费量KG")
    private Double confirmCountFee ;
 
    /**  */
    @ApiModelProperty(value = "物流费CNY")
    private Double logisticsFee ;
 
    /**  */
    @ApiModelProperty(value = "燃油附加费CNY")
    private Double oilfee ;
 
    /**  */
    @ApiModelProperty(value = "旺季附加费CNY")
    private Double busyseasonFee ;
 
    /**  */
    @ApiModelProperty(value = "附加费及杂费CNY")
    private Double othersFee ;
 
    /**  */
    @ApiModelProperty(value = "报关费CNY")
    private Double applyCustomsfee ;
 
    /**  */
    @ApiModelProperty(value = "清关费CNY")
    private Double clearCustomsfee ;
 
    /**  */
    @ApiModelProperty(value = "费用合计CNY")
    private Double toalFee ;
 
    /**  */
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime ;
 
    /**  */
    @ApiModelProperty(value = "更新人")
    private String updatedBy ;
 
    /**  */
    @ApiModelProperty(value = "创建人")
    private String createdBy ;
 
    /**  */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime ;
 
    /**  */

    /**  */
    @ApiModelProperty(value = "费用合计CNY")
    private Double totalFee ;
 
    /**  */
    @ApiModelProperty(value = "操作人")
    private String perName ;
 
    /**  */
    @ApiModelProperty(value = "操作人工号")
    private String perCode ;
 



    @ApiModelProperty(value = "测算日期")
    private Date estDate ;

   @ApiModelProperty(value = "测算日期起始")
   private Date estDateStart ;

   @ApiModelProperty(value = "测算日期完结")
   private Date estDateEnd ;

    @ApiModelProperty(value = "费用测算编号列表")
    private List<String> estIdList;



   @ApiModelProperty(value = "shipmentId列表")
   private List<String> shipmentIdList;



   /** 箱号 */
   @ApiModelProperty(value = "箱号")
   private String boxNum ;


   /** 箱数 */
   @ApiModelProperty(value = "箱数")
   private Double boxCount ;


   /** 商品数量 */
   @ApiModelProperty(value = "商品数量")
   private Double qty ; ;

   /** 新shipmentId */
   @ApiModelProperty(value = "新shipmentId")
   private String newShipmentId;

   @ApiModelProperty(value = "人工录入单价")
   private Integer isEntryUnitPrice;

   @ApiModelProperty(value = "录入单价费用(CNY/KG)")
   private Double entryUnitPrice;

}