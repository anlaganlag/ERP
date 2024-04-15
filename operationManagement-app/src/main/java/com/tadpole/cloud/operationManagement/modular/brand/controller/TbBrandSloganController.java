package com.tadpole.cloud.operationManagement.modular.brand.controller;

import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandLogoParam;
import com.tadpole.cloud.operationManagement.api.brand.model.params.TbBrandSloganParam;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.*;
import org.springframework.web.bind.annotation.*;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbBrandSloganService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 品牌sloga表 前端控制器
* </p>
*
* @author S20190161
* @since 2023-10-30
*/
@RestController
@Api(tags = "品牌sloga表")
@ApiResource(name = "品牌sloga表", path = "/brand/brandSlogan")
public class TbBrandSloganController {

    @Autowired
    private TbBrandSloganService service;

    @ApiOperation(value = "获取品牌slogan表明细")
    @GetResource(path = "/detail")
    @BusinessLog(title = "获取品牌slogan表明细", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData detail(@RequestParam Long id) {
        return ResponseData.success(service.queryById(id));

    }

    @ApiOperation(value = "保存品牌slogan表")
    @PostResource(name="保存品牌slogan表",path = "/save")
    @BusinessLog(title = "保存品牌slogan表", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save(@RequestBody @Valid TbBrandSloganParam param) {
        service.save(param);
        return ResponseData.success();

    }

    @ApiOperation(value = "获取品牌slogan息")
    @GetResource(path = "/queryByBcId")
    @BusinessLog(title = "获取品牌slogan息", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryByBcId(@RequestParam Long bcId) {
        return ResponseData.success(service.queryByBcId(bcId));

    }

    @ApiOperation(value = "新增版本")
    @PostResource(name="新增版本",path = "/save2")
    @BusinessLog(title = "新增版本", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save2() {

        return ResponseData.success();
    }
    @ApiOperation(value = "状态更改")
    @PostResource(name="状态更改",path = "/save3")
    @BusinessLog(title = "状态更改", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save3() {
        return ResponseData.success();
    }
    @ApiOperation(value = "编辑")
    @PostResource(name="编辑",path = "/save4")
    @BusinessLog(title = "编辑", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save4() {
        return ResponseData.success();
    }
}
