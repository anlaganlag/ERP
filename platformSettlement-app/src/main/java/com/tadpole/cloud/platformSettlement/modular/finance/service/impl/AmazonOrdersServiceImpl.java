package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import com.tadpole.cloud.platformSettlement.api.finance.entity.AmazonOrders;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.AmazonOrdersMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IAmazonOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
* <p>
* 销量汇总订单数据 服务实现类
* </p>
*
* @author gal
* @since 2022-05-06
*/
@Service
public class AmazonOrdersServiceImpl extends ServiceImpl<AmazonOrdersMapper, AmazonOrders> implements IAmazonOrdersService {

    @Resource
    private AmazonOrdersMapper mapper;

}
