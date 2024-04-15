package com.tadpole.cloud.operationManagement.modular.stock.controller;


import com.tadpole.cloud.operationManagement.modular.stock.service.IStockPurchaseOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * 采购订单 前端控制器
    * </p>
*
* @author cyt
* @since 2022-06-30
*/
@RestController
@RequestMapping("//stock-purchase-orders")
public class StockPurchaseOrdersController {

    @Autowired
    private IStockPurchaseOrdersService service;

    }
