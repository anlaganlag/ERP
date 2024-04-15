package com.tadpole.cloud.externalSystem.modular.saihu.service;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.externalSystem.api.saihu.entity.SaihuShop;

/**
 * <p>
 * 赛狐店铺列表 服务类
 * </p>
 *
 * @author ty
 * @since 2024-02-22
 */
public interface ISaihuShopService extends IService<SaihuShop> {

    /**
     * 获取店铺列表
     * @return
     * @throws Exception
     */
    ResponseData shopList();

    /**
     * 获取自定义店铺列表
     * @return
     * @throws Exception
     */
    ResponseData customList() throws Exception;

}
