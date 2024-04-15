package com.tadpole.cloud.platformSettlement.modular.finance.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ty
 * @description: 领星店铺维度数据更新信息类型
 * @date: 2022/5/17
 */
@Getter
public enum LxShopSynType {
    TRANSACTION("0", "Date Range"),
    SETTLEMENT("1", "Settlement"),
    REFUND("2", "refundOrders");

    /**
     * 同步类型
     */
    private String code;

    /**
     * 同步名称
     */
    private String name;


    LxShopSynType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static LxShopSynType getEnumValue(String name) {
        LxShopSynType resultEnum = null;
        LxShopSynType[] enumAry = LxShopSynType.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getName().equals(name)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

    public static LxShopSynType getEnumName(String value) {
        LxShopSynType resultEnum = null;
        LxShopSynType[] enumAry = LxShopSynType.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getCode().equals(value)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

    public static List<String> getEnumValueList() {
        List<String> list = new ArrayList<>();
        LxShopSynType[] enumAry = LxShopSynType.values();
        for (int i = 0; i < enumAry.length; i++) {
            list.add(enumAry[i].getCode());
        }
        return list;
    }
}
