package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.entity.SpotExchangeRate;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SpotExchangeRateParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SpotExchangeRateResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISpotExchangeRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* <p>
* ERP即期汇率 前端控制器
* </p>
*
* @author gal
* @since 2021-12-24
*/
@RestController
@ApiResource(name = "ERP即期汇率", path = "/spotExchangeRate")
@Api(tags = "ERP即期汇率")
public class SpotExchangeRateController {

    @Autowired
    private ISpotExchangeRateService service;

    @PostResource(name = "ERP即期汇率列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "ERP即期汇率列表", response = SpotExchangeRateResult.class)
    @BusinessLog(title = "ERP即期汇率-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody SpotExchangeRateParam param) {
        PageResult<SpotExchangeRateResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "ERP即期汇率", path = "/queryErp",requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "ERP即期汇率", response = SpotExchangeRate.class)
    @BusinessLog(title = "ERP即期汇率-获取ERP即期汇率",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData queryErp() {
        List<SpotExchangeRate> list = service.queryErp();
        service.insertErpRate(list);
        return ResponseData.success();
    }

    @GetResource(name = "原币下拉列表", path = "/originalCurrencyList",requiredPermission = false)
    @ApiOperation(value = "原币下拉列表", response = SpotExchangeRate.class)
    public ResponseData originalCurrencyList() {
        List<SpotExchangeRate> list = service.originalCurrencyList();
        return ResponseData.success(list);
    }

    @PostResource(name = "导出即期汇率列表", path = "/exportSpotExchangeRateList", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出即期汇率列表")
    @BusinessLog(title = "ERP即期汇率-导出即期汇率列表",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportFinancialSiteList(@RequestBody SpotExchangeRateParam param, HttpServletResponse response) throws IOException {
        List<SpotExchangeRateResult> pageBySpec = service.exportShopCurrencyList(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("导出即期汇率列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), SpotExchangeRateResult.class).sheet("导出即期汇率列表").doWrite(pageBySpec);
    }
}
