package com.tadpole.cloud.operationManagement.modular.shipment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.AmzInvSkuDayAgg;
import com.tadpole.cloud.operationManagement.modular.shipment.mapper.AmzInvSkuDayAggMapper;
import com.tadpole.cloud.operationManagement.modular.shipment.service.IAmzInvSkuDayAggService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* <p>
    * 店铺库存销量明细表 服务实现类
    * </p>
*
* @author lsy
* @since 2023-02-17
*/
@Service
public class AmzInvSkuDayAggServiceImpl extends ServiceImpl<AmzInvSkuDayAggMapper, AmzInvSkuDayAgg> implements IAmzInvSkuDayAggService {

    @Resource
    private AmzInvSkuDayAggMapper mapper;

}
