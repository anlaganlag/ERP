package com.tadpole.cloud.platformSettlement.modular.finance.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.util.ExcelUtils;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.OutStationAllocationParam;
import com.tadpole.cloud.platformSettlement.api.finance.model.result.OutStationAllocationResult;
import com.tadpole.cloud.platformSettlement.modular.finance.enums.OutStationAllocationCheckStatus;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IOutStationAllocationService;
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
* 站外分摊汇总表 前端控制器
* </p>
*
* @author gal
* @since 2021-12-24
*/
@RestController
@ApiResource(name = "站外分摊汇总", path = "/outStationAllocation")
@Api(tags = "站外分摊汇总")
public class OutStationAllocationController {

    @Autowired
    private IOutStationAllocationService service;
    @Autowired
    private BaseSelectConsumer baseSelectConsumer;

    @PostResource(name = "站外分摊汇总列表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "站外分摊汇总列表", response = OutStationAllocationResult.class)
    @BusinessLog(title = "站外分摊汇总-站外分摊汇总列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody OutStationAllocationParam param) {
        return ResponseData.success(service.findPageBySpec(param));
    }

    @PostResource(name = "导出", path = "/export", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "导出")
    @BusinessLog(title = "站外分摊汇总-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void export(@RequestBody OutStationAllocationParam param, HttpServletResponse response) throws IOException {
        List<OutStationAllocationResult> pageBySpec = service.queryList(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("站外分摊汇总表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), OutStationAllocationResult.class).sheet("站外分摊汇总表").doWrite(pageBySpec);
    }

    @PostResource(name = "获取数量", path = "/getQuantity", requiredPermission = false)
    @ApiOperation(value = "获取数量")
    @BusinessLog(title = "站外分摊汇总-获取数量",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData getQuantity(@RequestBody OutStationAllocationParam param) {
        return ResponseData.success(service.getQuantity(param));
    }

    @ParamValidator
    @PostResource(name = "站外分摊汇总导入Excel", path = "/upload", requiredPermission = false)
    @ApiOperation(value = "站外分摊汇总导入Excel")
    @BusinessLog(title = "站外分摊汇总-站外分摊汇总导入Excel",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@RequestParam(value = "file", required = true) MultipartFile file) {
        List<String> departmentList= baseSelectConsumer.getDepartment();
        List<String> teamList= baseSelectConsumer.getTeam();
        return service.importExcel(file,departmentList,teamList);
    }

    @GetResource(name = "修改", path = "/edit",requiredPermission = false)
    @ApiOperation(value = "修改", response = OutStationAllocationResult.class)
    @BusinessLog(title = "站外分摊汇总-修改",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(OutStationAllocationParam param) {
        return service.edit(param);
    }

    @GetResource(name = "确认", path = "/confirm",requiredPermission = false)
    @ApiOperation(value = "确认", response = OutStationAllocationResult.class)
    @BusinessLog(title = "站外分摊汇总-确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirm(OutStationAllocationParam param) throws Exception {
        return service.confirm(param);
    }

    @GetResource(name = "删除", path = "/delete",requiredPermission = false)
    @ApiOperation(value = "删除", response = OutStationAllocationResult.class)
    @BusinessLog(title = "站外分摊汇总-删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(OutStationAllocationParam param) throws Exception {
        service.delete(param);
        return ResponseData.success();
    }

    @PostResource(name = "批量确认", path = "/confirmBatch",requiredPermission = false)
    @ApiOperation(value = "批量确认", response = OutStationAllocationResult.class)
    @BusinessLog(title = "站外分摊汇总-批量确认",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData confirmBatch(@RequestBody OutStationAllocationParam param) throws Exception {
        return service.confirmBatch(param);
    }

    @PostResource(name = "批量删除", path = "/deleteBatch",requiredPermission = false)
    @ApiOperation(value = "批量删除", response = OutStationAllocationResult.class)
    @BusinessLog(title = "站外分摊汇总-批量删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData deleteBatch(@RequestBody OutStationAllocationParam param) throws Exception {
        service.deleteBatch(param);
        return ResponseData.success();
    }

    @GetResource(name = "确认状态下拉", path = "/status",requiredPermission = false)
    @ApiOperation(value = "确认状态下拉", response = OutStationAllocationResult.class)
    public ResponseData status() throws Exception {
        return ResponseData.success(OutStationAllocationCheckStatus.getEnumList());
    }

    /**
     * 导入模板下载
     * @param response
     */
    @GetResource(name = "模板下载", path = "/downloadExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("模板下载")
    public void downloadExcel(HttpServletResponse response) {
        new ExcelUtils().downloadExcel(response, "/template/站外分摊汇总导入模板.xlsx");
    }
}
