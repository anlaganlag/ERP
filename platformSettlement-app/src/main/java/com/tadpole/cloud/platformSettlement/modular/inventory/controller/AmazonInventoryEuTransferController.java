package com.tadpole.cloud.platformSettlement.modular.inventory.controller;

import com.tadpole.cloud.platformSettlement.modular.inventory.service.IAmazonInventoryEuTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* EBMS转仓清单（欧洲站点调拨) 前端控制器
* </p>
*
* @author S20190161
* @since 2022-12-20
*/
@RestController
@Api(tags = "EBMS转仓清单（欧洲站点调拨)")
@ApiResource(name = "EBMS转仓清单（欧洲站点调拨)", path = "/amazonInventoryEuTransfer")
public class AmazonInventoryEuTransferController {

    @Autowired
    private IAmazonInventoryEuTransferService service;
}
