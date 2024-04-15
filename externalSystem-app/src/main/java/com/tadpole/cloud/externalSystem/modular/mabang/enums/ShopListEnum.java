package com.tadpole.cloud.externalSystem.modular.mabang.enums;

import lombok.Getter;


@Getter
public enum ShopListEnum {

    SHOP_ON("1", "店铺状态_启用", "status"),
    SHOP_OFF("2", "店铺状态_停用", "status"),
    DEL("1", "已删除", "is_delete"),
    NO_DEL("0", "未删除", "is_delete"),

    SYS_SYN("0", "系统同步", "sync_type"),
    MAN_SYN("1", "手动同步", "sync_type"),

    SYN_SUCCESS("1", "同步失败", "syn_status"),
    SYN_FAIL("0", "同步成功", "syn_status"),



    sea_warehouse("2", "海外仓", "sea_warehouse"),
    split_empty("3", "拆分空单", "split_empty"),
    sea_split("4", "海外仓且拆分空单", "sea_split"),
    limit_platform("5","订单限定平台","limit_platform"),

    is_no_aim_warehouse("6","非雁田定制仓","is_no_aim_warehouse"),
    is_no_aim_plat("7","非目标平台","is_no_aim_plat"),
    is_k3_purchase_order("8","马帮销售给K3系统，然后k3系统进行采购入库处理","is_k3_purchase_order"),
    is_k3_purchase_order_no_item("9","销售订单明细项item的status没有<已发货>的状态数据，不需要做K3采购入库处理","is_k3_purchase_order_no_item"),
    is_k3_purchase_order_exclude("10","在马帮做的测试订单，不需要做K3采购入库处理","is_k3_purchase_order_no_item"),


    Mercadolibre("Mercadolibre","Mercadolibre","Mercadolibre"),
    eBay("eBay","eBay","eBay"),
    LAZADA("LAZADA","LAZADA","LAZADA"),
    Shopee("Shopee","Shopee","Shopee"),
    速卖通("速卖通","速卖通","速卖通"),



    RETURN_FINISHED_STATUS("4","已完成","return_finished_status"),
    RETURN_REJECTED_STATUS("5","已作废","return_rejected_status"),


    SYNC_SHIPPED_ORDER("YFHDD","YFHDD","sync_shopped_order"),
    SYNC_RETURN_ORDER("XSTHDD","XSTHDD","sync_return_order");


    /**
     * 审核节点编码
     */
    private String code;

    /**
     * 审核节点名称
     */
    private String name;

    /**
     * 审核节点表字段
     */
    private String columnName;

    ShopListEnum(String code, String name, String columnName) {
        this.code = code;
        this.name = name;
        this.columnName = columnName;
    }

    public static ShopListEnum getEnum(String value) {
        ShopListEnum resultEnum = null;
        ShopListEnum[] enumAry = ShopListEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getCode().equals(value)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }
}
