package com.tadpole.cloud.externalSystem.api.oa;

import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.ebms.model.resp.EbmsUserInfo;
import com.tadpole.cloud.externalSystem.api.oa.model.result.CompanyResult;
import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmdepartmentResult;
import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmjobtitlesResult;
import com.tadpole.cloud.externalSystem.api.oa.model.result.HrmresourcetoebmsResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author: ty
 * @description: EBMS基础信息API
 * @date: 2023/4/17
 */
@RequestMapping("/oaHrmApi")
public interface OaHrmApi {

    /**
     * @return
     */
    @RequestMapping(value = "/getCompany", method = RequestMethod.GET)
    List<CompanyResult> getCompany();

    @RequestMapping(value = "/getDepartment", method = RequestMethod.GET)
    List<HrmdepartmentResult> getDepartment();

    @RequestMapping(value = "/getHrmResource", method = RequestMethod.GET)
    List<HrmresourcetoebmsResult> getHrmResource();

    @RequestMapping(value = "/getJobTitle", method = RequestMethod.GET)
    List<HrmjobtitlesResult> getJobTitle();


}
