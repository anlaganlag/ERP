package com.tadpole.cloud.externalSystem.modular.oa.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.oa.entity.HrmShiftCalendarNormal;
import com.tadpole.cloud.externalSystem.modular.oa.service.IHrmdepartmentService;
import com.tadpole.cloud.externalSystem.modular.oa.service.IHrmjobtitlesService;
import com.tadpole.cloud.externalSystem.modular.oa.service.IHrmresourcetoebmsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.externalSystem.modular.oa.service.ICompanyService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author S20190109
 * @since 2023-05-11
 */
@RestController
@Api(tags = "OA组织信息")
@ApiResource(name = "", path = "/oaHrm")
public class OaHrmController {

    @Autowired
    private ICompanyService iCompanyService;

    @Autowired
    private IHrmdepartmentService iHrmdepartmentService;

    @Autowired
    private IHrmresourcetoebmsService hrmresourcetoebmsService;

    @Autowired
    private IHrmjobtitlesService hrmjobtitlesService;

    @GetResource(name = "获取公司", path = "/getCompany", requiredPermission = false, requiredLogin = false)
    @ApiOperation("获取公司")
    public ResponseData getCompany() {
        return ResponseData.success(iCompanyService.getCompany());
    }

    @GetResource(name = "获取部门", path = "/getDepartment", requiredPermission = false, requiredLogin = false)
    @ApiOperation("获取部门")
    public ResponseData getDepartment() {
        return ResponseData.success(iHrmdepartmentService.getDepartment());
    }


    @GetResource(name = "获取人员", path = "/getHrmResource", requiredPermission = false, requiredLogin = false)
    @ApiOperation("获取人员")
    public ResponseData getHrmResource() {
        return ResponseData.success(hrmresourcetoebmsService.getHrmResource());
    }

    @GetResource(name = "获取职位", path = "/getJobTitle", requiredPermission = false, requiredLogin = false)
    @ApiOperation("获取职位")
    public ResponseData getJobTitle() {
        return ResponseData.success(hrmjobtitlesService.getJobTitle());
    }

    @GetResource(name = "单点登录", path = "/ssoLogin")
    @ApiOperation("单点登录")
    public ResponseData ssoLogin() {
        return ResponseData.success(hrmjobtitlesService.ssoLogin());
    }

    @GetResource(name = "工作日历", path = "/hrmCalendar")
    @ApiOperation("工作日历")
    public ResponseData hrmCalendar(HrmShiftCalendarNormal param) {
        return ResponseData.success(hrmjobtitlesService.hrmCalendar(param));
    }

}
