package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.K3TransferItem;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.K3TransferItemParam;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IK3TransferItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* <p>
    * K3调拨单明细项 前端控制器
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
@RestController
@ApiResource(name="k3cloud直接调拨单列表",path = "/k3TransferItem")
@Api(tags =  "k3cloud直接调拨单列表")
public class K3TransferItemController {

    @Autowired
    private IK3TransferItemService service;

    @PostResource(name = "k3直接调拨单明细保存", path = "/add", requiredPermission = false)
    @ApiOperation(value = "k3直接调拨单明细保存", response = K3TransferItem.class)
    public ResponseData add(@RequestBody K3TransferItemParam param) {
        List<K3TransferItem> itemList=service.list(param);
        return service.add(itemList);
    }


    @PostResource(name = "k3马帮仓库信息", path = "/mabangWarehouse", requiredPermission = false)
    @ApiOperation(value = "k3马帮仓库信息", response = K3TransferItem.class)
    public ResponseData mabangWarehouse() {
       return service.mabangWarehouse();
    }

    @PostResource(name = "test", path = "/test", requiredPermission = false)
    @ApiOperation(value = "test", response = K3TransferItem.class)
    public ResponseData test() {
        List<K3TransferItem> itemList=service.list(K3TransferItemParam.builder().build());
        return service.add(itemList);
    }
}
