package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangOrderItem;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.MabangOrderItemMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* <p>
    * 马帮订单具体商品项item 服务实现类
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
@Service
public class MabangOrderItemServiceImpl extends ServiceImpl<MabangOrderItemMapper, MabangOrderItem> implements IMabangOrderItemService {

    @Resource
    private MabangOrderItemMapper mapper;

}
