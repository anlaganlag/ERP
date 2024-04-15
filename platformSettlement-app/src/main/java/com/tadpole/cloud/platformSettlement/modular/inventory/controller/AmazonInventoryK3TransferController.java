package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import com.tadpole.cloud.platformSettlement.modular.inventory.service.IAmazonInventoryK3TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* K3跨组织直接调拨单 前端控制器
* </p>
*
* @author S20190161
* @since 2022-12-20
*/
@RestController
@Api(tags = "K3跨组织直接调拨单")
@ApiResource(name = "K3跨组织直接调拨单", path = "/amazonInventoryK3Transfer")
public class AmazonInventoryK3TransferController {

    @Autowired
    private IAmazonInventoryK3TransferService service;
}
