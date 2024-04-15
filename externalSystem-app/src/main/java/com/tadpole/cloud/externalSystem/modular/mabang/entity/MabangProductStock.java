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
    * 马帮商品库存信息
    * </p>
*
* @author lsy
* @since 2023-05-23
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("MABANG_PRODUCT_STOCK")
@ExcelIgnoreUnannotated
public class MabangProductStock implements Serializable {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.ASSIGN_ID)
    private String id;

    /** 商品SKU */
    @TableField("STOCK_SKU")
    private String stockSku;

    /** 仓库编号 */
    @TableField("WAREHOUSE_ID")
    private String warehouseId;

    /** 仓库名称 */
    @TableField("WAREHOUSE_NAME")
    private String warehouseName;

    /** 库存总数 */
    @TableField("STOCK_QUANTITY")
    private BigDecimal stockQuantity;

    /** 未发货数量 */
    @TableField("WAITING_QUANTITY")
    private BigDecimal waitingQuantity;

    /** 调拨在途 */
    @TableField("ALLOT_SHIPPING_QUANTITY")
    private BigDecimal allotShippingQuantity;

    /** 采购在途数量 */
    @TableField("SHIPPING_QUANTITY")
    private BigDecimal shippingQuantity;

    /** 加工在途量 */
    @TableField("PROCESSING_QUANTITY")
    private BigDecimal processingQuantity;

    /** fba未发货量 */
    @TableField("FBA_WAITING_QUANTITY")
    private BigDecimal fbaWaitingQuantity;

    /** 仓位 */
    @TableField("GRID_CODE")
    private String gridCode;

    /** 同步方式 */
    @TableField("SYNC_TYPE")
    private String syncType;

    /** 同步时间 */
    @TableField("SYNC_TIME")
    private Date syncTime;

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