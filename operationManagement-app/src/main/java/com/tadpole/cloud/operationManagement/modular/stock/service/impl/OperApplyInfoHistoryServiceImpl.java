package com.tadpole.cloud.operationManagement.modular.stock.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.operationManagement.modular.stock.entity.OperApplyInfoHistory;
import com.tadpole.cloud.operationManagement.modular.stock.mapper.OperApplyInfoHistoryMapper;
import com.tadpole.cloud.operationManagement.modular.stock.service.IOperApplyInfoHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* <p>
    * 备货申请信息历史记录 服务实现类
    * </p>
*
* @author lsy
* @since 2022-06-14
*/
@Service
public class OperApplyInfoHistoryServiceImpl extends ServiceImpl<OperApplyInfoHistoryMapper, OperApplyInfoHistory> implements IOperApplyInfoHistoryService {

    @Resource
    private OperApplyInfoHistoryMapper mapper;

}
