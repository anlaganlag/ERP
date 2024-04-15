package com.tadpole.cloud.platformSettlement.api.inventory.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.*;

/**
* <p>
* K3跨组织直接调拨单
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
@TableName("AMAZON_INVENTORY_K3_TRANSFER")
@ExcelIgnoreUnannotated
public class AmazonInventoryK3Transfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @TableField("SHOP_NAME")
    private String shopName;

    @TableField("SITE")
    private String site;

    @TableField("SKU")
    private String sku;

    @TableField("WAREHOUSE_NAME")
    private String warehouseName;

    @TableField("MATERIAL_CODE")
    private String materialCode;

    @TableField("QTY")
    private Long qty;

    @TableField("CREATE_TIME")
    private Date createTime;
}
