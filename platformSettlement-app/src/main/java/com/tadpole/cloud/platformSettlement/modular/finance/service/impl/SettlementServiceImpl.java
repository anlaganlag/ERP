package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import com.tadpole.cloud.platformSettlement.api.finance.entity.Settlement;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.SettlementMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISettlementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* <p>
* selltement主数据 服务实现类
* </p>
*
* @author gal
* @since 2021-10-25
*/
@Service
public class SettlementServiceImpl extends ServiceImpl<SettlementMapper, Settlement> implements ISettlementService {

    @Autowired
    private SettlementMapper mapper;

}
