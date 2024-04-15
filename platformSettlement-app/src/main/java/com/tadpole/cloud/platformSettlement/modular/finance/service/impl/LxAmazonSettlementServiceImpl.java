package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import com.tadpole.cloud.platformSettlement.api.finance.entity.LxAmazonSettlement;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.LxAmazonSettlementMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ILxAmazonSettlementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* <p>
* 领星Settlement源文件汇总 服务实现类
* </p>
*
* @author cyt
* @since 2022-05-13
*/
@Service
public class LxAmazonSettlementServiceImpl extends ServiceImpl<LxAmazonSettlementMapper, LxAmazonSettlement> implements ILxAmazonSettlementService {

    @Autowired
    private LxAmazonSettlementMapper mapper;

}
