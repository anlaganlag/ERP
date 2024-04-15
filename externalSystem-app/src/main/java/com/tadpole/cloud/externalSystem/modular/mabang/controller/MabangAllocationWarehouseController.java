package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangAllocationWarehouseParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.K3TransferMabangItemResult;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangAllocationWarehouse2Result;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangAllocationWarehouseResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangAllocationWarehouseService;
import com.alibaba.excel.EasyExcel;
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
    * 马帮分仓调拨单 前端控制器
    * </p>
*
* @author lsy
* @since 2022-06-15
*/
@RestController
@ApiResource(name="Mabang Allocation Warehouse 分仓调拨单",path = "/mabangAllocationWarehouse")
@Api(tags =  "Mabang Allocation Warehouse 分仓调拨单")
public class MabangAllocationWarehouseController {

    private final String ControllerName = "平台发展ERP-马帮库存同步-马帮分仓调拨单列表";

    @Autowired
    private IMabangAllocationWarehouseService service;


    @PostResource(name = "马帮分仓调拨单列表", path = "/list", menuFlag = true,requiredPermission = false)
    @ApiOperation(value = "马帮分仓调拨单列表", response = MabangAllocationWarehouseResult.class)
    public ResponseData list(@RequestBody MabangAllocationWarehouseParam param) {
        PageResult<MabangAllocationWarehouseResult> list = service.list(param);
        return ResponseData.success(list);
    }



    @GetResource(name = "查看详情", path = "/display",requiredPermission = false)
    @ApiOperation("查看详情")
    public ResponseData display(@RequestParam String billNo ,@RequestParam String  allocationCode) {
        PageResult<K3TransferMabangItemResult> list = this.service.display(billNo,allocationCode);
        return ResponseData.success(list);
    }

    @PostResource(name = "马帮分仓调拨单列表2.0", path = "/mergeList", requiredPermission = false)
    @ApiOperation(value = "马帮分仓调拨单列表2.0", response = MabangAllocationWarehouse2Result.class)
    public ResponseData mergeList(@RequestBody MabangAllocationWarehouseParam param) {
        PageResult<MabangAllocationWarehouse2Result> list2 = service.mergeList(param);
        return ResponseData.success(list2);
    }


    @PostResource(name = ControllerName + "导出", path = "/exportExcel", requiredPermission = false)
    @ApiOperation(ControllerName + "导出")
    public void exportExcel(HttpServletResponse response,@RequestBody MabangAllocationWarehouseParam param) throws IOException {
        try {
            List<MabangAllocationWarehouse2Result> results = service.exportExcel(param);
            exportExcel(response, ControllerName);
            EasyExcel.write(response.getOutputStream(), MabangAllocationWarehouse2Result.class).sheet(ControllerName).doWrite(results);
        } catch (Exception ex) {
            response.sendError(500, ControllerName + "导出错误");
        }
    }

    private void exportExcel(HttpServletResponse response, String sheetName) throws IOException {
        String fileName = new String((sheetName + ".xlsx").getBytes("UTF-8"), "ISO8859-1");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
    }
}
