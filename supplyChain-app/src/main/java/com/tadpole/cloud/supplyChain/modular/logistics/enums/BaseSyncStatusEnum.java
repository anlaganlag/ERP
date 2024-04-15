package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

/**
 * @author: ty
 * @description: 同步第三方接口状态枚举类
 * @date: 2022/7/22
 */
@Getter
public enum BaseSyncStatusEnum {

    DEFAULT("0", "未同步"),
    SUCCESS("1", "同步成功"),//组织调拨为已保存
    ERROR("-1", "同步失败"),
    SUBMIT("2", "已提交"),//组织调拨
    AUDIT_SUCCESS("3", "审核成功"),//组织调拨
    AUDIT_ERROR("4", "审核失败"),//组织调拨
    NOT("5", "无需同步");

    /**
     * 同步编码
     */
    private String code;

    /**
     * 同步名称
     */
    private String name;


    BaseSyncStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
