package com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseModifParm {

   //api:pur-do-change-purchase

   @ApiModelProperty("采购单单号")
   private String groupId	        ;// 	TRUE		采购单单号

   @ApiModelProperty("员工名称")
   private String employeeName	    ;// 	TRUE		员工名称

   @ApiModelProperty("操作类型:1标记完成采购单;2作废采购单;3修改采购单基础信息;4修改采购单商品信息;5添加采购单商品")
   private String actionType	    ;// 	TRUE		操作类型:1标记完成采购单;2作废采购单;3修改采购单基础信息;4修改采购单商品信息;5添加采购单商品

   @ApiModelProperty("作废采购单商品类型：1.全部作废 2.部分作废 操作类型为2时必传")
   private String scrapOrder	    ;// 	FALSE		作废采购单商品类型：1.全部作废 2.部分作废 操作类型为2时必传

   @ApiModelProperty("作废采购单下部分商品集合,scrapOrder = 2时必传,商品编号数组json字符串")
   private String stockSkus	        ;// 	FALSE		作废采购单下部分商品集合,scrapOrder = 2时必传,商品编号数组json字符串

   @ApiModelProperty("仓库名称,不支持修改成空")
   private String warehouseName	    ;// 	FALSE		仓库名称,不支持修改成空

   @ApiModelProperty("快递方式")
   private String expressType	    ;// 	FALSE		快递方式

   @ApiModelProperty("快递单号,多个;隔开")
   private String expressId	        ;// 	FALSE		快递单号,多个;隔开

   @ApiModelProperty("运费,不支持修改成空")
   private String expressMoney	    ;// 	FALSE		运费,不支持修改成空

   @ApiModelProperty("到货时间,不支持修改成空")
   private String estimatedTime	    ;// 	FALSE		到货时间,不支持修改成空

   @ApiModelProperty("自定义单据号")
   private String orderBillNO	    ;// 	FALSE		自定义单据号

   @ApiModelProperty("备注")
   private String content	        ;// 	FALSE		备注

   @ApiModelProperty("是否计算采购在途;1:计算;2不计算")
   private String isCalculate	    ;// 	FALSE		是否计算采购在途;1:计算;2不计算

   @ApiModelProperty("商品项list")
   private List<PurchaseModifItem> purchaseDetailData = new ArrayList<>();//	    FALSE

}
