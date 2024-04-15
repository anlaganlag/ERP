package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * 马帮商品库存信息 前端控制器
    * </p>
*
* @author lsy
* @since 2023-05-23
*/
@RestController
@Api(tags = "马帮商品库存信息")
@ApiResource(name = "马帮商品库存信息", path = "/mabangProductStock")
public class MabangProductStockController {

    @Autowired
    private IMabangProductStockService service;

    }
