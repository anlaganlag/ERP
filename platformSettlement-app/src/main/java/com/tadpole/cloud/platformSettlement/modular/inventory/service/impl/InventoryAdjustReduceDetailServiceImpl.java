package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.InventoryAdjustReduceDetail;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.InventoryAdjustReduceDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IInventoryAdjustReduceDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* <p>
*  服务实现类
* </p>
*
* @author gal
* @since 2021-12-10
*/
@Service
public class InventoryAdjustReduceDetailServiceImpl extends ServiceImpl<InventoryAdjustReduceDetailMapper, InventoryAdjustReduceDetail> implements IInventoryAdjustReduceDetailService {

    @Autowired
    private InventoryAdjustReduceDetailMapper mapper;

}
