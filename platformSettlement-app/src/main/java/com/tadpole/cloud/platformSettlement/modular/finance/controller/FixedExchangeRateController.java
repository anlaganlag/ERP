package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.platformSettlement.api.finance.entity.FixedExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.FixedExchangeRateParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.FixedExchangeRateResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IFixedExchangeRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* <p>
* ERP固定汇率 前端控制器
* </p>
*
* @author gal
* @since 2021-10-25
*/
@RestController
@ApiResource(name = "ERP固定汇率", path = "/fixedExchangeRate")
@Api(tags = "ERP固定汇率")
public class FixedExchangeRateController {

    @Autowired
    private IFixedExchangeRateService service;

    @PostResource(name = "ERP固定汇率列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "ERP固定汇率列表", response = FixedExchangeRateResult.class)
    @BusinessLog(title = "ERP固定汇率-ERP固定汇率列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody FixedExchangeRateParam param) {
        PageResult<FixedExchangeRateResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "ERP固定汇率", path = "/queryErp",requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "ERP固定汇率", response = FixedExchangeRate.class)
    @BusinessLog(title = "ERP固定汇率-获取ERP固定汇率",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData queryErp() {
        List<FixedExchangeRate> list = service.queryErp();
        service.insertErpRate(list);
        return ResponseData.success();
    }


    @GetResource(name = "原币下拉列表", path = "/originalCurrencyList",requiredPermission = false)
    @ApiOperation(value = "原币下拉列表", response = FixedExchangeRate.class)
    public ResponseData originalCurrencyList() {
        List<FixedExchangeRate> list = service.originalCurrencyList();
        return ResponseData.success(list);
    }
}
