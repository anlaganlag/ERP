package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.entity.TbComShop;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ShopCurrencyParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.FinancialSiteResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ShopCurrencyResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IShopCurrencyService;
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
* 店铺报告币别 前端控制器
* </p>
*
* @author gal
* @since 2021-10-25
*/
@RestController
@ApiResource(name = "店铺报告币别管理", path = "/shopCurrency")
@Api(tags = "店铺报告币别管理")
public class ShopCurrencyController {

    @Autowired
    private IShopCurrencyService service;

    @PostResource(name = "店铺报告币别列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "店铺报告币别列表", response = FinancialSiteResult.class)
    @BusinessLog(title = "店铺报告币别管理-店铺报告币别列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody ShopCurrencyParam param) {
        PageResult<ShopCurrencyResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "导出店铺报告币别列表", path = "/exportShopCurrencyList", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出店铺报告币别列表")
    @BusinessLog(title = "店铺报告币别管理-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportFinancialSiteList(ShopCurrencyParam param, HttpServletResponse response) throws IOException {
        List<ShopCurrencyResult> pageBySpec = service.exportShopCurrencyList(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("导出店铺报告币别列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), ShopCurrencyResult.class).sheet("导出店铺报告币别列表").doWrite(pageBySpec);
    }

    @GetResource(name = "店铺报告币别自动分析", path = "/autoAnalysis", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "店铺报告币别自动分析", response = FinancialSiteResult.class)
    @BusinessLog(title = "店铺报告币别管理-店铺报告币别自动分析",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData autoAnalysis() {
        TbComShop shop = new TbComShop();
        List<TbComShop> list = service.findShopEbms(shop);
        service.autoAnalysis(list);
        service.updateOriCurrency();

        return ResponseData.success();
    }

    @GetResource(name = "账号下拉", path = "/getShop", requiredPermission = false)
    @ApiOperation(value = "账号下拉", response = ShopCurrencyResult.class)
    public ResponseData getShop() {
        List<ShopCurrencyResult> list = service.getShop();
        return ResponseData.success(list);
    }

    @GetResource(name = "财务站点信息修改", path = "/updateCurrency", requiredPermission = false)
    @ApiOperation(value = "财务站点信息修改", response = ShopCurrencyResult.class)
    @BusinessLog(title = "店铺报告币别管理-财务站点信息修改",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData updateCurrency(ShopCurrencyParam param) {
        service.updateCurrency(param);
        return ResponseData.success();
    }

    @GetResource(name = "获取收款币", path = "/getCurrency", requiredPermission = false)
    @ApiOperation(value = "获取收款币", response = ShopCurrencyResult.class)
    public ResponseData getCurrency(ShopCurrencyParam param) {
        List<ShopCurrencyResult> list =   service.getCurrency(param);
        return ResponseData.success(list);
    }

    @GetResource(name = "获取店铺银行账号", path = "/getBankAccount", requiredPermission = false)
    @ApiOperation(value = "获取店铺银行账号", response = ShopCurrencyResult.class)
    public ResponseData getBankAccount(TbComShop shop) {
        List<TbComShop> list = service.findShopEbms(shop);
        return ResponseData.success(list);
    }
}
