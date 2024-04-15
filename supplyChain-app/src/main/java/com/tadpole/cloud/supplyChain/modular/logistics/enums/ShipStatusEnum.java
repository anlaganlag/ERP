package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

/**
 * @author: ty
 * @description: 海外仓发货状态
 * @date: 2022/9/22
 */
@Getter
public enum ShipStatusEnum {
    SHIP_NOT("未发货"),
    SHIP_ING("发货中");

    /**
     * 操作类型名称
     */
    private String name;

    ShipStatusEnum(String name) {
        this.name = name;
    }
}
