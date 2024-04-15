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
* EBMS转仓清单（欧洲站点调拨)
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
@TableName("AMAZON_INVENTORY_EU_TRANSFER")
@ExcelIgnoreUnannotated
public class AmazonInventoryEuTransfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @TableField("ORG")
    private String org;

    @TableField("WAREHOUSE_NAME")
    private String warehouseName;

    @TableField("SKU")
    private String sku;

    @TableField("QTY")
    private Long qty;

    @TableField("CREATE_TIME")
    private Date createTime;
}