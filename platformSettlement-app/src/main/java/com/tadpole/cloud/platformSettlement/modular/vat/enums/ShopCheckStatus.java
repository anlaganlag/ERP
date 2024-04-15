package com.tadpole.cloud.platformSettlement.modular.vat.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 结算报告审核状态
 * @date: 2021/10/30
 */
@Getter
public enum ShopCheckStatus {

    ZC(0, "正常"),
    BGQS(1, "VAT报告缺失");

    /**
     * 审核编码
     */
    private Integer code;

    /**
     * 审核名称
     */
    private String name;


    ShopCheckStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ShopCheckStatus getEnumValue(String name) {
        ShopCheckStatus resultEnum = null;
        ShopCheckStatus[] enumAry = ShopCheckStatus.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getName().equals(name)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

    public static ShopCheckStatus getEnumName(String value) {
        ShopCheckStatus resultEnum = null;
        ShopCheckStatus[] enumAry = ShopCheckStatus.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getCode().equals(value)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

    public static List<Map<Integer,String>> getEnumList() {
        List<Map<Integer,String>> list = new ArrayList<>();
        ShopCheckStatus[] enumAry = ShopCheckStatus.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<Integer,String>  map = new HashMap<Integer,String>();
            map.put(enumAry[i].getCode(),enumAry[i].getName());
            list.add(map);
        }
        return list;
    }
}
