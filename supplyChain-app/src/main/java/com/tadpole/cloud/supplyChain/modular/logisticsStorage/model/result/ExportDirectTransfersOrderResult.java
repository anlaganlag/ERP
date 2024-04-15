package com.tadpole.cloud.supplyChain.modular.logisticsStorage.model.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 导出物流调拨记录明细项;
 * @author : LSY
 * @date : 2023-12-29
 */
@ApiModel(value = "导出物流调拨记录明细项",description = "")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
public class ExportDirectTransfersOrderResult implements Serializable{
   private static final long serialVersionUID = 1L;

   /**基本信息(序号)**/
   //@ExcelProperty(value ="fBillHead")
   private  Integer billHead;

   /**(基本信息)单据编号**/
   //@ExcelProperty(value ="fBillNo")
   private  String billNo;

   /**(基本信息)调拨方向**/
   //@ExcelProperty(value ="fTransferDirect")
   private  String transferDirect;

   /**(基本信息)调拨类型**/
   //@ExcelProperty(value ="fTransferBizType")
   private  String transferBizType;

   /**(基本信息)调出库存组织#编码**/
   //@ExcelProperty(value ="fStockOutOrgId")
   private  String stockOutOrgId;

   /**(基本信息)调入库存组织#编码**/
   //@ExcelProperty(value ="fStockOrgId")
   private  String stockOrgId;

   /**(基本信息)日期**/
   //@ExcelProperty(value ="fDate")
   private  Date bizDate;

   /***明细信息(序号)**/
   //@ExcelProperty(value ="fBillEntry")
   private  Integer billEntry;

   /***(明细信息)物料编码#编码**/
   //@ExcelProperty(value ="fMaterialId")
   private  String materialId;

   /**(明细信息)调拨数量**/
   //@ExcelProperty(value ="fQty")
   private  Integer qty;

   /**(明细信息)调出仓库#编码**/
   //@ExcelProperty(value ="fSrcStockId")
   private  String srcStockId;

   /**(明细信息)调入仓库#编码**/
   //@ExcelProperty(value ="fDestStockId")
   private  String destStockId;

   /**(明细信息)调入仓库#名称**/
   //@ExcelProperty(value ="fDestStockIdName")
   private  String destStockIdName;

   /**(明细信息)需求team#编码**/
   //@ExcelProperty(value ="fBscTeam")
   private  String bscTeam;

   /**(明细信息)需求team#名称**/
   //@ExcelProperty(value ="fBscTeamName")
   private  String bscTeamName;

   //@ExcelProperty(value ="fStockOutOrgIdName")
   private  String stockOutOrgIdName="";

   //@ExcelProperty(value ="fStockOrgIdName")
   private  String stockOrgIdName="";

   //@ExcelProperty(value ="spli")
   private  String spli="";

   //@ExcelProperty(value ="fMaterialIdName")
   private  String materialIdName="";


}