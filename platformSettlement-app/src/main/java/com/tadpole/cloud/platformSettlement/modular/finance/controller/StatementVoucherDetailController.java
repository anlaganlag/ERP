package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IStatementVoucherDetailService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 收入记录表凭证明细 前端控制器
* </p>
*
* @author gal
* @since 2021-10-25
*/
@RestController
@RequestMapping("//statement-voucher-detail")
public class StatementVoucherDetailController {

    @Autowired
    private IStatementVoucherDetailService service;

}
