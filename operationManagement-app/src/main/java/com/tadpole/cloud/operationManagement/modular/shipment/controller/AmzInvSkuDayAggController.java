package com.tadpole.cloud.operationManagement.modular.shipment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.operationManagement.modular.shipment.service.IAmzInvSkuDayAggService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * 店铺库存销量明细表 前端控制器
    * </p>
*
* @author lsy
* @since 2023-02-17
*/
@RestController
@Api(tags = "店铺库存销量明细表")
@ApiResource(name = "店铺库存销量明细表", path = "/amzInvSkuDayAgg")
public class AmzInvSkuDayAggController {

    @Autowired
    private IAmzInvSkuDayAggService service;

    }
