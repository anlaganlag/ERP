package com.tadpole.cloud.operationManagement.modular.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * 店铺数据下载任务枚举类
 */

@AllArgsConstructor
@Getter

public enum ShopDataDwTaskEnum {
    // -1：不下载店铺数据 (运营确认不需要拉取数据), 0：数据初始化，默认值, 1：需要创建下载店铺数据任务,  2：已创建店铺数据下载任务，3：缺少必要条件，暂停创建下载任务, 4: EBMS创建下载任务失败,
    NOT_CREATE(BigDecimal.valueOf(-1L), "不下载店铺数据", "不下载店铺数据（运营确认不需要拉取数据）"),
    INIT(BigDecimal.ZERO, "默认值", "数据初始化，默认值"),
    NEED_CREATED(BigDecimal.ONE, "需要创建下载店铺数据任务", "需要创建下载店铺数据任务"),
    CREATED(BigDecimal.valueOf(2L), "已创建店铺数据下载任务", "已创建店铺数据下载任务"),
    MISSING_CONDITIONS(BigDecimal.valueOf(3L), "缺少必要条件", "暂停创建下载任务"),

    CREATE_FAIL(BigDecimal.valueOf(4L), "创建下载任务失败", "EBMS创建下载任务失败"),
    ;


    private BigDecimal code;
    private String name;
    private String remark;


}
