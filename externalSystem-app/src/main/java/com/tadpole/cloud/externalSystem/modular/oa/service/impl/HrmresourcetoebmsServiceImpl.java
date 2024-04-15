package com.tadpole.cloud.externalSystem.modular.oa.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmresourcetoebmsResult;
import com.tadpole.cloud.externalSystem.modular.oa.entity.Hrmresourcetoebms;
import com.tadpole.cloud.externalSystem.modular.oa.mapper.HrmresourcetoebmsMapper;
import com.tadpole.cloud.externalSystem.modular.oa.service.IHrmresourcetoebmsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author S20190109
 * @since 2023-05-12
 */
@Service
public class HrmresourcetoebmsServiceImpl extends ServiceImpl<HrmresourcetoebmsMapper, Hrmresourcetoebms> implements IHrmresourcetoebmsService {

    @Resource
    private HrmresourcetoebmsMapper mapper;

    @DataSource(name="OA")
    @Override
    public List<HrmresourcetoebmsResult> getHrmResource() {
        return this.baseMapper.getHrmResource();
    }
}
