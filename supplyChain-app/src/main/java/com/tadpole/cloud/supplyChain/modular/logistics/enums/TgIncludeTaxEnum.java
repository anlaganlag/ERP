package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 含税状态
 * @date: 2023/5/22
 */
@Getter
public enum TgIncludeTaxEnum {

    YES("1","是"),
    NO("0", "否");

    /**
     * 含税状态编码
     */
    private String code;

    /**
     * 含税状态名称
     */
    private String name;

    TgIncludeTaxEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static List<Map<String, Object>> getIncludeTax() {
        List<Map<String, Object>> result = new ArrayList<>();
        TgIncludeTaxEnum[] enumAry = TgIncludeTaxEnum.values();
        for (TgIncludeTaxEnum tgIncludeTaxEnum : enumAry) {
            Map<String, Object> includeTaxMap = new HashMap<>();
            includeTaxMap.put("code", tgIncludeTaxEnum.getCode());
            includeTaxMap.put("name", tgIncludeTaxEnum.getName());
            result.add(includeTaxMap);
        }
        return result;
    }
}
