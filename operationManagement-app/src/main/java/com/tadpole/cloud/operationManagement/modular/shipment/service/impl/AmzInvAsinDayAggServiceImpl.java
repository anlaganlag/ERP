package com.tadpole.cloud.operationManagement.modular.shipment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.shipment.entity.AmzInvAsinDayAgg;
import com.tadpole.cloud.operationManagement.modular.shipment.mapper.AmzInvAsinDayAggMapper;
import com.tadpole.cloud.operationManagement.modular.shipment.service.IAmzInvAsinDayAggService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* <p>
    * 店铺销量库存表 服务实现类
    * </p>
*
* @author lsy
* @since 2023-02-17
*/
@Service
public class AmzInvAsinDayAggServiceImpl extends ServiceImpl<AmzInvAsinDayAggMapper, AmzInvAsinDayAgg> implements IAmzInvAsinDayAggService {

    @Resource
    private AmzInvAsinDayAggMapper mapper;

}
