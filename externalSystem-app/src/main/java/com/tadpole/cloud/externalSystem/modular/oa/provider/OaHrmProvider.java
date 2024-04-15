package com.tadpole.cloud.externalSystem.modular.oa.provider;

import com.tadpole.cloud.externalSystem.api.oa.OaHrmApi;
import com.tadpole.cloud.externalSystem.api.oa.model.result.CompanyResult;
import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmdepartmentResult;
import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmjobtitlesResult;
import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmresourcetoebmsResult;
import com.tadpole.cloud.externalSystem.modular.oa.service.ICompanyService;
import com.tadpole.cloud.externalSystem.modular.oa.service.IHrmdepartmentService;
import com.tadpole.cloud.externalSystem.modular.oa.service.IHrmjobtitlesService;
import com.tadpole.cloud.externalSystem.modular.oa.service.IHrmresourcetoebmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: ty
 * @description: EBMS基础信息服务提供者
 * @date: 2023/4/17
 */
@RestController
public class OaHrmProvider implements OaHrmApi {

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IHrmdepartmentService iHrmdepartmentService;

    @Autowired
    private IHrmresourcetoebmsService hrmresourcetoebmsService;

    @Autowired
    private IHrmjobtitlesService hrmjobtitlesService;

    @Override
    public List<CompanyResult> getCompany() {
        return companyService.getCompany();
    }

    @Override
    public List<HrmdepartmentResult> getDepartment() {
        return iHrmdepartmentService.getDepartment();
    }

    @Override
    public List<HrmresourcetoebmsResult> getHrmResource() {
        return hrmresourcetoebmsService.getHrmResource();
    }

    @Override
    public List<HrmjobtitlesResult> getJobTitle() {
        return hrmjobtitlesService.getJobTitle();
    }
}
