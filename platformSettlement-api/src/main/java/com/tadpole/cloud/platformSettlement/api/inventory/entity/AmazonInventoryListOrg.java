package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.io.Serializable;

/**
* <p>
* 庫存列表3.0
* </p>
*
* @author S20190161
* @since 2022-12-20
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("AMAZON_INVENTORY_LIST_ORG")
@ExcelIgnoreUnannotated
public class AmazonInventoryListOrg implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ExcelProperty("销售组织")
    @TableField("SALES_ORG")
    private String salesOrg;

    @ExcelProperty({"出库数量","当月销售数量"})
    @TableField("OUTGOING_MONTHLY_SALES")
    private Long outgoingMonthlySales;

    @ExcelProperty({"出库数量","当月退货数量"})
    @TableField("OUTGOING_MONTHLY_RETURNED")
    private Long outgoingMonthlyReturned;

    @ExcelProperty({"出库数量","当月销毁的数量"})
    @TableField("OUTGOING_MONTHLY_DESTORYED")
    private Long outgoingMonthlyDestoryed;

    @ExcelProperty({"出库数量","当月移除的数量"})
    @TableField("OUTGOING_MONTHLY_REMOVAL")
    private Long outgoingMonthlyRemoval;

    @ExcelProperty({"出库数量","Amazon库存调增"})
    @TableField("OUTGOING_INVENTORY_INCREASE")
    private Long outgoingInventoryIncrease;

    @ExcelProperty({"出库数量","Amazon库存调减"})
    @TableField("OUTGOING_INVENTORY_REDUCTION")
    private Long outgoingInventoryReduction;
}
