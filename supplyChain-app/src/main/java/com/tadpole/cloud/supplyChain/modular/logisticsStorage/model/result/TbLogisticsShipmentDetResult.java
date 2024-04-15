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
 * 亚马逊发货申请记录-明细项;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "亚马逊发货申请记录-明细项",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsShipmentDetResult implements Serializable{
 private static final long serialVersionUID = 1L;
 
    /** 编号 */
    @ApiModelProperty(value = "编号")
    @ExcelProperty(value ="编号")
    private BigDecimal shipDetId ;
 
    /** 出货清单号 */
    @ApiModelProperty(value = "出货清单号")
    @ExcelProperty(value ="出货清单号")
    private String packCode ;
 
    /** PlanName */
    @ApiModelProperty(value = "PlanName")
    @ExcelProperty(value ="PlanName")
    private String planName ;
 
    /** MerchantSKU */
    @ApiModelProperty(value = "MerchantSKU")
    @ExcelProperty(value ="MerchantSKU")
    private String merchantSku ;
 
    /** 数量 */
    @ApiModelProperty(value = "数量")
    @ExcelProperty(value ="数量")
    private Integer quantity ;


}