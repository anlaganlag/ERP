package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgLxListingInfo;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgLxListingInfoMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgLxListingInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author: ty
 * @description: 领星亚马逊Listing信息
 * @date: 2023/6/6
 */
@Slf4j
@Service
public class TgLxListingInfoServiceImpl extends ServiceImpl<TgLxListingInfoMapper, TgLxListingInfo> implements ITgLxListingInfoService {

    @Override
    @DataSource(name = "logistics")
    public boolean remove(Wrapper<TgLxListingInfo> queryWrapper) {
        return SqlHelper.retBool(this.getBaseMapper().delete(queryWrapper));
    }

    @Override
    @DataSource(name = "logistics")
    public boolean save(TgLxListingInfo entity) {
        return SqlHelper.retBool(this.getBaseMapper().insert(entity));
    }

    @Override
    @DataSource(name = "logistics")
    public boolean saveBatch(Collection<TgLxListingInfo> entityList) {
        return this.saveBatch(entityList, 1000);
    }
}
