package com.tadpole.cloud.operationManagement.modular.brand.controller;

import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.*;
import org.springframework.web.bind.annotation.*;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandCommunal;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbBrandCommunalService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 品牌管理主表 前端控制器
* </p>
*
* @author S20190161
* @since 2023-10-30
*/
@RestController
@Api(tags = "品牌管理主表")
@ApiResource(name = "品牌管理主表", path = "/brand/brandCommunal")
public class TbBrandCommunalController {

    @Autowired
    private TbBrandCommunalService service;

    @ApiOperation(value = "分页查询", response = TbBrandTrademarkResult.class)
    @PostResource(name = "品牌管理主表-分页查询", path = "/getPage", menuFlag = true)
    @BusinessLog(title = "分页查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getPage(@RequestBody TbBrandCommunalParam param) {

        return ResponseData.success(service.getPage(param));
    }

    @ApiOperation(value ="获取品牌管理主表明细")
    @GetResource(path = "/detail")
    @BusinessLog(title ="获取品牌管理主表明细",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData detail(@RequestParam Long id)
    {
    return ResponseData.success( service.queryById(id));

    }
    @ApiOperation(value ="保存品牌管理主表")
    @PostResource(name="保存品牌管理主表",path = "/save")
    @BusinessLog(title ="保存品牌管理主表",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save(@RequestBody @Valid TbBrandCommunalParam param)
    {
    service.save(param);
    return ResponseData.success();
    }

    @ApiOperation(value = "状态变更")
    @PostResource(name = "状态变更",path = "/save2")
    @BusinessLog(title = "状态变更", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData save2() {
        return ResponseData.success();
    }
    @ApiOperation(value = "公共信息修改")
    @PostResource(name = "公共信息修改",path = "/save3")
    @BusinessLog(title = "公共信息修改", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData save3() {
        return ResponseData.success();
    }
    }
