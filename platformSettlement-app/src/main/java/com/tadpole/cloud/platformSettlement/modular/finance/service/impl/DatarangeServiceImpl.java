package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import com.tadpole.cloud.platformSettlement.api.finance.entity.Datarange;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.DatarangeMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IDatarangeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* <p>
* datarange主数据 服务实现类
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Service
public class DatarangeServiceImpl extends ServiceImpl<DatarangeMapper, Datarange> implements IDatarangeService {

    @Autowired
    private DatarangeMapper mapper;

}
