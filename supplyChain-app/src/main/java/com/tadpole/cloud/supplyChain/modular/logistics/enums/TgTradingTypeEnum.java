package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 成交方式
 * @date: 2023/6/19
 */
@Getter
public enum TgTradingTypeEnum {
    EXW("EXW", "EXW"),
    FOB("FOB", "FOB"),
    CFR("CFR", "CFR"),
    CIF("CIF", "CIF");

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    TgTradingTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static List<Map<String, Object>> getTgTradingType() {
        List<Map<String, Object>> result = new ArrayList<>();
        TgTradingTypeEnum[] enumAry = TgTradingTypeEnum.values();
        for (TgTradingTypeEnum tgTradingTypeEnum : enumAry) {
            Map<String, Object> tradingTypeMap = new HashMap<>();
            tradingTypeMap.put("code", tgTradingTypeEnum.getCode());
            tradingTypeMap.put("name", tgTradingTypeEnum.getName());
            result.add(tradingTypeMap);
        }
        return result;
    }
}
