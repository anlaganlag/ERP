package com.tadpole.cloud.externalSystem.modular.oa.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.modular.oa.entity.Company;
import com.tadpole.cloud.externalSystem.modular.oa.mapper.CompanyMapper;
import com.tadpole.cloud.externalSystem.api.oa.model.result.CompanyResult;
import com.tadpole.cloud.externalSystem.modular.oa.service.ICompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author S20190109
 * @since 2023-05-11
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements ICompanyService {

    @Resource
    private CompanyMapper mapper;

    @DataSource(name="OA")
    @Override
    public List<CompanyResult> getCompany() {

        return this.baseMapper.getCompany();
    }
}
