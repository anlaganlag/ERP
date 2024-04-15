package com.tadpole.cloud.externalSystem.modular.oa.mapper;

import com.tadpole.cloud.externalSystem.modular.oa.entity.Company;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tadpole.cloud.externalSystem.api.oa.model.result.CompanyResult;

import java.util.List;

/**
 * <p>
 *  Mapper接口
 * </p>
 *
 * @author S20190109
 * @since 2023-05-11
 */
public interface CompanyMapper extends BaseMapper<Company> {

    List<CompanyResult> getCompany();
}
