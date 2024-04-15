package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangOrderItemService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * 马帮订单具体商品项item 前端控制器
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
@RestController
@RequestMapping("/mabang-order-item")
public class MabangOrderItemController {

    @Autowired
    private IMabangOrderItemService service;

    }
