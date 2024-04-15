package com.tadpole.cloud.operationManagement.modular.stock.controller;


import com.tadpole.cloud.operationManagement.modular.stock.service.ISysBizConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * 系统业务配置表 前端控制器
    * </p>
*
* @author lsy
* @since 2022-08-31
*/
@RestController
@RequestMapping("/sysBizConfig")
public class SysBizConfigController {

    @Autowired
    private ISysBizConfigService service;

    }
