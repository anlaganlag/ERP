package com.tadpole.cloud.platformSettlement.modular.finance.enums;

import lombok.Getter;

/**
 * @author: ty
 * @description: 领星店铺维度数据更新信息状态
 * @date: 2022/5/17
 */
@Getter
public enum LxShopSynStatus {
    DEFAULT("0", "未同步"),
    SUCCESS("1", "同步成功"),
    NONE("2", "无数据"),
    ERROR("-1", "同步失败");

    /**
     * 同步类型
     */
    private String code;

    /**
     * 同步名称
     */
    private String name;


    LxShopSynStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static LxShopSynStatus getEnumValue(String name) {
        LxShopSynStatus resultEnum = null;
        LxShopSynStatus[] enumAry = LxShopSynStatus.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getName().equals(name)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

    public static LxShopSynStatus getEnumName(String value) {
        LxShopSynStatus resultEnum = null;
        LxShopSynStatus[] enumAry = LxShopSynStatus.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getCode().equals(value)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }
}
