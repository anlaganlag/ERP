package com.tadpole.cloud.product.modular.productplan.enums;

import lombok.Getter;

/**
 * @author: ljx
 * @description: 提案阶段
 * @date: 2022/7/21
 */
@Getter
public enum RdProposalEnum {
    TA_PAGE_NEW("新增"),
    TA_PAGE_EDIT("编辑"),
    TA_STATE_NEW("新提案"),
    TA_STATE_ARCH("已归档"),
    TA_PROCESS_DESIGN("设计中"),
    TA_PROCESS_SAMPLE("拿样中"),
    TA_PROCESS_ORDER("定品中"),
    TA_PROCESS_ARCHIVE("已归档"),
    TA_ARCH_TYPE_APPR("审批归档"),
    TA_ARCH_TYPE_REVOKE("撤销归档")
    ;


    /**
     * 海外仓入库管理签收状态
     */
    private String name;

    RdProposalEnum(String name) {
        this.name = name;
    }

}
