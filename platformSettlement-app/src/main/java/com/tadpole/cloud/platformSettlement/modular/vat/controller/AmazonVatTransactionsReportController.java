package com.tadpole.cloud.platformSettlement.modular.vat.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IShopCurrencyService;
import com.tadpole.cloud.platformSettlement.modular.vat.enums.ReportCheckStatus;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.AmazonVatTransactionsReportParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.AmazonVatTransactionsReportResult;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IAmazonVatTransactionsReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <p>
 * 源报告表 前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-08-06
 */
@RestController
@ApiResource(name = "VAT源报告", path = "/vatReport")
@Api(tags = "VAT源报告")
public class AmazonVatTransactionsReportController {

    @Autowired
    private IAmazonVatTransactionsReportService service;
    @Autowired
    private IShopCurrencyService shopService;

    @PostResource(name = "源报告展示", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "源报告展示" ,response = AmazonVatTransactionsReportResult.class)
    @BusinessLog(title = "VAT源报告-源报告展示查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody AmazonVatTransactionsReportParam param) {
        return service.queryListPage(param);
    }

    @PostResource(name = "合计", path = "/querySum", menuFlag = true)
    @ApiOperation(value = "合计" ,response = AmazonVatTransactionsReportResult.class)
    @BusinessLog(title = "VAT源报告-合计查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData querySum(@RequestBody AmazonVatTransactionsReportParam param) {
        return service.querySum(param);
    }

    @GetResource(name = "获取报告", path = "/getReport")
    @ApiOperation(value = "获取报告" ,response = AmazonVatTransactionsReportResult.class)
    @BusinessLog(title = "VAT源报告-获取报告",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData getReport(AmazonVatTransactionsReportParam param) {
        return ResponseData.success();
    }

    @PostResource(name = "批量审核", path = "/verifyBatch")
    @ApiOperation(value = "批量审核" ,response = AmazonVatTransactionsReportResult.class)
    @BusinessLog(title = "VAT源报告-批量审核",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData verifyBatch(@RequestBody AmazonVatTransactionsReportParam param) {
        return service.verifyBatch(param);
    }

    @PostResource(name = "批量作废", path = "/cancelBatch")
    @ApiOperation(value = "批量作废" ,response = AmazonVatTransactionsReportResult.class)
    @BusinessLog(title = "VAT源报告-批量作废",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData cancelBatch(@RequestBody AmazonVatTransactionsReportParam param) {
        return service.cancelBatch(param);
    }

    @PostResource(name = "上传报告", path = "/upload")
    @ApiOperation(value = "上传报告" ,response = AmazonVatTransactionsReportResult.class)
    @BusinessLog(title = "VAT源报告-上传报告",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData upload(@RequestParam("file") MultipartFile file, AmazonVatTransactionsReportParam param) {
        return ResponseData.success();
    }

    @GetResource(name = "生成Sales明细", path = "/generateSalesDetail")
    @ApiOperation(value = "生成Sales明细" ,response = AmazonVatTransactionsReportResult.class)
    @BusinessLog(title = "VAT源报告-生成Sales明细",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData generateSalesDetail(AmazonVatTransactionsReportParam param) {
        return service.generateSalesDetail(param);
    }

    @PostResource(name = "导出", path = "/export", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "VAT源报告-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody AmazonVatTransactionsReportParam param, HttpServletResponse response){
        List<AmazonVatTransactionsReportResult> resultList = service.export(param);
        try {
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String("VAT源报告展示.xlsx".getBytes(StandardCharsets.UTF_8),"ISO8859-1"));
            EasyExcel.write(response.getOutputStream(), AmazonVatTransactionsReportResult.class).sheet("VAT源报告展示").doWrite(resultList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetResource(name = "获取编辑数据", path = "/getEditData")
    @ApiOperation(value = "获取编辑数据" ,response = AmazonVatTransactionsReportResult.class)
    @BusinessLog(title = "VAT源报告-获取编辑数据",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getEditData(AmazonVatTransactionsReportParam param) {
        return service.getEditData(param);
    }

    @GetResource(name = "编辑", path = "/edit")
    @ApiOperation(value = "编辑" ,response = AmazonVatTransactionsReportResult.class)
    @BusinessLog(title = "VAT源报告-编辑",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(AmazonVatTransactionsReportParam param) {
        return service.edit(param);
    }

    @GetResource(name = "自动审核", path = "/autoVerify")
    @ApiOperation(value = "自动审核" ,response = AmazonVatTransactionsReportResult.class)
    @BusinessLog(title = "VAT源报告-自动审核",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData autoVerify(AmazonVatTransactionsReportParam param) {
        return service.autoVerify(param);
    }

    @GetResource(name = "审核状态下拉", path = "/verifyStatus",requiredPermission = false)
    @ApiOperation(value = "审核状态下拉", response = AmazonVatTransactionsReportResult.class)
    public ResponseData verifyStatus() throws Exception {
        return ResponseData.success(ReportCheckStatus.getEnumList());
    }
}
