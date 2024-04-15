package com.tadpole.cloud.externalSystem.modular.mabang.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.page.PageResult;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangWarehouse;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangWarehouseParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma.WarehouseParm;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangWarehouseResult;
import com.tadpole.cloud.externalSystem.modular.mabang.service.IMabangWarehouseService;
import com.alibaba.excel.EasyExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* <p>
    * 马帮仓库列表 前端控制器
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
@RestController
@ApiResource(name = "马帮仓库列表", path = "/maBangWarehouse")
@Api(tags = "马帮仓库列表")
public class MabangWarehouseController {

    @Autowired
    private IMabangWarehouseService service;

    @PostResource(name = "马帮仓库查询列表", path = "/list", menuFlag = true)
    @ApiOperation(value = "马帮仓库查询列表", response = MabangWarehouse.class)
    public ResponseData queryListPage(@RequestBody MabangWarehouseParam param) throws IOException {

        PageResult<MabangWarehouseResult> pageBySpec = service.findPageBySpec(param);

        return ResponseData.success(pageBySpec);
    }

    @PostResource(name = "生成仓库列表", path = "/getWarehouseList", requiredPermission = false, requiredLogin = false)
    @ApiOperation(value = "生成仓库列表")
    public ResponseData getWarehouseList(@RequestBody(required = false) WarehouseParm warehouseParm) throws Exception{
        return service.getWarehouseList(warehouseParm);
    }

    @GetResource(name = "仓库名称", path="/queryNames", requiredPermission = false)
    @ApiOperation(value = "仓库名称", response = ResponseData.class)
    public ResponseData queryNames(){
        return service.queryNames();
    }


    @PostResource(name = "马帮仓库列表导出", path = "/exportExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation("马帮仓库列表导出")
    public void exportExcel(HttpServletResponse response,@RequestBody MabangWarehouseParam param) throws IOException {
        try {
            List<MabangWarehouseResult> results = service.exportExcel(param);
            exportExcel(response, "马帮仓库列表");
            EasyExcel.write(response.getOutputStream(), MabangWarehouseResult.class).sheet("马帮仓库列表").doWrite(results);
        } catch (Exception ex) {
            response.sendError(500, "马帮仓库列表导出错误");
        }
    }

    private void exportExcel(HttpServletResponse response, String sheetName) throws IOException {
        String fileName = new String((sheetName + ".xlsx").getBytes("UTF-8"), "ISO8859-1");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
    }
}
