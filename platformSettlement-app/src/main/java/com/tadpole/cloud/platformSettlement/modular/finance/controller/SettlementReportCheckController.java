package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementReportCheckParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementReportCheckResult;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISettlementReportCheckService;
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
 * AZ结算报告审核 前端控制器
 * </p>
 *
 * @author gal
 * @since 2021-10-25
 */
@RestController
@ApiResource(name = "AZ结算报告审核", path = "/settlementReportCheck")
@Api(tags = "AZ结算报告审核")
public class SettlementReportCheckController {

    @Autowired
    private ISettlementReportCheckService service;

    @PostResource(name = "AZ结算报告审核列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "AZ结算报告审核", response = SettlementReportCheckResult.class)
    @BusinessLog(title = "AZ结算报告审核-AZ结算报告审核列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody SettlementReportCheckParam param) {
        PageResult<SettlementReportCheckResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "AZ结算报告审核", path = "/verify", requiredPermission = false)
    @ApiOperation(value = "AZ结算报告审核", response = SettlementReportCheckResult.class)
    @BusinessLog(title = "AZ结算报告审核-AZ结算报告审核",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData verify(SettlementReportCheckParam param) throws Exception {
        service.verify(param);
        return ResponseData.success();
    }

    @PostResource(name = "AZ结算报告批量审核", path = "/verifyList", requiredPermission = false)
    @ApiOperation(value = "AZ结算报告批量审核", response = SettlementReportCheckResult.class)
    @BusinessLog(title = "AZ结算报告审核-AZ结算报告批量审核",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData verifyList(@RequestBody List<SettlementReportCheckParam> params) throws Exception {
        service.verifyList(params);
        return ResponseData.success();
    }

    @GetResource(name = "修改站点", path = "/editSite", requiredPermission = false)
    @ApiOperation(value = "修改站点", response = SettlementReportCheckResult.class)
    @BusinessLog(title = "AZ结算报告审核-修改站点",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData editSite(SettlementReportCheckParam param) {
        return service.editSite(param);
    }

    @GetResource(name = "修改单据类型", path = "/editType", requiredPermission = false)
    @ApiOperation(value = "修改单据类型", response = SettlementReportCheckResult.class)
    @BusinessLog(title = "AZ结算报告审核-修改单据类型",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData editType(SettlementReportCheckParam param) {
        service.editType(param);
        return ResponseData.success();
    }

    @GetResource(name = "修改收款币种", path = "/editProceedsCurrency", requiredPermission = false)
    @ApiOperation(value = "修改收款币种", response = SettlementReportCheckResult.class)
    @BusinessLog(title = "AZ结算报告审核-修改收款币种",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData editProceedsCurrency(SettlementReportCheckParam param) {
        service.editProceedsCurrency(param);
        return ResponseData.success();
    }

    @GetResource(name = "修改收款金额", path = "/editAmount", requiredPermission = false)
    @ApiOperation(value = "修改收款金额", response = SettlementReportCheckResult.class)
    @BusinessLog(title = "AZ结算报告审核-修改收款金额",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData editAmount(SettlementReportCheckParam param) {
        service.editAmount(param);
        return ResponseData.success();
    }

    @GetResource(name = "AZ结算报告驳回", path = "/reject", requiredPermission = false)
    @ApiOperation(value = "AZ结算报告驳回", response = SettlementReportCheckResult.class)
    @BusinessLog(title = "AZ结算报告审核-AZ结算报告驳回",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData reject(SettlementReportCheckParam param) throws Exception {
        service.reject(param);
        return ResponseData.success();
    }

    @GetResource(name = "汇款银行下拉", path = "/bankList", requiredPermission = false)
    @ApiOperation(value = "汇款银行下拉", response = SettlementReportCheckResult.class)
    public ResponseData bankList() {
        List<SettlementReportCheckResult> pageBySpec = service.bankList();
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "导出AZ结算报告审核列表", path = "/exportSettlementReportCheck", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出AZ结算报告审核列表")
    @BusinessLog(title = "AZ结算报告审核-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportSettlementReportCheckList(SettlementReportCheckParam param, HttpServletResponse response) throws IOException {
        List<SettlementReportCheckResult> pageBySpec = service.exportSettlementReportCheckList(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("导出AZ结算报告审核列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), SettlementReportCheckResult.class).sheet("导出AZ结算报告审核列表").doWrite(pageBySpec);
    }
}
