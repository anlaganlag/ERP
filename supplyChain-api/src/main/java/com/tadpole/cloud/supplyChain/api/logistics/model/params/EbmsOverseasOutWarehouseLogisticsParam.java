package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 海外仓出库管理明细入参类
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EbmsOverseasOutWarehouseLogisticsParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /** 票单号 */
    @ApiModelProperty("票单号")
    private String packCode;

    /** 箱条码 */
    @ApiModelProperty("箱条码")
    private String packDetBoxCode;

    /** 箱号 */
    @ApiModelProperty("箱号")
    private BigDecimal packDetBoxNum;

    /** 承运商 */
    @ApiModelProperty("承运商")
    private String logisticsCompany;

    /** 物流单号 */
    @ApiModelProperty("物流单号")
    private String logisticsNum;

}