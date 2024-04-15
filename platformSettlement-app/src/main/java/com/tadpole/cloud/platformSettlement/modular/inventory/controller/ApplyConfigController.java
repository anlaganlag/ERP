package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.inventory.entity.ApplyConfig;
import com.tadpole.cloud.platformSettlement.api.inventory.model.params.ApplyConfigParam;
import com.tadpole.cloud.platformSettlement.api.inventory.model.result.ApplyConfigResult;
import com.tadpole.cloud.platformSettlement.modular.inventory.service.IApplyConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

/**
* <p>
*  前端控制器
* </p>
*
* @author cyt
* @since 2022-05-24
*/
@RestController
@ApiResource(name = "流程配置表", path = "/applyConfig")
@Api(tags = "流程配置表")
public class ApplyConfigController {

    @Autowired
    private IApplyConfigService service;

    @PostResource(name = "流程配置列表", path = "/list", menuFlag = true)
    @ApiOperation(value = "流程配置列表", response = ApplyConfig.class)
    @BusinessLog(title = "流程配置表-流程配置列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody ApplyConfigParam param) throws IOException {
        PageResult<ApplyConfigResult> pageBySpec = service.queryListPage(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "新增", path = "/add",requiredPermission = false)
    @ApiOperation(value="新增",response = ApplyConfig.class)
    @BusinessLog(title = "流程配置表-新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody ApplyConfigParam param) {
        return service.add(param);
    }

    @PostResource(name = "修改", path = "/update",requiredPermission = false)
    @ApiOperation(value="修改",response = ApplyConfig.class)
    @BusinessLog(title = "流程配置表-修改",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody ApplyConfigParam param) {
        return service.edit(param);
    }

    @PostResource(name = "删除", path = "/delete",requiredPermission = false)
    @ApiOperation(value="删除", response = ApplyConfig.class)
    @BusinessLog(title = "流程配置表-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody ApplyConfigParam param) throws ParseException {
        return service.delete(param);
    }
}
