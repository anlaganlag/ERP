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
    * K3采购入库单明细项
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
@TableName("K3_PURCHASE_IN_STOCK_ORDER_ITEM")
public class K3PurchaseInStockOrderItemResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;



   @TableId(value = "ID", type = IdType.AUTO)
    private String id;


    @ApiModelProperty("单据编号")
    private String fBillNo;


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


    @ApiModelProperty("创建人")
    private String createdBy;


    @ApiModelProperty("创建时间")
    private Date createdTime;


    @ApiModelProperty("更新人")
    private String updatedBy;


    @ApiModelProperty("更新时间")
    private Date updatedTime;

}
