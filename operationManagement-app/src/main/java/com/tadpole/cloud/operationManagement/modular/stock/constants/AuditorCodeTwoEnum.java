package com.tadpole.cloud.operationManagement.modular.stock.constants;

import lombok.Getter;

/**
 * @author: ty
 * @description: 审核节点编码枚举类
 * @date: 2021/10/30
 */
@Getter
public enum AuditorCodeTwoEnum {

    YYRY("yyry", "运营人员", "auditor_code"),
    CPXFZR("cpxfzr", "产品线负责人", "auditor_code"),
    TZG("tzg", "Team主管", "auditor_code"),
    SYBJL("sybjl", "事业部经理", "auditor_code"),
    JHB("jhb", "计划部", "auditor_code");

    /**
     * 审核节点编码
     */
    private String code;

    /**
     * 审核节点名称
     */
    private String name;

    /**
     * 审核节点表字段
     */
    private String columnName;

    AuditorCodeTwoEnum(String code, String name, String columnName) {
        this.code = code;
        this.name = name;
        this.columnName = columnName;
    }

    public static AuditorCodeTwoEnum getEnum(String value) {
        AuditorCodeTwoEnum resultEnum = null;
        AuditorCodeTwoEnum[] enumAry = AuditorCodeTwoEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getCode().equals(value)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }
}
