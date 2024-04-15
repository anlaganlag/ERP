package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILogisticsSettlementDetailService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ty
 * @description: 物流实际结算明细
 * @date: 2022/11/14
 */
@RestController
@ApiResource(name = "物流实际结算明细", path = "/logisticsSettlementDetail")
@Api(tags = "物流实际结算明细")
public class LogisticsSettlementDetailController {

    @Autowired
    private ILogisticsSettlementDetailService logisticsSettlementDetailService;
}
