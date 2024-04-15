package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsBtbPackBoxDetailService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  BTB订单发货箱子明细信息前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-11-10
 */
@RestController
@Api(tags = "BTB订单发货箱子明细信息")
@ApiResource(name = "BTB订单发货箱子明细信息", path = "/lsBtbPackBoxDetail")
public class LsBtbPackBoxDetailController {

    @Autowired
    private ILsBtbPackBoxDetailService service;

}
