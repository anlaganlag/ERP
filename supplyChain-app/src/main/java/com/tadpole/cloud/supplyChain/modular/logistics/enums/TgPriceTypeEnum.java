package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 通关价格类型
 * @date: 2023/5/23
 */
@Getter
public enum TgPriceTypeEnum {
    BUY("采购价"),
    SALE("销售价");

    /**
     * 价格类型
     */
    private String name;

    TgPriceTypeEnum(String name) {
        this.name = name;
    }

    public static List<Map<String, Object>> getTgPriceType() {
        List<Map<String, Object>> result = new ArrayList<>();
        TgPriceTypeEnum[] enumAry = TgPriceTypeEnum.values();
        for (TgPriceTypeEnum tgPriceTypeEnum : enumAry) {
            Map<String, Object> auditStatusMap = new HashMap<>();
            auditStatusMap.put("name", tgPriceTypeEnum.getName());
            result.add(auditStatusMap);
        }
        return result;
    }
}
