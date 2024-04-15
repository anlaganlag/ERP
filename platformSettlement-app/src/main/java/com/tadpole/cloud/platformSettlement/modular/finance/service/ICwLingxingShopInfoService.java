package com.tadpole.cloud.platformSettlement.modular.finance.service;

import com.tadpole.cloud.platformSettlement.api.finance.entity.CwLingxingShopInfo;
import com.tadpole.cloud.platformSettlement.api.finance.entity.LxShopSynRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 *  领星亚马逊店铺信息服务类
 * </p>
 *
 * @author ty
 * @since 2022-04-29
 */
public interface ICwLingxingShopInfoService extends IService<CwLingxingShopInfo> {

    /**
     * 同步领星亚马逊店铺信息
     * @return
     */
    void syncSellerLists() throws Exception;

    /**
     * 根据同步信息类型获取位同步的店铺信息
     * @param lxShopSynRecord
     * @return
     */
    List<CwLingxingShopInfo> getLxShopInfoBySynType(LxShopSynRecord lxShopSynRecord);
}
