package com.tadpole.cloud.operationManagement.modular.shipment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.operationManagement.modular.shipment.service.ITrackingShippingService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * 追踪明细项-出货清单 前端控制器
    * </p>
*
* @author lsy
* @since 2023-02-02
*/
@RestController
@Api(tags = "追踪明细项-出货清单")
@ApiResource(name = "追踪明细项-出货清单", path = "/trackingShipping")
public class TrackingShippingController {

    @Autowired
    private ITrackingShippingService service;

    }
