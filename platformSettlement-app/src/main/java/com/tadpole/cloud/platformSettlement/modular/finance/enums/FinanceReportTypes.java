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
public enum FinanceReportTypes {

    WQR(0, "Data Range"),
    YQR(1, "Settlement");

    /**
     * 审核编码
     */
    private Integer code;

    /**
     * 审核名称
     */
    private String name;


    FinanceReportTypes(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static FinanceReportTypes getEnumValue(String name) {
        FinanceReportTypes resultEnum = null;
        FinanceReportTypes[] enumAry = FinanceReportTypes.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getName().equals(name)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

    public static FinanceReportTypes getEnumName(String value) {
        FinanceReportTypes resultEnum = null;
        FinanceReportTypes[] enumAry = FinanceReportTypes.values();
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
        FinanceReportTypes resultEnum = null;
        FinanceReportTypes[] enumAry = FinanceReportTypes.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<Integer,String>  map = new HashMap<Integer,String>();
            map.put(enumAry[i].getCode(),enumAry[i].getName());
            list.add(map);
        }
        return list;
    }
}
