package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.AmazonInventoryList;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.AmazonInventoryListOrg;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 庫存列表3.0 服务类
 * </p>
 *
 * @author S20190161
 * @since 2022-12-20
 */
public interface IAmazonInventoryListOrgService extends IService<AmazonInventoryListOrg> {

    List<AmazonInventoryListOrg> getListOrg(AmazonInventoryList param);
}
