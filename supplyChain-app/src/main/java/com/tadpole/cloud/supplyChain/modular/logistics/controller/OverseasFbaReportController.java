package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasFbaReportParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasFbaReportResult;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasInWarehouseTotalResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasInWarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: ty
 * @description: 亚马逊发海外仓报表
 * @date: 2022/10/18
 */
@RestController
@ApiResource(name = "亚马逊发海外仓报表", path = "/fbaReport")
@Api(tags = "亚马逊发海外仓报表")
public class OverseasFbaReportController {

    @Autowired
    private IOverseasInWarehouseService overseasInWarehouseService;

    /**
     * 亚马逊发海外仓报表查询列表
     * @param param
     * @return
     */
    @ParamValidator
    @PostResource(name = "亚马逊发海外仓报表查询列表", path = "/queryFbaReportPage",  menuFlag = true,requiredPermission = false)
    @ApiOperation(value = "亚马逊发海外仓报表查询列表", response = OverseasFbaReportResult.class)
    @BusinessLog(title = "亚马逊发海外仓报表-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryFbaReportPage(@RequestBody OverseasFbaReportParam param) {
        return overseasInWarehouseService.queryFbaReportPage(param);
    }

    /**
     * 亚马逊发海外仓报表查询列表数据汇总
     * @param param
     * @return
     */
    @PostResource(name = "亚马逊发海外仓报表查询列表数据汇总", path = "/queryFbaReportTotal", requiredPermission = false)
    @ApiOperation(value = "亚马逊发海外仓报表查询列表数据汇总", response = OverseasInWarehouseTotalResult.class)
    @BusinessLog(title = "亚马逊发海外仓报表-列表数据汇总",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryFbaReportTotal(@RequestBody OverseasFbaReportParam param) {
        return overseasInWarehouseService.queryFbaReportTotal(param);
    }

    /**
     * 亚马逊发海外仓报表导出
     * @param param
     * @param response
     * @return
     * @throws IOException
     */
    @PostResource(name = "亚马逊发海外仓报表导出", path = "/exportFbaReport", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "亚马逊发海外仓报表导出")
    @BusinessLog(title = "亚马逊发海外仓报表-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData exportFbaReport(@RequestBody OverseasFbaReportParam param, HttpServletResponse response) throws IOException {
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("亚马逊发海外仓报表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), OverseasFbaReportResult.class).sheet("亚马逊发海外仓报表").doWrite(overseasInWarehouseService.exportFbaReport(param));
        return ResponseData.success();
    }
}
