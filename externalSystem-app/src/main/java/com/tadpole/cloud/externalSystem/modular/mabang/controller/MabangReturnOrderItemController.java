package com.tadpole.cloud.externalSystem.modular.mabang.controller;

import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangReturnOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * 马帮退货单明细项列表 前端控制器
    * </p>
*
* @author cyt
* @since 2022-08-24
*/
@RestController
@RequestMapping("/mabangReturnOrderItem")
public class MabangReturnOrderItemController {

    @Autowired
    private IMabangReturnOrderItemService service;

    }
