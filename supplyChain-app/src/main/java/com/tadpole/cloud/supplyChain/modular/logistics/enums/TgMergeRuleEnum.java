package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 规则合并
 * @date: 2023/5/29
 */
@Getter
public enum TgMergeRuleEnum {

    INVOICE_PRO_NAME_EN("INVOICE_PRO_NAME_EN","英文品名合并"),
    CLEAR_MATERIAL_EN("CLEAR_MATERIAL_EN","英文材质合并"),
    HSCODE("HSCODE", "HSCode合并");

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    TgMergeRuleEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static List<Map<String, Object>> getMergeRule() {
        List<Map<String, Object>> result = new ArrayList<>();
        TgMergeRuleEnum[] enumAry = TgMergeRuleEnum.values();
        for (TgMergeRuleEnum tgMergeRuleEnum : enumAry) {
            Map<String, Object> mergeRuleMap = new HashMap<>();
            mergeRuleMap.put("code", tgMergeRuleEnum.getCode());
            mergeRuleMap.put("name", tgMergeRuleEnum.getName());
            result.add(mergeRuleMap);
        }
        return result;
    }
}
