package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ITgCountryInfoService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 国家地区信息 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-05-22
 */
@RestController
@Api(tags = "国家地区信息")
@ApiResource(name = "国家地区信息", path = "/tgCountryInfo")
public class TgCountryInfoController {

    @Autowired
    private ITgCountryInfoService service;

    /**
     * 国家下拉
     * @return
     */
    @GetResource(name = "国家下拉", path = "/countrySelect")
    @ApiOperation(value = "国家下拉")
    public ResponseData countrySelect() {
        return service.countrySelect();
    }
}
