package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import com.tadpole.cloud.platformSettlement.api.finance.entity.AmazonOrderDetail;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.AmazonOrderDetailMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IAmazonOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
* <p>
* 所有订单明细表 服务实现类
* </p>
*
* @author gal
* @since 2022-05-06
*/
@Service
public class AmazonOrderDetailServiceImpl extends ServiceImpl<AmazonOrderDetailMapper, AmazonOrderDetail> implements IAmazonOrderDetailService {

    @Resource
    private AmazonOrderDetailMapper mapper;

}
