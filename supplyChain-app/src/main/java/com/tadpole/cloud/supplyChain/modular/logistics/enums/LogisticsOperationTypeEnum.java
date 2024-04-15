package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

/**
 * @author: ty
 * @description: 物流实际结算操作类型
 * @date: 2022/11/18
 */
@Getter
public enum LogisticsOperationTypeEnum {

    COMPLETE("完成对账"),
    RESET("重新对账");

    /**
     * 物流实际结算操作类型
     */
    private String name;

    LogisticsOperationTypeEnum(String name) {
        this.name = name;
    }
}
