package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.SettlementDetailUsdParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SettlementDetailUsdResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SubsidySummaryResult;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.FinanceReportTypes;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.SubsidySummaryCheckStatus;
import com.tadpole.cloud.platformSettlement.modular.finance.service.ISettlementDetailUsdService;
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
* 结算单明细(美金) 前端控制器
* </p>
*
* @author gal
* @since 2021-12-24
*/
@RestController
@ApiResource(name = "AZ结算单明细(美金)", path = "/settlementDetailUsd")
@Api(tags = "AZ结算单明细(美金)")
public class SettlementDetailUsdController {

    @Autowired
    private ISettlementDetailUsdService service;

    @PostResource(name = "AZ结算单明细(美金)", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "AZ结算单明细(美金)", response = SettlementDetailUsdResult.class)
    @BusinessLog(title = "AZ结算单明细(美金)-AZ结算单明细(美金)列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody SettlementDetailUsdParam param) {
        PageResult<SettlementDetailUsdResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "结算单明细确认", path = "/confirm",requiredPermission = false)
    @ApiOperation(value = "结算单明细确认", response = SettlementDetailUsdResult.class)
    @BusinessLog(title = "AZ结算单明细(美金)-结算单明细确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirm(SettlementDetailUsdParam param) throws Exception {
        return service.confirm(param);
    }

    @PostResource(name = "结算单明细反审核", path = "/unconfirm",requiredPermission = false)
    @ApiOperation(value = "结算单明细反审核", response = SettlementDetailUsdResult.class)
    @BusinessLog(title = "AZ结算单明细(美金)-结算单明细反审核",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData unconfirm(@RequestBody SettlementDetailUsdParam param) throws Exception {
        return service.unconfirm(param);
    }

    @PostResource(name = "删除下游分摊表", path = "/deleteAlloc",requiredPermission = false)
    @ApiOperation(value = "删除下游分摊表", response = SettlementDetailUsdResult.class)
    @BusinessLog(title = "AZ结算单明细(美金)-删除下游分摊表",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData deleteAlloc(@RequestBody SettlementDetailUsdParam param) throws Exception {
        return service.deleteAlloc(param);
    }

    @PostResource(name = "结算单明细批量确认", path = "/confirmBatch", requiredPermission = false)
    @ApiOperation(value = "结算单明细批量确认", response = SettlementDetailUsdResult.class)
    @BusinessLog(title = "AZ结算单明细(美金)-结算单明细批量确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirmBatch(@RequestBody SettlementDetailUsdParam param) throws Exception {
        ResponseData rd =service.confirmBatch(param);
        return rd;
    }

    @PostResource(name = "获取汇总金额", path = "/getQuantity", requiredPermission = false)
    @ApiOperation(value = "获取汇总金额", response = SettlementDetailUsdResult.class)
    @BusinessLog(title = "AZ结算单明细(美金)-获取汇总金额",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getQuantity(@RequestBody SettlementDetailUsdParam param) {
        SettlementDetailUsdResult pageBySpec=service.getQuantity(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "重刷汇率", path = "/afreshExchangeRate", requiredPermission = false)
    @ApiOperation(value = "重刷汇率", response = SettlementDetailUsdResult.class)
    @BusinessLog(title = "AZ结算单明细(美金)-重刷汇率",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData afreshExchangeRate(@RequestBody SettlementDetailUsdParam param) throws Exception {
        return service.afreshExchangeRate(param);
    }

    @PostResource(name = "批量刷汇率", path = "/refreshExchangeRate", requiredPermission = false)
    @ApiOperation(value = "批量刷汇率", response = SettlementDetailUsdResult.class)
    @BusinessLog(title = "AZ结算单明细(美金)-批量刷汇率",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData refreshExchangeRate() throws Exception {
        service.refreshExchangeRate();
        return ResponseData.success();
    }

    @PostResource(name = "导出结算单明细(美金)", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出结算单明细(美金)")
    @BusinessLog(title = "AZ结算单明细(美金)-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportStatementIncomeList(@RequestBody SettlementDetailUsdParam param, HttpServletResponse response) throws IOException {
        List<SettlementDetailUsdResult> pageBySpec = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("导出结算单明细(美金).xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), SettlementDetailUsdResult.class).sheet("导出结算单明细(美金)").doWrite(pageBySpec);

    }

    @GetResource(name = "确认状态下拉", path = "/status",requiredPermission = false)
    @ApiOperation(value = "确认状态下拉", response = SubsidySummaryResult.class)
    public ResponseData status() throws Exception {

        return ResponseData.success(SubsidySummaryCheckStatus.getEnumList());
    }

    @GetResource(name = "报告类型下拉", path = "/types",requiredPermission = false)
    @ApiOperation(value = "报告类型下拉", response = SubsidySummaryResult.class)
    public ResponseData types() throws Exception {

        return ResponseData.success(FinanceReportTypes.getEnumList());
    }

}
