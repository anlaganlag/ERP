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
import com.tadpole.cloud.platformSettlement.api.finance.model.params.ManualAllocationAdjustParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.ManualAllocationAdjustResult;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.ManualAllocationAdjustCheckStatus;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IManualAllocationAdjustService;
import com.tadpole.cloud.platformSettlement.modular.manage.consumer.BaseSelectConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* <p>
* 手动分摊调整表 前端控制器
* </p>
*
* @author gal
* @since 2021-12-24
*/
@RestController
@ApiResource(name = "手动分摊调整", path = "/manualAllocationAdjust")
@Api(tags = "手动分摊调整")
public class ManualAllocationAdjustController {

    @Autowired
    private IManualAllocationAdjustService service;
    @Autowired
    private BaseSelectConsumer baseSelectConsumer;

    @PostResource(name = "手动分摊调整", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "手动分摊调整", response = ManualAllocationAdjustResult.class)
    @BusinessLog(title = "手动分摊调整-手动分摊调整列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody ManualAllocationAdjustParam param) {
        PageResult<ManualAllocationAdjustResult> pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "手动分摊调整-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody ManualAllocationAdjustParam param, HttpServletResponse response) throws IOException {
        List<ManualAllocationAdjustResult> pageBySpec = service.queryList(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("手动分摊列表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), ManualAllocationAdjustResult.class).sheet("手动分摊列表").doWrite(pageBySpec);

    }

    @ParamValidator
    @PostResource(name = "手动分摊列导入Excel", path = "/upload", requiredPermission = false)
    @ApiOperation(value = "手动分摊列导入Excel")
    @BusinessLog(title = "手动分摊调整-手动分摊列导入Excel",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@RequestParam(value = "file", required = true) MultipartFile file) {
        List<String> departmentList= baseSelectConsumer.getDepartment();
        List<String> teamList= baseSelectConsumer.getTeam();
        return service.importExcel(file,departmentList,teamList);
    }

    @PostResource(name = "修改", path = "/edit",requiredPermission = false)
    @ApiOperation(value = "修改", response = ManualAllocationAdjustResult.class)
    @BusinessLog(title = "手动分摊调整-修改",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody ManualAllocationAdjustParam param) {
        service.edit(param);
        return ResponseData.success();
    }

    @GetResource(name = "确认", path = "/confirm",requiredPermission = false)
    @ApiOperation(value = "确认", response = ManualAllocationAdjustResult.class)
    @BusinessLog(title = "手动分摊调整-确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirm(ManualAllocationAdjustParam param) throws Exception {
        return service.confirm(param);
    }

    @GetResource(name = "删除", path = "/delete",requiredPermission = false)
    @ApiOperation(value = "删除", response = ManualAllocationAdjustResult.class)
    @BusinessLog(title = "手动分摊调整-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(ManualAllocationAdjustParam param) throws Exception {
        service.delete(param);
        return ResponseData.success();
    }

    @PostResource(name = "批量确认", path = "/confirmBatch",requiredPermission = false)
    @ApiOperation(value = "批量确认", response = ManualAllocationAdjustResult.class)
    @BusinessLog(title = "手动分摊调整-批量确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirmBatch(@RequestBody ManualAllocationAdjustParam param) throws Exception {
        return service.confirmBatch(param);
    }

    @PostResource(name = "批量删除", path = "/deleteBatch",requiredPermission = false)
    @ApiOperation(value = "批量删除", response = ManualAllocationAdjustResult.class)
    @BusinessLog(title = "手动分摊调整-批量删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteBatch(@RequestBody ManualAllocationAdjustParam param) throws Exception {
        service.deleteBatch(param);
        return ResponseData.success();
    }

    @GetResource(name = "确认状态下拉", path = "/status",requiredPermission = false)
    @ApiOperation(value = "确认状态下拉", response = ManualAllocationAdjustResult.class)
    public ResponseData status() throws Exception {
        return ResponseData.success(ManualAllocationAdjustCheckStatus.getEnumList());
    }

    /**
     * 导入模板下载
     * @param response
     */
    @GetResource(name = "模板下载", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("模板下载")
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/手动分摊调整导入模板.xlsx");
    }

    @PostResource(name = "获取汇总金额", path = "/getQuantity", requiredPermission = false)
    @ApiOperation(value = "获取汇总金额", response = ManualAllocationAdjustResult.class)
    @BusinessLog(title = "手动分摊调整-获取汇总金额",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getQuantity(@RequestBody ManualAllocationAdjustParam param) {
        ManualAllocationAdjustResult pageBySpec=service.getQuantity(param);
        return ResponseData.success(pageBySpec);
    }
}
