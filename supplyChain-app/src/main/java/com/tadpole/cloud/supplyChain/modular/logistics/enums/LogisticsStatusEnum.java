package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: cyt
 * @description: 海外仓出库管理物流状态
 * @date: 2022/7/25
 */
@Getter
public enum LogisticsStatusEnum {
    EMPTY("空"),
    NOT_CONFIRM("未发货"),
    PART_CONFIRM("部分发货"),
    ALREADY_CONFIRM("全部发货");

    /**
     * 海外仓入库管理物流状态
     */
    private String name;

    LogisticsStatusEnum(String name) {
        this.name = name;
    }

    public static List<Map<String, Object>> getLogisticsStatus() {
        List<Map<String, Object>> result = new ArrayList<>();
        LogisticsStatusEnum[] enumAry = LogisticsStatusEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<String, Object> logisticsStatusMap = new HashMap<>();
            logisticsStatusMap.put("logisticsStatus", enumAry[i].getName());
            result.add(logisticsStatusMap);
        }
        return result;
    }
}
