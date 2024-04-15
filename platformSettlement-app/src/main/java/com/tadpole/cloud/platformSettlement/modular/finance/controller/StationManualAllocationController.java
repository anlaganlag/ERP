package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.entity.StationManualAllocation;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.StationManualAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.TotalStorageFeeParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.StationManualAllocationResult;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.StationManualAllocationStatus;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IStationManualAllocationService;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.BaseSelectConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
* <p>
* 站内手工分摊表 前端控制器
* </p>
*
* @author gal
* @since 2021-12-24
*/
@RestController
@ApiResource(name = "站内费用手工分摊", path = "/stationManualAllocation")
@Api(tags = "站内费用手工分摊")
public class StationManualAllocationController {

    @Autowired
    private IStationManualAllocationService service;
    @Autowired
    private BaseSelectConsumer baseSelectConsumer;

    @PostResource(name = "站内费用手工分摊", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "站内费用手工分摊", response = StationManualAllocationResult.class)
    @BusinessLog(title = "站内费用手工分摊-站内费用手工分摊列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody StationManualAllocationParam param) {
        PageResult<StationManualAllocationResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "站内费用手工分摊-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody StationManualAllocationParam param, HttpServletResponse response) throws IOException {
        List<StationManualAllocationResult> pageBySpec = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("导出站内费用手工分摊表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), StationManualAllocationResult.class).sheet("导出站内费用手工分摊表").doWrite(pageBySpec);
    }

    @ParamValidator
    @PostResource(name = "导入Excel", path = "/upload", requiredPermission = false)
    @ApiOperation(value = "导入Excel")
    @BusinessLog(title = "站内费用手工分摊-导入Excel",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@RequestParam(value = "file", required = true) MultipartFile file) {
        List<String> departmentList= baseSelectConsumer.getDepartment();
        List<String> teamList= baseSelectConsumer.getTeam();
        return service.importExcel(file,departmentList,teamList);
    }

    @GetResource(name = "修改", path = "/edit",requiredPermission = false)
    @ApiOperation(value = "修改", response = StationManualAllocationResult.class)
    @BusinessLog(title = "站内费用手工分摊-修改",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData editFiscalPeriod(StationManualAllocationParam param) {
        service.edit(param);
        return ResponseData.success();
    }

    @GetResource(name = "确认", path = "/confirm",requiredPermission = false)
    @ApiOperation(value = "确认", response = StationManualAllocationResult.class)
    @BusinessLog(title = "站内费用手工分摊-确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirm(StationManualAllocationParam param) throws Exception {
       return service.confirm(param);
    }

    @PostResource(name = "获取汇总金额", path = "/getQuantity", requiredPermission = false)
    @ApiOperation(value = "获取汇总金额", response = StationManualAllocationResult.class)
    @BusinessLog(title = "站内费用手工分摊-获取汇总金额",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getQuantity(@RequestBody StationManualAllocationParam param) {
        StationManualAllocationResult pageBySpec=service.getQuantity(param);
        return ResponseData.success(pageBySpec);
    }

    @GetResource(name = "删除", path = "/delete",requiredPermission = false)
    @ApiOperation(value = "删除", response = StationManualAllocationResult.class)
    @BusinessLog(title = "站内费用手工分摊-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(StationManualAllocationParam param) throws Exception {
        service.delete(param);
        return ResponseData.success();
    }

    @PostResource(name = "刷listings", path = "/fillListing",requiredPermission = false)
    @ApiOperation(value = "刷listings", response = StationManualAllocationResult.class)
    @BusinessLog(title = "站内费用手工分摊-刷listings",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData fillListing(@RequestBody StationManualAllocationParam param)  {
        service.fillListing(param);
        return ResponseData.success();
    }

    @PostResource(name = "刷销售品牌", path = "/fillSalesBrand",requiredPermission = false)
    @ApiOperation(value = "刷销售品牌", response = StationManualAllocationResult.class)
    @BusinessLog(title = "站内费用手工分摊-刷销售品牌",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData fillSalesBrand(@RequestBody StationManualAllocationParam param)  {
        service.fillSalesBrand(param);
        return ResponseData.success();
    }


    @PostResource(name = "刷fnskuListings", path = "/fnskuFillDestroyListing",requiredPermission = false)
    @ApiOperation(value = "刷fnskuListings", response = StationManualAllocationResult.class)
    @BusinessLog(title = "销毁移除-刷fnskuListings",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData fnskuFillDestroyListing(@RequestBody  StationManualAllocationParam param)  {
        service.fnskuFillDestroyListing(param);
        return ResponseData.success();
    }

    @PostResource(name = "批量确认", path = "/confirmBatch",requiredPermission = false)
    @ApiOperation(value = "批量确认", response = StationManualAllocationResult.class)
    @BusinessLog(title = "站内费用手工分摊-批量确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirmBatch(@RequestBody StationManualAllocationParam param) throws Exception {
        return service.confirmBatch(param);
    }

    @PostResource(name = "批量删除", path = "/deleteBatch",requiredPermission = false)
    @ApiOperation(value = "批量删除", response = StationManualAllocationResult.class)
    @BusinessLog(title = "站内费用手工分摊-批量删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteBatch(@RequestBody StationManualAllocationParam param) throws Exception {
        service.deleteBatch(param);
        return ResponseData.success();
    }

    @GetResource(name = "确认状态下拉", path = "/status",requiredPermission = false)
    @ApiOperation(value = "确认状态下拉", response = StationManualAllocationResult.class)
    public ResponseData status() throws Exception {
        return ResponseData.success(StationManualAllocationStatus.getEnumList());
    }


    @PostResource(name = "拉取仓储销毁移除费", path = "/pullStorageDisposeFee")
    @ApiOperation(value = "拉取仓储销毁移除费", response = StationManualAllocationResult.class)
    public ResponseData pullStorageDisposeFee(@RequestBody  StationManualAllocationParam param) throws Exception {
        return service.pullStorageDisposeFee(param);
    }



    /**
     * 导入模板下载
     * @param response
     */
    @GetResource(name = "模板下载", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("模板下载")
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/站内费用手工分摊导入模板.xlsx");
    }

    @PostResource(name = "更新分摊行状态", path = "/updateAllocLineStatus")
    @ApiOperation(value = "更新分摊行状态", response = StationManualAllocationResult.class)
    public ResponseData updateAllocLineStatus()  {
         service.updateAllocLineStatus();
         return ResponseData.success();
    }

    @PostResource(name = "合并分摊行", path = "/mergeAllocLine")
    @ApiOperation(value = "合并分摊行")
    @BusinessLog(title = "合并分摊行",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData mergeAllocLine(@RequestBody StationManualAllocationParam param) throws ParseException {
        return service.mergeAllocLine(param);
    }


}
