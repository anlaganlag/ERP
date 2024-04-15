package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangProductStock;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.MabangProductStockMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangProductStockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* <p>
    * 马帮商品库存信息 服务实现类
    * </p>
*
* @author lsy
* @since 2023-05-23
*/
@Service
public class MabangProductStockServiceImpl extends ServiceImpl<MabangProductStockMapper, MabangProductStock> implements IMabangProductStockService {

    @Resource
    private MabangProductStockMapper mapper;

}
