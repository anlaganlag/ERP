package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *  BTB物流单明细入参类
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
public class LsBtbLogisticsNoDetailParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.AUTO)
    private BigDecimal id;

    /** 发货批次号 */
    @ApiModelProperty("发货批次号")
    private String shipmentNum;

    /** BTB订单号 */
    @ApiModelProperty("BTB订单号")
    private String orderNo;

    /** 箱条码 */
    @ApiModelProperty("箱条码")
    private String boxBarcode;

    /** 箱号 */
    @ApiModelProperty("箱号")
    private String boxNo;

    /** 体积CBM */
    @ApiModelProperty("体积CBM")
    private BigDecimal boxVolume;

    /** 重量KG */
    @ApiModelProperty("重量KG")
    private BigDecimal boxWeight;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty("创建人")
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty("更新人")
    private String updateUser;

}
