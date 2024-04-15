package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.libs.validator.stereotype.ParamValidator;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OverseasChangeReportParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OverseasChangeReportResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasWarehouseManageRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: ty
 * @description: 海外仓换标报表
 * @date: 2022/10/18
 */
@RestController
@ApiResource(name = "海外仓换标报表", path = "/changeReport")
@Api(tags = "海外仓换标报表")
public class OverseasChangeReportController {

    @Autowired
    private IOverseasWarehouseManageRecordService recordService;

    /**
     * 海外仓换标报表查询列表
     * @param param
     * @return
     */
    @ParamValidator
    @PostResource(name = "海外仓换标报表查询列表", path = "/queryChangeReportPage",  menuFlag = true,requiredPermission = false)
    @ApiOperation(value = "海外仓换标报表查询列表", response = OverseasChangeReportResult.class)
    @BusinessLog(title = "海外仓换标报表-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryChangeReportPage(@RequestBody OverseasChangeReportParam param) {
        return recordService.queryChangeReportPage(param);
    }

    /**
     * 海外仓换标报表导出
     * @param param
     * @param response
     * @return
     * @throws IOException
     */
    @PostResource(name = "海外仓换标报表导出", path = "/exportChangeReport", requiredPermission = false,requiredLogin = false)
    @ApiOperation(value = "海外仓换标报表导出")
    @BusinessLog(title = "海外仓换标报表-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData exportChangeReport(@RequestBody OverseasChangeReportParam param, HttpServletResponse response) throws IOException {
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("海外仓换标报表.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), OverseasChangeReportResult.class).sheet("海外仓换标报表").doWrite(recordService.exportChangeReport(param));
        return ResponseData.success();
    }
}
