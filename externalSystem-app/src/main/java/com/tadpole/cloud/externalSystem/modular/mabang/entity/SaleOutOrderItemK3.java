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
    * 根据K3仓库可用数量自动产生-销售出库单明细项
    * </p>
*
* @author lsy
* @since 2023-04-07
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("SALE_OUT_ORDER_ITEM_K3")
@ExcelIgnoreUnannotated
public class SaleOutOrderItemK3 implements Serializable {

    private static final long serialVersionUID = 1L;


    /** 数据id--数据id */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 销售出库单ID--销售出库单ID */
    @TableField("SALE_OUT_ORDER_ID")
    private BigDecimal saleOutOrderId;

    /** 调用k3接口传递字段FBillNo--调用k3接口传递字段FBillNo */
    @TableField("F_BILL_NO")
    private String fBillNo;

    /** 物料编码--取值stockSku */
    @TableField("F_MATERIAL_ID")
    private String fMaterialId;

    /** 物料名称--调用k3接口传递字段FMaterialName--由物料编码带值 */
    @TableField("F_MATERIAL_NAME")
    private String fMaterialName;

    /** 规格型号--调用k3接口传递字段FMateriaModel--由物料编码带值 */
    @TableField("F_MATERIA_MODEL")
    private String fMateriaModel;

    /** 库存单位--由物料编码带值，默认值：Pcs */
    @TableField("F_UNIT_ID")
    private String fUnitId;

    /** 实发数量--调用k3接口传递字段FRealQty--取值outQuantity */
    @TableField("F_REAL_QTY")
    private String fRealQty;

    /** 仓库ID--调用k3接口传递字段FStockID--取值whCode */
    @TableField("F_STOCK_ID")
    private String fStockId;

    /** 仓库名称-- */
    @TableField("WAREHOUSE_NAME")
    private String warehouseName;

    /** 库存状态--默认值：KCZT01_SYS */
    @TableField("F_STOCK_STATUS_ID")
    private String fStockStatusId;

    /** 开票品名--由物料编码带值 */
    @TableField("F_PAEZ_BASE_PROPERTY")
    private String fPaezBaseProperty;

    /** 开票规格型号--由物料编码带值 */
    @TableField("F_PAEZ_BASE_PROPERTY1")
    private String fPaezBaseProperty1;

    /** 需求Team--默认值：平台发展组 */
    @TableField("F_BSC_TEAM")
    private String fBscTeam;

    /** 需求部门--由需求Team带值 */
    @TableField("F_BSC_DEPT")
    private String fBscDept;

    /** F_BSC_SubRemark1-- */
    @TableField("F_BSC_SubRemark1")
    private String fBscSubremark1;

    /** F_CustMat_ID-- */
    @TableField("F_CustMat_ID")
    private String fCustmatId;

    /** F_Customer_SKU-- */
    @TableField("F_Customer_SKU")
    private String fCustomerSku;

    /** F_Owner_Type_ID-- */
    @TableField("F_Owner_Type_ID")
    private String fOwnerTypeId;

    /** F_SALBASE_QTY-- */
    @TableField("F_SALBASE_QTY")
    private BigDecimal fSalbaseQty;

    /** F_PRICEBASE_QTY-- */
    @TableField("F_PRICEBASE_QTY")
    private BigDecimal fPricebaseQty;

    /** F_BASEUNIT_QTY-- */
    @TableField("F_BASEUNIT_QTY")
    private BigDecimal fBaseunitQty;

    /** 调用马帮erp新增采购单返回的采购批次号-- */
    @TableField("GROUP_ID")
    private String groupId;

    /** 是否删除--是否删除:正常：0，删除：1 */
    @TableField("IS_DELETE")
    private String isDelete;

    /** 创建时间--创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 更新时间--更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 销售出库价格 */
    @TableField("SALE_PRICE")
    private BigDecimal salePrice;

    /** 仓位ID--调用k3接口传递字段stockLocId--取值fStockLocId */
    @TableField("F_STOCK_LOC_ID")
    private String fStockLocId;



}