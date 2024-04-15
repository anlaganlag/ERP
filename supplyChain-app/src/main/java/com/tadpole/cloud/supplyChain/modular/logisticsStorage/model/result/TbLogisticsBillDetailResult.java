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
 * 物流账单明细;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "物流账单明细",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsBillDetailResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 系统编号 */
    @ApiModelProperty(value = "系统编号")
    @ExcelProperty(value ="系统编号")
    private BigDecimal id ;
 
    /** 物流商编码 */
    @ApiModelProperty(value = "物流商编码")
    @ExcelProperty(value ="物流商编码")
    private String lpCode ;
 
    /** 物流账号 */
    @ApiModelProperty(value = "物流账号")
    @ExcelProperty(value ="物流账号")
    private String lcCode ;
 
    /** 物流对账单号 */
    @ApiModelProperty(value = "物流对账单号")
    @ExcelProperty(value ="物流对账单号")
    private String billNum ;
 
    /** 物流跟踪单号（头程物流单号） */
    @ApiModelProperty(value = "物流跟踪单号（头程物流单号）")
    @ExcelProperty(value ="物流跟踪单号（头程物流单号）")
    private String lhrOddNum ;
 
    /** 物流订单日期 */
    @ApiModelProperty(value = "物流订单日期")
    @ExcelProperty(value ="物流订单日期")
    private Date logisticsOrderDate ;
 
    /** 平台 */
    @ApiModelProperty(value = "平台")
    @ExcelProperty(value ="平台")
    private String elePlatformName ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    @ExcelProperty(value ="账号")
    private String shopNameSimple ;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @ExcelProperty(value ="站点")
    private String countryCode ;
 
    /** 金畅出货日期 */
    @ApiModelProperty(value = "金畅出货日期")
    @ExcelProperty(value ="金畅出货日期")
    private Date lhrSendGoodDate ;
 
    /** 转单号 */
    @ApiModelProperty(value = "转单号")
    @ExcelProperty(value ="转单号")
    private String transferNum ;
 
    /** 渠道及派送 */
    @ApiModelProperty(value = "渠道及派送")
    @ExcelProperty(value ="渠道及派送")
    private String channel ;
 
    /** 申报价值 */
    @ApiModelProperty(value = "申报价值")
    @ExcelProperty(value ="申报价值")
    private String declaredVal ;
 
    /** 目的国 */
    @ApiModelProperty(value = "目的国")
    @ExcelProperty(value ="目的国")
    private String destinationCountry ;
 
    /** 目的仓 */
    @ApiModelProperty(value = "目的仓")
    @ExcelProperty(value ="目的仓")
    private String destinationWarehouse ;
 
    /** 单价 */
    @ApiModelProperty(value = "单价")
    @ExcelProperty(value ="单价")
    private BigDecimal unitPrice ;
 
    /** 汇率 */
    @ApiModelProperty(value = "汇率")
    @ExcelProperty(value ="汇率")
    private BigDecimal exchangeRate ;
 
    /** FBA ID */
    @ApiModelProperty(value = "FBA ID")
    @ExcelProperty(value ="FBA ID")
    private String shipmentId ;
 
    /** 总费用 */
    @ApiModelProperty(value = "总费用")
    @ExcelProperty(value ="总费用")
    private BigDecimal totalCost ;
 
    /** 海外税号 */
    @ApiModelProperty(value = "海外税号")
    @ExcelProperty(value ="海外税号")
    private String vatNo ;
 
    /** 海外税号的后四位 */
    @ApiModelProperty(value = "海外税号的后四位")
    @ExcelProperty(value ="海外税号的后四位")
    private String eori ;
 
    /** 计费箱数 */
    @ApiModelProperty(value = "计费箱数")
    @ExcelProperty(value ="计费箱数")
    private Integer boxNum ;
 
    /** 箱数 */
    @ApiModelProperty(value = "箱数")
    @ExcelProperty(value ="箱数")
    private Integer diffBoxNum ;
 
    /** 计费重量 */
    @ApiModelProperty(value = "计费重量")
    @ExcelProperty(value ="计费重量")
    private BigDecimal weight ;
 
    /** 差异重量 */
    @ApiModelProperty(value = "差异重量")
    @ExcelProperty(value ="差异重量")
    private BigDecimal diffWeight ;
 
    /** 体积重 */
    @ApiModelProperty(value = "体积重")
    @ExcelProperty(value ="体积重")
    private BigDecimal volumeWeight ;
 
    /** 差异体积重 */
    @ApiModelProperty(value = "差异体积重")
    @ExcelProperty(value ="差异体积重")
    private BigDecimal diffVolumeWeight ;
 
    /** 实重 */
    @ApiModelProperty(value = "实重")
    @ExcelProperty(value ="实重")
    private BigDecimal realWeight ;
 
    /** Quantity */
    @ApiModelProperty(value = "Quantity")
    @ExcelProperty(value ="Quantity")
    private Integer quantity ;
 
    /** 发货批次号 */
    @ApiModelProperty(value = "发货批次号")
    @ExcelProperty(value ="发货批次号")
    private String lhrCode ;
 
    /** 计费类型 */
    @ApiModelProperty(value = "计费类型")
    @ExcelProperty(value ="计费类型")
    private String chargType ;
 
    /** 物流跟踪单 对账状态 */
    @ApiModelProperty(value = "物流跟踪单 对账状态")
    @ExcelProperty(value ="物流跟踪单 对账状态")
    private String status ;
 
    /** 红蓝单 */
    @ApiModelProperty(value = "红蓝单")
    @ExcelProperty(value ="红蓝单")
    private String logRedOrBlueList ;
 
    /** 货运方式1 */
    @ApiModelProperty(value = "货运方式1")
    @ExcelProperty(value ="货运方式1")
    private String logTraMode1 ;
 
    /** 货运方式2 */
    @ApiModelProperty(value = "货运方式2")
    @ExcelProperty(value ="货运方式2")
    private String logTraMode2 ;
 
    /** 海运路线 */
    @ApiModelProperty(value = "海运路线")
    @ExcelProperty(value ="海运路线")
    private String logSeaTraRoute ;
 
    /** 货物特性 */
    @ApiModelProperty(value = "货物特性")
    @ExcelProperty(value ="货物特性")
    private String logGoodCharacter ;
 
    /** 是否含税 */
    @ApiModelProperty(value = "是否含税")
    @ExcelProperty(value ="是否含税")
    private Integer logpIsIncTax ;
 
    /** 海运货柜类型 */
    @ApiModelProperty(value = "海运货柜类型")
    @ExcelProperty(value ="海运货柜类型")
    private String logSeaTraConType ;
 
    /** 预估计费方式 */
    @ApiModelProperty(value = "预估计费方式")
    @ExcelProperty(value ="预估计费方式")
    private String lerPreChargType ;
 
    /** 是否无用 */
    @ApiModelProperty(value = "是否无用")
    @ExcelProperty(value ="是否无用")
    private Integer isNormal ;
 
    /** 备注 */
    @ApiModelProperty(value = "备注")
    @ExcelProperty(value ="备注")
    private String remarks ;


}