package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MaterialPriceInfoParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MaterialPriceInfoResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMaterialPriceInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 物料价格信息 前端控制器
 * </p>
 *
 * @author lsy
 * @since 2023-05-06
 */
@RestController
@ApiResource(name = "物料价格信息", path = "/materialPriceInfo")
@Api(tags = "物料价格信息")
public class MaterialPriceInfoController {

    @Autowired
    private IMaterialPriceInfoService service;

    @PostResource(name = "物料价格信息列表", path = "/list", requiredPermission = false,requiredLogin = false,menuFlag = true)
    @ApiOperation(value = "物料价格信息列表", response = ResponseData.class)
    public ResponseData findPageBySpec(@RequestBody MaterialPriceInfoParam param) {
        PageResult<MaterialPriceInfoResult> pageResult = service.findPageBySpec(param);
        return ResponseData.success(pageResult);
    }


    @PostResource(name = "设置物料临时价格", path = "/updateTemporaryPrice", requiredPermission = false)
    @ApiOperation(value = "设置物料临时价格", response = ResponseData.class)
    public ResponseData updateTemporaryPrice(@RequestBody MaterialPriceInfoParam param) {
        return service.updateTemporaryPrice(param);
    }

}
