package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 海外仓入库管理签收状态
 * @date: 2022/7/21
 */
@Getter
public enum ConfirmStatusEnum {
    NOT_CONFIRM("待签收"),
    PART_CONFIRM("部分签收"),
    ALREADY_CONFIRM("签收完成");

    /**
     * 海外仓入库管理签收状态
     */
    private String name;

    ConfirmStatusEnum(String name) {
        this.name = name;
    }

    public static List<Map<String, Object>> getConfirmStatus() {
        List<Map<String, Object>> result = new ArrayList<>();
        ConfirmStatusEnum[] enumAry = ConfirmStatusEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<String, Object> confirmStatusMap = new HashMap<>();
            confirmStatusMap.put("confirmStatus", enumAry[i].getName());
            result.add(confirmStatusMap);
        }
        return result;
    }
}
