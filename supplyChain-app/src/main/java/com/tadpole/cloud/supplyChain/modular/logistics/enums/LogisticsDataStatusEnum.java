package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 物流实际结算明细数据状态
 * @date: 2022/12/16
 */
@Getter
public enum LogisticsDataStatusEnum {
    DEL(0, "部分发货"),
    NORMAL(1,"全部发货");

    /**
     * 数据状态编码
     */
    private Integer code;

    /**
     * 数据状态名称
     */
    private String name;

    LogisticsDataStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static List<Map<Integer, Object>> getLogisticsDataStatus() {
        List<Map<Integer, Object>> result = new ArrayList<>();
        LogisticsDataStatusEnum[] enumAry = LogisticsDataStatusEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<Integer, Object> logisticsDataStatusMap = new HashMap<>();
            logisticsDataStatusMap.put(enumAry[i].getCode(), enumAry[i].getName());
            result.add(logisticsDataStatusMap);
        }
        return result;
    }
}
