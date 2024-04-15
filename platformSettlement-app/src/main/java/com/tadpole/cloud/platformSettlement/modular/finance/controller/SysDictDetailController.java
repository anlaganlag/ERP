package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISysDictDetailService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 用户字典明细 前端控制器
* </p>
*
* @author gal
* @since 2021-11-11
*/
@RestController
@RequestMapping("//sys-dict-detail")
public class SysDictDetailController {

    @Autowired
    private ISysDictDetailService service;

}
