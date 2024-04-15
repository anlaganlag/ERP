package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: ty
 * @description: 批量锁定/解锁入参
 * @date: 2023/11/29
 */
@Data
public class LsLogisticsNoCheckLockParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;

    /** ID集合 */
    @ApiModelProperty("ID集合")
    private List<BigDecimal> idList;

    /** 锁定状态：锁定，未锁定 */
    @ApiModelProperty("锁定状态：锁定，未锁定")
    private String lockStatus;

}
