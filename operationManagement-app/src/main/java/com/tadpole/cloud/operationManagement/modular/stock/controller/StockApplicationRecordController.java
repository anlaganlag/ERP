package com.tadpole.cloud.operationManagement.modular.stock.controller;


import cn.stylefeng.guns.cloud.libs.scanner.annotation.BusinessLog;
import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.enums.LogAnnotionOpTypeEnum;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import cn.stylefeng.guns.cloud.model.web.response.ResponseData;
import com.tadpole.cloud.operationManagement.modular.stock.model.params.StockApplicationRecordParam;
import com.tadpole.cloud.operationManagement.modular.stock.model.result.StockApplicationRecordResult;
import com.tadpole.cloud.operationManagement.modular.stock.service.IStockApplicationRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 备货申请记录 前端控制器
 * </p>
 *
 * @author ly
 * @since 2022-09-06
 */
@RestController
@ApiResource(name = "备货申请记录", path = "/stockApplicationRecord")
@Api(tags = "备货申请记录")
@Slf4j
public class StockApplicationRecordController {

    @Resource
    private IStockApplicationRecordService service;

    private final String controllerName = "备货申请记录";

    /**
     * 日常采购申请单列表查询
     *
     * @param reqParam
     * @return
     */
    @PostResource(name = "备货申请记录列表", path = "/list", menuFlag = true, requiredPermission = false)
    @ApiOperation(value = "备货申请记录列表", response = StockApplicationRecordResult.class)
    @BusinessLog(title = controllerName + "_" +"备货申请记录列表",opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData queryPage(@RequestBody StockApplicationRecordParam reqParam) {
        return service.queryListPage(reqParam);
    }

    @ApiOperation("运营核对数据导出")
    @PostResource(name = "/运营核对数据导出", path = "/exportExcel", requiredPermission = false )
    @BusinessLog(title = controllerName + "_" +"运营核对数据导出",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportExcel(@RequestBody StockApplicationRecordParam param, HttpServletResponse response) throws IOException {
        try {
            service.exportExcel(response,param);
        } catch (Exception e) {
            log.error("运营核对数据导出异常:{}",e);
        }
    }

    @ApiOperation("计划分析数据")
    @PostResource(name = "/计划分析数据导出", path = "/exportExcelAnalysis", requiredPermission = false )
    @BusinessLog(title = controllerName + "_" +"计划分析数据",opType = LogAnnotionOpTypeEnum.EXPORT)
    public void exportExcelAnalysis(@RequestBody StockApplicationRecordParam param,HttpServletResponse response) throws IOException {
        try {
            service.exportExcelAnalysis(response,param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}