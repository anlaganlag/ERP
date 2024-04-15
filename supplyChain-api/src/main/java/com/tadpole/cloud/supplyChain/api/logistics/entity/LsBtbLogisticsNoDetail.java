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
 *  BTB物流单明细实体类
 * </p>
 *
 * @author ty
 * @since 2023-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("LS_BTB_LOGISTICS_NO_DETAIL")
@ExcelIgnoreUnannotated
public class LsBtbLogisticsNoDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 发货批次号 */
    @TableField("SHIPMENT_NUM")
    private String shipmentNum;

    /** BTB订单号 */
    @TableField("ORDER_NO")
    private String orderNo;

    /** 箱条码 */
    @TableField("BOX_BARCODE")
    private String boxBarcode;

    /** 箱号 */
    @TableField("BOX_NO")
    private String boxNo;

    /** 体积CBM */
    @TableField("BOX_VOLUME")
    private BigDecimal boxVolume;

    /** 重量KG */
    @TableField("BOX_WEIGHT")
    private BigDecimal boxWeight;

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