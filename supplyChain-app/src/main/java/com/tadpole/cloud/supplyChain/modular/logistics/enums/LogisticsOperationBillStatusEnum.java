package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 对账状态
 * @date: 2022/11/18
 */
@Getter
public enum LogisticsOperationBillStatusEnum {

    NOT("未对账"),
    ALREADY("已对账");

    /**
     * 对账状态
     */
    private String name;

    LogisticsOperationBillStatusEnum(String name) {
        this.name = name;
    }

    public static List<Map<String, Object>> getBillStatus() {
        List<Map<String, Object>> result = new ArrayList<>();
        LogisticsOperationBillStatusEnum[] enumAry = LogisticsOperationBillStatusEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<String, Object> billStatusMap = new HashMap<>();
            billStatusMap.put("billStatus", enumAry[i].getName());
            result.add(billStatusMap);
        }
        return result;
    }
}
