package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 合并状态
 * @date: 2023/5/31
 */
@Getter
public enum TgMergeStatusEnum {

    NOT_MERGE("0","未合并"),
    MERGE("1","已合并");

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    TgMergeStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static List<Map<String, Object>> getMergeStatus() {
        List<Map<String, Object>> result = new ArrayList<>();
        TgMergeStatusEnum[] enumAry = TgMergeStatusEnum.values();
        for (TgMergeStatusEnum tgMergeStatusEnum : enumAry) {
            Map<String, Object> mergeStatusMap = new HashMap<>();
            mergeStatusMap.put("code", tgMergeStatusEnum.getCode());
            mergeStatusMap.put("name", tgMergeStatusEnum.getName());
            result.add(mergeStatusMap);
        }
        return result;
    }
}
