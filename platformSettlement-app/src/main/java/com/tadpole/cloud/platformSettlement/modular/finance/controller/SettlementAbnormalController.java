package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISettlementAbnormalService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  AZ结算异常前端控制器
 * </p>
 *
 * @author ty
 * @since 2022-07-28
 */
@RestController
@RequestMapping("/settlementAbnormal")
public class SettlementAbnormalController {

    @Autowired
    private ISettlementAbnormalService service;

}
