package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 物流跟踪状态
 * @date: 2022/9/16
 */
@Getter
public enum LogisticsTrackStatusEnum {
    STATUS_1("已发货"),
    STATUS_2("出口报关中"),
    STATUS_3("国外清关中"),
    STATUS_4("尾程派送中"),
    STATUS_5("已签收"),
    STATUS_6("物流完结");

    /**
     * 海外仓入库管理签收状态
     */
    private String name;

    LogisticsTrackStatusEnum(String name) {
        this.name = name;
    }

    public static List<Map<String, Object>> getLogisticsTrackStatus() {
        List<Map<String, Object>> result = new ArrayList<>();
        LogisticsTrackStatusEnum[] enumAry = LogisticsTrackStatusEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<String, Object> confirmStatusMap = new HashMap<>();
            confirmStatusMap.put("logisticsTrackStatus", enumAry[i].getName());
            result.add(confirmStatusMap);
        }
        return result;
    }
}
