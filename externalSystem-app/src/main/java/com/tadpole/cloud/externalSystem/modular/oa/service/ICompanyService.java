package com.tadpole.cloud.externalSystem.modular.oa.service;

import com.tadpole.cloud.externalSystem.modular.oa.entity.Company;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tadpole.cloud.externalSystem.api.oa.model.result.CompanyResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author S20190109
 * @since 2023-05-11
 */
public interface ICompanyService extends IService<Company> {

    List<CompanyResult> getCompany();
}
