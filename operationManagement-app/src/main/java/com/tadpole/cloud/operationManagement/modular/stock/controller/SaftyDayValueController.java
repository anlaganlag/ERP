package com.tadpole.cloud.operationManagement.modular.stock.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.tadpole.cloud.operationManagement.modular.stock.service.ISaftyDayValueService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * 安全天数参数值 前端控制器
    * </p>
*
* @author cyt
* @since 2022-07-20
*/
@RestController
@RequestMapping("/saftyDayValue")
public class SaftyDayValueController {

    @Autowired
    private ISaftyDayValueService service;

    }
