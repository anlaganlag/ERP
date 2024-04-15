package com.tadpole.cloud.externalSystem.modular.oa.service.impl;

import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.externalSystem.api.oa.model.result.CompanyResult;
import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmdepartmentResult;
import com.tadpole.cloud.externalSystem.modular.oa.entity.Hrmdepartment;
import com.tadpole.cloud.externalSystem.modular.oa.mapper.HrmdepartmentMapper;
import com.tadpole.cloud.externalSystem.modular.oa.service.IHrmdepartmentService;
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
 * @since 2023-05-11
 */
@Service
public class HrmdepartmentServiceImpl extends ServiceImpl<HrmdepartmentMapper, Hrmdepartment> implements IHrmdepartmentService {

    @Resource
    private HrmdepartmentMapper mapper;

    @DataSource(name="OA")
    @Override
    public List<HrmdepartmentResult> getDepartment() {

        return this.baseMapper.getDepartment();
    }

}
