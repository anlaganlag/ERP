package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3CrossTransferItem;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.K3CrossTransferItemMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IK3CrossTransferItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* <p>
    * K3跨组织直接调拨单明细项 服务实现类
    * </p>
*
* @author lsy
* @since 2022-06-28
*/
@Service
public class K3CrossTransferItemServiceImpl extends ServiceImpl<K3CrossTransferItemMapper, K3CrossTransferItem> implements IK3CrossTransferItemService {

    @Resource
    private K3CrossTransferItemMapper mapper;

}
