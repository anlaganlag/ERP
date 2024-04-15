package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILsK3PaymentApplyDetailService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 物流费K3付款申请单明细 前端控制器
 * </p>
 *
 * @author ty
 * @since 2023-12-05
 */
@RestController
@Api(tags = "物流费K3付款申请单明细")
@ApiResource(name = "物流费K3付款申请单明细", path = "/lsK3PaymentApplyDetail")
public class LsK3PaymentApplyDetailController {

    @Autowired
    private ILsK3PaymentApplyDetailService service;

}
