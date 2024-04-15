package com.tadpole.cloud.externalSystem.modular.saihu.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.saihu.model.params.SaiHuProductParam;
import com.tadpole.cloud.externalSystem.api.saihu.model.result.SaiHuBaseResult;
import com.tadpole.cloud.externalSystem.modular.saihu.service.ISaihuShopService;
import com.tadpole.cloud.externalSystem.modular.saihu.service.ISaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ty
 * @description: 赛狐店铺列表Controller类
 * @date: 2024/2/21
 */
@RestController
@ApiResource(name = "赛狐店铺列表Controller类", path = "/saihuShop")
@Api(tags = "赛狐店铺列表Controller类")
public class SaihuShopController {
    @Autowired
    private ISaleService saleService;
    @Autowired
    private ISaihuShopService saihuShopService;

    @PostResource(name = "获取店铺列表", path = "/shopList", requiredPermission = false, requiredLogin = false)
    @ApiOperation("获取店铺列表")
    public ResponseData shopList() {
        return saihuShopService.shopList();
    }

    @PostResource(name = "获取自定义店铺列表", path = "/customList", requiredPermission = false, requiredLogin = false)
    @ApiOperation("获取自定义店铺列表")
    public ResponseData customList() throws Exception {
        return saihuShopService.customList();
    }

    @PostResource(name = "赛狐在线产品", path = "/productList", requiredPermission = false, requiredLogin = false)
    @ApiOperation("赛狐在线产品")
    public SaiHuBaseResult productList(@RequestBody SaiHuProductParam param) throws Exception {
        return saleService.productList(param);
    }
}
