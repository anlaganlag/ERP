package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IReceivableDetailDetailService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 应收明细详情 前端控制器
* </p>
*
* @author gal
* @since 2021-10-25
*/
@RestController
@RequestMapping("//receivable-detail-detail")
public class ReceivableDetailDetailController {

    @Autowired
    private IReceivableDetailDetailService service;

}
