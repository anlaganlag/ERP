package com.tadpole.cloud.externalSystem.modular.lingxing.controller;

import cn.stylefeng.guns.cloud.libs.scanner.annotation.PostResource;
import cn.stylefeng.guns.cloud.libs.scanner.stereotype.ApiResource;
import com.tadpole.cloud.externalSystem.api.lingxing.model.req.sourcereport.*;
import com.tadpole.cloud.externalSystem.api.lingxing.model.resp.LingXingBaseRespData;
import com.tadpole.cloud.externalSystem.modular.lingxing.service.LingXingSourceReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ty
 * @description: 亚马逊源表数据Controller类
 * @date: 2022/4/24
 */
@RestController
@ApiResource(name = "亚马逊源表数据Controller类", path = "/lingXingSourceReport")
@Api(tags = "亚马逊源表数据Controller类")
public class LingXingSourceReportController {

    @Autowired
    private LingXingSourceReportService lingXingSourceReportService;

    @PostResource(name = "查询亚马逊源报表-所有订单", path = "/allOrders", requiredPermission = false, requiredLogin = false)
    @ApiOperation("查询亚马逊源报表-所有订单")
    public LingXingBaseRespData allOrders(@RequestBody AllOrdersReq req) throws Exception {
        return lingXingSourceReportService.allOrders(req);
    }

    @PostResource(name = "查询查询亚马逊源表-Settlement文件列表", path = "/settlementFileList", requiredPermission = false, requiredLogin = false)
    @ApiOperation("查询查询亚马逊源表-Settlement文件列表")
    public LingXingBaseRespData settlementFileList(@RequestBody SettlementFileListReq req) throws Exception {
        return lingXingSourceReportService.settlementFileList(req);
    }

    @PostResource(name = "查询亚马逊源表——Settlement源文件下载", path = "/getSettlementFile", requiredPermission = false, requiredLogin = false)
    @ApiOperation("查询亚马逊源表——Settlement源文件下载")
    public LingXingBaseRespData getSettlementFile(@RequestBody SettlementFileReq req) throws Exception {
        return lingXingSourceReportService.getSettlementFile(req);
    }

    @PostResource(name = "查询亚马逊源报表-退货订单", path = "/refundOrders", requiredPermission = false, requiredLogin = false)
    @ApiOperation("查询亚马逊源报表-退货订单")
    public LingXingBaseRespData refundOrders(@RequestBody RefundOrdersReq req) throws Exception {
        return lingXingSourceReportService.refundOrders(req);
    }

    @PostResource(name = "查询亚马逊源报表-交易明细", path = "/transaction", requiredPermission = false, requiredLogin = false)
    @ApiOperation("查询亚马逊源报表-交易明细")
    public LingXingBaseRespData transaction(@RequestBody TransactionReq req) throws Exception {
        return lingXingSourceReportService.transaction(req);
    }
}
