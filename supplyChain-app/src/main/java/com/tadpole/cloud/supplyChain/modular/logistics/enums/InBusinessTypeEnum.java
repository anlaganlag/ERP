package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 海外仓入库业务类型
 * @date: 2022/7/21
 */
@Getter
public enum InBusinessTypeEnum {
    INSIDE_TO_OVERSEAS("国内仓发海外仓"),
    AMAZON_TO_OVERSEAS("亚马逊仓发海外仓"),
    DIFF_SITE_IN_CHANGE("跨站点换标入库"),
    SAME_SITE_IN_CHANGE("同站点换标入库"),
    CHECK_ADD("盘盈");

    /**
     * 海外仓入库业务类型名称
     */
    private String name;

    InBusinessTypeEnum(String name) {
        this.name = name;
    }

    public static List<Map<String, Object>> getInBusinessType() {
        List<Map<String, Object>> result = new ArrayList<>();
        InBusinessTypeEnum[] enumAry = InBusinessTypeEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<String, Object> inBusinessTypeMap = new HashMap<>();
            inBusinessTypeMap.put("inBusinessType", enumAry[i].getName());
            result.add(inBusinessTypeMap);
        }
        return result;
    }
}
