package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementReportParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.StatementIncomeResult;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.SettlementReportStatus;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISettlementReportService;
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
* 结算报告 前端控制器
* </p>
*
* @author gal
* @since 2021-12-24
*/
@RestController
@ApiResource(name = "结算报告", path = "/settlementReport")
@Api(tags = "结算报告")
public class SettlementReportController {

    @Autowired
    private ISettlementReportService service;

    @PostResource(name = "结算报告列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "结算报告列表", response = SettlementReportResult.class)
    @BusinessLog(title = "结算报告-结算报告列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody SettlementReportParam param) {
        PageResult<SettlementReportResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "结算报告确认", path = "/confirm",requiredPermission = false)
    @ApiOperation(value = "结算报告确认", response = SettlementReportResult.class)
    @BusinessLog(title = "结算报告-结算报告确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirm(SettlementReportParam param) throws Exception {
        service.confirm(param);
        return ResponseData.success();
    }

    @PostResource(name = "结算报告批量确认", path = "/confirmBatch", requiredPermission = false)
    @ApiOperation(value = "结算报告批量确认", response = SettlementReportResult.class)
    @BusinessLog(title = "结算报告-结算报告批量确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirmBatch(@RequestBody SettlementReportParam param) throws Exception {
        ResponseData rd =service.confirmBatch(param);
        return rd;
    }
    @PostResource(name = "导出结算报告", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出结算报告")
    @BusinessLog(title = "结算报告-导出结算报告",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody SettlementReportParam param, HttpServletResponse response) throws IOException {
        List<SettlementReportResult> pageBySpec = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("结算报告.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), SettlementReportResult.class).sheet("结算报告").doWrite(pageBySpec);
    }

    @PostResource(name = "结算报告刷金额", path = "/refreshAmount", requiredPermission = false)
    @ApiOperation(value = "结算报告刷金额", response = SettlementReportResult.class)
    @BusinessLog(title = "结算报告-结算报告刷金额",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData refreshAmount(@RequestBody SettlementReportParam param) {
        return service.refreshAmount(param);
    }

    @GetResource(name = "刷退货数量", path = "/refreshReturnAmount", requiredPermission = false)
    @ApiOperation(value = "刷退货数量")
    @BusinessLog(title = "结算报告-刷退货数量",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData refreshReturnAmount() {
        try {
            return service.refreshReturnAmount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseData.success();
    }

    @GetResource(name = "确认状态下拉", path = "/status",requiredPermission = false)
    @ApiOperation(value = "确认状态下拉", response = SettlementReportResult.class)
    public ResponseData status() throws Exception {
        return ResponseData.success(SettlementReportStatus.getEnumList());
    }

    @PostResource(name = "数量", path = "/getQuantity",requiredPermission = false)
    @ApiOperation(value = "数量", response = SettlementReportResult.class)
    @BusinessLog(title = "结算报告-数量查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getQuantity(@RequestBody SettlementReportParam param) throws Exception {
        SettlementReportResult pageBySpec=service.getQuantity(param);
        return ResponseData.success(pageBySpec);
    }


    @PostResource(name = "店铺站点维度生成结算报告", path = "/shopSiteToReport", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "店铺站点维度生成结算报告", response = StatementIncomeResult.class)
    @BusinessLog(title = "AZ收入记录-店铺站点维度生成结算报告",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData shopSiteToReport(@RequestBody SettlementReportParam param) throws Exception {
        return service.shopSiteToReport(param);
    }
}
