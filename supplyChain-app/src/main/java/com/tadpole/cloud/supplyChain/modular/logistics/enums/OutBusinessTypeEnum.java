package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 海外仓出库业务类型
 * @date: 2022/7/21
 */
@Getter
public enum OutBusinessTypeEnum {
    OVERSEAS_TO_AMAZON("海外仓发亚马逊仓"),
    DIFF_SITE_OUT_CHANGE("跨站点换标出库"),
    SAME_SITE_OUT_CHANGE("同站点换标出库"),
    CHECK_SUB("盘亏"),
    RAKUTEN("乐天海外仓出库"),
    OVERSEAS_TO_WALMART("海外仓发沃尔玛仓");

    /**
     * 海外仓出库业务类型名称
     */
    private String name;

    OutBusinessTypeEnum(String name) {
        this.name = name;
    }

    public static List<Map<String, Object>> getOutBusinessType() {
        List<Map<String, Object>> result = new ArrayList<>();
        OutBusinessTypeEnum[] enumAry = OutBusinessTypeEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<String, Object> outBusinessTypeMap = new HashMap<>();
            outBusinessTypeMap.put("outBusinessType", enumAry[i].getName());
            result.add(outBusinessTypeMap);
        }
        return result;
    }
}
