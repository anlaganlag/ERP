package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* 物流商的价格信息;
* @author : LSY
* @date : 2023-12-29
*/
@ApiModel(value = "物流商的价格信息导出实体",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class TbLogisticsNewPriceExportResult implements Serializable{
private static final long serialVersionUID = 1L;

   /** 物流价格编号 */
   @ApiModelProperty(value = "物流价格编号")
//   @ExcelProperty(value ="物流价格编号")
   private BigDecimal pkLogpId ;

   /** 物流账号 */
   @ApiModelProperty(value = "物流账号")
   @ExcelProperty(value ="物流账号")
   private String busLcCode ;

   /** 物流商编码;物流商编码 */
   @ApiModelProperty(value = "物流商编码")
//   @ExcelProperty(value ="物流商编码")
   private String lpCode ;

   @ApiModelProperty(value = "物流商简称")
//   @ExcelProperty(value ="物流商简称")
   private String lpSimpleName ;

   /** 物流商名称 */
   @ApiModelProperty(value = "物流商名称")
//   @ExcelProperty(value ="物流商名称")
   private String lpName ;

   /** 物流单链接模板 */
   @ApiModelProperty(value = "物流单链接模板")
//   @ExcelProperty(value ="物流单链接模板")
   private String logListLinkTemp ;

   /** 创建日期 */
   @ApiModelProperty(value = "创建日期")
//   @ExcelProperty(value ="创建日期")
   private Date sysCreateDate ;

   /** 员工编号 */
   @ApiModelProperty(value = "员工编号")
//   @ExcelProperty(value ="员工编号")
   private String sysPerCode ;

   /** 员工姓名 */
   @ApiModelProperty(value = "员工姓名")
//   @ExcelProperty(value ="员工姓名")
   private String sysPerName ;

   /** 最后更新日期 */
   @ApiModelProperty(value = "最后更新日期")
//   @ExcelProperty(value ="最后更新日期")
   private Date sysUpdDatetime ;

   /** 最后更新人编号 */
   @ApiModelProperty(value = "最后更新人编号")
//   @ExcelProperty(value ="最后更新人编号")
   private String sysUpdPerCode ;

   /** 最后更新人姓名 */
   @ApiModelProperty(value = "最后更新人姓名")
//   @ExcelProperty(value ="最后更新人姓名")
   private String sysUpdPerName ;



   /** 站点 */
   @ApiModelProperty(value = "站点")
   @ExcelProperty(value ="站点")
   private String busLpCountryCode ;

   /** 分区号 */
   @ApiModelProperty(value = "分区号--新平台叫国家地区")
   @ExcelProperty(value ="国家地区")
   private String busLspNum ;

   /** 货运方式1 */
   @ApiModelProperty(value = "货运方式1")
//   @ExcelProperty(value ="货运方式1")
   private String busLogTraMode1 ;

   /** 运输方式 */
   @ApiModelProperty(value = "发货方式")
   @ExcelProperty(value ="发货方式")
   private String busLogTraMode2 ;

   /** 物流渠道 */
   @ApiModelProperty(value = "物流渠道")
   @ExcelProperty(value ="物流渠道")
   private String busLogSeaTraRoute ;

   /** 红蓝单 */
   @ApiModelProperty(value = "红蓝单")
   @ExcelProperty(value ="红蓝单")
   private String busLogRedOrBlueList ;

   /** 货物特性 */
   @ApiModelProperty(value = "货物特性")
   @ExcelProperty(value ="货物特性")
   private String busLogGoodCharacter ;

   /** 是否含税 */
   @ApiModelProperty(value = "是否含税(0:不含税,1含税)")
//   @ExcelProperty(value ="是否含税")
   private Integer busLogpIsIncTax ;

   /** 是否含税 */
   @ApiModelProperty(value = "是：含税,否：不含税")
   @ExcelProperty(value ="是否含税")
   private String busLogpIsIncTaxStr ;

   /** 计费币种 */
   @ApiModelProperty(value = "计费币种")
   @ExcelProperty(value ="计费币种")
   private String busLogpChargCurrency ;

   /** 计费方式 */
   @ApiModelProperty(value = "计费方式")
   @ExcelProperty(value ="计费方式")
   private String busLogpChargType ;

//--------------------------------------------------------------重量区间明细-------------------

   /** 重量KG(>) */
   @ApiModelProperty(value = "重量KG(>)")
   @ExcelProperty(value ="重量KG(>)")
   private BigDecimal busLogpDetWeightGreater ;

   /** 重量KG(<) */
   @ApiModelProperty(value = "重量KG(<)")
   @ExcelProperty(value ="重量KG(<)")
   private BigDecimal busLogpDetWeightLess ;

   /** 体积CBM(>) */
   @ApiModelProperty(value = "体积CBM(>)")
   @ExcelProperty(value ="体积CBM(>)")
   private BigDecimal busLogpDetVolumeGreater ;

   /** 体积CBM(<) */
   @ApiModelProperty(value = "体积CBM(<)")
   @ExcelProperty(value ="体积CBM(<)")
   private BigDecimal busLogpDetVolumeLess ;

   /** 单价费用 */
   @ApiModelProperty(value = "单价费用")
   @ExcelProperty(value ="单价费用")
   private BigDecimal busLogpDetUnitPrice ;

   /** 报关费 */
   @ApiModelProperty(value = "报关费")
   @ExcelProperty(value ="报关费")
   private BigDecimal busLogpDetCustDlearanceFee ;

   /** 清关费 */
   @ApiModelProperty(value = "清关费")
   @ExcelProperty(value ="清关费")
   private BigDecimal busLogpDetCustClearanceFee ;

   /** 旺季附加费KG */
   @ApiModelProperty(value = "旺季附加费KG")
   @ExcelProperty(value ="旺季附加费KG")
   private BigDecimal busLogpDetBusySeasonAddFee ;

   /** 燃油附加税率 */
   @ApiModelProperty(value = "燃油附加税率")
   @ExcelProperty(value ="燃油附加税率")
   private BigDecimal busLogpDetFuelFee ;

   /** 附加费及杂费KG */
   @ApiModelProperty(value = "附加费及杂费KG")
   @ExcelProperty(value ="附加费及杂费KG")
   private BigDecimal busLogpDetAddAndSundryFee ;

   /** 备注 */
   @ApiModelProperty(value = "备注")
   @ExcelProperty(value ="备注")
   private String busLogpDetRemark ;

   /** 适用开始日期 */
   @ApiModelProperty(value = "适用开始日期")
   @ExcelProperty(value ="适用开始日期")
   private Date busLogpDetAppStartDate ;

   /** 适用结束日期 */
   @ApiModelProperty(value = "适用结束日期")
//   @ExcelProperty(value ="适用结束日期")
   private Date busLogpDetAppEndDate ;

   /** 价格唯一值 */
   @ApiModelProperty(value = "价格唯一值")
   private String olnyKey ;


}