package com.tadpole.cloud.operationManagement.modular.shipment.service.impl;

import com.tadpole.cloud.operationManagement.modular.shipment.entity.TrackingShipping;
import com.tadpole.cloud.operationManagement.modular.shipment.mapper.TrackingShippingMapper;
import com.tadpole.cloud.operationManagement.modular.shipment.service.ITrackingShippingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* <p>
    * 追踪明细项-出货清单 服务实现类
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
@Service
public class TrackingShippingServiceImpl extends ServiceImpl<TrackingShippingMapper, TrackingShipping> implements ITrackingShippingService {

    @Resource
    private TrackingShippingMapper mapper;

}
