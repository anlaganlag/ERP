package com.tadpole.cloud.platformSettlement.api.finance.model.result;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
* <p>
* AZ结算报告审核
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@ApiModel
public class MaterialResult implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty("ID")
    private BigDecimal id;

    /** 物料编码 */
    @ApiModelProperty("MATERIAL_CODE")
    private String materialCode;

    /** 运营大类 */
    @ApiModelProperty("PRODUCT_TYPE")
    private String productType;
}