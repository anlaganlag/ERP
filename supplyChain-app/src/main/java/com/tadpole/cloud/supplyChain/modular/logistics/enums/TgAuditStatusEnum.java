package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ty
 * @description: 通关审核状态
 * @date: 2023/5/22
 */
@Getter
public enum TgAuditStatusEnum {
    PASS("1","审核通过"),
    NOT("0", "未审核"),
    RESET("2", "反审"),
    AUDIT("3", "待审核");

    /**
     * 审核状态编码
     */
    private String code;

    /**
     * 审核状态名称
     */
    private String name;

    TgAuditStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static List<Map<String, Object>> getTgAuditStatus() {
        List<Map<String, Object>> result = new ArrayList<>();
        TgAuditStatusEnum[] enumAry = TgAuditStatusEnum.values();
        for (TgAuditStatusEnum tgAuditStatusEnum : enumAry) {
            Map<String, Object> auditStatusMap = new HashMap<>();
            if (TgAuditStatusEnum.RESET.getCode().equals(tgAuditStatusEnum.getCode())) {
                continue;
            }
            auditStatusMap.put("code", tgAuditStatusEnum.getCode());
            auditStatusMap.put("name", tgAuditStatusEnum.getName());
            result.add(auditStatusMap);
        }
        return result;
    }
}
