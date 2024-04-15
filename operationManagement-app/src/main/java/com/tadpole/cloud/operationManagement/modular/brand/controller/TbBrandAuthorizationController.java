package com.tadpole.cloud.operationManagement.modular.brand.controller;

import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.*;
import org.springframework.web.bind.annotation.*;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbBrandAuthorizationService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 品牌授权表 前端控制器
* </p>
*
* @author S20190161
* @since 2023-10-30
*/
@RestController
@Api(tags = "品牌授权表")
@ApiResource(name = "品牌授权表", path = "/brand/brandAuthorization")
public class TbBrandAuthorizationController {

    @Autowired
    private TbBrandAuthorizationService service;

    @ApiOperation(value = "获取品牌授权表明细")
    @GetResource(path = "/detail")
    @BusinessLog(title = "获取品牌授权表明细", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData detail(@RequestParam Long id) {
        return ResponseData.success(service.queryById(id));

    }

    @ApiOperation(value = "获取品牌所有授权信息")
    @GetResource(path = "/queryByBcId")
    @BusinessLog(title = "获取品牌所有授权信息", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryByBcId(@RequestParam Long bcId) {
        return ResponseData.success(service.queryByBcId(bcId));

    }

    @ApiOperation(value = "保存品牌授权表")
    @PostResource(name="保存品牌授权表",path = "/save")
    @BusinessLog(title = "保存品牌授权表", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save(@RequestBody @Valid TbBrandAuthorizationParam param) {
        service.save(param);
        return ResponseData.success();

    }
    @ApiOperation(value = "删除品牌授权")
    @PostResource(name="删除品牌授权",path = "/delete")
    @BusinessLog(title = "删除品牌授权", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody  TbBrandAuthorizationParam param) {
        service.delete(param.getId());
        return ResponseData.success();

    }
}
