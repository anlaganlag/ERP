package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import com.tadpole.cloud.externalSystem.modular.mabang.entity.SaleReturnOrderItem;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.SaleReturnOrderItemMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.service.ISaleReturnOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* <p>
    * 销售退货单明细项 服务实现类
    * </p>
*
* @author lsy
* @since 2022-08-24
*/
@Service
public class SaleReturnOrderItemServiceImpl extends ServiceImpl<SaleReturnOrderItemMapper, SaleReturnOrderItem> implements ISaleReturnOrderItemService {

    @Resource
    private SaleReturnOrderItemMapper mapper;

}
