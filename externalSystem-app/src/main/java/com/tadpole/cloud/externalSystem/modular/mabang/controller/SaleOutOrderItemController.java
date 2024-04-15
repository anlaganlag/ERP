package com.tadpole.cloud.externalSystem.modular.mabang.controller;
import com.tadpole.cloud.externalSystem.modular.mabang.service.ISaleOutOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * 销售出库单明细项 前端控制器
    * </p>
*
* @author cyt
* @since 2022-08-24
*/
@RestController
@RequestMapping("/saleOutOrderItem")
public class SaleOutOrderItemController {

    @Autowired
    private ISaleOutOrderItemService service;

    }
