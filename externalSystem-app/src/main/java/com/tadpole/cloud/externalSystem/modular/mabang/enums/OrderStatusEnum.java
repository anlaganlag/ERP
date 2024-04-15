package com.tadpole.cloud.externalSystem.modular.mabang.enums;

import lombok.Getter;

/**
 * @author: cyt
 * @description: 同步第三方接口状态枚举类
 * @date: 2022/8/24
 */
@Getter
public enum OrderStatusEnum {

    DISTRIBUTION("2", "配货中"), //配货中
    SHIPPED("3", "已发货"),//已发货
    COMPLETED("4", "已完成"),//已完成
    INVALID("5", "已作废");//已作废

    /**
     * 同步编码
     */
    private String code;

    /**
     * 同步名称
     */
    private String name;


    OrderStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
