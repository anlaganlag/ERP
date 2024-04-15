package com.tadpole.cloud.externalSystem.modular.mabang.enums;

import lombok.Getter;

/**
 * @author: cyt
 * @description: 商品处理状态枚举类
 * @date: 2022/9/13
 */
@Getter
public enum CommodityStatusEnum {

    WAITHANDLE("1", "待处理"), //待处理
    CHECKIN("2", "验货入库"),//验货入库
    NATURALLOSS("3", "自然耗损");//自然耗损

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;


    CommodityStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
