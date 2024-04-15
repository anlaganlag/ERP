package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasWarehouseSyncErrorParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.SyncErpParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasWarehouseSyncErrorDetailResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasWarehouseSyncErrorResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasWarehouseManageRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author: ty
 * @description: 海外仓同步ERP异常管理
 * @date: 2022/11/15
 */
@RestController
@ApiResource(name = "海外仓同步ERP异常管理", path = "/overseasWarehouseSyncError")
@Api(tags = "海外仓同步ERP异常管理")
public class OverseasWarehouseSyncErrorController {

    @Autowired
    private IOverseasWarehouseManageRecordService recordService;

    /**
     * 海外仓同步ERP异常管理查询列表
     * @param param
     * @return
     */
    @PostResource(name = "海外仓同步ERP异常管理查询列表", path = "/querySyncErrorPage", menuFlag = true)
    @ApiOperation(value = "海外仓同步ERP异常管理查询列表", response = OverseasWarehouseSyncErrorResult.class)
    @BusinessLog(title = "海外仓同步ERP异常管理-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData querySyncErrorPage(@RequestBody OverseasWarehouseSyncErrorParam param) {
        return recordService.querySyncErrorPage(param);
    }

    /**
     * 海外仓同步ERP异常管理明细
     * @param param
     * @return
     */
    @PostResource(name = "海外仓同步ERP异常管理明细", path = "/querySyncErrorDetail", menuFlag = true)
    @ApiOperation(value = "海外仓同步ERP异常管理明细", response = OverseasWarehouseSyncErrorDetailResult.class)
    @BusinessLog(title = "海外仓同步ERP异常管理-列表明细查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData querySyncErrorDetail(@RequestBody SyncErpParam param) {
        return recordService.querySyncErrorDetail(param);
    }

    /**
     * 海外仓同步ERP异常管理查询列表导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "海外仓同步ERP异常管理查询列表导出", path = "/exportSyncError", requiredPermission = false)
    @ApiOperation(value = "海外仓同步ERP异常管理查询列表导出")
    @BusinessLog(title = "海外仓同步ERP异常管理-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportSyncError(@RequestBody OverseasWarehouseSyncErrorParam param, HttpServletResponse response) throws IOException {
        List<OverseasWarehouseSyncErrorDetailResult> resultList = recordService.exportSyncError(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("海外仓同步ERP异常管理查询列表导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), OverseasWarehouseSyncErrorDetailResult.class).sheet("海外仓同步ERP异常管理查询列表导出").doWrite(resultList);
    }

    /**
     * 同步ERP
     * @param param
     * @return
     */
    @PostResource(name = "同步ERP", path = "/syncErp", menuFlag = true)
    @ApiOperation(value = "同步ERP", response = ResponseData.class)
    @BusinessLog(title = "海外仓同步ERP异常管理-同步ERP",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData syncErp(@RequestBody SyncErpParam param) {
        return recordService.syncErp(param);
    }

    /**
     * 海外仓同步EBMS异常管理查询列表
     * @param param
     * @return
     */
    @PostResource(name = "海外仓同步EBMS异常管理查询列表", path = "/querySyncEBMSErrorPage", menuFlag = true)
    @ApiOperation(value = "海外仓同步EBMS异常管理查询列表", response = OverseasWarehouseSyncErrorResult.class)
    @BusinessLog(title = "海外仓同步EBMS异常管理-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData querySyncEBMSErrorPage(@RequestBody OverseasWarehouseSyncErrorParam param) {
        return recordService.querySyncEBMSErrorPage(param);
    }

    /**
     * 海外仓同步EBMS异常管理明细
     * @param param
     * @return
     */
    @PostResource(name = "海外仓同步EBMS异常管理明细", path = "/querySyncEBMSErrorDetail", menuFlag = true)
    @ApiOperation(value = "海外仓同步EBMS异常管理明细", response = OverseasWarehouseSyncErrorDetailResult.class)
    @BusinessLog(title = "海外仓同步EBMS异常管理-列表明细查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData querySyncEBMSErrorDetail(@RequestBody SyncErpParam param) {
        return recordService.querySyncEBMSErrorDetail(param);
    }

    /**
     * 海外仓同步EBMS异常管理查询列表导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "海外仓同步EBMS异常管理查询列表导出", path = "/exportSyncEBMSError", requiredPermission = false)
    @ApiOperation(value = "海外仓同步EBMS异常管理查询列表导出")
    @BusinessLog(title = "海外仓同步EBMS异常管理-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportSyncEBMSError(@RequestBody OverseasWarehouseSyncErrorParam param, HttpServletResponse response) throws IOException {
        List<OverseasWarehouseSyncErrorDetailResult> resultList = recordService.exportSyncEBMSError(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("海外仓同步EBMS异常管理查询列表导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), OverseasWarehouseSyncErrorDetailResult.class).sheet("海外仓同步EBMS异常管理查询列表导出").doWrite(resultList);
    }

    /**
     * 同步EBMS
     * @param param
     * @return
     */
    @PostResource(name = "同步EBMS", path = "/syncEBMS", menuFlag = true)
    @ApiOperation(value = "同步EBMS", response = ResponseData.class)
    @BusinessLog(title = "海外仓同步EBMS异常管理-同步EBMS",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData syncEBMS(@RequestBody SyncErpParam param) {
        return recordService.syncEBMS(param);
    }
}
