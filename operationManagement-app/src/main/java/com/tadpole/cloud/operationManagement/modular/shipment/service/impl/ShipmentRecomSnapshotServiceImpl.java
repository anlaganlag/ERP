package com.tadpole.cloud.operationManagement.modular.shipment.service.impl;

import com.tadpole.cloud.operationManagement.modular.shipment.entity.ShipmentRecomSnapshot;
import com.tadpole.cloud.operationManagement.modular.shipment.mapper.ShipmentRecomSnapshotMapper;
import com.tadpole.cloud.operationManagement.modular.shipment.service.IShipmentRecomSnapshotService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* <p>
    * 发货推荐数据快照 服务实现类
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
@Service
public class ShipmentRecomSnapshotServiceImpl extends ServiceImpl<ShipmentRecomSnapshotMapper, ShipmentRecomSnapshot> implements IShipmentRecomSnapshotService {

    @Resource
    private ShipmentRecomSnapshotMapper mapper;

}
