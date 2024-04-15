package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import com.tadpole.cloud.platformSettlement.api.finance.entity.LxAmazonRefundOrders;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.LxAmazonRefundOrdersMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ILxAmazonRefundOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* <p>
*  服务实现类
* </p>
*
* @author cyt
* @since 2022-06-10
*/
@Service
public class LxAmazonRefundOrdersServiceImpl extends ServiceImpl<LxAmazonRefundOrdersMapper, LxAmazonRefundOrders> implements ILxAmazonRefundOrdersService {

    @Autowired
    private LxAmazonRefundOrdersMapper mapper;

}
