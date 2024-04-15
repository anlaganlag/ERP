package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockAsinBlacklist;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockAsinBlacklistParam;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockAsinBlacklistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * ASIN黑名单 前端控制器
 *
 * @author lsy
 * @since 2022-12-19
 */

@Slf4j
@RestController
@Api(tags = "ASIN黑名单")
@ApiResource(name = "ASIN黑名单", path = "/stockAsinBlacklist")


public class StockAsinBlacklistController {

    @Autowired
    private IStockAsinBlacklistService service;

    private final String controllerName = "ASIN黑名单";



    /**
     * ASIN黑名单-列表
     */
    @PostResource(name = "ASIN黑名单-列表", path = "/list", requiredPermission = false , menuFlag = true)
    @ApiOperation(value = "ASIN黑名单-列表", response = StockAsinBlacklist.class)
    @BusinessLog(title = controllerName + "_" +"ASIN黑名单-列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData asinBlacklist(@RequestBody StockAsinBlacklistParam param) {
        try {
            return ResponseData.success(service.asinBlacklist(param));
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }


    /**
     * ASIN黑名单-列表
     */
    @PostResource(name = "ASIN黑名单-下载", path = "/export", requiredPermission = false )
    @ApiOperation(value = "ASIN黑名单-下载", response = StockAsinBlacklist.class)
    @BusinessLog(title = controllerName + "_" +"ASIN黑名单-下载",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody StockAsinBlacklistParam param, HttpServletResponse response) throws IOException {

        List<StockAsinBlacklist> data = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("ASIN黑名单.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), StockAsinBlacklist.class).sheet("ASIN黑名单").doWrite(data);
        return ResponseData.success();
    }
}
