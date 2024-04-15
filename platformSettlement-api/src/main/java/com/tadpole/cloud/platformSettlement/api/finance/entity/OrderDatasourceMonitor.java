package com.tadpole.cloud.platformSettlement.api.finance.entity;

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
* @author ty
* @since 2022-06-15
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("CW_ORDER_DATASOURCE_MONITOR")
public class OrderDatasourceMonitor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    @TableField("SYS_SHOPS_NAME")
    private String sysShopsName;

    @TableField("SYS_SITE")
    private String sysSite;

    @TableField("PURCHASE_DATE")
    private String purchaseDate;

    @TableField("SALE_DAILY_QUANTITY")
    private BigDecimal saleDailyQuantity;

    @TableField("SALE_DAILY_AMOUNT")
    private BigDecimal saleDailyAmount;

    @TableField("NORMAL_ORDER_QUANTITY")
    private BigDecimal normalOrderQuantity;

    @TableField("TEST_ORDER_QUANTITY")
    private BigDecimal testOrderQuantity;

    @TableField("SMALL_ORDER_QUANTITY")
    private BigDecimal smallOrderQuantity;

    @TableField("REISSUE_ORDER_QUANTITY")
    private BigDecimal reissueOrderQuantity;

    @TableField("DESTROY_ORDER_QUANTITY")
    private BigDecimal destroyOrderQuantity;

    @TableField("ABNORMAL_ORDER_QUANTITY")
    private BigDecimal abnormalOrderQuantity;

    @TableField("SMALL_PLATFORM_ORDER_QUANTITY")
    private BigDecimal smallPlatformOrderQuantity;

    @TableField("CANCEL_ORDER_QUANTITY")
    private BigDecimal cancelOrderQuantity;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("CREATE_BY")
    private String createBy;

    @TableField("UPDATE_TIME")
    private Date updateTime;

    @TableField("UPDATE_BY")
    private String updateBy;

}