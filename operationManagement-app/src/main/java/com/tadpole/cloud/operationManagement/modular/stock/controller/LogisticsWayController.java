package com.tadpole.cloud.operationManagement.modular.stock.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.excel.listener.BaseExcelListener;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.LogisticsWayParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.LogisticsWayResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.ILogisticsWayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 设置物流方式 前端控制器
 * </p>
 *
 * @author lsy
 * @since 2022-09-13
 */

@Slf4j
@RestController
@Api(tags = "设置物流方式")
@ApiResource(name = "设置物流方式", path = "/logisticsWay")
public class LogisticsWayController {

    private final ILogisticsWayService service;

    private final String controllerName = "发货方式";

    public LogisticsWayController(ILogisticsWayService service) {
        this.service = service;
    }

    @PostResource(name = controllerName + "_" +"分页查询", path = "/listPage", menuFlag = true)
    @ApiOperation(value = controllerName + "_" +"分页查询", response = LogisticsWayResult.class)
    @BusinessLog(title = controllerName + "_" +"分页查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody LogisticsWayParam param) {
        return service.queryListPage(param);
    }

    @PostResource(name = controllerName + "_" +"导出", path = "/exportExcel", requiredPermission = false )
    @ApiOperation(controllerName + "_" +"导出")
    @BusinessLog(title = controllerName + "_" +"导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportExcel(HttpServletResponse response,@RequestBody LogisticsWayParam param) throws IOException {
        try {
            List<LogisticsWayResult> results = service.exportExcel(param);
            exportExcel(response, controllerName);
            EasyExcel.write(response.getOutputStream(), LogisticsWayResult.class).sheet(controllerName).doWrite(results);
        } catch (Exception ex) {
            response.sendError(500, controllerName + "_" +"导出错误");
        }
    }

    private void exportExcel(HttpServletResponse response, String sheetName) throws IOException {
        String fileName = new String((sheetName + ".xlsx").getBytes("UTF-8"), "ISO8859-1");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
    }

    @PostResource(name = controllerName + "_" +"新增", path = "/add", requiredPermission = false)
    @ApiOperation(value = controllerName + "_" +"新增", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"新增",opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody LogisticsWayParam param) {
        if (ObjectUtil.isEmpty(param)) {
            return ResponseData.error(controllerName + "_" +"新增失败，原因：获取数据异常，请确认传入数据。");
        }
        try {
            return service.add(param, controllerName);
        } catch (Exception ex) {
            return ResponseData.error(controllerName + "_" +"新增异常，原因：" + ex.getMessage());
        }
    }

    @PostResource(name = controllerName + "_" +"修改", path = "/edit", requiredPermission = false)
    @ApiOperation(value = controllerName + "_" +"修改", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"修改",opType = LogAnnotionOpTypeEnum.UPDATE)
    public ResponseData edit(@RequestBody LogisticsWayParam param) {
        if (ObjectUtil.isEmpty(param)) {
            return ResponseData.error(controllerName + "_" +"修改失败，原因：获取数据异常，请确认传入数据。");
        }
        try {
            return service.edit(param,controllerName);
        } catch (Exception ex) {
            return ResponseData.error(controllerName + "_" +"修改异常，原因：" + ex.getMessage());
        }
    }

    @PostResource(name = controllerName + "_" +"删除", path = "/delete", requiredPermission = false)
    @ApiOperation(value = controllerName + "_" +"删除", response = ResponseData.class)
    @BusinessLog(title = controllerName + "_" +"删除",opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody List<Integer> idList) {
        if (ObjectUtil.isEmpty(idList)) {
            return ResponseData.error(controllerName + "_" +"删除失败，原因：获取数据异常，请确认传入数据。");
        }
        try {
            return service.delete(idList,controllerName);
        } catch (Exception ex) {
            return ResponseData.error(controllerName + "_" +"删除异常，原因：" + ex.getMessage());
        }
    }

    //ResponseData uploadExport(List<LogisticsWayParam> paramList, String controllerName);
    @ParamValidator
    @ApiOperation(value = controllerName + "_" +"导入", response = ResponseData.class)
    @PostResource(name = controllerName + "_" +"导入", path = "/uploadExcel", requiredPermission = false)
    @BusinessLog(title = controllerName + "_" +"导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData uploadExcel(@RequestParam(value = "file", required = true) MultipartFile file) {
        if (file == null) {
            return ResponseData.error(controllerName + "_" +"导入失败，原因：未检测到上传文件。");
        }


        BufferedInputStream buffer = null;
        try {
            buffer = new BufferedInputStream(file.getInputStream());
            BaseExcelListener listener = new BaseExcelListener<LogisticsWayParam>();
            EasyExcel.read(buffer, LogisticsWayParam.class, listener).sheet().headRowNumber(1).doRead();
            List<LogisticsWayParam> dataList = listener.getDataList();
            if (CollectionUtil.isEmpty(dataList)) {
                return ResponseData.error(controllerName + "_" +"导入失败，原因：数据为空。");
            }
            return service.uploadExcel(dataList, controllerName);
        } catch (Exception ex) {
            return ResponseData.error(ResponseData.DEFAULT_ERROR_CODE, controllerName + "_" +"导入 失败 ! /r/n " + ex.getMessage());
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error(controllerName + "_" +"导入 关闭流异常", e);
                }
            }
        }
    }
}
