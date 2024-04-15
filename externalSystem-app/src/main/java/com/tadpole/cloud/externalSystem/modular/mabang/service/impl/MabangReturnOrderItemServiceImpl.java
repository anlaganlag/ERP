package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangReturnOrderItem;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.MabangReturnOrderItemMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangReturnOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* <p>
    * 马帮退货单明细项列表 服务实现类
    * </p>
*
* @author lsy
* @since 2022-08-24
*/
@Service
public class MabangReturnOrderItemServiceImpl extends ServiceImpl<MabangReturnOrderItemMapper, MabangReturnOrderItem> implements IMabangReturnOrderItemService {

    @Resource
    private MabangReturnOrderItemMapper mapper;

}
