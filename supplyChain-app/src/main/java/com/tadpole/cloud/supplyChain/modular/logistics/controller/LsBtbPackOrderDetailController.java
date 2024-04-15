package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsBtbPackOrderDetailService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * BTB订单发货明细信息 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-12-19
 */
@RestController
@Api(tags = "BTB订单发货明细信息")
@ApiResource(name = "BTB订单发货明细信息", path = "/lsBtbPackOrderDetail")
public class LsBtbPackOrderDetailController {

    @Autowired
    private ILsBtbPackOrderDetailService service;

}
