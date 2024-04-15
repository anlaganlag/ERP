package com.tadpole.cloud.externalSystem.modular.mabang.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@ExcelIgnoreUnannotated
public class K3PurchaseInStockOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 单据编号 */
    @TableField("F_BILL_NO")
    private String fBillNo;

    /** 物料编码 */
    @TableField("F_MATERIAL_ID")
    private String fMaterialId;

    /** 库存单位 */
    @TableField("F_UNIT_ID")
    private String fUnitId;

    /** 实收数量 */
    @TableField("F_REAL_QTY")
    private BigDecimal fRealQty;

    /** 计价单位 */
    @TableField("F_PRICE_UNIT_ID")
    private String fPriceUnitId;

    /** 货主类型 */
    @TableField("FOWNERTYPEID")
    private String fownertypeid;

    /** 采购单位 */
    @TableField("F_REMAIN_IN_STOCK_UNIT_ID")
    private String fRemainInStockUnitId;

    /** 采购数量 */
    @TableField("F_REMAIN_IN_STOCK_QTY")
    private BigDecimal fRemainInStockQty;

    /** 含税单价 */
    @TableField("F_TAX_PRICE")
    private BigDecimal fTaxPrice;

    /** 成本价 */
    @TableField("F_COST_PRICE")
    private BigDecimal fCostPrice;

    /** 货主 */
    @TableField("FOWNERID")
    private String fownerid;

    /** 需求TEAM */
    @TableField("F_PAEZ_BASE2")
    private String fPaezBase2;

    /** 仓库ID */
    @TableField("F_STOCK_ID")
    private String fStockId;

    /** 应收数量 */
    @TableField("F_MUST_QTY")
    private BigDecimal fMustQty;

    /** 价格系数 */
    @TableField("F_PRICE_COEFFICIENT")
    private BigDecimal fPriceCoefficient;

    /** 采购基本分子 */
    @TableField("FPURBASENUM")
    private BigDecimal fpurbasenum;

    /** 库存基本分母 */
    @TableField("F_STOCK_BASE_DEN")
    private BigDecimal fStockBaseDen;

    /** 携带的主业务单位 */
    @TableField("FSRCBIZUNITID")
    private String fsrcbizunitid;

    /** 创建人 */
    @TableField("CREATED_BY")
    private String createdBy;

    /** 创建时间 */
    @TableField("CREATED_TIME")
    private Date createdTime;

    /** 更新人 */
    @TableField("UPDATED_BY")
    private String updatedBy;

    /** 更新时间 */
    @TableField("UPDATED_TIME")
    private Date updatedTime;

}