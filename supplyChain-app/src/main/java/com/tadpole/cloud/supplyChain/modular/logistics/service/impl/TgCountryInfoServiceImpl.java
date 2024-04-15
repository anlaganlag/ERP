package com.tadpole.cloud.supplyChain.modular.logistics.service.impl;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tadpole.cloud.supplyChain.api.logistics.entity.TgCountryInfo;
import com.tadpole.cloud.supplyChain.modular.logistics.mapper.TgCountryInfoMapper;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCountryInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 国家地区信息 服务实现类
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@Service
public class TgCountryInfoServiceImpl extends ServiceImpl<TgCountryInfoMapper, TgCountryInfo> implements ITgCountryInfoService {

    @Resource
    private TgCountryInfoMapper mapper;

    @Override
    @DataSource(name = "logistics")
    public ResponseData countrySelect() {
        return ResponseData.success(this.list());
    }
}
