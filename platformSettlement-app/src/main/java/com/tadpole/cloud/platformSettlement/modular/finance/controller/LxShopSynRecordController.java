package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ILxShopSynRecordService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
*  前端控制器
* </p>
*
* @author ty
* @since 2022-05-17
*/
@RestController
@RequestMapping("/lxShopSynRecord")
public class LxShopSynRecordController {

    @Autowired
    private ILxShopSynRecordService service;

}
