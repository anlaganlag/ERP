package com.tadpole.cloud.product.modular.productproposal.service.impl;

import com.tadpole.cloud.product.api.productproposal.entity.RdSqSetting;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdSqSettingMapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSqSettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 提案-设置-拿样次数设置 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2023-11-17
 */
@Service
public class RdSqSettingServiceImpl extends ServiceImpl<RdSqSettingMapper, RdSqSetting> implements IRdSqSettingService {

    @Resource
    private RdSqSettingMapper mapper;

}
