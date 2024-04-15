package com.tadpole.cloud.externalSystem.modular.saihu.constants;

/**
 * @author: ty
 * @description: 赛狐URL常量类
 * @date: 2024/1/30
 */
public class SaihuUrlConstants {

    /**
     * 授权：
     * 获取access-token
     */
    public static final String ACCESS_TOKEN = "/api/oauth/v2/token.json";

    /**
     * 系统：
     * 获取店铺列表
     */
    public static final String SHOP = "/api/shop/pageList.json";

    /**
     * 系统：
     * 获取自定义店铺列表
     */
    public static final String CUSTOM = "/api/shop/custom/list.json";

    /**
     * 销售：
     * 在线产品
     */
    public static final String PRODUCT = "/api/order/api/product/pageList.json";


    public static final String ACCOUNT_LIST = "/api/account/getSubUserPage.json";

    public static final String ACCOUNT_STATUS = "/api/account/subStop.json";

    public static final String ACCOUNT_CREATE = "/api/account/saveSubUser.json";

}
