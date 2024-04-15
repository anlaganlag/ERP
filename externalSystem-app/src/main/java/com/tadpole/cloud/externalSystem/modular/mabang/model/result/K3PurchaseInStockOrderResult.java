package com.tadpole.cloud.externalSystem.modular.mabang.model.result;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * K3采购入库单
    * </p>
*
* @author S20190161
* @since 2023-05-17
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ExcelIgnoreUnannotated
@TableName("K3_PURCHASE_IN_STOCK_ORDER")
public class K3PurchaseInStockOrderResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "ID", type = IdType.AUTO)
    private String id;


    @ApiModelProperty("单据编号")
    private String fBillNo;


    @ApiModelProperty("单据类型")
    private String fBillTypeId;


    @ApiModelProperty("入库日期")
    private Date fDate;


    @ApiModelProperty("收料组织")
    private String fStockOrgId;


    @ApiModelProperty("采购组织")
    private String fPurchaseOrgId;


    @ApiModelProperty("供应商")
    private String fSupplierId;


    @ApiModelProperty("货主类型")
    private String fOwnerTypeIdHead;


    @ApiModelProperty("货主")
    private String fOwnerIdHead;


    @ApiModelProperty("结算组织")
    private String fSettleOrgId;


    @ApiModelProperty("结算币别")
    private String fSettleCurrId;


    @ApiModelProperty("定价时点")
    private String fPriceTimePoint;


    @ApiModelProperty("同步方式")
    private String syncType;


    @ApiModelProperty("同步时间")
    private Date syncTime;


    @ApiModelProperty("同步状态;同步状态(-1：初始化，0 ：同步失败,1：同步成功)")
    private String syncStatus;


    @ApiModelProperty("同步请求参数")
    private String syncRequstPar;


    @ApiModelProperty("同步结果消息内容")
    private String syncResultMsg;


    @ApiModelProperty("创建人")
    private String createdBy;


    @ApiModelProperty("创建时间")
    private Date createdTime;


    @ApiModelProperty("更新人")
    private String updatedBy;


    @ApiModelProperty("更新时间")
    private Date updatedTime;

    // item 信息

     @ApiModelProperty("物料编码")
     private String fMaterialId;


     @ApiModelProperty("库存单位")
     private String fUnitId;


     @ApiModelProperty("实收数量")
     private BigDecimal fRealQty;


     @ApiModelProperty("计价单位")
     private String fPriceUnitId;


     @ApiModelProperty("货主类型")
     private String fownertypeid;


     @ApiModelProperty("采购单位")
     private String fRemainInStockUnitId;


     @ApiModelProperty("采购数量")
     private BigDecimal fRemainInStockQty;


     @ApiModelProperty("含税单价")
     private BigDecimal fTaxPrice;


     @ApiModelProperty("成本价")
     private BigDecimal fCostPrice;


     @ApiModelProperty("货主")
     private String fownerid;


     @ApiModelProperty("需求TEAM")
     private String fPaezBase2;


     @ApiModelProperty("仓库ID")
     private String fStockId;


     @ApiModelProperty("应收数量")
     private BigDecimal fMustQty;


     @ApiModelProperty("价格系数")
     private BigDecimal fPriceCoefficient;


     @ApiModelProperty("采购基本分子")
     private BigDecimal fpurbasenum;


     @ApiModelProperty("库存基本分母")
     private BigDecimal fStockBaseDen;


     @ApiModelProperty("携带的主业务单位")
     private String fsrcbizunitid;

     @ApiModelProperty("K3采购入库单审核时间")
     private Date verifyDate;





}
