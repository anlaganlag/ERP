package com.tadpole.cloud.platformSettlement.modular.finance.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.platformSettlement.api.finance.model.params.MonitorGoogdwillParam;
import com.tadpole.cloud.platformSettlement.api.finance.entity.MonitorGoogdwill;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import io.swagger.annotations.Api;
import com.tadpole.cloud.platformSettlement.modular.finance.service.IMonitorGoogdwillService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* <p>
    * goodwill监控表	 前端控制器
    * </p>
*
* @author S20190161
* @since 2023-07-17
*/
@RestController
@Api(tags = "goodwill监控表	")
@ApiResource(name = "goodwill监控表	", path = "/monitorGoogdwill")
public class MonitorGoogdwillController {

    @Autowired
    private IMonitorGoogdwillService service;

    @BusinessLog(title = "goodwill监控表",opType = LogAnnotionOpTypeEnum.QUERY)
    @PostResource(name = "goodwill监控表", path = "/queryListPage", menuFlag = true)
    @ApiOperation(value = "goodwill监控表")
    public ResponseData queryListPage(@RequestBody MonitorGoogdwillParam param) {
        var pageBySpec = service.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }


    @BusinessLog(title = "goodwill监控表-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    @PostResource(name = "导出", path = "/export")
    @ApiOperation(value = "导出")
    public ResponseData export(@RequestBody MonitorGoogdwillParam param, HttpServletResponse response) throws IOException {
        var list=service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("goodwill.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), MonitorGoogdwill.class).sheet("goodwill").doWrite(list);
        return ResponseData.success();
    }
    //定时JOB刷新
    @PostResource(name = "Job刷Goowill费", path = "/afreshStorageFee")
    @ApiOperation(value = "Job刷Goowill费")
    public ResponseData afreshStorageFee() {
        service.afreshStorageFee();
        return ResponseData.success();
    }
}
