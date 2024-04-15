package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import com.tadpole.cloud.externalSystem.modular.mabang.service.IK3PurchaseInStockOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * K3采购入库单明细项 前端控制器
    * </p>
*
* @author S20190161
* @since 2023-05-17
*/
@RestController
@Api(tags = "K3采购入库单明细项")
@ApiResource(name = "K3采购入库单明细项", path = "/k3PurchaseInStockOrderItem")
public class K3PurchaseInStockOrderItemController {

    @Autowired
    private IK3PurchaseInStockOrderItemService service;

    }
