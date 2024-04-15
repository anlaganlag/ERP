package com.tadpole.cloud.externalSystem.modular.k3.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.modular.k3.mapper.TranferApplyMapper;
import com.tadpole.cloud.externalSystem.modular.k3.service.TranferApplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TranferApplyServiceImpl implements TranferApplyService {
    @Resource
    private TranferApplyMapper tranferApplyMapper;

    @DataSource(name = "k3cloud")
    @Override
    public String getBscId(String orgName, String sku) {
        return tranferApplyMapper.getBscId(orgName, sku);
    }
}
