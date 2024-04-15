package com.tadpole.cloud.platformSettlement.modular.inventory.enums;

import lombok.Getter;

/**
 * @author: cyt
 * @description: 申请类型类
 * @date: 2022/5/24
 */
@Getter
public enum ApplicationTypeEnum {

    KCYC("KCYC", "KCYC-库存移除"),
    KCXH("KCXH", "KCXH-库存销毁");

    /**
     * 枚举值
     */
    private String code;

    /**
     * 枚举名称
     */
    private String name;

    ApplicationTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ApplicationTypeEnum getEnum(String value) {
        ApplicationTypeEnum resultEnum = null;
        ApplicationTypeEnum[] enumAry = ApplicationTypeEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getCode().equals(value)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

    public static String getValueByKey(String code){
        ApplicationTypeEnum[] applicationTypeEnums=values();
        for(ApplicationTypeEnum applicationType:applicationTypeEnums){
            if(applicationType.getCode().equals(code)){
                return applicationType.getName();
            }
        }
        return null;
    }

}
