package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.OverseasOutWarehouseDetail;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.OverseasOutWarehouseDetailMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasOutWarehouseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 海外仓出库管理详情服务实现类
 * </p>
 *
 * @author cyt
 * @since 2022-07-20
 */
@Service
public class OverseasOutWarehouseDetailServiceImpl extends ServiceImpl<OverseasOutWarehouseDetailMapper, OverseasOutWarehouseDetail> implements IOverseasOutWarehouseDetailService {

    @Autowired
    private OverseasOutWarehouseDetailMapper mapper;

}
