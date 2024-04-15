package com.tadpole.cloud.platformSettlement.modular.inventory.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.DisposeRemoveTrack;
import com.tadpole.cloud.platformSettlement.modular.inventory.mapper.DisposeRemoveTrackMapper;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IDisposeRemoveTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 销毁移除跟踪表 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-02-28
 */
@Service
public class DisposeRemoveTrackServiceImpl extends ServiceImpl<DisposeRemoveTrackMapper, DisposeRemoveTrack> implements IDisposeRemoveTrackService {

    @Autowired
    private DisposeRemoveTrackMapper mapper;
}
