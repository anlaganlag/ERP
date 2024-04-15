package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.PurchaseOrdersResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IExportOrImportService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IPurchaseOrdersService;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockApprovaltimeParameterService;
import com.tadpole.cloud.operationManagement.modular.stock.vo.req.OperApplyInfoReqV3;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 事业部审批 v3
 * *
* @author lsy
* @since 2022-06-14
*/
@RestController
@ApiResource(name = "日常采购申请单V3", path = "/purchaseOrdersV3")
@Api(tags = "日常采购申请单")
@Slf4j
public class PurchaseOrdersV3Controller {

    @Autowired
    private IPurchaseOrdersService service;

    @Autowired
    private IStockApprovaltimeParameterService parameterService;

    @Autowired
    private IExportOrImportService exportOrImportService;


    private final String controllerName = "日常采购申请单V3";


    /**
     * 日常采购申请单列表查询v3
     *
     * @param reqVO
     * @return
     */
    @PostResource(name = "日常采购申请单列表V3", path = "/list", menuFlag = true, requiredPermission = false)
    @ApiOperation(value = "日常采购申请单列表V3", response = PurchaseOrdersResult.class)
    @BusinessLog(title = controllerName + "_" +"日常采购申请单列表V3",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody OperApplyInfoReqV3 reqVO) {
        return ResponseData.success(service.queryPageV3(reqVO));
    }




    /**
     * 采购申请单导入v3
     * @param file
     * @return
     * @throws IOException
     */
    @ParamValidator
    @PostResource(name = "采购申请单导入V3", path = "/upload", requiredPermission = false)
    @ApiOperation(value = "采购申请单导入V3")
    @BusinessLog(title = controllerName + "_" +"采购申请单导入V3",opType = LogAnnotionOpTypeEnum.IMPORT)
    public ResponseData upload(@RequestParam("file") MultipartFile file) throws IOException {
        return exportOrImportService.purchaseOrdersImport(file);
    }


    /**
     * @param response
     * @param param
     * @throws IOException
     */
    @PostResource(name = "采购申请单导出V3", path = "/exportExcel" )
    @ApiOperation("采购申请单导出V3")
    @BusinessLog(title = controllerName + "_" +"采购申请单导出V3",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportExcel2(HttpServletResponse response, @RequestBody OperApplyInfoReqV3 param) throws Exception {
        exportOrImportService.purchaseOrdersExportV3(response,param);
    }

}