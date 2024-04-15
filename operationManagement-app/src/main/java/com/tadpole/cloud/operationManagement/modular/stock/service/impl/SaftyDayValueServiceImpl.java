package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SaftyDayValue;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.SaftyDayValueMapper;
import com.tadpole.cloud.operationManagement.modular.stock.service.ISaftyDayValueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* <p>
    * 安全天数参数值 服务实现类
    * </p>
*
* @author cyt
* @since 2022-07-20
*/
@Service
public class SaftyDayValueServiceImpl extends ServiceImpl<SaftyDayValueMapper, SaftyDayValue> implements ISaftyDayValueService {

    @Resource
    private SaftyDayValueMapper mapper;

}
