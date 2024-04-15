package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import com.tadpole.cloud.externalSystem.modular.mabang.service.IK3CrossTransferItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * K3跨组织直接调拨单明细项 前端控制器
    * </p>
*
* @author lsy
* @since 2022-06-28
*/
@RestController
@RequestMapping("/k3-cross-transfer-item")
public class K3CrossTransferItemController {

    @Autowired
    private IK3CrossTransferItemService service;

    }
