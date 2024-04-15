package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

/**
 * @author: ty
 * @description: 供应链订单号前缀
 * @date: 2022/7/23
 */
@Getter
public enum OrderTypePreEnum {
    AZYCRK("AZYCRK", "亚马逊移除入库"),
    KZHBRK("KZHBRK", "跨组织换标入库"),
    TZHBRK("TZHBRK", "同组织换标入库"),
    PDRK("PDRK", "盘点入库"),
    CKQD("CKQD", "出库清单出库"),
    KZHBCK("KZHBCK", "跨组织换标出库"),
    TZHBCK("TZHBCK", "同组织换标出库"),
    PDCK("PDCK", "盘点出库"),
    KZHB("KZHB", "跨组织换标"),
    TZHB("TZHB", "同组织换标"),
    PD("PD", "盘点"),
    BGD("BGD", "报关单"),
    QGD("QGD", "清关单");

    /**
     * 海外仓订单号前缀编码
     */
    private String code;

    /**
     * 海外仓订单号前缀名称
     */
    private String name;


    OrderTypePreEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static OrderTypePreEnum getEnumByName(String name) {
        OrderTypePreEnum resultEnum = null;
        OrderTypePreEnum[] enumAry = OrderTypePreEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getName().equals(name)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

    public static OrderTypePreEnum getEnumByValue(String value) {
        OrderTypePreEnum resultEnum = null;
        OrderTypePreEnum[] enumAry = OrderTypePreEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getCode().equals(value)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }
}
