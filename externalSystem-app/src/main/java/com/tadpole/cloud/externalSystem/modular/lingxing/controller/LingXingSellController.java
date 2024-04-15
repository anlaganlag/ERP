package com.tadpole.cloud.externalSystem.modular.lingxing.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sell.ListingReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sell.OrderDetailReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sell.OrderReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LingXingSellService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ty
 * @description: 领星销售Controller类
 * @date: 2022/8/12
 */
@RestController
@ApiResource(name = "领星销售Controller类", path = "/lingXingSell")
@Api(tags = "领星销售Controller类")
public class LingXingSellController {

    @Autowired
    private LingXingSellService lingXingSellService;

    @PostResource(name = "亚马逊订单列表", path = "/order", requiredPermission = false, requiredLogin = false)
    @ApiOperation("亚马逊订单列表")
    public LingXingBaseRespData order(@Validated @RequestBody OrderReq req) throws Exception {
        return lingXingSellService.order(req);
    }

    @PostResource(name = "亚马逊订单详情", path = "/orderDetail", requiredPermission = false, requiredLogin = false)
    @ApiOperation("亚马逊订单详情")
    public LingXingBaseRespData orderDetail(@Validated @RequestBody OrderDetailReq req) throws Exception {
        return lingXingSellService.orderDetail(req);
    }

    @PostResource(name = "查询亚马逊Listing", path = "/listing", requiredPermission = false, requiredLogin = false)
    @ApiOperation("查询亚马逊Listing")
    public LingXingBaseRespData listing(@Validated @RequestBody ListingReq req) throws Exception {
        return lingXingSellService.listing(req);
    }
}
