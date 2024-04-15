package com.tadpole.cloud.platformSettlement.modular.finance.service.impl;

import com.tadpole.cloud.platformSettlement.api.finance.entity.SettlementAbnormal;
import com.tadpole.cloud.platformSettlement.modular.finance.mapper.SettlementAbnormalMapper;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISettlementAbnormalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  AZ结算异常服务实现类
 * </p>
 *
 * @author ty
 * @since 2022-07-28
 */
@Service
public class SettlementAbnormalServiceImpl extends ServiceImpl<SettlementAbnormalMapper, SettlementAbnormal> implements ISettlementAbnormalService {

    @Autowired
    private SettlementAbnormalMapper mapper;

}
