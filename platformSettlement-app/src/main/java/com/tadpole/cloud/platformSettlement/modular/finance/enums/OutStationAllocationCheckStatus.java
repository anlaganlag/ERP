package com.tadpole.cloud.platformSettlement.modular.finance.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: dul
 * @description: 补贴汇总确认状态
 * @date: 2021/10/30
 */
@Getter
public enum OutStationAllocationCheckStatus {

    WQR(0, "未确认"),
    YQR(1, "已确认");

    /**
     * 审核编码
     */
    private Integer code;

    /**
     * 审核名称
     */
    private String name;


    OutStationAllocationCheckStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static int getEnumValue(String name) {
        OutStationAllocationCheckStatus resultEnum = null;
        Integer code = null;
        OutStationAllocationCheckStatus[] enumAry = OutStationAllocationCheckStatus.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getName().equals(name)) {
                resultEnum = enumAry[i];
                code = resultEnum.getCode();
                break;
            }
        }
        return code;
    }

    public static OutStationAllocationCheckStatus getEnumName(String value) {
        OutStationAllocationCheckStatus resultEnum = null;
        OutStationAllocationCheckStatus[] enumAry = OutStationAllocationCheckStatus.values();
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
        OutStationAllocationCheckStatus resultEnum = null;
        OutStationAllocationCheckStatus[] enumAry = OutStationAllocationCheckStatus.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<Integer,String>  map = new HashMap<Integer,String>();
            map.put(enumAry[i].getCode(),enumAry[i].getName());
            list.add(map);
        }
        return list;
    }
}
