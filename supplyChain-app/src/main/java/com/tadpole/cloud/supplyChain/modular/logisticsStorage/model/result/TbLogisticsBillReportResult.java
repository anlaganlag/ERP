package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.*;
import java.lang.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

 /**
 * 物流账单报告;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流账单报告",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsBillReportResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** ID */
    @ApiModelProperty(value = "ID")
    @ExcelProperty(value ="ID")
    private BigDecimal id ;
 
    /** 物流跟踪单号（头程物流单号） */
    @ApiModelProperty(value = "物流跟踪单号（头程物流单号）")
    @ExcelProperty(value ="物流跟踪单号（头程物流单号）")
    private String lhrOddNum ;
 
    /** 金畅出货日期 */
    @ApiModelProperty(value = "金畅出货日期")
    @ExcelProperty(value ="金畅出货日期")
    private Date lhrSendGoodDate ;
 
    /** 店铺简称 */
    @ApiModelProperty(value = "店铺简称")
    @ExcelProperty(value ="店铺简称")
    private String shopNameSimple ;
 
    /** 国家编码 */
    @ApiModelProperty(value = "国家编码")
    @ExcelProperty(value ="国家编码")
    private String countryCode ;
 
    /** 物流账号 */
    @ApiModelProperty(value = "物流账号")
    @ExcelProperty(value ="物流账号")
    private String lcCode ;
 
    /** 是否报关 */
    @ApiModelProperty(value = "是否报关")
    @ExcelProperty(value ="是否报关")
    private Integer logpIsEntry ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1")
    @ExcelProperty(value ="货运方式1")
    private String logTraMode1 ;
 
    /** 货运方式2 */
    @ApiModelProperty(value = "货运方式2")
    @ExcelProperty(value ="货运方式2")
    private String logTraMode2 ;
 
    /** 红蓝单 */
    @ApiModelProperty(value = "红蓝单")
    @ExcelProperty(value ="红蓝单")
    private String logRedOrBlueList ;
 
    /** 发货批次号 */
    @ApiModelProperty(value = "发货批次号")
    @ExcelProperty(value ="发货批次号")
    private String lhrCode ;
 
    /** 出仓发货件数 */
    @ApiModelProperty(value = "出仓发货件数")
    @ExcelProperty(value ="出仓发货件数")
    private Integer lhrOutGoodNum ;
 
    /** 出仓发货重量 */
    @ApiModelProperty(value = "出仓发货重量")
    @ExcelProperty(value ="出仓发货重量")
    private BigDecimal lhrOutGoodWeight ;
 
    /** 计费重量 */
    @ApiModelProperty(value = "计费重量")
    @ExcelProperty(value ="计费重量")
    private BigDecimal chargeableWeight ;
 
    /** 差异重量 */
    @ApiModelProperty(value = "差异重量")
    @ExcelProperty(value ="差异重量")
    private BigDecimal diffWeight ;
 
    /** 总费用 */
    @ApiModelProperty(value = "总费用")
    @ExcelProperty(value ="总费用")
    private BigDecimal totalCost ;
 
    /** 燃油费率 */
    @ApiModelProperty(value = "燃油费率")
    @ExcelProperty(value ="燃油费率")
    private BigDecimal lfrRuelRate ;
 
    /** 单价费用 */
    @ApiModelProperty(value = "单价费用")
    @ExcelProperty(value ="单价费用")
    private BigDecimal lpcswUnitPrice ;
 
    /** ShipmentID */
    @ApiModelProperty(value = "ShipmentID")
    @ExcelProperty(value ="ShipmentID")
    private String shipmentId ;
 
    /** Quantity */
    @ApiModelProperty(value = "Quantity")
    @ExcelProperty(value ="Quantity")
    private Integer quantity ;
 
    /** 物流对账单号 */
    @ApiModelProperty(value = "物流对账单号")
    @ExcelProperty(value ="物流对账单号")
    private String billNum ;
 
    /** 状态 */
    @ApiModelProperty(value = "状态")
    @ExcelProperty(value ="状态")
    private String state ;
 
    /** 物流审核时间 */
    @ApiModelProperty(value = "物流审核时间")
    @ExcelProperty(value ="物流审核时间")
    private Date logisticsAudit ;
 
    /** 财务审核时间 */
    @ApiModelProperty(value = "财务审核时间")
    @ExcelProperty(value ="财务审核时间")
    private Date financeAudit ;
 
    /** 是否支付 */
    @ApiModelProperty(value = "是否支付")
    @ExcelProperty(value ="是否支付")
    private Integer isPayment ;
 
    /** 纳税时间 */
    @ApiModelProperty(value = "纳税时间")
    @ExcelProperty(value ="纳税时间")
    private Date taxPayment ;
 
    /** 物流成本核算时间 */
    @ApiModelProperty(value = "物流成本核算时间")
    @ExcelProperty(value ="物流成本核算时间")
    private Date logisticsCosts ;


}