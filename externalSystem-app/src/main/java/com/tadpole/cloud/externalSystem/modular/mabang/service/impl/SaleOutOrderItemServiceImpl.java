package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import com.tadpole.cloud.externalSystem.modular.mabang.entity.SaleOutOrderItem;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.SaleOutOrderItemMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.service.ISaleOutOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* <p>
    * 销售出库单明细项 服务实现类
    * </p>
*
* @author lsy
* @since 2022-08-24
*/
@Service
public class SaleOutOrderItemServiceImpl extends ServiceImpl<SaleOutOrderItemMapper, SaleOutOrderItem> implements ISaleOutOrderItemService {

    @Resource
    private SaleOutOrderItemMapper mapper;

}
