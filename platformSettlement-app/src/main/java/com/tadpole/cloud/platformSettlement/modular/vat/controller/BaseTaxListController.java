package com.tadpole.cloud.platformSettlement.modular.vat.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.GetResource;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.modular.vat.entity.BaseTaxList;
import com.tadpole.cloud.platformSettlement.modular.vat.model.params.BaseTaxListParam;
import com.tadpole.cloud.platformSettlement.modular.vat.model.result.BaseTaxListResult;
import com.tadpole.cloud.platformSettlement.modular.vat.service.IBaseTaxListService;
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
 * 基础信息-税号列表 前端控制器
 * </p>
 *
 * @author cyt
 * @since 2022-08-04
 */
@RestController
@Api(tags = "BaseTaxList VAT基础信息-税号")
@ApiResource(name = "BaseTaxList VAT基础信息-税号", path = "/baseTaxList")
public class BaseTaxListController {

    @Autowired
    private IBaseTaxListService service;

    private final String ControllerName = "VAT基础信息-税号";

    @PostResource(name = ControllerName + "列表查询", path = "/list", menuFlag = true)
    @ApiOperation(value = ControllerName + "列表查询", response = ResponseData.class)
    @BusinessLog(title = "BaseTaxList VAT基础信息-税号-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryListPage(@RequestBody BaseTaxListParam param) {
        return ResponseData.success(service.queryListPage(param));
    }

    @PostResource(name = ControllerName + "导出", path = "/exportExcel", requiredPermission = false, requiredLogin = false)
    @ApiOperation(ControllerName + "导出")
    @BusinessLog(title = "BaseTaxList VAT基础信息-税号-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportExcel(HttpServletResponse response, BaseTaxListParam param) throws IOException {
        try {
            List<BaseTaxListResult> results = service.exportExcel(param);
            exportExcel(response, ControllerName);
            EasyExcel.write(response.getOutputStream(), BaseTaxListResult.class).sheet(ControllerName).doWrite(results);
        } catch (Exception ex) {
            response.sendError(500, ControllerName + "导出错误");
        }
    }

    @GetResource(name = ControllerName + "重新取值", path = "/refresh", menuFlag = false)
    @ApiOperation(value = ControllerName + "重新取值", response = ResponseData.class)
    @BusinessLog(title = "BaseTaxList VAT基础信息-税号-重新取值",opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData RefreshData() {
        List<BaseTaxList> ebmsList= service.queryEbmsData();
        if (service.RefreshData(ebmsList)) {
            return ResponseData.success();
        } else {
            return ResponseData.error(ControllerName + "重新取值 失败！");
        }
    }

    private void exportExcel(HttpServletResponse response, String sheetName) throws IOException {
        String fileName = new String((sheetName + ".xlsx").getBytes("UTF-8"), "ISO8859-1");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
    }
}
