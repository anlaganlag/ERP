package com.tadpole.cloud.platformSettlement.modular.inventory.enums;

import lombok.Getter;

/**
 * @author: cyt
 * @description: 审批节点状态枚举类
 * @date: 2022/5/24
 */
@Getter
public enum AuditStatusEnum {

    AUDIT_STATUS_0("0", "初始导入"),
    AUDIT_STATUS_1("1", "申请"),
    AUDIT_STATUS_2("2", "中心最高负责人审批"),
    AUDIT_STATUS_3("3", "财务审批"),
    AUDIT_STATUS_4("4", "总经理审批"),
    AUDIT_STATUS_5("5", "归档");

    /**
     * 枚举值
     */
    private String code;

    /**
     * 枚举名称
     */
    private String name;

    AuditStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static AuditStatusEnum getEnum(String value) {
        AuditStatusEnum resultEnum = null;
        AuditStatusEnum[] enumAry = AuditStatusEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getCode().equals(value)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }
}
