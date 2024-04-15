package com.tadpole.cloud.platformSettlement.modular.finance.mapper;

import com.tadpole.cloud.platformSettlement.api.finance.entity.InventoryStorageOverageFees;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* <p>
* FBA存货存储超额费用报告 Mapper 接口
* </p>
*
* @author S20190161
* @since 2022-10-12
*/
public interface InventoryStorageOverageFeesMapper extends BaseMapper<InventoryStorageOverageFees> {
    void afreshStorageFee();
}
