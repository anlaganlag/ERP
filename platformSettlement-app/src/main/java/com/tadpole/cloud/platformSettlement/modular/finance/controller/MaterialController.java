package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IMaterialService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 物料信息表 前端控制器
* </p>
*
* @author gal
* @since 2021-12-24
*/
@RestController
@RequestMapping("//material")
public class MaterialController {

    @Autowired
    private IMaterialService service;

}
