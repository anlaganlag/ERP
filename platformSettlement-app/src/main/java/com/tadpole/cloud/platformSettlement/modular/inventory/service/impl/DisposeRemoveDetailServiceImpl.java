package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.DisposeRemoveDetail;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.DisposeRemoveDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IDisposeRemoveDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* <p>
*  服务实现类
* </p>
*
* @author gal
* @since 2021-12-13
*/
@Service
public class DisposeRemoveDetailServiceImpl extends ServiceImpl<DisposeRemoveDetailMapper, DisposeRemoveDetail> implements IDisposeRemoveDetailService {

    @Autowired
    private DisposeRemoveDetailMapper mapper;

}
