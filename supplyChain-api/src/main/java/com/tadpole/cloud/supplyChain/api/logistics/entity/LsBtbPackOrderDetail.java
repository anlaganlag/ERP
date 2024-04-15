package com.tadpole.cloud.supplyChain.api.logistics.entity;

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
 * BTB订单发货明细信息 实体类
 * </p>
 *
 * @author ty
 * @since 2023-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LS_BTB_PACK_ORDER_DETAIL")
@ExcelIgnoreUnannotated
public class LsBtbPackOrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;


    /** ID */
   @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 订单号 */
    @TableField("ORDER_NO")
    private String orderNo;

    /** BTB订单明细ID */
    @TableField("ORDER_DETAIL_ID")
    private String orderDetailId;

    /** SKU */
    @TableField("SKU")
    private String sku;

    /** 物料编码 */
    @TableField("MATERIAL_CODE")
    private String materialCode;

    /** 商品中文名称 */
    @TableField("PRODUCT_INFO")
    private String productInfo;

    /** 数量 */
    @TableField("QUANTITY")
    private Integer quantity;

    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createTime;

    /** 创建人 */
    @TableField("CREATE_USER")
    private String createUser;

    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /** 更新人 */
    @TableField("UPDATE_USER")
    private String updateUser;

}