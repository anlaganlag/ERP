package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.SpecialApplyInfoHistory;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.SpecialApplyInfoHistoryMapper;
import com.tadpole.cloud.operationManagement.modular.stock.service.ISpecialApplyInfoHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* <p>
    * 特殊备货申请信息历史记录 服务实现类
    * </p>
*
* @author lsy
* @since 2022-06-24
*/
@Service
public class SpecialApplyInfoHistoryServiceImpl extends ServiceImpl<SpecialApplyInfoHistoryMapper, SpecialApplyInfoHistory> implements ISpecialApplyInfoHistoryService {

    @Resource
    private SpecialApplyInfoHistoryMapper mapper;

}
