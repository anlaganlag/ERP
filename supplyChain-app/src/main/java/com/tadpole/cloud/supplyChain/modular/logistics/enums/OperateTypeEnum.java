package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 海外仓管理操作类型
 * @date: 2022/7/20
 */
@Getter
public enum OperateTypeEnum {
    CHANGE("换标"),
    CHECK("盘点"),
    RAKUTEN("乐天海外仓出库"),
    INSIDE_TO_OVERSEAS("国内仓发海外仓"),
    AMAZON_TO_OVERSEAS("亚马逊仓发海外仓"),
    OVERSEAS_TO_AMAZON("海外仓发亚马逊仓"),
    INSIDE_TO_OVERSEAS_SHIPMENT_DEL("物流单删除"),
    OVERSEAS_TO_WALMART("海外仓发沃尔玛仓");

    /**
     * 操作类型名称
     */
    private String name;

    OperateTypeEnum(String name) {
        this.name = name;
    }

    public static List<Map<String, Object>> getOperateTypeName() {
        List<Map<String, Object>> result = new ArrayList<>();
        OperateTypeEnum[] enumAry = OperateTypeEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<String, Object> operateTypeMap = new HashMap<>();
            operateTypeMap.put("operateType", enumAry[i].getName());
            result.add(operateTypeMap);
        }
        return result;
    }
}
