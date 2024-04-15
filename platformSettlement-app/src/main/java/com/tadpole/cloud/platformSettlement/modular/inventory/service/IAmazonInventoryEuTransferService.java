package com.tadpole.cloud.platformSettlement.modular.inventory.service;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.AmazonInventoryEuTransfer;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * EBMS转仓清单（欧洲站点调拨) 服务类
 * </p>
 *
 * @author S20190161
 * @since 2022-12-20
 */
public interface IAmazonInventoryEuTransferService extends IService<AmazonInventoryEuTransfer> {
    List<AmazonInventoryEuTransfer> getEbmsEuTransfer();

    void save( List<AmazonInventoryEuTransfer> list);
}
