package com.tadpole.cloud.operationManagement.modular.shipment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.operationManagement.modular.shipment.service.IShipmentRecomSnapshotService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * 发货推荐数据快照 前端控制器
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
@RestController
@Api(tags = "发货推荐数据快照")
@ApiResource(name = "发货推荐数据快照", path = "/shipmentRecomSnapshot")
public class ShipmentRecomSnapshotController {

    @Autowired
    private IShipmentRecomSnapshotService service;

    }
