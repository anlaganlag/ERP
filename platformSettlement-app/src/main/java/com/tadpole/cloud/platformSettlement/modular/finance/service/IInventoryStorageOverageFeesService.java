package com.tadpole.cloud.platformSettlement.modular.finance.service;

import cn.stylefeng.guns.cloud.model.page.PageTotalResult;
import com.tadpole.cloud.platformSettlement.api.finance.entity.InventoryStorageOverageFees;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.InventoryStorageOverageFeesResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.InventoryStorageOverageFeesTotal;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.InventoryStorageOverageFeesParam;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * FBA存货存储超额费用报告 服务类
 * </p>
 *
 * @author S20190161
 * @since 2022-10-12
 */
public interface IInventoryStorageOverageFeesService extends IService<InventoryStorageOverageFees> {

    PageTotalResult<InventoryStorageOverageFeesResult, InventoryStorageOverageFeesTotal> findPageBySpec(InventoryStorageOverageFeesParam param);

    int deleteBatch(InventoryStorageOverageFeesParam param);

    int updateBatch(InventoryStorageOverageFeesParam param);

    List<InventoryStorageOverageFees> export(InventoryStorageOverageFeesParam param);

    void afreshStorageFee();
}
