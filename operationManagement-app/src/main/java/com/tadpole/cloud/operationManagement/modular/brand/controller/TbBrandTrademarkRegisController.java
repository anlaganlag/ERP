package com.tadpole.cloud.operationManagement.modular.brand.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.api.brand.model.params.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.operationManagement.modular.brand.service.TbBrandTrademarkRegisService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
* <p>
* 品牌商标注册表 前端控制器
* </p>
*
* @author S20190161
* @since 2023-10-24
*/
@RestController
@Api(tags = "品牌商标注册表")
@ApiResource(name = "品牌商标注册表", path = "/brand/brandTrademarkRegis")
public class TbBrandTrademarkRegisController {

    @Autowired
    private TbBrandTrademarkRegisService service;

    @ApiOperation(value ="注册信息")
    @GetResource(name="注册信息",path = "/detail")
    @BusinessLog(title ="注册信息",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData detail(@RequestParam Long id)
    {
        return ResponseData.success( service.queryById(id));
    }
    @ApiOperation(value ="材料信息")
    @GetResource(name="材料信息",path = "/detail2")
    @BusinessLog(title ="材料信息",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData detail2(@RequestParam Long id)
    {
        return ResponseData.success( service.queryById(id));
    }
    @ApiOperation(value ="保存品牌商标注册表信息")
    @PostResource(name="保存品牌商标注册表信息",path = "/save")
    @BusinessLog(title ="保存品牌商标注册表信息",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData save(@RequestBody @Valid TbBrandTrademarkRegisParam param)
    {
        service.save(param);
        return ResponseData.success();

    }
    @ApiOperation(value ="添加扩类")
    @PostResource(name="添加扩类",path = "/addOverClass")
    @BusinessLog(title ="添加扩类",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData addOverClass(@RequestBody @Valid TbBrandTrademarkRegisOverParam param)
    {
        service.addOverClass(param);
        return ResponseData.success();

    }


    }
