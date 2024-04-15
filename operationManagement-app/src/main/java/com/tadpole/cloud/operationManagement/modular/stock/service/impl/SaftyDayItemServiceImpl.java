package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SaftyDayItem;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.SaftyDayItemMapper;
import com.tadpole.cloud.operationManagement.modular.stock.service.ISaftyDayItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* <p>
    * 安全天数明细项 服务实现类
    * </p>
*
* @author cyt
* @since 2022-07-20
*/
@Service
public class SaftyDayItemServiceImpl extends ServiceImpl<SaftyDayItemMapper, SaftyDayItem> implements ISaftyDayItemService {

    @Resource
    private SaftyDayItemMapper mapper;

}
