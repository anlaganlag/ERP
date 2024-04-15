package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasInWarehouseDetail;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.OverseasInWarehouseDetailMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasInWarehouseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  海外仓入库管理详情服务实现类
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */
@Service
public class OverseasInWarehouseDetailServiceImpl extends ServiceImpl<OverseasInWarehouseDetailMapper, OverseasInWarehouseDetail> implements IOverseasInWarehouseDetailService {

    @Autowired
    private OverseasInWarehouseDetailMapper mapper;

}
