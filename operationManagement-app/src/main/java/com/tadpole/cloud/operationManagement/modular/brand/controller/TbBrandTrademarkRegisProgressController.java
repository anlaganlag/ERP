package com.tadpole.cloud.operationManagement.modular.brand.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.api.brand.model.result.*;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import com.tadpole.cloud.operationManagement.api.brand.entity.TbBrandTrademarkRegisProgress;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbBrandTrademarkRegisProgressService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
* <p>
* 品牌商标注册进度表 前端控制器
* </p>
*
* @author S20190161
* @since 2023-10-24
*/
@RestController
@Api(tags = "品牌商标注册进度表")
@ApiResource(name = "品牌商标注册进度表", path = "/brand/brandTrademarkRegisProgress")
public class TbBrandTrademarkRegisProgressController {

    @Autowired
    private TbBrandTrademarkRegisProgressService service;

    @ApiOperation(value ="获取品牌商标注册进度表明细")
    @GetResource(name="获取品牌商标注册进度表明细",path = "/detail")
    @BusinessLog(title ="获取品牌商标注册进度表明细",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData detail(@RequestParam Long id)
    {
        return ResponseData.success( service.queryById(id));

    }
    @ApiOperation(value ="保存品牌商标注册进度表信息")
    @PostResource(name="保存品牌商标注册进度表信息",path = "/save")
    @BusinessLog(title ="保存品牌商标注册进度表信息",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save(@RequestBody @Valid TbBrandTrademarkRegisProgressParam param)
    {
        service.save(param);
        return ResponseData.success();

    }

    }
