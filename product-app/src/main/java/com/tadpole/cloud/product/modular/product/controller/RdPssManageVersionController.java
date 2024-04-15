package com.tadpole.cloud.product.modular.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.product.modular.product.service.IRdPssManageVersionService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 产品同款版本 前端控制器
 * </p>
 *
 * @author S20210221
 * @since 2024-04-03
 */
@RestController
@Api(tags = "产品同款版本")
@ApiResource(name = "产品同款版本", path = "/rdPssManageVersion")
public class RdPssManageVersionController {

    @Autowired
    private IRdPssManageVersionService service;

}
