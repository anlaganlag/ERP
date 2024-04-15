package com.tadpole.cloud.supplyChain.modular.logistics.enums;

import lombok.Getter;

/**
 * @author: ty
 * @description: 海外仓K3组织调拨类型
 * @date: 2022/8/3
 */
@Getter
public enum TransferBusinessTypeEnum {

    AMAZON_TO_OVERSEAS("亚马逊仓发海外仓", "InnerOrgTransfer"),//组织内调拨
    AMAZON_TO_OVERSEAS_OVER("亚马逊仓发海外仓", "OverOrgTransfer"),//跨组织调拨
    OVERSEAS_TO_AMAZON("海外仓发亚马逊仓", "InnerOrgTransfer"),//组织内调拨
    DIFF_SITE_CHANGE("跨站点换标", "OverOrgTransfer"),//跨组织调拨
    SAME_SITE_CHANGE("同站点换标", "InnerOrgTransfer"),//组织内调拨
    OVERSEAS_TO_WALMART("海外仓发沃尔玛仓", "OverOrgTransfer");//跨组织调拨

    /**
     * 海外仓K3组织调拨业务名称
     */
    private String name;

    /**
     * 海外仓K3组织调拨业务类型
     */
    private String type;

    TransferBusinessTypeEnum(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
