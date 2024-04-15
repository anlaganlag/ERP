package com.tadpole.cloud.externalSystem.modular.mabang.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
* <p>
    * 拉取马帮发货订单明细参数
    * </p>
*
* @author cyt
* @since 2022-09-02
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SyncSaleOutOrderParam implements Serializable, BaseValidatingParam {

    /**
     * 年份
     */
    @ApiModelProperty("年份")
    private String year;

    /**
     * 月份
     */
    @ApiModelProperty("月份")
    private String month;

    /**
     * 年月
     */
    @ApiModelProperty("年月")
    private String yearMonth;

    /**
     * 店铺id
     */
    @ApiModelProperty("店铺id")
    private String shopId;

    /**
     * 金蝶组织编码
     */
    @ApiModelProperty("金蝶组织编码")
    private String financeCode;

    /**
     * 马帮店铺原始财务编码
     */
    @ApiModelProperty("马帮店铺原始财务编码")
    private String originalFinanceCode;

    /**
     * SKU
     */
    @ApiModelProperty("SKU")
    private String sku;

}