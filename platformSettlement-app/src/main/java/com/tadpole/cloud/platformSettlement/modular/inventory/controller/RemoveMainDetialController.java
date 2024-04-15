package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import com.tadpole.cloud.platformSettlement.modular.inventory.service.IRemoveMainDetialService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* Amazon销毁移除子表 前端控制器
* </p>
*
* @author gal
* @since 2021-11-24
*/
@RestController
@RequestMapping("//remove-main-detial")
public class RemoveMainDetialController {

    @Autowired
    private IRemoveMainDetialService service;
}
