package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3PurchaseInStockOrderItem;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.K3PurchaseInStockOrderItemMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IK3PurchaseInStockOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* <p>
    * K3采购入库单明细项 服务实现类
    * </p>
*
* @author S20190161
* @since 2023-05-17
*/
@Service
public class K3PurchaseInStockOrderItemServiceImpl extends ServiceImpl<K3PurchaseInStockOrderItemMapper, K3PurchaseInStockOrderItem> implements IK3PurchaseInStockOrderItemService {

    @Resource
    private K3PurchaseInStockOrderItemMapper mapper;



}
