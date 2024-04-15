package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description:
 * @date: 2022/9/7
 */
@Getter
public enum OverseasBusinessTypeEnum {
    /**
     * 国内仓发海外仓（未签收）、亚马逊仓发海外仓（未签收）
     */
    COME("来货"),

    /**
     * 国内仓发海外仓（已签收）、亚马逊仓发海外仓（已签收）、跨站点换标入库、同站点换标入库
     */
    IN("入库"),

    /**
     * 海外仓发亚马逊仓、乐天海外仓出库、跨站点换标出库、同站点换标出库
     */
    OUT("出库"),

    /**
     * 盘点
     */
    CHECK_ADD("盘盈"),

    /**
     * 盘点
     */
    CHECK_SUB("盘亏"),

    /**
     * 关闭来货
     */
    CLOSE_SHIPMENT("关闭来货");

    /**
     * 海外仓入库业务类型名称
     */
    private String name;

    OverseasBusinessTypeEnum(String name) {
        this.name = name;
    }

    public static List<Map<String, Object>> getBusinessType() {
        List<Map<String, Object>> result = new ArrayList<>();
        OverseasBusinessTypeEnum[] enumAry = OverseasBusinessTypeEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<String, Object> businessTypeMap = new HashMap<>();
            businessTypeMap.put("businessType", enumAry[i].getName());
            result.add(businessTypeMap);
        }
        return result;
    }
}
