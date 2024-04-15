package com.tadpole.cloud.platformSettlement.modular.finance.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 销毁移除月分摊确认状态
 * @date: 2022/5/24
 */
@Getter
public enum RemovalShipmentConfirmStatus {
    NOT_CONFIRM("0", "未确认"),
    CONFIRM("1", "已确认"),
    DESTROY("2", "已作废");

    /**
     * 审核编码
     */
    private String code;

    /**
     * 审核名称
     */
    private String name;


    RemovalShipmentConfirmStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static RemovalShipmentConfirmStatus getEnumValue(String name) {
        RemovalShipmentConfirmStatus resultEnum = null;
        RemovalShipmentConfirmStatus[] enumAry = RemovalShipmentConfirmStatus.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getName().equals(name)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

    public static RemovalShipmentConfirmStatus getEnumName(String value) {
        RemovalShipmentConfirmStatus resultEnum = null;
        RemovalShipmentConfirmStatus[] enumAry = RemovalShipmentConfirmStatus.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getCode().equals(value)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

    public static List<Map<String,String>> getEnumList() {
        List<Map<String,String>> list = new ArrayList<>();
        RemovalShipmentConfirmStatus[] enumAry = RemovalShipmentConfirmStatus.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<String,String>  map = new HashMap<String,String>();
            map.put(enumAry[i].getCode(),enumAry[i].getName());
            list.add(map);
        }
        return list;
    }
}
