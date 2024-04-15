package com.tadpole.cloud.externalSystem.modular.mabang.model.params;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
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
@TableName("K3_PURCHASE_IN_STOCK_ORDER_ITEM")
public class K3PurchaseInStockOrderItemParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.AUTO)
    private String id;

    /** 单据编号 */
    @ApiModelProperty("单据编号")
    private String fBillNo;

    /** 物料编码 */
    @ApiModelProperty("物料编码")
    private String fMaterialId;

    /** 库存单位 */
    @ApiModelProperty("库存单位")
    private String fUnitId;

    /** 实收数量 */
    @ApiModelProperty("实收数量")
    private BigDecimal fRealQty;

    /** 计价单位 */
    @ApiModelProperty("计价单位")
    private String fPriceUnitId;

    /** 货主类型 */
    @ApiModelProperty("货主类型")
    private String fownertypeid;

    /** 采购单位 */
    @ApiModelProperty("采购单位")
    private String fRemainInStockUnitId;

    /** 采购数量 */
    @ApiModelProperty("采购数量")
    private BigDecimal fRemainInStockQty;

    /** 含税单价 */
    @ApiModelProperty("含税单价")
    private BigDecimal fTaxPrice;

    /** 成本价 */
    @ApiModelProperty("成本价")
    private BigDecimal fCostPrice;

    /** 货主 */
    @ApiModelProperty("货主")
    private String fownerid;

    /** 需求TEAM */
    @ApiModelProperty("需求TEAM")
    private String fPaezBase2;

    /** 仓库ID */
    @ApiModelProperty("仓库ID")
    private String fStockId;

    /** 应收数量 */
    @ApiModelProperty("应收数量")
    private BigDecimal fMustQty;

    /** 价格系数 */
    @ApiModelProperty("价格系数")
    private BigDecimal fPriceCoefficient;

    /** 采购基本分子 */
    @ApiModelProperty("采购基本分子")
    private BigDecimal fpurbasenum;

    /** 库存基本分母 */
    @ApiModelProperty("库存基本分母")
    private BigDecimal fStockBaseDen;

    /** 携带的主业务单位 */
    @ApiModelProperty("携带的主业务单位")
    private String fsrcbizunitid;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createdBy;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createdTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updatedBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updatedTime;

}
