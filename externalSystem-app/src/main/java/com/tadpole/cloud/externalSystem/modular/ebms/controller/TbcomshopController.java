package com.tadpole.cloud.externalSystem.modular.ebms.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.TbcomshopParam;
import com.tadpole.cloud.externalSystem.api.ebms.model.result.TbcomshopResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.externalSystem.modular.ebms.service.ITbcomshopService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author S20190109
 * @since 2023-05-15
 */
@RestController
@Api(tags = "店铺")
@ApiResource(name = "店铺", path = "/tbcomshop")
public class TbcomshopController {

    @Autowired
    private ITbcomshopService service;

    @GetResource(name = "查询店铺", path = "/getShopList", requiredPermission = false, requiredLogin = false)
    @ApiOperation("查询店铺")
    public List<TbcomshopResult> getWaitMatList(TbcomshopParam param) {
        return service.getShopList(param);
    }

    @GetResource(name = "获取平台", path = "/getPlatformList", requiredPermission = false, requiredLogin = false)
    @ApiOperation("获取平台")
    public List<String> getPlatformList() {
        return service.getPlatformList();
    }

    @GetResource(name = "获取平台店铺名称", path = "/getAllShopNameList")
    @ApiOperation("获取平台店铺名称")
    public ResponseData getAllShopNameList() {
        return ResponseData.success(service.getAllShopNameList());
    }

}
