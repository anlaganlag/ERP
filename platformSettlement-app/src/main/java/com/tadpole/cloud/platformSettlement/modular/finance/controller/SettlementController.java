package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISettlementService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* selltement主数据 前端控制器
* </p>
*
* @author gal
* @since 2021-10-25
*/
@RestController
@RequestMapping("//settlement")
public class SettlementController {

    @Autowired
    private ISettlementService service;

}
