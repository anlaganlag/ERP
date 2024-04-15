package com.tadpole.cloud.operationManagement.modular.brand.controller;

import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.*;
import org.springframework.web.bind.annotation.*;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandLogo;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbBrandLogoService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 品牌logo表 前端控制器
* </p>
*
* @author S20190161
* @since 2023-10-30
*/
@RestController
@Api(tags = "品牌logo表")
@ApiResource(name = "品牌logo表", path = "/brand/brandLogo")
public class TbBrandLogoController {

    @Autowired
    private TbBrandLogoService service;

    @ApiOperation(value = "获取品牌logo表明细")
    @GetResource(name="获取品牌logo表明细",path = "/detail")
    @BusinessLog(title = "获取品牌logo表明细", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData detail(@RequestParam Long id) {
        return ResponseData.success(service.queryById(id));

    }

    @ApiOperation(value = "保存品牌logo表")
    @PostResource(name="保存品牌logo表",path = "/save")
    @BusinessLog(title = "保存品牌logo表", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save(@RequestBody @Valid TbBrandLogoParam param) {
        service.save(param);
        return ResponseData.success();
    }
    @ApiOperation(value = "获取品牌logo信息")
    @GetResource(path = "/queryByBcId")
    @BusinessLog(title = "获取品牌logo信息", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryByBcId(@RequestParam Long bcId) {
        return ResponseData.success(service.queryByBcId(bcId));
    }
    @ApiOperation(value = "新增版本")
    @PostResource(name="新增版本",path = "/save2")
    @BusinessLog(title = "新增版本", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save2(@RequestBody @Valid TbBrandLogoParam param) {
        service.save(param);
        return ResponseData.success();
    }
    @ApiOperation(value = "状态更改")
    @PostResource(name="状态更改",path = "/save3")
    @BusinessLog(title = "状态更改", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save3(@RequestBody @Valid TbBrandLogoParam param) {
        service.save(param);
        return ResponseData.success();
    }
    @ApiOperation(value = "图片上传")
    @PostResource(name="图片上传",path = "/save4")
    @BusinessLog(title = "图片上传", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save4(@RequestBody @Valid TbBrandLogoParam param) {
        service.save(param);
        return ResponseData.success();
    }

}
