package com.tadpole.cloud.supplyChain.modular.logistics.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.supplyChain.api.logistics.model.params.OwVolumeReportParam;
import com.tadpole.cloud.supplyChain.api.logistics.model.result.OwVolumeReportResult;
import com.tadpole.cloud.supplyChain.modular.logistics.service.IOverseasWarehouseManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author: ty
 * @description: 库存产品重量体积报表
 * @date: 2022/12/22
 */
@RestController
@ApiResource(name = "库存产品重量体积报表", path = "/owVolumeReport")
@Api(tags = "库存产品重量体积报表")
public class OwVolumeReportController {

    @Autowired
    private IOverseasWarehouseManageService manageService;

    /**
     * 库存产品重量体积报表查询列表
     * @param param
     * @return
     */
    @PostResource(name = "库存产品重量体积报表查询列表", path = "/querySyncErrorPage", menuFlag = true)
    @ApiOperation(value = "库存产品重量体积报表查询列表", response = OwVolumeReportResult.class)
    @BusinessLog(title = "库存产品重量体积报表-列表查询",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryOwVolumePage(@RequestBody OwVolumeReportParam param) {
        return manageService.queryOwVolumePage(param);
    }

    /**
     * 库存产品重量体积报表查询列表导出
     * @param param
     * @param response
     * @throws IOException
     */
    @PostResource(name = "库存产品重量体积报表查询列表导出", path = "/exportOwVolume", requiredPermission = false)
    @ApiOperation(value = "库存产品重量体积报表查询列表导出")
    @BusinessLog(title = "库存产品重量体积报表-导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportOwVolume(@RequestBody OwVolumeReportParam param, HttpServletResponse response) throws IOException {
        List<OwVolumeReportResult> resultList = manageService.exportOwVolume(param);
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("库存产品重量体积报表查询列表导出.xlsx".getBytes("utf-8"),"ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), OwVolumeReportResult.class).sheet("库存产品重量体积报表查询列表导出").doWrite(resultList);
    }
}
