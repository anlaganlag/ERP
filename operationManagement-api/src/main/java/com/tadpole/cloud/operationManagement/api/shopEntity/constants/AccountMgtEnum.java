package com.tadpole.cloud.operationManagement.api.shopEntity.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账户管理常量枚举
 */
@AllArgsConstructor
@Getter
public enum AccountMgtEnum {
    /**
     * 资金流向
     */
    FLOW_IN(1,"资金流入","充值"),
    FLOW_OUT(-1,"资金流出","支出"),
    FLOW_ERROR(0,"资金异常","异常流水待排查后更改流入流出"),

    /**
     * 资金业务类型
     */
    BIZ_FLOW_TYPE_DEPOSIT_AMOUNT(100,"deposit_amount","预存金额"),
    BIZ_FLOW_TYPE_REAL_TIME_AMOUNT(101,"real_time_amount","实时金额"),
    /**
     * 数据来源
     */
    BIZ_DATA_SOURCES_operationManagement(500,"operationManagement","数据来源运营服务"),
    BIZ_DATA_SOURCES_platformSettlement(501,"platformSettlement","数据来源平台结算服务"),
    BIZ_DATA_SOURCES_externalSystem(502,"platformSettlement","数据来源第三方扩展服务"),
    BIZ_DATA_SOURCES_supplyChain(503,"platformSettlement","数据来源供应链服务"),

    /**
     * 账户状态
     */
    ACCOUNT_STATE_ENABLE(500,"enable","启用"),
    ACCOUNT_STATE_DISABLED(501,"disabled","禁用"),
    ACCOUNT_STATE_CANCEL(502,"cancel","作废")
    ;
    /**
     * 编码
     */
    private Integer code;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

}
