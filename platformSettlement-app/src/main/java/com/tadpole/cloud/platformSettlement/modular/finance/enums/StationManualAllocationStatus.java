package com.tadpole.cloud.platformSettlement.modular.finance.enums;

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
public enum StationManualAllocationStatus {

    WSH(0, "未确认"),
    YSH(1, "已确认");

    /**
     * 审核编码
     */
    private Integer code;

    /**
     * 审核名称
     */
    private String name;


    StationManualAllocationStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static StationManualAllocationStatus getEnumValue(String name) {
        StationManualAllocationStatus resultEnum = null;
        StationManualAllocationStatus[] enumAry = StationManualAllocationStatus.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getName().equals(name)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

    public static StationManualAllocationStatus getEnumName(String value) {
        StationManualAllocationStatus resultEnum = null;
        StationManualAllocationStatus[] enumAry = StationManualAllocationStatus.values();
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
        StationManualAllocationStatus resultEnum = null;
        StationManualAllocationStatus[] enumAry = StationManualAllocationStatus.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<Integer,String>  map = new HashMap<Integer,String>();
            map.put(enumAry[i].getCode(),enumAry[i].getName());
            list.add(map);
        }
        return list;
    }
}
