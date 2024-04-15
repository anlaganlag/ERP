package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.NoStationAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.NoStationAllocationResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SubsidySummaryResult;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.FinanceReportTypes;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.SubsidySummaryCheckStatus;
import com.tadpole.cloud.platformSettlement.modular.finance.service.INoStationAllocationService;
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
* 无需分摊站内费用表 前端控制器
* </p>
*
* @author gal
* @since 2021-12-24
*/
@RestController
@ApiResource(name = "无需分摊站内费用", path = "/nostationAllocation")
@Api(tags = "无需分摊站内费用")
public class NoStationAllocationController {

    @Autowired
    private INoStationAllocationService service;

    @PostResource(name = "无需分摊站内费用", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "无需分摊站内费用", response = NoStationAllocationResult.class)
    @BusinessLog(title = "无需分摊站内费用-无需分摊站内费用列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody NoStationAllocationParam param) {
        PageResult<NoStationAllocationResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "无需分摊站内费用确认", path = "/confirm",requiredPermission = false)
    @ApiOperation(value = "无需分摊站内费用确认", response = NoStationAllocationResult.class)
    @BusinessLog(title = "无需分摊站内费用-无需分摊站内费用确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirm(NoStationAllocationParam param) throws Exception {
        service.confirm(param);
        return ResponseData.success();
    }

    @PostResource(name = "无需分摊站内费用批量确认", path = "/confirmBatch", requiredPermission = false)
    @ApiOperation(value = "无需分摊站内费用批量确认", response = NoStationAllocationResult.class)
    @BusinessLog(title = "无需分摊站内费用-无需分摊站内费用批量确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirmBatch(@RequestBody NoStationAllocationParam param) throws Exception {
        ResponseData rd =service.confirmBatch(param);
        return rd;
    }

    @GetResource(name = "重刷成本", path = "/afreshCost",requiredPermission = false)
    @ApiOperation(value = "重刷成本", response = NoStationAllocationResult.class)
    @BusinessLog(title = "无需分摊站内费用-重刷成本",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData afreshCost(NoStationAllocationParam param) throws Exception {
        return service.afreshCost(param);
    }

    @PostResource(name = "批量刷成本", path = "/refreshCost", requiredPermission = false)
    @ApiOperation(value = "批量刷成本", response = NoStationAllocationResult.class)
    @BusinessLog(title = "无需分摊站内费用-批量刷成本",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData refreshCost() throws Exception {
        return service.refreshCost();
    }

    @PostResource(name = "获取汇总金额", path = "/getQuantity", requiredPermission = false)
    @ApiOperation(value = "获取汇总金额", response = NoStationAllocationResult.class)
    @BusinessLog(title = "无需分摊站内费用-获取汇总金额",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getQuantity(@RequestBody NoStationAllocationParam param) {
        NoStationAllocationResult pageBySpec=service.getQuantity(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "导出无需分摊站内费用", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出无需分摊站内费用")
    @BusinessLog(title = "无需分摊站内费用-导出无需分摊站内费用",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody NoStationAllocationParam param, HttpServletResponse response) throws IOException {
        List<NoStationAllocationResult> pageBySpec = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("导出无需分摊站内费用.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), NoStationAllocationResult.class).sheet("导出无需分摊站内费用").doWrite(pageBySpec);
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
