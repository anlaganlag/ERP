package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.SaleOutOrderItemK3;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.SaleOutOrderItemK3Mapper;
import com.tadpole.cloud.externalSystem.modular.mabang.service.ISaleOutOrderItemK3Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* <p>
    * 根据K3仓库可用数量自动产生-销售出库单明细项 服务实现类
    * </p>
*
* @author lsy
* @since 2023-04-07
*/
@Service
public class SaleOutOrderItemK3ServiceImpl extends ServiceImpl<SaleOutOrderItemK3Mapper, SaleOutOrderItemK3> implements ISaleOutOrderItemK3Service {

    @Resource
    private SaleOutOrderItemK3Mapper mapper;

}
