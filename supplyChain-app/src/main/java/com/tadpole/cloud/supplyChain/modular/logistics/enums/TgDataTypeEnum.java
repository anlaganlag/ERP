package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

/**
 * @author: ty
 * @description: 通关数据类型
 * @date: 2023/5/30
 */
@Getter
public enum TgDataTypeEnum {
    NOT_MERGE("0","产品基本信息未合并的数据"),
    MERGE("1", "产品基本信息合并后的合并数据");

    /**
     * 含税状态编码
     */
    private String code;

    /**
     * 含税状态名称
     */
    private String name;

    TgDataTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
