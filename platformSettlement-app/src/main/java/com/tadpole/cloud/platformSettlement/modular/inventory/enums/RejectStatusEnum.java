package com.tadpole.cloud.platformSettlement.modular.inventory.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: cyt
 * @description: 申请类型类
 * @date: 2022/5/24
 */
@Getter
public enum RejectStatusEnum {

    ZJSP("0", "逐级审批"),
    ZDBJD("1", "直达本节点");

    /**
     * 枚举值
     */
    private String code;

    /**
     * 枚举名称
     */
    private String name;

    RejectStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static RejectStatusEnum getEnum(String value) {
        RejectStatusEnum resultEnum = null;
        RejectStatusEnum[] enumAry = RejectStatusEnum.values();
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
        RejectStatusEnum resultEnum = null;
        RejectStatusEnum[] enumAry = RejectStatusEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<String,String>  map = new HashMap<String,String>();
            map.put(enumAry[i].getCode(),enumAry[i].getName());
            list.add(map);
        }
        return list;
    }

}
