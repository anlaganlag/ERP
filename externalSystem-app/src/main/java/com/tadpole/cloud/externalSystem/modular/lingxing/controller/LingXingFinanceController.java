package com.tadpole.cloud.externalSystem.modular.lingxing.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.finance.ProfitMskuReq;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LingXingFinanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ty
 * @description: 财务Controller类
 * @date: 2022/4/25
 */
@RestController
@ApiResource(name = "财务Controller类", path = "/lingXingFinance")
@Api(tags = "财务Controller类")
public class LingXingFinanceController {

    @Autowired
    private LingXingFinanceService lingXingFinanceService;

    @PostResource(name = "查询利润报表 - MSKU", path = "/profitMsku", requiredPermission = false, requiredLogin = false)
    @ApiOperation("查询利润报表 - MSKU")
    public LingXingBaseRespData profitMsku(@RequestBody ProfitMskuReq req) throws Exception {
        return lingXingFinanceService.profitMsku(req);
    }
}
