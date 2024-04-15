package com.tadpole.cloud.operationManagement.modular.shipment.model.params;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
* <p>
    * 发货追踪汇总表
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SkuCheckParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** sku */
    @ApiModelProperty("sku")
    private String sku;


    /** 店铺 */
    @ApiModelProperty("店铺")
    private String shopName;


    /** 站点 */
    @ApiModelProperty("站点")
    private String site;


    /** 申请批次号 */
    @ApiModelProperty("申请批次号")
    private String applyBatchNo;





    @ApiModelProperty("item_sku")
    private String item_sku;




}
