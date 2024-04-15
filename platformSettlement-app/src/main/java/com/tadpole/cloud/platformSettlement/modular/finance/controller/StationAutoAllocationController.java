package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.api.base.model.params.RpMaterialParam;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.entity.StationAutoAllocation;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StationAutoAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.StationAutoAllocationResult;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.SubsidySummaryResult;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.FinanceReportTypes;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.SubsidySummaryCheckStatus;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IStationAutoAllocationService;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.RpMaterialConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* <p>
* 站内自动分摊表 前端控制器
* </p>
*
* @author gal
* @since 2021-12-24
*/
@RestController
@ApiResource(name = "站内费用自动分摊", path = "/stationAutoAllocation")
@Api(tags = "站内费用自动分摊")
public class StationAutoAllocationController {

    @Autowired
    private IStationAutoAllocationService service;
    @Autowired
    private RpMaterialConsumer rpMaterialConsumer;

    @PostResource(name = "站内费用自动分摊", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "站内费用自动分摊", response = StationAutoAllocationResult.class)
    @BusinessLog(title = "站内费用自动分摊-站内费用自动分摊列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody StationAutoAllocationParam param) {
        PageResult<StationAutoAllocationResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "站内费用自动分摊确认", path = "/confirm",requiredPermission = false)
    @ApiOperation(value = "站内费用自动分摊确认", response = StationAutoAllocationResult.class)
    @BusinessLog(title = "站内费用自动分摊-站内费用自动分摊确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirm(StationAutoAllocationParam param) throws Exception {
        return service.confirm(param);
    }

    @PostResource(name = "站内费用手动分摊确认", path = "/confirmManual",requiredPermission = false)
    @ApiOperation(value = "站内费用手动分摊确认", response = StationAutoAllocationResult.class)
    @BusinessLog(title = "站内费用自动分摊-站内费用手动分摊确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirmManual(@RequestBody List<StationAutoAllocation> params) throws Exception {
        service.confirmManual(params);
        return ResponseData.success();
    }

    @PostResource(name = "站内费用自动分摊批量确认", path = "/confirmBatch", requiredPermission = false)
    @ApiOperation(value = "站内费用自动分摊批量确认", response = StationAutoAllocationResult.class)
    @BusinessLog(title = "站内费用自动分摊-站内费用自动分摊批量确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirmBatch(@RequestBody StationAutoAllocationParam param) throws Exception {
        ResponseData rd =service.confirmBatch(param);
        return rd;
    }

    @PostResource(name = "获取汇总金额", path = "/getQuantity", requiredPermission = false)
    @ApiOperation(value = "获取汇总金额", response = StationAutoAllocationResult.class)
    @BusinessLog(title = "站内费用自动分摊-获取汇总金额",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getQuantity(@RequestBody StationAutoAllocationParam param) {
        return ResponseData.success(service.getQuantity(param));
    }

    @PostResource(name = "导出站内费用自动分摊列表", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出站内费用自动分摊列表")
    @BusinessLog(title = "站内费用自动分摊-导出站内费用自动分摊列表",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody StationAutoAllocationParam param, HttpServletResponse response) throws IOException {
        List<StationAutoAllocationResult> pageBySpec = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("导出站内费用自动分摊列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), StationAutoAllocationResult.class).sheet("导出站内费用自动分摊列表").doWrite(pageBySpec);
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

    @GetResource(name = "运营大类", path = "/ProductType", requiredPermission = false)
    @ApiOperation(value = "运营大类")
    public ResponseData getProductTypeSelect(@RequestParam(value = "category", required = false, defaultValue = "") String category) {
        RpMaterialParam param = new RpMaterialParam();
        param.setCategory(category);
        return ResponseData.success(rpMaterialConsumer.getProductTypeSelect(param));
    }

}
