package com.tadpole.cloud.supplyChain.api.logistics.model.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @author: ty
 * @description: 同步erp入参类
 * @date: 2022/11/23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SyncErpParam implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 单号 */
    @ApiModelProperty("单号")
    private String mcOrder;
}
