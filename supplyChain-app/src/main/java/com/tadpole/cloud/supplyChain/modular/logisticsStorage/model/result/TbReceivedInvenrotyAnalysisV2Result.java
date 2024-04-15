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
 * 来货报告;
 * @author : LSY
 * @date : 2024-3-18
 */
@ApiModel(value = "来货报告",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbReceivedInvenrotyAnalysisV2Result implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 站点 */
    @ApiModelProperty(value = "站点")
    @ExcelProperty(value ="站点")
    private String countryCode ;
 
    /** 账号 */
    @ApiModelProperty(value = "账号")
    @ExcelProperty(value ="账号")
    private String shopNameSimple ;
 
    /** sku */
    @ApiModelProperty(value = "sku")
    @ExcelProperty(value ="sku")
    private String itemSku ;
 
    /** ShipmentID */
    @ApiModelProperty(value = "ShipmentID")
    @ExcelProperty(value ="ShipmentID")
    private String shipmentId ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    @ExcelProperty(value ="数量")
    private Integer quantity ;
 
    /** 同步时间 */
    @ApiModelProperty(value = "同步时间")
    @ExcelProperty(value ="同步时间")
    private Date syncTime ;


}