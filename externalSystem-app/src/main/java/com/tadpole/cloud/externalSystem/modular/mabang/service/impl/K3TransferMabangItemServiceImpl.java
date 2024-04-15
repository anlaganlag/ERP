package com.tadpole.cloud.externalSystem.modular.mabang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3TransferMabangItem;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.K3TransferMabangItemMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IK3TransferMabangItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* <p>
    * K3调拨单同步到马帮记录明细项 服务实现类
    * </p>
*
* @author lsy
* @since 2022-06-15
*/
@Service
public class K3TransferMabangItemServiceImpl extends ServiceImpl<K3TransferMabangItemMapper, K3TransferMabangItem> implements IK3TransferMabangItemService {

    @Resource
    private K3TransferMabangItemMapper mapper;

}
