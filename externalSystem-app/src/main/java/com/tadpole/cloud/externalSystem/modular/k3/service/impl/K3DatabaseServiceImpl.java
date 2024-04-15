package com.tadpole.cloud.externalSystem.modular.k3.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.modular.k3.service.K3DatabaseService;
import com.tadpole.cloud.externalSystem.modular.mabang.mapper.B2bPaymentDetailMapper;
import com.tadpole.cloud.externalSystem.modular.mabang.model.k3.K3BankAccountInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class K3DatabaseServiceImpl implements K3DatabaseService {
    @Resource
    private B2bPaymentDetailMapper b2bPaymentDetailMapper;

    @DataSource(name = "k3cloud")
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public K3BankAccountInfo queryK3BankAccountInfo(String bankNo,String orgCode) {
        return b2bPaymentDetailMapper.queryK3BankAccountInfo(bankNo,orgCode);
    }
}
