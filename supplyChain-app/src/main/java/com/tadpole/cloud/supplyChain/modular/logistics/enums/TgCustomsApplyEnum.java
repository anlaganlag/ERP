package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 是否正式报关
 * @date: 2023/6/19
 */
@Getter
public enum TgCustomsApplyEnum {

    YES("是", "是"),
    NO("否", "否");

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    TgCustomsApplyEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static List<Map<String, Object>> getTgCustomsApply() {
        List<Map<String, Object>> result = new ArrayList<>();
        TgCustomsApplyEnum[] enumAry = TgCustomsApplyEnum.values();
        for (TgCustomsApplyEnum tgCustomsApplyEnum : enumAry) {
            Map<String, Object> customsApplyMap = new HashMap<>();
            customsApplyMap.put("code", tgCustomsApplyEnum.getCode());
            customsApplyMap.put("name", tgCustomsApplyEnum.getName());
            result.add(customsApplyMap);
        }
        return result;
    }
}
