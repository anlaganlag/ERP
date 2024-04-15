package com.tadpole.cloud.product.modular.product.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.product.modular.product.model.params.RdManageUpRecordParam;
import com.tadpole.cloud.product.modular.product.model.params.RdPlManageParam;
import com.tadpole.cloud.product.modular.product.model.params.RdPssManageParam;
import com.tadpole.cloud.product.modular.product.model.result.RdPlManageResult;
import com.tadpole.cloud.product.modular.product.model.result.RdPssManageResult;
import com.tadpole.cloud.product.modular.product.service.IRdManageUpRecordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.product.modular.product.service.IRdPlManageService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 产品线管理 前端控制器
 * </p>
 *
 * @author S20210221
 * @since 2023-11-25
 */
@RestController
@Api(tags = "产品线管理")
@ApiResource(name = "产品线管理", path = "/rdPlManage")
public class RdPlManageController {

    @Autowired
    private IRdPlManageService service;

    @Autowired
    private IRdManageUpRecordService manageUpRecordService;

    @PostResource(name = "产品线管理", path = "/listPage", menuFlag = true)
    @ApiOperation(value = "产品线管理")
    @BusinessLog(title = "产品线管理",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listPage(@RequestBody RdPlManageParam param) {
        return ResponseData.success(service.list(param));
    }

    @PostResource(name = "产品线管理-新增产品线", path = "/add", menuFlag = true)
    @ApiOperation(value = "产品线管理-新增产品线")
    @BusinessLog(title = "产品线管理-新增产品线",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData add(@RequestBody RdPlManageParam param) {
        return service.add(param);
    }

    @PostResource(name = "产品线管理-编辑", path = "/edit")
    @ApiOperation(value = "产品线管理-编辑")
    @BusinessLog(title = "产品线管理-编辑",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData edit(@RequestBody RdPlManageParam param) throws IllegalAccessException {
        return service.edit(param);
    }

    @PostResource(name = "产品线管理-启用", path = "/enable")
    @ApiOperation(value = "产品线管理-启用")
    @BusinessLog(title = "产品线管理-启用",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData enable(@RequestBody RdPlManageParam param) {
        service.enable(param);
        return ResponseData.success();
    }

    @PostResource(name = "产品线管理-禁用", path = "/disable")
    @ApiOperation(value = "产品线管理-禁用")
    @BusinessLog(title = "产品线管理-禁用",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData disable(@RequestBody RdPlManageParam param) {
        service.disable(param);
        return ResponseData.success();
    }

    @PostResource(name = "产品线管理-日志", path = "/log")
    @ApiOperation(value = "产品线管理-日志")
    @BusinessLog(title = "产品线管理-日志",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData log(@RequestBody RdManageUpRecordParam param) {
        param.setSysType("PL_RECORD");
        return ResponseData.success(manageUpRecordService.getList(param));
    }

    @PostResource(name = "产品线管理-批量启用", path = "/enableBatch")
    @ApiOperation(value = "产品线管理-批量启用")
    @BusinessLog(title = "产品线管理-批量启用",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData enableBatch(@RequestBody List<RdPlManageParam> params) {
        service.enableBatch(params);
        return ResponseData.success();
    }

    @PostResource(name = "产品线管理-批量禁用", path = "/disableBatch")
    @ApiOperation(value = "产品线管理-批量禁用")
    @BusinessLog(title = "产品线管理-批量禁用",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData disableBatch(@RequestBody List<RdPlManageParam> params) {
        service.disableBatch(params);
        return ResponseData.success();
    }

    @PostResource(name = "产品线管理-修改人员", path = "/editPersonBatch")
    @ApiOperation(value = "产品线管理-修改人员")
    @BusinessLog(title = "产品线管理-修改人员",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData editPersonBatch(@RequestBody List<RdPlManageParam> params) throws IllegalAccessException {
        service.editPersonBatch(params);
        return ResponseData.success();
    }

    @PostResource(name = "产品线管理-导出", path = "/export",  requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "产品线管理-导出", response = ResponseData.class)
    @BusinessLog(title = "产品线管理-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody RdPlManageParam param, HttpServletResponse response) throws IOException {
        List<RdPlManageResult> list = service.list(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("产品线管理.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), RdPlManageResult.class).sheet("产品线管理").doWrite(list);
        return ResponseData.success();
    }


    @PostResource(name = "归属产品线", path = "/listSelect",  requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "归属产品线")
    @BusinessLog(title = "归属产品线",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData listSelect() {
        return ResponseData.success(service.list(new RdPlManageParam()));
    }
}
