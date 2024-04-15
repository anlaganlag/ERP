package com.tadpole.cloud.platformSettlement.modular.vat.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: cyt
 * @description: 生成状态举类
 * @date: 2022/08/09
 */
@Getter
public enum GenerateStatusEnum {

    DEFAULT(0, "未生成"),
    SUCCESS(1, "已生成");

    /**
     * 同步编码
     */
    private Integer code;

    /**
     * 同步名称
     */
    private String name;


    GenerateStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static List<Map<Integer,String>> getEnumList() {
        List<Map<Integer,String>> list = new ArrayList<>();
        GenerateStatusEnum resultEnum = null;
        GenerateStatusEnum[] enumAry = GenerateStatusEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<Integer,String>  map = new HashMap<Integer,String>();
            map.put(enumAry[i].getCode(),enumAry[i].getName());
            list.add(map);
        }
        return list;
    }
}
