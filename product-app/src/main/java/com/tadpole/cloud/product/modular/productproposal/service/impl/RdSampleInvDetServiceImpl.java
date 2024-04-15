package com.tadpole.cloud.product.modular.productproposal.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tadpole.cloud.product.api.productproposal.entity.RdSampleInvDet;
import com.tadpole.cloud.product.api.productproposal.model.params.RdSampleInvDetParam;
import com.tadpole.cloud.product.modular.productproposal.mapper.RdSampleInvDetMapper;
import com.tadpole.cloud.product.modular.productproposal.service.IRdSampleInvDetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 提案-开发样盘点明细 服务实现类
 * </p>
 *
 * @author S20190096
 * @since 2024-04-10
 */
@Service
public class RdSampleInvDetServiceImpl extends ServiceImpl<RdSampleInvDetMapper, RdSampleInvDet> implements IRdSampleInvDetService {

    @Resource
    private RdSampleInvDetMapper mapper;

    @DataSource(name = "product")
    @Override
    @Transactional
    public List<RdSampleInvDet> listSampleInvDet(RdSampleInvDetParam param) {
        QueryWrapper<RdSampleInvDet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SYS_INV_CODE",param.getSysInvCode());
        return this.mapper.selectList(queryWrapper);
    }
}
