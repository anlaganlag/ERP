package com.tadpole.cloud.operationManagement.modular.shipment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.operationManagement.modular.shipment.service.IAmzInvAsinDayAggService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * 店铺销量库存表 前端控制器
    * </p>
*
* @author lsy
* @since 2023-02-17
*/
@RestController
@Api(tags = "店铺销量库存表")
@ApiResource(name = "店铺销量库存表", path = "/amzInvAsinDayAgg")
public class AmzInvAsinDayAggController {

    @Autowired
    private IAmzInvAsinDayAggService service;

    }
