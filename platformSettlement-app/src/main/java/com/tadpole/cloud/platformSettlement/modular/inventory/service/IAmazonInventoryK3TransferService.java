package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.AmazonInventoryK3Transfer;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * K3跨组织直接调拨单 服务类
 * </p>
 *
 * @author S20190161
 * @since 2022-12-20
 */
public interface IAmazonInventoryK3TransferService extends IService<AmazonInventoryK3Transfer> {
    List<AmazonInventoryK3Transfer> getErpEuTransfer();

    void save( List<AmazonInventoryK3Transfer> list);
}
