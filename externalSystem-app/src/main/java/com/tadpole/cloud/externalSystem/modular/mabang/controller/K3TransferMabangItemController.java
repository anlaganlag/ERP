package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IK3TransferMabangItemService;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * K3调拨单同步到马帮记录明细项 前端控制器
    * </p>
*
* @author lsy
* @since 2022-06-15
*/
@RestController
@RequestMapping("/k3-transfer-mabang-item")
public class K3TransferMabangItemController {

    @Autowired
    private IK3TransferMabangItemService service;

    }
