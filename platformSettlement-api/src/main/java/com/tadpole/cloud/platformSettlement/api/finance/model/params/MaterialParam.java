package com.tadpole.cloud.platformSettlement.api.finance.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
* <p>
* 设置期初余额
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Data
@ApiModel
public class MaterialParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty("ID")
    private BigDecimal id;

    @ApiModelProperty("MATERIAL_CODE")
    private String materialCode;

}