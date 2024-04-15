package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import com.tadpole.cloud.supplyChain.modular.logistics.service.ILogisticsSettlementRecordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ty
 * @description: 物流实际结算记录
 * @date: 2022/11/14
 */
@RestController
@ApiResource(name = "物流实际结算记录", path = "/logisticsSettlementRecord")
@Api(tags = "物流实际结算记录")
public class LogisticsSettlementRecordController {

    @Autowired
    private ILogisticsSettlementRecordService logisticsSettlementRecordService;

}

