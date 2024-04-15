package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import com.tadpole.cloud.platformSettlement.modular.inventory.service.IEndingInventoryDetailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 期末库存列表 前端控制器
* </p>
*
* @author gal
* @since 2021-12-13
*/
@RestController
@RequestMapping("//ending-inventory-detail")
public class EndingInventoryDetailController {

    @Autowired
    private IEndingInventoryDetailService service;
}
