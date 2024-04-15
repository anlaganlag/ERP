package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import cn.stylefeng.guns.cloud.system.core.dbs.annotation.DataSource;
import com.tadpole.cloud.operationManagement.modular.stock.service.IExportOrImportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@ApiResource(name = "备货导入导出接口", path = "/importOrExport")
@Api(tags = "备货导入导出接口")
public class ExportOrImportController {

    @Autowired
    private IExportOrImportService service;

    private final String controllerName = "备货导入导出接口";


    @PostResource(name = "日常备货申请-导入", path = "/operImport", requiredPermission = false)
    @ApiOperation(value = "日常备货申请-导入")
    @DataSource(name = "stocking")
    @BusinessLog(title = controllerName + "_" +"日常备货申请-导入",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData operImport( MultipartFile file) {
       return service.operImport(file);
    }


    @PostResource(name = "日常备货申请-导入并提交", path = "/operImportComit", requiredPermission = false)
    @ApiOperation(value = "日常备货申请-导入并提交")
    @BusinessLog(title = controllerName + "_" +"日常备货申请-导入并提交",opType = LogAnnotionOpTypeEnum.UPDATE)
    @DataSource(name = "stocking")
    public ResponseData operImportComit( MultipartFile file) {
//        return service.operImportComit(file);
        return service.operImportComit2(file);
    }


    @GetResource(name = "日常备货申请-导出", path = "/operExport", requiredPermission = false)
    @ApiOperation(value = "日常备货申请-导出")
    @BusinessLog(title = controllerName + "_" +"日常备货申请-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    @DataSource(name = "stocking")
    public void operExport(HttpServletResponse response) throws Exception {
//        service.operExport(response);
        service.operExportFast2(response);
    }


}
