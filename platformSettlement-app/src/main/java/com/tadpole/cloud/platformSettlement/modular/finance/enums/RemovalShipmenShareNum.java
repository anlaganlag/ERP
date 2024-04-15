package com.tadpole.cloud.platformSettlement.modular.finance.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 销毁移除成本摊销期枚举类
 * @date: 2022/5/19
 */
@Getter
public enum RemovalShipmenShareNum {

    SHARE_NUM_0(0, "空"),
    SHARE_NUM_1(1, "1"),
    SHARE_NUM_2(2, "2"),
    SHARE_NUM_3(3, "3"),
    SHARE_NUM_4(4, "4"),
    SHARE_NUM_5(5, "5"),
    SHARE_NUM_6(6, "6"),
    SHARE_NUM_7(7, "7"),
    SHARE_NUM_8(8, "8"),
    SHARE_NUM_9(9, "9"),
    SHARE_NUM_10(10, "10"),
    SHARE_NUM_11(11, "11"),
    SHARE_NUM_12(12, "12");

    /**
     * 审核编码
     */
    private Integer code;

    /**
     * 审核名称
     */
    private String name;


    RemovalShipmenShareNum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static RemovalShipmenShareNum getEnumValue(String name) {
        RemovalShipmenShareNum resultEnum = null;
        RemovalShipmenShareNum[] enumAry = RemovalShipmenShareNum.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getName().equals(name)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

    public static RemovalShipmenShareNum getEnumName(String value) {
        RemovalShipmenShareNum resultEnum = null;
        RemovalShipmenShareNum[] enumAry = RemovalShipmenShareNum.values();
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
        RemovalShipmenShareNum[] enumAry = RemovalShipmenShareNum.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<Integer,String>  map = new HashMap<Integer,String>();
            map.put(enumAry[i].getCode(),enumAry[i].getName());
            list.add(map);
        }
        return list;
    }
}
