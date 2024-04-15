package com.tadpole.cloud.platformSettlement.modular.sales.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/**
 * @author: dul
 * @description: 销量目标确认状态
 * @date: 2021/10/30
 */
@Getter
public enum SeasonEnum {

    Q1("Q1", "一季度"),
    Q2("Q2", "二季度"),
    Q3("Q3", "三季度"),
    Q4("Q4", "四季度");

    private String code;

    private String name;


    SeasonEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getEnumValue(String name) {
        SeasonEnum resultEnum = null;
        String code = null;
        SeasonEnum[] enumAry = SeasonEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getName().equals(name)) {
                resultEnum = enumAry[i];
                code = resultEnum.getCode();
                break;
            }
        }
        return code;
    }

    public static SeasonEnum getEnumName(String value) {
        SeasonEnum resultEnum = null;
        SeasonEnum[] enumAry = SeasonEnum.values();
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
        SeasonEnum resultEnum = null;
        SeasonEnum[] enumAry = SeasonEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            Map<String,String>  map = new HashMap<String,String>();
            map.put(enumAry[i].getCode(),enumAry[i].getName());
            list.add(map);
        }
        return list;
    }
}
