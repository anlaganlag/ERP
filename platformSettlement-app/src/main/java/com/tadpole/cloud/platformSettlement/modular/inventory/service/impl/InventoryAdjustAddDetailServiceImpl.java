package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.InventoryAdjustAddDetail;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.InventoryAdjustAddDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IInventoryAdjustAddDetailService;
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
public class InventoryAdjustAddDetailServiceImpl extends ServiceImpl<InventoryAdjustAddDetailMapper, InventoryAdjustAddDetail> implements IInventoryAdjustAddDetailService {

    @Autowired
    private InventoryAdjustAddDetailMapper mapper;

}
