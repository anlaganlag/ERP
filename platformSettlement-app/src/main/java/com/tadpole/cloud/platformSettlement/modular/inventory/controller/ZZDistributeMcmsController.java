package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import com.tadpole.cloud.platformSettlement.modular.inventory.service.IZZDistributeMcmsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
*  前端控制器
* </p>
*
* @author gal
* @since 2021-12-09
*/
@RestController
@RequestMapping("//z-zdistribute-mcms")
public class ZZDistributeMcmsController {

    @Autowired
    private IZZDistributeMcmsService service;
}
