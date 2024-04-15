package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.params;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* 物流单费用导入解析model;
* @author : LSY
* @date : 2023-12-29
*/
@ApiModel(value = "物流单费用导入解析model",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsBillManageImportParam implements Serializable{
private static final long serialVersionUID = 1L;

   /** 头程物流单号 */
   @ApiModelProperty(value = "头程物流单号")
   @ExcelProperty(value ="头程物流单号*")
   private String lhrOddNumb ;

   /** 确认计费量 */
   @ApiModelProperty(value = "确认计费量")
   @ExcelProperty(value ="确认计费量*")
   private BigDecimal logComfirmBillVolume ;

   /** 单价 */
   @ApiModelProperty(value = "单价")
   @ExcelProperty(value ="单价*")
   private BigDecimal lhrLogUnitPrice ;

   /** 燃油附加费 */
   @ApiModelProperty(value = "燃油附加费")
   @ExcelProperty(value ="燃油附加费")
   private BigDecimal lhrLogFuelFee ;

   /** 旺季附加费 */
   @ApiModelProperty(value = "旺季附加费")
   @ExcelProperty(value ="旺季附加费")
   private BigDecimal lhrLogBusySeasonAddFee ;

   /** 附加费及杂费 */
   @ApiModelProperty(value = "附加费及杂费")
   @ExcelProperty(value ="附加费及杂费")
   private BigDecimal lhrLogAddAndSundryFee ;

   /** 预估附加费及杂费备注 */
   @ApiModelProperty(value = "预估附加费及杂费备注")
   @ExcelProperty(value ="附加费及杂费备注")
   private String lhrPreLogAddAndSundryFeeRemark ;

   /** 报关费 */
   @ApiModelProperty(value = "报关费")
   @ExcelProperty(value ="报关费")
   private BigDecimal lhrLogCustDlearanceFee ;

   /** 清关费 */
   @ApiModelProperty(value = "清关费")
   @ExcelProperty(value ="清关费")
   private BigDecimal lhrLogCustClearanceFee ;

   /** 税费 */
   @ApiModelProperty(value = "税费")
   @ExcelProperty(value ="税费")
   private BigDecimal lhrLogTaxFee ;

}