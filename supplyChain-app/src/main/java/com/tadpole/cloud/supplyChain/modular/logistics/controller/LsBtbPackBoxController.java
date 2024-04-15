package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsBtbPackBoxService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  BTB订单发货箱子信息前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-11-10
 */
@RestController
@Api(tags = "BTB订单发货箱子信息")
@ApiResource(name = "BTB订单发货箱子信息", path = "/lsBtbPackBox")
public class LsBtbPackBoxController {

    @Autowired
    private ILsBtbPackBoxService service;

}
