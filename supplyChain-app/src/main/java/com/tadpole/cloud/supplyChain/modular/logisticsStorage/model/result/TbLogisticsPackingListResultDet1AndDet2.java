package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackingListDet1;
import com.tadpole.cloud.supplyChain.modular.logisticsStorage.entity.TbLogisticsPackingListDet2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* 出货清单信息明细1,2;
* @author : LSY
* @date : 2023-12-29
*/
@ApiModel(value = "出货清单信息明细1,2",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsPackingListResultDet1AndDet2 implements Serializable{
private static final long serialVersionUID = 1L;

   /** 出货清单号 */
   @ApiModelProperty(value = "出货清单号")
   private String packCode ;

   /** 出货清单明细1-箱子长宽高重 List */
   @ApiModelProperty(value = "出货清单明细1-箱子长宽高重 List")
   private List<TbLogisticsPackingListDet1> tbLogisticsPackingListDet1s ;

   /** 出货清单明细2-装箱内容 List */
   @ApiModelProperty(value = "出货清单明细2-装箱内容 List")
   private List<TbLogisticsPackingListDet2> tbLogisticsPackingListDet2s ;

}