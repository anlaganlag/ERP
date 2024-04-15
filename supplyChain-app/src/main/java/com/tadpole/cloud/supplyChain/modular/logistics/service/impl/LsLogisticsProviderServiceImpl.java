package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.LsLogisticsProvider;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.LsLogisticsProviderMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsLogisticsProviderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 物流商管理 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-11-07
 */
@Service
public class LsLogisticsProviderServiceImpl extends ServiceImpl<LsLogisticsProviderMapper, LsLogisticsProvider> implements ILsLogisticsProviderService {

    @Resource
    private LsLogisticsProviderMapper mapper;

    @Override
    @DataSource(name = "EBMS")
    public List<LsLogisticsProvider> logisticsProviderSelect() {
        return mapper.logisticsProviderSelect();
    }

    @Override
    @DataSource(name = "logistics")
    public List<LsLogisticsProvider> lpCodeSelect() {
        LambdaQueryWrapper<LsLogisticsProvider> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(LsLogisticsProvider :: getLpCode).groupBy(LsLogisticsProvider :: getLpCode);
        return this.list(queryWrapper);
    }

    @Override
    @DataSource(name = "logistics")
    public List<LsLogisticsProvider> lpNameSelect() {
        LambdaQueryWrapper<LsLogisticsProvider> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(LsLogisticsProvider :: getLpName).groupBy(LsLogisticsProvider :: getLpName);
        return this.list(queryWrapper);
    }

    @Override
    @DataSource(name = "logistics")
    public List<LsLogisticsProvider> lpSimpleNameSelect() {
        LambdaQueryWrapper<LsLogisticsProvider> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(LsLogisticsProvider :: getLpSimpleName).groupBy(LsLogisticsProvider :: getLpSimpleName);
        return this.list(queryWrapper);
    }

    @Override
    @DataSource(name = "logistics")
    public List<LsLogisticsProvider> lpUniSocCreCodeSelect() {
        LambdaQueryWrapper<LsLogisticsProvider> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(LsLogisticsProvider :: getLpUniSocCreCode).groupBy(LsLogisticsProvider :: getLpUniSocCreCode);
        return this.list(queryWrapper);
    }
}
