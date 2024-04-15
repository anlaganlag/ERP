package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport.TransactionReq;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.LingxingDatarangeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.FinancialSiteResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.LingxingDatarangeResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ShopCurrencyResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ILingxingDatarangeService;
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
*  前端控制器
* </p>
*
* @author gal
* @since 2022-04-28
*/
@RestController
@ApiResource(name = "daterange记录(每日)", path = "/lingxingDaterange")
@Api(tags = "daterange记录(每日)")
public class LingxingDatarangeController {

    @Autowired
    private ILingxingDatarangeService service;

    @PostResource(name = "daterange记录(每日)列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "daterange记录(每日)列表", response = LingxingDatarangeResult.class)
    @BusinessLog(title = "daterange记录(每日)-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody LingxingDatarangeParam param) {
        PageResult<LingxingDatarangeResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "AZ报告记录生成校验", path = "/verifyReportRecords", requiredPermission = false)
    @ApiOperation(value = "AZ报告记录生成校验", response = LingxingDatarangeResult.class)
    @BusinessLog(title = "daterange记录(每日)-AZ报告记录生成校验",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData verifyReportRecords(@RequestBody LingxingDatarangeParam param) {
        return service.verifyReportRecords(param);
    }

    @PostResource(name = "生成AZ报告记录", path = "/generateReportRecords", requiredPermission = false)
    @ApiOperation(value = "生成AZ报告记录", response = LingxingDatarangeResult.class)
    @BusinessLog(title = "daterange记录(每日)-生成AZ报告记录",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData generateReportRecords(@RequestBody LingxingDatarangeParam param) {
         String bankAccount=service.getBankAccount(param);
         return service.generateReportRecords(param,bankAccount);
    }

    @PostResource(name = "获取领星交易明细", path = "/generateDateRangeRecords", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "获取领星交易明细")
    @BusinessLog(title = "daterange记录(每日)-获取领星交易明细",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData generateDateRangeRecords(@RequestBody TransactionReq param) throws Exception{
        return service.generateDateRangeRecords(param);
    }

    @PostResource(name = "文件导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "文件导出")
    @BusinessLog(title = "daterange记录(每日)-文件导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody LingxingDatarangeParam param, HttpServletResponse response) throws IOException {
        List<LingxingDatarangeResult> list = service.list(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("Date Range记录(每日)列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), LingxingDatarangeResult.class).sheet("Date Range记录(每日)列表").doWrite(list);
        return ResponseData.success();
    }

    @GetResource(name = "平台下拉", path = "/getPlatform", requiredPermission = false)
    @ApiOperation(value = "平台下拉", response = FinancialSiteResult.class)
    public ResponseData getPlatform() {
        List<FinancialSiteResult> list = service.getPlatform();
        return ResponseData.success(list);
    }

    @GetResource(name = "账号下拉", path = "/getShop", requiredPermission = false)
    @ApiOperation(value = "账号下拉", response = ShopCurrencyResult.class)
    public ResponseData getShop() {
        List<ShopCurrencyResult> list = service.getShop();
        return ResponseData.success(list);
    }

    @GetResource(name = "站点下拉", path = "/getSite", requiredPermission = false)
    @ApiOperation(value = "站点下拉", response = FinancialSiteResult.class)
    public ResponseData getSite() {
        List<FinancialSiteResult> list = service.getSite();
        return ResponseData.success(list);
    }

    @GetResource(name = "结算ID下拉", path = "/getSettlementIdSelect", requiredPermission = false)
    @ApiOperation(value = "结算ID下拉", response = LingxingDatarangeResult.class)
    public ResponseData getSettlementIdSelect(LingxingDatarangeParam param) { return ResponseData.success(service.getSettlementIdSelect(param)); }
}
