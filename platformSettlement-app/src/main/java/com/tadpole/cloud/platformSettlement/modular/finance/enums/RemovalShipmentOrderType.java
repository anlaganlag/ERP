package com.tadpole.cloud.platformSettlement.modular.finance.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ty
 * @description: 销毁移除订单类型
 * @date: 2022/5/19
 */
@Getter
public enum RemovalShipmentOrderType {

    ORDER_TYPE_F("F","移除至服务商"),
    ORDER_TYPE_H("H", "移除至海外仓"),
    ORDER_TYPE_T("T", "销毁按月分摊"),
    ORDER_TYPE_Z("Z","销毁当月分摊"),
    ORDER_TYPE_DISPOSAL("Disposal","Disposal");

    /**
     * 销毁移除订单类型编码
     */
    private String code;

    /**
     * 销毁移除订单类型名称
     */
    private String name;

    RemovalShipmentOrderType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据名称获取枚举值
     * @param name
     * @return
     */
    public static RemovalShipmentOrderType getEnumValue(String name) {
        RemovalShipmentOrderType resultEnum = null;
        RemovalShipmentOrderType[] enumAry = RemovalShipmentOrderType.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getName().equals(name)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

    /**
     * 根据编码获取枚举值
     * @param code
     * @return
     */
    public static RemovalShipmentOrderType getEnumName(String code) {
        RemovalShipmentOrderType resultEnum = null;
        RemovalShipmentOrderType[] enumAry = RemovalShipmentOrderType.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getCode().equals(code)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

    public static List<String> getEnumValueList() {
        List<String> list = new ArrayList<>();
        RemovalShipmentOrderType[] enumAry = RemovalShipmentOrderType.values();
        for (int i = 0; i < enumAry.length; i++) {
            list.add(enumAry[i].getName());
        }
        return list;
    }
}
