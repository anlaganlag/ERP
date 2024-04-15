package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ILxAmazonSettlementService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 领星Settlement源文件汇总 前端控制器
* </p>
*
* @author cyt
* @since 2022-05-13
*/
@RestController
@RequestMapping("//lx-amazon-settlement")
public class LxAmazonSettlementController {

    @Autowired
    private ILxAmazonSettlementService service;

}
