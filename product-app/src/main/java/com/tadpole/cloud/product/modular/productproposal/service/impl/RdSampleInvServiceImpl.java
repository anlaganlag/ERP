package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.product.api.productproposal.entity.RdSampleInv;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdSampleInvMapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSampleInvService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 提案-开发样盘点 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2024-04-10
 */
@Service
public class RdSampleInvServiceImpl extends ServiceImpl<RdSampleInvMapper, RdSampleInv> implements IRdSampleInvService {

    @Resource
    private RdSampleInvMapper mapper;

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<RdSampleInv> listSampleInv() {
        return this.mapper.selectList(null);
    }
}
