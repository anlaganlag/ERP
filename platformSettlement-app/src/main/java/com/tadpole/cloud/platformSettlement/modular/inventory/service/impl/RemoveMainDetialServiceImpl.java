package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import com.tadpole.cloud.platformSettlement.api.inventory.entity.RemoveMainDetial;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.RemoveMainDetialMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IRemoveMainDetialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* <p>
* Amazon销毁移除子表 服务实现类
* </p>
*
* @author gal
* @since 2021-11-24
*/
@Service
public class RemoveMainDetialServiceImpl extends ServiceImpl<RemoveMainDetialMapper, RemoveMainDetial> implements IRemoveMainDetialService {

    @Autowired
    private RemoveMainDetialMapper mapper;

}
