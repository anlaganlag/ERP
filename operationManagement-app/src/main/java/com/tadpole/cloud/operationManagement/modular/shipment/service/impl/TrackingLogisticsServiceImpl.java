package com.tadpole.cloud.operationManagement.modular.shipment.service.impl;

import com.tadpole.cloud.operationManagement.modular.shipment.entity.TrackingLogistics;
import com.tadpole.cloud.operationManagement.modular.shipment.mapper.TrackingLogisticsMapper;
import com.tadpole.cloud.operationManagement.modular.shipment.service.ITrackingLogisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* <p>
    * 追踪明细项-物流信息 服务实现类
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
@Service
public class TrackingLogisticsServiceImpl extends ServiceImpl<TrackingLogisticsMapper, TrackingLogistics> implements ITrackingLogisticsService {

    @Resource
    private TrackingLogisticsMapper mapper;

}
