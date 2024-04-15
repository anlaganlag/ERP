package com.tadpole.cloud.externalSystem.modular.mabang.model.k3;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.fastjson.annotation.JSONField;
import com.tadpole.cloud.externalSystem.api.k3.annotation.TransferSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* <p>
    * K3采购入库单请求参数
    * </p>
*
* @author lsy
* @since 2023-05-12
*/
@NoArgsConstructor
@Data
public class K3PurchaseOrderInStockParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


 @JSONField(name = "NeedUpDateFields",ordinal = 1)
 private List<String> needUpDateFields;
 @JSONField(name = "NeedReturnFields",ordinal = 2)
 private List<String> needReturnFields;
 @JSONField(name = "IsDeleteEntry",ordinal = 3)// 是否删除已存在的分录，布尔类型，默认true（非必录）
 private Boolean isDeleteEntry =true;
 @JSONField(name = "SubSystemId",ordinal = 4)
 private String subSystemId;
 @JSONField(name = "IsVerifyBaseDataField",ordinal = 5)
 private Boolean isVerifyBaseDataField;
 @JSONField(name = "IsEntryBatchFill",ordinal = 6) //  是否批量填充分录，默认true（非必录）
 private Boolean isEntryBatchFill  =true;
 @JSONField(name = "ValidateFlag",ordinal = 7)// 是否验证数据合法性标志，布尔类型，默认true（非必录）注（设为false时不对数据合法性进行校验）
 private Boolean validateFlag  =true;
 @JSONField(name = "NumberSearch",ordinal = 8)// 是否用编码搜索基础资料，布尔类型，默认true（非必录）
 private Boolean numberSearch  =true;
 @JSONField(name = "IsAutoAdjustField",ordinal = 9) //是否自动调整JSON字段顺序，布尔类型，默认false（非必录）
 private Boolean isAutoAdjustField =true;
 @JSONField(name = "InterationFlags",ordinal = 10)
 private String interationFlags;
 @JSONField(name = "IgnoreInterationFlag",ordinal = 11)
 private String ignoreInterationFlag;
 @JSONField(name = "IsControlPrecision",ordinal = 12)//是否控制精度，为true时对金额、单价和数量字段进行精度验证，默认false（非必录）
 private Boolean isControlPrecision =false;
 @JSONField(name = "ValidateRepeatJson",ordinal = 13)//校验Json数据包是否重复传入，一旦重复传入，接口调用失败，默认false（非必录）
 private Boolean validateRepeatJson = false;
 @JSONField(name = "Model",ordinal = 14)//必填 表单数据包，JSON类型（必录）
 private ModelDTO model;

 @NoArgsConstructor
 @Data
 public static class ModelDTO {
  @JSONField(name = "FBillNo",ordinal = 1)
  private String fBillNo;  //单据编号
  @JSONField(serializeUsing = TransferSerializer.class,name = "FBillTypeID",ordinal = 2)
  private String fBillTypeId;  //单据类型 RKD09_SYS

  @JSONField(name = "FDate",ordinal = 3,format = "yyyy-MM-dd")//必填  入库日期
  private Date fDate;

  @JSONField(serializeUsing = TransferSerializer.class,name = "FStockOrgId",ordinal = 4)
  private String fStockOrgId; //收料组织

  @JSONField(serializeUsing = TransferSerializer.class,name = "FPurchaseOrgId",ordinal = 5)
  private String fPurchaseOrgId; //采购组织 002

  @JSONField(serializeUsing = TransferSerializer.class,name = "FSupplierId",ordinal = 6)
  private String fSupplierId; //供应商  平台发展

  @JSONField(name = "FOwnerTypeIdHead",ordinal = 7)
  private String fOwnerTypeIdHead; //货主类型 //BD_OwnerOrg

  @JSONField(serializeUsing = TransferSerializer.class,name = "FOwnerIdHead",ordinal = 8)
  private String fOwnerIdHead;  //货主  002


  @JSONField(name = "FInStockFin",ordinal = 9)
  private FInStockFinDTO fInStockFin; //财务信息
  @JSONField(name = "FInStockEntry",ordinal = 10)
  private List<FInStockEntryDTO> fInStockEntry; //入库明细


  
  @NoArgsConstructor
  @Data
  public static class FInStockFinDTO {

   @JSONField(serializeUsing = TransferSerializer.class,name = "FSettleOrgId",ordinal = 1)
   private String fSettleOrgId; //结算组织

   @JSONField(serializeUsing = TransferSerializer.class,name = "FSettleCurrId",ordinal = 2)
   private String fSettleCurrId; //结算币别
   @JSONField(name = "FPriceTimePoint",ordinal = 3)
   private String fPriceTimePoint; //定价时点



  }

  @NoArgsConstructor
  @Data
  public static class FInStockEntryDTO {

   @JSONField(serializeUsing = TransferSerializer.class,name = "FMaterialId",ordinal = 1)
   private String fMaterialId; //物料编码

   @JSONField(serializeUsing = TransferSerializer.class,name = "FUnitID",ordinal = 2)
   private String fUnitId; //库存单位 PCS


   @JSONField(name =  "FRealQty",ordinal = 3)
   private BigDecimal fRealQty; //实收数量
   @JSONField(serializeUsing = TransferSerializer.class,name = "FPriceUnitID",ordinal = 4)
   private String fPriceUnitId; //计价单位 PSC

   @JSONField(name =  "FOWNERTYPEID",ordinal = 5)
   private String fownertypeid; //货主类型  BD_OwnerOrg

   @JSONField(serializeUsing = TransferSerializer.class,name = "FRemainInStockUnitId",ordinal = 6)
   private String fRemainInStockUnitId; //采购单位  PCS

   @JSONField(name =  "FRemainInStockQty",ordinal = 7)
   private BigDecimal fRemainInStockQty; //采购数量

   @JSONField(name =  "FTaxPrice",ordinal = 8)
   private BigDecimal fTaxPrice; //含税单价

   @JSONField(name =  "FCostPrice",ordinal = 9)
   private BigDecimal fCostPrice; //成本价
   @JSONField(serializeUsing = TransferSerializer.class,name = "FOWNERID",ordinal = 10)
   private String fownerid; //货主 002

   @JSONField(serializeUsing = TransferSerializer.class,name =  "F_PAEZ_Base2",ordinal = 11)
   private String fPaezBase2; //需求TEAM 不是标准接口字段 ，二开接口必填字段  Team1

   @JSONField(serializeUsing = TransferSerializer.class,name =  "FStockId",ordinal = 12)
   private String fStockId; //仓库ID 不是标准接口字段 ，二开接口必填字段  FStockId  YT04_002

   @JSONField(name =  "FMustQty",ordinal = 13)
   private BigDecimal fMustQty; //应收数量  默认等于采购数量

   @JSONField(name =  "FPriceCoefficient",ordinal = 14)
   private BigDecimal fPriceCoefficient; //价格系数 默认1

   @JSONField(name =  "FPURBASENUM",ordinal = 15)
   private BigDecimal fpurbasenum; //采购基本分子 默认等于采购数量

   @JSONField(name =  "FStockBaseDen",ordinal = 15)
   private BigDecimal fStockBaseDen; //库存基本分母  默认等于采购数量

   @JSONField(serializeUsing = TransferSerializer.class,name = "FSRCBIZUNITID",ordinal = 4)
   private String fsrcbizunitid; //携带的主业务单位 PSC

  }
 }
}