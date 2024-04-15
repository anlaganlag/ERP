package com.tadpole.cloud.platformSettlement.api.inventory.entity;

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
*
* </p>
*
* @author cyt
* @since 2022-05-24
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("REMOVAL_DESTROY_APPLY")
@ExcelIgnoreUnannotated
public class RemovalDestroyApply implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @TableField("APPLY_CODE")
    private String applyCode;

    /** 账号 */
    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    /** 站点 */
    @TableField("SYS_SITE")
    private String sysSite;

    /** 商家SKU */
    @TableField("MERCHANT_SKU")
    private String merchantSku;

    /** 事业部 */
    @TableField("DEPARTMENT")
    private String department;

    /** Team */
    @TableField("TEAM")
    private String team;

    /** 运营大类 */
    @TableField("PRODUCT_TYPE")
    private String productType;

    /** Order ID */
    @TableField("ORDER_ID")
    private String orderId;

    /** 申请数量 */
    @TableField("APPLY_QUANTITY")
    private int applyQuantity;

    /** 收购单价 */
    @TableField("UNIT_PRICE")
    private BigDecimal unitPrice;

    /** 总价 */
    @TableField("TOTAL_PRICE")
    private BigDecimal totalPrice;

    /** 备注 */
    @TableField("REMARK")
    private String remark;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @TableField("CREATE_BY")
    private String createBy;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @TableField("UPDATE_BY")
    private String updateBy;
}