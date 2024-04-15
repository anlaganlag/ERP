package com.tadpole.cloud.externalSystem.modular.mabang.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: cyt
 * @description: 同步第三方接口状态枚举类
 * @date: 2022/8/24
 */
@Getter
public enum SyncBaseStatusEnum {

    DEFAULT("-1", "未推送"),
    ERROR("0", "推送失败"),
    SUCCESS("1", "推送成功"),
    AUDIT_ERROR("3", "审核失败");

    /**
     * 同步编码
     */
    private String code;

    /**
     * 同步名称
     */
    private String name;


    SyncBaseStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static List<Map<String,String>> getEnumList() {
        List<Map<String,String>> list = new ArrayList<>();
        SyncBaseStatusEnum[] enumAry = SyncBaseStatusEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<String,String>  map = new HashMap<String,String>();
            map.put(enumAry[i].getCode(),enumAry[i].getName());
            list.add(map);
        }
        return list;
    }

}
