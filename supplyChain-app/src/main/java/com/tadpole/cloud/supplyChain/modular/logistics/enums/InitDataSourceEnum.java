package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

/**
 * @author: ty
 * @description: 海外仓信息初始数据来源标记
 * @date: 2022/10/27
 */
@Getter
public enum InitDataSourceEnum {
    HAND("0", "手动添加"),
    FBA_TO_OVERSEAS("1", "FBA发海外仓"),
    INSIDE_TO_OVERSEAS("2", "国内仓发海外仓"),
    CHANGE("3", "换标");

    /**
     * 标记编码
     */
    private String code;

    /**
     * 标记名称
     */
    private String name;

    InitDataSourceEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
