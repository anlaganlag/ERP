package com.tadpole.cloud.externalSystem.modular.ebms.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.api.ebms.model.param.EbmsOverseasOutWarehouseParam;
import com.tadpole.cloud.externalSystem.modular.ebms.service.IEbmsOverseaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ty
 * @description: 海外仓调用EBMS接口
 * @date: 2023/4/3
 */
@RestController
@ApiResource(name = "海外仓调用EBMS接口", path = "/ebmsOversea")
@Api(tags = "海外仓调用EBMS接口")
public class EbmsOverseaController {

    @Autowired
    private IEbmsOverseaService ebmsOverseaService;

    @PostResource(name = "海外仓箱子信息", path = "/getAllBoxInfo", requiredPermission = false, requiredLogin = false)
    @ApiOperation("海外仓箱子信息")
    public ResponseData getAllBoxInfo() {
        return ebmsOverseaService.getAllBoxInfo();
    }

    @PostResource(name = "创建出货清单", path = "/createShipmentList", requiredPermission = false, requiredLogin = false)
    @ApiOperation("创建出货清单")
    public ResponseData createShipmentList(@RequestBody EbmsOverseasOutWarehouseParam param) {
        return ebmsOverseaService.createShipmentList(param);
    }
}
