package com.tadpole.cloud.externalSystem.modular.lingxing.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.modular.lingxing.mapper.AdvertisingGroupMapper;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.advertising.AdvertisingGroup;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.AdvertisingGroupService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class AdvertisingGroupServiceImpl extends ServiceImpl<AdvertisingGroupMapper, AdvertisingGroup> implements AdvertisingGroupService {
    @DataSource(name = "BIDW")
    @Override
    public boolean saveBatch(Collection<AdvertisingGroup> entityList, int batchSize) {
        return false;
    }
    @DataSource(name = "BIDW")
    @Override
    public boolean saveOrUpdateBatch(Collection<AdvertisingGroup> entityList, int batchSize) {
        return false;
    }
    @DataSource(name = "BIDW")
    @Override
    public boolean updateBatchById(Collection<AdvertisingGroup> entityList, int batchSize) {
        return false;
    }

    @DataSource(name = "BIDW")
    @Override
    public boolean saveOrUpdate(AdvertisingGroup entity) {
        return  this.baseMapper.insert(entity)>0;

    }
    @DataSource(name = "BIDW")
    @Override
    public AdvertisingGroup getOne(Wrapper<AdvertisingGroup> queryWrapper, boolean throwEx) {
        return null;
    }
    @DataSource(name = "BIDW")
    @Override
    public Map<String, Object> getMap(Wrapper<AdvertisingGroup> queryWrapper) {
        return null;
    }
    @DataSource(name = "BIDW")
    @Override
    public <V> V getObj(Wrapper<AdvertisingGroup> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @DataSource(name = "BIDW")
    @Override
    public Class<AdvertisingGroup> getEntityClass() {
        return null;
    }
}
