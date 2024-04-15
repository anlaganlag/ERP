package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import com.tadpole.cloud.platformSettlement.api.finance.entity.DatarangeDetailTemp;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.DatarangeDetailTempMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IDatarangeDetailTempService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* <p>
* datarange明细数据中间表 服务实现类
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Service
public class DatarangeDetailTempServiceImpl extends ServiceImpl<DatarangeDetailTempMapper, DatarangeDetailTemp> implements IDatarangeDetailTempService {

    @Autowired
    private DatarangeDetailTempMapper mapper;

}
