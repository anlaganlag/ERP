package com.tadpole.cloud.platformSettlement.modular.vat.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 报告审核状态
 * @date: 2021/10/30
 */
@Getter
public enum ReportCheckStatus {

    WSH(1, "未审核"),
    YZDSH(2, "已自动审核"),
    YSDSH(3, "已手动审核"),
    YZF(4, "已作废"),
    YSC(5, "已生成");

    /**
     * 审核编码
     */
    private Integer code;

    /**
     * 审核名称
     */
    private String name;


    ReportCheckStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ReportCheckStatus getEnumValue(String name) {
        ReportCheckStatus resultEnum = null;
        ReportCheckStatus[] enumAry = ReportCheckStatus.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getName().equals(name)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

    public static ReportCheckStatus getEnumName(String value) {
        ReportCheckStatus resultEnum = null;
        ReportCheckStatus[] enumAry = ReportCheckStatus.values();
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
        ReportCheckStatus[] enumAry = ReportCheckStatus.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<Integer,String>  map = new HashMap<Integer,String>();
            map.put(enumAry[i].getCode(),enumAry[i].getName());
            list.add(map);
        }
        return list;
    }
}
