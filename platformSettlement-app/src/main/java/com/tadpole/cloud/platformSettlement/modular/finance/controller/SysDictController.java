package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.CwSysDict;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SysDictDetail;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
* <p>
* 用户字典表 前端控制器
* </p>
*
* @author gal
* @since 2021-10-29
*/
@RestController
@ApiResource(name = "财务字典管理", path = "/cwSysDict")
@Api(tags = "财务字典管理")
public class SysDictController {

    @Autowired
    private ISysDictService service;

    @PostResource(name = "财务字典", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "财务字典", response = CwSysDict.class)
    @BusinessLog(title = "财务字典管理-财务字典查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody CwSysDict param) {
        List<CwSysDict> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "财务字典明细", path = "/queryDetail", requiredPermission = false)
    @ApiOperation(value = "财务字典明细", response = CwSysDict.class)
    @BusinessLog(title = "财务字典管理-财务字典明细查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryDetail(@RequestBody SysDictDetail param) {
        List<SysDictDetail> pageBySpec = service.queryDetail(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "财务字典新增", path = "/add", requiredPermission = false)
    @ApiOperation(value = "财务字典新增", response = SysDictDetail.class)
    @BusinessLog(title = "财务字典管理-财务字典新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody SysDictDetail param) throws IOException {
        return service.addSysDictDetail(param);
    }

    @PostResource(name = "财务字典修改", path = "/update", requiredPermission = false)
    @ApiOperation(value = "财务字典修改", response = SysDictDetail.class)
    @BusinessLog(title = "财务字典管理-财务字典修改",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData update(@RequestBody SysDictDetail param) {
         service.updateSysDictDetail(param);
        return ResponseData.success();
    }

    @PostResource(name = "财务字典删除", path = "/delete", requiredPermission = false)
    @ApiOperation(value = "财务字典删除", response = SysDictDetail.class)
    @BusinessLog(title = "财务字典管理-财务字典删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody SysDictDetail param) {
         service.deleteSysDictDetail(param);
        return ResponseData.success();
    }
}
