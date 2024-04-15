package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.alibaba.excel.EasyExcel;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockAreaBlacklist;
import com.tadpole.cloud.operationManagement.modular.stock.entity.StockAsinBlacklist;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockAreaBlacklistParam;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockAreaBlacklistService;
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
 * * 区域黑名单 前端控制器
 *
 * @author lsy
 * @since 2022-12-19
 */


@Slf4j
@RestController
@Api(tags = "区域黑名单")
@ApiResource(name = "区域黑名单", path = "/stockAreaBlacklist")
public class StockAreaBlacklistController {

    @Autowired
    private IStockAreaBlacklistService service;

    private final String controllerName = "区域黑名单";


    /**
     * 区域黑名单-列表
     */
    @PostResource(name = "区域黑名单-列表", path = "/list", requiredPermission = false , menuFlag = true)
    @ApiOperation(value = "区域黑名单-列表", response = StockAreaBlacklist.class)
    @BusinessLog(title = controllerName + "_" +"区域黑名单-列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData asinBlacklist(@RequestBody StockAreaBlacklistParam param) {
        try {
            return ResponseData.success(service.areaBlacklist(param));
        } catch (Exception e) {
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     * 区域黑名单-下载
     */
    @PostResource(name = "区域黑名单-下载", path = "/export", requiredPermission = false )
    @ApiOperation(value = "区域黑名单-下载", response = StockAsinBlacklist.class)
    @BusinessLog(title = controllerName + "_" +"区域黑名单-下载",opType = LogAnnotionOpTypeEnum.EXPORT)
    public ResponseData export(@RequestBody StockAreaBlacklistParam param, HttpServletResponse response) throws IOException {

        List<StockAreaBlacklist> data = service.export(param);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String("区域黑名单.xlsx".getBytes("utf-8"), "ISO8859-1"));
        EasyExcel.write(response.getOutputStream(), StockAreaBlacklist.class).sheet("区域黑名单").doWrite(data);
        return ResponseData.success();
    }

}
