package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.EndingInventoryDetail;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.EndingInventoryDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IEndingInventoryDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* <p>
* 期末库存列表 服务实现类
* </p>
*
* @author gal
* @since 2021-12-13
*/
@Service
public class EndingInventoryDetailServiceImpl extends ServiceImpl<EndingInventoryDetailMapper, EndingInventoryDetail> implements IEndingInventoryDetailService {

    @Autowired
    private EndingInventoryDetailMapper mapper;

}
